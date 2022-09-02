package br.com.challenge.apirest.alura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.apirest.alura.data.vo.v1.ResumoVO;
import br.com.challenge.apirest.alura.service.ResumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/budget-control/resumo")
@CrossOrigin // acesso livre ou defina um ip ou host
@Tag(name = "Resumo do Mês", description = "Total de receitas, despesas, saldo final e etc.")
public class ResumoController {

	@Autowired
	private ResumoService service;

	@Cacheable(value = "resumoMes")
	@GetMapping(value = "/{ano}/{mes}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Resumo do mês com path mes e ano; devolve JSON VO.", description = "Resumo do mês com path mês e ano; devolve JSON VO, com valores totais do mês.", tags = {
			"Resumo do Mês" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ResumoVO.class))),

					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public ResumoVO monthSummary(@PathVariable(value = "ano") Integer ano, @PathVariable(value = "mes") Integer mes) {
		return service.monthSummary(ano, mes);
	}

	@Cacheable(value = "resumoMesExcel")
	@GetMapping("/downloadExcel/{ano}/{mes}")
	@Operation(summary = "Resumo do mês com path mês e ano; devolve XSLX.", description = "Resumo do mês com path mês e ano; devolve XSLX, com valores totais do mês.", tags = {
			"Resumo do Mês" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Resource.class))),

					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content), })
	public ResponseEntity<Resource> download(@PathVariable(value = "ano") Integer ano,
			@PathVariable(value = "mes") Integer mes, HttpServletRequest request) {

		ResumoVO resumoVO = service.monthSummary(ano, mes);
		Resource resource = service.loadExcelAsResource(resumoVO);

		return ResponseEntity.ok()
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				// indica se o conteúdo deve ser exibido como uma página da web
				.body(resource);
	}

}
