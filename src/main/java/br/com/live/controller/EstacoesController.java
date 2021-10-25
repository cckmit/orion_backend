package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyEstacao;
import br.com.live.entity.Estacao;
import br.com.live.repository.EstacaoRepository;
import br.com.live.service.EstacaoService;

@RestController
@CrossOrigin
@RequestMapping("/estacao")
public class EstacoesController {
	
	private EstacaoRepository estacaoRepository;
	private EstacaoService estacaoService;
	
    @Autowired
    public EstacoesController(EstacaoRepository estacaoRepository, EstacaoService estacaoService) {
    	this.estacaoRepository = estacaoRepository;
    	this.estacaoService = estacaoService;
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public List<Estacao> findAllEstacao() {                  
        return estacaoRepository.findAll();
    }
    
    @RequestMapping(value = "/find-by-codigo/{codEstacao}", method = RequestMethod.GET)
    public Estacao findByCodEstacao(@PathVariable("codEstacao") int codEstacao) {                  
        return estacaoRepository.findByCodEstacao(codEstacao);
    }
    
    @RequestMapping(value = "/save-estacao", method = RequestMethod.POST)
    public void saveEstacao(@RequestBody BodyEstacao body) {                  
        estacaoService.saveEstacao(body.codEstacao,body.descricao,body.catalogo);
    }
}