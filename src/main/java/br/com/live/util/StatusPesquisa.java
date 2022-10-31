package br.com.live.util;

public class StatusPesquisa {
	
	private boolean encontrou;
	private Object objetoRetorno;
	
	public StatusPesquisa(boolean encontrou, Object objetoRetorno) {
		super();
		this.encontrou = encontrou;
		this.objetoRetorno = objetoRetorno;
	}
	public boolean isEncontrou() {
		return encontrou;
	}
	public void setEncontrou(boolean encontrou) {
		this.encontrou = encontrou;
	}
	public Object getObjetoRetorno() {
		return objetoRetorno;
	}
	public void setObjetoRetorno(Object objetoRetorno) {
		this.objetoRetorno = objetoRetorno;
	}
}
