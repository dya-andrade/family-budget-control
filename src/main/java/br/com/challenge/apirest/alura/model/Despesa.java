package br.com.challenge.apirest.alura.model;

import java.io.Serializable;
import java.util.Objects;

import br.com.challenge.apirest.alura.vo.DespesaVO;
import br.com.challenge.apirest.alura.vo.MovimentacaoVO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity 
@Table(name = "despesas")
public class Despesa extends Movimentacao<Despesa, DespesaVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private Categoria categoria;
	
	public Despesa() {}
	
	@Override
	public Despesa updateVOToEntity(DespesaVO vo) {
		super.setDescricao(vo.getDescricao());
		super.setData(vo.getData());
		super.setValor(vo.getValor());
		this.setCategoria(vo.getCategoria());

		return this;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(categoria);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Despesa other = (Despesa) obj;
		return categoria == other.categoria;
	}
}
