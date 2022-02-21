package com.thiagobsn.banco.domain.transferencia.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagobsn.banco.domain.conta.model.Conta;
import com.thiagobsn.banco.domain.tipotransferencia.model.TipoTransferencia;
import com.thiagobsn.banco.domain.transferencia.dto.ListaTransferenciaDTO;
import com.thiagobsn.banco.domain.transferencia.model.Transferencia;
import com.thiagobsn.banco.domain.transferencia.repository.TransferenciaRepository;
import com.thiagobsn.banco.enums.TipoTransferenciaEnum;
import com.thiagobsn.banco.enums.TransferenciaStatusEnum;

@Service
public class TransferenciaService {
	
	@Autowired
	private TransferenciaRepository transferenciaRepository;
	
	
	public void salvarTranferenciaManual(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
		Transferencia transferencia = buildTransferenciaManual(contaOrigem, contaDestino, valor);
		salvar(transferencia);
	}
	
	public Transferencia salvar(Transferencia transferencia) {
		return transferenciaRepository.save(transferencia);
	}
	
	public Transferencia buscarPorCodigo(Long codigo) {
		Optional<Transferencia> transferencia  = transferenciaRepository.findById(codigo);
		if(transferencia.isPresent()) {
			return transferencia.get();
		}
		return null;
	}
	
	public List<ListaTransferenciaDTO> listarTraferenciaPor(Long codigoTipoConta, Long numeroAgencia, Long numeroConta){
		
		List<Transferencia> lista = transferenciaRepository.findAllByContaOrigemNumeroAndAgenciaOrigemNumeroAndTipoContaOrigemCodigoAndStatus(codigoTipoConta, numeroAgencia, numeroConta, TransferenciaStatusEnum.EFETIVADA);
		
		return lista.stream().map(tranf ->
			ListaTransferenciaDTO.builder()
			.codigo(tranf.getCodigo())
			.contaOrigem(tranf.getContaOrigem().getNumero())
			.agenciaOrigem(tranf.getAgenciaOrigem().getNumero())
			.tipoContaOrigem(tranf.getTipoContaOrigem().getCodigo())
			.contaDestino(tranf.getContaDestino().getNumero())
			.agenciaDestino(tranf.getAgenciaDestino().getNumero())
			.tipoContaDestino(tranf.getTipoContaDestino().getCodigo())
			.valor(tranf.getValor())
			.data(tranf.getData())
			.build())
				.collect(Collectors.toList());
	}
	
	private Transferencia buildTransferenciaManual(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
		return Transferencia.builder()
				.tipoTransferencia(TipoTransferencia.builder().codigo(TipoTransferenciaEnum.MANUAL.getCodigo()).build())
				.contaOrigem(contaOrigem)
				.agenciaOrigem(contaOrigem.getAgencia())
				.tipoContaOrigem(contaOrigem.getTipoConta())
				.contaDestino(contaDestino)
				.agenciaDestino(contaDestino.getAgencia())
				.tipoContaDestino(contaDestino.getTipoConta())
				.valor(valor)
				.data(LocalDate.now())
				.status(TransferenciaStatusEnum.EFETIVADA)
				.build();
	}

}
