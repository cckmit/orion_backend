package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.model.TabelaPreco;
import br.com.live.custom.TabelaPrecoCustom;

@RestController
@CrossOrigin
@RequestMapping("/tabelas-preco")
public class TabelaPrecoController {

    private TabelaPrecoCustom tabelaPrecoCustom;

    @Autowired
    public TabelaPrecoController(TabelaPrecoCustom tabelaPrecoCustom) {
          this.tabelaPrecoCustom = tabelaPrecoCustom;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<TabelaPreco> findAll() {
          return tabelaPrecoCustom.findAll();
    }
	
}
