package br.com.challenge.apirest.alura.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

public abstract class MovimentacaoController<E extends Movimentacao, V extends MovimentacaoVO, S extends MovimentacaoService<E, V, ?>> {

	@Autowired
	protected S service;

	@Autowired
	private CacheService cache;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Cria uma movimentação com JSON.", description = "Cria uma movimentação com JSON via body.", responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = MovimentacaoVO.class))),

			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public V create(@RequestBody @Valid V vo) {
		cache.evictAllCaches();
		return service.create(vo);
	}

	@Cacheable(value = "findAll")
	@GetMapping(produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Busca todas movimentações, com param opcional descrição.", description = "Busca todas movimentações, com param opcional descrição e devolve uma lista JSON VO.", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MovimentacaoVO.class))) }),

			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public ResponseEntity<Page<V>> findAll(@RequestParam(value = "descricao", defaultValue = "") String descricao,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "5") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "descricao"));

		if (descricao.isBlank())
			return ResponseEntity.ok(service.findAll(pageable));
		else
			return ResponseEntity.ok(service.findByDescricao(descricao, pageable));
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Busca a movimentação pelo ID.", description = "Busca a movimentação pelo ID e devolve um JSON VO.", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = MovimentacaoVO.class))),

			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public V findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Atualiza uma movimentação pelo ID.", description = "Atualiza uma movimentação pelo ID e devolve um JSON VO.", responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = MovimentacaoVO.class))),

			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public V update(@PathVariable(value = "id") Long id, @RequestBody @Valid V vo) {
		cache.evictAllCaches();
		return service.update(id, vo);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deleta uma movimentação pelo ID.", description = "Deleta uma movimentação pelo ID e devolve status code 200.", responses = {
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),

			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		cache.evictAllCaches();
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Cacheable(value = "findByMonth")
	@GetMapping(value = "/{ano}/{mes}", produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Busca todas movimentações pelo path mês e path ano.", description = "Busca todas movimentações pelo path mês e path ano; e devolve uma lista de JSON VO.", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MovimentacaoVO.class))) }),

			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public List<V> findByMonth(@PathVariable(value = "ano") Integer ano, @PathVariable(value = "mes") Integer mes) {
		return service.findByMonth(ano, mes);
	}
}
