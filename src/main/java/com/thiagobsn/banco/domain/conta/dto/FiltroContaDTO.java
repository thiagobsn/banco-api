package com.thiagobsn.banco.domain.conta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class FiltroContaDTO {
	
	private Long numeroConta;
	private Long numeroAgencia;
	private Long codigoTipoConta;

}
