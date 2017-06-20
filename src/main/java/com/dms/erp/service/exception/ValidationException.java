package com.dms.erp.service.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -5920939526777836937L;

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

}
