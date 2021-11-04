package br.com.live.util;

public class StatusGravacao {

	private boolean concluido;
	private String mensagem;
	private String mensagemCompleta;
	
	public StatusGravacao (boolean concluido, String mensagem) {
		this.concluido = concluido;
		this.mensagem = mensagem;
		this.mensagemCompleta = mensagem;
	}

	public StatusGravacao (boolean concluido, String mensagem, String mensagemCompleta) {
		this.concluido = concluido;
		this.mensagem = mensagem;
		this.mensagemCompleta = mensagemCompleta;
	}
	
	public boolean isConcluido() {
		return concluido;
	}

	public String getMensagem() {
		return mensagem;
	}
	
	public String getMensagemCompleta() {
		return mensagemCompleta;
	}
}
