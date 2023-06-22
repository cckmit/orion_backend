package br.com.live.producao.model;

import java.util.ArrayList;
import java.util.List;

public class DadosGeracaoOrdemProducao {

	private String referencia;
	private int periodo;
	private int alternativa;
	private int roteiro;
	private String observacao1;
	private String observacao2;
	private int quantidade;
	private int pedido;
	private int colecaoPlanoMestre;
	private List<DadosGeracaoOrdemProducaoItem> gradeTamanhosCores;
	
	public DadosGeracaoOrdemProducao(String referencia, int periodo, int alternativa, int roteiro, String observacao1,
			String observacao2) {
		super();
		this.referencia = referencia;
		this.periodo = periodo;
		this.alternativa = alternativa;
		this.roteiro = roteiro;
		this.observacao1 = observacao1;
		this.observacao2 = observacao2;
		this.pedido = 0;
		this.colecaoPlanoMestre = 0;
		this.gradeTamanhosCores = new ArrayList<DadosGeracaoOrdemProducaoItem>();
	}
	
	public DadosGeracaoOrdemProducao(String referencia, int periodo, int alternativa, int roteiro, String observacao1,
			String observacao2, int pedido) {
		super();
		this.referencia = referencia;
		this.periodo = periodo;
		this.alternativa = alternativa;
		this.roteiro = roteiro;
		this.observacao1 = observacao1;
		this.observacao2 = observacao2;
		this.pedido = pedido;
		this.colecaoPlanoMestre = 0;
		this.gradeTamanhosCores = new ArrayList<DadosGeracaoOrdemProducaoItem>();
	}

	public DadosGeracaoOrdemProducao(String referencia, int periodo, int alternativa, int roteiro, String observacao1,
			String observacao2, int pedido, int colecaoPlanoMestre) {
		super();
		this.referencia = referencia;
		this.periodo = periodo;
		this.alternativa = alternativa;
		this.roteiro = roteiro;
		this.observacao1 = observacao1;
		this.observacao2 = observacao2;
		this.pedido = pedido;
		this.colecaoPlanoMestre = colecaoPlanoMestre;
		this.gradeTamanhosCores = new ArrayList<DadosGeracaoOrdemProducaoItem>();
	}
	
	public void addItem(String tamanho, String cor, int quantidade) {
		this.gradeTamanhosCores.add(new DadosGeracaoOrdemProducaoItem(tamanho, cor, quantidade));
		this.quantidade += quantidade;
	}

	public String getReferencia() {
		return referencia;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public int getAlternativa() {
		return alternativa;
	}

	public int getRoteiro() {
		return roteiro;
	}

	public String getObservacao1() {
		return observacao1;
	}

	public String getObservacao2() {
		return observacao2;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public int getPedido() {
		return pedido;
	}

	public int getColecaoPlanoMestre() {
		return colecaoPlanoMestre;
	}

	public List<DadosGeracaoOrdemProducaoItem> getGradeTamanhosCores() {
		return gradeTamanhosCores;
	}	
}
