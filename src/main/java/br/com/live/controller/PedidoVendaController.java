package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.custom.DemandaProdutoCustom;
import br.com.live.model.PedidoVenda;

@RestController
@CrossOrigin
@RequestMapping("/pedidos")
public class PedidoVendaController {

    private DemandaProdutoCustom demandaProdutoCustom;

    @Autowired
    public PedidoVendaController(DemandaProdutoCustom demandaProdutoCustom) {
          this.demandaProdutoCustom = demandaProdutoCustom;
    }
	
    @RequestMapping(value = "/{perInicio}/{perFim}", method = RequestMethod.GET)
    public List<PedidoVenda> findByPeriodo(@PathVariable("perInicio") int periodoInicio, @PathVariable("perFim") int periodoFim) {                  
        return demandaProdutoCustom.findPedidosByPeriodo(periodoInicio, periodoFim);
    }
	
}
