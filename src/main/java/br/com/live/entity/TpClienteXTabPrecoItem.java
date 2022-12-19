package br.com.live.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_com_281")
public class TpClienteXTabPrecoItem {
	
	@Id
	@Column (name = "id_item")
	public long idItem;
	
	@Column (name = "id_capa")
	public String idCapa;
	
	public int catalogo;
	
	@Column (name = "tipo_cliente")
	public int tipo_cliente; // TODO - ARRUMAR PARA tipoCliente
	
	@Column(name = "col_tab_entr")
	public int colTabEntr;
	
	@Column(name = "mes_tab_entr")
	public int mesTabEntr;
	
	@Column(name = "seq_tab_entr")
	public int seqTabEntr;
	
	@Column (name = "periodo_ini")
	public Date periodoIni;
	
	@Column (name = "periodo_fim")
	public Date periodoFim;
	
	
	public long getIdItem() {
		return idItem;
	}

	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}

	public int getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(int catalogo) {
		this.catalogo = catalogo;
	}

	public int getTipo_cliente() {
		return tipo_cliente;
	}

	public void setTipo_cliente(int tipo_cliente) {
		this.tipo_cliente = tipo_cliente;
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

	public Date getPeriodoIni() {
		return periodoIni;
	}

	public void setPeriodoIni(Date periodoIni) {
		this.periodoIni = periodoIni;
	}

	public Date getPeriodoFim() {
		return periodoFim;
	}
	
	public void setPeriodoFim(Date periodoFim) {
		this.periodoFim = periodoFim;
	}
	
	public String getIdCapa() {
		return idCapa;
	}

	public void setIdCapa(String idCapa) {
		this.idCapa = idCapa;
	}

	public TpClienteXTabPrecoItem() {
		
	}

	public TpClienteXTabPrecoItem(long idItem, String idCapa, int catalogo, int tipo_cliente, int colTabEntr, int mesTabEntr,
			int seqTabEntr, Date periodoIni, Date periodoFim) {

		this.idItem = idItem;
		this.idCapa = idCapa;
		this.catalogo = catalogo;
		this.tipo_cliente = tipo_cliente;
		this.colTabEntr = colTabEntr;
		this.mesTabEntr = mesTabEntr;
		this.seqTabEntr = seqTabEntr;
		this.periodoIni = periodoIni;
		this.periodoFim = periodoFim;
	}
}