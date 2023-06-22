package br.com.live.produto.model;

import java.util.ArrayList;
import java.util.List;

import br.com.live.util.ConteudoChaveNumerica;

public class Colecao {

	public int id;
    public String descricao;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static List<ConteudoChaveNumerica> parseToConteudoChaveNumerica(List<Colecao> colecoes) {	
		List<ConteudoChaveNumerica> lista = new ArrayList<ConteudoChaveNumerica> ();		
		for (Colecao colecao : colecoes) {
			lista.add(new ConteudoChaveNumerica(colecao.id, colecao.id + " - " + colecao.descricao));
		}		
		return lista;
	}
}
