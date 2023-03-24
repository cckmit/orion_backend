package br.com.live.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.FinanceiroCustom;
import br.com.live.model.ConsultaTitulosComissao;

@Service
@Transactional
public class FinanceiroService {
	
	private final FinanceiroCustom financeiroCustom;
	
	@Autowired
	FinanceiroService(FinanceiroCustom financeiroCustom) {
		this.financeiroCustom = financeiroCustom;
	}
	
	public List<ConsultaTitulosComissao> encontraDataInicioCobrancaAtrasadas(int mes, int ano, int representante){
		
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
		
		return financeiroCustom.findTitulosAtrasadosAnalitico(dataInicio, representante);	
	}

}
