package br.com.live.produto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.produto.entity.SubColecao;
import br.com.live.produto.repository.SubColecaoRepository;

@RestController
@CrossOrigin
@RequestMapping("/subcolecoes")
public class SubColecaoController {

    private SubColecaoRepository subColecaoRepository;

    @Autowired
    public SubColecaoController(SubColecaoRepository subColecaoRepository) {
          this.subColecaoRepository = subColecaoRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<SubColecao> findAll() {
          return subColecaoRepository.findAll();
    }
	
}

