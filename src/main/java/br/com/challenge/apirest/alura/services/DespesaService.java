package br.com.challenge.apirest.alura.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.challenge.apirest.alura.mapper.DozerMapper;
import br.com.challenge.apirest.alura.model.Despesa;
import br.com.challenge.apirest.alura.repositories.DespesaRepository;
import br.com.challenge.apirest.alura.vo.DespesaVO;

@Service
public class DespesaService extends MovimentacaoService<Despesa, DespesaVO, DespesaRepository> {

	@Override
	protected Despesa repositoryFindByDescricaoAndMes(String descricao, int mes) {
		return repository.findByDescricaoAndMes(descricao, mes);
	}

	@Override
	public List<DespesaVO> repositoryFindByMonth(int ano, int mes) {
		return DozerMapper.parseListObjects(repository.findByMonth(ano, mes), DespesaVO.class);
	}

	@Override
	public Page<DespesaVO> repositoryFindByDescricao(String descricao, Pageable pageable) {
		return parseListPageVO(repository.findByDescricao(descricao, pageable));	
	}

	@Override
	protected Despesa parseEntity(DespesaVO vo) {
		return DozerMapper.parseObject(vo, Despesa.class);
	}

	@Override
	protected DespesaVO parseVO(Despesa entity) {
		return DozerMapper.parseObject(entity, DespesaVO.class);
	}

	@Override
	protected Page<DespesaVO> parseListPageVO(Page<Despesa> vos) {
		return vos.map(p -> DozerMapper.parseObject(p, DespesaVO.class));
	}

	@Override
	protected Despesa setUpdate(Despesa entity, DespesaVO vo) {
		entity.setDescricao(vo.getDescricao());
		entity.setData(vo.getData());
		entity.setValor(vo.getValor());
		entity.setCategoria(vo.getCategoria());

		return entity;
	}

}
