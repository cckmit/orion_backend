package br.com.live.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.FechamentoComissaoCustom;
import br.com.live.model.ConsultaTitulosComissao;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Service
@Transactional
public class FechamentoComissaoService {
	
	private final FechamentoComissaoCustom financeiroCustom;
	
	@Autowired
	FechamentoComissaoService(FechamentoComissaoCustom financeiroCustom) {
		this.financeiroCustom = financeiroCustom;
	}
	
	public List<ConsultaTitulosComissao> findTitulosAtrasadosAnalitico(int mes, int ano, String representante){
		String dataInicio = findDataInicioCobrancaAtrasadas(mes, ano);		
		return financeiroCustom.findTitulosAtrasadosAnalitico(dataInicio, representante);
	}
	
	public List<ConsultaTitulosComissao> findLancamentosFaturamento(int mes, int ano, String representante){
		String mesComZero = "";
		if (mes < 10) {
			mesComZero = "0" + mes;
		};
		return financeiroCustom.findLancamentosFaturamento(mesComZero, ano, representante);
	}
	
	public List<ConsultaTitulosComissao> findLancamentosBaixaTitulos(int mes, int ano, String representante){
		String mesComZero = "";
		if (mes < 10) {
			mesComZero = "0" + mes;
		};
		return financeiroCustom.findLancamentosBaixaTitulos(mesComZero, ano, representante);
	}
	
	public List<ConsultaTitulosComissao> findTitulosAtrasadosSintetico(int mes, int ano, String representante){
		String dataInicio = findDataInicioCobrancaAtrasadas(mes, ano);
		String dataAnterior = findMesAnteriorDatInicio(dataInicio);
		return financeiroCustom.findTitulosAtrasadosSintetico(dataInicio,  dataAnterior, representante);	
	}
	
	public String findMesAnteriorDatInicio(String dataInicio) {
		String dataAnterior = "";
		int mesAnterior = 0;
		
		String[] dataAnt = dataInicio.split("[/]");
		int dia = Integer.parseInt(dataAnt[0]);
		int mesAtual = Integer.parseInt(dataAnt[1]);
		int ano = Integer.parseInt(dataAnt[2]);
		
		if(mesAtual == 1) {
			mesAnterior = 12;
			ano = ano - 1;
		} else {
			mesAnterior = mesAtual - 1;
		};
		
		if(mesAnterior == 2) {
			dia = 28;
		} else if (mesAnterior == 1 || mesAnterior == 3 || mesAnterior == 5 || mesAnterior == 7 || mesAnterior == 8 || mesAnterior == 10 || mesAnterior == 12) {
			dia = 31;
		} else {
			dia = 30;
		};
		dataAnterior = dia + "/" + mesAnterior + "/" + ano;
		return dataAnterior;
	}
	
	public String findDataInicioCobrancaAtrasadas(int mes, int ano){
		
		int diaInicio = 0;
		int mesInicio = 0;
		int anoInicio = 0;
		String dataInicio = "";
		
		if(mes == 1) {
			mesInicio = 11;
			anoInicio = ano - 1;
		} else if (mes == 2) {
			mesInicio = 12;
			anoInicio = ano - 1;
		} else {
			mesInicio = mes - 2;
			anoInicio = ano;
		};
		
		if(mesInicio == 2) {
			diaInicio = 28;
		} else if (mesInicio == 1 || mesInicio == 3 || mesInicio == 5 || mesInicio == 7 || mesInicio == 8 || mesInicio == 10 || mesInicio == 12) {
			diaInicio = 31;
		} else {
			diaInicio = 30;
		};
		if(mesInicio < 10) {
			dataInicio = diaInicio + "/" + "0" + mesInicio + "/" + anoInicio;
		} else {
			dataInicio = diaInicio + "/" + mesInicio + "/" + anoInicio;			
		};		
		
		return dataInicio;	
	}
	
	public List<ConteudoChaveAlfaNum> findAllEstacoes(){
		return financeiroCustom.findAllEstacoes();
	}
	
	public List<ConsultaTitulosComissao> findBonusPorRepresentante(int mes, int ano, String representante, String estacao){
		
		float totalFaturado = 0;
		float valorProporcional = 0;
		String mesComZero = "";
		if (mes < 10) {
			mesComZero = "0" + mes;
		};
		float metaFitness = financeiroCustom.findMetaPorRespresentanteFitness(representante, estacao); // Buscando a Meta do Representante pra coleção na linha Fitness
		float metaBeach = financeiroCustom.findMetaPorRespresentanteBeach(representante, estacao); // Buscando a Meta do Representante pra coleção na linha Beach
		// Descobrindo a porcentagem de cada meta representa na meta total
		float totalMeta = metaFitness + metaBeach;
		float porcLinhaFitness = (metaFitness * 100) / totalMeta;
		float porcLinhaBeach = 100 - porcLinhaFitness;
		//  ------------------------------------------------
		float percAtingidoFitness = financeiroCustom.findPercAtingidoFitness(mesComZero, ano, representante, estacao);
		float percAtingidoBeach = financeiroCustom.findPercAtingidoBeach(mesComZero, ano, representante, estacao);
		
		if(percAtingidoFitness >= 100) {
			valorProporcional = totalFaturado * (percAtingidoFitness / 100);
			totalFaturado = financeiroCustom.findTotalFaturadoPorRepresentanteNoMes(mesComZero, ano, representante);
		} if(percAtingidoBeach >= 100) {
			valorProporcional = totalFaturado * (percAtingidoBeach / 100);
			totalFaturado = financeiroCustom.findTotalFaturadoPorRepresentanteNoMes(mesComZero, ano, representante);
		} if(percAtingidoFitness >= 100 && percAtingidoBeach >= 100) {
			valorProporcional = totalFaturado;
			totalFaturado = financeiroCustom.findTotalFaturadoPorRepresentanteNoMes(mesComZero, ano, representante);
		}
		
		return financeiroCustom.findBonusPorRepresentante(mesComZero, ano, representante, estacao, totalFaturado, porcLinhaFitness, porcLinhaBeach, percAtingidoFitness, 
				percAtingidoBeach, valorProporcional);
	}

}
