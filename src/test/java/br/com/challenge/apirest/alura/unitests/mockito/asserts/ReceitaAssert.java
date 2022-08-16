package br.com.challenge.apirest.alura.unitests.mockito.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import br.com.challenge.apirest.alura.model.Receita;
import br.com.challenge.apirest.alura.vo.ReceitaVO;

public class ReceitaAssert {
	
	private int number;
	
	public ReceitaAssert(int number) {
		this.number = number;
	}

	public final void assertVO(ReceitaVO vo) {
		assertNotNull(vo);
		assertNotNull(vo.getDescricao());
		assertNotNull(vo.getData());
		assertNotNull(vo.getValor());
						
		assertEquals("Descricao Test " + number, vo.getDescricao());
		assertEquals(LocalDate.of(2000 + number, number, number), vo.getData());
		assertEquals(0.0 + number, vo.getValor());
	}
	
	public final void assertEntity(Receita entity) {
		assertNotNull(entity);
		assertNotNull(entity.getId());
		assertNotNull(entity.getDescricao());
		assertNotNull(entity.getData());
		assertNotNull(entity.getValor());
				
		assertTrue(entity.getId() > 0);
		
		assertEquals("Descricao Test " + number, entity.getDescricao());
		assertEquals(LocalDate.of(2000 + number, number, number), entity.getData());
		assertEquals(0.0 + number, entity.getValor());
	}

	public int getNumber() {
		return number;
	}
}
