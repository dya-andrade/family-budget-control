package br.com.challenge.apirest.alura.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "receitas")
public class Receita extends Movimentacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Receita() {}
	
}
