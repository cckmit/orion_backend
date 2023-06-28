package br.com.live.producao.model;

import java.util.List;

public class ConsultaPainelPlanejamentoListas {
	
	public List<ConsultaPainelPlanejamento> listAcabadosPlanejamento;
	public List<ConsultaPainelPlanejamento> listAcabadosDetalharEstoque;
	public List<ConsultaPainelPlanejamento> listAcabadosDetalharCarteira;
	public List<ConsultaPainelPlanejamento> listAcabadosDetalharOrdens;
	
	public List<ConsultaPainelPlanejamento> listMateriaisPlanejamento;
	public List<ConsultaPainelPlanejamento> listMateriaisDetalharEstoque;
	public List<ConsultaPainelPlanejamento> listMateriaisDetalharOrdens;
	public List<ConsultaPainelPlanejamento> listMateriaisDetalharCompras;
	
	
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
	
	public List<ConsultaPainelPlanejamento> getListMateriaisPlanejamento() {
		return listMateriaisPlanejamento;
	}
	public void setListMateriaisPlanejamento(List<ConsultaPainelPlanejamento> listMateriaisPlanejamento) {
		this.listMateriaisPlanejamento = listMateriaisPlanejamento;
	}
	public List<ConsultaPainelPlanejamento> getListMateriaisDetalharEstoque() {
		return listMateriaisDetalharEstoque;
	}
	public void setListMateriaisDetalharEstoque(List<ConsultaPainelPlanejamento> listMateriaisDetalharEstoque) {
		this.listMateriaisDetalharEstoque = listMateriaisDetalharEstoque;
	}
	public List<ConsultaPainelPlanejamento> getListMateriaisDetalharOrdens() {
		return listMateriaisDetalharOrdens;
	}
	public void setListMateriaisDetalharOrdens(List<ConsultaPainelPlanejamento> listMateriaisDetalharOrdens) {
		this.listMateriaisDetalharOrdens = listMateriaisDetalharOrdens;
	}
	public List<ConsultaPainelPlanejamento> getListMateriaisDetalharCompras() {
		return listMateriaisDetalharCompras;
	}
	public void setListMateriaisDetalharCompras(List<ConsultaPainelPlanejamento> listMateriaisDetalharCompras) {
		this.listMateriaisDetalharCompras = listMateriaisDetalharCompras;
	}
	
	public ConsultaPainelPlanejamentoListas() {
		
	}
	public ConsultaPainelPlanejamentoListas(List<ConsultaPainelPlanejamento> listAcabadosPlanejamento, List<ConsultaPainelPlanejamento> listAcabadosDetalharEstoque,
			List<ConsultaPainelPlanejamento> listAcabadosDetalharCarteira,	List<ConsultaPainelPlanejamento> listAcabadosDetalharOrdens,
			List<ConsultaPainelPlanejamento> listMateriaisPlanejamento,	List<ConsultaPainelPlanejamento> listMateriaisDetalharEstoque,
			List<ConsultaPainelPlanejamento> listMateriaisDetalharOrdens, List<ConsultaPainelPlanejamento> listMateriaisDetalharCompras) {
		
		this.listAcabadosPlanejamento = listAcabadosPlanejamento;
		this.listAcabadosDetalharEstoque = listAcabadosDetalharEstoque;
		this.listAcabadosDetalharCarteira = listAcabadosDetalharCarteira;
		this.listAcabadosDetalharOrdens = listAcabadosDetalharOrdens;
		this.listMateriaisPlanejamento = listMateriaisPlanejamento;
		this.listMateriaisDetalharEstoque = listMateriaisDetalharEstoque;
		this.listMateriaisDetalharOrdens = listMateriaisDetalharOrdens;
		this.listMateriaisDetalharCompras = listMateriaisDetalharCompras;
	}
}
