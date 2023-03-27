package br.com.live.model;

public class ConsultaTitulosComissao {
	
	
	public int portador;
	public String dataEmissao;
	public String representante;
	public int tipoTitulo;
	public String cliente;
	public String titulo;
	public String vencimento;
	public float percComissao;
	public float valorEmAberto;
	public float valorComissao;
	public float mesAnterior;
	public float mesAtual;
	public float saldo;
	public String dataInicio;
	public String dataAnterior;
	
	public int getPortador() {
		return portador;
	}
	public void setPortador(int portador) {
		this.portador = portador;
	}
	public String getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public String getRepresentante() {
		return representante;
	}
	public void setRepresentante(String representante) {
		this.representante = representante;
	}
	public int getTipoTitulo() {
		return tipoTitulo;
	}
	public void setTipoTitulo(int tipoTitulo) {
		this.tipoTitulo = tipoTitulo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getVencimento() {
		return vencimento;
	}
	public void setVencimento(String vencimento) {
		this.vencimento = vencimento;
	}
	public float getPercComissao() {
		return percComissao;
	}
	public void setPercComissao(float percComissao) {
		this.percComissao = percComissao;
	}
	public float getValorEmAberto() {
		return valorEmAberto;
	}
	public void setValorEmAberto(float valorEmAberto) {
		this.valorEmAberto = valorEmAberto;
	}
	public float getValorComissao() {
		return valorComissao;
	}
	public void setValorComissao(float valorComissao) {
		this.valorComissao = valorComissao;
	}
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getDataAnterior() {
		return dataAnterior;
	}
	public void setDataAnterior(String dataAnterior) {
		this.dataAnterior = dataAnterior;
	}
	public float getMesAnterior() {
		return mesAnterior;
	}
	public void setMesAnterior(float mesAnterior) {
		this.mesAnterior = mesAnterior;
	}
	public float getMesAtual() {
		return mesAtual;
	}
	public void setMesAtual(float mesAtual) {
		this.mesAtual = mesAtual;
	}
	public float getSaldo() {
		return saldo;
	}
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	public ConsultaTitulosComissao() {
		
	}
	public ConsultaTitulosComissao(int portador, String dataEmissao, String representante, int tipoTitulo, String cliente, String titulo,
			String vencimento, float percComissao, float valorEmAberto, float valorComissao, float mesAnterior, float mesAtual, float saldo, String dataInicio, String dataAnterior) {
		
		this.portador = portador;
		this.dataEmissao = dataEmissao;
		this.representante = representante;
		this.tipoTitulo = tipoTitulo;
		this.cliente = cliente;
		this.titulo = titulo;
		this.vencimento = vencimento;
		this.percComissao = percComissao;
		this.valorEmAberto = valorEmAberto;
		this.valorComissao = valorComissao;
		this.mesAnterior = mesAnterior;
		this.mesAtual = mesAtual;
		this.saldo = saldo;
		this.dataInicio = dataInicio;
		this.dataAnterior = dataAnterior;
	}
}
