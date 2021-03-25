package br.com.live.util;

import java.util.ArrayList;
import java.util.List;

import br.com.live.entity.ItemSugCancelProducao;

public class ParametrosItemSugCancelProd {

	public List<String> selecionados ; 
		
	public List<ItemSugCancelProducao> getItensSelecionados () {
		
		String[] codSeparado ;
		String grupo ;		
		String item ;		
		
		ItemSugCancelProducao itemSugerido ;
		List<ItemSugCancelProducao> itensSugeridos = new ArrayList<ItemSugCancelProducao> ();
		
		for (String selecionado : selecionados) {
				
			codSeparado = selecionado.split("[.]");
			
			grupo = codSeparado[0];			
			item = codSeparado[1];
			
			itemSugerido = new ItemSugCancelProducao("1", grupo, item) ;
			itensSugeridos.add(itemSugerido);
		}
		
		return itensSugeridos;
	}
	
}
