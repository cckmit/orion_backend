package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_bi_001")
public class ProgramaBi {

	@Id	
	public String id;
	
	@Column(name="area_modulo")
	public String areaModulo;
	
	public int atividade;
	public String descricao;
	public String ferramenta;
	public String frequencia;
	public String planilha;
	public String extrator;
	public String help;
	
	public ProgramaBi() {
		
	}
	
	public ProgramaBi(String areaModulo, int atividade, String descricao, String ferramenta, String frequencia, String planilha, String extrator, String help) {
		this.id = areaModulo + String.format("%02d", atividade);
		this.areaModulo = areaModulo;
		this.atividade = atividade;
		this.descricao = descricao;
		this.ferramenta = ferramenta;
		this.frequencia = frequencia;
		this.planilha = planilha;
		this.extrator = extrator;
		this.help = help;
	}
	
}
