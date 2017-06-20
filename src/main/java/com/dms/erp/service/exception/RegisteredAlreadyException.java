package com.dms.erp.service.exception;

/**
 * Exceptions generated from registrations already registered.
 * 
 * @author Diorgenes Morais
 * @version 1.0.0
 */
public class RegisteredAlreadyException extends RuntimeException {

	private static final long serialVersionUID = 2064377280984319665L;

	public RegisteredAlreadyException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegisteredAlreadyException(String message) {
		super(message);
	}

}
