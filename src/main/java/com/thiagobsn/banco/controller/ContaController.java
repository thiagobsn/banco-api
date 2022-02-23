package com.thiagobsn.banco.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiagobsn.banco.domain.conta.dto.AberturaContaDTO;
import com.thiagobsn.banco.domain.conta.dto.ContaDTO;
import com.thiagobsn.banco.domain.conta.dto.DepositoContaDTO;
import com.thiagobsn.banco.domain.conta.service.ContaService;
import com.thiagobsn.banco.domain.transferencia.dto.ListaTransferenciaDTO;
import com.thiagobsn.banco.domain.transferencia.dto.ReverterTransferenciaDTO;
import com.thiagobsn.banco.domain.transferencia.dto.AgendadaDTO;
import com.thiagobsn.banco.domain.transferencia.dto.TransferenciaContaDTO;
import com.thiagobsn.banco.domain.transferencia.service.TransferenciaService;
import com.thiagobsn.banco.domain.transferenciaagendada.dto.TransferenciaAgendadaDTO;
import com.thiagobsn.banco.domain.transferenciaagendada.service.TransferenciaAgendadaService;
import com.thiagobsn.banco.exception.BancoApiException;
import com.thiagobsn.banco.exception.ContaInvalidaException;
import com.thiagobsn.banco.exception.SaldoInsuficienteException;

@RestController
@RequestMapping("/api/contas")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private TransferenciaService transferenciaService;
	
	@Autowired
	private TransferenciaAgendadaService transferenciaAgendadaService;
	
	@PostMapping(value = "/")
	public ResponseEntity<ContaDTO> salvarNovoConta(@RequestBody AberturaContaDTO conta) {
		return new ResponseEntity<>(contaService.novaConta(conta), HttpStatus.OK);
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<List<ContaDTO>> listarTodos() {
		return new ResponseEntity<>(contaService.listarTodos(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/depositar")
	public ResponseEntity<Boolean> depositar(@RequestBody DepositoContaDTO deposito) throws ContaInvalidaException {
		contaService.depositar(deposito);
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}
	
	@PostMapping(value = "/transferir")
	public ResponseEntity<Boolean> traferir(@RequestBody TransferenciaContaDTO transferencia) throws SaldoInsuficienteException, ContaInvalidaException, BancoApiException {
		contaService.traferir(transferencia);
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{codigoTipoConta}/{numeroAgencia}/{numeroConta}/transferencias")
	public ResponseEntity<List<ListaTransferenciaDTO>> listarTranferencias(@PathVariable Long codigoTipoConta, @PathVariable Long numeroAgencia, @PathVariable Long numeroConta) {
		return new ResponseEntity<>(transferenciaService.listarTraferenciaPor(codigoTipoConta, numeroAgencia, numeroConta), HttpStatus.OK);
	}
	
	@PostMapping(value = "/{codigoTipoConta}/{numeroAgencia}/{numeroConta}/transferencias/reverter")
	public ResponseEntity<Boolean> reverterTrafereancia(@PathVariable Long codigoTipoConta, 
			@PathVariable Long numeroAgencia, 
			@PathVariable Long numeroConta, 
			@RequestBody ReverterTransferenciaDTO reverterTransferenciaDTO) throws ContaInvalidaException {
		contaService.reverterTransferencia(codigoTipoConta, numeroAgencia, numeroConta, reverterTransferenciaDTO);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	@PostMapping(value = "/{codigoTipoConta}/{numeroAgencia}/{numeroConta}/transferencias/agendar")
	public ResponseEntity< List<LocalDate>> agendarTrafereancia(@PathVariable Long codigoTipoConta, 
			@PathVariable Long numeroAgencia, 
			@PathVariable Long numeroConta, 
			@RequestBody AgendadaDTO transferenciaAgendadaDTO) throws Exception {
		return new ResponseEntity<>(transferenciaAgendadaService.agendar(codigoTipoConta, numeroAgencia, numeroConta, transferenciaAgendadaDTO), HttpStatus.OK);
	}
	
	@GetMapping(value = "/transferencias/agendamentos")
	public ResponseEntity<List<TransferenciaAgendadaDTO>> agendamentos() throws Exception {
		return new ResponseEntity<>(transferenciaAgendadaService.listarTodos(), HttpStatus.OK);
	}

}
