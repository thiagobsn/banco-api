package com.thiagobsn.banco.domain.tipotransferencia.model;

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
@Table(name = "TIPOTRANSFERENCIA")
@AllArgsConstructor @NoArgsConstructor
public class TipoTransferencia {
	
	@Id
	private Long codigo;
	
	@Column(length = 30)
	private String nome;
	
}
