package br.com.live.administrativo.model;

public class Bens {
	
	public int id;
	public String descricao;
	public int code;
	public int sequence;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public Bens() {
		
	}

	public Bens(int id, String descricao, int code, int sequence) {
		this.id = id;
		this.descricao = descricao;
		this.code = code;
		this.sequence = sequence;
	}
}
