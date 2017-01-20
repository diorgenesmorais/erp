package com.dms.erp.service.exception;

public class IdNullException extends RuntimeException {

	private static final long serialVersionUID = -7309738749033331246L;

	public IdNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdNullException(String message) {
		super(message);
	}

}
