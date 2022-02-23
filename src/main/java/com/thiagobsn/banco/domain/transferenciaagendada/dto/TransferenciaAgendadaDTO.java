package com.thiagobsn.banco.domain.transferenciaagendada.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.thiagobsn.banco.domain.conta.model.Conta;
import com.thiagobsn.banco.enums.AgendamentoStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class TransferenciaAgendadaDTO {
	
	private Long codigo;
	
	private LocalDate dataAgendamento;
	
	private Conta contaOrigem;
	
	private Conta contaDestino;
	
	private BigDecimal valor;
	
	private AgendamentoStatusEnum status;

}
