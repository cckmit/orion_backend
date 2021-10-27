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
import br.com.live.body.BodyMetas;
import br.com.live.body.BodyMetasRepresentante;
import br.com.live.custom.EstacaoCustom;
import br.com.live.entity.Estacao;
import br.com.live.entity.MetasDaEstacao;
import br.com.live.entity.MetasPorRepresentante;
import br.com.live.repository.EstacaoRepository;
import br.com.live.repository.MetasDaEstacaoRepository;
import br.com.live.repository.MetasPorRepresentanteRepository;
import br.com.live.service.EstacaoService;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/estacao")
public class EstacoesController {
	
	private EstacaoRepository estacaoRepository;
	private EstacaoService estacaoService;
	private MetasDaEstacaoRepository metasDaEstacaoRepository;
	private MetasPorRepresentanteRepository metasPorRepresentanteRepository;
	private EstacaoCustom estacaoCustom;
	
    @Autowired
    public EstacoesController(EstacaoRepository estacaoRepository, EstacaoService estacaoService, MetasDaEstacaoRepository metasDaEstacaoRepository, 
    		MetasPorRepresentanteRepository metasPorRepresentanteRepository, EstacaoCustom estacaoCustom) {
    	this.estacaoRepository = estacaoRepository;
    	this.estacaoService = estacaoService;
    	this.metasDaEstacaoRepository = metasDaEstacaoRepository;
    	this.metasPorRepresentanteRepository = metasPorRepresentanteRepository;
    	this.estacaoCustom = estacaoCustom;
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public List<Estacao> findAllEstacao() {                  
        return estacaoRepository.findAll();
    }
    
    @RequestMapping(value = "/find-by-codigo/{codEstacao}", method = RequestMethod.GET)
    public Estacao findByCodEstacao(@PathVariable("codEstacao") int codEstacao) {                  
        return estacaoRepository.findByCodEstacao(codEstacao);
    }
    
    @RequestMapping(value = "/find-representantes", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findRepresentantes() {                  
        return estacaoCustom.findRepresentantes();
    }
    
    @RequestMapping(value = "/save-estacao", method = RequestMethod.POST)
    public List<Estacao> saveEstacao(@RequestBody BodyEstacao body) {                  
        estacaoService.saveEstacao(body.codEstacao,body.descricao,body.catalogo);
        return estacaoRepository.findAll();
    }
    
    @RequestMapping(value = "/find-metas-vendas/{codEstacao}/{tipoMeta}", method = RequestMethod.GET)
    public List<MetasDaEstacao> findMetasVendas(@PathVariable("codEstacao") int codEstacao, @PathVariable("tipoMeta") int tipoMeta) {                  
        return metasDaEstacaoRepository.findByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);
    }
    
    @RequestMapping(value = "/find-metas-representante/{codEstacao}/{tipoMeta}", method = RequestMethod.GET)
    public List<MetasPorRepresentante> findMetasRepresentante(@PathVariable("codEstacao") int codEstacao, @PathVariable("tipoMeta") int tipoMeta) {                  
        return metasPorRepresentanteRepository.findByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);
    }
    
    @RequestMapping(value = "/save-metas", method = RequestMethod.POST)
    public void saveMetas(@RequestBody BodyMetas body) {                  
        estacaoService.saveMetas(body.codEstacao, body.mes, body.ano, body.tipoMeta, body.percDist);
    }
    
    @RequestMapping(value = "/save-metas-representante", method = RequestMethod.POST)
    public void saveMetasRepresentante(@RequestBody BodyMetasRepresentante body) {                  
        estacaoService.saveMetasRepresentante(body.codEstacao, body.codRepresentante, body.tipoMeta, body.meta);
    }
    
    @RequestMapping(value = "/delete-metas/{codEstacao}/{tipoMeta}/{idMeta}", method = RequestMethod.DELETE)
    public List<MetasDaEstacao> deleteMetas(@PathVariable("codEstacao") int codEstacao, @PathVariable("tipoMeta") int tipoMeta, @PathVariable("idMeta") String idMeta) {                  
        estacaoService.excluirMetas(idMeta);
        return metasDaEstacaoRepository.findByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);
    }
    
    @RequestMapping(value = "/delete-metas-representante/{codEstacao}/{tipoMeta}/{idMeta}", method = RequestMethod.DELETE)
    public List<MetasPorRepresentante> deleteMetasRepresentante(@PathVariable("codEstacao") int codEstacao, @PathVariable("tipoMeta") int tipoMeta, @PathVariable("idMeta") String idMeta) {                  
        estacaoService.excluirMetasRepresentante(idMeta);
        return metasPorRepresentanteRepository.findByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);
    }
}