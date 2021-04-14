package br.com.live.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="orion_010")
@SequenceGenerator(name = "ID_ORION_010", sequenceName = "ID_ORION_010", initialValue = 1, allocationSize = 1)
public class PlanoMestre {

	public final static int EM_ANALISE = 0;
	public final static int CONFIRMADO = 1;	
	
	@Id		
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ORION_010")
	@Column(name="num_plano_mestre")
	public long id;

	@Column(name="descricao")
    public String descricao;
	
	@Column(name="data")
    public Date data;    
    
    @Column(name="situacao")
    public int situacao;
    
    public PlanoMestre() {
    	this.id = 0;
    	this.descricao = "";
    	this.data = new Date();
    	this.situacao = PlanoMestre.EM_ANALISE;
    }
           
    public PlanoMestre(String descricao) {
    	this();
    	this.descricao = descricao;
    }
    
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}
}