package br.com.challenge.apirest.alura.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.challenge.apirest.alura.model.Despesa;
import br.com.challenge.apirest.alura.vo.CategoriaVO;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {

	@Query("SELECT d FROM Despesa d WHERE d.descricao = :descricao AND MONTH(d.data) = :mes") 
	Despesa findByDescricaoAndMes(String descricao, int mes);
	
	@Query("SELECT d FROM Despesa d WHERE d.descricao LIKE LOWER(CONCAT ('%',:descricao,'%'))")
	List<Despesa> findByDescricao(String descricao); // LOWER - converte para minúsculo

	@Query("SELECT d FROM Despesa d WHERE YEAR(d.data) = :ano AND MONTH(d.data) = :mes")
	List<Despesa> findByMonth(Integer ano, Integer mes);

	@Query("SELECT SUM(d.valor) FROM Despesa d WHERE YEAR(d.data) = :ano AND MONTH(d.data) = :mes")
	Double findTotalAmountByMonth(Integer ano, Integer mes);
	
	@Query("SELECT NEW br.com.challenge.apirest.alura.vo.CategoriaVO(d.categoria, SUM(d.valor)) FROM Despesa d WHERE YEAR(d.data) = :ano AND MONTH(d.data) = :mes GROUP BY d.categoria")
	List<CategoriaVO> findTotalAmountByCategoria(Integer ano, Integer mes);
}
