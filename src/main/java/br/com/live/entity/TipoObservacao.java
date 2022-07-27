package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_cfc_221")
public class TipoObservacao {
	
    @Id
    public long id;
    
    public String descricao;
    
    @Column(name = "necessita_liberacao")
    public int necessitaLiberacao;
    
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

	public int getNecessitaLiberacao() {
		return necessitaLiberacao;
	}

	public void setNecessitaLiberacao(int necessitaLiberacao) {
		this.necessitaLiberacao = necessitaLiberacao;
	}

	TipoObservacao() {
    	
    }

	public TipoObservacao(long id, String descricao, int necessitaLiberacao) {
		this.id = id;
		this.descricao = descricao;
		this.necessitaLiberacao = necessitaLiberacao;
	}
}
