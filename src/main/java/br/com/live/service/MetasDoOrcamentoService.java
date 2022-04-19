package br.com.live.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import br.com.live.custom.MetasDoOrcamentoCustom;
import br.com.live.model.ConsultaMetasOrcamento;
import br.com.live.repository.MetasDoOrcamentoRepository;


import java.util.List;

@Service
@Transactional
public class MetasDoOrcamentoService {
	
	private final MetasDoOrcamentoCustom metasDoOrcamentoCustom;
	private final MetasDoOrcamentoRepository metasDoOrcamentoRepository;
	
	public MetasDoOrcamentoService(MetasDoOrcamentoCustom metasDoOrcamentoCustom, MetasDoOrcamentoRepository metasDoOrcamentoRepository) {
		this.metasDoOrcamentoCustom = metasDoOrcamentoCustom;
		this.metasDoOrcamentoRepository = metasDoOrcamentoRepository;
	}
	
	public List<ConsultaMetasOrcamento> findDadosGrid(int ano, int tipoMeta) {
		return metasDoOrcamentoCustom.findMetasOrcamentoGrid(ano, tipoMeta);
	}
	
	public void saveValoresMetasOrcamento(List<ConsultaMetasOrcamento> listMetas, int ano, int tipoMeta) {
		br.com.live.entity.MetasDoOrcamento metaOrcamento = null;
		
		for (ConsultaMetasOrcamento dadosMetas : listMetas) {
				
			if (dadosMetas.id == null) {
				metaOrcamento = new br.com.live.entity.MetasDoOrcamento(dadosMetas.descricao, ano, tipoMeta, dadosMetas.valorMes1, dadosMetas.valorMes2, dadosMetas.valorMes3, dadosMetas.valorMes4, dadosMetas.valorMes5,
						dadosMetas.valorMes6, dadosMetas.valorMes7, dadosMetas.valorMes8, dadosMetas.valorMes9, dadosMetas.valorMes10, dadosMetas.valorMes11, dadosMetas.valorMes12);
			} else {
				metaOrcamento = metasDoOrcamentoRepository.findByIdMetas(dadosMetas.id);
				
				metaOrcamento.valorMes1 = dadosMetas.valorMes1;
				metaOrcamento.valorMes2 = dadosMetas.valorMes2;
				metaOrcamento.valorMes3 = dadosMetas.valorMes3;
				metaOrcamento.valorMes4 = dadosMetas.valorMes4;
				metaOrcamento.valorMes5 = dadosMetas.valorMes5;
				metaOrcamento.valorMes6 = dadosMetas.valorMes6;
				metaOrcamento.valorMes7 = dadosMetas.valorMes7;
				metaOrcamento.valorMes8 = dadosMetas.valorMes8;
				metaOrcamento.valorMes9 = dadosMetas.valorMes9;
				metaOrcamento.valorMes10 = dadosMetas.valorMes10;
				metaOrcamento.valorMes11 = dadosMetas.valorMes11;
				metaOrcamento.valorMes12 = dadosMetas.valorMes12;
				
			}
			metasDoOrcamentoRepository.save(metaOrcamento);
		}
	}
}
