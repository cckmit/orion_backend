package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_com_210")
public class TipoClientePorCanal {
	
	@Id
	public int id;
	
	@Column(name = "id_canal")
	public int idCanal;
	
	@Column(name = "tipo_cliente")
	public int tipoCliente;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCanal() {
		return idCanal;
	}

	public void setIdCanal(int idCanal) {
		this.idCanal = idCanal;
	}

	public int getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(int tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	
	public TipoClientePorCanal() {
		
	}

	public TipoClientePorCanal(int id, int idCanal, int tipoCliente) {
		
		this.id = id;
		this.idCanal = idCanal;
		this.tipoCliente = tipoCliente;
	}
	

}
