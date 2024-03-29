package br.com.live.producao.model;

import java.util.Date;

public class ConsultaPreOrdemProducao {

	public long id;
	public long idPlanoMestre;
	public String referencia;
	public String descrReferencia;
	public int periodo;
	public String alternativa;
	public int roteiro;
	public int quantidade;
	public String deposito;
	public int ordemGerada;
	public String status;
	public String mensagemGravacaoOrdem;
	public Date dataEmbarque;
	public int qtdeEstagioCritico;
	public double tempoProducaoUnit;
	public int qtdeAtendidaPelaReservaTecido;	
		
	public int getQtdeEstagioCritico() {
		return qtdeEstagioCritico;
	}
	public void setQtdeEstagioCritico(int qtdeEstagioCritico) {
		this.qtdeEstagioCritico = qtdeEstagioCritico;
	}
	public double getTempoProducaoUnit() {
		return tempoProducaoUnit;
	}
	public void setTempoProducaoUnit(double tempoProducaoUnit) {
		this.tempoProducaoUnit = tempoProducaoUnit;
	}	
	public long getIdPlanoMestre() {
		return idPlanoMestre;
	}
	public void setIdPlanoMestre(long idPlanoMestre) {
		this.idPlanoMestre = idPlanoMestre;
	}
	public String getMensagemGravacaoOrdem() {
		return mensagemGravacaoOrdem;
	}
	public void setMensagemGravacaoOrdem(String mensagemGravacaoOrdem) {
		this.mensagemGravacaoOrdem = mensagemGravacaoOrdem;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getOrdemGerada() {
		return ordemGerada;
	}
	public void setOrdemGerada(int ordemGerada) {
		this.ordemGerada = ordemGerada;
	}
	public String getDeposito() {
		return deposito;
	}
	public void setDeposito(String deposito) {
		this.deposito = deposito;
	}
	public String observacao;
	
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getDescrReferencia() {
		return descrReferencia;
	}
	public void setDescrReferencia(String descrReferencia) {
		this.descrReferencia = descrReferencia;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public String getAlternativa() {
		return alternativa;
	}
	public void setAlternativa(String alternativa) {
		this.alternativa = alternativa;
	}
	public int getRoteiro() {
		return roteiro;
	}
	public void setRoteiro(int roteiro) {
		this.roteiro = roteiro;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public Date getDataEmbarque() {
		return dataEmbarque;
	}
	public void setDataEmbarque(Date dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}
	public int getQtdeAtendidaPelaReservaTecido() {
		return qtdeAtendidaPelaReservaTecido;
	}
	public void setQtdeAtendidaPelaReservaTecido(int qtdeAtendidaPelaReservaTecido) {
		this.qtdeAtendidaPelaReservaTecido = qtdeAtendidaPelaReservaTecido;
	}	
}