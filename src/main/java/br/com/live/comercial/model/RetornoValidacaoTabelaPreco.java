package br.com.live.comercial.model;

import java.util.List;

public class RetornoValidacaoTabelaPreco {
	public List<ImportarTabPreco> itensEditados;
	public int habilitaExec;
	
	public List<ImportarTabPreco> getItensTabela() {
		return itensEditados;
	}
	public void setItensTabela(List<ImportarTabPreco> itensEditados) {
		this.itensEditados = itensEditados;
	}
	public int getHabilitaExec() {
		return habilitaExec;
	}
	public void setHabilitaExec(int habilitaExec) {
		this.habilitaExec = habilitaExec;
	}
	
	public RetornoValidacaoTabelaPreco() {
		
	}

	public RetornoValidacaoTabelaPreco(List<ImportarTabPreco> itensEditados, int habilitaExec) {
		this.itensEditados = itensEditados;
		this.habilitaExec = habilitaExec;
	}
}
