package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.Colecao;
import br.com.live.repository.ColecaoRepository;

@RestController
@CrossOrigin
@RequestMapping("/colecoes")
public class ColecaoController {

    private ColecaoRepository colecaoRepository;

    @Autowired
    public ColecaoController(ColecaoRepository colecaoRepository) {
          this.colecaoRepository = colecaoRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Colecao> findAll() {
          return colecaoRepository.findAll();
    }
	
}

