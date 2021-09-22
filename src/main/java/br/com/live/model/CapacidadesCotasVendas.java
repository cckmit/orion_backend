package br.com.live.model;

import java.util.Date;
import java.util.List;

import br.com.live.util.ConteudoChaveNumerica;

public class CapacidadesCotasVendas {
	private int periodo;
	private Date dataInicial;
	private Date dataFinal;
	private int linha;
	private String descLinha;
	private int colecao;
	private String descColecao;
	private int minutos;
	private int pecas;
	private String id;
	private String depositos;
	private List<ConteudoChaveNumerica> listaDepositos;
	
	private void parseListaDepositos(String depositos) {
		
		
	}
	
	public String getDepositos() {
		return depositos;
	}
	public void setDepositos(String depositos) {
		this.depositos = depositos;
	}
	public List<ConteudoChaveNumerica> getListaDepositos() {
		return listaDepositos;
	}
	public void setListaDepositos(List<ConteudoChaveNumerica> listaDepositos) {
		this.listaDepositos = listaDepositos;
	}
	
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public int getLinha() {
		return linha;
	}
	public void setLinha(int linha) {
		this.linha = linha;
	}
	public String getDescLinha() {
		return descLinha;
	}
	public void setDescLinha(String descLinha) {
		this.descLinha = descLinha;
	}
	public int getColecao() {
		return colecao;
	}
	public void setColecao(int colecao) {
		this.colecao= colecao;
	}
	public String getDescColecao() {
		return descColecao;
	}
	public void setDescColecao(String descColecao) {
		this.descColecao = descColecao;
	}
	public int getMinutos() {
		return minutos;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	public int getPecas() {
		return pecas;
	}
	public void setPecas(int pecas) {
		this.pecas = pecas;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
