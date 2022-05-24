package br.com.live.model;

import java.util.List;

public class SugestaoReservaTecidos {

	public List<OrdemProducao> listaPriorizadaOrdens; // Aba por ordem
	public List<SugestaoTecidoDetalhaSortimentos> listaGradeDetPrevistoAtendidoPorSortimento; // Botão grade tamanhos
	public List<SugestaoTecidoDetalhaGradeTamanhos> listaGradeDetPrevistoAtendidoPorTamanho; // Botão grade tamanhos
	
	public List<SugestaoReservaPorOrdemTecido> listaSugestaoReservaPorOrdem; // Grid tecidos da ordem selecionada
	public List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto; // Aba por produto
	public List<SugestaoReservaPorTecido> listaSugestaoReservaPorTecido; // Aba por tecido	
	public List<SugestaoReservaTecidosReservados> listaDetalhaTecidosReservados; // quantidades de tecidos reservados (principal + substitutos)	
	
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