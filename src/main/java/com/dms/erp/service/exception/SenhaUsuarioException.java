package com.dms.erp.service.exception;

public class SenhaUsuarioException extends RuntimeException {

	private static final long serialVersionUID = 4866694748188537453L;

	public SenhaUsuarioException(String message, Throwable cause) {
		super(message, cause);
	}

	public SenhaUsuarioException(String message) {
		super(message);
	}

}
