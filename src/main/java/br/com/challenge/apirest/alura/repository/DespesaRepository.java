package br.com.challenge.apirest.alura.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.challenge.apirest.alura.data.vo.v1.CategoriaVO;
import br.com.challenge.apirest.alura.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	@Query("SELECT d FROM Despesa d WHERE d.descricao = :descricao AND MONTH(d.data) = :mes") 
	Despesa findByDescricaoAndMes(String descricao, int mes);
	
	@Query("SELECT d FROM Despesa d WHERE d.descricao LIKE LOWER(CONCAT ('%',:descricao,'%'))")
	Page<Despesa> findByDescricao(String descricao, Pageable pageable); // LOWER - converte para minúsculo

	@Query("SELECT d FROM Despesa d WHERE YEAR(d.data) = :ano AND MONTH(d.data) = :mes")
	List<Despesa> findByMonth(Integer ano, Integer mes);

	@Query("SELECT SUM(d.valor) FROM Despesa d WHERE YEAR(d.data) = :ano AND MONTH(d.data) = :mes")
	Double findTotalAmountByMonth(Integer ano, Integer mes);
	
	@Query("SELECT NEW br.com.challenge.apirest.alura.data.vo.v1.CategoriaVO(d.categoria, SUM(d.valor)) FROM Despesa d WHERE YEAR(d.data) = :ano AND MONTH(d.data) = :mes GROUP BY d.categoria")
	List<CategoriaVO> findTotalAmountByCategoria(Integer ano, Integer mes);
}
