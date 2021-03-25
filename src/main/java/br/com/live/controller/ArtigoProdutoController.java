package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.custom.ProdutoCustom;
import br.com.live.model.ArtigoProduto;

@RestController
@CrossOrigin
@RequestMapping("/artigosproduto")
public class ArtigoProdutoController {

    private ProdutoCustom produtoRepository;

    @Autowired
    public ArtigoProdutoController(ProdutoCustom produtoRepository) {
          this.produtoRepository = produtoRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ArtigoProduto> findAll() {
          return produtoRepository.findAllArtigosProduto();
    }
	
}

