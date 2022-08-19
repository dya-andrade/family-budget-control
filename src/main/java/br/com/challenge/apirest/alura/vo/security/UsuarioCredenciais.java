package br.com.challenge.apirest.alura.vo.security;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

@NotBlank
public class UsuarioCredenciais implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String senha;
	
	public UsuarioCredenciais(){}

	@JsonCreator
	public UsuarioCredenciais(@JsonProperty(value = "email", required = true) String email, 
			@JsonProperty(value = "senha", required = true) String senha) {
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioCredenciais other = (UsuarioCredenciais) obj;
		return Objects.equals(email, other.email) && Objects.equals(senha, other.senha);
	}
}
