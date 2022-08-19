package br.com.challenge.apirest.alura.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.challenge.apirest.alura.mapper.DozerMapper;
import br.com.challenge.apirest.alura.model.Despesa;
import br.com.challenge.apirest.alura.repositories.DespesaRepository;
import br.com.challenge.apirest.alura.vo.DespesaVO;

@Service
public class DespesaService extends MovimentacaoService<Despesa, DespesaVO, DespesaRepository> {

	@Override
	protected Despesa findByDescricaoAndMes(String descricao, int mes) {
		return repository.findByDescricaoAndMes(descricao, mes);
	}

	@Override
	public List<DespesaVO> findByMonth(int ano, int mes) {
		return DozerMapper.parseListObjects(repository.findByMonth(ano, mes), DespesaVO.class);
	}

	@Override
	public List<DespesaVO> findByDescricao(String descricao) {
		return DozerMapper.parseListObjects(repository.findByDescricao(descricao), DespesaVO.class);	
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
	protected List<DespesaVO> parseListVO(List<Despesa> vos) {
		return DozerMapper.parseListObjects(vos, DespesaVO.class);
	}

	@Override
	protected Despesa setUpdate(Despesa entity, DespesaVO vo) {
		entity.setDescricao(vo.getDescricao());
		entity.setData(vo.getData());
		entity.setValor(vo.getValor());

		return entity;
	}

}
