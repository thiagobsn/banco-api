package com.thiagobsn.banco.domain.cliente.dto;

import lombok.Data;

@Data
public class ClienteCadastroDTO {
	
	private Long codigo;
	private String nome;
	private String sobrenome;
	private String cpf;
	
}
