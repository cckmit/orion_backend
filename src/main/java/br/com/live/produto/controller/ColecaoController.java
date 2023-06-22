package br.com.live.produto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.produto.custom.ProdutoCustom;
import br.com.live.produto.model.Colecao;


@RestController
@CrossOrigin
@RequestMapping("/colecoes")
public class ColecaoController {

	private ProdutoCustom produtoRepository;

    @Autowired
    public ColecaoController(ProdutoCustom produtoRepository) {
          this.produtoRepository = produtoRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Colecao> findAllColecoes() {
          return produtoRepository.findAllColecoes();
    }
    
    @RequestMapping(value = "/permanentes", method = RequestMethod.GET)
    public List<Colecao> findAllColecoesPermanentes() {
          return produtoRepository.findAllColecoesPermanentes();
    }
    
    @RequestMapping(value = "/sub-colecoes", method = RequestMethod.GET)
    public List<Colecao> findAllSubColecoes() {
          return produtoRepository.findAllSubColecoes();
    }
    
    @RequestMapping(value = "/all-colecoes", method = RequestMethod.GET)
    public List<Colecao> findAllColecoesWithPermanentes() {
          return produtoRepository.findAllColecoesWithPermanentes();
    }
    
    @RequestMapping(value = "/colecoes-union", method = RequestMethod.GET)
    public List<Colecao> findColecoesWithSemColecao() {
          return produtoRepository.findColecoesWithSemColecao();
    }
}

