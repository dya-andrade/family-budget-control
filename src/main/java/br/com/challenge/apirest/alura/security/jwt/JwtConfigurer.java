package br.com.challenge.apirest.alura.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	public JwtConfigurer(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//Instancia um novo filtro para todo o tráfego HTTP
		JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);
		
		//Adiciona esse filtro às politicas de segurança HTTP
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
