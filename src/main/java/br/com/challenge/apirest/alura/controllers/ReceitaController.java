package br.com.challenge.apirest.alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.apirest.alura.services.ReceitaService;
import br.com.challenge.apirest.alura.util.MediaType;
import br.com.challenge.apirest.alura.vo.ReceitaVO;


@RestController
@RequestMapping("/budget-control/receitas")
public class ReceitaController {

	@Autowired
	private ReceitaService service;
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON}, produces = { MediaType.APPLICATION_JSON})
	public ReceitaVO create(@RequestBody ReceitaVO receitaVO) {
		return service.create(receitaVO);
	}
}
