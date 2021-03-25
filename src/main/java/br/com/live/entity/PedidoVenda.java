package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_vi_pedidos")
public class PedidoVenda {

	@Id	
	@Column(name="pedido_venda")
	public int id;
	
	@Column(name="num_periodo_prod")	 
	public int periodo;
	
}
