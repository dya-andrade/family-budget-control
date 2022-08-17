package br.com.challenge.apirest.alura.testcontainers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

//Teste de Integração
//Biblioteca que usa a API Docker para fornecer instâncias leves e descartáveis de bancos de dados comuns
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

	//PowerShell como admin e logar no docker desktop
	//& 'C:\Program Files\Docker\Docker\DockerCli.exe' -SwitchDaemon
	
	 static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
		
		//version docker mysql
		static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.29");
		
		private static void startContainers() {
			Startables.deepStart(Stream.of(mysql)).join();
		}
		
		private static Map<String, String> createConnectionConfiguration() {
			return Map.of(
					"spring.datasource.url", mysql.getJdbcUrl(),
					"spring.datasource.username", mysql.getUsername(),
					"spring.datasource.password", mysql.getPassword()
			);
		}

		@SuppressWarnings({"unchecked", "rawtypes"})
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			
			startContainers();
			
			//acessa o ambiente do spring, pega as configs do testContainers e seta no context do spring
			
			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			
			MapPropertySource testContainers = new MapPropertySource("testContainers", (Map) createConnectionConfiguration());
			
			environment.getPropertySources().addFirst(testContainers);
		}

	}
}
