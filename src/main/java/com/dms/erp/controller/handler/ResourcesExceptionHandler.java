package com.dms.erp.controller.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dms.erp.model.DetailedError;
import com.dms.erp.service.exception.RegisteredAlreadyException;
import com.dms.erp.service.exception.ValidationException;

/**
 * Classe que manipula as exceções do controle
 * 
 * @author Diorgenes Morais
 * @version 1.0.2
 */
@ControllerAdvice
public class ResourcesExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<DetailedError> handlerValidationException(ValidationException e, HttpServletRequest request) {

		DetailedError erro = new DetailedError();
		erro.setStatus(400L);
		erro.setTitulo("Validação");
		erro.setMensagemDesenvolvedor("http://erros.erp.com/400");
		erro.setTimestamp(System.currentTimeMillis());
		erro.setResponseText(e.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(RegisteredAlreadyException.class)
	public ResponseEntity<DetailedError> handlerRegisteredAlreadyException(RegisteredAlreadyException e,
			HttpServletRequest request) {

		DetailedError erro = new DetailedError();
		erro.setStatus(409L);
		erro.setTitulo("Conflito - registro já existe");
		erro.setMensagemDesenvolvedor("http://erros.erp.com/409");
		erro.setTimestamp(System.currentTimeMillis());
		erro.setResponseText(e.getMessage());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DetailedError> handlerDataIntegrityViolationException(DataIntegrityViolationException e,
			HttpServletRequest request) {

		DetailedError erro = new DetailedError();
		erro.setStatus(400L);
		erro.setTitulo("Requisição inválida");
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/400");
		erro.setTimestamp(System.currentTimeMillis());
		erro.setResponseText(e.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
