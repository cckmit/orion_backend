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
import br.com.live.entity.ConsumoFiosLinhas;
import br.com.live.entity.ConsumoMetragemFio;
import br.com.live.entity.MarcasFio;
import br.com.live.entity.TipoPonto;
import br.com.live.entity.TipoPontoFio;
import br.com.live.entity.TiposFio;
import br.com.live.model.ConsultaConsumoMetragem;
import br.com.live.model.ConsultaTabelaConsumo;
import br.com.live.model.ConsultaTipoPonto;
import br.com.live.model.ConsultaTipoPontoFio;
import br.com.live.model.ConsultaTiposFio;
import br.com.live.model.Maquinas;
import br.com.live.model.OptionProduto;
import br.com.live.repository.ConsumoFiosLinhasRepository;
import br.com.live.repository.ConsumoMetragemFioRepository;
import br.com.live.repository.MarcasFioRepository;
import br.com.live.repository.TiposFioRepository;
import br.com.live.repository.TiposPontoFioRepository;
import br.com.live.repository.TiposPontoRepository;
import br.com.live.service.EngenhariaService;
import br.com.live.util.ConteudoChaveNumerica;

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
    private ConsumoFiosLinhasRepository consumoFiosLinhasRepository;
    private ConsumoMetragemFioRepository consumoMetragemFioRepository;

	@Autowired
	public EngenhariaController(MarcasFioRepository marcasFioRepository, TiposFioRepository tiposFioRepository, EngenhariaService engenhariaService, EngenhariaCustom engenhariaCustom,
    TiposPontoFioRepository tiposPontoFioRepository, TiposPontoRepository tiposPontoRepository, ConsumoFiosLinhasRepository consumoFiosLinhasRepository, ConsumoMetragemFioRepository consumoMetragemFioRepository) {
		this.marcasFioRepository = marcasFioRepository;
		this.tiposFioRepository = tiposFioRepository;
		this.engenhariaService = engenhariaService;
		this.engenhariaCustom = engenhariaCustom;
        this.tiposPontoRepository = tiposPontoRepository;
        this.tiposPontoFioRepository = tiposPontoFioRepository;
        this.consumoFiosLinhasRepository = consumoFiosLinhasRepository;
        this.consumoMetragemFioRepository = consumoMetragemFioRepository;
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

    @RequestMapping(value = "/find-all-ref-consumo", method = RequestMethod.GET)
    public List<ConsultaTabelaConsumo> findAllReferenciasConsumo() {                  
        return engenhariaCustom.findAllReferenciasSalvas();
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

    //
    // Consulta Tipo de Ponto por Referencia
    //
    @RequestMapping(value = "/find-tipo-ponto-by-ref/{referencia}", method = RequestMethod.GET)
    public List<ConsultaTabelaConsumo> findTfiposPontoFioById(@PathVariable("referencia") String referencia) {                 
        return engenhariaCustom.findConsumoByReferencia(referencia);
    }

    //
    // Get Dados Referência
    //
    @RequestMapping(value = "/get-dados-referencia/{referencia}", method = RequestMethod.GET)
    public OptionProduto getDadosReferencia(@PathVariable("referencia") String referencia) {
        return engenhariaCustom.findReferenciaById(referencia);
    }

    //
    // Consulta Tipo de Ponto por Referencia
    //
    @RequestMapping(value = "/find-consumo-by-id/{idConsumo}", method = RequestMethod.GET)
    public ConsumoFiosLinhas findConsumoById(@PathVariable("idConsumo") String idConsumo) {
        return consumoFiosLinhasRepository.findConsumoById(idConsumo);
    }

    //
    // Consulta Consumo Metragem by ID
    //
    @RequestMapping(value = "/find-consumo-metragem-by-id/{idConsumoMetragem}", method = RequestMethod.GET)
    public ConsumoMetragemFio findConsumoMetragemById(@PathVariable("idConsumoMetragem") String idConsumoMetragem) {
        return consumoMetragemFioRepository.findConsumoMetragemFioById(idConsumoMetragem);
    }

    //
    // Return Option Pacotes
    //
    @RequestMapping(value = "/option-pacotes/{tipoFio}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> returnOptionPacotes(@PathVariable("tipoFio") int tipoFio) {
        return engenhariaService.makeListOptionPackages(tipoFio);
    }

    //
    // Return Option Tipos de Fio
    //
    @RequestMapping(value = "/option-tipos-fio/{idTipoPonto}/{sequencia}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> returnOptionTiposFio(@PathVariable("idTipoPonto") int idTipoPonto, @PathVariable("sequencia") int sequencia) {
        return engenhariaService.makeListOptionTiposFio(idTipoPonto, sequencia);
    }

    //
    // Consulta Resumo por Tipo de Fio
    //
    @RequestMapping(value = "/resumo/{referencia}", method = RequestMethod.GET)
    public List<ConsultaConsumoMetragem> ConsultaResumoPorReferencia(@PathVariable("referencia") String referencia) {
        return engenhariaCustom.ConsultaResumoPorReferencia(referencia);
    }

    @RequestMapping(value = "/save-marcas", method = RequestMethod.POST)
    public MarcasFio saveMarcas(@RequestBody BodyEngenharia body) {                  
    	return engenhariaService.saveMarcas(body.id, body.descricao);
    }
    
    @RequestMapping(value = "/save-tipos", method = RequestMethod.POST)
    public TiposFio saveTipos(@RequestBody BodyEngenharia body) {                  
    	return engenhariaService.saveTipos(body.id, body.descricao, body.titulo, body.centimetrosCone, body.centimetrosCone2, body.centimetrosCone3);
    }

    //
    // Salva Tipos de Ponto
    //
    @RequestMapping(value = "/save-tipos-ponto", method = RequestMethod.POST)
    public TipoPonto saveTiposPonto(@RequestBody BodyEngenharia body) {                  
    	return engenhariaService.saveTiposPonto(body.id, body.descricao);
    }

    //
    // Salva Tipos de Ponto (Fio)
    //
    @RequestMapping(value = "/save-tipos-ponto-fio", method = RequestMethod.POST)
    public TipoPontoFio saveTiposPontoFio(@RequestBody BodyEngenharia body) {                  
    	return engenhariaService.saveTiposPontoFio(body.idTipoPonto, body.idRegistro, body.tipoFio1, body.tipoFio2, body.tipoFio3, body.consumoFio, body.descricao);
    }

    //
    // Salva Consumo
    //
    @RequestMapping(value = "/save-consumo", method = RequestMethod.POST)
    public ConsumoFiosLinhas saveConsumo(@RequestBody BodyEngenharia body) {                  
    	return engenhariaService.saveConsumo(body.idRegistro, body.referencia, body.idTipoPonto, body.comprimentoCostura);
    }

    //
    // Cálcula Consumo Metragem Fio
    //
    @RequestMapping(value = "/calcula-consumo-metragem", method = RequestMethod.POST)
    public List<ConsultaConsumoMetragem> calculaConsumoMetragem(@RequestBody BodyEngenharia body) {                  
    	engenhariaService.CalculaConsumoFio(body.idTipoPonto, body.idRegistro, body.referencia);
        return engenhariaCustom.ConsultaConsumoMetragem(body.idRegistro);
    }

    //
    // Atualizar Pacote
    //
    @RequestMapping(value = "/atualizar-pacote", method = RequestMethod.POST)
    public void atualizarPacote(@RequestBody BodyEngenharia body) {                  
    	engenhariaService.atualizarPacote(body.centimetrosCone, body.idConsumoMet, body.idTipoFio);
        engenhariaService.CalculaConsumoFio(body.idTipoPonto, body.idRegistro, body.referencia);
        //return engenhariaCustom.ConsultaConsumoMetragem(body.idRegistro);
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

    @RequestMapping(value = "/delete-consumo-costura/{idComposto}/{referencia}", method = RequestMethod.DELETE)
    public List<ConsultaTabelaConsumo> deleteConsumoCostura(@PathVariable("idComposto") String idComposto, @PathVariable("referencia") String referencia) {                  
    	engenhariaService.deleteConsumoFiosLinhas(idComposto);
        return engenhariaCustom.findConsumoByReferencia(referencia);
    }
}
