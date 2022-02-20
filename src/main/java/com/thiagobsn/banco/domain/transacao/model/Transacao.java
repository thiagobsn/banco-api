package com.thiagobsn.banco.domain.transacao.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.thiagobsn.banco.domain.agencia.model.Agencia;
import com.thiagobsn.banco.domain.conta.model.Conta;
import com.thiagobsn.banco.domain.tipooperacao.model.TipoOperacao;
import com.thiagobsn.banco.domain.tipotransacao.model.TipoTransacao;
import com.thiagobsn.banco.enums.TransacaoStausEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "TRANSACAO")
@AllArgsConstructor @NoArgsConstructor
public class Transacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne
    @JoinColumn(name = "codigo_tipotransacao")
	private TipoTransacao tipoTransacao;
	
	@ManyToOne
    @JoinColumn(name = "codigo_tipooperacao")
	private TipoOperacao tipoOperacao;
	
	@ManyToOne
	@JoinColumn(name = "numero_conta")
	private Conta conta;
	 
	@ManyToOne
	@JoinColumn(name = "numero_agencia")
	private Agencia agencia;
	
	private BigDecimal valor;
	
	private LocalDate data;
	
	private TransacaoStausEnum status;
	

}
