package br.com.challenge.apirest.alura.model;

import java.io.Serializable;

import br.com.challenge.apirest.alura.data.vo.v1.ReceitaVO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "receitas")
public class Receita extends Movimentacao<Receita, ReceitaVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Receita() {}

	@Override
	public Receita updateVOToEntity(ReceitaVO vo) {
		super.setDescricao(vo.getDescricao());
		super.setData(vo.getData());
		super.setValor(vo.getValor());

		return this;
	}
}
