package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_exp_001")
public class VariacaoPesoArtigo {
	
	@Id
	public long id;
	public float variacao;
	
	public VariacaoPesoArtigo() {
	}

	public VariacaoPesoArtigo(long id, float variacao) {
		this.id = id;
		this.variacao = variacao;
	}
}
