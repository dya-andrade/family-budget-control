package br.com.challenge.apirest.alura.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
		
	@Value("${cors.originPatterns:default}") //ler do .yml
	private String corsOriginPatterns = "";
	

	@Override //cors de forma global
	public void addCorsMappings(CorsRegistry registry) {
		var allowedOrigins = corsOriginPatterns.split(",");
		registry.addMapping("/**") //todas rotas API
			//.allowedMethods("GET", "POST")
			.allowedMethods("*") //todos metodos
			.allowedOrigins(allowedOrigins)
		.allowCredentials(true); //autenticação
	}

}
