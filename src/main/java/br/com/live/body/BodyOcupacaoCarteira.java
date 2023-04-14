package br.com.live.body;

import java.util.List;

import br.com.live.model.DadosOcupacaoCarteiraPorCanaisVenda;
import br.com.live.model.DadosOcupacaoCarteiraPorModalidade;

public class BodyOcupacaoCarteira {
	// parametros de envio
	public int mes;
	public int ano;	
	public String tipoOrcamento;
	public boolean pedidosDisponibilidade;
	public boolean pedidosProgramados;
	public boolean pedidosProntaEntrega;
	// dados de retorno
	List<DadosOcupacaoCarteiraPorCanaisVenda> dadosCarteiraPorCanaisVarejo;
	List<DadosOcupacaoCarteiraPorCanaisVenda> dadosCarteiraPorCanaisAtacado;
	DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraPorVarejo;
	DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraPorAtacado;	
	DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraTotal;
	
	public BodyOcupacaoCarteira(List<DadosOcupacaoCarteiraPorCanaisVenda> dadosCarteiraPorCanaisVarejo,
		List<DadosOcupacaoCarteiraPorCanaisVenda> dadosCarteiraPorCanaisAtacado,
		DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraPorVarejo,
		DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraPorAtacado,	
		DadosOcupacaoCarteiraPorModalidade dadosOcupacaoCarteiraTotal) {
		this.dadosCarteiraPorCanaisVarejo = dadosCarteiraPorCanaisVarejo;
		this.dadosCarteiraPorCanaisAtacado = dadosCarteiraPorCanaisAtacado;
		this.dadosOcupacaoCarteiraPorVarejo = dadosOcupacaoCarteiraPorVarejo;
		this.dadosOcupacaoCarteiraPorAtacado = dadosOcupacaoCarteiraPorAtacado;	
		this.dadosOcupacaoCarteiraTotal = dadosOcupacaoCarteiraTotal;		
	}
}
