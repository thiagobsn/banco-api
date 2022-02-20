package com.thiagobsn.banco.domain.cliente.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagobsn.banco.domain.cliente.dto.ClienteCadastroDTO;
import com.thiagobsn.banco.domain.cliente.dto.ClienteDTO;
import com.thiagobsn.banco.domain.cliente.model.Cliente;
import com.thiagobsn.banco.domain.cliente.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ClienteRepository clienteRepository;
	
	public ClienteCadastroDTO novoCadastro(ClienteCadastroDTO clienteCadastroDTO) {
		Cliente cliente = modelMapper.map(clienteCadastroDTO, Cliente.class);
		cliente = clienteRepository.save(cliente);
		return modelMapper.map(cliente, ClienteCadastroDTO.class);
	}
	
	public List<ClienteDTO> listarTodos() {
		return clienteRepository.findAll().stream()
				.map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
				.collect(Collectors.toList());
	}
	
}
