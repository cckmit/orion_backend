package br.com.live.produto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.produto.custom.EstoqueProdutoCustom;
import br.com.live.produto.model.Deposito;

@RestController
@CrossOrigin
@RequestMapping("/depositos")
public class DepositoController {

    private EstoqueProdutoCustom estoqueProdutoCustom;

    @Autowired
    public DepositoController(EstoqueProdutoCustom estoqueProdutoCustom) {
          this.estoqueProdutoCustom = estoqueProdutoCustom;
    }
	
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<Deposito> findAllDepositos() {
        return estoqueProdutoCustom.findAllDepositos();
    }    
    
    @RequestMapping(value = "permite-req-almox", method = RequestMethod.GET)
    public List<Deposito> findAllDepositosPermiteReqAlmox() {
    	return estoqueProdutoCustom.findAllDepositosPermiteReqAlmox();
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Deposito> findAllDepositosPecas() {
        return estoqueProdutoCustom.findAllDepositosPecas();
    }
    
    @RequestMapping(value = "tecidos", method = RequestMethod.GET)
    public List<Deposito> findAllDepositosTecidos() {
        return estoqueProdutoCustom.findAllDepositosTecidos();
    }
    
    @RequestMapping(value = "aviamentos", method = RequestMethod.GET)
    public List<Deposito> findAllDepositosAviamentos() {
        return estoqueProdutoCustom.findAllDepositosAviamentos();
    }    
}
