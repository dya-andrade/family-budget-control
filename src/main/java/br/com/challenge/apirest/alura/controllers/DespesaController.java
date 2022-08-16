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

import br.com.challenge.apirest.alura.services.CacheService;
import br.com.challenge.apirest.alura.services.DespesaService;
import br.com.challenge.apirest.alura.util.MediaType;
import br.com.challenge.apirest.alura.vo.DespesaVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/budget-control/despesas")
@CrossOrigin // acesso livre ou defina um ip ou host
@Tag(name = "Despesas", description = "Alimentação, transporte, passeio e etc.")
public class DespesaController {

	@Autowired
	private DespesaService service;
	
	@Autowired
	private CacheService cache;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Criar Despesa", description = "Criar uma nova despesa em JSON.", tags = {
			"Despesas" }, responses = {
					@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = DespesaVO.class))),

					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public DespesaVO create(@RequestBody @Valid DespesaVO despesaVO) {
		cache.clearAllCachesDespesa();
		return service.create(despesaVO);
	}

	@Cacheable()
	@GetMapping(produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Listar Despesas", description = "Listar todas as despesas em JSON.", tags = {
			"Despesas" }, responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DespesaVO.class))) }),

					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public List<DespesaVO> findAll(@RequestParam(value = "descricao", defaultValue = "") String descricao) {
		if (descricao.isBlank())
			return service.findAll();
		else
			return service.findByDescricao(descricao);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Buscar Despesa", description = "Buscar uma despesa pelo ID.", tags = {
			"Despesas" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = DespesaVO.class))),

					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public DespesaVO findById(@PathVariable(value = "id") Integer id) {
		return service.findById(id);
	}

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Atualizar Despesa", description = "Atualizar uma despesa em JSON.", tags = {
			"Despesas" }, responses = {
					@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = DespesaVO.class))),

					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public DespesaVO update(@PathVariable(value = "id") Integer id, @RequestBody @Valid DespesaVO despesaVO) {
		cache.clearAllCachesDespesa();
		return service.update(id, despesaVO);
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletar Despesa", description = "Deletar uma despesa pelo ID", tags = {
			"Despesas" }, responses = {
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),

					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) {
		cache.clearAllCachesDespesa();
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Cacheable(value = "listaDespesasMes")
	@GetMapping(value = "/{ano}/{mes}", produces = { MediaType.APPLICATION_JSON })
	@Operation(summary = "Listar Despesas Por Mês", description = "Listar todas as despesas por mês em JSON.", tags = {
	"Despesas" }, responses = { @ApiResponse(description = "Success", responseCode = "200", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DespesaVO.class))) }),

			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public List<DespesaVO> findByMonth(@PathVariable(value = "ano") Integer ano,
			@PathVariable(value = "mes") Integer mes) {
		return service.findByMonth(ano, mes);
	}
}
