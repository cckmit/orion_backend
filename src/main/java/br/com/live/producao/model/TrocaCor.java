package br.com.live.producao.model;

public class TrocaCor {
	public int ordemProducao;
	public String nivel;
	public String grupo;
	public String subGrupo;
	public String item;
	public String descGrupo;
	public String descCor;
	public String narrativa;
	
	public int getOrdemProducao() {
		return ordemProducao;
	}
	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
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
	public String getSubGrupo() {
		return subGrupo;
	}
	public void setSubGrupo(String subGrupo) {
		this.subGrupo = subGrupo;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getDescGrupo() {
		return descGrupo;
	}
	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}
	public String getDescCor() {
		return descCor;
	}
	public void setDescCor(String descCor) {
		this.descCor = descCor;
	}
	public String getNarrativa() {
		return narrativa;
	}
	public void setNarrativa(String narrativa) {
		this.narrativa = narrativa;
	}
	public TrocaCor() {
		
	}
	
	public TrocaCor(int ordemProducao, String nivel, String grupo, String subGrupo, String item, String descGrupo, String descCor, String narrativa) {
		this.ordemProducao = ordemProducao;
		this.nivel = nivel;
		this.grupo = grupo;
		this.subGrupo = subGrupo;
		this.item = item;
		this.descGrupo = descGrupo;
		this.descCor = descCor;
		this.narrativa = narrativa;
	}
}
