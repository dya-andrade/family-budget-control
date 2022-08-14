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

	public static Categoria categoriaPorOrdinal(int posicao) {
		if (ALIMENTACAO.ordinal() == posicao)
			return ALIMENTACAO;
		
		else if (SAUDE.ordinal() == posicao)
			return SAUDE;
		
		else if (MORADIA.ordinal() == posicao)
			return MORADIA;
		
		else if (TRANSPORTE.ordinal() == posicao)
			return TRANSPORTE;
		
		else if (EDUCACAO.ordinal() == posicao)
			return EDUCACAO;
		
		else if (LAZER.ordinal() == posicao)
			return LAZER;
		
		else if (IMPREVISTOS.ordinal() == posicao)
			return IMPREVISTOS;
		
		else
			return OUTRAS;
	}
}
