package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_vi_estoque")
public class EstoqueProduto {

	@Id
	public int id;
	public String nivel;
	public String grupo;
	public String sub;
	public String item;
	public int deposito;
	public Double quantidade;
	public int colecao;

	@Column(name = "linha_produto")
	public int linha;

	public int artigo;

	@Column(name = "artigo_cotas")
	public int artigoCotas;

	public int origem;
	public int permanente;
	public String embarque;

	public EstoqueProduto(int id, String nivel, String grupo, String sub, String item, int deposito, Double quantidade,
			int colecao, int linha, int artigo, int artigoCotas, int origem, int permanente, String embarque) {
		this.id = id;
		this.nivel = nivel;
		this.grupo = grupo;
		this.sub = sub;
		this.item = item;
		this.deposito = deposito;
		this.quantidade = quantidade;
		this.colecao = colecao;
		this.linha = linha;
		this.artigo = artigo;
		this.artigoCotas = artigoCotas;
		this.origem = origem;
		this.permanente = permanente;
		this.embarque = embarque;
	}

}