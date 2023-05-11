package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_fin_050")
public class DevolucaoMostruario {
	
	@Id
	public int id;
	public int representante;
	public String estacao;
	public String nivel;
	public String grupo;
	public String subgrupo;
	public String item;
	public int quantidade;
	
    @Column(name = "tab_col")
    public int tabCol;
    
    @Column(name = "tab_mes")
    public int tabMes;
    
    @Column(name = "tab_seq")
    public int tabSeq;
    
    @Column(name = "preco_unt")
    public float precoUnt;
    
    public float total;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRepresentante() {
		return representante;
	}
	public void setRepresentante(int representante) {
		this.representante = representante;
	}
	public String getEstacao() {
		return estacao;
	}
	public void setEstacao(String estacao) {
		this.estacao = estacao;
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
	public String getSubgrupo() {
		return subgrupo;
	}
	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getTabCol() {
		return tabCol;
	}
	public void setTabCol(int tabCol) {
		this.tabCol = tabCol;
	}
	public int getTabMes() {
		return tabMes;
	}
	public void setTabMes(int tabMes) {
		this.tabMes = tabMes;
	}
	public int getTabSeq() {
		return tabSeq;
	}
	public void setTabSeq(int tabSeq) {
		this.tabSeq = tabSeq;
	}
	public float getPrecoUnt() {
		return precoUnt;
	}
	public void setPrecoUnt(float precoUnt) {
		this.precoUnt = precoUnt;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public DevolucaoMostruario() {
		
	}
	
	public DevolucaoMostruario(int id, int representante, String estacao, String nivel, String grupo, String subgrupo,
			String item, int quantidade, int tabCol, int tabMes, int tabSeq, float precoUnt, float total) {
		
		this.id = id;
		this.representante = representante;
		this.estacao = estacao;
		this.nivel = nivel;
		this.grupo = grupo;
		this.subgrupo = subgrupo;
		this.item = item;
		this.quantidade = quantidade;
		this.tabCol = tabCol;
		this.tabMes = tabMes;
		this.tabSeq = tabSeq;
		this.precoUnt = precoUnt;
		this.total = total;
	}
	
}
