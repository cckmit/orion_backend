package br.com.live.util;

public class StatusGravacao {

	private boolean concluido;
	private String mensagem;
	
	public StatusGravacao (boolean concluido, String mensagem) {
		this.concluido = concluido;
		this.mensagem = mensagem;
	}
	
	public boolean isConcluido() {
		return concluido;
	}

	public String getMensagem() {
		return mensagem;
	}
}
