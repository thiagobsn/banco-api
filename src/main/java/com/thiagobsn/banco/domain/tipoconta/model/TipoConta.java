package com.thiagobsn.banco.domain.tipoconta.model;

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
@Table(name = "TIPOCONTA")
@AllArgsConstructor @NoArgsConstructor
public class TipoConta {
	
	@Id
	private Long codigo;
	
	@Column(length = 30)
	private String descricao;
	
	boolean ativo;

}
