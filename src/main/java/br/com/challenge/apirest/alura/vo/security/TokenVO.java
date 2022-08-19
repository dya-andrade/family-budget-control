package br.com.challenge.apirest.alura.vo.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	private Boolean autenticado;

	private Date criado;

	private Date expiracao;

	@JsonProperty(value = "access_token")
	private String accessToken;
	
	@JsonProperty(value = "refresh_token")
	private String refreshToken;

	public TokenVO() {
	}

	public TokenVO(String email, Boolean autenticado, Date criado, Date expiracao, String accessToken,
			String refreshToken) {
		this.email = email;
		this.autenticado = autenticado;
		this.criado = criado;
		this.expiracao = expiracao;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getAutenticado() {
		return autenticado;
	}

	public void setAutenticado(Boolean autenticado) {
		this.autenticado = autenticado;
	}

	public Date getCriado() {
		return criado;
	}

	public void setCriado(Date criado) {
		this.criado = criado;
	}

	public Date getExpiracao() {
		return expiracao;
	}

	public void setExpiracao(Date expiracao) {
		this.expiracao = expiracao;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessToken, autenticado, criado, email, expiracao, refreshToken);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenVO other = (TokenVO) obj;
		return Objects.equals(accessToken, other.accessToken) && Objects.equals(autenticado, other.autenticado)
				&& Objects.equals(criado, other.criado) && Objects.equals(email, other.email)
				&& Objects.equals(expiracao, other.expiracao) && Objects.equals(refreshToken, other.refreshToken);
	}
}
