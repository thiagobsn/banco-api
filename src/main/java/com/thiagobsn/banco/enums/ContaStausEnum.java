package com.thiagobsn.banco.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContaStausEnum {
	
	INATIVA(0),
	ATIVA(1),
	BLOQUEADA(3);
	
	private int status;

}
