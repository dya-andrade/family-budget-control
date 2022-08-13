package br.com.challenge.apirest.alura.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.apirest.alura.exceptions.AlreadyRegisteredException;
import br.com.challenge.apirest.alura.exceptions.RequiredObjectIsNullException;
import br.com.challenge.apirest.alura.exceptions.ResourceNotFoundException;
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

	public List<ReceitaVO> findAll() {

		logger.info("Buscando todas as receitas!");

		var vos = DozerMapper.parseListObjects(repository.findAll(), ReceitaVO.class);
		
		return vos;
	}

	public ReceitaVO findById(Integer id) {
		
		logger.info("Buscando uma receita!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhuma receita encontrada com este ID!"));
		
		var vo = DozerMapper.parseObject(entity, ReceitaVO.class);
		
		return vo;
	}

	public ReceitaVO update(Integer id, ReceitaVO receitaVO) {
		
		if(receitaVO == null) throw new RequiredObjectIsNullException();

		logger.info("Editando uma receita!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhuma receita encontrada com este ID!"));

		entity.setDescricao(receitaVO.getDescricao());
		entity.setData(receitaVO.getData());
		entity.setValor(receitaVO.getValor());
		
		var vo = DozerMapper.parseObject(repository.save(entity), ReceitaVO.class);
		
		logger.info("Receita editada!");
		
		return vo;
	}

	public void delete(Integer id) {
		
		logger.info("Deletando uma receita!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhuma receita encontrada com este ID!"));

		repository.delete(entity);
		
		logger.info("Receita deletada!");
	}

	public List<ReceitaVO> findByDescricao(String descricao) {
		
		logger.info("Buscando receitas pela descrição!");
				
		var vos = DozerMapper.parseListObjects(repository.findByDescricao(descricao), ReceitaVO.class);

		return vos;
	}
}
