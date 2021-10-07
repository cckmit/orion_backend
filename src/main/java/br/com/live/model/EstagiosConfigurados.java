package br.com.live.model;

public class EstagiosConfigurados {
	private int sequencia;
	private int estagio;
	private int lead;
	private String descEstagio;
	private String dataInicio;
	private String dataFim;
	
	public int getSequencia() {
		return sequencia;
	}
	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}
	public int getEstagio() {
		return estagio;
	}
	public void setEstagio(int estagio) {
		this.estagio = estagio;
	}
	public int getLead() {
		return lead;
	}
	public void setLead(int lead) {
		this.lead = lead;
	}
	public String getDescEstagio() {
		return descEstagio;
	}
	public void setDescEstagio(String descEstagio) {
		this.descEstagio = descEstagio;
	}
	
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getDataFim() {
		return dataFim;
	}
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	public EstagiosConfigurados( ) {
		
	}
	public EstagiosConfigurados(int sequencia, int estagio, int lead, String descEstagio, String dataInicio, String dataFim) {
		this.sequencia = sequencia;
		this.estagio = estagio;
		this.lead = lead;
		this.descEstagio = descEstagio;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

}
