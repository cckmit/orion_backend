package br.com.live.produto.model;

import java.util.Date;

public class Embarque {

	public long id;	
	public String descricao;
	public String grupo;
	public String subgrupo;
	public String item;
	public String grupoEmbarque;
	public Date dataEmbarque;
	public int existeEmbarque;
	
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
	
	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getSubgrupo() {
		return subgrupo;
	}

	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getGrupoEmbarque() {
		return grupoEmbarque;
	}

	public void setGrupoEmbarque(String grupoEmbarque) {
		this.grupoEmbarque = grupoEmbarque;
	}

	public Date getDataEmbarque() {
		return dataEmbarque;
	}

	public void setDataEmbarque(Date dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}
	
	public int getExisteEmbarque() {
		return existeEmbarque;
	}

	public void setExisteEmbarque(int existeEmbarque) {
		this.existeEmbarque = existeEmbarque;
	}

	public Embarque () {}

	public Embarque(long id, String descricao, String grupo, String subgrupo, String item, String grupoEmbarque,
			Date dataEmbarque, int existeEmbarque) {
		this.id = id;
		this.descricao = descricao;
		this.grupo = grupo;
		this.subgrupo = subgrupo;
		this.item = item;
		this.grupoEmbarque = grupoEmbarque;
		this.dataEmbarque = dataEmbarque;
		this.existeEmbarque = existeEmbarque;
	}
}
