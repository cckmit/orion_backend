package br.com.live.producao.body;

import java.util.List;

import br.com.live.util.ConteudoChaveNumerica;

public class BodyObservacaoPorOp {
	public int estagio;
	public int ordemProducao;
	public int ordemConfeccao;
	public int quantidade;
	public int tipoObservacao;
	public String obsAdicional;
	
	public List<ConteudoChaveNumerica> listEstagio;
	public List<ConteudoChaveNumerica> listOrdemProducao;
	
}
