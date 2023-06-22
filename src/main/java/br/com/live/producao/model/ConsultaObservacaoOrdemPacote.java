package br.com.live.producao.model;

public class ConsultaObservacaoOrdemPacote {
	public String id;
	public String estagio;
	public int ordemProducao;
	public int ordemConfeccao;
	public String tipoObservacao;
	public int quantidade;
	public String observacaoAdicional;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEstagio() {
		return estagio;
	}

	public void setEstagio(String estagio) {
		this.estagio = estagio;
	}

	public int getOrdemProducao() {
		return ordemProducao;
	}

	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}

	public int getOrdemConfeccao() {
		return ordemConfeccao;
	}

	public void setOrdemConfeccao(int ordemConfeccao) {
		this.ordemConfeccao = ordemConfeccao;
	}

	public String getTipoObservacao() {
		return tipoObservacao;
	}

	public void setTipoObservacao(String tipoObservacao) {
		this.tipoObservacao = tipoObservacao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getObservacaoAdicional() {
		return observacaoAdicional;
	}

	public void setObservacaoAdicional(String observacaoAdicional) {
		this.observacaoAdicional = observacaoAdicional;
	}

	public ConsultaObservacaoOrdemPacote() {
		
	}

	public ConsultaObservacaoOrdemPacote(String id, String estagio, int ordemProducao, int ordemConfeccao,
			String tipoObservacao, int quantidade, String observacaoAdicional) {
		this.id = id;
		this.estagio = estagio;
		this.ordemProducao = ordemProducao;
		this.ordemConfeccao = ordemConfeccao;
		this.tipoObservacao = tipoObservacao;
		this.quantidade = quantidade;
		this.observacaoAdicional = observacaoAdicional;
	}
}
