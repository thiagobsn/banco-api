package com.thiagobsn.banco.domain.conta.dto;

import java.math.BigDecimal;

import com.thiagobsn.banco.domain.agencia.dto.AgenciaDTO;
import com.thiagobsn.banco.domain.cliente.dto.ClienteDTO;
import com.thiagobsn.banco.domain.tipoconta.dto.TipoContaDTO;

import lombok.Data;

@Data
public class ContaDTO {
	
	private Long numero;
	
	private ClienteDTO cliente;
	
	private AgenciaDTO agencia;
	
	private TipoContaDTO tipoConta;
	
	private BigDecimal saldo;

}
