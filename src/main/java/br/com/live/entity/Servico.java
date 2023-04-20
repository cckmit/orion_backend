package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_015")
public class Servico {

    @Id
    public int id;

    @Column(name = "nome_servico")
    public String nomeServico;

    public String objetivo;

    @Column(name = "time_responsavel")
    public String timeResponsavel;

    public boolean disponibilidade;

    @Column(name = "tecnicos_fornecedores")
    public String tecnicosFornecedores;
    
    public int gestorResponsavel;

    public Servico(int id, String nomeServico, String objetivo, String timeResponsavel, boolean disponibilidade, String tecnicosFornecedores, int gestorResponsavel) {
        this.id = id;
        this.nomeServico = nomeServico;
        this.objetivo = objetivo;
        this.timeResponsavel = timeResponsavel;
        this.disponibilidade = disponibilidade;
        this.tecnicosFornecedores = tecnicosFornecedores;
        this.gestorResponsavel = gestorResponsavel;
    }

    public Servico(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getTimeResponsavel() {
        return timeResponsavel;
    }

    public void setTimeResponsavel(String timeResponsavel) {
        this.timeResponsavel = timeResponsavel;
    }

    public boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getTecnicosFornecedores() {
        return tecnicosFornecedores;
    }

    public void setTecnicosFornecedores(String tecnicosFornecedores) {
        this.tecnicosFornecedores = tecnicosFornecedores;
    }

	public int getGestorResponsavel() {
		return gestorResponsavel;
	}

	public void setGestorResponsavel(int gestorResponsavel) {
		this.gestorResponsavel = gestorResponsavel;
	}
    
}
