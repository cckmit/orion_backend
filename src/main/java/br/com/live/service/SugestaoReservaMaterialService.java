package br.com.live.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ProdutoCustom;
import br.com.live.custom.SugestaoReservaMaterialCustom;
import br.com.live.model.ArtigoProduto;
import br.com.live.model.OrdemProducao;
import br.com.live.model.Produto;
import br.com.live.model.SugestaoReservaConfigArtigos;
import br.com.live.model.SugestaoReservaMateriaisReservados;
import br.com.live.util.ConteudoChaveNumerica;
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

	public SugestaoReservaMateriais calcularSugestaoReservaPorOrdem(List<String> camposSelParaPriorizacao, int periodoInicial, int periodoFinal, String embarques, String referencias, String estagios, String artigos, String tecidos, String depositosTecidos, String depositosAviamentos, boolean isSomenteFlat, boolean isDiretoCostura, boolean isOrdensSemTecido, int percentualMinimoAtender, int regraReserva) {
		return sugestaoReservaMaterialPorOrdensService.calcularSugestaoReserva(camposSelParaPriorizacao, periodoInicial, periodoFinal, embarques, referencias, estagios, artigos, tecidos, depositosTecidos, depositosAviamentos, isSomenteFlat, isDiretoCostura, isOrdensSemTecido, percentualMinimoAtender, regraReserva);		
	}
	
	public int findQtdePecasLiberadasDia() {
		return ordemProducaoService.findQtdePecasApontadaNoDiaPorEstagioUsuario(2);
	}	

	public int findQtdeFlatPecasLiberadasDia() {
		return ordemProducaoService.findQtdePecasFlatApontadaNoDia(2);
	}	

	public double findQtdeMinutosFlatLiberadasDia() {
		return ordemProducaoService.findQtdeMinutosFlatApontadaNoDia(2);
	}	
	
	public int[] findQtdePecasLiberadasDiaPorArtigo() {		
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
				
				quantidade = ordemProducaoService.findQtdePecasApontadaNoDiaPorEstagioArtigos(2, true, configuracao.getArtigos());
	
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
		qtdeOutros = ordemProducaoService.findQtdePecasApontadaNoDiaPorEstagioArtigos(2, false, artigosConfig);		
		
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

	public double[] findQtdeMinutosLiberadosDiaPorArtigo() {		
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
				
				quantidade = ordemProducaoService.findQtdeMinutosApontadoNoDiaPorEstagioArtigos(2, true, configuracao.getArtigos());
	
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
		qtdeOutros = ordemProducaoService.findQtdeMinutosApontadoNoDiaPorEstagioArtigos(2, false, artigosConfig);		
		
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
		
	public List<SugestaoReservaConfigArtigos> findConfigArtigos() {	
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
}