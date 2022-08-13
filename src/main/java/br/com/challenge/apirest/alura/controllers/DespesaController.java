package br.com.challenge.apirest.alura.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.apirest.alura.services.DespesaService;
import br.com.challenge.apirest.alura.util.MediaType;
import br.com.challenge.apirest.alura.vo.DespesaVO;

@RestController
@RequestMapping("/budget-control/despesas")
public class DespesaController {

	@Autowired
	private DespesaService service;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public DespesaVO create(@RequestBody DespesaVO despesaVO) {
		return service.create(despesaVO);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON })
	public List<DespesaVO> findAll(@RequestParam(value = "descricao", defaultValue = "") String descricao) {
		if(descricao.isBlank()) 
			return service.findAll();
		else 
			return service.findByDescricao(descricao);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	public DespesaVO findById(@PathVariable(value = "id") Integer id) {
		return service.findById(id);
	}

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public DespesaVO update(@PathVariable(value = "id") Integer id, @RequestBody DespesaVO despesaVO) {
		return service.update(id, despesaVO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/{ano}/{mes}", produces = { MediaType.APPLICATION_JSON })
	public List<DespesaVO> findByMonth(@PathVariable(value = "ano") Integer ano, @PathVariable(value = "mes") Integer mes) {
		return service.findByMonth(ano, mes);
	}
}
