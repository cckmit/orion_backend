package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.model.RetornoValidacaoTabelaPreco;
import br.com.live.model.TabelaPreco;
import br.com.live.service.TabelaPrecoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.body.BodyImportTabPreco;
import br.com.live.custom.TabelaPrecoCustom;

@RestController
@CrossOrigin
@RequestMapping("/tabelas-preco")
public class TabelaPrecoController {

    private TabelaPrecoCustom tabelaPrecoCustom;
    private TabelaPrecoService tabelaPrecoService;

    @Autowired
    public TabelaPrecoController(TabelaPrecoCustom tabelaPrecoCustom, TabelaPrecoService tabelaPrecoService) {
          this.tabelaPrecoCustom = tabelaPrecoCustom;
          this.tabelaPrecoService = tabelaPrecoService;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<TabelaPreco> findAll() {
          return tabelaPrecoCustom.findAll();
    }
    
    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public List<TabelaPreco> findAllTabelas() {
          return tabelaPrecoCustom.findAllTabelas();
    }
    
    @RequestMapping(value = "/find-all-async/{leitor}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllAsyncTabelas(@PathVariable("leitor") String leitor) {
          return tabelaPrecoCustom.findAllTabelasAsync(leitor);
    }
    
    @RequestMapping(value = "/validar-tabela-import", method = RequestMethod.POST)
    public RetornoValidacaoTabelaPreco validarTabPreco(@RequestBody BodyImportTabPreco body) {
          return tabelaPrecoService.validarTabPreco(body.itensTabela);
    }
    
    @RequestMapping(value = "/gravar-tabela-import", method = RequestMethod.POST)
    public void atualizarTabPreco(@RequestBody BodyImportTabPreco body) {
          tabelaPrecoService.atualizarPrecoTabela(body.itensTabela, body.tabelaPreco, body.nivelImportacao);
    }
}
