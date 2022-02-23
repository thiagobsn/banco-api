package com.thiagobsn.banco.domain.transferenciaagendada.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagobsn.banco.domain.conta.model.Conta;
import com.thiagobsn.banco.domain.conta.service.ContaService;
import com.thiagobsn.banco.domain.conta.util.ContaAdapter;
import com.thiagobsn.banco.domain.transferencia.dto.AgendadaDTO;
import com.thiagobsn.banco.domain.transferenciaagendada.dto.TransferenciaAgendadaDTO;
import com.thiagobsn.banco.domain.transferenciaagendada.model.TransferenciaAgendada;
import com.thiagobsn.banco.domain.transferenciaagendada.repository.TransferenciaAgendadaRepository;
import com.thiagobsn.banco.enums.AgendamentoStatusEnum;
import com.thiagobsn.banco.exception.BancoApiException;
import com.thiagobsn.banco.exception.ContaInvalidaException;

@Service
public class TransferenciaAgendadaService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ContaAdapter contaAdapter;
	
	@Autowired
	private TransferenciaAgendadaRepository transferenciaAgendadaRepository;
	
	@Autowired 
	private ContaService contaService;
	
	
	public List<LocalDate> agendar(Long codigoTipoConta, Long numeroAgencia, Long numeroConta, AgendadaDTO agendamentoDTO) throws Exception {
		List<LocalDate> datasAgendamento = new ArrayList<>();
		Conta contaOrigem = contaService.buscarConta(contaAdapter.toFiltroContaDTO(numeroConta, numeroAgencia, codigoTipoConta));
		Conta contaDestino = contaService.buscarConta(contaAdapter.toFiltroContaDTO(agendamentoDTO.getContaDestino(), agendamentoDTO.getAgenciaDestino(), agendamentoDTO.getTipoContaDestino()));
		
		LocalDate dataPrimeiraTransferencia = agendamentoDTO.getData();
		
		if(validarDadosAgendamento(contaOrigem, contaDestino, agendamentoDTO.getValor(), dataPrimeiraTransferencia)) {
			
			datasAgendamento = getDatasAgendameto(dataPrimeiraTransferencia, agendamentoDTO.getNumeroRepeticoes());
			
			List<TransferenciaAgendada> lista = datasAgendamento.stream().map(data -> {
				return buildTransferenciaAgendada(contaOrigem, contaDestino, agendamentoDTO.getValor(), data);
			}).collect(Collectors.toList());
			
			transferenciaAgendadaRepository.saveAll(lista);
		}
		
		return datasAgendamento;
	}
	
	public List<TransferenciaAgendadaDTO> listarTodos() {
		List<TransferenciaAgendada> list  = transferenciaAgendadaRepository.findAll();
		return list.stream().map(agendada -> modelMapper.map(agendada, TransferenciaAgendadaDTO.class)).collect(Collectors.toList());
	}

	
	private List<LocalDate> getDatasAgendameto(LocalDate dataInicial, Integer repeticoes) {
		List<LocalDate> listaDatasAgendamento = new ArrayList<LocalDate>();
		listaDatasAgendamento.add(dataInicial);
		for(int i = 0;  i < repeticoes ; i++) {
			listaDatasAgendamento.add(dataInicial.plusMonths(i+1));
		}
		return listaDatasAgendamento;
	}
	
	private TransferenciaAgendada buildTransferenciaAgendada(Conta contaOrigem, Conta contaDestino, BigDecimal valor, LocalDate data) {
		return TransferenciaAgendada.builder()
				.contaOrigem(contaOrigem)
				.agenciaOrigem(contaOrigem.getAgencia())
				.tipoContaOrigem(contaOrigem.getTipoConta())
				.contaDestino(contaDestino)
				.agenciaDestino(contaDestino.getAgencia())
				.tipoContaDestino(contaDestino.getTipoConta())
				.valor(valor)
				.dataAgendamento(data)
				.status(AgendamentoStatusEnum.AGENDADO)
				.build();
	}
	
	private boolean validarDadosAgendamento(Conta contaOrigem, Conta contaDestino, BigDecimal valor, LocalDate data) throws BancoApiException, ContaInvalidaException {
		contaService.validarConta(contaOrigem);
		contaService.validarConta(contaDestino);
		if(contaOrigem.equals(contaDestino)) {
			throw new BancoApiException("Operação não permitida!");
		}
		if(valor.compareTo(BigDecimal.ZERO) < 1) {
			throw new BancoApiException("Valor inválido!");
		}
		if(!data.isAfter(LocalDate.now()) ) {
			throw new BancoApiException("Data inválida!");
		}
		return true;
	}

}
