package br.com.challenge.apirest.alura.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.challenge.apirest.alura.mapper.DozerMapper;
import br.com.challenge.apirest.alura.model.Receita;
import br.com.challenge.apirest.alura.repositories.ReceitaRepository;
import br.com.challenge.apirest.alura.vo.ReceitaVO;

@Service
public class ReceitaService extends MovimentacaoService<Receita, ReceitaVO, ReceitaRepository> {

	@Override
	protected Receita findByDescricaoAndMes(String descricao, int mes) {
		return repository.findByDescricaoAndMes(descricao, mes);
	}

	@Override
	public List<ReceitaVO> findByMonth(int ano, int mes) {
		return DozerMapper.parseListObjects(repository.findByMonth(ano, mes), ReceitaVO.class);
	}

	@Override
	public List<ReceitaVO> findByDescricao(String descricao) {
		return DozerMapper.parseListObjects(repository.findByDescricao(descricao), ReceitaVO.class);
	}

	@Override
	protected Receita parseEntity(ReceitaVO vo) {
		return DozerMapper.parseObject(vo, Receita.class);
	}

	@Override
	protected ReceitaVO parseVO(Receita entity) {
		return DozerMapper.parseObject(entity, ReceitaVO.class);
	}

	@Override
	protected List<ReceitaVO> parseListVO(List<Receita> vos) {
		return DozerMapper.parseListObjects(vos, ReceitaVO.class);
	}

	@Override
	protected Receita setUpdate(Receita entity, ReceitaVO vo) {
		entity.setDescricao(vo.getDescricao());
		entity.setData(vo.getData());
		entity.setValor(vo.getValor());

		return entity;
	}
}
