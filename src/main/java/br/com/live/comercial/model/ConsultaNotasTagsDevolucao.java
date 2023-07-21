package br.com.live.comercial.model;

public class ConsultaNotasTagsDevolucao {
	
	public int id;
	public String data;
	public String hora;
	public String usuario;
	public int nfDevolucao;
	public String tipoDevolucao;
	public String motivo;
	public String transacao;
	public int caixa;
	public String produto;
	public String codBarrasTag;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getNfDevolucao() {
		return nfDevolucao;
	}
	public void setNfDevolucao(int nfDevolucao) {
		this.nfDevolucao = nfDevolucao;
	}
	public String getTipoDevolucao() {
		return tipoDevolucao;
	}
	public void setTipoDevolucao(String tipoDevolucao) {
		this.tipoDevolucao = tipoDevolucao;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getTransacao() {
		return transacao;
	}
	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}
	public int getCaixa() {
		return caixa;
	}
	public void setCaixa(int caixa) {
		this.caixa = caixa;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}	
	public String getCodBarrasTag() {
		return codBarrasTag;
	}
	public void setCodBarrasTag(String codBarrasTag) {
		this.codBarrasTag = codBarrasTag;
	}
	public ConsultaNotasTagsDevolucao() {
		
	}
	public ConsultaNotasTagsDevolucao(int id, String data, String hora, String usuario, int nfDevolucao,
			String tipoDevolucao, String motivo, String transacao, int caixa, String produto, String codBarrasTag) {
		
		this.id = id;
		this.data = data;
		this.hora = hora;
		this.usuario = usuario;
		this.nfDevolucao = nfDevolucao;
		this.tipoDevolucao = tipoDevolucao;
		this.motivo = motivo;
		this.transacao = transacao;
		this.caixa = caixa;
		this.produto = produto;
		this.codBarrasTag = codBarrasTag;
	}
	

}
