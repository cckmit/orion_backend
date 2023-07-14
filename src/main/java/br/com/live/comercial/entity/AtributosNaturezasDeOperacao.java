package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_com_230")
public class AtributosNaturezasDeOperacao {
	
	@Id
	public int id;
	
	@Column(name = "cod_natureza")
	public int codNatureza;
	
	public String descricao;
	public int venda;
	public int devolucao;
	public int ranking;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCodNatureza() {
		return codNatureza;
	}
	public void setCodNatureza(int codNatureza) {
		this.codNatureza = codNatureza;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getVenda() {
		return venda;
	}
	public void setVenda(int venda) {
		this.venda = venda;
	}
	public int getDevolucao() {
		return devolucao;
	}
	public void setDevolucao(int devolucao) {
		this.devolucao = devolucao;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	
	public AtributosNaturezasDeOperacao() {
		
	}
	public AtributosNaturezasDeOperacao(int id, int codNatureza, String descricao, int venda, int devolucao,
			int ranking) {
		
		this.id = id;
		this.codNatureza = codNatureza;
		this.descricao = descricao;
		this.venda = venda;
		this.devolucao = devolucao;
		this.ranking = ranking;
	}

}
