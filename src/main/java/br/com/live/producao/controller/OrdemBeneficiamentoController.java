package br.com.live.producao.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.producao.body.BodyOrdemBeneficiamento;
import br.com.live.producao.custom.OrdemBeneficiamentoCustom;
import br.com.live.producao.entity.OrdemBeneficiamentoItem;
import br.com.live.producao.model.AnaliseQualidade;
import br.com.live.producao.model.OrdemBeneficiamentoItens;
import br.com.live.producao.repository.OrdemBeneficiamentoItemRepository;
import br.com.live.producao.service.OrdemBeneficiamentoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.StatusGravacao;
import net.sf.jasperreports.engine.JRException;

@RestController
@CrossOrigin
@RequestMapping("/beneficiamento")
public class OrdemBeneficiamentoController {
	
	private OrdemBeneficiamentoCustom ordemBeneficiamentoCustom;
	private OrdemBeneficiamentoService ordemBeneficiamentoService;
	private OrdemBeneficiamentoItemRepository ordemBeneficiamentoItemRepository;

    @Autowired
    public OrdemBeneficiamentoController(OrdemBeneficiamentoCustom ordemBeneficiamentoCustom, OrdemBeneficiamentoService ordemBeneficiamentoService,
    		OrdemBeneficiamentoItemRepository ordemBeneficiamentoItemRepository) {
          this.ordemBeneficiamentoCustom = ordemBeneficiamentoCustom;
          this.ordemBeneficiamentoService = ordemBeneficiamentoService;
          this.ordemBeneficiamentoItemRepository = ordemBeneficiamentoItemRepository;
    }
    // Encontrar Equipamentos (Máquinas)
    @RequestMapping(value = "/find-all-maquinas/{maquina}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllMaquina(@PathVariable("maquina") String maquina) {
          return ordemBeneficiamentoCustom.findMaquinas(maquina.toUpperCase()); 
    }
    // Encontrar Referencia
    @RequestMapping(value = "/find-all-produtos/{produto}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllProdutos(@PathVariable("produto") String produto) {
          return ordemBeneficiamentoCustom.findProdutos(produto); 
    }
    // Encontrar Periodo Producao
    @RequestMapping(value = "/find-all-periodo-producao/{periodoProducao}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllPeriodo(@PathVariable("periodoProducao") int periodoProducao) {
          return ordemBeneficiamentoCustom.findPeriodo(periodoProducao); 
    }
    // Encontrar Todos os Depositos
    @RequestMapping(value = "/find-all-depositos", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllDepositos() {
          return ordemBeneficiamentoCustom.findDepositos(); 
    }
    // Encontrar Todas as Ordens de Beneficiamento
    @RequestMapping(value = "/find-all-ordens-beneficiamento/{ordem}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllOrdensBenef(@PathVariable("ordem") int ordem) {
          return ordemBeneficiamentoCustom.findAllOrdensBenef(ordem); 
    }
    // Encontrar Todas a Analise de Qualidade da Ordem Selecionada
    @RequestMapping(value = "/find-analise-ordem/{ordem}", method = RequestMethod.GET)
    public List<AnaliseQualidade> findAnaliseQualidade(@PathVariable("ordem") int ordem) {
          return ordemBeneficiamentoCustom.findAnalise(ordem); 
    }
    // Encontrar Todas a Informações de Lote da Ordem Selecionada
    @RequestMapping(value = "/find-analise-lote-ordem/{ordem}", method = RequestMethod.GET)
    public List<AnaliseQualidade> findInfoLote(@PathVariable("ordem") int ordem) {
          return ordemBeneficiamentoCustom.findInfoLote(ordem); 
    }
    // Encontrar Todas a Informações de Rolos da Ordem Selecionada
    @RequestMapping(value = "/find-analise-rolos-ordem/{ordem}", method = RequestMethod.GET)
    public List<AnaliseQualidade> findInfoRolos(@PathVariable("ordem") int ordem) {
          return ordemBeneficiamentoCustom.findInfoRolos(ordem); 
    }
    // Encontrar Todos os Itens do GRID
    @RequestMapping(value = "/find-all-itens-ordem/{usuario}", method = RequestMethod.GET)
    public List<OrdemBeneficiamentoItens> findAllItensOrdem(@PathVariable("usuario") String usuario) {                  
        return ordemBeneficiamentoCustom.findAllItensOrdens(usuario);
    }
 // Encontrar número de Itens do GRID
    @RequestMapping(value = "/find-count-itens/{usuario}", method = RequestMethod.GET)
    public int findCountItens(@PathVariable("usuario") String usuario) {                  
        return ordemBeneficiamentoCustom.findCountItens(usuario);
    }
    // Encontrar Itens pra edição
    @RequestMapping(value = "/find-by-id-itens/{id}", method = RequestMethod.GET)
    public OrdemBeneficiamentoItem findByIdItens(@PathVariable("id") String id) {                  
        return ordemBeneficiamentoItemRepository.findById(id);
    }
    //
    // Salvar Ordem da Capa
    //
    @RequestMapping(value = "/salvar-item-ordem-beneficiamento", method = RequestMethod.POST)
    public StatusGravacao saveOrdemBeneficiamentoItem(@RequestBody BodyOrdemBeneficiamento body) {                  
    	return ordemBeneficiamentoService.salvarItemOrdem(body.id, body.usuario, body.ordemProducao, body.produto, body.qtdeRolos, body.qtdeQuilos, body.larguraTecido, 
    			body.gramatura, body.rendimento, body.alternativaItem, body.roteiroItem, body.deposito, body.observacao);
    }    
    @RequestMapping(value = "/deletar-item-ordem/{id}/{usuario}", method = RequestMethod.DELETE)
    public void deleteIdOrdem(@PathVariable("id") String id, @PathVariable("usuario") String usuario) {                  
    	ordemBeneficiamentoService.deleteItemOrdem(id, usuario);
    }
    
    @RequestMapping(value = "/gerar-ordem-beneficiamento", method = RequestMethod.POST)
    public List<OrdemBeneficiamentoItens> gerarOrdemBeneficiamento(@RequestBody BodyOrdemBeneficiamento body) {                  
    	return ordemBeneficiamentoService.gerarOrdemBeneficiamento(body.periodoProducao, body.dataPrograma, body.previsaoTermino, body.maquina, body.tipoOrdem, body.usuario);
    }
    
    @RequestMapping(value = "/gerar-pdf-laudo", method = RequestMethod.POST)
    public String gerarPdfLaudo(@RequestBody BodyOrdemBeneficiamento body) throws FileNotFoundException, JRException {
    	return ordemBeneficiamentoService.gerarPdfLaudo(body.ordem);
    }

}
