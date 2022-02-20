package com.thiagobsn.banco.domain.transferencia.model;

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
import com.thiagobsn.banco.domain.tipoconta.model.TipoConta;
import com.thiagobsn.banco.domain.tipotransferencia.model.TipoTransferencia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "TRANSFERENCIA")
@AllArgsConstructor @NoArgsConstructor
public class Transferencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne
    @JoinColumn(name = "conta_origem")
	private Conta contaOrigem;
	
	@ManyToOne
    @JoinColumn(name = "agencia_origem")
	private Agencia agenciaOrigem;
	
	@ManyToOne
    @JoinColumn(name = "tipoconta_origem")
	private TipoConta tipoContaOrigem;
	
	@ManyToOne
    @JoinColumn(name = "conta_destino")
	private Conta contaDestino;
	
	@ManyToOne
    @JoinColumn(name = "agencia_destino")
	private Agencia agenciaDestino;
	
	@ManyToOne
    @JoinColumn(name = "tipoconta_destino")
	private TipoConta tipoContaDestino;
	
	private BigDecimal valor;
	
	@ManyToOne
    @JoinColumn(name = "codigo_tipotransferencia")
	private TipoTransferencia tipoTransferencia;
	
	private LocalDate data;
	
	private Integer status;

}
