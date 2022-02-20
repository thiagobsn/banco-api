package com.thiagobsn.banco.domain.transferencia.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagobsn.banco.domain.agencia.model.Agencia;
import com.thiagobsn.banco.domain.conta.model.Conta;
import com.thiagobsn.banco.domain.tipoconta.model.TipoConta;
import com.thiagobsn.banco.domain.tipotransferencia.model.TipoTransferencia;
import com.thiagobsn.banco.domain.transferencia.dto.TransferenciaEntreContasDTO;
import com.thiagobsn.banco.domain.transferencia.model.Transferencia;
import com.thiagobsn.banco.domain.transferencia.repository.TransferenciaRepository;
import com.thiagobsn.banco.enums.TipoTransferenciaEnum;

@Service
public class TransferenciaService {
	
	@Autowired
	private TransferenciaRepository transferenciaRepository;
	
	public void salvarTranferenciaManual(TransferenciaEntreContasDTO transfereciaDTO) {
		Transferencia transferencia = buildTransferenciaManual(transfereciaDTO);
		transferenciaRepository.save(transferencia);
	}
	
	private Transferencia buildTransferenciaManual(TransferenciaEntreContasDTO tranferecia) {
		return Transferencia.builder()
				.tipoTransferencia(TipoTransferencia.builder().codigo(TipoTransferenciaEnum.MANUAL.getCodigo()).build())
				.contaOrigem(Conta.builder().numero(tranferecia.getContaOrigem()).build())
				.agenciaOrigem(Agencia.builder().numero(tranferecia.getAgenciaOrigem()).build())
				.tipoContaOrigem(TipoConta.builder().codigo(tranferecia.getTipoContaOrigem()).build())
				.contaDestino(Conta.builder().numero(tranferecia.getContaDestino()).build())
				.agenciaDestino(Agencia.builder().numero(tranferecia.getAgenciaDestino()).build())
				.tipoContaDestino(TipoConta.builder().codigo(tranferecia.getTipoContaDestino()).build())
				.valor(tranferecia.getValor())
				.data(LocalDate.now())
				.status(1)
				.build();
	}

}
