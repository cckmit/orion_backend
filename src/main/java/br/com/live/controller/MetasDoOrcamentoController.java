package br.com.live.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyMetasOrcamento;
import br.com.live.model.ConsultaMetasOrcamento;
import br.com.live.service.MetasDoOrcamentoService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/metas-orcamento")
public class MetasDoOrcamentoController {
	
	private final MetasDoOrcamentoService metasDoOrcamentoService;
	
	@Autowired
	public MetasDoOrcamentoController(MetasDoOrcamentoService metasDoOrcamentoService) {
		this.metasDoOrcamentoService = metasDoOrcamentoService;
	}
	
	@RequestMapping(value = "/find-dados-grid/{ano}/{tipoMeta}", method = RequestMethod.GET)
    public List<ConsultaMetasOrcamento> findDadosGrid(@PathVariable("ano") int ano, @PathVariable("tipoMeta") int tipoMeta) {      
        return metasDoOrcamentoService.findDadosGrid(ano, tipoMeta);
    }
	
	@RequestMapping(value = "/find-all-total-geral/{tipoMeta}/{ano}", method = RequestMethod.GET)
    public String findtotalGeral(@PathVariable("tipoMeta") int tipoMeta, @PathVariable("ano") int ano) {      
        return metasDoOrcamentoService.findTotalGeral(tipoMeta, ano);
    }

	@RequestMapping(value = "/importar", method = RequestMethod.POST)
	public List<ConsultaMetasOrcamento> importarMetas(@RequestBody BodyMetasOrcamento body) {
		metasDoOrcamentoService.importarMetas(body.listMetas, body.ano, body.tipoMeta);
		return metasDoOrcamentoService.findDadosGrid(body.ano,body.tipoMeta);
	}
}
