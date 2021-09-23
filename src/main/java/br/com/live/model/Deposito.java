package br.com.live.model;

import java.util.ArrayList;
import java.util.List;

import br.com.live.util.ConteudoChaveNumerica;

public class Deposito {

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
		
	public static List<ConteudoChaveNumerica> parseToConteudoChaveNumerica(List<Deposito> depositos) {	
		List<ConteudoChaveNumerica> lista = new ArrayList<ConteudoChaveNumerica> ();		
		for (Deposito deposito : depositos) {
			lista.add(new ConteudoChaveNumerica(deposito.id, deposito.id + " - " + deposito.descricao));
		}		
		return lista;
	}
}
