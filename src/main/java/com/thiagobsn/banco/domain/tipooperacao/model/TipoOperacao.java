package com.thiagobsn.banco.domain.tipooperacao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "TIPOOPERACAO")
@AllArgsConstructor @NoArgsConstructor
public class TipoOperacao {
	
	@Id
	@Column(length = 1)
	private String codigo;
	
	@Column(length = 30)
	private String descricao;
	
	boolean ativo;

}
