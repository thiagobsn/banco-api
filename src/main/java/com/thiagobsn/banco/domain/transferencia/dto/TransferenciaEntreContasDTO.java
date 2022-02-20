package com.thiagobsn.banco.domain.transferencia.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransferenciaEntreContasDTO {
	
	private Long contaOrigem;
	private Long agenciaOrigem;
	private Long tipoContaOrigem;
	
	private Long contaDestino;
	private Long agenciaDestino;
	private Long tipoContaDestino;
	
	private BigDecimal valor;
	
}
