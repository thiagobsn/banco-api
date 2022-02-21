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
import com.thiagobsn.banco.domain.conta.model.Conta;
import com.thiagobsn.banco.domain.conta.repository.ContaRepository;
import com.thiagobsn.banco.domain.tipoconta.model.TipoConta;
import com.thiagobsn.banco.domain.transacao.service.TransacaoService;
import com.thiagobsn.banco.domain.transferencia.dto.ReverterTransferenciaDTO;
import com.thiagobsn.banco.domain.transferencia.dto.TransferenciaEntreContasDTO;
import com.thiagobsn.banco.domain.transferencia.model.Transferencia;
import com.thiagobsn.banco.domain.transferencia.service.TransferenciaService;
import com.thiagobsn.banco.enums.TransferenciaStatusEnum;

@Service
public class ContaService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private TransacaoService transacaoService;
	
	@Autowired
	private TransferenciaService transferenciaService;
	
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
	public void depositar(DepositoContaDTO depositoContaDTO) {
		
		Conta conta = buscarConta(depositoContaDTO.getNumeroConta(), depositoContaDTO.getNumeroAgencia(), depositoContaDTO.getCodigoTipoConta());
		
		BigDecimal saldoAtual = conta.getSaldo();
		BigDecimal novoSaldo = saldoAtual.add(depositoContaDTO.getValor());
		conta.setSaldo(novoSaldo);
		salvar(conta);
		
		transacaoService.salvarTransacaoDeposito(conta, conta.getAgencia(), depositoContaDTO.getValor());
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void traferir(TransferenciaEntreContasDTO transferenciaDTO) {
		Conta contaOrigem = buscarConta(transferenciaDTO.getContaOrigem(), transferenciaDTO.getAgenciaOrigem(), transferenciaDTO.getTipoContaOrigem());
		Conta contaDestino = buscarConta(transferenciaDTO.getContaDestino(), transferenciaDTO.getAgenciaDestino(), transferenciaDTO.getTipoContaDestino());
		
		if(contaOrigem != null && contaDestino != null) {
			
			BigDecimal saldoContaOrigem = contaOrigem.getSaldo();
			BigDecimal valorTransferencia = transferenciaDTO.getValor();
			
			if(valorTransferencia.compareTo(saldoContaOrigem) <= 0) {
				contaOrigem.setSaldo(saldoContaOrigem.subtract(valorTransferencia));
				contaDestino.setSaldo(contaDestino.getSaldo().add(valorTransferencia));
				
				salvar(contaOrigem);
				salvar(contaDestino);
				
				transacaoService.salvarTransacaoTranferenciaDebito(contaOrigem, contaOrigem.getAgencia(), valorTransferencia);
				transacaoService.salvarTransacaoTranferenciaCredito(contaDestino, contaDestino.getAgencia(), valorTransferencia);
				
				transferenciaService.salvarTranferenciaManual(contaOrigem, contaDestino, valorTransferencia);
				
			}
		}
		
	}
	
	private Conta salvar(Conta conta) {
		return contaRepository.save(conta);
	}
	
	private Conta buscarConta(Long numeroConta , Long numeroAgencia, Long codigoTipoConta) {
		return contaRepository.findByNumeroAndAgenciaNumeroAndTipoContaCodigo(numeroConta, numeroAgencia, codigoTipoConta);
	}

	public void reverterTransferencia(Long codigoTipoConta, Long numeroAgencia, Long numeroConta, ReverterTransferenciaDTO reverterTransferenciaDTO) {
		Transferencia transferencia = transferenciaService.buscarPorCodigo(reverterTransferenciaDTO.getCodigo());
		if(isRevertTraferenciaValido(codigoTipoConta, numeroAgencia, numeroConta, transferencia)) {
			
			Conta contaOrigem = buscarConta(transferencia.getContaOrigem().getNumero(), transferencia.getContaOrigem().getAgencia().getNumero(), transferencia.getContaOrigem().getTipoConta().getCodigo());
			Conta contaDestino = buscarConta(transferencia.getContaDestino().getNumero(), transferencia.getContaDestino().getAgencia().getNumero(), transferencia.getContaDestino().getTipoConta().getCodigo());
			
			if(contaOrigem != null && contaDestino != null) {
				
				BigDecimal valorTransferencia = transferencia.getValor();
				
				contaDestino.setSaldo(contaDestino.getSaldo().subtract(valorTransferencia));
				contaOrigem.setSaldo(contaOrigem.getSaldo().add(valorTransferencia));
				
				salvar(contaDestino);
				salvar(contaOrigem);
				
				transacaoService.salvarTransacaoEstornoCredito(contaOrigem, contaOrigem.getAgencia(), valorTransferencia);
				transacaoService.salvarTransacaoEstornoDebito(contaDestino, contaDestino.getAgencia(), valorTransferencia);
				
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
}
