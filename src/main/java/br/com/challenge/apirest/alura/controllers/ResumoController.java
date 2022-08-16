package br.com.challenge.apirest.alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.apirest.alura.services.ResumoService;
import org.springframework.http.MediaType;
import br.com.challenge.apirest.alura.vo.ResumoVO;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/budget-control/resumo")
public class ResumoController {

	@Autowired
	private ResumoService service;

	@Cacheable(value = "resumoMes")
	@GetMapping(value = "/{ano}/{mes}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResumoVO monthSummary(@PathVariable(value = "ano") Integer ano, @PathVariable(value = "mes") Integer mes) {
		return service.monthSummary(ano, mes);
	}

	@GetMapping("/downloadExcel/{ano}/{mes}")
	public ResponseEntity<Resource> download(@PathVariable(value = "ano") Integer ano,
			@PathVariable(value = "mes") Integer mes, HttpServletRequest request) {

		ResumoVO resumoVO = service.monthSummary(ano, mes);
		Resource resource = service.loadExcelAsResource(resumoVO);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				// indica se o conteúdo deve ser exibido como uma página da web
				.body(resource);
	}

}
