package br.com.live.service;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.live.custom.SugestaoReservaTecidoCustom;
import br.com.live.model.OrdemProducao;
import br.com.live.model.Produto;
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

	public SugestaoReservaTecidos calcularSugestaoReservaPorOrdem(List<String> camposSelParaPriorizacao, int periodoInicial, int periodoFinal, String embarques, String referencias, String estagios, String artigos, String tecidos, String depositos, boolean isSomenteFlat, int percentualMinimoAtender) {
		return sugestaoReservaTecidoPorOrdensService.calcularSugestaoReserva(camposSelParaPriorizacao, periodoInicial, periodoFinal, embarques, referencias, estagios, artigos, tecidos, depositos, isSomenteFlat, percentualMinimoAtender);		
	}
	
	public void liberarProducao(List<OrdemProducao> listaOrdensLiberar, boolean urgente) {		
		if (!urgente) Collections.sort(listaOrdensLiberar);		
		for (OrdemProducao ordem : listaOrdensLiberar) {			
			ordemProducaoService.baixarEstagioProducao(ordem.ordemProducao, 1); // Estagio 1 - Programação
			ordemProducaoService.gravarSeqPrioridadeDia(ordem.ordemProducao, urgente);
		}
	}			
}