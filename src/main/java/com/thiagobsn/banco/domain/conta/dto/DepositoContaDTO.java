package com.thiagobsn.banco.domain.conta.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DepositoContaDTO {
	
	private Long numeroConta;
	private Long numeroAgencia;
	private Long codigoTipoConta;
	
	private BigDecimal valor;

}
