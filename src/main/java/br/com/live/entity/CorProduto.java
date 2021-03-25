package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_vi_cores_produtos")
public class CorProduto {

	@Id
	public int id;
	public String item;
	public String descricao;

	public CorProduto(int id, String item, String descricao) {
		this.id = id;
		this.item = item;
		this.descricao = descricao;
	}

}