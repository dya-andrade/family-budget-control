package br.com.challenge.apirest.alura.unitests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.challenge.apirest.alura.model.Categoria;
import br.com.challenge.apirest.alura.model.Despesa;
import br.com.challenge.apirest.alura.repositories.DespesaRepository;
import br.com.challenge.apirest.alura.services.DespesaService;
import br.com.challenge.apirest.alura.unitests.mockito.mocks.MockDespesa;
import br.com.challenge.apirest.alura.vo.DespesaVO;

@TestInstance(Lifecycle.PER_CLASS) //  cria apenas uma instância da classe de teste e reutilizá-la entre os testes.
@ExtendWith(MockitoExtension.class)
public class DespesaServiceTest {
	
	private MockDespesa mockDespesa;
	
	@InjectMocks
	private DespesaService service;
	
	@Mock
	private DespesaRepository repository;
	
	@BeforeEach //executado antes de cada método 
	void setUpMocks() throws Exception {
		mockDespesa = new MockDespesa();
		MockitoAnnotations.openMocks(this); // inicializa campos anotados com anotações Mockito
	}
	
	private Despesa findEntity(Integer id) {
		Despesa entity = mockDespesa.mockEntity(id);

		//define um valor de retorno 
		when(repository.findById(id)).thenReturn(Optional.of(entity));

		return entity;
	}

	private DespesaVO persistEntity(Despesa entity) {
		Despesa persisted = entity;

		//define um valor de retorno 
		when(repository.save(entity)).thenReturn(persisted);
		
		DespesaVO vo = mockDespesa.mockVO(entity.getId());

		return vo;
	}
	
	private void assertResult(DespesaVO result) {
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getDescricao());
		assertNotNull(result.getData());
		assertNotNull(result.getValor());
		assertNotNull(result.getCategoria());
		
		int number = result.getKey();
		
		assertEquals(number, result.getKey());
		assertEquals("Descricao Test " + number, result.getDescricao());
		assertEquals(LocalDate.of(number, number, number), result.getData());
		assertEquals(0.0 + number, result.getValor());
		assertEquals(Categoria.categoriaPorOrdinal(number), result.getCategoria());
	}

	private void assertExceptionIsNull(Exception exception) {
		String exceptedMessage = "Não é permitido persistir um objeto nulo!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(exceptedMessage));
	}

}
