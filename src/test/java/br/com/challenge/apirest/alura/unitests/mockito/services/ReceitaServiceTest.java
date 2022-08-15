package br.com.challenge.apirest.alura.unitests.mockito.services;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.challenge.apirest.alura.model.Receita;
import br.com.challenge.apirest.alura.repositories.ReceitaRepository;
import br.com.challenge.apirest.alura.services.ReceitaService;
import br.com.challenge.apirest.alura.unitests.mockito.mocks.ReceitaMock;
import br.com.challenge.apirest.alura.vo.ReceitaVO;

@TestInstance(Lifecycle.PER_CLASS) //  cria apenas uma instância da classe de teste e reutilizá-la entre os testes.
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class) 
public class ReceitaServiceTest {
	
	private ReceitaMock receitaMock;
		
	@InjectMocks //cria uma instância da classe e injeta os mocks
	private ReceitaService service;
	
	@Mock //cria uma simulação
	private ReceitaRepository repository;
	
	@BeforeEach //executado antes de cada método 
	void setUpMocks() throws Exception {
		receitaMock = new ReceitaMock();
		MockitoAnnotations.openMocks(this); // inicializa campos anotados com anotações Mockito
	}
	
	private Receita findEntity(Integer id) {
		Receita entity = receitaMock.mockEntity(id);

		//define um valor de retorno 
		when(repository.findById(id)).thenReturn(Optional.of(entity));

		return entity;
	}

	private ReceitaVO persistEntity(Receita entity) {
		Receita persisted = entity;

		//define um valor de retorno 
		when(repository.save(entity)).thenReturn(persisted);
		
		ReceitaVO vo = receitaMock.mockVO(entity.getId());

		return vo;
	}
}
