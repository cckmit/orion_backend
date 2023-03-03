package br.com.live.model;

import java.util.Date;

public class DadosSequenciamentoDecoracoes {
	private int id;
	private int seqPrioridade;
	private int periodo;
	private int ordemProducao;
	private String referencia;
	private String descricaoReferencia;
	private String cores;
	private int quantidade;
	private String observacao;
	private int codEstagioProx;
	private String descEstagioProx;
	private String estagiosAgrupados;
	private String endereco;
	private Date dataEntrada;
	private double tempoUnitario;
	private double tempoTotal;
	private Date dataInicio;
	private Date dataTermino;

	public DadosSequenciamentoDecoracoes() {}
	
	public DadosSequenciamentoDecoracoes(int id, int seqPrioridade, int periodo, int ordemProducao, String referencia,
			String descricaoReferencia, String cores, int quantidade, String observacao, int codEstagioProx,
			String descEstagioProx, String estagiosAgrupados, String endereco, Date dataEntrada, double tempoUnitario,
			double tempoTotal, Date dataInicio, Date dataTermino) {
		super();		
		this.id = id;
		this.seqPrioridade = seqPrioridade;
		this.periodo = periodo;
		this.ordemProducao = ordemProducao;
		this.referencia = referencia;
		this.descricaoReferencia = descricaoReferencia;
		this.cores = cores;
		this.quantidade = quantidade;
		this.observacao = observacao;
		this.codEstagioProx = codEstagioProx;
		this.descEstagioProx = descEstagioProx;
		this.estagiosAgrupados = estagiosAgrupados;
		this.endereco = endereco;
		this.dataEntrada = dataEntrada;
		this.tempoUnitario = tempoUnitario;
		this.tempoTotal = tempoTotal;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
	}
	
	public DadosSequenciamentoDecoracoes(int seqPrioridade, int periodo, int ordemProducao, String referencia,
			String descricaoReferencia, String cores, int quantidade, String observacao, int codEstagioProx,
			String descEstagioProx, String estagiosAgrupados, String endereco, Date dataEntrada, double tempoUnitario,
			double tempoTotal) {
		super();
		this.id = 0;
		this.seqPrioridade = seqPrioridade;
		this.periodo = periodo;
		this.ordemProducao = ordemProducao;
		this.referencia = referencia;
		this.descricaoReferencia = descricaoReferencia;
		this.cores = cores;
		this.quantidade = quantidade;
		this.observacao = observacao;
		this.codEstagioProx = codEstagioProx;
		this.descEstagioProx = descEstagioProx;
		this.estagiosAgrupados = estagiosAgrupados;
		this.endereco = endereco;
		this.dataEntrada = dataEntrada;
		this.tempoUnitario = tempoUnitario;
		this.tempoTotal = tempoTotal;
		this.dataInicio = null;
		this.dataTermino = null;		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSeqPrioridade() {
		return seqPrioridade;
	}

	public void setSeqPrioridade(int seqPrioridade) {
		this.seqPrioridade = seqPrioridade;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public int getOrdemProducao() {
		return ordemProducao;
	}

	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getDescricaoReferencia() {
		return descricaoReferencia;
	}

	public void setDescricaoReferencia(String descricaoReferencia) {
		this.descricaoReferencia = descricaoReferencia;
	}

	public String getCores() {
		return cores;
	}

	public void setCores(String cores) {
		this.cores = cores;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getCodEstagioProx() {
		return codEstagioProx;
	}

	public void setCodEstagioProx(int codEstagioProx) {
		this.codEstagioProx = codEstagioProx;
	}

	public String getDescEstagioProx() {
		return descEstagioProx;
	}

	public void setDescEstagioProx(String descEstagioProx) {
		this.descEstagioProx = descEstagioProx;
	}

	public String getEstagiosAgrupados() {
		return estagiosAgrupados;
	}

	public void setEstagiosAgrupados(String estagiosAgrupados) {
		this.estagiosAgrupados = estagiosAgrupados;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public double getTempoUnitario() {
		return tempoUnitario;
	}

	public void setTempoUnitario(double tempoUnitario) {
		this.tempoUnitario = tempoUnitario;
	}

	public double getTempoTotal() {
		return tempoTotal;
	}

	public void setTempoTotal(double tempoTotal) {
		this.tempoTotal = tempoTotal;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}		
}