package br.com.challenge.apirest.alura.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.challenge.apirest.alura.mapper.DozerMapper;
import br.com.challenge.apirest.alura.model.Receita;
import br.com.challenge.apirest.alura.repositories.ReceitaRepository;
import br.com.challenge.apirest.alura.vo.ReceitaVO;

@Service
public class ReceitaService extends MovimentacaoService<Receita, ReceitaVO, ReceitaRepository> {

	@Override
	protected Receita repositoryFindByDescricaoAndMes(String descricao, int mes) {
		return repository.findByDescricaoAndMes(descricao, mes);
	}

	@Override
	public List<ReceitaVO> repositoryFindByMonth(int ano, int mes) {
		return DozerMapper.parseListObjects(repository.findByMonth(ano, mes), ReceitaVO.class);
	}

	@Override
	public Page<ReceitaVO> repositoryFindByDescricao(String descricao, Pageable pageable) {
		return parseListPageVO(repository.findByDescricao(descricao, pageable));
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
	protected Page<ReceitaVO> parseListPageVO(Page<Receita> vos) {
		return vos.map(p -> DozerMapper.parseObject(p, ReceitaVO.class));
	}
}
