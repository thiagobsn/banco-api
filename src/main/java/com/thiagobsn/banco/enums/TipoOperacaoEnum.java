package com.thiagobsn.banco.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoOperacaoEnum {
	
	CREDITO("C"),
	DEBITO("D");
	
	private String codigo;

}
