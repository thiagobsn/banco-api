package com.thiagobsn.banco.exception;

public class ContaInvalidaException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ContaInvalidaException(String errorMessage) {
		super(errorMessage);
	}
}
