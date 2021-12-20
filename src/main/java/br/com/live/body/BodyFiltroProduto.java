package br.com.live.body;

import java.util.ArrayList;
import java.util.List;

import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodyFiltroProduto  {

	public List<ConteudoChaveNumerica> colecoes ;
	public List<ConteudoChaveNumerica> colecoesPermanentes ;
	public List<ConteudoChaveAlfaNum> referencias ; 	
	public List<ConteudoChaveAlfaNum> cores ;
	
	private String formata(List<ConteudoChaveNumerica> listConteudo) {

		String texto = "";

		for (ConteudoChaveNumerica conteudo : listConteudo) {
			if (texto.equalsIgnoreCase(""))
				texto = "(" + Integer.toString(conteudo.value);
			else
				texto = texto + "," + Integer.toString(conteudo.value);
		}

		if (!texto.equalsIgnoreCase(""))
			texto = texto + ")";

		return texto;
	}

	private String formataString(List<ConteudoChaveAlfaNum> listConteudo) {

		String texto = "";

		for (ConteudoChaveAlfaNum conteudo : listConteudo) {
			if (texto.equalsIgnoreCase(""))
				texto = "('" + conteudo.value + "'";
			else
				texto = texto + ",'" + conteudo.value +"'";
		}

		if (!texto.equalsIgnoreCase(""))
			texto = texto + ")";

		return texto;
	}
	
	public List<ConteudoChaveNumerica> mergeColecoes() {
		List<ConteudoChaveNumerica> agrupColecoes = new ArrayList<ConteudoChaveNumerica>();
		
		for (ConteudoChaveNumerica conteudo : colecoes) {		
			agrupColecoes.add(conteudo);			
		}
		
		for (ConteudoChaveNumerica conteudo : colecoesPermanentes) {		
			agrupColecoes.add(conteudo);			
		}
		
		return agrupColecoes;
	}
	
	public String getColecoes() {
		return formata(mergeColecoes());
	}
	
	public String getSubColecoes() {
		return formata(this.colecoes);
	}
	
	public String getReferencias() {
		return formataString(this.referencias);
	}

	public String getCores() {
		return formataString(this.cores);
	}
	
}
