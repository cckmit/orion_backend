package br.com.live.model;

public class ConsultaTitulosComissao {
	
	
	public int portador;
	public String dataEmissao;
	public String representante;
	public int tipoTitulo;
	public String cliente;
	public int titulo;
	public String vencimento;
	public float percComissao;
	public float valorEmAberto;
	public float valorComissao;
	
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
	public int getTitulo() {
		return titulo;
	}
	public void setTitulo(int titulo) {
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
	
	public ConsultaTitulosComissao() {
		
	}
	public ConsultaTitulosComissao(int portador, String dataEmissao, String representante, int tipoTitulo, String cliente, int titulo,
			String vencimento, float percComissao, float valorEmAberto, float valorComissao) {
		
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
	}
}
