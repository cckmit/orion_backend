package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_045")
public class CapacidadeCotasVendasCapa {
	
	@Id	
	public String id;
	public int periodo;
	public int linha;
	public int categoria;
	
	public CapacidadeCotasVendasCapa() {
		
	}
	
	public CapacidadeCotasVendasCapa(int periodo, int linha, int categoria) {
		this.id = periodo + "-" + linha + "-" + categoria;
		this.periodo = periodo;
		this.linha = linha;
		this.categoria = categoria;
	}

}
