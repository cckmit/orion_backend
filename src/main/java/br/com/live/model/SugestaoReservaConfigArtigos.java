package br.com.live.model;

import java.util.List;

import br.com.live.util.ConteudoChaveNumerica;

public class SugestaoReservaConfigArtigos {

	private int coluna;
	private String descricao;
	private int meta;
	private String artigos;
	private List<ConteudoChaveNumerica> listaArtigos;
	
	public SugestaoReservaConfigArtigos() {}
	
	public SugestaoReservaConfigArtigos(int coluna, String descricao, int meta, String artigos) {
		super();
		this.coluna = coluna;
		this.descricao = descricao;
		this.meta = meta;
		this.artigos = artigos;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getMeta() {
		return meta;
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	public String getArtigos() {
		return artigos;
	}

	public void setArtigos(String artigos) {
		this.artigos = artigos;
	}

	public List<ConteudoChaveNumerica> getListaArtigos() {
		return listaArtigos;
	}

	public void setListaArtigos(List<ConteudoChaveNumerica> listaArtigos) {
		this.listaArtigos = listaArtigos;
	}	
}