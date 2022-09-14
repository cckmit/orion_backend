package br.com.live.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyComercial;
import br.com.live.entity.BloqueioTitulosForn;
import br.com.live.model.ConsultaTitulosBloqForn;
import br.com.live.service.ComercialService;

@RestController
@CrossOrigin
@RequestMapping("/comercial")
public class ComercialController {
	
	private ComercialService comercialService;
	
	@Autowired
	public ComercialController(ComercialService comercialService) {
		this.comercialService = comercialService;
	}
	
	@RequestMapping(value = "/save-envio-produtos-e-commerce", method = RequestMethod.POST)
    public void gravarEnvioProdEcommerce(@RequestBody BodyComercial body) {
		comercialService.saveProdutosIntegracaoEcom(body.referencia, body.tamanho, body.cor);
	}
	
	@RequestMapping(value = "/save-bloqueio-fornecedor", method = RequestMethod.POST)
    public void saveBloqueioFornecedor(@RequestBody BodyComercial body) {
		comercialService.saveBloqueio(body.fornecedor, body.motivo, body.editMode);
	}
	
	@RequestMapping(value = "/liberar-bloqueio-fornecedor", method = RequestMethod.POST)
    public List<ConsultaTitulosBloqForn> liberarBloqueioFornecedor(@RequestBody BodyComercial body) {
		comercialService.liberarBloqueio(body.fornecedor);
		return comercialService.findAllFornBloq();
	}
	
	@RequestMapping(value = "/find-all-titulos-bloq", method = RequestMethod.GET)
    public List<ConsultaTitulosBloqForn> findAllFornBloq() {
		return comercialService.findAllFornBloq();
	}
	
	@RequestMapping(value = "/find-fornecedor-bloq/{idForn}", method = RequestMethod.GET)
    public BloqueioTitulosForn findAllFornBloq(@PathVariable("idForn") String idForn) {
		return comercialService.findForncedorBloq(idForn);
	}
}