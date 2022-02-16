package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyExpedicao;
import br.com.live.entity.ParametrosMapaEndereco;
import br.com.live.model.DadosModalEndereco;
import br.com.live.model.Embarque;
import br.com.live.model.EnderecoCount;
import br.com.live.repository.ParametrosMapaEndRepository;
import br.com.live.service.ExpedicaoService;

@RestController
@CrossOrigin
@RequestMapping("/endereco")
public class ExpedicaoController {
	
	private ExpedicaoService enderecoService;
	private ParametrosMapaEndRepository parametrosMapaEndRepository;
	
    @Autowired
    public ExpedicaoController(ExpedicaoService enderecoService, ParametrosMapaEndRepository parametrosMapaEndRepository) {
    	this.enderecoService = enderecoService;
    	this.parametrosMapaEndRepository = parametrosMapaEndRepository;
    }

    @RequestMapping(value = "/find-endereco/{deposito}", method = RequestMethod.GET)
    public List<EnderecoCount> findEnderecoRef(@PathVariable("deposito") int deposito) {
        return enderecoService.findEnderecoRef(deposito);
    }
    
    @RequestMapping(value = "/find-dados-embarque/{numeroTag}", method = RequestMethod.GET)
    public Embarque findEnderecoRef(@PathVariable("numeroTag") String numeroTag) {
        return enderecoService.findGrupoEmbarque(numeroTag);
    }
    
    @RequestMapping(value = "/find-dados-modal-endereco/{deposito}/{endereco}", method = RequestMethod.GET)
    public DadosModalEndereco findDadosModalEnd(@PathVariable("deposito") int deposito, @PathVariable("endereco") String endereco) {
        return enderecoService.findDadosModalEnd(deposito, endereco);
    }
    
    @RequestMapping(value = "/find-parametros/{deposito}", method = RequestMethod.GET)
    public ParametrosMapaEndereco findParametros(@PathVariable("deposito") int deposito) {
        return parametrosMapaEndRepository.findByDeposito(deposito);
    }
    
    @RequestMapping(value = "/gravar-endereco", method = RequestMethod.POST)
    public String findEnderecoRef(@RequestBody BodyExpedicao body) {
    	return enderecoService.gravarEndereco(body.numeroTag, body.endereco);
    }
    
    @RequestMapping(value = "/gravar-parametros", method = RequestMethod.POST)
    public void gravaParametros(@RequestBody BodyExpedicao body) {
    	enderecoService.salvarParametros(body.deposito, body.blocoInicio, body.blocoFim, body.corredorInicio, body.corredorFim, body.boxInicio, body.boxFim, body.cestoInicio, body.cestoFim);
    	enderecoService.gerarEnderecosDinamicos(body.deposito);
    }

}
