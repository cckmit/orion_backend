package br.com.live.util;

public class ErrorMessageException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ErrorMessageException(String mensagem) {
		super(mensagem);
	}
}