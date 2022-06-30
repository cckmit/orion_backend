package br.com.live.model;
import java.util.List;

public class RequisicaoAlmoxarifado {
	private int empresa;
	private String descEmpresa;
	private int divisaoProducao;
	private String descDivisaoProducao;
	private int centroCusto;
	private String descCentroCusto;
	private String observacao;
	private String requisitante;
	private List<RequisicaoAlmoxarifadoItem> listaItens;
	
	public int getEmpresa() {
		return empresa;
	}
	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}
	public String getDescEmpresa() {
		return descEmpresa;
	}
	public void setDescEmpresa(String descEmpresa) {
		this.descEmpresa = descEmpresa;
	}
	public int getDivisaoProducao() {
		return divisaoProducao;
	}
	public void setDivisaoProducao(int divisaoProducao) {
		this.divisaoProducao = divisaoProducao;
	}
	public String getDescDivisaoProducao() {
		return descDivisaoProducao;
	}
	public void setDescDivisaoProducao(String descDivisaoProducao) {
		this.descDivisaoProducao = descDivisaoProducao;
	}
	public int getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(int centroCusto) {
		this.centroCusto = centroCusto;
	}
	public String getDescCentroCusto() {
		return descCentroCusto;
	}
	public void setDescCentroCusto(String descCentroCusto) {
		this.descCentroCusto = descCentroCusto;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public List<RequisicaoAlmoxarifadoItem> getListaItens() {
		return listaItens;
	}
	public void setListaItens(List<RequisicaoAlmoxarifadoItem> listaItens) {
		this.listaItens = listaItens;
	}
	public String getRequisitante() {
		return requisitante;
	}
	public void setRequisitante(String requisitante) {
		this.requisitante = requisitante;
	}	
	
}
