package br.com.live.model;

public class ConsultaRequisicaoTecidosItem {

	private long id;
	private String nivel;
	private String grupo;
	private String sub;
	private String item;
	private String narrativa;
	private int alternativa;
	private int roteiro;
	private double quantidade;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public int getAlternativa() {
		return alternativa;
	}
	public void setAlternativa(int alternativa) {
		this.alternativa = alternativa;
	}
	public int getRoteiro() {
		return roteiro;
	}
	public void setRoteiro(int roteiro) {
		this.roteiro = roteiro;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}	
}
