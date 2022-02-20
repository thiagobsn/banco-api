package com.thiagobsn.banco.domain.tipoconta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TIPOCONTA")
public class TipoConta {
	
	@Id
	private Long codigo;
	
	@Column(length = 30)
	private String descricao;
	
	boolean ativo;

}
