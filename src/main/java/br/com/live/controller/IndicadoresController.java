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
import br.com.live.entity.IndicadoresDiario;
import br.com.live.entity.IndicadoresMensal;
import br.com.live.entity.IndicadoresSemanal;
import br.com.live.entity.ResultadosIndicadorDiario;
import br.com.live.entity.ResultadosIndicadorMensal;
import br.com.live.entity.ResultadosIndicadorSemanal;
import br.com.live.model.IndicadoresAdministrativos;
import br.com.live.repository.TipoIndicadorRepository;
import br.com.live.service.IndicadoresService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;


@RestController
@CrossOrigin
@RequestMapping("/administrativo")
public class IndicadoresController {
	
	private IndicadoresCustom indicadoresCustom;
	private IndicadoresService indicadoresService;
	private TipoIndicadorRepository tipoIndicadorRepository;
	
	@Autowired
	public IndicadoresController(IndicadoresCustom indicadoresCustom, IndicadoresService indicadoresService, TipoIndicadorRepository tipoIndicadorRepository) {
		this.indicadoresCustom = indicadoresCustom;
		this.indicadoresService = indicadoresService;
		this.tipoIndicadorRepository = tipoIndicadorRepository;
				
	}
	// Carregar os dados do Botão Pesquisar
	@RequestMapping(value = "/find-all-indicadores/{idUsuario}", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findAllIndicadores(@PathVariable("idUsuario") String idUsuario) {
		return indicadoresCustom.findAllIndicadores(idUsuario);
	}
	// Carregar os dados do Botão Pesquisar
	@RequestMapping(value = "/find-indicador-select/{id}", method = RequestMethod.GET)
	public IndicadoresAdministrativos findIndicadorSelect(@PathVariable("id") int id) {
		return indicadoresCustom.findIndicadorSelect(id);
	}
	// Carregar todos os Indicadores do Ano selecionado
	@RequestMapping(value = "/find-all-mensal/{ano}/{variaveis}/{idIndicador}", method = RequestMethod.GET)
    public List<IndicadoresMensal> findByAno(@PathVariable("ano") int ano, @PathVariable("variaveis") String variaveis, @PathVariable("idIndicador") int idIndicador) {
        return indicadoresService.insertUpdateTable(ano, variaveis, idIndicador);
    }
	// Carregar todos os Resultados do Ano selecionado
	@RequestMapping(value = "/find-all-mensal-resultado/{ano}/{idIndicador}", method = RequestMethod.GET)
    public List<ResultadosIndicadorMensal> findAllByAno(@PathVariable("ano") int ano, @PathVariable("idIndicador") int idIndicador) {
        return indicadoresService.inserirResultadoMensal(ano, idIndicador);
    }
	// Carregar todos os Indicadores do Mês e Ano selecionado
	@RequestMapping(value = "/find-all-semanal/{mes}/{ano}/{variaveis}/{idIndicador}", method = RequestMethod.GET)
    public List<IndicadoresSemanal> findByMesAno(@PathVariable("mes") String mes, @PathVariable("ano") int ano, @PathVariable("variaveis") String variaveis, @PathVariable("idIndicador") int idIndicador) {
        return indicadoresService.insertUpdateTableSemana(mes, ano, variaveis, idIndicador);
    }
	// Carregar todos os Resultados do Mês e Ano selecionado
	@RequestMapping(value = "/find-all-semanal-resultado/{mes}/{ano}/{idIndicador}", method = RequestMethod.GET)
    public List<ResultadosIndicadorSemanal> findAllByMesAno(@PathVariable("mes") String mes, @PathVariable("ano") int ano, @PathVariable("idIndicador") int idIndicador) {
        return indicadoresService.inserirResultadoSemanal(mes, ano, idIndicador);
    }
	// Carregar todos os Indicadores do Dia selecionado
	@RequestMapping(value = "/find-all-diario/{mes}/{ano}/{variaveis}/{idIndicador}", method = RequestMethod.GET)
    public List<IndicadoresDiario> findByDia(@PathVariable("mes") String mes, @PathVariable("ano") int ano, @PathVariable("variaveis") String variaveis, @PathVariable("idIndicador") int idIndicador) {
        return indicadoresService.insertUpdateTableDiario(mes, ano, variaveis, idIndicador);
    }
	// Carregar todos os Resultados do Mês selecionado
	@RequestMapping(value = "/find-all-diario-resultado/{mes}/{ano}/{idIndicador}", method = RequestMethod.GET)
    public List<ResultadosIndicadorDiario> findAllByDia(@PathVariable("mes") String mes, @PathVariable("ano") int ano, @PathVariable("idIndicador") int idIndicador) {
        return indicadoresService.inserirResultadoDiario(mes, ano, idIndicador);
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
    // Salvar Novos Valores Semanal de Indicador
    //
    @RequestMapping(value = "/gravar-valores-semanal", method = RequestMethod.POST)
    public List<IndicadoresSemanal> gravarValoresSemanal(@RequestBody BodyIndicadores body) {                  
    	return indicadoresService.aplicarFormulaSemanal(body.idIndicador, body.valoresSemanal);
    }
    //
    // Salvar Novos Valores Diários de Indicador
    //
    @RequestMapping(value = "/gravar-valores-diario", method = RequestMethod.POST)
    public List<IndicadoresDiario> gravarValoresDiario(@RequestBody BodyIndicadores body) {                  
    	return indicadoresService.aplicarFormulaDiario(body.idIndicador, body.valoresDiario);
    }
    //
    // Salva Novo Indicador
    //
    @RequestMapping(value = "/save-indicador", method = RequestMethod.POST)
    public Indicadores saveIndicadores(@RequestBody BodyIndicadores body) {
    	return indicadoresService.saveIndicador(body.id, body.nomeIndicador, body.grupoIndicador, body.area, body.departamento, body.setor, body.gestorAvaliado,
    			body.unidadeMedida, body.frequenciaMonitoramento, body.fonteDados, body.polaridade, body.responsavelRegistro, body.responsavelPublicacao,
    			body.observacao, body.formulaCalculo, body.variaveis);
    }
    // Deletar Indicador
    @RequestMapping(value = "/delete-indicador/{idIndicador}", method = RequestMethod.DELETE)
    public List<Indicadores> deleteIndicador(@PathVariable("idIndicador") int idIndicador) {                  
    	return indicadoresService.deleteIndicador(idIndicador);
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
