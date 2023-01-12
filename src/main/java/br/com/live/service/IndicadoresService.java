package br.com.live.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.com.live.custom.IndicadoresCustom;
import br.com.live.entity.Indicadores;
import br.com.live.entity.IndicadoresDiario;
import br.com.live.entity.IndicadoresMensal;
import br.com.live.entity.IndicadoresSemanal;
import br.com.live.entity.ResultadosIndicadorDiario;
import br.com.live.entity.ResultadosIndicadorMensal;
import br.com.live.entity.ResultadosIndicadorSemanal;
import br.com.live.repository.IndicadoresDiarioRepository;
import br.com.live.repository.IndicadoresMensalRepository;
import br.com.live.repository.IndicadoresRepository;
import br.com.live.repository.IndicadoresSemanalRepository;
import br.com.live.repository.ResultadosIndicadorDiarioRepository;
import br.com.live.repository.ResultadosIndicadorMensalRepository;
import br.com.live.repository.ResultadosIndicadorSemanalRepository;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
@Transactional
public class IndicadoresService {
	
	private IndicadoresRepository indicadoresRepository;
	private IndicadoresMensalRepository indicadoresMensalRepository;
	private IndicadoresSemanalRepository indicadoresSemanalRepository;
	private IndicadoresDiarioRepository indicadoresDiarioRepository;
	private IndicadoresCustom indicadoresCustom;
	private ResultadosIndicadorMensalRepository resultadosIndicadorMensalRepository;
	private ResultadosIndicadorSemanalRepository resultadosIndicadorSemanalRepository;
	private ResultadosIndicadorDiarioRepository resultadosIndicadorDiarioRepository;
	
	public IndicadoresService(IndicadoresRepository indicadoresRepository, IndicadoresMensalRepository indicadoresMensalRepository, IndicadoresCustom indicadoresCustom,
			ResultadosIndicadorMensalRepository resultadosIndicadorMensalRepository, IndicadoresSemanalRepository indicadoresSemanalRepository,
			ResultadosIndicadorSemanalRepository resultadosIndicadorSemanalRepository, IndicadoresDiarioRepository indicadoresDiarioRepository,
			ResultadosIndicadorDiarioRepository resultadosIndicadorDiarioRepository) {	
		this.indicadoresRepository = indicadoresRepository;
		this.indicadoresMensalRepository = indicadoresMensalRepository;
		this.indicadoresCustom = indicadoresCustom;
		this.resultadosIndicadorMensalRepository = resultadosIndicadorMensalRepository;
		this.indicadoresSemanalRepository = indicadoresSemanalRepository;
		this.resultadosIndicadorSemanalRepository = resultadosIndicadorSemanalRepository;
		this.indicadoresDiarioRepository = indicadoresDiarioRepository;
		this.resultadosIndicadorDiarioRepository = resultadosIndicadorDiarioRepository;
	}
	
	public Indicadores saveIndicador(long id, String nomeIndicador, int grupoIndicador, int area, int departamento, int setor, int gestorAvaliado, String unidadeMedida,
			String frequenciaMonitoramento, String fonteDados, String polaridade, int responsavelRegistro, int responsavelPublicacao, String observacao, String formulaCalculo,
			String variaveis) {
		
		Indicadores dadosIndicador = null;
		
		dadosIndicador = indicadoresRepository.findByIdIndicador(id);
		
		if (dadosIndicador == null) {
			id = indicadoresRepository.findNextID();
			dadosIndicador = new Indicadores(id, nomeIndicador, grupoIndicador, area, departamento, setor, gestorAvaliado, unidadeMedida, frequenciaMonitoramento, fonteDados,
					polaridade, responsavelRegistro, responsavelPublicacao, observacao, formulaCalculo, variaveis);
		} else {
			dadosIndicador.nomeIndicador = nomeIndicador;
			dadosIndicador.grupoIndicador = grupoIndicador;
			dadosIndicador.area = area;
			dadosIndicador.departamento = departamento;
			dadosIndicador.setor = setor;
			dadosIndicador.gestorAvaliado = gestorAvaliado;
			dadosIndicador.unidadeMedida = unidadeMedida;
			dadosIndicador.frequenciaMonitoramento = frequenciaMonitoramento;
			dadosIndicador.fonteDados = fonteDados;
			dadosIndicador.polaridade = polaridade;
			dadosIndicador.responsavelRegistro = responsavelRegistro;
			dadosIndicador.responsavelPublicacao = responsavelPublicacao;
			dadosIndicador.observacao = observacao;
			dadosIndicador.formulaCalculo = formulaCalculo;
			dadosIndicador.variaveis = variaveis;
		}
		indicadoresRepository.save(dadosIndicador);
		return dadosIndicador;
	}
	// Classe para criar ou atualizar Dados da Tabela Mensal
	public List<IndicadoresMensal> insertUpdateTable(int ano, String variaveis, int idIndicador) {
		IndicadoresMensal dadosMensal = null;
		List<IndicadoresMensal> listMensal = indicadoresMensalRepository.findByAno(ano, idIndicador);
		
		if (listMensal.size() == 0) {
			String meta = "meta periodo";
			dadosMensal = new IndicadoresMensal(idIndicador, variaveis, ano, meta, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			indicadoresMensalRepository.saveAndFlush(dadosMensal);
			inserirVariaveisMensal(ano, variaveis, idIndicador);
		} 
		return indicadoresCustom.findDadosAno(ano, idIndicador);
	}
	// ---------
	public void inserirVariaveisMensal(int ano, String variaveis, int idIndicador) {
		
		String[] variaveisConcat = variaveis.split("[.]");
		IndicadoresMensal dadosVariaveis = null;
		
		for (String var : variaveisConcat) {
			dadosVariaveis = new IndicadoresMensal(idIndicador, variaveis, ano, var, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
			indicadoresMensalRepository.saveAndFlush(dadosVariaveis);
		}
	}
	// ---------
	public List<ResultadosIndicadorMensal> inserirResultadoMensal(int ano, int idIndicador) {
		ResultadosIndicadorMensal dadosResMensal = null;
		List<ResultadosIndicadorMensal> resultMensal = resultadosIndicadorMensalRepository.findByAno(ano, idIndicador);
		
		if (resultMensal.size() == 0) {
			String idResMensal = ano + "-resultados";
			String res = "resultados";
			dadosResMensal = new ResultadosIndicadorMensal(idResMensal, idIndicador, ano, res, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			resultadosIndicadorMensalRepository.saveAndFlush(dadosResMensal);			
		} 
		return indicadoresCustom.findResultadosMensal(ano, idIndicador);
	}
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public List<IndicadoresSemanal> insertUpdateTableSemana(String mes, int ano, String variaveis, int idIndicador){
		
		IndicadoresSemanal dadosSemanal = null;
		List<IndicadoresSemanal> listSemana = indicadoresSemanalRepository.findByMesAno(idIndicador, mes, ano);
		if (listSemana.size() == 0) {
			String meta = "meta periodo";
			dadosSemanal = new IndicadoresSemanal(idIndicador, variaveis, mes, ano, meta, "", 0, 0, 0, 0, 0);
			indicadoresSemanalRepository.saveAndFlush(dadosSemanal);
			inserirVariaveisSemanal(mes, ano, variaveis, idIndicador);
		}		
		return indicadoresCustom.findDadosMesAno(mes, ano, idIndicador);
	}
	// ------------------------------------------------------------------------------------------
	public void inserirVariaveisSemanal(String mes, int ano, String variaveis, int idIndicador) {
		
		String[] variaveisConcat = variaveis.split("[.]");
		IndicadoresSemanal dadosVariaveis = null;
		
		for (String var : variaveisConcat) {
			dadosVariaveis = new IndicadoresSemanal(idIndicador, variaveis, mes, ano, var, "", 0, 0, 0, 0, 0); 
			indicadoresSemanalRepository.saveAndFlush(dadosVariaveis);
		}
	}
	// ------------------------------------------------------------------------------------------
	public List<ResultadosIndicadorSemanal> inserirResultadoSemanal(String mes, int ano, int idIndicador) {
		ResultadosIndicadorSemanal dadosResSemanal = null;
		List<ResultadosIndicadorSemanal> resultSemanal = resultadosIndicadorSemanalRepository.findByMesAno(mes, ano, idIndicador);
		
		if (resultSemanal.size() == 0) {
			String idResSemanal = mes + "-" + ano + "-resultados";
			String res = "resultados";
			dadosResSemanal = new ResultadosIndicadorSemanal(idResSemanal, idIndicador, mes, ano, res, "", 0, 0, 0, 0, 0);
			resultadosIndicadorSemanalRepository.saveAndFlush(dadosResSemanal);			
		} 
		return indicadoresCustom.findResultadosSemanal(mes, ano, idIndicador);
	}
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------		
	public List<IndicadoresMensal> aplicarFormulaMensal(int idIndicador, List<IndicadoresMensal> listMetas){
		IndicadoresMensal saveValores = null;
		int anoSelect = 0;
		
		for(IndicadoresMensal valor : listMetas) {
			indicadoresMensalRepository.deleteById(valor.ano + "-" + valor.codigo);
			anoSelect = valor.ano;
			
			saveValores = new IndicadoresMensal(idIndicador, valor.variaveis, valor.ano, valor.codigo, valor.descricao, valor.janeiro, valor.fevereiro, valor.marco, 
					valor.abril, valor.maio, valor.junho, valor.julho, valor.agosto, valor.setembro, valor.outubro, valor.novembro, valor.dezembro);
			indicadoresMensalRepository.saveAndFlush(saveValores);			
		}
		
		List<String> listMetaVariavel = retornarVariaveisMensais(idIndicador, anoSelect);
		ArrayList <String> meses = new ArrayList <String> (
			      Arrays.asList ("janeiro", "fevereiro", "marco", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"));
		for(String mes : meses) {
			String formula = indicadoresCustom.findFormula(idIndicador);
			double result  = 0;
			for(String var : listMetaVariavel) {
				formula = formula.replace(var, indicadoresCustom.retornaValorMensal(idIndicador, mes, anoSelect, var));
			}			
		
			Expression expression = new ExpressionBuilder(formula).build();

			try {
				result = expression.evaluate();	  
				
			} catch (Exception e) {	
				result = 0;				
			};
	        indicadoresCustom.updateResultMensal(idIndicador, result, mes, anoSelect);   
		}
		return indicadoresCustom.findDadosAno(anoSelect, idIndicador);
	}
    // -------------------------------------------------------------------------
    public List<String> retornarVariaveisMensais(int idIndicador, int ano){
    	List<String> vars = indicadoresCustom.findvariaveisMensaisById(idIndicador, ano);
    	return vars;
    }
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<IndicadoresSemanal> aplicarFormulaSemanal(int idIndicador, List<IndicadoresSemanal> listMetasSemana){
    	IndicadoresSemanal saveValores = null;
    	String mesSelect = "";
		int anoSelect = 0;
		

		for(IndicadoresSemanal valor : listMetasSemana) {
			indicadoresSemanalRepository.deleteById(valor.mes + "-" + valor.ano + "-" + valor.codigo);
			mesSelect = valor.mes;
			anoSelect = valor.ano;
			
			saveValores = new IndicadoresSemanal(idIndicador, valor.variaveis, valor.mes, valor.ano, valor.codigo, valor.descricao, valor.semana1, valor.semana2,
					valor.semana3, valor.semana4, valor.semana5);
			indicadoresSemanalRepository.saveAndFlush(saveValores);			
		}
		List<String> listMetaVariavel = retornarVariaveisSemanais(idIndicador, mesSelect, anoSelect);
		ArrayList <String> semanas = new ArrayList <String> (
			      Arrays.asList ("semana_1", "semana_2", "semana_3", "semana_4", "semana_5"));
		for(String sem : semanas) {
			String formula = indicadoresCustom.findFormula(idIndicador);
			double result  = 0;
			for(String var : listMetaVariavel) {										
				formula = formula.replace(var, indicadoresCustom.retornaValorSemanal(idIndicador, mesSelect, anoSelect, sem, var));
			}			
			Expression expression = new ExpressionBuilder(formula).build();
			try {
				result = expression.evaluate();	  
				
			} catch (Exception e) {	
				result = 0;				
			};        
	        indicadoresCustom.updateResultSemanal(idIndicador, result, sem, mesSelect, anoSelect);   
		}
		return indicadoresCustom.findDadosMesAno(mesSelect, anoSelect, idIndicador);
	}
    // -------------------------------------------------------------------------
    public List<String> retornarVariaveisSemanais(int idIndicador, String mes, int ano){
    	List<String> vars = indicadoresCustom.findvariaveisSemanaisById(idIndicador, mes, ano);
    	return vars;
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public List<IndicadoresDiario> insertUpdateTableDiario(String mes, int ano, String variaveis, int idIndicador){
		
		IndicadoresDiario dadosDiario = null;
		List<IndicadoresDiario> listDiario = indicadoresDiarioRepository.findByDia(idIndicador, mes, ano);
		if (listDiario.size() == 0) {
			String meta = "meta periodo";
			dadosDiario = new IndicadoresDiario(idIndicador, variaveis, mes, ano, meta, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			indicadoresDiarioRepository.saveAndFlush(dadosDiario);
			inserirVariaveisDiario(mes, ano, variaveis, idIndicador);
		}		
		return indicadoresCustom.findDadosDia(mes, ano, idIndicador);
	}
	// ------------------------------------------------------------------------------------------
	public void inserirVariaveisDiario(String mes, int ano, String variaveis, int idIndicador) {
		
		String[] variaveisConcat = variaveis.split("[.]");
		IndicadoresDiario dadosVariaveis = null;
		
		for (String var : variaveisConcat) {
			dadosVariaveis = new IndicadoresDiario(idIndicador, variaveis, mes, ano, var, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
			indicadoresDiarioRepository.saveAndFlush(dadosVariaveis);
		}
	}
	// ------------------------------------------------------------------------------------------
	public List<ResultadosIndicadorDiario> inserirResultadoDiario(String mes, int ano, int idIndicador) {
		ResultadosIndicadorDiario dadosResDiario = null;
		List<ResultadosIndicadorDiario> resultDiario = resultadosIndicadorDiarioRepository.findByDia(idIndicador, mes, ano);
		
		if (resultDiario.size() == 0) {
			String idResDiario = mes + "-" + ano + "-resultados";
			String res = "resultados";
			dadosResDiario = new ResultadosIndicadorDiario(idResDiario, idIndicador, mes, ano, res, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			resultadosIndicadorDiarioRepository.saveAndFlush(dadosResDiario);			
		} 
		return indicadoresCustom.findResultadosDiarios(mes, ano, idIndicador);
	}
	// ------------------------------------------------------------------------------------------
	public List<IndicadoresDiario> aplicarFormulaDiario(int idIndicador, List<IndicadoresDiario> listMetasDiaria){
    	IndicadoresDiario saveValores = null;
    	String mesSelect = "";
		int anoSelect = 0;
		
		for(IndicadoresDiario valor : listMetasDiaria) {
			indicadoresDiarioRepository.deleteById(valor.mes + "-" + valor.ano + "-" + valor.codigo);
			mesSelect = valor.mes;
			anoSelect = valor.ano;
			
			saveValores = new IndicadoresDiario(idIndicador, valor.variaveis, valor.mes, valor.ano, valor.codigo, valor.descricao, valor.dia1, valor.dia2, valor.dia3, 
												valor.dia4, valor.dia5, valor.dia6, valor.dia7, valor.dia8, valor.dia9, valor.dia10, valor.dia11, valor.dia12, valor.dia13, 
												valor.dia14, valor.dia15, valor.dia16, valor.dia17, valor.dia18, valor.dia19, valor.dia20, valor.dia21, valor.dia22, 
												valor.dia23, valor.dia24, valor.dia25, valor.dia26, valor.dia27, valor.dia28, valor.dia29, valor.dia30, valor.dia31);
			indicadoresDiarioRepository.saveAndFlush(saveValores);			
		}
		List<String> listMetaVariavel = retornarVariaveisDiarias(idIndicador, mesSelect, anoSelect);
		ArrayList <String> diarias = new ArrayList <String> (
			      Arrays.asList ("dia_1", "dia_2", "dia_3", "dia_4", "dia_5", "dia_6", "dia_7", "dia_8", "dia_9", "dia_10", "dia_11", "dia_12", "dia_13", "dia_14",
				  				"dia_15", "dia_16", "dia_17", "dia_18", "dia_19", "dia_20", "dia_21", "dia_22", "dia_23", "dia_24", "dia_25", "dia_26", "dia_27", "dia_28", 
								"dia_29", "dia_30", "dia_31"));
		for(String dias : diarias) {
			String formula = indicadoresCustom.findFormula(idIndicador);
			double result  = 0;
			for(String var : listMetaVariavel) {										
				formula = formula.replace(var, indicadoresCustom.retornaValorDiaria(idIndicador, mesSelect, anoSelect, dias, var));
			}			
			Expression expression = new ExpressionBuilder(formula).build();
			try {
				result = expression.evaluate();	  
				
			} catch (Exception e) {	
				result = 0;				
			};        
	        indicadoresCustom.updateResultDiario(idIndicador, result, dias, mesSelect, anoSelect);   
		}
		return indicadoresCustom.findDadosDia(mesSelect, anoSelect, idIndicador);
	}
    // -------------------------------------------------------------------------
    public List<String> retornarVariaveisDiarias(int idIndicador, String mes, int ano){
    	List<String> vars = indicadoresCustom.findvariaveisDiariasById(idIndicador, mes, ano);
    	return vars;
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<Indicadores> deleteIndicador(int idIndicador) {
    	indicadoresRepository.deleteById(idIndicador);
		return null;
	}
}
