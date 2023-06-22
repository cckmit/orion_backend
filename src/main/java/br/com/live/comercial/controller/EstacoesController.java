package br.com.live.comercial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.comercial.body.BodyAgrupador;
import br.com.live.comercial.body.BodyEstacao;
import br.com.live.comercial.body.BodyMetas;
import br.com.live.comercial.body.BodyMetasCategoria;
import br.com.live.comercial.body.BodyMetasRepresentante;
import br.com.live.comercial.custom.EstacaoCustom;
import br.com.live.comercial.entity.Agrupador;
import br.com.live.comercial.entity.Estacao;
import br.com.live.comercial.entity.MetasCategoria;
import br.com.live.comercial.entity.MetasDaEstacao;
import br.com.live.comercial.entity.MetasPorRepresentante;
import br.com.live.comercial.model.ConsultaColecoesAgrupador;
import br.com.live.comercial.model.ConsultaEstacaoAgrupadores;
import br.com.live.comercial.model.ConsultaEstacaoTabelaPreco;
import br.com.live.comercial.model.ConsultaMetasCategoria;
import br.com.live.comercial.model.TabelaPreco;
import br.com.live.comercial.repository.AgrupadorRepository;
import br.com.live.comercial.repository.EstacaoRepository;
import br.com.live.comercial.repository.MetasCategoriaRepository;
import br.com.live.comercial.repository.MetasDaEstacaoRepository;
import br.com.live.comercial.repository.MetasPorRepresentanteRepository;
import br.com.live.comercial.service.EstacaoService;
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
	private AgrupadorRepository agrupadorRepository;
	private MetasCategoriaRepository metasCategoriaRepository;
	
    @Autowired
    public EstacoesController(EstacaoRepository estacaoRepository, EstacaoService estacaoService, MetasDaEstacaoRepository metasDaEstacaoRepository, 
    		MetasPorRepresentanteRepository metasPorRepresentanteRepository, EstacaoCustom estacaoCustom, AgrupadorRepository agrupadorRepository, MetasCategoriaRepository metasCategoriaRepository) {
    	this.estacaoRepository = estacaoRepository;
    	this.estacaoService = estacaoService;
    	this.metasDaEstacaoRepository = metasDaEstacaoRepository;
    	this.metasPorRepresentanteRepository = metasPorRepresentanteRepository;
    	this.estacaoCustom = estacaoCustom;
    	this.agrupadorRepository = agrupadorRepository;
    	this.metasCategoriaRepository = metasCategoriaRepository;
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public List<Estacao> findAllEstacao() {                  
        return estacaoRepository.findAll();
    }
    
    @RequestMapping(value = "/find-by-codigo/{codEstacao}", method = RequestMethod.GET)
    public Estacao findByCodEstacao(@PathVariable("codEstacao") long codEstacao) {                  
        return estacaoRepository.findByCodEstacao(codEstacao);
    }
    
    @RequestMapping(value = "/find-representantes", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findRepresentantes() {                  
        return estacaoCustom.findRepresentantes();
    }
    
    @RequestMapping(value = "/find-meta-representante-by-idMeta/{idMetas}", method = RequestMethod.GET)
    public MetasPorRepresentante findRepresentantes(@PathVariable("idMetas") String idMetas) {                  
        return metasPorRepresentanteRepository.findByIdMetas(idMetas);
    }
    
    @RequestMapping(value = "/find-meta-venda-by-idMeta/{idMetas}", method = RequestMethod.GET)
    public MetasDaEstacao findVendas(@PathVariable("idMetas") String idMetas) {      
        return metasDaEstacaoRepository.findByIdMetas(idMetas);
    }
    
    @RequestMapping(value = "/save-estacao", method = RequestMethod.POST)
    public Estacao saveEstacao(@RequestBody BodyEstacao body) {                  
    	return estacaoService.saveEstacao(body.codEstacao,body.descricao,body.catalogo);
    }
    
    @RequestMapping(value = "/find-metas-vendas/{codEstacao}/{tipoMeta}", method = RequestMethod.GET)
    public List<MetasDaEstacao> findMetasVendas(@PathVariable("codEstacao") long codEstacao, @PathVariable("tipoMeta") int tipoMeta) {                  
        return metasDaEstacaoRepository.findByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);
    }
    
    @RequestMapping(value = "/find-metas-representante/{codEstacao}/{tipoMeta}", method = RequestMethod.GET)
    public List<MetasPorRepresentante> findMetasRepresentante(@PathVariable("codEstacao") long codEstacao, @PathVariable("tipoMeta") int tipoMeta) {                  
        return metasPorRepresentanteRepository.findByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);
    }
    
    @RequestMapping(value = "/copiar-representantes/{codEstacao}/{tipoMeta}", method = RequestMethod.GET)
    public List<MetasPorRepresentante> copiarRepresentantes(@PathVariable("codEstacao") long codEstacao, @PathVariable("tipoMeta") int tipoMeta) {                  
    	estacaoService.copiarRepresentantes(codEstacao, tipoMeta);
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
    public List<MetasDaEstacao> deleteMetas(@PathVariable("codEstacao") long codEstacao, @PathVariable("tipoMeta") int tipoMeta, @PathVariable("idMeta") String idMeta) {                  
        estacaoService.excluirMetas(idMeta);
        return metasDaEstacaoRepository.findByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);
    }
    
    @RequestMapping(value = "/delete-metas-representante/{codEstacao}/{tipoMeta}/{idMeta}", method = RequestMethod.DELETE)
    public List<MetasPorRepresentante> deleteMetasRepresentante(@PathVariable("codEstacao") long codEstacao, @PathVariable("tipoMeta") int tipoMeta, @PathVariable("idMeta") String idMeta) {                  
        estacaoService.excluirMetasRepresentante(idMeta);
        return metasPorRepresentanteRepository.findByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);
    }
    
    @RequestMapping(value = "/find-tabelas-preco/{codEstacao}", method = RequestMethod.GET)
    public List<ConsultaEstacaoTabelaPreco> findTabelasPreco(@PathVariable("codEstacao") long codEstacao) {                  
        return estacaoCustom.findTabelasPreco(codEstacao);
    }
    
    @RequestMapping(value = "/save-estacao-tabela", method = RequestMethod.POST)
    public void saveMetasRepresentante(@RequestBody BodyEstacao body) {                  
        estacaoService.saveEstacaoTabelasPreco(body.codEstacao, TabelaPreco.getColTabelaPreco(body.codTabelaPreco), TabelaPreco.getMesTabelaPreco(body.codTabelaPreco), TabelaPreco.getSeqTabelaPreco(body.codTabelaPreco));
    }
    
    @RequestMapping(value = "/delete-estacao-tabela/{codEstacao}/{id}", method = RequestMethod.DELETE)
    public List<ConsultaEstacaoTabelaPreco> deleteEstacaoTabelaPreco(@PathVariable("codEstacao") long codEstacao, @PathVariable("id") String id) {                  
        estacaoService.excluirEstacaoTabela(id);
        return estacaoCustom.findTabelasPreco(codEstacao);
    }
    
    @RequestMapping(value = "/find-dados-capa-agrupador/{codAgrupador}", method = RequestMethod.GET)
    public Agrupador findDadosCapaAgrupador(@PathVariable("codAgrupador") int codAgrupador) {                  
        return agrupadorRepository.findByCodAgrupador(codAgrupador);
    }
    
    @RequestMapping(value = "/find-all-agrupadores", method = RequestMethod.GET)
    public List<Agrupador> findDadosCapaAgrupador() {                  
        return agrupadorRepository.findAll();
    }
    
    @RequestMapping(value = "/save-agrupadores", method = RequestMethod.POST)
    public Agrupador saveAgrupadores(@RequestBody BodyAgrupador body) {
    	return estacaoService.saveAgrupador(body.codAgrupador, body.descricao);
    }
    
    @RequestMapping(value = "/find-colecoes-agrupador/{codAgrupador}", method = RequestMethod.GET)
    public List<ConsultaColecoesAgrupador> findColecoesAgrupador(@PathVariable("codAgrupador") int codAgrupador) {                  
        return estacaoCustom.findColecoesGrid(codAgrupador);
    }
    
    @RequestMapping(value = "/save-agrupadores-colecao", method = RequestMethod.POST)
    public List<ConsultaColecoesAgrupador> saveAgrupadorColecao(@RequestBody BodyAgrupador body) {
    	return estacaoService.saveColecaoAgrupador(body.codAgrupador, body.colecao, body.subColecao);
    }
    
    @RequestMapping(value = "/delete-colecao/{codAgrupador}/{id}", method = RequestMethod.DELETE)
    public List<ConsultaColecoesAgrupador> deleteColecao(@PathVariable("codAgrupador") int codAgrupador, @PathVariable("id") String id) {                  
        estacaoService.excluirColecao(id);
        return estacaoCustom.findColecoesGrid(codAgrupador);
    }
    
    @RequestMapping(value = "/delete-agrupador/{codEstacao}/{id}", method = RequestMethod.DELETE)
    public List<ConsultaEstacaoAgrupadores> deleteEstacaoAgrupador(@PathVariable("codEstacao") long codEstacao, @PathVariable("id") String id) {                  
        estacaoService.excluirEstacaoAgrupador(id);
        return estacaoCustom.findAgrupadoresGrid(codEstacao);
    }
    
    @RequestMapping(value = "/delete-estacao/{codEstacao}", method = RequestMethod.DELETE)
    public void deleteEstacao(@PathVariable("codEstacao") long codEstacao) {                  
        estacaoService.excluirEstacao(codEstacao);
    }
    
    @RequestMapping(value = "/delete-agrupador-total/{codAgrupador}", method = RequestMethod.DELETE)
    public void deleteAgrupador(@PathVariable("codAgrupador") int codAgrupador) {                  
        estacaoService.excluirAgrupador(codAgrupador);
    }
    
    @RequestMapping(value = "/find-estacao-agrupador/{codEstacao}", method = RequestMethod.GET)
    public List<ConsultaEstacaoAgrupadores> findEstacaoAgrupador(@PathVariable("codEstacao") int codEstacao) {                  
        return estacaoCustom.findAgrupadoresGrid(codEstacao);
    }
    
    @RequestMapping(value = "/find-desc-catalogo/{codCatalogo}", method = RequestMethod.GET)
    public String findDescCatalogo(@PathVariable("codCatalogo") int codCatalogo) {                  
        return estacaoCustom.findDescCatalogo(codCatalogo);
    }
    
    @RequestMapping(value = "/duplicar-estacao/{codEstacaoAnt}", method = RequestMethod.GET)
    public Estacao duplicarEstacao(@PathVariable("codEstacaoAnt") long codEstacaoAnt) {                  
        return estacaoService.duplicarCadastro(codEstacaoAnt);
    }
    
    @RequestMapping(value = "/save-estacao-agrupadores", method = RequestMethod.POST)
    public List<ConsultaEstacaoAgrupadores> saveEstacaoAgrupador(@RequestBody BodyAgrupador body) {
    	return estacaoService.saveEstacaoAgrupador(body.codEstacao, body.codAgrupador);
    }
    
    @RequestMapping(value = "/save-representantes-metas-categoria", method = RequestMethod.POST)
    public void saveRepresentantesCategoria(@RequestBody BodyMetasCategoria body) {
    	estacaoService.saveMetasCategoria(body.codEstacao, body.codRepresentante, body.tipoMeta);
    }
    
    @RequestMapping(value = "/save-valores-metas-categoria", method = RequestMethod.POST)
    public void saveValoresCategoria(@RequestBody BodyMetasCategoria body) {
    	estacaoService.saveValoresMetasCategoria(body.listMetas);
    }
    
    @RequestMapping(value = "/find-representante-metas-categoria/{codEstacao}/{tipoMeta}", method = RequestMethod.GET)
    public List<MetasCategoria> findRepMetasCategoria(@PathVariable("codEstacao") long codEstacao, @PathVariable("tipoMeta") int tipoMeta) {                  
    	return metasCategoriaRepository.findByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);
    }
    
    @RequestMapping(value = "/delete-metas-categoria/{idMetas}/{codEstacao}/{tipoMeta}", method = RequestMethod.DELETE)
    public List<ConsultaMetasCategoria> deleteMetasCategoria(@PathVariable("idMetas") String idMetas, @PathVariable("codEstacao") long codEstacao, @PathVariable("tipoMeta") int tipoMeta) {                  
        estacaoService.excluirMetaCategoria(idMetas);
        return estacaoService.findMetasCategoriaGrid(codEstacao, tipoMeta);
    }
    
    @RequestMapping(value = "/find-metas-categoria-grid/{codEstacao}/{tipoMeta}", method = RequestMethod.GET)
    public List<ConsultaMetasCategoria> findMetasCategoriaGrid(@PathVariable("codEstacao") long codEstacao, @PathVariable("tipoMeta") int tipoMeta) {                  
    	return estacaoService.findMetasCategoriaGrid(codEstacao, tipoMeta);
    }
}