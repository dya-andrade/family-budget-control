package br.com.challenge.apirest.alura.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends GenericFilterBean{

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override //vai ser executado a cada requisição
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// Filtra todo o tráfego HTTP 
		// Obtém o tonken do cabeçalho da request
		String token = tokenProvider.resolveToken((HttpServletRequest) request);
		
		// Se o token for diferente de null e válido então prossegue
		if(token != null && tokenProvider.validateToken(token)) {
			
			// Tenta obter a autenticação à partir do token passado
			Authentication auth = tokenProvider.getAuthentication(token);
			
			// Se estiver tudo OK com o token então a autenticação do usuario é setada no contexto
			if(auth != null) { 	
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		
		// Aplica o filtro
		chain.doFilter(request, response);
	}

}
