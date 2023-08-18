package br.com.live.producao.model;

public class ConsultaMovimentacoesCaixaPreta {
	
	public int id;
	public int idCaixa;
	public String codCaixa;
	public String centroCusto;
	public String descricao;
	public String dataCadastro;
	public String ultimaAlteracao;
	public String situacao;
	public String localDestino;
	public String data;
	public String hora;
	public String usuario;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdCaixa() {
		return idCaixa;
	}
	public void setIdCaixa(int idCaixa) {
		this.idCaixa = idCaixa;
	}
	public String getCodCaixa() {
		return codCaixa;
	}
	public void setCodCaixa(String codCaixa) {
		this.codCaixa = codCaixa;
	}
	public String getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(String centroCusto) {
		this.centroCusto = centroCusto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getLocalDestino() {
		return localDestino;
	}
	public void setLocalDestino(String localDestino) {
		this.localDestino = localDestino;
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
	
	public ConsultaMovimentacoesCaixaPreta() {
		
	}
	
	public ConsultaMovimentacoesCaixaPreta(int id, int idCaixa, String codCaixa, String centroCusto, String descricao,
			String dataCadastro, String ultimaAlteracao, String situacao, String localDestino, String data, String hora,
			String usuario) {
		
		this.id = id;
		this.idCaixa = idCaixa;
		this.codCaixa = codCaixa;
		this.centroCusto = centroCusto;
		this.descricao = descricao;
		this.dataCadastro = dataCadastro;
		this.ultimaAlteracao = ultimaAlteracao;
		this.situacao = situacao;
		this.localDestino = localDestino;
		this.data = data;
		this.hora = hora;
		this.usuario = usuario;
	}

}
