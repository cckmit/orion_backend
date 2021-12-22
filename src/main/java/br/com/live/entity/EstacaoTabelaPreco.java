package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_073")
public class EstacaoTabelaPreco {

	@Id
	public String id;
	
	@Column(name = "cod_estacao")
	public long codEstacao;
	
	@Column(name = "col_tab")
	public int colTab;
	
	@Column(name = "mes_tab")
	public int mesTab;
	
	@Column(name = "seq_tab")
	public int seqTab;
	
	public EstacaoTabelaPreco() {
		
	}

	public EstacaoTabelaPreco(long codEstacao, int colTab, int mesTab, int seqTab) {
		this.id = codEstacao + "-" + colTab + "-" + mesTab + "-" + seqTab;
		this.codEstacao = codEstacao;
		this.colTab = colTab;
		this.mesTab = mesTab;
		this.seqTab = seqTab;
	}
}
