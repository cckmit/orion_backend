package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_004")
public class MetasPorUsuario {

	@Id
	public String id;

	@Column(name = "codigo_usuario")
	public int codUsuario;

	@Column(name = "tipo_meta")
	public int tipoMeta;

	@Column(name = "mes_1")
	public float mes1;
	
	@Column(name = "mes_2")
	public float mes2;
	
	@Column(name = "mes_3")
	public float mes3;
	
	@Column(name = "mes_4")
	public float mes4;
	
	@Column(name = "mes_5")
	public float mes5;
	
	@Column(name = "mes_6")
	public float mes6;
	
	@Column(name = "mes_7")
	public float mes7;
	
	@Column(name = "mes_8")
	public float mes8;
	
	@Column(name = "mes_9")
	public float mes9;
	
	@Column(name = "mes_10")
	public float mes10;
	
	@Column(name = "mes_11")
	public float mes11;
	
	@Column(name = "mes_12")
	public float mes12;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}

	public int getTipoMeta() {
		return tipoMeta;
	}

	public void setTipoMeta(int tipoMeta) {
		this.tipoMeta = tipoMeta;
	}

	public float getMes1() {
		return mes1;
	}

	public void setMes1(float mes1) {
		this.mes1 = mes1;
	}

	public float getMes2() {
		return mes2;
	}

	public void setMes2(float mes2) {
		this.mes2 = mes2;
	}

	public float getMes3() {
		return mes3;
	}

	public void setMes3(float mes3) {
		this.mes3 = mes3;
	}

	public float getMes4() {
		return mes4;
	}

	public void setMes4(float mes4) {
		this.mes4 = mes4;
	}

	public float getMes5() {
		return mes5;
	}

	public void setMes5(float mes5) {
		this.mes5 = mes5;
	}

	public float getMes6() {
		return mes6;
	}

	public void setMes6(float mes6) {
		this.mes6 = mes6;
	}

	public float getMes7() {
		return mes7;
	}

	public void setMes7(float mes7) {
		this.mes7 = mes7;
	}

	public float getMes8() {
		return mes8;
	}

	public void setMes8(float mes8) {
		this.mes8 = mes8;
	}

	public float getMes9() {
		return mes9;
	}

	public void setMes9(float mes9) {
		this.mes9 = mes9;
	}

	public float getMes10() {
		return mes10;
	}

	public void setMes10(float mes10) {
		this.mes10 = mes10;
	}

	public float getMes11() {
		return mes11;
	}

	public void setMes11(float mes11) {
		this.mes11 = mes11;
	}

	public float getMes12() {
		return mes12;
	}

	public void setMes12(float mes12) {
		this.mes12 = mes12;
	}

	public MetasPorUsuario() {

	}

	public MetasPorUsuario(int codUsuario, int tipoMeta, float mes1, float mes2, float mes3, float mes4,
			float mes5, float mes6, float mes7, float mes8, float mes9, float mes10, float mes11, float mes12) {
		this.id = codUsuario + "-" + tipoMeta;
		this.codUsuario = codUsuario;
		this.tipoMeta = tipoMeta;
		this.mes1 = mes1;
		this.mes2 = mes2;
		this.mes3 = mes3;
		this.mes4 = mes4;
		this.mes5 = mes5;
		this.mes6 = mes6;
		this.mes7 = mes7;
		this.mes8 = mes8;
		this.mes9 = mes9;
		this.mes10 = mes10;
		this.mes11 = mes11;
		this.mes12 = mes12;
	}
}
