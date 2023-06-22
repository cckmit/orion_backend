package br.com.live.producao.model;

public class ConsultaItensTamPlanoMestre {

	public long id;
	public long idPlanoMestre;
	public String grupo;	
	public String sub;
	public String item;    
	public int ordem;
	public int qtdeEstoque;
	public int qtdeDemanda;
	public int qtdeProcesso;
	public int qtdeSaldo;
	public int qtdeSugestao;
	public int qtdeEqualizado;
	public int qtdeDiferenca;
	public int qtdeProgramada;
		
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdPlanoMestre() {
		return idPlanoMestre;
	}
	public void setIdPlanoMestre(long idPlanoMestre) {
		this.idPlanoMestre = idPlanoMestre;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public int getQtdeEstoque() {
		return qtdeEstoque;
	}
	public void setQtdeEstoque(int qtdeEstoque) {
		this.qtdeEstoque = qtdeEstoque;
	}
	public int getQtdeDemanda() {
		return qtdeDemanda;
	}
	public void setQtdeDemanda(int qtdeDemanda) {
		this.qtdeDemanda = qtdeDemanda;
	}
	public int getQtdeProcesso() {
		return qtdeProcesso;
	}
	public void setQtdeProcesso(int qtdeProcesso) {
		this.qtdeProcesso = qtdeProcesso;
	}
	public int getQtdeSaldo() {
		return qtdeSaldo;
	}
	public void setQtdeSaldo(int qtdeSaldo) {
		this.qtdeSaldo = qtdeSaldo;
	}
	public int getQtdeSugestao() {
		return qtdeSugestao;
	}
	public void setQtdeSugestao(int qtdeSugestao) {
		this.qtdeSugestao = qtdeSugestao;
	}
	public int getQtdeEqualizado() {
		return qtdeEqualizado;
	}
	public void setQtdeEqualizado(int qtdeEqualizado) {
		this.qtdeEqualizado = qtdeEqualizado;
	}
	public int getQtdeDiferenca() {
		return qtdeDiferenca;
	}
	public void setQtdeDiferenca(int qtdeDiferenca) {
		this.qtdeDiferenca = qtdeDiferenca;
	}
	public int getQtdeProgramada() {
		return qtdeProgramada;
	}
	public void setQtdeProgramada(int qtdeProgramada) {
		this.qtdeProgramada = qtdeProgramada;
	}
			
}