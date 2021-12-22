package br.com.live.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.live.custom.EstacaoCustom;
import br.com.live.entity.Agrupador;
import br.com.live.entity.AgrupadorColecao;
import br.com.live.entity.Estacao;
import br.com.live.entity.EstacaoAgrupador;
import br.com.live.entity.EstacaoTabelaPreco;
import br.com.live.entity.MetasDaEstacao;
import br.com.live.entity.MetasPorRepresentante;
import br.com.live.model.ConsultaColecoesAgrupador;
import br.com.live.model.ConsultaEstacaoAgrupadores;
import br.com.live.repository.AgrupadorColecaoRepository;
import br.com.live.repository.AgrupadorRepository;
import br.com.live.repository.EstacaoAgrupadorRepository;
import br.com.live.repository.EstacaoRepository;
import br.com.live.repository.EstacaoTabelaPrecoRepository;
import br.com.live.repository.MetasDaEstacaoRepository;
import br.com.live.repository.MetasPorRepresentanteRepository;

@Service
@Transactional
public class EstacaoService {
	
	private final EstacaoRepository estacaoRepository;
	private final MetasDaEstacaoRepository metasDaEstacaoRepository;
	private final MetasPorRepresentanteRepository metasPorRepresentanteRepository;
	private final EstacaoCustom estacaoCustom;
	private final EstacaoTabelaPrecoRepository estacaoTabelaPrecoRepository;
	private final AgrupadorRepository agrupadorRepository;
	private final AgrupadorColecaoRepository agrupadorColecaoRepository;
	private final EstacaoAgrupadorRepository estacaoAgrupadorRepository;

	public EstacaoService(EstacaoRepository estacaoRepository, MetasDaEstacaoRepository metasDaEstacaoRepository, MetasPorRepresentanteRepository metasPorRepresentanteRepository,
			EstacaoCustom estacaoCustom, EstacaoTabelaPrecoRepository estacaoTabelaPrecoRepository, AgrupadorRepository agrupadorRepository,
			AgrupadorColecaoRepository agrupadorColecaoRepository, EstacaoAgrupadorRepository estacaoAgrupadorRepository) {
		this.estacaoRepository = estacaoRepository;		
		this.metasDaEstacaoRepository = metasDaEstacaoRepository;
		this.metasPorRepresentanteRepository = metasPorRepresentanteRepository;
		this.estacaoCustom = estacaoCustom;
		this.estacaoTabelaPrecoRepository = estacaoTabelaPrecoRepository;
		this.agrupadorRepository = agrupadorRepository;
		this.agrupadorColecaoRepository = agrupadorColecaoRepository;
		this.estacaoAgrupadorRepository = estacaoAgrupadorRepository;
	}
	
	public Estacao saveEstacao(int codEstacao, String descricao, int catalogo) {
		 
		Estacao estacao = null;
		
		if (codEstacao == 0) {
			codEstacao = estacaoCustom.findNewCodEstacao();
			estacao = new Estacao(codEstacao, descricao, catalogo);
		} else {
			estacao = estacaoRepository.findByCodEstacao(codEstacao);
			estacao.catalogo = catalogo;
			estacao.descricao = descricao;
		}
		estacaoRepository.save(estacao);
		
		return estacaoRepository.findByCodEstacao(codEstacao);
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
	
	public void saveEstacaoTabelasPreco(int codEstacao, int colTab, int mesTab, int seqTab) {
		String idEstTab = codEstacao + "-" + colTab + "-" + mesTab + "-" + seqTab;
		
		EstacaoTabelaPreco dados = estacaoTabelaPrecoRepository.findByIdComposto(idEstTab);
		
		if (dados != null) {
			estacaoTabelaPrecoRepository.deleteById(idEstTab);
		}
		
		dados = new EstacaoTabelaPreco(codEstacao, colTab, mesTab, seqTab);
		
		estacaoTabelaPrecoRepository.save(dados);
	}
	
	public Agrupador saveAgrupador(int codAgrupador, String descricao) {
		Agrupador agrupador= null;
		
		if (codAgrupador == 0) {
			codAgrupador = estacaoCustom.findNewCodAgrupador();
			
			agrupador = new Agrupador(codAgrupador, descricao);
		} else {
			agrupador = agrupadorRepository.findByCodAgrupador(codAgrupador);
			agrupador.descricao = descricao;
		}
		
		agrupadorRepository.save(agrupador);
		
		return agrupadorRepository.findByCodAgrupador(codAgrupador);
	}
	
	public List<ConsultaColecoesAgrupador> saveColecaoAgrupador(int codAgrupador, int colecao, int subColecao) {
		
		String idAgrupCol = codAgrupador + "-" + colecao + "-" + subColecao;
		
		AgrupadorColecao agrupCol = agrupadorColecaoRepository.findByIdComposto(idAgrupCol);
		
		if (agrupCol == null) {
			agrupCol = new AgrupadorColecao(codAgrupador, colecao, subColecao);
		}
		
		agrupadorColecaoRepository.save(agrupCol);
		
		return estacaoCustom.findColecoesGrid(codAgrupador);
	}
	
	public List<ConsultaEstacaoAgrupadores> saveEstacaoAgrupador(int codEstacao, int codAgrupador) {
		
		String idComposto = codEstacao + "-" + codAgrupador;
		
		EstacaoAgrupador estacaoAgrupador = estacaoAgrupadorRepository.findByIdComposto(idComposto);
		
		if (estacaoAgrupador == null) {
			estacaoAgrupador = new EstacaoAgrupador(codAgrupador,codEstacao);
		}
		
		estacaoAgrupadorRepository.save(estacaoAgrupador);
		
		return estacaoCustom.findAgrupadoresGrid(codEstacao);
	}
	
	public void copiarRepresentantes(long codEstacao, int tipoMeta) {
		MetasPorRepresentante metasRepCopia;
		
		List<MetasPorRepresentante> vendasRep = metasPorRepresentanteRepository.findByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);
		
		if (tipoMeta == 1) {
			estacaoCustom.deleteByCodEstacaoAndTipoMeta(codEstacao, 2);
		} else {
			estacaoCustom.deleteByCodEstacaoAndTipoMeta(codEstacao, 1);
		}
		
		for (MetasPorRepresentante dadosRep : vendasRep) {
			if (tipoMeta == 1) {
				metasRepCopia = new MetasPorRepresentante(dadosRep.codEstacao, dadosRep.codRepresentante, 2, dadosRep.meta, dadosRep.descricaoRep);
			} else {
				metasRepCopia = new MetasPorRepresentante(dadosRep.codEstacao, dadosRep.codRepresentante, 1, dadosRep.meta, dadosRep.descricaoRep);
			}
			metasPorRepresentanteRepository.save(metasRepCopia);
		}	
	}
	
	public Estacao duplicarCadastro(long codEstacaoOrigem) {
		Estacao estacao = estacaoRepository.findByCodEstacao(codEstacaoOrigem);
		
		Estacao dadosEstacao = saveEstacao(0, estacao.descricao + "(CÓPIA DA ESTAÇÃO " + codEstacaoOrigem + ")", estacao.catalogo);
		
		gravarNovaEstacao(codEstacaoOrigem, dadosEstacao.codEstacao);
		
		return dadosEstacao;
	}
	
	public void gravarNovaEstacao(long codEstacaoOrigem, long codEstacaoDestino) {	
		gravarMetasDaEstacao(codEstacaoOrigem, codEstacaoDestino, 1);
		gravarMetasDaEstacao(codEstacaoOrigem, codEstacaoDestino, 2);
		
		gravarMetasRepresentantes(codEstacaoOrigem, codEstacaoDestino, 1);
		gravarMetasRepresentantes(codEstacaoOrigem, codEstacaoDestino, 2);
		
		gravarTabelasPreco(codEstacaoOrigem, codEstacaoDestino);
		gravarAgrupadores(codEstacaoOrigem, codEstacaoDestino);
	}
	
	public void gravarMetasDaEstacao(long codEstacaoOrigem, long codEstacaoDestino, int tipoMeta) {
		MetasDaEstacao dadosImportados;
		
		List<MetasDaEstacao> dadosEstacaoVendas = metasDaEstacaoRepository.findByCodEstacaoAndTipoMeta(codEstacaoOrigem, tipoMeta);
		
		for (MetasDaEstacao estacaoVendas : dadosEstacaoVendas) {
			dadosImportados = new MetasDaEstacao(codEstacaoDestino, estacaoVendas.mes, estacaoVendas.ano, estacaoVendas.tipoMeta, estacaoVendas.percDistribuicao);
			metasDaEstacaoRepository.save(dadosImportados);
		}
	}
	
	public void gravarMetasRepresentantes(long codEstacaoOrigem, long codEstacaoDestino, int tipoMeta) {
		MetasPorRepresentante dadosImportados;
		
		List<MetasPorRepresentante> dadosMetasRepresentante= metasPorRepresentanteRepository.findByCodEstacaoAndTipoMeta(codEstacaoOrigem, tipoMeta);
		
		for (MetasPorRepresentante estacaoRep : dadosMetasRepresentante) {
			dadosImportados = new MetasPorRepresentante(codEstacaoDestino, estacaoRep.codRepresentante, estacaoRep.tipoMeta, estacaoRep.meta, estacaoRep.descricaoRep);
			metasPorRepresentanteRepository.save(dadosImportados);
		}
	}
	
	public void gravarTabelasPreco(long codEstacaoOrigem, long codEstacaoDestino) {
		EstacaoTabelaPreco dadosImportados;
		
		List<EstacaoTabelaPreco> dadosTabelasPreco = estacaoTabelaPrecoRepository.findByCodEstacao(codEstacaoOrigem);
		
		for (EstacaoTabelaPreco tabelaPreco : dadosTabelasPreco) {
			dadosImportados = new EstacaoTabelaPreco(codEstacaoDestino,tabelaPreco.colTab, tabelaPreco.mesTab, tabelaPreco.seqTab);
			estacaoTabelaPrecoRepository.save(dadosImportados);
		}
	}
	
	public void gravarAgrupadores(long codEstacaoOrigem, long codEstacaoDestino) {
		EstacaoAgrupador dadosImportados;
		
		List<EstacaoAgrupador> dadosAgrupador = estacaoAgrupadorRepository.findByCodEstacao(codEstacaoOrigem);
		
		for (EstacaoAgrupador agrupador : dadosAgrupador) {
			dadosImportados = new EstacaoAgrupador(agrupador.codAgrupador, codEstacaoDestino);
			estacaoAgrupadorRepository.save(dadosImportados);
		}
	}
	
	public void excluirEstacao(long codEstacao) {
		metasDaEstacaoRepository.deleteByCodEstacao(codEstacao);
		metasPorRepresentanteRepository.deleteByCodEstacao(codEstacao);
		estacaoTabelaPrecoRepository.deleteByCodEstacao(codEstacao);
		estacaoAgrupadorRepository.deleteByCodEstacao(codEstacao);
		estacaoRepository.deleteByCodEstacao(codEstacao);
	}

	public void excluirMetas(String id) {
		metasDaEstacaoRepository.deleteById(id);
	}
	
	public void excluirMetasRepresentante(String id) {
		metasPorRepresentanteRepository.deleteById(id);
	}
	
	public void excluirEstacaoTabela(String idTabela) {
		estacaoTabelaPrecoRepository.deleteById(idTabela);
	}
	
	public void excluirColecao(String idComposto) {
		agrupadorColecaoRepository.deleteById(idComposto);
	}
	
	public void excluirEstacaoAgrupador(String idEstacaoAgrupador) {
		estacaoAgrupadorRepository.deleteById(idEstacaoAgrupador);
	}
	
	public void excluirAgrupador(int codAgrupador) {
		agrupadorColecaoRepository.deleteByCodAgrupador(codAgrupador);
		agrupadorRepository.deleteById(codAgrupador);
	}
}
