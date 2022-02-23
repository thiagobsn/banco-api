package com.thiagobsn.banco.domain.transferencia.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class TransferenciaContaDTO {
	
	private Long contaOrigem;
	private Long agenciaOrigem;
	private Long tipoContaOrigem;
	
	private Long contaDestino;
	private Long agenciaDestino;
	private Long tipoContaDestino;
	
	private BigDecimal valor;
	
}
