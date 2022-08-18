package br.com.challenge.apirest.alura.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.challenge.apirest.alura.repositories.security.UsuarioRepository;
import br.com.challenge.apirest.alura.security.jwt.JwtTokenProvider;
import br.com.challenge.apirest.alura.vo.security.TokenVO;
import br.com.challenge.apirest.alura.vo.security.UsuarioCredenciais;

@Service
public class AutenticacaoService {
	
	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UsuarioRepository repository;
	

	public ResponseEntity<?> signin(UsuarioCredenciais data) {

		if (checkIfParamsIsNotNull(data))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

		var tokenResponse = new TokenVO();

		try {

			var username = data.getUsername();
			var password = data.getPassword();
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
			authManager.authenticate(authentication);

			var user = repository.findByEmail(username);

			if (user.isPresent()) {
				tokenResponse = tokenProvider.createAccessToken(username, user.get().getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}

		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}

		if (tokenResponse == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		else
			return ResponseEntity.ok(tokenResponse);
	}

	public ResponseEntity<?> refreshToken(String username, String refreshToken) {

		if (checkIfParamsIsNotNull(username, refreshToken))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

		var user = repository.findByEmail(username);

		var tokenResponse = new TokenVO();

		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}

		if(tokenResponse == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		else
			return ResponseEntity.ok(tokenResponse);
	}

	private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
		return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
	}

	private boolean checkIfParamsIsNotNull(UsuarioCredenciais data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank() || data.getPassword() == null
				|| data.getPassword().isBlank();
	}
}
