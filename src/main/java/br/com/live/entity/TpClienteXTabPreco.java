package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_com_280")
public class TpClienteXTabPreco {
	
	@Id	
	public String id;
	public int catalogo;
	
	@Column(name = "tipo_cliente")
	public int tipoCliente;
	
	@Column(name = "col_tab_entr")
	public int colTabEntr;
	
	@Column(name = "mes_tab_entr")
	public int mesTabEntr;
	
	@Column(name = "seq_tab_entr")
	public int seqTabEntr;
	
	@Column(name = "num_dias")
	public int numDias;
	
	@Column(name = "num_interno")
	public int numInterno;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(int catalogo) {
		this.catalogo = catalogo;
	}

	public int getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(int tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public int getColTabEntr() {
		return colTabEntr;
	}

	public void setColTabEntr(int colTabEntr) {
		this.colTabEntr = colTabEntr;
	}

	public int getMesTabEntr() {
		return mesTabEntr;
	}

	public void setMesTabEntr(int mesTabEntr) {
		this.mesTabEntr = mesTabEntr;
	}

	public int getSeqTabEntr() {
		return seqTabEntr;
	}

	public void setSeqTabEntr(int seqTabEntr) {
		this.seqTabEntr = seqTabEntr;
	}

	public int getNumDias() {
		return numDias;
	}

	public void setNumDias(int numDias) {
		this.numDias = numDias;
	}
	
	public int getNumInterno() {
		return numInterno;
	}

	public void setNumInterno(int numInterno) {
		this.numInterno = numInterno;
	}

	public TpClienteXTabPreco() {
		
	}
	
	public TpClienteXTabPreco(int catalogo, int tipoCliente, int colTabEntr, int mesTabEntr, int seqTabEntr, int numDias, int numInterno){
		this.id = catalogo + "-" + tipoCliente;
		this.catalogo = catalogo;
		this.tipoCliente = tipoCliente;
		this.colTabEntr = colTabEntr;
		this.mesTabEntr = mesTabEntr;
		this.seqTabEntr = seqTabEntr;
		this.numDias = numDias;
		this.numInterno = numInterno;
	}
}
