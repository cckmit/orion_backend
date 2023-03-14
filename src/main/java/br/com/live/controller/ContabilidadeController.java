package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyContabilidade;
import br.com.live.custom.ContabilidadeCustom;
import br.com.live.model.ConsultaLanctoContabeis;
import br.com.live.model.RetornoLancamentoCont;
import br.com.live.service.ContabilidadeService;


@RestController
@CrossOrigin
@RequestMapping("/contabilidade")
public class ContabilidadeController {
	
	private ContabilidadeService contabilidadeService;
	private ContabilidadeCustom contabilidadeCustom;

    @Autowired
    public ContabilidadeController(ContabilidadeService contabilidadeService, ContabilidadeCustom contabilidadeCustom) {
    	this.contabilidadeService = contabilidadeService;
    	this.contabilidadeCustom = contabilidadeCustom;
          
    }
    
    @RequestMapping(value = "/find-usuario-systextil/{idUsuario}", method = RequestMethod.GET)
    public String findUserSystextil(@PathVariable("idUsuario") int idUsuario) {                  
        return contabilidadeCustom.findUserSystextil(idUsuario);
    }
    
    @RequestMapping(value = "/save-systextil", method = RequestMethod.POST)
    public int salvarSystextil(@RequestBody BodyContabilidade body) {                  
    	return contabilidadeService.salvarSystextil(body.usuario);
    }
    
    @RequestMapping(value = "/find-criticas-by-id/{id}", method = RequestMethod.GET)
    public String findCriticasById(@PathVariable("id") int id) {                  
        return contabilidadeCustom.findCriticasById(id);
    }
    
    @RequestMapping(value = "/importar-lancamentos-contabeis", method = RequestMethod.POST)
    public RetornoLancamentoCont importarLancamentosContabeis(@RequestBody BodyContabilidade body) {                  
    	return contabilidadeService.importarLancamentosContabeis(body.tabImportarLanctoContab, body.usuario, body.datainsercao);
    	 
    }
}