package com.thiagobsn.banco.exception;

public class BancoApiException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BancoApiException(String errorMessage) {
		super(errorMessage);
	}
}
