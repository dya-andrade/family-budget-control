package br.com.challenge.apirest.alura.unitests.mockito.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.challenge.apirest.alura.model.Categoria;
import br.com.challenge.apirest.alura.model.Despesa;
import br.com.challenge.apirest.alura.vo.DespesaVO;

public class DespesaMock {

	private Despesa despesa;

	private DespesaVO despesaVO;

	public Despesa mockEntity() {
		return mockEntity(0);
	}

	public DespesaVO mockVO() {
		return mockVO(0);
	}

	public Despesa mockEntity(Integer number) {
		despesa = new Despesa();

		despesa.setId(number);
		despesa.setDescricao("Descricao Test " + number);
		despesa.setData(LocalDate.of(2000 + number, number, number));
		despesa.setValor(0.0 + number);
		despesa.setCategoria(Categoria.categoriaPorOrdinal(number));

		return despesa;
	}

	public DespesaVO mockVO(Integer number) {
		despesaVO = new DespesaVO();

		despesaVO.setDescricao("Descricao Test " + number);
		despesaVO.setData(LocalDate.of(2000 + number, number, number));
		despesaVO.setValor(0.0 + number);
		despesaVO.setCategoria(Categoria.categoriaPorOrdinal(number));

		return despesaVO;
	}

	public List<Despesa> mockEntityList() {

		List<Despesa> despesas = new ArrayList<Despesa>();

		for (int i = 0; i < 10; i++) {
			despesas.add(mockEntity(i));
		}

		return despesas;
	}

	public List<DespesaVO> mockVOList() {
		List<DespesaVO> vos = new ArrayList<DespesaVO>();

		for (int i = 0; i < 10; i++) {
			vos.add(mockVO(i));
		}

		return vos;
	}

}
