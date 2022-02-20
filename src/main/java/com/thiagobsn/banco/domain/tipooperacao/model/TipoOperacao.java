package com.thiagobsn.banco.domain.tipooperacao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TipoOperacao")
public class TipoOperacao {
	
	@Id
	private Long codigo;
	
	@Column(length = 1)
	private String descricao;
	
	boolean ativo;

}
