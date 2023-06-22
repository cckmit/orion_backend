package br.com.live.produto.model;

public class ConsultaDadosFilete {

	public int tipoCorte;
	public int tubularAberto;
	public double comprimentoFilete;
	public double larguraFilete;
	public double larguraRisco;
	public double larguraTecido;
		
	public ConsultaDadosFilete() {
		this.tipoCorte = 0;
		this.tubularAberto = 0;
		this.comprimentoFilete = 0.0;
		this.larguraFilete = 0.0;
		this.larguraRisco = 0.0;
		this.larguraTecido = 0.0;		
	}
	
	public int getTipoCorte() {
		return tipoCorte;
	}
	public void setTipoCorte(int tipoCorte) {
		this.tipoCorte = tipoCorte;
	}
	public int getTubularAberto() {
		return tubularAberto;
	}
	public void setTubularAberto(int tubularAberto) {
		this.tubularAberto = tubularAberto;
	}
	public double getComprimentoFilete() {
		return comprimentoFilete;
	}
	public void setComprimentoFilete(double comprimentoFilete) {
		this.comprimentoFilete = comprimentoFilete;
	}
	public double getLarguraFilete() {
		return larguraFilete;
	}
	public void setLarguraFilete(double larguraFilete) {
		this.larguraFilete = larguraFilete;
	}
	public double getLarguraRisco() {
		return larguraRisco;
	}
	public void setLarguraRisco(double larguraRisco) {
		this.larguraRisco = larguraRisco;
	}
	public double getLarguraTecido() {
		return larguraTecido;
	}
	public void setLarguraTecido(double larguraTecido) {
		this.larguraTecido = larguraTecido;
	}
}    