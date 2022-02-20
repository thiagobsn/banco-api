package com.thiagobsn.banco.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoTransacaoEnum {
	
	SAQUE(1L),
	DEPOSITO(2L),
	TRANFERENCIA(3L);
	
	private Long codigo;

}
