package br.com.live.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.body.BodySugestaoReservaMateriais;
import br.com.live.custom.ProdutoCustom;
import br.com.live.custom.SugestaoReservaMaterialCustom;
import br.com.live.model.ArtigoProduto;
import br.com.live.model.OrdemProducao;
import br.com.live.model.Produto;
import br.com.live.model.SugestaoReservaConfigArtigos;
import br.com.live.model.SugestaoReservaMateriaisReservados;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;
import br.com.live.model.SugestaoReservaMateriais;

@Service
@Transactional
public class SugestaoReservaMaterialService {

	private final SugestaoReservaMaterialCustom sugestaoReservaMaterialCustom;
	private final SugestaoReservaMaterialPorOrdensService sugestaoReservaMaterialPorOrdensService;
	private final OrdemProducaoService ordemProducaoService;
	private final ProdutoCustom produtoCustom;

	public SugestaoReservaMaterialService(SugestaoReservaMaterialCustom sugestaoReservaMaterialCustom,
			SugestaoReservaMaterialPorOrdensService sugestaoReservaMaterialPorOrdensService,
			OrdemProducaoService ordemProducaoService, ProdutoCustom produtoCustom) {		
		this.sugestaoReservaMaterialCustom = sugestaoReservaMaterialCustom;
		this.sugestaoReservaMaterialPorOrdensService = sugestaoReservaMaterialPorOrdensService;
		this.ordemProducaoService = ordemProducaoService;
		this.produtoCustom = produtoCustom;
	}

	public List<Produto> findTecidosEmOrdensParaLiberacao() {
		return sugestaoReservaMaterialCustom.findTecidosEmOrdensParaLiberacao();
	}
	
	public List<Produto> findReferenciasEmOrdensParaLiberacao() {
		return sugestaoReservaMaterialCustom.findReferenciasEmOrdensParaLiberacao();				
	}	

	public SugestaoReservaMateriais calcularSugestaoReservaPorOrdem(List<String> camposSelParaPriorizacao, int periodoInicial, int periodoFinal, String embarques, String referencias, String estagios, String artigos, String tecidos, String depositosTecidos, String depositosAviamentos, boolean isSomenteFlat, boolean isDiretoCostura, boolean isOrdensSemTecido, int percentualMinimoAtender, int regraReserva, boolean isMostruario) {
		return sugestaoReservaMaterialPorOrdensService.calcularSugestaoReserva(camposSelParaPriorizacao, periodoInicial, periodoFinal, embarques, referencias, estagios, artigos, tecidos, depositosTecidos, depositosAviamentos, isSomenteFlat, isDiretoCostura, isOrdensSemTecido, percentualMinimoAtender, regraReserva, isMostruario);		
	}
	
	public int findQtdePecasLiberadasDia() {
		return ordemProducaoService.findQtdePecasApontadaNoDiaPorEstagioUsuario(2);
	}	

	public int findQtdeFlatPecasLiberadasDia(Date dataInicial, Date dataFinal) {
		return ordemProducaoService.findQtdePecasFlatApontadaNoDia(2, dataInicial, dataFinal);
	}	

	public double findQtdeMinutosFlatLiberadasDia(Date dataInicial, Date dataFinal) {
		return ordemProducaoService.findQtdeMinutosFlatApontadaNoDia(2, dataInicial, dataFinal);
	}	
	
	public int[] findQtdePecasLiberadasDiaPorArtigo(Date dataInicial, Date dataFinal) {		
		List<SugestaoReservaConfigArtigos> configArtigos = sugestaoReservaMaterialCustom.findConfigArtigos();		
		String artigosConfig = "";
		int quantidade = 0;
		int qtde1 = 0;
		int qtde2 = 0;
		int qtde3 = 0;
		int qtde4 = 0;
		int qtde5 = 0;
		int qtde6 = 0;
		int qtde7 = 0;
		int qtde8 = 0;
		int qtde9 = 0;
		int qtdeOutros = 0;
		int [] quantidades = new int[10];
		
		for (SugestaoReservaConfigArtigos configuracao : configArtigos) {
			if ((configuracao.getArtigos() != null) && (!configuracao.getArtigos().isBlank())) {
				if (artigosConfig.isEmpty()) artigosConfig += configuracao.getArtigos();
				else artigosConfig += "," + configuracao.getArtigos();
				
				quantidade = ordemProducaoService.findQtdePecasApontadaNoDiaPorEstagioArtigos(2, true, configuracao.getArtigos(), dataInicial, dataFinal);
	
				if (configuracao.getColuna() == 1) qtde1 = quantidade;
				if (configuracao.getColuna() == 2) qtde2 = quantidade;
				if (configuracao.getColuna() == 3) qtde3 = quantidade;
				if (configuracao.getColuna() == 4) qtde4 = quantidade;
				if (configuracao.getColuna() == 5) qtde5 = quantidade;
				if (configuracao.getColuna() == 6) qtde6 = quantidade;
				if (configuracao.getColuna() == 7) qtde7 = quantidade;
				if (configuracao.getColuna() == 8) qtde8 = quantidade;
				if (configuracao.getColuna() == 9) qtde9 = quantidade;
			}
		}
		qtdeOutros = ordemProducaoService.findQtdePecasApontadaNoDiaPorEstagioArtigos(2, false, artigosConfig, dataInicial, dataFinal);		
		
		quantidades[0] = qtdeOutros;
		quantidades[1] = qtde1;
		quantidades[2] = qtde2;
		quantidades[3] = qtde3;
		quantidades[4] = qtde4;
		quantidades[5] = qtde5;
		quantidades[6] = qtde6;
		quantidades[7] = qtde7;
		quantidades[8] = qtde8;
		quantidades[9] = qtde9;		
		
		return quantidades;
	}	

	public double[] findQtdeMinutosLiberadosDiaPorArtigo(Date dataInicial, Date dataFinal) {
		List<SugestaoReservaConfigArtigos> configArtigos = sugestaoReservaMaterialCustom.findConfigArtigos();		
		String artigosConfig = "";
		double quantidade = 0;
		double qtde1 = 0;
		double qtde2 = 0;
		double qtde3 = 0;
		double qtde4 = 0;
		double qtde5 = 0;
		double qtde6 = 0;
		double qtde7 = 0;
		double qtde8 = 0;
		double qtde9 = 0;
		double qtdeOutros = 0;
		double [] quantidades = new double[10];
		
		for (SugestaoReservaConfigArtigos configuracao : configArtigos) {
			if ((configuracao.getArtigos() != null) && (!configuracao.getArtigos().isBlank())) {
				if (artigosConfig.isEmpty()) artigosConfig += configuracao.getArtigos();
				else artigosConfig += "," + configuracao.getArtigos();
				
				quantidade = ordemProducaoService.findQtdeMinutosApontadoNoDiaPorEstagioArtigos(2, true, configuracao.getArtigos(), dataInicial, dataFinal);
	
				if (configuracao.getColuna() == 1) qtde1 = quantidade;
				if (configuracao.getColuna() == 2) qtde2 = quantidade;
				if (configuracao.getColuna() == 3) qtde3 = quantidade;
				if (configuracao.getColuna() == 4) qtde4 = quantidade;
				if (configuracao.getColuna() == 5) qtde5 = quantidade;
				if (configuracao.getColuna() == 6) qtde6 = quantidade;
				if (configuracao.getColuna() == 7) qtde7 = quantidade;
				if (configuracao.getColuna() == 8) qtde8 = quantidade;
				if (configuracao.getColuna() == 9) qtde9 = quantidade;
			}
		}
		qtdeOutros = ordemProducaoService.findQtdeMinutosApontadoNoDiaPorEstagioArtigos(2, false, artigosConfig, dataInicial, dataFinal);
		
		quantidades[0] = qtdeOutros;
		quantidades[1] = qtde1;
		quantidades[2] = qtde2;
		quantidades[3] = qtde3;
		quantidades[4] = qtde4;
		quantidades[5] = qtde5;
		quantidades[6] = qtde6;
		quantidades[7] = qtde7;
		quantidades[8] = qtde8;
		quantidades[9] = qtde9;		
		
		return quantidades;
	}	
	
	public void liberarProducao(List<OrdemProducao> listaOrdensLiberar, List<SugestaoReservaMateriaisReservados> listaMateriaisReservar , boolean urgente, long idUsuarioOrion) {		
		if (!urgente) Collections.sort(listaOrdensLiberar);		

		System.out.println("LIBERAR ORDENS DE PRODUÇÃO");
		for (OrdemProducao ordem : listaOrdensLiberar) {
			System.out.println("ORDEM: " + ordem.ordemProducao);
			sugestaoReservaMaterialCustom.excluirMateriaisReservadosPorOrdem(ordem.ordemProducao);
			ordemProducaoService.baixarEstagioProducao(ordem.ordemProducao, 2, idUsuarioOrion); // Estágio = 2 - ANALISE DE TECIDO
			ordemProducaoService.gravarSeqPrioridadeDia(ordem.ordemProducao, urgente);
		}
		
		System.out.println("GRAVAR QUANTIDADES DE TECIDOS DAS ORDENS LIBERADAS");		
		for (SugestaoReservaMateriaisReservados reservar : listaMateriaisReservar) {			
			System.out.println("ORDEM: " + reservar.idOrdem  + " - " + reservar.nivelMaterial + "." +  reservar.grupoMaterial + "." + reservar.subMaterial + "." + reservar.itemMaterial + " => " + reservar.qtdeReservado);
			sugestaoReservaMaterialCustom.gravarMateriaisReservados(reservar.idOrdem, reservar.nivelMaterial, reservar.grupoMaterial, reservar.subMaterial, reservar.itemMaterial, reservar.qtdeReservado);
		}
	}		
	
	public void gravarLembrete(List<OrdemProducao> listaOrdensComLembrete) {
		for (OrdemProducao ordem : listaOrdensComLembrete) {
			sugestaoReservaMaterialCustom.gravarLembrete(ordem.getOrdemProducao(), ordem.getLembreteSugestao());
		}
	}
	
	public void gravarObservacaoOP(List<OrdemProducao> listaOrdensComObservacao) {
		for (OrdemProducao ordem : listaOrdensComObservacao) {
			ordemProducaoService.gravarObservacao(ordem.getOrdemProducao(), ordem.getObservacao());
		}
	}
	
	public void gravarConfigArtigos(int coluna, String descricao, int meta, String artigos, int metaMinutos) {
		sugestaoReservaMaterialCustom.gravarConfigArtigos(coluna, descricao, meta, artigos, metaMinutos);
	}
		
	public List<SugestaoReservaConfigArtigos> findListConfigArtigos() {	
		List<SugestaoReservaConfigArtigos> configArtigos = sugestaoReservaMaterialCustom.findConfigArtigos();
		for (SugestaoReservaConfigArtigos configuracao : configArtigos) {
			List<ArtigoProduto> artigos = produtoCustom.findArtigosProdutoByCodigos(configuracao.getArtigos());
			List<ConteudoChaveNumerica> listaArtigos = new ArrayList<ConteudoChaveNumerica>();			
			for (ArtigoProduto artigo : artigos) {
				listaArtigos.add(new ConteudoChaveNumerica(artigo.getId(), artigo.getId() + " - " + artigo.getDescricao()));
			}
			configuracao.setListaArtigos(listaArtigos);
		}
		return configArtigos;
	}
	
	public BodySugestaoReservaMateriais findQtdePecasLiberadasDiaPorArtigos(Date dataInicial, Date dataFinal) {
		
		int nrDiasConsulta = FormataData.getDiffInDays(dataInicial, dataFinal) + 1;		
				
	    long diffInMilliseconds = dataFinal.getTime() - dataInicial.getTime();
	    int diffInDays = (int) TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
		
		List<SugestaoReservaConfigArtigos> artigos = findListConfigArtigos();
		int[] qtdesProduzidas = findQtdePecasLiberadasDiaPorArtigo(dataInicial, dataFinal);
		double[] minutosProduzidos = findQtdeMinutosLiberadosDiaPorArtigo(dataInicial, dataFinal);
		
		BodySugestaoReservaMateriais bodyRetorno = new BodySugestaoReservaMateriais(); 
		
		bodyRetorno.qtdeFlatProduzida = findQtdeFlatPecasLiberadasDia(dataInicial, dataFinal);
		bodyRetorno.qtdeOutros = qtdesProduzidas[0];
		bodyRetorno.qtdeProduzida1 = qtdesProduzidas[1];
		bodyRetorno.qtdeProduzida2 = qtdesProduzidas[2];
		bodyRetorno.qtdeProduzida3 = qtdesProduzidas[3];
		bodyRetorno.qtdeProduzida4 = qtdesProduzidas[4];
		bodyRetorno.qtdeProduzida5 = qtdesProduzidas[5];
		bodyRetorno.qtdeProduzida6 = qtdesProduzidas[6];
		bodyRetorno.qtdeProduzida7 = qtdesProduzidas[7];
		bodyRetorno.qtdeProduzida8 = qtdesProduzidas[8];
		bodyRetorno.qtdeProduzida9 = qtdesProduzidas[9];
		
		bodyRetorno.minutosFlatProduzidos = findQtdeMinutosFlatLiberadasDia(dataInicial, dataFinal);
		bodyRetorno.minutosOutros = minutosProduzidos[0];		
		bodyRetorno.minutosProduzidos1 = minutosProduzidos[1];
		bodyRetorno.minutosProduzidos2 = minutosProduzidos[2];
		bodyRetorno.minutosProduzidos3 = minutosProduzidos[3];
		bodyRetorno.minutosProduzidos4 = minutosProduzidos[4];
		bodyRetorno.minutosProduzidos5 = minutosProduzidos[5];
		bodyRetorno.minutosProduzidos6 = minutosProduzidos[6];
		bodyRetorno.minutosProduzidos7 = minutosProduzidos[7];
		bodyRetorno.minutosProduzidos8 = minutosProduzidos[8];
		bodyRetorno.minutosProduzidos9 = minutosProduzidos[9];
		
		for (SugestaoReservaConfigArtigos artigo : artigos) {
			if (artigo.getColuna() == 1) {
				bodyRetorno.descricao1 = artigo.getDescricao();
				bodyRetorno.meta1 = artigo.getMeta() * nrDiasConsulta;
				bodyRetorno.metaMinutos1 = artigo.getMetaMinutos() * nrDiasConsulta;
			} else if (artigo.getColuna() == 2) {
				bodyRetorno.descricao2 = artigo.getDescricao();
				bodyRetorno.meta2 = artigo.getMeta() * nrDiasConsulta;
				bodyRetorno.metaMinutos2 = artigo.getMetaMinutos() * nrDiasConsulta;
			} else if (artigo.getColuna() == 3) {
				bodyRetorno.descricao3 = artigo.getDescricao();
				bodyRetorno.meta3 = artigo.getMeta() * nrDiasConsulta;
				bodyRetorno.metaMinutos3 = artigo.getMetaMinutos() * nrDiasConsulta;
			} else if (artigo.getColuna() == 4) {
				bodyRetorno.descricao4 = artigo.getDescricao();
				bodyRetorno.meta4 = artigo.getMeta() * nrDiasConsulta;
				bodyRetorno.metaMinutos4 = artigo.getMetaMinutos() * nrDiasConsulta;
			} else if (artigo.getColuna() == 5) {
				bodyRetorno.descricao5 = artigo.getDescricao();
				bodyRetorno.meta5 = artigo.getMeta() * nrDiasConsulta;
				bodyRetorno.metaMinutos5 = artigo.getMetaMinutos() * nrDiasConsulta;
			} else if (artigo.getColuna() == 6) {
				bodyRetorno.descricao6 = artigo.getDescricao();
				bodyRetorno.meta6 = artigo.getMeta() * nrDiasConsulta;
				bodyRetorno.metaMinutos6 = artigo.getMetaMinutos() * nrDiasConsulta;
			} else if (artigo.getColuna() == 7) {
				bodyRetorno.descricao7 = artigo.getDescricao();
				bodyRetorno.meta7 = artigo.getMeta() * nrDiasConsulta;
				bodyRetorno.metaMinutos7 = artigo.getMetaMinutos() * nrDiasConsulta;
			} else if (artigo.getColuna() == 8) {
				bodyRetorno.descricao8 = artigo.getDescricao();
				bodyRetorno.meta8 = artigo.getMeta() * nrDiasConsulta;
				bodyRetorno.metaMinutos8 = artigo.getMetaMinutos() * nrDiasConsulta;
			} else if (artigo.getColuna() == 9) {
				bodyRetorno.descricao9 = artigo.getDescricao();
				bodyRetorno.meta9 = artigo.getMeta() * nrDiasConsulta;
				bodyRetorno.metaMinutos9 = artigo.getMetaMinutos() * nrDiasConsulta;
			}
		}
		
		return bodyRetorno;
	}
	
	public BodySugestaoReservaMateriais findConfiguracoesPorArtigos() {
		List<SugestaoReservaConfigArtigos> listaConfigArtigos = findListConfigArtigos();
		BodySugestaoReservaMateriais bodyRetorno = new BodySugestaoReservaMateriais();

		for (SugestaoReservaConfigArtigos configuracao : listaConfigArtigos) {			
			if (configuracao.getColuna() == 1) {
				bodyRetorno.artigos1 = configuracao.getListaArtigos();
				bodyRetorno.descricao1 = configuracao.getDescricao();
				bodyRetorno.meta1 = configuracao.getMeta();
				bodyRetorno.metaMinutos1 = configuracao.getMetaMinutos();
			} else if (configuracao.getColuna() == 2) {
				bodyRetorno.artigos2 = configuracao.getListaArtigos();
				bodyRetorno.descricao2 = configuracao.getDescricao();
				bodyRetorno.meta2 = configuracao.getMeta();
				bodyRetorno.metaMinutos2 = configuracao.getMetaMinutos();
			} else if (configuracao.getColuna() == 3) {
				bodyRetorno.artigos3 = configuracao.getListaArtigos();
				bodyRetorno.descricao3 = configuracao.getDescricao();
				bodyRetorno.meta3 = configuracao.getMeta();
				bodyRetorno.metaMinutos3 = configuracao.getMetaMinutos();
			} else if (configuracao.getColuna() == 4) {
				bodyRetorno.artigos4 = configuracao.getListaArtigos();
				bodyRetorno.descricao4 = configuracao.getDescricao();
				bodyRetorno.meta4 = configuracao.getMeta();
				bodyRetorno.metaMinutos4 = configuracao.getMetaMinutos();
			} else if (configuracao.getColuna() == 5) {
				bodyRetorno.artigos5 = configuracao.getListaArtigos();
				bodyRetorno.descricao5 = configuracao.getDescricao();
				bodyRetorno.meta5 = configuracao.getMeta();
				bodyRetorno.metaMinutos5 = configuracao.getMetaMinutos();
			} else if (configuracao.getColuna() == 6) {
				bodyRetorno.artigos6 = configuracao.getListaArtigos();
				bodyRetorno.descricao6 = configuracao.getDescricao();
				bodyRetorno.meta6 = configuracao.getMeta();
				bodyRetorno.metaMinutos6 = configuracao.getMetaMinutos();
			} else if (configuracao.getColuna() == 7) {
				bodyRetorno.artigos7 = configuracao.getListaArtigos();
				bodyRetorno.descricao7 = configuracao.getDescricao();
				bodyRetorno.meta7 = configuracao.getMeta();
				bodyRetorno.metaMinutos7 = configuracao.getMetaMinutos();
			} else if (configuracao.getColuna() == 8) {
				bodyRetorno.artigos8 = configuracao.getListaArtigos();
				bodyRetorno.descricao8 = configuracao.getDescricao();
				bodyRetorno.meta8 = configuracao.getMeta();
				bodyRetorno.metaMinutos8 = configuracao.getMetaMinutos();
			} else if (configuracao.getColuna() == 9) {
				bodyRetorno.artigos9 = configuracao.getListaArtigos();
				bodyRetorno.descricao9 = configuracao.getDescricao();
				bodyRetorno.meta9 = configuracao.getMeta();
				bodyRetorno.metaMinutos9 = configuracao.getMetaMinutos();
			}
		}
		return bodyRetorno;
	}
}