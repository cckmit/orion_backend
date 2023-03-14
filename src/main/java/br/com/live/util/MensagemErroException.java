package br.com.live.util;

public class MensagemErroException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MensagemErroException(String mensagem) {
		super(mensagem);
	}
}