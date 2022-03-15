package br.com.live.model;

import java.util.List;

public class SugestaoReservaTecidos {

	public static final int POR_EMBARQUE_ESTAG_CRITICOS_TEMPO_PRODUCAO = 0;
	public static final int POR_ESTAG_CRITICOS_TEMPO_PRODUCAO_EMBARQUE = 1;
	public static final int POR_TEMPO_PRODUCAO_EMBARQUE_ESTAG_CRITICOS = 2;
		
	public List<OrdemProducao> listaPriorizadaOrdens;
	public List<SugestaoReservaPorOrdemTecido> listaSugestaoReservaPorOrdem;
	public List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto;
	public List<SugestaoReservaPorTecido> listaSugestaoReservaPorTecido;
	public List<OrdemProducaoItem> listaOrdensItensComQtdesAtendidas;
	
	public SugestaoReservaTecidos(List<OrdemProducao> listaPriorizadaPreOrdens, List<SugestaoReservaPorOrdemTecido> listaSugestaoReservaPorOrdem, List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto, List<SugestaoReservaPorTecido> listaSugestaoReservaPorTecido, List<OrdemProducaoItem> listaOrdensItensComQtdesAtendidas) {
		this.listaPriorizadaOrdens = listaPriorizadaPreOrdens;
		this.listaSugestaoReservaPorOrdem = listaSugestaoReservaPorOrdem;
		this.listaSugestaoReservaPorProduto = listaSugestaoReservaPorProduto;
		this.listaSugestaoReservaPorTecido = listaSugestaoReservaPorTecido;
		// TODO - REVISAR SE AINDA VAI TER A OPÇÃO DE LIBERAR A ORDEM PARCIAL? - ACHO QUE SIM POIS DEVE GERAR NOVA ORDEM COM SALDO RESTANTE.
		this.listaOrdensItensComQtdesAtendidas = listaOrdensItensComQtdesAtendidas;
	}
}