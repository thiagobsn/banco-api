package com.thiagobsn.banco.domain.conta.model;

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
import com.thiagobsn.banco.domain.cliente.model.Cliente;
import com.thiagobsn.banco.domain.tipoconta.model.TipoConta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "CONTA")
@Builder
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(exclude = {"saldo", "abertura", "encerramento"})
public class Conta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numero;
	
	@ManyToOne
    @JoinColumn(name = "codigo_cliente")
	private Cliente cliente;
	
	@ManyToOne
    @JoinColumn(name = "numero_agencia")
	private Agencia agencia;
	
	@ManyToOne
	@JoinColumn(name = "codigo_tipoconta")
	private TipoConta tipoConta;
	
	private BigDecimal saldo;
	
	private LocalDate abertura;
	
	private LocalDate encerramento;
	
}
