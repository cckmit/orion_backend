package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyIndicadores;
import br.com.live.custom.IndicadoresCustom;
import br.com.live.entity.AreaIndicador;
import br.com.live.entity.DepartamentoIndicador;
import br.com.live.entity.GrupoIndicador;
import br.com.live.entity.Indicadores;
import br.com.live.entity.IndicadoresDiario;
import br.com.live.entity.IndicadoresMensal;
import br.com.live.entity.IndicadoresSemanal;
import br.com.live.entity.ResultadosIndicadorDiario;
import br.com.live.entity.ResultadosIndicadorMensal;
import br.com.live.entity.ResultadosIndicadorSemanal;
import br.com.live.entity.SetorIndicador;
import br.com.live.entity.UndMedidaIndicador;
import br.com.live.model.IndicadoresAdministrativos;
import br.com.live.service.IndicadoresService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;


@RestController
@CrossOrigin
@RequestMapping("/administrativo")
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
	// Carregar Grupos de Indicadores
	@RequestMapping(value = "/find-all-grupo", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllGrupo() {
        return indicadoresCustom.findGrupo();
	}
	// Carregar Área de Indicadores
	@RequestMapping(value = "/find-all-area", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllArea() {
        return indicadoresCustom.findArea();
	}
	// Carregar Departamento de Indicadores
	@RequestMapping(value = "/find-all-departamento", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllDepartamento() {
        return indicadoresCustom.findDepartamento();
	}
	// Carregar Setor de Indicadores
	@RequestMapping(value = "/find-all-setor", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllSetor() {
        return indicadoresCustom.findSetor();
	}
	// Carregar Setor de Indicadores
	@RequestMapping(value = "/find-all-usuarios", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllUsuarios() {
        return indicadoresCustom.findUsuarios();
	}
	// Carregar Unidade de Medida de Indicadores
	@RequestMapping(value = "/find-all-und-medida", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllUndMedida() {
        return indicadoresCustom.findUndMedida();
	}
	//
    // Salva Novo Grupo de Indicador
    //
    @RequestMapping(value = "/save-grupo-indicador", method = RequestMethod.POST)
    public GrupoIndicador saveGrupoIndicador(@RequestBody BodyIndicadores body) {                  
    	return indicadoresCustom.cadastrarGrupo(body.descricaoGrupo);
    }
    //
    // Salva Nova Área de Indicador
    //
    @RequestMapping(value = "/save-area-indicador", method = RequestMethod.POST)
    public AreaIndicador saveAreaIndicador(@RequestBody BodyIndicadores body) {                  
    	return indicadoresCustom.cadastrarArea(body.descricaoArea);
    }
    //
    // Salva Novo Departamento de Indicador
    //
    @RequestMapping(value = "/save-departamento-indicador", method = RequestMethod.POST)
    public DepartamentoIndicador saveDepartamentoIndicador(@RequestBody BodyIndicadores body) {                  
    	return indicadoresCustom.cadastrarDepartamento(body.descricaoDepartamento);
    }
    //
    // Salva Novo Setor de Indicador
    //
    @RequestMapping(value = "/save-setor-indicador", method = RequestMethod.POST)
    public SetorIndicador saveSetorIndicador(@RequestBody BodyIndicadores body) {                  
    	return indicadoresCustom.cadastrarSetor(body.descricaoSetor);
    }
    //
    // Salva Nova Unidade de Medida de Indicador
    //
    @RequestMapping(value = "/save-und-medida-indicador", method = RequestMethod.POST)
    public UndMedidaIndicador saveUndMedIndicador(@RequestBody BodyIndicadores body) {                  
    	return indicadoresCustom.cadastrarUndMedida(body.descricaoUndMed);
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
}
