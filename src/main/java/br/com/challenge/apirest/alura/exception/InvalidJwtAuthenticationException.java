package br.com.challenge.apirest.alura.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN) // proibido acesso
public class InvalidJwtAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public InvalidJwtAuthenticationException(String ex) {
		super(ex);
	}
}
