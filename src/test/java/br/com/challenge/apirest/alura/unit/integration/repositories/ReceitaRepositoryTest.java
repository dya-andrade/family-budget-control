package br.com.challenge.apirest.alura.unit.integration.repositories;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.challenge.apirest.alura.model.Receita;
import br.com.challenge.apirest.alura.repositories.ReceitaRepository;
import br.com.challenge.apirest.alura.testcontainers.AbstractIntegrationTest;

//@RunWith(SpringRunner.class) JUnit 4
@ExtendWith(SpringExtension.class) // JUnit5
@DataJpaTest // não precisa do context do spring, apenas do JPA
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // não irá alterar o bd
@TestInstance(Lifecycle.PER_CLASS) //  cria apenas uma instância da classe de teste e reutilizá-la entre os testes.
@TestMethodOrder(OrderAnnotation.class)
public class ReceitaRepositoryTest extends AbstractIntegrationTest { // abstractIntegration cria o banco atraves do																	// flyway

	@Autowired
	private ReceitaRepository repository;

	@Test
	@Order(1) // Simulando REPOSITORY
	public void testFindByDescricaoAndMes() {

		Receita receita = repository.findByDescricaoAndMes("Salário", 8);

		assertNotNull(receita);
	}
	
	@Test
	@Order(2) // Simulando REPOSITORY
	public void testFindByDescricao() {
		
		Pageable pageable = PageRequest.of(0, 5, Sort.by(Direction.ASC, "descricao"));

		Page<Receita> receitas = repository.findByDescricao("o", pageable);

		assertFalse(receitas.isEmpty());
	}
	
	@Test
	@Order(3) // Simulando REPOSITORY
	public void testFindByMonth() {

		List<Receita> receitas = repository.findByMonth(2022, 8);

		assertFalse(receitas.isEmpty());
	}
	
	@Test
	@Order(4) // Simulando REPOSITORY
	public void testFindTotalAmountByMonth() {

		Double valorToral = repository.findTotalAmountByMonth(2022, 8);

		assertNotNull(valorToral);
		assertTrue(valorToral > 0);
	}
}
