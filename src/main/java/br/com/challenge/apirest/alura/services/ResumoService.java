package br.com.challenge.apirest.alura.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.challenge.apirest.alura.repositories.DespesaRepository;
import br.com.challenge.apirest.alura.repositories.ReceitaRepository;
import br.com.challenge.apirest.alura.vo.CategoriaVO;
import br.com.challenge.apirest.alura.vo.ResumoVO;

@Service
public class ResumoService {
	
	private Logger logger = Logger.getLogger(ResumoService.class.getName());

	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
	private ReceitaRepository receitaRepository;

	public ResumoVO monthSummary(Integer ano, Integer mes) {
		
		logger.info("Realizando o resumo do mês!");
		
		Double valorTotalReceitas = receitaRepository.findTotalAmountByMonth(ano, mes);		
		Double valorTotalDespesas = despesaRepository.findTotalAmountByMonth(ano, mes);
		
		Double saldoFinal = valorTotalReceitas - valorTotalDespesas;
		
		List<CategoriaVO> valorTotalPorCategoria = despesaRepository.findTotalAmountByCategoria(ano, mes);
		
		ResumoVO resumoMesVO = new ResumoVO();

		resumoMesVO.setTotalReceitas(valorTotalReceitas);
		resumoMesVO.setTotalDespesas(valorTotalDespesas);
		resumoMesVO.setSaldoFinal(saldoFinal);
		resumoMesVO.setTotalPorCategoria(valorTotalPorCategoria);
		
		return resumoMesVO;
	}

}
