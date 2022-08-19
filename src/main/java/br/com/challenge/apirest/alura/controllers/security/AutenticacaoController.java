package br.com.challenge.apirest.alura.controllers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.apirest.alura.services.security.AutenticacaoService;
import br.com.challenge.apirest.alura.vo.security.UsuarioCredenciais;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/budget-control/auth")
@Tag(name = "Autenticação", description = "Token para acesso aos endpoints.")
public class AutenticacaoController {

	@Autowired
	private AutenticacaoService service;

	@Operation(summary = "Autenticação via crendenciais do usuario.", description = "Autenticação via crendenciais do usuario com retorno do token de acesso.", tags = {
			"Autenticação" })
	@PostMapping(value = "/signin")
	public ResponseEntity<?> signin(@RequestBody UsuarioCredenciais data) {

		var token = service.signin(data);

		return token;
	}

	@Operation(summary = "Autenticação via token, com path username.", description = "Autenticação via token, e path username com retorno do token refresh.", tags = {
			"Autenticação" })
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity<?> refreshToken(@PathVariable("username") String username,
			@RequestHeader("Authorization") String refreshToken) {

		var token = service.refreshToken(username, refreshToken);

		return token;
	}
}
