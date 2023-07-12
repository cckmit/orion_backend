package br.com.live.comercial.model;

public class ConsultaTipoClientePorCanal {
	
	public int id;
	public int codigo;
	public int tipoCliente;
	public String descTipoCliente;
	public String descricao;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(int tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	public String getDescTipoCliente() {
		return descTipoCliente;
	}
	public void setDescTipoCliente(String descTipoCliente) {
		this.descTipoCliente = descTipoCliente;
	}	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public ConsultaTipoClientePorCanal() {
		
	}
	
	public ConsultaTipoClientePorCanal(int id, int codigo, int tipoCliente, String descTipoCliente, String descricao) {
		this.id = id;
		this.codigo = codigo;
		this.tipoCliente = tipoCliente;
		this.descTipoCliente = descTipoCliente;
		this.descricao = descricao;
	}
	
}
