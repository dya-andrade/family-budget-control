package br.com.challenge.apirest.alura.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Categoria {

	ALIMENTACAO("Alimentação"),

	SAUDE("Saúde"),

	MORADIA("Moradia"),

	TRANSPORTE("Transporte"),

	EDUCACAO("Educação"),

	LAZER("Lazer"),

	IMPREVISTOS("Imprevistos"),

	@JsonEnumDefaultValue
	OUTRAS("Outras");

	private String descricao;

	Categoria(String descricao) {
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}

	public static Categoria categoriaPorPosicao(int posicao) {
		Categoria[] categorias = Categoria.values();
		return categorias[posicao];
	}
}
