package br.com.challenge.apirest.alura.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.print.attribute.standard.MediaSize.ISO;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.challenge.apirest.alura.vo.MovimentacaoVO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Movimentacao<E, V extends MovimentacaoVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String descricao;
	
	@Column(nullable = false)
	private Double valor;
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate data;
	
	public Movimentacao() {}
	
	public abstract E updateVOToEntity(V vo);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, descricao, id, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movimentacao other = (Movimentacao) obj;
		return Objects.equals(data, other.data) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(id, other.id) && Objects.equals(valor, other.valor);
	}
}
