package com.thiagobsn.banco.domain.tipotransacao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "TIPOTRANSACAO")
@AllArgsConstructor @NoArgsConstructor
public class TipoTransacao {
	
	@Id
	private Long codigo;
	
	@Column(length = 30)
	private String descricao;
	
	boolean ativo;

}
