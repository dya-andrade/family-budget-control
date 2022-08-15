package br.com.challenge.apirest.alura.unitests.mockito.mocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.challenge.apirest.alura.model.Receita;
import br.com.challenge.apirest.alura.vo.ReceitaVO;

public class ReceitaMock {

	private Receita receita;

	private ReceitaVO receitaVO;
	
	public Receita mockEntity() {
		return mockEntity(0);
	}

	public ReceitaVO mockVO() {
		return mockVO(0);
	}

	public Receita mockEntity(Integer number) {
		receita = new Receita();

		receita.setId(number);
		receita.setDescricao("Descricao Test " + number);
		receita.setData(LocalDate.of(2000 + number, number, number));
		receita.setValor(0.0 + number);

		return receita;
	}

	public ReceitaVO mockVO(Integer number) {
		receitaVO = new ReceitaVO();
		
		receitaVO.setDescricao("Descricao Test " + number);
		receitaVO.setData(LocalDate.of(2000 + number, number, number));
		receitaVO.setValor(0.0 + number);

		return receitaVO;
	}

	public List<Receita> mockEntityList() {
		
		List<Receita> receitas = new ArrayList<Receita>();
		
		for (int i = 0; i < 10; i++) {
			receitas.add(mockEntity(i));
		}
		
		return receitas;
	}

	public List<ReceitaVO> mockVOList() {
		List<ReceitaVO> vos = new ArrayList<ReceitaVO>();
		
		for (int i = 0; i < 10; i++) {
			vos.add(mockVO(i));
		}
		
		return vos;
	}
}
