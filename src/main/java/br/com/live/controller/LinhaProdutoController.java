package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.custom.ProdutoCustom;
import br.com.live.model.LinhaProduto;

@RestController
@CrossOrigin
@RequestMapping("/linhasproduto")
public class LinhaProdutoController {

	private ProdutoCustom produtoRepository;

    @Autowired
    public LinhaProdutoController(ProdutoCustom produtoRepository) {
          this.produtoRepository = produtoRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<LinhaProduto> findAll() {
          return produtoRepository.findAllLinhasProdutos();
    }
	
}

