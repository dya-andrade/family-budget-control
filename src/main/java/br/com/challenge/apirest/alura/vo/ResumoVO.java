package br.com.challenge.apirest.alura.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "totalReceitas", "totalDespesas", "saldoFinal", "totalPorCategoria" })
public class ResumoVO extends RepresentationModel<ResumoVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("valor_total_receitas")
	private Double totalReceitas;
	
	@JsonProperty("valor_total_despesas")
	private Double totalDespesas;
	
	@JsonProperty("saldo_final")
	private Double saldoFinal;
	
	@JsonProperty("valor_total_por_categoria")
	private List<CategoriaVO> totalPorCategoria;
	
	public ResumoVO() {}

	public Double getTotalReceitas() {
		return totalReceitas;
	}

	public void setTotalReceitas(Double totalReceitas) {
		this.totalReceitas = totalReceitas;
	}

	public Double getTotalDespesas() {
		return totalDespesas;
	}

	public void setTotalDespesas(Double totalDespesas) {
		this.totalDespesas = totalDespesas;
	}

	public Double getSaldoFinal() {
		return saldoFinal;
	}

	public void setSaldoFinal(Double saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

	public List<CategoriaVO> getTotalPorCategoria() {
		return totalPorCategoria;
	}

	public void setTotalPorCategoria(List<CategoriaVO> totalPorCategoria) {
		this.totalPorCategoria = totalPorCategoria;
	}

	@Override
	public int hashCode() {
		return Objects.hash(saldoFinal, totalDespesas, totalPorCategoria, totalReceitas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResumoVO other = (ResumoVO) obj;
		return Objects.equals(saldoFinal, other.saldoFinal) && Objects.equals(totalDespesas, other.totalDespesas)
				&& Objects.equals(totalPorCategoria, other.totalPorCategoria)
				&& Objects.equals(totalReceitas, other.totalReceitas);
	}
}
