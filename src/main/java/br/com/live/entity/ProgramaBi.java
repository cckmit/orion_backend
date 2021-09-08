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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaModulo() {
		return areaModulo;
	}

	public void setAreaModulo(String areaModulo) {
		this.areaModulo = areaModulo;
	}

	public int getAtividade() {
		return atividade;
	}

	public void setAtividade(int atividade) {
		this.atividade = atividade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFerramenta() {
		return ferramenta;
	}

	public void setFerramenta(String ferramenta) {
		this.ferramenta = ferramenta;
	}

	public String getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}

	public String getPlanilha() {
		return planilha;
	}

	public void setPlanilha(String planilha) {
		this.planilha = planilha;
	}

	public String getExtrator() {
		return extrator;
	}

	public void setExtrator(String extrator) {
		this.extrator = extrator;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}		
}
