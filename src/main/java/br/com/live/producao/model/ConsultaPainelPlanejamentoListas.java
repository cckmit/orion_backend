package br.com.live.producao.model;

import java.util.List;

public class ConsultaPainelPlanejamentoListas {
	
	public List<ConsultaPainelPlanejamento> listAcabadosPlanejamento;
	public List<ConsultaPainelPlanejamento> listAcabadosDetalharEstoque;
	public List<ConsultaPainelPlanejamento> listAcabadosDetalharCarteira;
	public List<ConsultaPainelPlanejamento> listAcabadosDetalharOrdens;
	
	public List<ConsultaPainelPlanejamento> getListAcabadosPlanejamento() {
		return listAcabadosPlanejamento;
	}
	public void setListAcabadosPlanejamento(List<ConsultaPainelPlanejamento> listAcabadosPlanejamento) {
		this.listAcabadosPlanejamento = listAcabadosPlanejamento;
	}
	public List<ConsultaPainelPlanejamento> getListAcabadosDetalharEstoque() {
		return listAcabadosDetalharEstoque;
	}
	public void setListAcabadosDetalharEstoque(List<ConsultaPainelPlanejamento> listAcabadosDetalharEstoque) {
		this.listAcabadosDetalharEstoque = listAcabadosDetalharEstoque;
	}
	public List<ConsultaPainelPlanejamento> getListAcabadosDetalharCarteira() {
		return listAcabadosDetalharCarteira;
	}
	public void setListAcabadosDetalharCarteira(List<ConsultaPainelPlanejamento> listAcabadosDetalharCarteira) {
		this.listAcabadosDetalharCarteira = listAcabadosDetalharCarteira;
	}
	public List<ConsultaPainelPlanejamento> getListAcabadosDetalharOrdens() {
		return listAcabadosDetalharOrdens;
	}
	public void setListAcabadosDetalharOrdens(List<ConsultaPainelPlanejamento> listAcabadosDetalharOrdens) {
		this.listAcabadosDetalharOrdens = listAcabadosDetalharOrdens;
	}
	
	public ConsultaPainelPlanejamentoListas() {
		
	}
	public ConsultaPainelPlanejamentoListas(List<ConsultaPainelPlanejamento> listAcabadosPlanejamento, List<ConsultaPainelPlanejamento> listAcabadosDetalharEstoque,
			List<ConsultaPainelPlanejamento> listAcabadosDetalharCarteira, List<ConsultaPainelPlanejamento> listAcabadosDetalharOrdens) {
		
		this.listAcabadosPlanejamento = listAcabadosPlanejamento;
		this.listAcabadosDetalharEstoque = listAcabadosDetalharEstoque;
		this.listAcabadosDetalharCarteira = listAcabadosDetalharCarteira;
		this.listAcabadosDetalharOrdens = listAcabadosDetalharOrdens;
	}
	
	

	
}
