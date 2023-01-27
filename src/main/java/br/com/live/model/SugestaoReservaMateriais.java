package br.com.live.model;

import java.util.List;

public class SugestaoReservaMateriais {

	public List<OrdemProducao> listaPriorizadaOrdens; // Aba por ordem
	public List<SugestaoMaterialDetalhaSortimentos> listaGradeDetPrevistoAtendidoPorSortimento; // Botão grade tamanhos
	public List<SugestaoMaterialDetalhaGradeTamanhos> listaGradeDetPrevistoAtendidoPorTamanho; // Botão grade tamanhos
	
	public List<SugestaoReservaPorOrdemMaterial> listaSugestaoReservaPorOrdem; // Grid tecidos da ordem selecionada
	public List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto; // Aba por produto
	public List<SugestaoReservaPorMaterial> listaSugestaoReservaPorMaterial; // Aba por tecido	
	public List<SugestaoReservaMateriaisReservados> listaDetalhaMateriaisReservados; // quantidades de tecidos reservados (principal + substitutos)	
	
	public SugestaoReservaMateriais(List<OrdemProducao> listaPriorizadaPreOrdens, List<SugestaoMaterialDetalhaSortimentos> listaGradeDetPrevistoAtendidoPorSortimento, List<SugestaoMaterialDetalhaGradeTamanhos> listaGradeDetPrevistoAtendidoPorTamanho, List<SugestaoReservaPorOrdemMaterial> listaSugestaoReservaPorOrdem, List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto, List<SugestaoReservaPorMaterial> listaSugestaoReservaPorTecido, List<SugestaoReservaMateriaisReservados> listaDetalhaTecidosReservados) {
		this.listaPriorizadaOrdens = listaPriorizadaPreOrdens;
		this.listaGradeDetPrevistoAtendidoPorSortimento = listaGradeDetPrevistoAtendidoPorSortimento;
		this.listaGradeDetPrevistoAtendidoPorTamanho = listaGradeDetPrevistoAtendidoPorTamanho;
		this.listaSugestaoReservaPorOrdem = listaSugestaoReservaPorOrdem;
		this.listaSugestaoReservaPorProduto = listaSugestaoReservaPorProduto;
		this.listaSugestaoReservaPorMaterial = listaSugestaoReservaPorTecido;
		this.listaDetalhaMateriaisReservados = listaDetalhaTecidosReservados;
	}
}