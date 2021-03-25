package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.ItemSugCancelProducao;
import br.com.live.repository.ItemSugCancelProducaoRepository;
import br.com.live.util.ParametrosItemSugCancelProd;

@RestController
@CrossOrigin
@RequestMapping("/item-sugestao-cancel")
public class ItemSugCancelProducaoController {

    private ItemSugCancelProducaoRepository itemSugCancelProducaoRepository;

    @Autowired
    public ItemSugCancelProducaoController(ItemSugCancelProducaoRepository itemSugCancelProducaoRepository) {
          this.itemSugCancelProducaoRepository = itemSugCancelProducaoRepository;
    }
	
	@RequestMapping(value = "/sugerir", method = RequestMethod.POST)	
	public List<ItemSugCancelProducao> sugerir(@RequestBody ParametrosItemSugCancelProd parametro) {		
		List<ItemSugCancelProducao> itens = parametro.getItensSelecionados();
        itemSugCancelProducaoRepository.saveAll(itens);				
		return itemSugCancelProducaoRepository.findAll();
	}

	@RequestMapping(value = "/remover", method = RequestMethod.POST)	
	public List<ItemSugCancelProducao> remover(@RequestBody ParametrosItemSugCancelProd parametro) {		
		List<ItemSugCancelProducao> itens = parametro.getItensSelecionados();			
        itemSugCancelProducaoRepository.deleteAll(itens);				
		return itemSugCancelProducaoRepository.findAll();
	}
}
