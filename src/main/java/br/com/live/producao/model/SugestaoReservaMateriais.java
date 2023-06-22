package br.com.live.producao.model;

import java.util.List;

public class SugestaoReservaMateriais {

	public List<OrdemProducao> listaPriorizadaOrdens; // Aba por ordem
	public List<SugestaoMaterialDetalhaSortimentos> listaGradeDetPrevistoAtendidoPorSortimento; // Botão grade tamanhos
	public List<SugestaoMaterialDetalhaGradeTamanhos> listaGradeDetPrevistoAtendidoPorTamanho; // Botão grade tamanhos	
	public List<SugestaoReservaPorOrdemMaterial> listaSugestaoReservaTecidosPorOrdem; // Grid tecidos da ordem selecionada
	public List<SugestaoReservaPorOrdemMaterial> listaSugestaoReservaAviamentosPorOrdem; // Grid aviamentos da ordem selecionada
	public List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto; // Aba por produto
	public List<SugestaoReservaPorMaterial> listaSugestaoReservaPorTecido; // Aba por tecido	
	public List<SugestaoReservaPorMaterial> listaSugestaoReservaPorAviamento; // Aba por aviamento
	public List<SugestaoReservaMateriaisReservados> listaDetalhaMateriaisReservados; // quantidades de tecidos reservados (principal + substitutos)	
	
	public SugestaoReservaMateriais(List<OrdemProducao> listaPriorizadaPreOrdens, 
			                        List<SugestaoMaterialDetalhaSortimentos> listaGradeDetPrevistoAtendidoPorSortimento, 
			                        List<SugestaoMaterialDetalhaGradeTamanhos> listaGradeDetPrevistoAtendidoPorTamanho, 
			                        List<SugestaoReservaPorOrdemMaterial> listaSugestaoReservaTecidosPorOrdem, 
			                        List<SugestaoReservaPorOrdemMaterial> listaSugestaoReservaAviamentosPorOrdem,
			                        List<SugestaoReservaPorProduto> listaSugestaoReservaPorProduto, 
			                        List<SugestaoReservaPorMaterial> listaSugestaoReservaPorTecido, 
			                        List<SugestaoReservaPorMaterial> listaSugestaoReservaPorAviamento,
			                        List<SugestaoReservaMateriaisReservados> listaDetalhaMateriaisReservados) {
		// Grid principal de ordens
		this.listaPriorizadaOrdens = listaPriorizadaPreOrdens;
		// Detalhamento de peças atentidas por grade (btn Grade Tamanhos)
		this.listaGradeDetPrevistoAtendidoPorSortimento = listaGradeDetPrevistoAtendidoPorSortimento;
		this.listaGradeDetPrevistoAtendidoPorTamanho = listaGradeDetPrevistoAtendidoPorTamanho;		
		// Utilizada para listar os tecidos da ordem selecionada no grid
		this.listaSugestaoReservaTecidosPorOrdem = listaSugestaoReservaTecidosPorOrdem;
		this.listaSugestaoReservaAviamentosPorOrdem = listaSugestaoReservaAviamentosPorOrdem;
		// Aba Por Produto
		this.listaSugestaoReservaPorProduto = listaSugestaoReservaPorProduto;
		// Aba Por Tecidos 
		this.listaSugestaoReservaPorTecido = listaSugestaoReservaPorTecido;		
		// Aba Por Aviamentos
		this.listaSugestaoReservaPorAviamento = listaSugestaoReservaPorAviamento;
		// Utilizado para identificar os tecidos liberados (grava na tabela do orion)  
		this.listaDetalhaMateriaisReservados = listaDetalhaMateriaisReservados;
	}
}