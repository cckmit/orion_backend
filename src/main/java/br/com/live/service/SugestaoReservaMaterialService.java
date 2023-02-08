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
	
	public int findQtdePecasLiberadasDia(long idUsuario) {
		return ordemProducaoService.findQtdePecasApontadaNoDiaPorEstagioUsuario(2, idUsuario);
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
	
	public void gravarConfigArtigos(int coluna, String descricao, int meta, String artigos) {
		sugestaoReservaMaterialCustom.gravarConfigArtigos(coluna, descricao, meta, artigos);
	}
		
	public List<SugestaoReservaConfigArtigos> findConfigArtigos() {	
		List<SugestaoReservaConfigArtigos> configArtigos = sugestaoReservaMaterialCustom.findConfigArtigos();
		for (SugestaoReservaConfigArtigos configuracao : configArtigos) {
			
			System.out.println(configuracao.getArtigos());
			
			List<ArtigoProduto> artigos = produtoCustom.findArtigosProdutoByCodigos(configuracao.getArtigos());
			List<ConteudoChaveNumerica> listaArtigos = new ArrayList<ConteudoChaveNumerica>();			
			for (ArtigoProduto artigo : artigos) {
				listaArtigos.add(new ConteudoChaveNumerica(artigo.getId(), artigo.getDescricao()));
			}
			configuracao.setListaArtigos(listaArtigos);
		}
		return configArtigos;
	}
}