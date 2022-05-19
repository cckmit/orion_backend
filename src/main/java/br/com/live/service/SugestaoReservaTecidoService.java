package br.com.live.service;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.live.custom.SugestaoReservaTecidoCustom;
import br.com.live.model.OrdemProducao;
import br.com.live.model.Produto;
import br.com.live.model.SugestaoReservaTecidosReservados;
import br.com.live.model.SugestaoReservaTecidos;

@Service
@Transactional
public class SugestaoReservaTecidoService {

	private final SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom;
	private final SugestaoReservaTecidoPorOrdensService sugestaoReservaTecidoPorOrdensService;
	private final OrdemProducaoService ordemProducaoService;

	public SugestaoReservaTecidoService(SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom,
			SugestaoReservaTecidoPorOrdensService sugestaoReservaTecidoPorOrdensService,
			OrdemProducaoService ordemProducaoService) {		
		this.sugestaoReservaTecidoCustom = sugestaoReservaTecidoCustom;
		this.sugestaoReservaTecidoPorOrdensService = sugestaoReservaTecidoPorOrdensService;
		this.ordemProducaoService = ordemProducaoService;
	}

	public List<Produto> findTecidosEmOrdensParaLiberacao() {
		return sugestaoReservaTecidoCustom.findTecidosEmOrdensParaLiberacao();
	}
	
	public List<Produto> findReferenciasEmOrdensParaLiberacao() {
		return sugestaoReservaTecidoCustom.findReferenciasEmOrdensParaLiberacao();				
	}	

	public SugestaoReservaTecidos calcularSugestaoReservaPorOrdem(List<String> camposSelParaPriorizacao, int periodoInicial, int periodoFinal, String embarques, String referencias, String estagios, String artigos, String tecidos, String depositos, boolean isSomenteFlat, boolean isDiretoCostura, int percentualMinimoAtender) {
		return sugestaoReservaTecidoPorOrdensService.calcularSugestaoReserva(camposSelParaPriorizacao, periodoInicial, periodoFinal, embarques, referencias, estagios, artigos, tecidos, depositos, isSomenteFlat, isDiretoCostura, percentualMinimoAtender);		
	}
	
	public int findQtdePecasLiberadasDia(long idUsuario) {
		return ordemProducaoService.findQtdePecasApontadaNoDiaPorEstagioUsuario(1, idUsuario);
	}	
	
	public void liberarProducao(List<OrdemProducao> listaOrdensLiberar, List<SugestaoReservaTecidosReservados> listaTecidosReservar , boolean urgente, long idUsuarioOrion) {		
		if (!urgente) Collections.sort(listaOrdensLiberar);		

		System.out.println("LIBERAR ORDENS DE PRODUÇÃO");
		for (OrdemProducao ordem : listaOrdensLiberar) {
			System.out.println("ORDEM: " + ordem.ordemProducao);
			sugestaoReservaTecidoCustom.excluirTecidosReservadosPorOrdem(ordem.ordemProducao);
			ordemProducaoService.baixarEstagioProducao(ordem.ordemProducao, 1, idUsuarioOrion); // Estagio 1 - Programação
			ordemProducaoService.gravarSeqPrioridadeDia(ordem.ordemProducao, urgente);
		}
		
		System.out.println("GRAVAR QUANTIDADES DE TECIDOS DAS ORDENS LIBERADAS");		
		for (SugestaoReservaTecidosReservados reservar : listaTecidosReservar) {			
			System.out.println("ORDEM: " + reservar.idOrdem  + " - " + reservar.nivelTecido + "." +  reservar.grupoTecido + "." + reservar.subTecido + "." + reservar.itemTecido + " => " + reservar.qtdeReservado);
			sugestaoReservaTecidoCustom.gravarTecidosReservados(reservar.idOrdem, reservar.nivelTecido, reservar.grupoTecido, reservar.subTecido, reservar.itemTecido, reservar.qtdeReservado);
		}
	}		
	
	public void gravarLembrete(List<OrdemProducao> listaOrdensComLembrete) {
		for (OrdemProducao ordem : listaOrdensComLembrete) {
			sugestaoReservaTecidoCustom.gravarLembrete(ordem.getOrdemProducao(), ordem.getLembreteSugestao());
		}
	}
}