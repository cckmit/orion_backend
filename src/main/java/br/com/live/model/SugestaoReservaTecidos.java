package br.com.live.model;

import java.util.List;

public class SugestaoReservaTecidos {

	public static final int POR_EMBARQUE_ESTAG_CRITICOS_TEMPO_PRODUCAO = 0;
	public static final int POR_ESTAG_CRITICOS_TEMPO_PRODUCAO_EMBARQUE = 1;
	public static final int POR_TEMPO_PRODUCAO_EMBARQUE_ESTAG_CRITICOS = 2;
	
	public List<SugestaoReservaPorOrdem> listaSugestaoReservaPorOrdem;
	public List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto;
	public List<SugestaoReservaPorTecido> listaSugestaoReservaPorTecido;
	
	public SugestaoReservaTecidos(List<SugestaoReservaPorOrdem> listaSugestaoReservaPorOrdem, List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto, List<SugestaoReservaPorTecido> listaSugestaoReservaPorTecido) {
		this.listaSugestaoReservaPorOrdem = listaSugestaoReservaPorOrdem;
		this.listaSugestaoReservaPorProduto = listaSugestaoReservaPorProduto;
		this.listaSugestaoReservaPorTecido = listaSugestaoReservaPorTecido;
	}	
}