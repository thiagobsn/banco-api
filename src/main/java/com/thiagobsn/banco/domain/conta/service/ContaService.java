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
import com.thiagobsn.banco.enums.ContaStausEnum;

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
				.status(ContaStausEnum.ATIVA)
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
		
		Conta conta = contaRepository.findByNumeroAndAgenciaNumeroAndTipoContaCodigo(depositoContaDTO.getNumeroConta(), depositoContaDTO.getNumeroAgencia(), depositoContaDTO.getCodigoTipoConta());
		
		BigDecimal saldoAtual = conta.getSaldo();
		BigDecimal novoSaldo = saldoAtual.add(depositoContaDTO.getValor());
		conta.setSaldo(novoSaldo);
		contaRepository.save(conta);
		
		transacaoService.salvarTransacaoTipoDeposito(conta, conta.getAgencia(), depositoContaDTO.getValor());
	}

}
