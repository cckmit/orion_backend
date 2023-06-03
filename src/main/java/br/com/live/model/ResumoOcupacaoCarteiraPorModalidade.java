package br.com.live.model;

public class ResumoOcupacaoCarteiraPorModalidade extends ResumoOcupacaoCarteira {
	private String modalidade;
	
	public ResumoOcupacaoCarteiraPorModalidade() {
		super();
		this.modalidade = "";
	}
	
	public ResumoOcupacaoCarteiraPorModalidade(String modalidade, double valorOrcado, double valorReal,
			double valorConfirmar) {
		super(valorOrcado, valorReal, valorConfirmar);
		this.modalidade = modalidade;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}
}
