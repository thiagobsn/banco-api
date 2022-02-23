package com.thiagobsn.banco.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AgendamentoStatusEnum {
	
	CANCELADO(0),
	AGENDADO(1),
	EFETIVADO(3),
	NAO_EFETIVADO(4);
	
	private Integer status;

}
