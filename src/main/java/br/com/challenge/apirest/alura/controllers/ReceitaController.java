package br.com.challenge.apirest.alura.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import br.com.challenge.apirest.alura.services.ReceitaService;
import br.com.challenge.apirest.alura.util.MediaType;
import br.com.challenge.apirest.alura.vo.ReceitaVO;

@RestController
@RequestMapping("/budget-control/receitas")
public class ReceitaController {

	@Autowired
	private ReceitaService service;

	@CacheEvict(value = {"listaReceitas", "listaReceitasMes", "resumoMes"}, allEntries = true)
	@PostMapping(consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public ReceitaVO create(@RequestBody ReceitaVO receitaVO) {
		return service.create(receitaVO);
	}

	@Cacheable(value = "listaReceitas")
	@GetMapping(produces = { MediaType.APPLICATION_JSON })
	public List<ReceitaVO> findAll(@RequestParam(value = "descricao", defaultValue = "") String descricao) {
		if(descricao.isBlank()) 
			return service.findAll();
		else 
			return service.findByDescricao(descricao);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	public ReceitaVO findById(@PathVariable(value = "id") Integer id) {
		return service.findById(id);
	}

	@CacheEvict(value = {"listaReceitas", "listaReceitasMes", "resumoMes"}, allEntries = true)
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	public ReceitaVO update(@PathVariable(value = "id") Integer id, @RequestBody ReceitaVO receitaVO) {
		return service.update(id, receitaVO);
	}

	@CacheEvict(value = {"listaReceitas", "listaReceitasMes", "resumoMes"}, allEntries = true)
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Cacheable(value = "listaReceitasMes")
	@GetMapping(value = "/{ano}/{mes}", produces = { MediaType.APPLICATION_JSON })
	public List<ReceitaVO> findByMonth(@PathVariable(value = "ano") Integer ano, @PathVariable(value = "mes") Integer mes) {
		return service.findByMonth(ano, mes);
	}
}
