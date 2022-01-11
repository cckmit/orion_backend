package br.com.live.body;

import java.util.List;

import br.com.live.util.ConteudoChaveNumerica;

public class BodyBens {
	public int id;
	public String descricao;
	
	public int tipoMovimentacao;
	public int sequencia;
	public int usuario;
	public String dataEnvio;
	public String cnpjOrigem;
	public String cnpjDestino;
	public String observacao;
	public String notaFiscal;
	public List<ConteudoChaveNumerica> bens;
}
