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

import br.com.challenge.apirest.alura.model.Despesa;
import br.com.challenge.apirest.alura.repositories.DespesaRepository;
import br.com.challenge.apirest.alura.services.DespesaService;
import br.com.challenge.apirest.alura.unitests.mockito.mocks.DespesaMock;
import br.com.challenge.apirest.alura.vo.DespesaVO;

@TestInstance(Lifecycle.PER_CLASS) //  cria apenas uma instância da classe de teste e reutilizá-la entre os testes.
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class) 
public class DespesaServiceTest {
	
	private DespesaMock despesaMock;
	
	@InjectMocks
	private DespesaService service;
	
	@Mock
	private DespesaRepository repository;
	
	@BeforeEach //executado antes de cada método 
	void setUpMocks() throws Exception {
		despesaMock = new DespesaMock();
		MockitoAnnotations.openMocks(this); // inicializa campos anotados com anotações Mockito
	}
	
	private Despesa findEntity(Integer id) {
		Despesa entity = despesaMock.mockEntity(id);

		//define um valor de retorno 
		when(repository.findById(id)).thenReturn(Optional.of(entity));

		return entity;
	}

	private DespesaVO persistEntity(Despesa entity) {
		Despesa persisted = entity;

		//define um valor de retorno 
		when(repository.save(entity)).thenReturn(persisted);
		
		DespesaVO vo = despesaMock.mockVO(entity.getId());

		return vo;
	}
}
