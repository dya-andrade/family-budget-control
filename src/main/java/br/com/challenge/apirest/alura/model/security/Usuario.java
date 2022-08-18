package br.com.challenge.apirest.alura.model.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Column(unique = true) // login
	private String email;

	private String senha;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_perfis_acesso", joinColumns = {
	@JoinColumn(name = "id_usuario") }, inverseJoinColumns = { @JoinColumn(name = "id_perfil_acesso") })
	private List<PerfilAcesso> perfilsAcesso;

	public List<String> getRoles() { // quebra de conversão, por conta do UserDetails
		List<String> roles = new ArrayList<>();

		for (PerfilAcesso perfilAcesso : perfilsAcesso) {
			roles.add(perfilAcesso.getRole());
		}

		return roles;
	}

	public Usuario() {
	} // JPA precisa de um construtor padrão
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public List<PerfilAcesso> getPerfilsAcesso() {
		return perfilsAcesso;
	}

	public void setPerfilsAcesso(List<PerfilAcesso> perfilsAcesso) {
		this.perfilsAcesso = perfilsAcesso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, nome, perfilsAcesso, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& Objects.equals(perfilsAcesso, other.perfilsAcesso) && Objects.equals(senha, other.senha);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.perfilsAcesso;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
