package br.com.challenge.apirest.alura.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.apirest.alura.data.vo.v1.ReceitaVO;
import br.com.challenge.apirest.alura.model.Receita;
import br.com.challenge.apirest.alura.service.ReceitaService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/budget-control/receitas")
@CrossOrigin // acesso livre ou defina um ip ou host
@Tag(name = "Receitas", description = "Salário, extras e etc.")
public class ReceitaController extends MovimentacaoController<Receita, ReceitaVO, ReceitaService> {


}
