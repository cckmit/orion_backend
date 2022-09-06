package br.com.live.model;

public class ConsultaOperacaoXMicromovimentos {

	private long id;
    private int sequencia;
    private int tipo;    
    private String informacao;
    private float tempo;
    private float interferencia;
    private String idMicromovimento;
    private long idTempoMaquina;
    private float tempoTotal;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getSequencia() {
		return sequencia;
	}
	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getInformacao() {
		return informacao;
	}
	public void setInformacao(String informacao) {
		this.informacao = informacao;
	}
	public float getTempo() {
		return tempo;
	}
	public void setTempo(float tempo) {
		this.tempo = tempo;
	}
	public float getInterferencia() {
		return interferencia;
	}
	public void setInterferencia(float interferencia) {
		this.interferencia = interferencia;
	}
	public String getIdMicromovimento() {
		return idMicromovimento;
	}
	public void setIdMicromovimento(String idMicromovimento) {
		this.idMicromovimento = idMicromovimento;
	}
	public long getIdTempoMaquina() {
		return idTempoMaquina;
	}
	public void setIdTempoMaquina(long idTempoMaquina) {
		this.idTempoMaquina = idTempoMaquina;
	}
	public float getTempoTotal() {
		return tempoTotal;
	}
	public void setTempoTotal(float tempoTotal) {
		this.tempoTotal = tempoTotal;
	}
    
    
	
}
