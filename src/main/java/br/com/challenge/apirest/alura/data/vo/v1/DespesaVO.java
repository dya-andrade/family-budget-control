package br.com.challenge.apirest.alura.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.challenge.apirest.alura.model.Categoria;

@JsonPropertyOrder({ "descricao", "categoria", "data", "valor" })
public class DespesaVO extends MovimentacaoVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Categoria categoria;

	public DespesaVO() {}

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
		DespesaVO other = (DespesaVO) obj;
		return categoria == other.categoria;
	}
}
