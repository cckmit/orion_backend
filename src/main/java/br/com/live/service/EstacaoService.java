package br.com.live.service;

import org.springframework.stereotype.Service;

import br.com.live.custom.EstacaoCustom;
import br.com.live.entity.Estacao;
import br.com.live.entity.MetasDaEstacao;
import br.com.live.entity.MetasPorRepresentante;
import br.com.live.repository.EstacaoRepository;
import br.com.live.repository.MetasDaEstacaoRepository;
import br.com.live.repository.MetasPorRepresentanteRepository;

@Service
public class EstacaoService {
	
	private final EstacaoRepository estacaoRepository;
	private final MetasDaEstacaoRepository metasDaEstacaoRepository;
	private final MetasPorRepresentanteRepository metasPorRepresentanteRepository;
	private final EstacaoCustom estacaoCustom;

	public EstacaoService(EstacaoRepository estacaoRepository, MetasDaEstacaoRepository metasDaEstacaoRepository, MetasPorRepresentanteRepository metasPorRepresentanteRepository, EstacaoCustom estacaoCustom) {
		this.estacaoRepository = estacaoRepository;		
		this.metasDaEstacaoRepository = metasDaEstacaoRepository;
		this.metasPorRepresentanteRepository = metasPorRepresentanteRepository;
		this.estacaoCustom = estacaoCustom;
	}
	
	public void saveEstacao(int codEstacao, String descricao, int catalogo) {
		 
		Estacao estacao = estacaoRepository.findByCodEstacao(codEstacao);
		
		if (estacao == null) {
			estacao = new Estacao(codEstacao, descricao, catalogo);
		} else {
			estacao.catalogo = catalogo;
			estacao.descricao = descricao;
		}
		
		estacaoRepository.save(estacao);
	}
	
	public void saveMetas(int codEstacao, int mes, int ano, int tipoMeta, float percDistribuicao) {
		
		String id = codEstacao + "-" + mes + "-" + ano + "-" + tipoMeta;
		
		MetasDaEstacao metas = metasDaEstacaoRepository.findByIdMetas(id);
		
		if (metas == null) {
			metas = new MetasDaEstacao(codEstacao, mes, ano, tipoMeta, percDistribuicao);
		} else {
			metas.percDistribuicao = percDistribuicao; 
		}
		metasDaEstacaoRepository.save(metas);
	}
	
	public void saveMetasRepresentante(int codEstacao, int codRepresentante, int tipoMeta, float meta) {
		
		String id = codEstacao + "-" + codRepresentante + "-" + tipoMeta;
		
		MetasPorRepresentante metas = metasPorRepresentanteRepository.findByIdMetas(id);
		
		if (metas == null) {
			String descRep = estacaoCustom.findDescRepresentante(codRepresentante);
			
			metas = new MetasPorRepresentante(codEstacao, codRepresentante, tipoMeta, meta, descRep);
		} else {
			metas.meta= meta; 
		}
		metasPorRepresentanteRepository.save(metas);
	}
	
	public void excluirMetas(String id) {
		metasDaEstacaoRepository.deleteById(id);
	}
	
	public void excluirMetasRepresentante(String id) {
		metasPorRepresentanteRepository.deleteById(id);
	}
}
