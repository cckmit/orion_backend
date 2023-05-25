package br.com.live.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.com.live.custom.IndicadoresCustom;
import br.com.live.entity.AreaIndicador;
import br.com.live.entity.Indicadores;
import br.com.live.entity.IndicadoresMensal;
import br.com.live.entity.ResultadosIndicadorMensal;
import br.com.live.model.ConsultaIndicadores;
import br.com.live.repository.IndicadoresMensalRepository;
import br.com.live.repository.IndicadoresRepository;
import br.com.live.repository.ResultadosIndicadorMensalRepository;
import br.com.live.repository.TipoIndicadorRepository;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
@Transactional
public class IndicadoresService {
	
	private IndicadoresRepository indicadoresRepository;
	private IndicadoresMensalRepository indicadoresMensalRepository;
	private IndicadoresCustom indicadoresCustom;
	private ResultadosIndicadorMensalRepository resultadosIndicadorMensalRepository;
	private TipoIndicadorRepository tipoIndicadorRepository;
	
	public IndicadoresService(IndicadoresRepository indicadoresRepository, IndicadoresMensalRepository indicadoresMensalRepository, IndicadoresCustom indicadoresCustom,
			ResultadosIndicadorMensalRepository resultadosIndicadorMensalRepository, TipoIndicadorRepository tipoIndicadorRepository) {	
		this.indicadoresRepository = indicadoresRepository;
		this.indicadoresMensalRepository = indicadoresMensalRepository;
		this.indicadoresCustom = indicadoresCustom;
		this.resultadosIndicadorMensalRepository = resultadosIndicadorMensalRepository;
		this.tipoIndicadorRepository = tipoIndicadorRepository; 
	}
	
	public List<ConsultaIndicadores> findAllIndicadores(int idUsuario){
		return indicadoresCustom.findAllIndicadores(idUsuario);		
	}
	
	public Indicadores findIndicadorById(int idUsuario){
		return indicadoresCustom.findIndicadorSelect(idUsuario);		
	}
	
	public List<ConsultaIndicadores> saveIndicador(long id, String nomeIndicador, int grupoIndicador, int area, int departamento, int setor, int gestorAvaliado, int unidadeMedida,
			int frequenciaMonitoramento, int fonteDados, int polaridade, int responsavelRegistro, int responsavelPublicacao, String observacao, String formulaCalculo,
			String variaveis, int idUsuario, int situacao) {
		
		Indicadores dadosIndicador = null;
		
		dadosIndicador = indicadoresRepository.findByIdIndicador(id);
		
		if (dadosIndicador == null) {
			id = indicadoresRepository.findNextID();
			dadosIndicador = new Indicadores(id, nomeIndicador, grupoIndicador, area, departamento, setor, gestorAvaliado, unidadeMedida, frequenciaMonitoramento, fonteDados,
					polaridade, responsavelRegistro, responsavelPublicacao, observacao, formulaCalculo, variaveis, situacao);
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
			dadosIndicador.situacao = situacao;
		}
		indicadoresRepository.save(dadosIndicador);
		return indicadoresCustom.findAllIndicadores(idUsuario);
	}
	// Classe para criar ou atualizar Dados da Tabela Mensal
	public List<IndicadoresMensal> insertUpdateTableMensal(int ano, String variaveis, int idIndicador) {
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
			String idResMensal = idIndicador + "-" + ano + "-resultados";
			String res = "resultados";
			dadosResMensal = new ResultadosIndicadorMensal(idResMensal, idIndicador, ano, res, "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			resultadosIndicadorMensalRepository.saveAndFlush(dadosResMensal);			
		} 
		return indicadoresCustom.findResultadosMensal(ano, idIndicador);
	}
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------		
	public List<IndicadoresMensal> aplicarFormulaMensal(int idIndicador, List<IndicadoresMensal> listMetas){
		IndicadoresMensal saveValores = null;
		int anoSelect = 0;
		
		for(IndicadoresMensal valor : listMetas) {
			indicadoresMensalRepository.deleteById(valor.id);
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

			for(String valorFormula : listMetaVariavel) {
				if(!valorFormula.equals("META PERIODO")) {
					formula = formula.replace(valorFormula, indicadoresCustom.retornaValorMensal(idIndicador, mes, anoSelect, valorFormula));					
				}
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
    // -------------------------------------------------------------------------
    public List<String> retornarVariaveisDiarias(int idIndicador, String mes, int ano){
    	List<String> vars = indicadoresCustom.findvariaveisDiariasById(idIndicador, mes, ano);
    	return vars;
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<ConsultaIndicadores> deleteIndicador(int idIndicador, int idUsuario) {
    	indicadoresRepository.deleteById(idIndicador);
    	deleteValores(idIndicador);
    	return indicadoresCustom.findAllIndicadores(idUsuario);
	}
    public void deleteValores(int idIndicador){
    	indicadoresCustom.deleteValoresMensais(idIndicador);
    	indicadoresCustom.deleteResultadosMensais(idIndicador); 
    }
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------
    public AreaIndicador saveAreaIndicador(int tipo, int sequencia, String descricao) {
    	int nextSequencia = 0;
    	AreaIndicador objetoSave;
    	
    	if(sequencia == 0) {
    		nextSequencia = indicadoresCustom.findNextSequencia(tipo);
    		objetoSave = new AreaIndicador(tipo + "-" + nextSequencia, tipo, nextSequencia, descricao.toUpperCase());
    		tipoIndicadorRepository.save(objetoSave);
    	} else {
    		AreaIndicador dadosTipo = indicadoresCustom.findTipoById(tipo + "-" + sequencia);
    		dadosTipo.descricao = descricao.toUpperCase();
    		tipoIndicadorRepository.save(dadosTipo); 
    	}
		return null;    	
    } 
    // ----------------------------------------------------------------------------------------------------------------------------------------------------------------
    public AreaIndicador deleteArea(int tipo, int sequencia) {
    	String idArea = tipo + "-" + sequencia;
    	tipoIndicadorRepository.deleteById(idArea);    	
		return null;
	}
}
