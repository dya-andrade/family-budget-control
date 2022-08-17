package br.com.challenge.apirest.alura.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.challenge.apirest.alura.model.Categoria;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@JsonPropertyOrder({ "descricao", "categoria", "data", "valor" })
@JsonFormat(locale = "pt_br", timezone = "UTC") 
public class DespesaVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
    @NotEmpty()
	private String descricao;

	@NotNull
    @PastOrPresent()
    @JsonFormat(pattern = "dd-MM-yyyy") 
	private LocalDate data;
    
    @NotNull
	private Double valor;
	
	private Categoria categoria;

	public DespesaVO() {
	}

	@JsonCreator
	public DespesaVO(@JsonProperty(value = "descricao", required = true) String descricao,
			@JsonProperty(value = "data", required = true) LocalDate data,
			@JsonProperty(value = "valor", required = true) Double valor,
			@JsonProperty(value = "categoria", required = false) Categoria categoria) {

		this.descricao = descricao;
		this.data = data;
		this.valor = valor;
		
		if(categoria == null)
			this.categoria = Categoria.OUTRAS;
		else 
			this.categoria = categoria;		
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria, data, descricao, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DespesaVO other = (DespesaVO) obj;
		return categoria == other.categoria && Objects.equals(data, other.data)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(valor, other.valor);
	}
}
