package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.service.PainelPlanejamentoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/painel-planejamento")
public class PainelPlanejamentoController {
	
	private PainelPlanejamentoService painelPlanejamentoService;
	
	@Autowired
	public PainelPlanejamentoController(PainelPlanejamentoService painelPlanejamentoService) {
		this.painelPlanejamentoService= painelPlanejamentoService;
	}
	
	@RequestMapping(value = "/find-all-colecao", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllColecaoWithPermanentes() {
          return painelPlanejamentoService.findAllColecaoWithPermanentes();
    }
	
	@RequestMapping(value = "/find-all-sub-colecao", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllSubColecoes() {
          return painelPlanejamentoService.findAllSubColecoes();
    }
	
	@RequestMapping(value = "/find-all-linha-produto", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllLinhaProduto() {
		return painelPlanejamentoService.findAllLinhaProduto();
	}
	
	@RequestMapping(value = "/find-all-artigo", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllArtigo() {
		return painelPlanejamentoService.findAllArtigo();
	}
	
	@RequestMapping(value = "/find-all-conta-estoque", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllContaEstoque() {
		return painelPlanejamentoService.findAllContaEstoque();
	}
	
	@RequestMapping(value = "/find-all-publico-alvo", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllPublicoAlvo() {
		return painelPlanejamentoService.findAllPublicoAlvo();
	}
	
	@RequestMapping(value = "/find-all-segmento", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllSegmento() {
		return painelPlanejamentoService.findAllSegmento();
	}
	
	@RequestMapping(value = "/find-all-faixa-etaria", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllFaixaEtaria() {
		return painelPlanejamentoService.findAllFaixaEtaria();
	}
	
	@RequestMapping(value = "/find-all-complemento", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllComplemento() {
		return painelPlanejamentoService.findAllComplemento();
	}
	
	@RequestMapping(value = "/find-all-depositos", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllDepositos() {
		return painelPlanejamentoService.findAllDepositos();
	}
	
	@RequestMapping(value = "/find-all-periodos-embarque", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllPeriodosEmbarque() {
		return painelPlanejamentoService.findAllPeriodosEmbarque();
	}
	
	@RequestMapping(value = "/find-all-periodos-producao", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllPeriodosProducao() {
		return painelPlanejamentoService.findAllPeriodosProducao();
	}
	
	@RequestMapping(value = "/find-all-periodos-carteira", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllPeriodosCarteira() {
		return painelPlanejamentoService.findAllPeriodosCarteira();
	}
	
	@RequestMapping(value = "/find-all-numero-interno", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllNumInterno() {
		return painelPlanejamentoService.findAllNumInterno();
	}
	
	@RequestMapping(value = "/find-all-artigo-cotas/{artigoCotas}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllArtigoCotas(@PathVariable("artigoCotas") String artigoCotas) {
		return painelPlanejamentoService.findAllArtigoCotas(artigoCotas);
	}

}
