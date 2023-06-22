package br.com.live.produto.model;

public class RetornoTokenFluxogama {
	boolean sucesso;
	Token retorno;
	
	public boolean isSucesso() {
		return sucesso;
	}
	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}
	public Token getRetorno() {
		return retorno;
	}
	public void setRetorno(Token retorno) {
		this.retorno = retorno;
	}
	
	public RetornoTokenFluxogama() {
		
	}
	
	public RetornoTokenFluxogama(boolean sucesso, Token retorno) {
		this.sucesso = sucesso;
		this.retorno = retorno;
	}
}
