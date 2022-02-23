package com.thiagobsn.banco.exception.handler;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.thiagobsn.banco.exception.BancoApiException;
import com.thiagobsn.banco.exception.ContaInvalidaException;
import com.thiagobsn.banco.exception.SaldoInsuficienteException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler  {
	
	@ExceptionHandler(value = { SaldoInsuficienteException.class, ContaInvalidaException.class, BancoApiException.class } )
	protected ResponseEntity<Object> handleConflict(Exception exception, WebRequest request) {
		return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}
	