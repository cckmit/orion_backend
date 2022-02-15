package br.com.live.model;

import java.util.List;

public class SugestaoReservaTecidos {

	public static final int POR_EMBARQUE_ESTAG_CRITICOS_TEMPO_PRODUCAO = 0;
	public static final int POR_ESTAG_CRITICOS_TEMPO_PRODUCAO_EMBARQUE = 1;
	public static final int POR_TEMPO_PRODUCAO_EMBARQUE_ESTAG_CRITICOS = 2;
		
	public List<ConsultaPreOrdemProducao> listaPriorizadaPreOrdens;
	public List<SugestaoReservaPorOrdemTecido> listaSugestaoReservaPorOrdem;
	public List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto;
	public List<SugestaoReservaPorTecido> listaSugestaoReservaPorTecido;
	public List<PreOrdemProducaoItem> listaOrdensItensComQtdesAtendidas;
	
	public SugestaoReservaTecidos(List<ConsultaPreOrdemProducao> listaPriorizadaPreOrdens, List<SugestaoReservaPorOrdemTecido> listaSugestaoReservaPorOrdem, List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto, List<SugestaoReservaPorTecido> listaSugestaoReservaPorTecido, List<PreOrdemProducaoItem> listaOrdensItensComQtdesAtendidas) {
		this.listaPriorizadaPreOrdens = listaPriorizadaPreOrdens;
		this.listaSugestaoReservaPorOrdem = listaSugestaoReservaPorOrdem;
		this.listaSugestaoReservaPorProduto = listaSugestaoReservaPorProduto;
		this.listaSugestaoReservaPorTecido = listaSugestaoReservaPorTecido;
		this.listaOrdensItensComQtdesAtendidas = listaOrdensItensComQtdesAtendidas;
	}
}