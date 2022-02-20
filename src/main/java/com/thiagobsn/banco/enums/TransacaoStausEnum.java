package com.thiagobsn.banco.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransacaoStausEnum {
	
	NAO_FETIVADA(0),
	EFETIVADA(1),
	FUTURA(3);
	
	private int status;

}
