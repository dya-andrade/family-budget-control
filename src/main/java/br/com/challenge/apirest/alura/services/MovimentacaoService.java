package br.com.challenge.apirest.alura.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.challenge.apirest.alura.exceptions.AlreadyRegisteredException;
import br.com.challenge.apirest.alura.exceptions.RequiredObjectIsNullException;
import br.com.challenge.apirest.alura.exceptions.ResourceNotFoundException;
import br.com.challenge.apirest.alura.model.Movimentacao;
import br.com.challenge.apirest.alura.vo.MovimentacaoVO;

@Service
@Transactional
public abstract class MovimentacaoService<E extends Movimentacao, V extends MovimentacaoVO, R extends JpaRepository<E, Long>> {

	private Logger logger = Logger.getLogger(MovimentacaoService.class.getName());

	@Autowired
	protected R repository;

	protected abstract E repositoryFindByDescricaoAndMes(String descricao, int mes);

	public abstract List<V> repositoryFindByMonth(int ano, int mes);

	public abstract Page<V> repositoryFindByDescricao(String descricao, Pageable pageable);

	protected abstract E parseEntity(V vo);

	protected abstract V parseVO(E entity);
	
	protected abstract Page<V> parseListPageVO(Page<E> entities);

	protected abstract E setUpdate(E entity, V vo);

	public V create(V vo) {

		if (vo == null)
			throw new RequiredObjectIsNullException();

		int mes = vo.getData().getMonth().getValue();

		E duplicada = this.repositoryFindByDescricaoAndMes(vo.getDescricao(), mes);

		if (duplicada != null)
			throw new AlreadyRegisteredException();

		logger.info("Criando uma entidade!");

		E entity = this.parseEntity(vo);

		E save = repository.save(entity);

		vo = this.parseVO(save);

		logger.info("Entidade criada!");

		return vo;
	}

	public Page<V> findAll(Pageable pageable) {

		logger.info("Buscando todas as entidades!");

		var vos = this.parseListPageVO(repository.findAll(pageable));

		return vos;
	}

	public V findById(Long id) {

		logger.info("Buscando uma entidade!");

		E entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhuma entidade encontrada com este ID!"));

		var vo = parseVO(entity);

		return vo;
	}

	public V update(Long id, V vo) {

		if (vo == null)
			throw new RequiredObjectIsNullException();

		logger.info("Editando uma entidade!");

		E entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhuma entidade encontrada com este ID!"));

		entity = this.setUpdate(entity, vo);

		vo = this.parseVO(repository.save(entity));

		logger.info("Entidade editada!");

		return vo;
	}

	public void delete(Long id) {

		logger.info("Deletando uma entidade!");

		E entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhuma entidade encontrada com este ID!"));

		repository.delete(entity);

		logger.info("Entidade deletada!");
	}

	public Page<V> findByDescricao(String descricao, Pageable pageable) {

		logger.info("Buscando entidades pela descrição!");

		var vos = this.repositoryFindByDescricao(descricao, pageable);

		return vos;
	}

	public List<V> findByMonth(Integer ano, Integer mes) {

		logger.info("Buscando entidades pelo mês/ano!");

		var vos = this.repositoryFindByMonth(ano, mes);

		return vos;
	}
}
