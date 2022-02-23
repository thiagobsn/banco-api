package com.thiagobsn.banco.domain.conta.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thiagobsn.banco.domain.agencia.model.Agencia;
import com.thiagobsn.banco.domain.cliente.dto.ClienteCadastroDTO;
import com.thiagobsn.banco.domain.cliente.model.Cliente;
import com.thiagobsn.banco.domain.cliente.service.ClienteService;
import com.thiagobsn.banco.domain.conta.dto.AberturaContaDTO;
import com.thiagobsn.banco.domain.conta.dto.ContaDTO;
import com.thiagobsn.banco.domain.conta.dto.DepositoContaDTO;
import com.thiagobsn.banco.domain.conta.dto.FiltroContaDTO;
import com.thiagobsn.banco.domain.conta.model.Conta;
import com.thiagobsn.banco.domain.conta.repository.ContaRepository;
import com.thiagobsn.banco.domain.conta.util.ContaAdapter;
import com.thiagobsn.banco.domain.tipoconta.model.TipoConta;
import com.thiagobsn.banco.domain.transacao.service.TransacaoService;
import com.thiagobsn.banco.domain.transferencia.dto.ReverterTransferenciaDTO;
import com.thiagobsn.banco.domain.transferencia.dto.TransferenciaContaDTO;
import com.thiagobsn.banco.domain.transferencia.model.Transferencia;
import com.thiagobsn.banco.domain.transferencia.service.TransferenciaService;
import com.thiagobsn.banco.enums.TipoOperacaoEnum;
import com.thiagobsn.banco.enums.TransferenciaStatusEnum;
import com.thiagobsn.banco.exception.BancoApiException;
import com.thiagobsn.banco.exception.ContaInvalidaException;
import com.thiagobsn.banco.exception.SaldoInsuficienteException;

@Service
public class ContaService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ContaAdapter contaAdapter;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private TransacaoService transacaoService;
	
	@Autowired
	private TransferenciaService transferenciaService;
	
	private static final TipoOperacaoEnum TIPO_OPERACAO_CREDITO = TipoOperacaoEnum.CREDITO;
	private static final TipoOperacaoEnum TIPO_OPERACAO_DEBITO = TipoOperacaoEnum.DEBITO;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ContaDTO novaConta(AberturaContaDTO aberturaContaDTO) {
		
		ClienteCadastroDTO clienteDTO = clienteService.novoCadastro(aberturaContaDTO.getCliente());
		
		Cliente cliente 	= modelMapper.map(clienteDTO, Cliente.class);
		Agencia agencia 	= modelMapper.map(aberturaContaDTO.getAgencia(), Agencia.class);
		TipoConta tipoConta = modelMapper.map(aberturaContaDTO.getTipoConta(), TipoConta.class);
		
		Conta novaConta = Conta.builder()
				.abertura(LocalDate.now())
				.cliente(cliente)
				.agencia(agencia)
				.tipoConta(tipoConta)
				.saldo(BigDecimal.ZERO)
				.build();
		
		
		novaConta = contaRepository.save(novaConta);
		
		ContaDTO contaDTO = modelMapper.map(novaConta, ContaDTO.class);
		
		return contaDTO;
	}
	
	public List<ContaDTO> listarTodos() {
		return contaRepository.findAll().stream().map(conta -> modelMapper.map(conta, ContaDTO.class)).collect(Collectors.toList());
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void depositar(DepositoContaDTO depositoContaDTO) throws ContaInvalidaException, BancoApiException {
		
		Conta conta = buscarConta(contaAdapter.toFiltroContaDTO(depositoContaDTO));
		validarConta(conta);
		valorValido(depositoContaDTO.getValor());
		
		BigDecimal saldoAtual = conta.getSaldo();
		BigDecimal novoSaldo = saldoAtual.add(depositoContaDTO.getValor());
		conta.setSaldo(novoSaldo);
		salvar(conta);
		
		transacaoService.salvarTransacaoDeposito(conta, conta.getAgencia(), depositoContaDTO.getValor());
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void traferir(TransferenciaContaDTO transferenciaDTO) throws SaldoInsuficienteException, ContaInvalidaException, BancoApiException {
		
		Conta contaOrigem = buscarConta(contaAdapter.toFiltroContaDTO(transferenciaDTO.getContaOrigem(), transferenciaDTO.getAgenciaOrigem(), transferenciaDTO.getTipoContaOrigem()));
		Conta contaDestino = buscarConta(contaAdapter.toFiltroContaDTO(transferenciaDTO.getContaDestino(), transferenciaDTO.getAgenciaDestino(), transferenciaDTO.getTipoContaDestino()));
		
		if(validarDadosTransferencia(contaOrigem, contaDestino, transferenciaDTO.getValor())) {
			
			BigDecimal saldoContaOrigem = contaOrigem.getSaldo();
			BigDecimal valorTransferencia = transferenciaDTO.getValor();
			
			if(valorTransferencia.compareTo(saldoContaOrigem) > 0) {
				throw new SaldoInsuficienteException("Saldo insuficiente!");
			}
			
			contaOrigem.setSaldo(saldoContaOrigem.subtract(valorTransferencia));
			contaDestino.setSaldo(contaDestino.getSaldo().add(valorTransferencia));
			
			salvar(contaOrigem);
			salvar(contaDestino);
			
			salvarTransacaoTranferencia(TIPO_OPERACAO_DEBITO, contaOrigem, valorTransferencia);
			salvarTransacaoTranferencia(TIPO_OPERACAO_CREDITO, contaDestino, valorTransferencia);
			
			transferenciaService.salvarTranferenciaManual(contaOrigem, contaDestino, valorTransferencia);
		}
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void reverterTransferencia(Long codigoTipoConta, Long numeroAgencia, Long numeroConta, ReverterTransferenciaDTO reverterTransferenciaDTO) throws ContaInvalidaException {
		
		Transferencia transferencia = transferenciaService.buscarPorCodigo(reverterTransferenciaDTO.getCodigoTransferencia());
		
		if(isRevertTraferenciaValido(codigoTipoConta, numeroAgencia, numeroConta, transferencia)) {
			
			Conta contaOrigem = buscarConta(contaAdapter.toFiltroContaDTO(transferencia.getContaOrigem()));
			Conta contaDestino = buscarConta(contaAdapter.toFiltroContaDTO(transferencia.getContaDestino()));
			
			if(validarConta(contaOrigem) && validarConta(contaDestino)) {
				
				BigDecimal valorTransferencia = transferencia.getValor();
				
				contaDestino.setSaldo(contaDestino.getSaldo().subtract(valorTransferencia));
				contaOrigem.setSaldo(contaOrigem.getSaldo().add(valorTransferencia));
				
				salvar(contaDestino);
				salvar(contaOrigem);
				
				salvarTransacaoEstorno(TIPO_OPERACAO_CREDITO, contaOrigem, valorTransferencia);
				salvarTransacaoEstorno(TIPO_OPERACAO_DEBITO, contaDestino, valorTransferencia);
				
				transferencia.setStatus(TransferenciaStatusEnum.ESTORNADA);
				transferenciaService.salvar(transferencia);
			}
		}
	}
	
	private boolean isRevertTraferenciaValido(Long codigoTipoConta, Long numeroAgencia, Long numeroConta, Transferencia transferencia) {
		Conta contaOrigem = transferencia.getContaOrigem();
		return transferencia != null && TransferenciaStatusEnum.EFETIVADA.equals(transferencia.getStatus()) && 
				( 	codigoTipoConta.equals(contaOrigem.getNumero()) && 
					numeroAgencia.equals(contaOrigem.getAgencia().getNumero()) && 
					codigoTipoConta.equals(contaOrigem.getTipoConta().getCodigo()) );
	}
	
	private void salvarTransacaoEstorno(TipoOperacaoEnum tipoOperacao, Conta conta, BigDecimal valor) {
		transacaoService.salvarTransacaoEstorno(tipoOperacao, conta, conta.getAgencia(), valor);
	}
	
	public void salvarTransacaoTranferencia(TipoOperacaoEnum tipoOperacao, Conta conta, BigDecimal valor) {
		transacaoService.salvarTransacaoTranferencia(tipoOperacao, conta, conta.getAgencia(), valor);
	}
	
	private Conta salvar(Conta conta) {
		return contaRepository.save(conta);
	}
	
	public Conta buscarConta(FiltroContaDTO filtroDTO) {
		return contaRepository.findByNumeroAndAgenciaNumeroAndTipoContaCodigo(filtroDTO.getNumeroConta(), filtroDTO.getNumeroAgencia(), filtroDTO.getCodigoTipoConta());
	}
	
	public boolean validarConta(Conta conta) throws ContaInvalidaException {
		if(conta != null && conta.getNumero() != null && conta.getAgencia() != null && conta.getTipoConta() != null) {
			return true;
		}
		throw new ContaInvalidaException("Conta inválida!");
	}
	
	private boolean validarDadosTransferencia(Conta contaOrigem, Conta contaDestino, BigDecimal valor) throws BancoApiException, ContaInvalidaException {
	
		validarConta(contaOrigem);
		validarConta(contaDestino);
		
		if(contaOrigem.equals(contaDestino)) {
			throw new BancoApiException("Operação não permitida!");
		}
		
		valorValido(valor);
		
		return true;
	}
	
	private boolean valorValido( BigDecimal valor) throws BancoApiException {
		if(valor.compareTo(BigDecimal.ZERO) < 1) {
			throw new BancoApiException("Valor inválido!");
		}
		return true;
	}
	
}
