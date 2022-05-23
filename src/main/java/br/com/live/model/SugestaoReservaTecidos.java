package br.com.live.model;

import java.util.List;

public class SugestaoReservaTecidos {

	public List<OrdemProducao> listaPriorizadaOrdens;
	public List<SugestaoTecidoDetalhaSortimentos> listaGradeDetPrevistoAtendidoPorSortimento;
	public List<SugestaoTecidoDetalhaGradeTamanhos> listaGradeDetPrevistoAtendidoPorTamanho;
	
	public List<SugestaoReservaPorOrdemTecido> listaSugestaoReservaPorOrdem;
	public List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto;
	public List<SugestaoReservaPorTecido> listaSugestaoReservaPorTecido;	
	public List<SugestaoReservaTecidosReservados> listaDetalhaTecidosReservados;
	
	public SugestaoReservaTecidos(List<OrdemProducao> listaPriorizadaPreOrdens, List<SugestaoTecidoDetalhaSortimentos> listaGradeDetPrevistoAtendidoPorSortimento, List<SugestaoTecidoDetalhaGradeTamanhos> listaGradeDetPrevistoAtendidoPorTamanho, List<SugestaoReservaPorOrdemTecido> listaSugestaoReservaPorOrdem, List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto, List<SugestaoReservaPorTecido> listaSugestaoReservaPorTecido, List<SugestaoReservaTecidosReservados> listaDetalhaTecidosReservados) {
		this.listaPriorizadaOrdens = listaPriorizadaPreOrdens;
		this.listaGradeDetPrevistoAtendidoPorSortimento = listaGradeDetPrevistoAtendidoPorSortimento;
		this.listaGradeDetPrevistoAtendidoPorTamanho = listaGradeDetPrevistoAtendidoPorTamanho;
		this.listaSugestaoReservaPorOrdem = listaSugestaoReservaPorOrdem;
		this.listaSugestaoReservaPorProduto = listaSugestaoReservaPorProduto;
		this.listaSugestaoReservaPorTecido = listaSugestaoReservaPorTecido;
		this.listaDetalhaTecidosReservados = listaDetalhaTecidosReservados;
	}
}