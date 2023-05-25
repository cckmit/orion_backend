package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyAreaIndicador;
import br.com.live.body.BodyIndicadores;
import br.com.live.custom.IndicadoresCustom;
import br.com.live.entity.AreaIndicador;
import br.com.live.entity.Indicadores;
import br.com.live.entity.IndicadoresMensal;
import br.com.live.entity.ResultadosIndicadorMensal;
import br.com.live.model.ConsultaIndicadores;
import br.com.live.service.IndicadoresService;
import br.com.live.util.ConteudoChaveNumerica;


@RestController
@CrossOrigin
@RequestMapping("/indicadores")
public class IndicadoresController {
	
	private IndicadoresCustom indicadoresCustom;
	private IndicadoresService indicadoresService;
	
	@Autowired
	public IndicadoresController(IndicadoresCustom indicadoresCustom, IndicadoresService indicadoresService) {
		this.indicadoresCustom = indicadoresCustom;
		this.indicadoresService = indicadoresService;
				
	}
	// Carregar os dados do Botão Pesquisar
	@RequestMapping(value = "/find-all-indicadores/{idUsuario}", method = RequestMethod.GET)
	public List<ConsultaIndicadores> findAllIndicadores(@PathVariable("idUsuario") int idUsuario) {
		return indicadoresService.findAllIndicadores(idUsuario);
	}
	// Carregar os dados do Botão Pesquisar
	@RequestMapping(value = "/find-indicador-select/{id}", method = RequestMethod.GET)
	public Indicadores findIndicadorSelect(@PathVariable("id") int id) {
		return indicadoresService.findIndicadorById(id);
	}
	// Carregar todos os Indicadores do Ano selecionado
	@RequestMapping(value = "/find-all-mensal/{ano}/{variaveis}/{idIndicador}", method = RequestMethod.GET)
    public List<IndicadoresMensal> findByAno(@PathVariable("ano") int ano, @PathVariable("variaveis") String variaveis, @PathVariable("idIndicador") int idIndicador) {
        return indicadoresService.insertUpdateTableMensal(ano, variaveis, idIndicador);
    }
	// Carregar todos os Resultados do Ano selecionado
	@RequestMapping(value = "/find-all-mensal-resultado/{ano}/{idIndicador}", method = RequestMethod.GET)
    public List<ResultadosIndicadorMensal> findAllByAno(@PathVariable("ano") int ano, @PathVariable("idIndicador") int idIndicador) {
        return indicadoresService.inserirResultadoMensal(ano, idIndicador);
    }
	// Carregar Área de Indicadores
	@RequestMapping(value = "/find-all-area/{tipo}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllArea(@PathVariable("tipo") int tipo) {
        return indicadoresCustom.findArea(tipo);
	}
	// Carregar Tipos de Pré Cadastro de Indicadores
	@RequestMapping(value = "/find-area/{tipo}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAreaByTipo(@PathVariable("tipo") int tipo) {
        return indicadoresCustom.findAreaByTipo(tipo);
	}
	// Carregar Setor de Indicadores
	@RequestMapping(value = "/find-all-usuarios", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllUsuarios() {
        return indicadoresCustom.findUsuarios();
	}
    //
    // Salvar Novos Valores Mensal de Indicador
    //
    @RequestMapping(value = "/gravar-valores-mensal", method = RequestMethod.POST)
    public List<IndicadoresMensal> gravarValoresMensal(@RequestBody BodyIndicadores body) {                  
    	return indicadoresService.aplicarFormulaMensal(body.idIndicador, body.valoresMensal);
    }    
    //
    // Salva Novo Indicador
    //
    @RequestMapping(value = "/save-indicador", method = RequestMethod.POST)
    public List<ConsultaIndicadores> saveIndicadores(@RequestBody BodyIndicadores body) {
    	return indicadoresService.saveIndicador(body.id, body.nomeIndicador, body.grupoIndicador, body.area, body.departamento, body.setor, body.gestorAvaliado,
    			body.unidadeMedida, body.frequenciaMonitoramento, body.fonteDados, body.polaridade, body.responsavelRegistro, body.responsavelPublicacao,
    			body.observacao, body.formulaCalculo, body.variaveis, body.idUsuario, body.situacao);
    }
    // Deletar Indicador
    @RequestMapping(value = "/delete-indicador/{idIndicador}/{idUsuario}", method = RequestMethod.DELETE)
    public List<ConsultaIndicadores> deleteIndicador(@PathVariable("idIndicador") int idIndicador, @PathVariable("idUsuario") int idUsuario) {                  
    	return indicadoresService.deleteIndicador(idIndicador, idUsuario);
    }
    //
    // Salva Novo GRUPO, ÁREA, SETOR, DEPARTAMENTO, UNIDADE MEDIDA 
    //
    @RequestMapping(value = "/save-area-indicador", method = RequestMethod.POST)
    public AreaIndicador saveAreaIndicador(@RequestBody BodyAreaIndicador body) {
    	return indicadoresService.saveAreaIndicador(body.tipo, body.sequencia, body.descricao);
    }
 // Deletar Indicador
    @RequestMapping(value = "/delete-area/{tipo}/{sequencia}", method = RequestMethod.DELETE)
    public AreaIndicador deleteArea(@PathVariable("tipo") int tipo, @PathVariable("sequencia") int sequencia) {                  
    	return indicadoresService.deleteArea(tipo, sequencia);
    }
}
