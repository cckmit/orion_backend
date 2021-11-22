package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyEngenharia;
import br.com.live.custom.EngenhariaCustom;
import br.com.live.entity.MarcasFio;
import br.com.live.entity.TipoPonto;
import br.com.live.entity.TipoPontoFio;
import br.com.live.entity.TiposFio;
import br.com.live.model.ConsultaTipoPonto;
import br.com.live.model.ConsultaTipoPontoFio;
import br.com.live.model.ConsultaTiposFio;
import br.com.live.model.Maquinas;
import br.com.live.model.OptionProduto;
import br.com.live.repository.MarcasFioRepository;
import br.com.live.repository.TiposFioRepository;
import br.com.live.repository.TiposPontoFioRepository;
import br.com.live.repository.TiposPontoRepository;
import br.com.live.service.EngenhariaService;

@RestController
@CrossOrigin
@RequestMapping("/engenharia")
public class EngenhariaController {
	
	private MarcasFioRepository marcasFioRepository;
	private TiposFioRepository tiposFioRepository; 
	private EngenhariaService engenhariaService;
	private EngenhariaCustom engenhariaCustom;
    private TiposPontoFioRepository tiposPontoFioRepository;
    private TiposPontoRepository tiposPontoRepository;

	@Autowired
	public EngenhariaController(MarcasFioRepository marcasFioRepository, TiposFioRepository tiposFioRepository, EngenhariaService engenhariaService, EngenhariaCustom engenhariaCustom,
    TiposPontoFioRepository tiposPontoFioRepository, TiposPontoRepository tiposPontoRepository) {
		this.marcasFioRepository = marcasFioRepository;
		this.tiposFioRepository = tiposFioRepository;
		this.engenhariaService = engenhariaService;
		this.engenhariaCustom = engenhariaCustom;
        this.tiposPontoRepository = tiposPontoRepository;
        this.tiposPontoFioRepository = tiposPontoFioRepository;
	}
	
	@RequestMapping(value = "/find-all-marcas", method = RequestMethod.GET)
    public List<MarcasFio> findAllMarcas() {
        return marcasFioRepository.findAll();
    }
    
    @RequestMapping(value = "/find-by-id-marcas/{idMarcas}", method = RequestMethod.GET)
    public MarcasFio findByIdMarcas(@PathVariable("idMarcas") int idMarcas) {                  
        return marcasFioRepository.findByIdMarcas(idMarcas);
    }
    
    @RequestMapping(value = "/find-all-tipos", method = RequestMethod.GET)
    public List<ConsultaTiposFio> findAllTipos() {                  
        return engenhariaCustom.findAllTiposFio();
    }
    
    @RequestMapping(value = "/find-by-id-tipos/{idTipos}", method = RequestMethod.GET)
    public TiposFio findByIdTipos(@PathVariable("idTipos") int idMarcas) {                  
        return tiposFioRepository.findByIdTipo(idMarcas);
    }

    @RequestMapping(value = "/find-all-maquinas", method = RequestMethod.GET)
    public List<Maquinas> findAllGruposMaquinas() {                  
        return engenhariaCustom.findAllMaquinas();
    }

    @RequestMapping(value = "/find-all-fios", method = RequestMethod.GET)
    public List<OptionProduto> findAllFios() {                  
        return engenhariaCustom.findAllFios();
    }

    @RequestMapping(value = "/find-all-tipos-ponto", method = RequestMethod.GET)
    public List<TipoPonto> findAllTiposPonto() {                  
        return tiposPontoRepository.findAll();
    }

    @RequestMapping(value = "/find-tipo-ponto-by-idRegistro/{idRegistro}", method = RequestMethod.GET)
    public TipoPontoFio findTipoPontoFioById(@PathVariable("idRegistro") String idRegistro) {                  
        return tiposPontoFioRepository.findByIdTipoPontoFio(idRegistro);
    }

    //
    // Consulta Tipo Ponto By Id
    //
    @RequestMapping(value = "/find-tipo-ponto-by-id/{idTipoPonto}", method = RequestMethod.GET)
    public ConsultaTipoPonto findTipoPontoById(@PathVariable("idTipoPonto") int idTipoPonto) {                  
        return engenhariaCustom.findTipoPonto(idTipoPonto);
    }   

    //
    // Consulta Tipo Ponto (Fio)
    //
    @RequestMapping(value = "/find-tipo-ponto-fio-by-id/{idTipoPonto}", method = RequestMethod.GET)
    public List<ConsultaTipoPontoFio> findTiposPontoFioById(@PathVariable("idTipoPonto") int idTipoPonto) {                  
        return engenhariaCustom.findTipoPontoFio(idTipoPonto);
    }
    
    @RequestMapping(value = "/save-marcas", method = RequestMethod.POST)
    public MarcasFio saveMarcas(@RequestBody BodyEngenharia body) {                  
    	return engenhariaService.saveMarcas(body.id, body.descricao);
    }
    
    @RequestMapping(value = "/save-tipos", method = RequestMethod.POST)
    public TiposFio saveTipos(@RequestBody BodyEngenharia body) {                  
    	return engenhariaService.saveTipos(body.id, body.descricao, body.titulo, body.centimetrosCone);
    }

    //
    // Salva Tipos de Ponto
    //
    @RequestMapping(value = "/save-tipos-ponto", method = RequestMethod.POST)
    public TipoPonto saveTiposPonto(@RequestBody BodyEngenharia body) {                  
    	return engenhariaService.saveTiposPonto(body.id, body.descricao, body.maquina);
    }

    //
    // Salva Tipos de Ponto (Fio)
    //
    @RequestMapping(value = "/save-tipos-ponto-fio", method = RequestMethod.POST)
    public TipoPontoFio saveTiposPontoFio(@RequestBody BodyEngenharia body) {                  
    	return engenhariaService.saveTiposPontoFio(body.idTipoPonto, body.idRegistro, body.tipoFio, body.consumoFio);
    }
    
    @RequestMapping(value = "/delete-marcas/{idMarcas}", method = RequestMethod.DELETE)
    public List<MarcasFio> deleteMarcas(@PathVariable("idMarcas") int idMarcas) {                  
    	engenhariaService.deleteMarcas(idMarcas);
        return marcasFioRepository.findAll();
    }
    
    @RequestMapping(value = "/delete-tipos/{idTipos}", method = RequestMethod.DELETE)
    public List<ConsultaTiposFio> deleteTipos(@PathVariable("idTipos") int idTipos) {                  
    	engenhariaService.deleteTipos(idTipos);
        return engenhariaCustom.findAllTiposFio();
    }

    @RequestMapping(value = "/delete-tipos-ponto/{idTipoPonto}", method = RequestMethod.DELETE)
    public List<TipoPonto> deleteTipoPonto(@PathVariable("idTipoPonto") int idTipoPonto) {                  
    	engenhariaService.deleteTipoPonto(idTipoPonto);
        return tiposPontoRepository.findAll();
    }

    @RequestMapping(value = "/delete-tipos-ponto-fio/{idComposto}/{idTipoPonto}", method = RequestMethod.DELETE)
    public List<ConsultaTipoPontoFio> deleteTipoPonto(@PathVariable("idComposto") String idComposto, @PathVariable("idTipoPonto") int idTipoPonto) {                  
    	engenhariaService.deleteTipoPontoFio(idComposto);
        return engenhariaCustom.findTipoPontoFio(idTipoPonto);
    }

}
