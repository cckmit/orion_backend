package br.com.live.model;

public class ConsultaCapacidadeArtigosEnderecos {
	public int artigo;
	public String descricao;
	public int quantPecCesto;
	public int perc0;
	public int perc1;
	public int perc40;
	public int perc41;
	public int perc94;
	public int perc95;
	public int perc99;
	
	public int getArtigo() {
		return artigo;
	}
	public void setArtigo(int artigo) {
		this.artigo = artigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getQuantPecCesto() {
		return quantPecCesto;
	}
	public void setQuantPecCesto(int quantPecCesto) {
		this.quantPecCesto = quantPecCesto;
	}
	public int getPerc0() {
		return perc0;
	}
	public void setPerc0(int perc0) {
		this.perc0 = perc0;
	}
	public int getPerc1() {
		return perc1;
	}
	public void setPerc1(int perc1) {
		this.perc1 = perc1;
	}
	public int getPerc40() {
		return perc40;
	}
	public void setPerc40(int perc40) {
		this.perc40 = perc40;
	}
	public int getPerc41() {
		return perc41;
	}
	public void setPerc41(int perc41) {
		this.perc41 = perc41;
	}
	public int getPerc94() {
		return perc94;
	}
	public void setPerc94(int perc94) {
		this.perc94 = perc94;
	}
	public int getPerc95() {
		return perc95;
	}
	public void setPerc95(int perc95) {
		this.perc95 = perc95;
	}
	public int getPerc99() {
		return perc99;
	}
	public void setPerc99(int perc99) {
		this.perc99 = perc99;
	}
	
	public ConsultaCapacidadeArtigosEnderecos() {
	}
	
	public ConsultaCapacidadeArtigosEnderecos(int artigo, String descricao, int quantPecCesto, int perc0, int perc1,
			int perc40, int perc41, int perc94, int perc95, int perc99) {
		this.artigo = artigo;
		this.descricao = descricao;
		this.quantPecCesto = quantPecCesto;
		this.perc0 = perc0;
		this.perc1 = perc1;
		this.perc40 = perc40;
		this.perc41 = perc41;
		this.perc94 = perc94;
		this.perc95 = perc95;
		this.perc99 = perc99;
	}
}
