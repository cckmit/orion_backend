package br.com.live.body;

import java.util.ArrayList;
import java.util.List;

import br.com.live.entity.ItemSugCancelProducao;

public class BodyParametrosItemSugCancelProd {

	public List<String> selecionados ; 
		
	public List<ItemSugCancelProducao> getItensSelecionados () {
		
		String[] codSeparado ;
		String grupo ;		
		String item ;		
		int colecao ;
		
		ItemSugCancelProducao itemSugerido ;
		List<ItemSugCancelProducao> itensSugeridos = new ArrayList<ItemSugCancelProducao> ();
		
		for (String selecionado : selecionados) {
				
			codSeparado = selecionado.split("[.]");
			
			grupo = codSeparado[0];			
			item = codSeparado[1];
			colecao = Integer.parseInt(codSeparado[2]);
			
			itemSugerido = new ItemSugCancelProducao("1", grupo, item, colecao) ;
			itensSugeridos.add(itemSugerido);
		}
		
		return itensSugeridos;
	}
	
}
