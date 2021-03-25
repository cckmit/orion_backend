package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_vi_colecoes_permanentes")
public class ColecaoPermanente {

	@Id	
	@Column(name="colecao")
	public int id;

    @Column(name="descr_colecao")
    public String descricao;
	
}
