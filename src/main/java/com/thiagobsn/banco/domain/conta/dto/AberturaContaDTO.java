package com.thiagobsn.banco.domain.conta.dto;

import com.thiagobsn.banco.domain.agencia.model.Agencia;
import com.thiagobsn.banco.domain.cliente.dto.ClienteCadastroDTO;
import com.thiagobsn.banco.domain.tipoconta.dto.TipoContaDTO;

import lombok.Data;

@Data
public class AberturaContaDTO {
	
	private ClienteCadastroDTO cliente;
	private Agencia agencia;
	private TipoContaDTO tipoConta;

}
