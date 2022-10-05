package br.com.live.model;

import java.util.Date;

public class ConsultaMinutaTransporte {
	public int nota;
	public int serie;
	public Date emissao;
	public int pedido;
	public String cliente;
	public int caixas;
	public Date libPaypal;
	public float pesoBruto;
	public float valorNota;
	public String cidade;
	public String estado;
	public int caixa;
	public String local;
	public String endereco;
	
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	public int getSerie() {
		return serie;
	}
	public void setSerie(int serie) {
		this.serie = serie;
	}
	public Date getEmissao() {
		return emissao;
	}
	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}
	public int getPedido() {
		return pedido;
	}
	public void setPedido(int pedido) {
		this.pedido = pedido;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public int getCaixas() {
		return caixas;
	}
	public void setCaixas(int caixas) {
		this.caixas = caixas;
	}
	public Date getLibPaypal() {
		return libPaypal;
	}
	public void setLibPaypal(Date libPaypal) {
		this.libPaypal = libPaypal;
	}
	public float getPesoBruto() {
		return pesoBruto;
	}
	public void setPesoBruto(float pesoBruto) {
		this.pesoBruto = pesoBruto;
	}
	public float getValorNota() {
		return valorNota;
	}
	public void setValorNota(float valorNota) {
		this.valorNota = valorNota;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getCaixa() {
		return caixa;
	}
	public void setCaixa(int caixa) {
		this.caixa = caixa;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public ConsultaMinutaTransporte() {
		
	}
	
	public ConsultaMinutaTransporte(int nota, int serie, Date emissao, int pedido, String cliente, int caixas,
			Date libPaypal, float pesoBruto, float valorNota, String cidade, String estado, int caixa, String local, String endereco) {
		this.nota = nota;
		this.serie = serie;
		this.emissao = emissao;
		this.pedido = pedido;
		this.cliente = cliente;
		this.caixas = caixas;
		this.libPaypal = libPaypal;
		this.pesoBruto = pesoBruto;
		this.valorNota = valorNota;
		this.cidade = cidade;
		this.estado = estado;
		this.caixa = caixa;
		this.local = local;
		this.endereco = endereco;
	}	
}
