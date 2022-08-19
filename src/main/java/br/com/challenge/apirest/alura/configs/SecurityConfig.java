package br.com.challenge.apirest.alura.configs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import br.com.challenge.apirest.alura.security.jwt.JwtConfigurer;
import br.com.challenge.apirest.alura.security.jwt.JwtTokenProvider;
import br.com.challenge.apirest.alura.services.security.AutorizacaoService;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration // configurar alguns beans
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutorizacaoService autenticacaoService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override // configs de autenticacao
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService);
	}

	@Override // configs de autorizacao
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				// significa que as sessões não vao armanezenar estado, nenhum registro das
				// interações anteriores são salvos.

				.and()

				.authorizeRequests()
				.antMatchers("/auth/signin", "/auth/refresh", "/v3/api-docs/**", "/swagger-ui/**", "/actuator/**")
				.permitAll().antMatchers(HttpMethod.DELETE, "/budget-control/**").hasRole("ADMINISTRADOR").anyRequest()
				.authenticated()

				.and().cors()

				.and().apply(new JwtConfigurer(tokenProvider));
	}

	@Override // configs de recursos estaticos (js, css, images, etc.)
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/swagger-ui/**", "/bus/v3/api-docs/**");
	}

	@Bean
	public PasswordEncoder passwordEnconder() {
		// como encriptar senhas para fazer comparação com db

		Map<String, PasswordEncoder> encoders = new HashMap<>();

		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());

		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

		passwordEncoder.setDefaultPasswordEncoderForMatches(new Pbkdf2PasswordEncoder());

		return passwordEncoder;
	}

}
