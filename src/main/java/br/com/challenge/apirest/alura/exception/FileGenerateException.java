package br.com.challenge.apirest.alura.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileGenerateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public FileGenerateException(String ex) {
		super(ex);
	}
	
	public FileGenerateException(String ex, Throwable cause) {
		super(ex, cause);
	}
}
