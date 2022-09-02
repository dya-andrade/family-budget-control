package br.com.challenge.apirest.alura.data.vo.v1;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@JsonPropertyOrder({ "descricao", "data", "valor" })
@JsonFormat(locale = "pt_br", timezone = "UTC") @NotNull
public abstract class MovimentacaoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty()
	private String descricao;

	@PastOrPresent()
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate data;

	private Double valor;

	public MovimentacaoVO() {}

	@JsonCreator
	public MovimentacaoVO(@JsonProperty(value = "descricao", required = true) String descricao,
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
		MovimentacaoVO other = (MovimentacaoVO) obj;
		return Objects.equals(data, other.data) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(valor, other.valor);
	}
}
