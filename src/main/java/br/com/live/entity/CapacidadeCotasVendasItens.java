package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_046")
public class CapacidadeCotasVendasItens {
	
	@Id	
	public String id;
	
	@Column(name="id_capacidade_cotas")
	public String idCapa;
	
	public String modelo;
	
	@Column(name="tempo_unitario")
	public float tempoUnitario;
	
	@Column(name="qtde_minutos")
	public float minutos;
	
	@Column(name="qtde_pecas")
	public int pecas;
	
	public CapacidadeCotasVendasItens() {
		
	}
	
	public CapacidadeCotasVendasItens(String idCapa, String modelo, float tempoUnitario, float minutos, int pecas) {
		this.id = idCapa + "-" + modelo;
		this.idCapa = idCapa;
		this.modelo = modelo;
		this.tempoUnitario = tempoUnitario;		
		this.pecas =pecas;
		this.minutos = ((float) pecas * tempoUnitario);
	}

}