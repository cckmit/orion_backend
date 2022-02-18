package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_120")
public class CapacidadeArtigoEndereco {
	@Id	
	public int artigo;
	
	@Column(name = "quant_pecas_cesto")	
	public int qtdePecasCesto;
	
	@Column(name = "quant_perc_0")
	public int qtdePerc0;
	
	@Column(name = "quant_perc_1")
	public int qtdePerc1;
	
	@Column(name = "quant_perc_40")
	public int qtdePerc40;
	
	@Column(name = "quant_perc_41")
	public int qtdePerc41;
	
	@Column(name = "quant_perc_94")
	public int qtdePerc94;
	
	@Column(name = "quant_perc_95")
	public int qtdePerc95;
	
	@Column(name = "quant_perc_99")
	public int qtdePerc99;
	
	public CapacidadeArtigoEndereco() {
	}

	public CapacidadeArtigoEndereco(int artigo, int qtdePecasCesto, int qtdePerc0, int qtdePerc1, int qtdePerc40,
			int qtdePerc41, int qtdePerc94, int qtdePerc95, int qtdePerc99) {
		this.artigo = artigo;
		this.qtdePecasCesto = qtdePecasCesto;
		this.qtdePerc0 = qtdePerc0;
		this.qtdePerc1 = qtdePerc1;
		this.qtdePerc40 = qtdePerc40;
		this.qtdePerc41 = qtdePerc41;
		this.qtdePerc94 = qtdePerc94;
		this.qtdePerc95 = qtdePerc95;
		this.qtdePerc99 = qtdePerc99;
	}
}
