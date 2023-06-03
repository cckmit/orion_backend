package br.com.live.body;

import java.util.List;

import br.com.live.model.PedidoVenda;
import br.com.live.model.ResumoOcupacaoCarteiraPorCanalVenda;
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
	public List<ResumoOcupacaoCarteiraPorCanalVenda> listResumoOcupacaoCarteiraPorCanaisVarejo;
	public List<ResumoOcupacaoCarteiraPorCanalVenda> listResumoOcupacaoCarteiraPorCanaisAtacado;
	public ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorVarejo;
	public ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorAtacado;	
	public ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraTotal;
	public List<PedidoVenda> resumoOcupacaoCarteiraPorPedido; 	
	
	public BodyOcupacaoCarteira(List<ResumoOcupacaoCarteiraPorCanalVenda> listResumoOcupacaoCarteiraPorCanaisVarejo,
		List<ResumoOcupacaoCarteiraPorCanalVenda> listResumoOcupacaoCarteiraPorCanaisAtacado,
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorVarejo,
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorAtacado,	
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraTotal,
		List<PedidoVenda> resumoOcupacaoCarteiraPorPedido) {
		this.listResumoOcupacaoCarteiraPorCanaisVarejo = listResumoOcupacaoCarteiraPorCanaisVarejo;
		this.listResumoOcupacaoCarteiraPorCanaisAtacado = listResumoOcupacaoCarteiraPorCanaisAtacado;
		this.resumoOcupacaoCarteiraPorVarejo = resumoOcupacaoCarteiraPorVarejo;
		this.resumoOcupacaoCarteiraPorAtacado = resumoOcupacaoCarteiraPorAtacado;	
		this.resumoOcupacaoCarteiraTotal = resumoOcupacaoCarteiraTotal;
		this.resumoOcupacaoCarteiraPorPedido = resumoOcupacaoCarteiraPorPedido;		
	}
}
