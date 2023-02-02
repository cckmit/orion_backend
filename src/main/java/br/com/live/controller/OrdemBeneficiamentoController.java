package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyOrdemBeneficiamento;
import br.com.live.custom.OrdemBeneficiamentoCustom;
import br.com.live.entity.OrdemBeneficiamentoItem;
import br.com.live.model.OrdemBeneficiamentoItens;
import br.com.live.repository.OrdemBeneficiamentoItemRepository;
import br.com.live.service.OrdemBeneficiamentoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

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
          return ordemBeneficiamentoCustom.findMaquinas(maquina); 
    }
    // Encontrar Referencia
    @RequestMapping(value = "/find-all-produtos/{referencia}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllProdutos(@PathVariable("referencia") String referencia) {
          return ordemBeneficiamentoCustom.findProdutos(referencia); 
    }
    // Encontrar Unidade de Medida
    @RequestMapping(value = "/find-all-unidade-medida", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllUndMedida() {
          return ordemBeneficiamentoCustom.findUndMedida(); 
    }
 // Encontrar Unidade de Medida
    @RequestMapping(value = "/find-all-periodo-producao/{periodoProducao}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllPeriodo(@PathVariable("periodoProducao") int periodoProducao) {
          return ordemBeneficiamentoCustom.findPeriodo(periodoProducao); 
    }
    // Encontrar Todos os Depositos
    @RequestMapping(value = "/find-all-depositos", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllDepositos() {
          return ordemBeneficiamentoCustom.findDepositos(); 
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
    @RequestMapping(value = "/save-item-ordem-beneficiamento", method = RequestMethod.POST)
    public void saveOrdemBeneficiamentoItem(@RequestBody BodyOrdemBeneficiamento body) {                  
    	ordemBeneficiamentoService.saveItemTemporario(body.id, body.usuario, body.produto, body.qtdeRolos, body.qtdeQuilos, body.undMedida, body.larguraTecido, body.gramatura, body.rendimento,
    			body.deposito);
    }
    
    @RequestMapping(value = "/delete-item-ordem/{id}/{usuario}", method = RequestMethod.DELETE)
    public void deleteIdOrdem(@PathVariable("id") String id, @PathVariable("usuario") String usuario) {                  
    	ordemBeneficiamentoService.deleteIdOrdem(id, usuario);
    }
    
    @RequestMapping(value = "/gerar-ordem-beneficiamento", method = RequestMethod.POST)
    public String gerarOrdemBeneficiamento(@RequestBody BodyOrdemBeneficiamento body) {                  
    	return ordemBeneficiamentoService.gerarOrdemBeneficiamento(body.periodoProducao, body.dataPrograma, body.previsaoTermino, body.maquina, body.tipoOrdem, body.usuario);
    }

}
