package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyDebitoCreditoPorRepresentante;
import br.com.live.custom.DebitoCreditoPorRepresentanteCustom;
import br.com.live.model.ConsultaDebCredPorRepresentante;
import br.com.live.service.DebitoCreditoPorRepresentanteService;
import br.com.live.util.ConteudoChaveAlfaNum;

@RestController
@CrossOrigin
@RequestMapping("/debito-credito-por-representante")
public class DebitoCreditoPorRepresentanteController {
	
	private DebitoCreditoPorRepresentanteService debitoCreditoPorRepresentanteService;
	private DebitoCreditoPorRepresentanteCustom debitoCreditoPorRepresentanteCustom;
	
	@Autowired
	public DebitoCreditoPorRepresentanteController(DebitoCreditoPorRepresentanteService debitoCreditoPorRepresentanteService, 
			DebitoCreditoPorRepresentanteCustom debitoCreditoPorRepresentanteCustom) {
		this.debitoCreditoPorRepresentanteService = debitoCreditoPorRepresentanteService;
		this.debitoCreditoPorRepresentanteCustom = debitoCreditoPorRepresentanteCustom;
	}
	
	@RequestMapping(value = "/find-all-representante/{representante}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllRepresentantes(@PathVariable("representante") String representante) {
        return debitoCreditoPorRepresentanteCustom.findRepresentantes(representante);
    }
	
	@RequestMapping(value = "/find-usuario/{id}", method = RequestMethod.GET)
    public String findUsuarioById(@PathVariable("id") int id) {
        return debitoCreditoPorRepresentanteService.findUsuarioById(id);
    }
	
	@RequestMapping(value = "/find-all-lancamentos", method = RequestMethod.GET)
    public List<ConsultaDebCredPorRepresentante> findAllDebitoCredito() {
        return debitoCreditoPorRepresentanteService.findAllDebitoCredito();
    }
	
	@RequestMapping(value = "/salvar-lancamento", method = RequestMethod.POST)
    public List<ConsultaDebCredPorRepresentante> saveLancamento(@RequestBody BodyDebitoCreditoPorRepresentante body) {                  
    	debitoCreditoPorRepresentanteService.saveLancamento(body.dataLancto, body.mes, body.ano, body.tipo, body.campanha, body.representante, body.observacao, 
    			body.valor, body.usuario);
    	return debitoCreditoPorRepresentanteService.findAllDebitoCredito();
    }
	
	@RequestMapping(value = "/delete-lancamento-by-id/{id}", method = RequestMethod.DELETE)
    public List<ConsultaDebCredPorRepresentante> deleteServicoById(@PathVariable("id") int id) {
        debitoCreditoPorRepresentanteService.deleteById(id);
        return debitoCreditoPorRepresentanteService.findAllDebitoCredito();
    }

}
