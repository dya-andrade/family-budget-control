package br.com.challenge.apirest.alura.unitests.mockito.repositories;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.challenge.apirest.alura.model.Receita;
import br.com.challenge.apirest.alura.repositories.ReceitaRepository;
import br.com.challenge.apirest.alura.unitests.mockito.asserts.ReceitaAssert;
import br.com.challenge.apirest.alura.unitests.mockito.mocks.ReceitaMock;

//@RunWith(SpringRunner.class) JUnit 4
@ExtendWith(SpringExtension.class) //JUnit5
@DataJpaTest//para testar repository
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //não irá alterar o bd
@TestMethodOrder(OrderAnnotation.class) 
public class ReceitaRepositoryTest {
	
	private ReceitaMock receitaMock;
	
	private Receita entity;
		
	@Mock
	private ReceitaRepository repository;
	
	@BeforeEach //executado antes de cada método 
	void setUpMocks() throws Exception {
		ReceitaAssert.setNumber(1);
		receitaMock = new ReceitaMock();
		entity = receitaMock.mockEntity(1);
		
		MockitoAnnotations.openMocks(this); // inicializa campos anotados com anotações Mockito
	}
	
	@Test
	@Order(1) //Simulando REPOSITORY
	public void testFindByDescricaoAndMes()  {
		
		String descricao = entity.getDescricao();
		int mes = entity.getData().getMonthValue();
				
		when(repository.findByDescricaoAndMes
				(descricao, mes)).thenReturn(entity);
		
		ReceitaAssert.assertEntity(entity);
	}
}
