package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.body.BodyOcupacaoCarteira;
import br.com.live.custom.OcupacaoCarteiraCustom;
import br.com.live.model.ResumoOcupacaoCarteiraPorCanalVenda;
import br.com.live.model.ResumoOcupacaoCarteiraPorModalidade;

@Service
@Transactional
public class OcupacaoCarteiraService {

	private final OcupacaoCarteiraCustom ocupacaoCarteiraCustom;
	
	public OcupacaoCarteiraService(OcupacaoCarteiraCustom ocupacaoCarteiraCustom) {
		this.ocupacaoCarteiraCustom = ocupacaoCarteiraCustom;
	}
	
	public BodyOcupacaoCarteira consultar(String valorResumir, int mes, int ano, String tipoOrcamento, boolean pedidosDisponibilidade, boolean pedidosProgramados, boolean pedidosProntaEntrega) {				                                                																		
		List<ResumoOcupacaoCarteiraPorCanalVenda> resumoCarteiraPorCanaisVarejo = ocupacaoCarteiraCustom.consultarOcupacaoCarteiraPorCanal(valorResumir, mes, ano, tipoOrcamento, pedidosDisponibilidade, pedidosProgramados, pedidosProntaEntrega, OcupacaoCarteiraCustom.MODALIDADE_VAREJO);
		List<ResumoOcupacaoCarteiraPorCanalVenda> resumoCarteiraPorCanaisAtacado = ocupacaoCarteiraCustom.consultarOcupacaoCarteiraPorCanal(valorResumir, mes, ano, tipoOrcamento, pedidosDisponibilidade, pedidosProgramados, pedidosProntaEntrega, OcupacaoCarteiraCustom.MODALIDADE_ATACADO);
		
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorVarejo = agruparPorModalidade(OcupacaoCarteiraCustom.MODALIDADE_VAREJO, resumoCarteiraPorCanaisVarejo);
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorAtacado = agruparPorModalidade(OcupacaoCarteiraCustom.MODALIDADE_ATACADO, resumoCarteiraPorCanaisAtacado);	
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraTotal = somar(resumoOcupacaoCarteiraPorVarejo, resumoOcupacaoCarteiraPorAtacado);

		return new BodyOcupacaoCarteira(resumoCarteiraPorCanaisVarejo, resumoCarteiraPorCanaisAtacado, resumoOcupacaoCarteiraPorVarejo, resumoOcupacaoCarteiraPorAtacado, resumoOcupacaoCarteiraTotal);
	}
	
	private ResumoOcupacaoCarteiraPorModalidade agruparPorModalidade(String tipoModalidade, List<ResumoOcupacaoCarteiraPorCanalVenda> listCanais) {
		double valorOrcado = 0; 
		double valorReal = 0; 
		double valorConfirmar = 0; 
		double valorTotal = 0; 
		double percentual = 0;

		for (ResumoOcupacaoCarteiraPorCanalVenda canal : listCanais) {
			valorOrcado += canal.getValorOrcado(); 
			valorReal += canal.getValorReal(); 
			valorConfirmar += canal.getValorConfirmar(); 
		}
		valorTotal = valorReal + valorConfirmar; 
		percentual = valorOrcado > 0 ? ((valorTotal / valorOrcado) * 100) : 0;
		
		return new ResumoOcupacaoCarteiraPorModalidade(tipoModalidade, valorOrcado, valorReal, valorConfirmar, valorTotal, percentual);
	}
	
	private ResumoOcupacaoCarteiraPorModalidade somar(ResumoOcupacaoCarteiraPorModalidade varejo, ResumoOcupacaoCarteiraPorModalidade atacado) {
		double valorOrcado = varejo.getValorOrcado() + atacado.getValorOrcado(); 
		double valorReal = varejo.getValorReal() + atacado.getValorReal(); 
		double valorConfirmar = varejo.getValorConfirmar() + atacado.getValorConfirmar(); 
		double valorTotal = valorReal + valorConfirmar; 
		double percentual = valorOrcado > 0 ? ((valorTotal / valorOrcado) * 100) : 0;
		
		return new ResumoOcupacaoCarteiraPorModalidade("AMBOS", valorOrcado, valorReal, valorConfirmar, valorTotal, percentual);
	}
}
