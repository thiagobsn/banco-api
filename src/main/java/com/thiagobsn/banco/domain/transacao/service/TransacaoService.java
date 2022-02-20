package com.thiagobsn.banco.domain.transacao.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagobsn.banco.domain.agencia.model.Agencia;
import com.thiagobsn.banco.domain.conta.model.Conta;
import com.thiagobsn.banco.domain.tipooperacao.model.TipoOperacao;
import com.thiagobsn.banco.domain.tipotransacao.model.TipoTransacao;
import com.thiagobsn.banco.domain.transacao.model.Transacao;
import com.thiagobsn.banco.domain.transacao.repository.TransacaoRepository;
import com.thiagobsn.banco.enums.TipoOperacaoEnum;
import com.thiagobsn.banco.enums.TipoTransacaoEnum;
import com.thiagobsn.banco.enums.TransacaoStausEnum;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	
	public void salvarTransacaoDeposito(Conta conta, Agencia agencia, BigDecimal valor) {
		salvarTransacao(buildTipoOperacaoCredito(), buildTipoTransacaoDeposito(), conta, agencia, valor);
	}
	
	public void salvarTransacaoTranferenciaCredito(Conta conta, Agencia agencia, BigDecimal valor) {
		salvarTransacao(buildTipoOperacaoCredito(), buildTipoTransacaoDeposito(), conta, agencia, valor);
	}
	
	public void salvarTransacaoTranferenciaDebito(Conta conta, Agencia agencia, BigDecimal valor) {
		salvarTransacao(buildTipoOperacaoDebito(), buildTipoTransacaoDeposito(), conta, agencia, valor);
	}
	
	private void salvarTransacao(TipoOperacao tipoOperacao, TipoTransacao tipoTransacao, Conta conta, Agencia agencia, BigDecimal valor) {
		
		Transacao transacao = Transacao.builder()
				.tipoOperacao(tipoOperacao)
				.tipoTransacao(tipoTransacao)
				.agencia(agencia)
				.conta(conta)
				.valor(valor)
				.data(LocalDate.now())
				.status(TransacaoStausEnum.EFETIVADA)
				.build();
		
		salvar(transacao);
	}
	
	
	private Transacao salvar(Transacao transacao) {
		return transacaoRepository.save(transacao);
	}
	
	private TipoOperacao buildTipoOperacaoCredito() {
		return buildTipoOperacao(TipoOperacaoEnum.CREDITO);
	}
	
	private TipoOperacao buildTipoOperacaoDebito() {
		return buildTipoOperacao(TipoOperacaoEnum.DEBITO);
	}
	
	private TipoOperacao buildTipoOperacao(TipoOperacaoEnum tipo) {
		return TipoOperacao.builder()
				.codigo(tipo.getCodigo())
				.build();
	}
	
	private TipoTransacao buildTipoTransacaoDeposito() {
		return TipoTransacao.builder()
		.codigo(TipoTransacaoEnum.DEPOSITO.getCodigo())
		.build();
	}
	
	
}
