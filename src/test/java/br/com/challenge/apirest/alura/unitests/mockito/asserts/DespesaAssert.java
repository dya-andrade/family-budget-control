package br.com.challenge.apirest.alura.unitests.mockito.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import br.com.challenge.apirest.alura.model.Categoria;
import br.com.challenge.apirest.alura.model.Despesa;
import br.com.challenge.apirest.alura.vo.DespesaVO;

public class DespesaAssert {
	
	private static int number;
	
	public static void assertVO(DespesaVO vo) {
		assertNotNull(vo);
		assertNotNull(vo.getDescricao());
		assertNotNull(vo.getData());
		assertNotNull(vo.getValor());
		assertNotNull(vo.getCategoria());
				
		assertEquals("Descricao Test " + number, vo.getDescricao());
		assertEquals(LocalDate.of(2000 + number, number, number), vo.getData());
		assertEquals(0.0 + number, vo.getValor());
		assertEquals(Categoria.categoriaPorOrdinal(number), vo.getCategoria());
	}
	
	public static void assertEntity(Despesa entity) {
		assertNotNull(entity);
		assertNotNull(entity.getId());
		assertNotNull(entity.getDescricao());
		assertNotNull(entity.getData());
		assertNotNull(entity.getValor());
		assertNotNull(entity.getCategoria());
		
		assertTrue(entity.getId() > 0);
		
		assertEquals("Descricao Test " + number, entity.getDescricao());
		assertEquals(LocalDate.of(2000 + number, number, number), entity.getData());
		assertEquals(0.0 + number, entity.getValor());
		assertEquals(Categoria.categoriaPorOrdinal(number), entity.getCategoria());
	}
	
	public static int getNumber() {
		return number;
	}

	public static void setNumber(int number) {
		DespesaAssert.number = number;
	}
}
