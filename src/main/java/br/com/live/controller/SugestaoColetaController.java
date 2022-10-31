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
import br.com.live.custom.SugestaoColetaCustom;
import br.com.live.entity.AreaColeta;
import br.com.live.model.SugestaoColeta;
import br.com.live.repository.AreaColetaRepository;
import br.com.live.service.SugestaoColetaService;
import br.com.live.util.StatusPesquisa;

@RestController
@CrossOrigin
@RequestMapping("/sugestao-coleta")
public class SugestaoColetaController {

	private SugestaoColetaService sugestaoColetaService;
	private SugestaoColetaCustom sugestaoColetaCustom; 
	private AreaColetaRepository areaColetaRepository; 	
	
	@Autowired
	public SugestaoColetaController(SugestaoColetaService sugestaoColetaService, SugestaoColetaCustom sugestaoColetaCustom, AreaColetaRepository areaColetaRepository) {
		this.sugestaoColetaService = sugestaoColetaService;
		this.sugestaoColetaCustom = sugestaoColetaCustom;
		this.areaColetaRepository = areaColetaRepository;
	}	
	
    @RequestMapping(value = "/find-pedidos-sugestao-coleta", method = RequestMethod.POST)
    public List<SugestaoColeta> findPedidosSugestaoColeta(@RequestBody BodyExpedicao body) {
    	sugestaoColetaService.cleanLoteColetaNaoLiberadoByUsuario(body.idUsuarioLote);
    	return sugestaoColetaCustom.findPedidosSugestaoColeta(body.dataEmissaoInicio, body.dataEmissaoFim, body.dataEmbarqueInicio, body.dataEmbarqueFim, 
    			body.empresas, body.clientes, body.representantes, body.transportadoras);
    }
        
    @RequestMapping(value = "/find-all-areas-coleta", method = RequestMethod.GET)
    public List<AreaColeta> findAllAreasColetas() {
    	return areaColetaRepository.findAll();
    }      
    
    @RequestMapping(value = "/save-area-coleta", method = RequestMethod.POST)
    public List<AreaColeta> saveAreaColeta(@RequestBody BodyExpedicao body) {    	
    	sugestaoColetaService.saveAreaColeta(body.idAreaColeta, body.descArea, body.endInicioArea, body.endFimArea);
    	return areaColetaRepository.findAll();
    }
    
    @RequestMapping(value = "/delete-area-coleta/{id}", method = RequestMethod.DELETE)
    public List<AreaColeta> deleteAreaColeta(@PathVariable("id") long id) {
    	sugestaoColetaService.deleteAreaColeta(id);
    	return areaColetaRepository.findAll(); 
    }
    
    @RequestMapping(value = "/create-lote-coleta", method = RequestMethod.POST)
    public void createLoteColeta(@RequestBody BodyExpedicao body) {
    	sugestaoColetaService.createLoteColeta(body.idUsuarioLote, body.pedidosLoteSugColeta);
    }
    
    @RequestMapping(value = "/find-sugestao-para-liberar", method = RequestMethod.POST)
    public StatusPesquisa findSugestaoColetaParaLiberarByIdUsuario(long idUsuario) {
    	return sugestaoColetaService.findSugestaoColetaParaLiberarByIdUsuario(idUsuario);
    }
}