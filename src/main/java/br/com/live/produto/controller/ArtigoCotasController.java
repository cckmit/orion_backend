package br.com.live.produto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.produto.custom.ProdutoCustom;
import br.com.live.produto.model.ArtigoCotas;

@RestController
@CrossOrigin
@RequestMapping("/artigoscotas")
public class ArtigoCotasController {

	private ProdutoCustom produtoRepository;	    

    @Autowired
    public ArtigoCotasController(ProdutoCustom produtoRepository) {
          this.produtoRepository = produtoRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ArtigoCotas> findAll() {
          return produtoRepository.findAllArtigosCotas();
    }
	
}
