package com.thiagobsn.banco.domain.cliente.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "Cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(length = 20)
	private String nome;
	
	@Column(length = 50)
	private String sobrenome;
	
	@Column(length = 13)
	private String cpf;
	
	boolean ativo;
	
}
