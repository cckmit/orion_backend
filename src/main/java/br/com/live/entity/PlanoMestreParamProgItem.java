package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="orion_017")
@SequenceGenerator(name = "ID_ORION_017", sequenceName = "ID_ORION_017", initialValue = 1, allocationSize = 1)
public class PlanoMestreParamProgItem {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ORION_017")
	public long id;
	
	@Column(name="num_plano_mestre")
	public long idPlanoMestre;
	
	@Column(name="num_item_plano_mestre")
	public long idItemPlanoMestre;
	
	public int alternativa;
	public int roteiro;
	public int periodo;
	public int multiplicador;
	
}
