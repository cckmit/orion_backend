package br.com.live.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.live.custom.SugestaoReservaTecidoCustom;
import br.com.live.model.Produto;
import br.com.live.model.SugestaoReservaTecidos;

@Service
@Transactional
public class SugestaoReservaTecidoService {

	private final SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom;
	private final SugestaoReservaTecidoPorOrdensService sugestaoReservaTecidoPorOrdensService;
	private final SugestaoReservaTecidoPorPreOrdensService sugestaoReservaTecidoPorPreOrdensService;

	public SugestaoReservaTecidoService(SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom,
			SugestaoReservaTecidoPorOrdensService sugestaoReservaTecidoPorOrdensService,
			SugestaoReservaTecidoPorPreOrdensService sugestaoReservaTecidoPorPreOrdensService) {		
		this.sugestaoReservaTecidoCustom = sugestaoReservaTecidoCustom;
		this.sugestaoReservaTecidoPorOrdensService = sugestaoReservaTecidoPorOrdensService;
		this.sugestaoReservaTecidoPorPreOrdensService = sugestaoReservaTecidoPorPreOrdensService;
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
	
	@Deprecated
	public SugestaoReservaTecidos calcularSugestaoReservaPorPreOrdem(String planosMestres, String embarques, String referencias, String depositos, int priorizacao, int percentualMinimoAtender) {
		return sugestaoReservaTecidoPorPreOrdensService.calcularSugestaoReserva(planosMestres, embarques, referencias, depositos, priorizacao, percentualMinimoAtender);		
	}	
}