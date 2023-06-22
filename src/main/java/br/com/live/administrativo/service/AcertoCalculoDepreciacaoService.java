package br.com.live.administrativo.service;

import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.administrativo.custom.AcertoCalculoDepreciacaoCustom;
import br.com.live.administrativo.model.AcertoCalculoDepreciacao;

@Service
@Transactional
public class AcertoCalculoDepreciacaoService {
	
	private final AcertoCalculoDepreciacaoCustom acertoCalculoDepreciacaoCustom;
	
	@Autowired
	AcertoCalculoDepreciacaoService(AcertoCalculoDepreciacaoCustom acertoCalculoDepreciacaoCustom) {
		this.acertoCalculoDepreciacaoCustom = acertoCalculoDepreciacaoCustom;
	}
	
	public void inserirMesesCalculo() {
		
		String bensStr = "84233,84248,86601,85910,84181,86696,84225,84194,85989,84208,84209,84228,3479,3480,85967,85991,82010";
		
		List<AcertoCalculoDepreciacao> bensList = acertoCalculoDepreciacaoCustom.findBensCalculoDepreciacao(bensStr);
		
		for (AcertoCalculoDepreciacao bem : bensList) {
			boolean faca = true;
			
			AcertoCalculoDepreciacao dadosBem = acertoCalculoDepreciacaoCustom.findMesCalculoDepreciacaoBens(bem.id, bem.sequence, bem.ano);
			
			YearMonth data = YearMonth.parse("" + bem.ano + "-" + String.format("%02d", dadosBem.mes) + "");
			
			while(faca) {
				data = data.plusMonths(1);
				
				if (data.getMonthValue() == 3 && data.getYear() == 2022) {
					faca = false;
				}
				
				int validarMes = acertoCalculoDepreciacaoCustom.validarMesJaInserido(data.getYear(), data.getMonthValue(), bem.id);

				if (validarMes != 1) {
					acertoCalculoDepreciacaoCustom.inserirDepreciacaoMes(acertoCalculoDepreciacaoCustom.findNextIdMes(), data.getYear(), data.getMonthValue(), bem.id, dadosBem.sequence);
				}
	        }
		}
		System.out.println("PROCESSO CONCLUIDO!");
	}
}
