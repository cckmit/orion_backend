package br.com.live.comercial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.comercial.body.BodyComissaoOrigemPed;
import br.com.live.comercial.custom.PedidoVendaCustom;
import br.com.live.comercial.model.PedidoVenda;
import br.com.live.comercial.service.PedidoVendaService;
import br.com.live.producao.custom.DemandaProdutoCustom;

@RestController
@CrossOrigin
@RequestMapping("/pedidos")
public class PedidoVendaController {

    private DemandaProdutoCustom demandaProdutoCustom;
    private PedidoVendaCustom pedidoVendaCustom;
    private PedidoVendaService pedidoVendaService;

    @Autowired
    public PedidoVendaController(DemandaProdutoCustom demandaProdutoCustom, PedidoVendaCustom pedidoVendaCustom, PedidoVendaService pedidoVendaService) {
          this.demandaProdutoCustom = demandaProdutoCustom;
          this.pedidoVendaCustom = pedidoVendaCustom;
          this.pedidoVendaService = pedidoVendaService;
    }
	
    @RequestMapping(value = "/{perInicio}/{perFim}", method = RequestMethod.GET)
    public List<PedidoVenda> findByPeriodo(@PathVariable("perInicio") int periodoInicio, @PathVariable("perFim") int periodoFim) {                  
        return demandaProdutoCustom.findPedidosByPeriodo(periodoInicio, periodoFim);
    }
    
    @RequestMapping(value = "/find-origens-pedido", method = RequestMethod.GET)
    public List<PedidoVenda> findOrigensPedido() {              
        return pedidoVendaCustom.findOrigensPedido();
    }
	
    @RequestMapping(value = "/save-origens-pedido", method = RequestMethod.POST)
    public List<PedidoVenda> savePerComissaoOrigensPedido(@RequestBody BodyComissaoOrigemPed body) {            
    	pedidoVendaService.salvarComissaoOrigem(body.percComissao);
    	return pedidoVendaCustom.findOrigensPedido();
    }
    
    @RequestMapping(value = "/find-sug-locked/{empresaSelect}", method = RequestMethod.GET)
    public Integer findSugestaoLocked(@PathVariable("empresaSelect") int empresaSelect) {              
        return pedidoVendaCustom.findSugestaoLocked(empresaSelect);
    }
    
    @RequestMapping(value = "/liberar-sugestao/{numeroSugestao}", method = RequestMethod.GET)
    public void liberarSugestao(@PathVariable("numeroSugestao") int numeroSugestao) {              
        pedidoVendaCustom.liberarSugestao(numeroSugestao);
    }
}
