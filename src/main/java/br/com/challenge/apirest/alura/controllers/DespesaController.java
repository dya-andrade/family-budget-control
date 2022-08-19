package br.com.challenge.apirest.alura.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.apirest.alura.model.Despesa;
import br.com.challenge.apirest.alura.services.DespesaService;
import br.com.challenge.apirest.alura.vo.DespesaVO;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/budget-control/despesas")
@CrossOrigin // acesso livre ou defina um ip ou host
@Tag(name = "Despesas", description = "Alimentação, transporte, passeio e etc.")
public class DespesaController extends MovimentacaoController<Despesa, DespesaVO, DespesaService> {

	
}
