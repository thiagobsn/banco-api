package com.thiagobsn.banco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiagobsn.banco.domain.cliente.dto.ClienteCadastroDTO;
import com.thiagobsn.banco.domain.cliente.dto.ClienteDTO;
import com.thiagobsn.banco.domain.cliente.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping(value = "/")
	public ResponseEntity<ClienteCadastroDTO> salvarNovoCliente(@RequestBody ClienteCadastroDTO cliente) {
		return new ResponseEntity<>(clienteService.novoCadastro(cliente), HttpStatus.OK);
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<List<ClienteDTO>> listarTodos() {
		return new ResponseEntity<>(clienteService.listarTodos(), HttpStatus.OK);
	}

}
