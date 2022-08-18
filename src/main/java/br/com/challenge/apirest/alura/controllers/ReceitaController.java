package br.com.challenge.apirest.alura.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.apirest.alura.services.ReceitaService;
import br.com.challenge.apirest.alura.services.infra.CacheService;
import br.com.challenge.apirest.alura.util.MediaType;
import br.com.challenge.apirest.alura.vo.ReceitaVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/budget-control/receitas")
@CrossOrigin // acesso livre ou defina um ip ou host
@Tag(name = "Receitas", description = "Salário, extras e etc.")
public class ReceitaController {

	@Autowired
	private ReceitaService service;
	
	@Autowired
	private CacheService cache;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Criar Receita", description = "Criar uma nova receita em JSON.", tags = {
	"Receitas" }, responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = ReceitaVO.class))),

			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public ReceitaVO create(@RequestBody @Valid ReceitaVO receitaVO) {
		cache.clearAllCachesReceita();
		return service.create(receitaVO);
	}

	@Cacheable(value = "listaReceitas")
	@GetMapping(produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Listar Receitas", description = "Listar todas as receitas em JSON.", tags = {
	"Receitas" }, responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReceitaVO.class))) }),

			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public List<ReceitaVO> findAll(@RequestParam(value = "descricao", defaultValue = "") String descricao) {
		if(descricao.isBlank()) 
			return service.findAll();
		else 
			return service.findByDescricao(descricao);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Buscar Receita", description = "Buscar uma receita pelo ID.", tags = {
	"Receitas" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ReceitaVO.class))),

			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public ReceitaVO findById(@PathVariable(value = "id") Integer id) {
		return service.findById(id);
	}

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Atualizar Receita", description = "Atualizar uma receita em JSON.", tags = {
	"Receitas" }, responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = ReceitaVO.class))),

			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public ReceitaVO update(@PathVariable(value = "id") Integer id, @RequestBody @Valid ReceitaVO receitaVO) {
		cache.clearAllCachesReceita();
		return service.update(id, receitaVO);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletar Receita", description = "Deletar uma receita pelo ID", tags = {
	"Receitas" }, responses = {
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),

			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) {
		cache.clearAllCachesReceita();
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Cacheable(value = "listaReceitasMes")
	@GetMapping(value = "/{ano}/{mes}", produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Listar Receitas Por Mês", description = "Listar todas as receitas por mês em JSON.", tags = {
	"Receitas" }, responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReceitaVO.class))) }),

			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public List<ReceitaVO> findByMonth(@PathVariable(value = "ano") Integer ano, @PathVariable(value = "mes") Integer mes) {
		return service.findByMonth(ano, mes);
	}
}
