package br.com.challenge.apirest.alura.data.vo.v1;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.challenge.apirest.alura.model.Categoria;

@JsonPropertyOrder({ "categoria", "total" })
public class CategoriaVO {
	
	private Categoria categoria;
	
	private Double total;
	
	public CategoriaVO() {}

	public CategoriaVO(Categoria categoria, Double total) {
		this.categoria = categoria;
		this.total = total;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaVO other = (CategoriaVO) obj;
		return categoria == other.categoria && Objects.equals(total, other.total);
	}
}
