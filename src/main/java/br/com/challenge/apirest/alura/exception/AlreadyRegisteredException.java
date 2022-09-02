package br.com.challenge.apirest.alura.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AlreadyRegisteredException() {
		super("Conflito, receita já registrada este mês!");
	}
	
	public AlreadyRegisteredException(String ex) {
		super(ex);
	}
}
