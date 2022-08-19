package br.com.challenge.apirest.alura.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.challenge.apirest.alura.model.Movimentacao;
import br.com.challenge.apirest.alura.services.MovimentacaoService;
import br.com.challenge.apirest.alura.services.infra.CacheService;
import br.com.challenge.apirest.alura.util.MediaType;
import br.com.challenge.apirest.alura.vo.MovimentacaoVO;
import jakarta.validation.Valid;

public abstract class MovimentacaoController<E extends Movimentacao, V extends MovimentacaoVO, S extends MovimentacaoService<E, V, ?>> {

	@Autowired
	protected S service;

	@Autowired
	private CacheService cache;	

	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public V create(@RequestBody @Valid V vo) {
		cache.evictAllCaches();
		return service.create(vo);
	}

	@Cacheable(value = "findAll")
	@GetMapping(produces = { MediaType.APPLICATION_JSON })
	public List<V> findAll(@RequestParam(value = "descricao", defaultValue = "") String descricao) {
		if (descricao.isBlank())
			return service.findAll();
		else
			return service.findByDescricao(descricao);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	public V findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public V update(@PathVariable(value = "id") Long id, @RequestBody @Valid V vo) {
		cache.evictAllCaches();
		return service.update(id, vo);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		cache.evictAllCaches();
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Cacheable(value = "findByMonth")
	@GetMapping(value = "/{ano}/{mes}", produces = { MediaType.APPLICATION_JSON })
	public List<V> findByMonth(@PathVariable(value = "ano") Integer ano,
			@PathVariable(value = "mes") Integer mes) {
		return service.findByMonth(ano, mes);
	}
}
