package br.com.live.body;

import java.util.List;

import br.com.live.model.ResumoOcupacaoCarteiraPorCanaisVenda;
import br.com.live.model.ResumoOcupacaoCarteiraPorModalidade;

public class BodyOcupacaoCarteira {
	// parametros de envio
	public String valorResumir;
	public int mes;
	public int ano;	
	public String tipoOrcamento;
	public boolean pedidosDisponibilidade;
	public boolean pedidosProgramados;
	public boolean pedidosProntaEntrega;
	// dados de retorno
	public List<ResumoOcupacaoCarteiraPorCanaisVenda> listResumoOcupacaoCarteiraPorCanaisVarejo;
	public List<ResumoOcupacaoCarteiraPorCanaisVenda> listResumoOcupacaoCarteiraPorCanaisAtacado;
	public ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorVarejo;
	public ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorAtacado;	
	public ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraTotal;
	
	public BodyOcupacaoCarteira(List<ResumoOcupacaoCarteiraPorCanaisVenda> listResumoOcupacaoCarteiraPorCanaisVarejo,
		List<ResumoOcupacaoCarteiraPorCanaisVenda> listResumoOcupacaoCarteiraPorCanaisAtacado,
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorVarejo,
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorAtacado,	
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraTotal) {
		this.listResumoOcupacaoCarteiraPorCanaisVarejo = listResumoOcupacaoCarteiraPorCanaisVarejo;
		this.listResumoOcupacaoCarteiraPorCanaisAtacado = listResumoOcupacaoCarteiraPorCanaisAtacado;
		this.resumoOcupacaoCarteiraPorVarejo = resumoOcupacaoCarteiraPorVarejo;
		this.resumoOcupacaoCarteiraPorAtacado = resumoOcupacaoCarteiraPorAtacado;	
		this.resumoOcupacaoCarteiraTotal = resumoOcupacaoCarteiraTotal;		
	}
}
