package br.com.challenge.apirest.alura.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.apirest.alura.exceptions.AlreadyRegisteredException;
import br.com.challenge.apirest.alura.exceptions.RequiredObjectIsNullException;
import br.com.challenge.apirest.alura.exceptions.ResourceNotFoundException;
import br.com.challenge.apirest.alura.mapper.DozerMapper;
import br.com.challenge.apirest.alura.model.Despesa;
import br.com.challenge.apirest.alura.repositories.DespesaRepository;
import br.com.challenge.apirest.alura.vo.DespesaVO;

@Service
public class DespesaService {
	
	private Logger logger = Logger.getLogger(DespesaService.class.getName());

	@Autowired
	private DespesaRepository repository;

	public DespesaVO create(DespesaVO despesaVO) {
		
		if(despesaVO == null) throw new RequiredObjectIsNullException();

		int mesDaDespesa = despesaVO.getData().getMonth().getValue();
		
		Despesa despesaDuplicada = repository.findByDescricaoAndMes(despesaVO.getDescricao(), mesDaDespesa);
		
		if(despesaDuplicada != null) throw new AlreadyRegisteredException();
		
		logger.info("Criando uma despesa!");
		
		var entity = DozerMapper.parseObject(despesaVO, Despesa.class);
		
		var vo = DozerMapper.parseObject(repository.save(entity), DespesaVO.class);
		
		logger.info("Despesa criada!");
		
		return vo;
	}

	public List<DespesaVO> findAll() {

		logger.info("Buscando todas as despesas!");

		var vos = DozerMapper.parseListObjects(repository.findAll(), DespesaVO.class);
		
		return vos;
	}

	public DespesaVO findById(Integer id) {
		
		logger.info("Buscando uma despesa!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhuma despesa encontrada com este ID!"));
		
		var vo = DozerMapper.parseObject(entity, DespesaVO.class);
		
		return vo;
	}

	public DespesaVO update(Integer id, DespesaVO despesaVO) {
		
		if(despesaVO == null) throw new RequiredObjectIsNullException();

		logger.info("Editando uma despesa!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhuma despesa encontrada com este ID!"));

		entity.setDescricao(despesaVO.getDescricao());
		entity.setData(despesaVO.getData());
		entity.setValor(despesaVO.getValor());
		
		var vo = DozerMapper.parseObject(repository.save(entity), DespesaVO.class);
		
		logger.info("Despesa editada!");
		
		return vo;
	}

	public void delete(Integer id) {
		
		logger.info("Deletando uma despesa!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhuma despesa encontrada com este ID!"));

		repository.delete(entity);
		
		logger.info("Despesa deletada!");
	}
	
	public List<DespesaVO> findByDescricao(String descricao) {
		
		logger.info("Buscando despesas pela descrição!");
				
		var vos = DozerMapper.parseListObjects(repository.findByDescricao(descricao), DespesaVO.class);

		return vos;
	}
}
