package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_021")
public class PlanoMestrePreOrdemItem {

	@Id
	public long id;

	@Column(name="num_id_ordem")
	public long idOrdem;	
	
	@Column(name="num_plano_mestre")
	public long idPlanoMestre;
	
	public String sub;
	public String item;
	public int quantidade;
	
}