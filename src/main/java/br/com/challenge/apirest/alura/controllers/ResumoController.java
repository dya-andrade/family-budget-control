package br.com.challenge.apirest.alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.apirest.alura.services.ResumoService;
import br.com.challenge.apirest.alura.util.MediaType;
import br.com.challenge.apirest.alura.vo.ResumoVO;

@RestController
@RequestMapping("/budget-control/resumo")
public class ResumoController {
	
	@Autowired
	private ResumoService service;
	
	@Cacheable(value = "resumoMes")
	@GetMapping(value = "/{ano}/{mes}", produces = { MediaType.APPLICATION_JSON })
	public ResumoVO monthSummary(@PathVariable(value = "ano") Integer ano, @PathVariable(value = "mes") Integer mes) {
		return service.monthSummary(ano, mes);
	}

}
