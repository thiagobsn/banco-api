package com.thiagobsn.banco.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoTransferenciaEnum {
	
	MANUAL(1L),
	AUTOMATICA(2L);
	
	private Long codigo;

}
