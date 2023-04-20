package br.com.live.util;

public class MessageErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MessageErrorException(String mensagem) {
		super(mensagem);
	}
}