package br.com.live.producao.model;

import java.util.Date;

public class PainelListaPrioridadesOrdensEmProducao {
	private String id;
	private String referencia;
	private String descReferencia;
	private String tamanho;
	private String cor;
	private String descCor;
	private int ordemProducao;
	private int ordemConfeccao;
	private String periodo;
	private int seqFilaEstagio;
	private int seqEstagio;
	private int seqOperacao;
	private int codEstagio;
	private String descEstagio;
	private String descEstagiosDecoracoes;
	private Date dataEntradaEstagio;
	private int pedidoVenda;
	private int emConserto;
	private int qtdeEmProducaoPacote;
	private int qtdeEmProducaoOrdem;
	private int qtdeNecessaria;
	private int codFamilia;
	private String descFaccao;
	private int qtdeCarteira;
	private int qtdeEstoque;
	private int qtdeSobraFalta;
	private int qtdeSugerida;
	private int qtdeColetada;
 	
	public PainelListaPrioridadesOrdensEmProducao() {
		super();	
	}

	public PainelListaPrioridadesOrdensEmProducao(String id, String referencia, String descReferencia, String tamanho, String cor, String descCor,
			int ordemProducao, int ordemConfeccao, String periodo, int seqFilaEstagio, int seqEstagio, int seqOperacao,
			int codEstagio, String descEstagio, Date dataEntradaEstagio, int pedidoVenda, int emConserto, int qtdeEmProducaoPacote, int qtdeEmProducaoOrdem,
			int qtdeNecessaria, int codFamilia, String descFaccao) {
		super();
		this.id = id;
		this.referencia = referencia;
		this.descReferencia = descReferencia;
		this.tamanho = tamanho;
		this.cor = cor;
		this.descCor = descCor;
		this.ordemProducao = ordemProducao;
		this.ordemConfeccao = ordemConfeccao;
		this.periodo = periodo;
		this.seqFilaEstagio = seqFilaEstagio;
		this.seqEstagio = seqEstagio;
		this.seqOperacao = seqOperacao;
		this.codEstagio = codEstagio;
		this.descEstagio = descEstagio;
		this.dataEntradaEstagio = dataEntradaEstagio;
		this.pedidoVenda = pedidoVenda;
		this.emConserto = emConserto;
		this.qtdeEmProducaoPacote = qtdeEmProducaoPacote;
		this.qtdeEmProducaoOrdem = qtdeEmProducaoOrdem;
		this.qtdeNecessaria = qtdeNecessaria;
		this.codFamilia = codFamilia;
		this.descFaccao = descFaccao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getDescReferencia() {
		return descReferencia;
	}

	public void setDescReferencia(String descReferencia) {
		this.descReferencia = descReferencia;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getDescCor() {
		return descCor;
	}

	public void setDescCor(String descCor) {
		this.descCor = descCor;
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

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public int getSeqFilaEstagio() {
		return seqFilaEstagio;
	}

	public void setSeqFilaEstagio(int seqFilaEstagio) {
		this.seqFilaEstagio = seqFilaEstagio;
	}

	public int getSeqEstagio() {
		return seqEstagio;
	}

	public void setSeqEstagio(int seqEstagio) {
		this.seqEstagio = seqEstagio;
	}

	public int getSeqOperacao() {
		return seqOperacao;
	}

	public void setSeqOperacao(int seqOperacao) {
		this.seqOperacao = seqOperacao;
	}

	public int getCodEstagio() {
		return codEstagio;
	}

	public void setCodEstagio(int codEstagio) {
		this.codEstagio = codEstagio;
	}

	public String getDescEstagio() {
		return descEstagio;
	}

	public void setDescEstagio(String descEstagio) {
		this.descEstagio = descEstagio;
	}

	public Date getDataEntradaEstagio() {
		return dataEntradaEstagio;
	}

	public void setDataEntradaEstagio(Date dataEntradaEstagio) {
		this.dataEntradaEstagio = dataEntradaEstagio;
	}

	public int getPedidoVenda() {
		return pedidoVenda;
	}

	public void setPedidoVenda(int pedidoVenda) {
		this.pedidoVenda = pedidoVenda;
	}

	public int getQtdeEmProducaoPacote() {
		return qtdeEmProducaoPacote;
	}

	public void setQtdeEmProducaoPacote(int qtdeEmProducaoPacote) {
		this.qtdeEmProducaoPacote = qtdeEmProducaoPacote;
	}

	public int getQtdeEmProducaoOrdem() {
		return qtdeEmProducaoOrdem;
	}

	public void setQtdeEmProducaoOrdem(int qtdeEmProducaoOrdem) {
		this.qtdeEmProducaoOrdem = qtdeEmProducaoOrdem;
	}

	public int getQtdeNecessaria() {
		return qtdeNecessaria;
	}

	public void setQtdeNecessaria(int qtdeNecessaria) {
		this.qtdeNecessaria = qtdeNecessaria;
	}

	public int getCodFamilia() {
		return codFamilia;
	}

	public void setCodFamilia(int codFamilia) {
		this.codFamilia = codFamilia;
	}

	public String getDescFaccao() {
		return descFaccao;
	}

	public void setDescFaccao(String descFaccao) {
		this.descFaccao = descFaccao;
	}
	
	public int getQtdeCarteira() {
		return qtdeCarteira;
	}

	public void setQtdeCarteira(int qtdeCarteira) {
		this.qtdeCarteira = qtdeCarteira;
	}

	public int getQtdeEstoque() {
		return qtdeEstoque;
	}

	public void setQtdeEstoque(int qtdeEstoque) {
		this.qtdeEstoque = qtdeEstoque;
	}

	public int getQtdeSugerida() {
		return qtdeSugerida;
	}

	public void setQtdeSugerida(int qtdeSugerida) {
		this.qtdeSugerida = qtdeSugerida;
	}

	public int getQtdeColetada() {
		return qtdeColetada;
	}

	public void setQtdeColetada(int qtdeColetada) {
		this.qtdeColetada = qtdeColetada;
	}

	public int getQtdeSobraFalta() {
		return qtdeSobraFalta;
	}

	public void setQtdeSobraFalta(int qtdeSobraFalta) {
		this.qtdeSobraFalta = qtdeSobraFalta;
	}

	public String getDescEstagiosDecoracoes() {
		return descEstagiosDecoracoes;
	}

	public void setDescEstagiosDecoracoes(String descEstagiosDecoracoes) {
		this.descEstagiosDecoracoes = descEstagiosDecoracoes;
	}

	public int getEmConserto() {
		return emConserto;
	}

	public void setEmConserto(int emConserto) {
		this.emConserto = emConserto;
	}
}