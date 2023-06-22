package br.com.live.comercial.model;

import java.util.Date;

public class ConsultaMinutaTransporte {
	public int nota;
	public int empresa;
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
	public int volume;
	public int minuta;
	public int tipoMinuta;
	public int status;
	public String usuario;
	public Date data;
	
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
	public int getVolume() { return volume; }
	public void setVolume(int volume) { this.volume = volume; }
	public int getMinuta() { return minuta;	}
	public void setMinuta(int minuta) { this.minuta = minuta; }

	public int getTipoMinuta() {
		return tipoMinuta;
	}

	public void setTipoMinuta(int tipoMinuta) {
		this.tipoMinuta = tipoMinuta;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getEmpresa() {
		return empresa;
	}

	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public ConsultaMinutaTransporte() {
		
	}

	public ConsultaMinutaTransporte(int nota, int empresa, int serie, Date emissao, int pedido, String cliente, int caixas, Date libPaypal, float pesoBruto, float valorNota, String cidade, String estado, int caixa, String local, String endereco, int volume, int minuta, int tipoMinuta, int status, String usuario, Date data) {
		this.nota = nota;
		this.empresa = empresa;
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
		this.volume = volume;
		this.minuta = minuta;
		this.tipoMinuta = tipoMinuta;
		this.status = status;
		this.usuario = usuario;
		this.data = data;
	}
}
