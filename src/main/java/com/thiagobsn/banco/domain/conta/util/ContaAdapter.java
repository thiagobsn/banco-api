package com.thiagobsn.banco.domain.conta.util;

import org.springframework.stereotype.Component;

import com.thiagobsn.banco.domain.conta.dto.DepositoContaDTO;
import com.thiagobsn.banco.domain.conta.dto.FiltroContaDTO;
import com.thiagobsn.banco.domain.conta.model.Conta;

@Component
public class ContaAdapter {
	
	
	public FiltroContaDTO toFiltroContaDTO(Conta conta) {
		return toFiltroContaDTO(conta.getNumero(), conta.getAgencia().getNumero(), conta.getTipoConta().getCodigo());
	}
	
	public FiltroContaDTO toFiltroContaDTO(DepositoContaDTO depositoContaDTO) {
		return toFiltroContaDTO(depositoContaDTO.getNumeroConta(), depositoContaDTO.getNumeroAgencia(), depositoContaDTO.getCodigoTipoConta());
	}
	
	public FiltroContaDTO toFiltroContaDTO(Long numeroConta , Long numeroAgencia, Long codigoTipoConta) {
		return FiltroContaDTO.builder()
				.numeroConta(numeroConta)
				.numeroAgencia(numeroAgencia)
				.codigoTipoConta(codigoTipoConta)
				.build();
	}

}
