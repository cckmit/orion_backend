package br.com.live.service;

import br.com.live.custom.PlanoMestreCustom;
import br.com.live.custom.SugestaoReservaTecidoCustom;

public class SugestaoReservaTecidoService {
	
	private final SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom;
	private final PlanoMestreCustom planoMestreCustom;
	
	public SugestaoReservaTecidoService(SugestaoReservaTecidoCustom sugestaoReservaTecidoCustom, PlanoMestreCustom planoMestreCustom) {
		this.sugestaoReservaTecidoCustom = sugestaoReservaTecidoCustom;
		this.planoMestreCustom= planoMestreCustom;
	}	
	
	public void calcularSugestaoReserva(String planosMestres, String embarques, String referencias) {
		
		//List<Consulta> planoMestreCustom.findPreOrdensByPlanosEmbarquesReferencias(planosMestres, embarques, referencias);
		
		// Ler as pré-ordens calculadas sem OP
		// Priorizar as ordens (embarque e tempo produção costura)
		// Identificar as necessidades de tecidos (somando pelas necessidades que aparecem nas ordens)
		// Identificar as quantidade disponível de tecido em estoque
		// Sugerir os tecidos conforme as pré-ordens priorizadas
		
	}
	
}