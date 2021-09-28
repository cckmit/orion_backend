package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_046")
public class CapacidadeCotasVendasItens {
	
	@Id	
	public String id; // id_cotas + referencia + tamanho + cor
	@Column(name="id_capacidade_cotas")
	public long idCapacidadeCota;
	public String referencia;
	public String tamanho;
	public String cor;    
	@Column(name="bloqueio_venda")
	public int bloqueioVenda;

	public CapacidadeCotasVendasItens() {
		
	}

	public CapacidadeCotasVendasItens(long idCapacidadeCota, String referencia, String tamanho, String cor, int bloqueioVenda) {
		this.id = idCapacidadeCota + "-" +  referencia + "-" + tamanho + "-" + cor;
		this.idCapacidadeCota = idCapacidadeCota;
		this.referencia = referencia; 
		this.tamanho = tamanho; 
		this.cor = cor; 
		this.bloqueioVenda = bloqueioVenda;
	}
}