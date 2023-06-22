package br.com.live.producao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_vi_processo")
public class ProcessoProduto {

	@Id
	public int id;
	public String nivel;
	public String grupo;
	public String sub;
	public String item;
	public int quantidade;
	public int periodo;
	public int colecao;

	@Column(name = "linha_produto")
	public int linha;

	public int artigo;

	@Column(name = "artigo_cotas")
	public int artigoCotas;

	public int origem;
	public int permanente;
	public long embarque;

	public ProcessoProduto(int id, String nivel, String grupo, String sub, String item, int quantidade, int periodo,
			int colecao, int linha, int artigo, int artigoCotas, int origem, int permanente, long embarque) {
		this.id = id;
		this.nivel = nivel;
		this.grupo = grupo;
		this.sub = sub;
		this.item = item;
		this.quantidade = quantidade;
		this.periodo = periodo;
		this.colecao = colecao;
		this.linha = linha;
		this.artigo = artigo;
		this.artigoCotas = artigoCotas;
		this.origem = origem;
		this.embarque = embarque;
	}

}
