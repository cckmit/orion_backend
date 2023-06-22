package br.com.live.producao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_vi_demanda")
public class DemandaProduto {

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
	public int natureza;

	@Column(name = "numero_controle")
	public int nrInterno;
	public int pedido;
	
	public String embarque;
	
	@Column(name = "situacao_venda")
	public int situacaoVenda;
	
	public int deposito;
	
	public DemandaProduto() {};
	
	public DemandaProduto(int id, String nivel, String grupo, String sub, String item, int quantidade, int periodo,
			int colecao, int linha, int artigo, int artigoCotas, int origem, int permanente, int natureza,
			int nrInterno, int pedido, String embarque, int situacaoVenda, int deposito) {
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
		this.permanente = permanente;
		this.natureza = natureza;
		this.nrInterno = nrInterno;
		this.pedido = pedido;
		this.embarque = embarque;
		this.situacaoVenda = situacaoVenda;
		this.deposito = deposito;
	}

}
