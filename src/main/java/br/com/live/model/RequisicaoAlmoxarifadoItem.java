package br.com.live.model;

public class RequisicaoAlmoxarifadoItem {
	private String id;
	private int sequencia;
	private String nivel;
	private String grupo;
	private String sub;
	private String item;
	private String narrativa;
	private String unidade;
	private int deposito;	
	private String descDeposito;
	private double quantidade;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSequencia() {
		return sequencia;
	}
	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
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
	public String getNarrativa() {
		return narrativa;
	}
	public void setNarrativa(String narrativa) {
		this.narrativa = narrativa;
	}
	public int getDeposito() {
		return deposito;
	}
	public void setDeposito(int deposito) {
		this.deposito = deposito;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public String getDescDeposito() {
		return descDeposito;
	}
	public void setDescDeposito(String descDeposito) {
		this.descDeposito = descDeposito;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}	
}
