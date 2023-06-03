package br.com.live.model;

public class ResumoOcupacaoCarteiraPorCanalVenda extends ResumoOcupacaoCarteira{
	protected String canal;
	
	public ResumoOcupacaoCarteiraPorCanalVenda() {
		super();
		this.canal = "";
	}
	
	public ResumoOcupacaoCarteiraPorCanalVenda(String canal, double valorOrcado, double valorReal,
			double valorConfirmar) {
		super(valorOrcado, valorReal, valorConfirmar);
		this.canal = canal;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}
}
