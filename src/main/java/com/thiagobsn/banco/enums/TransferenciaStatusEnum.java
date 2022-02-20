package com.thiagobsn.banco.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransferenciaStatusEnum {
	
	NAO_FETIVADA(0),
	EFETIVADA(1),
	ESTORNADA(3);
	
	private Integer status;

}
