package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_017")
public class PlanoMestreParamProgItem {
	
	@Id	
	@Column(name="num_item_plano_mestre")
	public long idItemPlanoMestre;
		
	@Column(name="num_plano_mestre")
	public long idPlanoMestre;
		
	public int alternativa;
	public int roteiro;
	public int periodo;
	public int multiplicador;
	
}
