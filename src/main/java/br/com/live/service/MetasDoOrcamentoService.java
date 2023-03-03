package br.com.live.service;

import javax.transaction.Transactional;

import br.com.live.entity.MetasDoOrcamento;
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
	
	public String findTotalGeral(int tipoMeta, int ano) {
		return metasDoOrcamentoCustom.findTotalGeralPorTipoMeta(tipoMeta, ano);
	}

	public void importarMetas(List<ConsultaMetasOrcamento> listMetas, int ano, int tipoMeta) {
		metasDoOrcamentoRepository.deleteByAnoAndTipoMeta(ano, tipoMeta);
		MetasDoOrcamento metasSalvas = null;

		for (ConsultaMetasOrcamento dadosMeta : listMetas) {
			metasSalvas = new MetasDoOrcamento(dadosMeta.descricao, dadosMeta.modalidade, ano, tipoMeta, dadosMeta.valorMes1, dadosMeta.valorMes2, dadosMeta.valorMes3,
					dadosMeta.valorMes4, dadosMeta.valorMes5, dadosMeta.valorMes6, dadosMeta.valorMes7, dadosMeta.valorMes8, dadosMeta.valorMes9, dadosMeta.valorMes10,
					dadosMeta.valorMes11, dadosMeta.valorMes12);
			metasDoOrcamentoRepository.save(metasSalvas);
		}
	}
}
