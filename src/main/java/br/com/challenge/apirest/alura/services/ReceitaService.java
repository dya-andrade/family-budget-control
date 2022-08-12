package br.com.challenge.apirest.alura.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.apirest.alura.exceptions.AlreadyRegisteredException;
import br.com.challenge.apirest.alura.exceptions.RequiredObjectIsNullException;
import br.com.challenge.apirest.alura.mapper.DozerMapper;
import br.com.challenge.apirest.alura.model.Receita;
import br.com.challenge.apirest.alura.repositories.ReceitaRepository;
import br.com.challenge.apirest.alura.vo.ReceitaVO;

@Service
public class ReceitaService {
	
	private Logger logger = Logger.getLogger(ReceitaService.class.getName());

	@Autowired
	private ReceitaRepository repository;

	public ReceitaVO create(ReceitaVO receitaVO) {
		
		if(receitaVO == null) throw new RequiredObjectIsNullException();

		int mesDaReceita = receitaVO.getData().getMonth().getValue();
		
		Receita receitaDuplicada = repository.findByDescricaoAndMes(receitaVO.getDescricao(), mesDaReceita);
		
		if(receitaDuplicada != null) throw new AlreadyRegisteredException();
		
		logger.info("Criando uma receita!");
		
		var entity = DozerMapper.parseObject(receitaVO, Receita.class);
		
		var vo = DozerMapper.parseObject(repository.save(entity), ReceitaVO.class);
		
		logger.info("Receita criada!");
		
		return vo;
	}

}
