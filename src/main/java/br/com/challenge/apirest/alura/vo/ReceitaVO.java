package br.com.challenge.apirest.alura.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@JsonPropertyOrder({ "descricao", "data", "valor" })
public class ReceitaVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @NotNull @NotEmpty(message = "A descrição não pode ser vazia.")
	private String descricao;

    @NotNull @PastOrPresent(message = "Somente data no passado ou presente.")
	private LocalDate data;

    @NotNull
	private Double valor;

	public ReceitaVO() {
	}

	@JsonCreator
	public ReceitaVO(@JsonProperty(value = "descricao", required = true) String descricao,
			@JsonProperty(value = "data", required = true) LocalDate data,
			@JsonProperty(value = "valor", required = true) Double valor) {

		this.descricao = descricao;
		this.data = data;
		this.valor = valor;
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

	@Override
	public int hashCode() {
		return Objects.hash(data, descricao, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReceitaVO other = (ReceitaVO) obj;
		return Objects.equals(data, other.data) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(valor, other.valor);
	}
}
