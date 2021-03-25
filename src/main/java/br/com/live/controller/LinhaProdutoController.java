package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.LinhaProduto;
import br.com.live.repository.LinhaProdutoRepository;

@RestController
@CrossOrigin
@RequestMapping("/linhasproduto")
public class LinhaProdutoController {

    private LinhaProdutoRepository linhaProdutoRepository;

    @Autowired
    public LinhaProdutoController(LinhaProdutoRepository linhaProdutoRepository) {
          this.linhaProdutoRepository = linhaProdutoRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<LinhaProduto> findAll() {
          return linhaProdutoRepository.findAll();
    }
	
}

