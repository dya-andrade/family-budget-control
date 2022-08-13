package br.com.challenge.apirest.alura.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.challenge.apirest.alura.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {

	@Query("SELECT d FROM Despesa d WHERE d.descricao = :descricao AND MONTH(d.data) = :mes") 
	Despesa findByDescricaoAndMes(String descricao, int mes);
	
	@Query("SELECT d FROM Despesa d WHERE d.descricao LIKE LOWER(CONCAT ('%',:descricao,'%'))")
	List<Despesa> findByDescricao(String descricao); // LOWER - converte para minúsculo
}
