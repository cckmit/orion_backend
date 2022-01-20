package br.com.live.body;

import java.util.List;

import br.com.live.util.ConteudoChaveNumerica;

public class BodyTarefas {
	
	public int id;
	public int tipo;
	public int sistema;
	public int origem;
	public int usuarioSolicitante;
	public int usuarioAtribuido;	
	public String titulo;
	public String assunto;
	public int situacao;
	public long anexo;
	public float tempoEstimado;	
	public String dataPrevista;
	public int numDocInterno;
	public int numDocFornecedor;
	
	public List<ConteudoChaveNumerica> usuarios;
	public String dataInicio;
	public String dataFim;
}
