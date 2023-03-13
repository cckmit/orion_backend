package br.com.live.model;

import java.util.List;

public class RetornoLancamentoCont {
	
	public int status;
	public List<ConsultaLanctoContabeis> listCont;
	
	public RetornoLancamentoCont(int status, List<ConsultaLanctoContabeis> listCont) {
		this.status = status;
		this.listCont = listCont;
	}
	
	public RetornoLancamentoCont() {
		
	}

}
