package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ind_100")
public class ResultadosIndicadorDiario {
	
	@Id
	private String id;
	
	@Column(name = "id_indicador")
	public int idIndicador;
	
	private String mes;
	private int ano;
	private String codigo;
    private String descricao;
    
    @Column(name = "dia_1")
	public float dia1;
    
    @Column(name = "dia_2")
	public float dia2;
    
    @Column(name = "dia_3")
	public float dia3;
    
    @Column(name = "dia_4")
	public float dia4;
    
    @Column(name = "dia_5")
	public float dia5;
    
    @Column(name = "dia_6")
	public float dia6;
    
    @Column(name = "dia_7")
	public float dia7;
    
    @Column(name = "dia_8")
	public float dia8;
    
    @Column(name = "dia_9")
	public float dia9;
    
    @Column(name = "dia_10")
	public float dia10;
    
    @Column(name = "dia_11")
	public float dia11;
    
    @Column(name = "dia_12")
	public float dia12;
    
    @Column(name = "dia_13")
	public float dia13;
    
    @Column(name = "dia_14")
	public float dia14;
    
    @Column(name = "dia_15")
	public float dia15;
    
    @Column(name = "dia_16")
	public float dia16;
    
    @Column(name = "dia_17")
	public float dia17;
    
    @Column(name = "dia_18")
	public float dia18;
    
    @Column(name = "dia_19")
	public float dia19;
    
    @Column(name = "dia_20")
	public float dia20;
    
    @Column(name = "dia_21")
	public float dia21;
    
    @Column(name = "dia_22")
	public float dia22;
    
    @Column(name = "dia_23")
	public float dia23;
    
    @Column(name = "dia_24")
	public float dia24;
    
    @Column(name = "dia_25")
	public float dia25;
    
    @Column(name = "dia_26")
	public float dia26;
    
    @Column(name = "dia_27")
	public float dia27;
    
    @Column(name = "dia_28")
	public float dia28;
    
    @Column(name = "dia_29")
	public float dia29;
    
    @Column(name = "dia_30")
	public float dia30;
    
    @Column(name = "dia_31")
   	public float dia31;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIdIndicador() {
		return idIndicador;
	}
	public void setIdIndicador(int idIndicador) {
		this.idIndicador = idIndicador;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getDia1() {
		return dia1;
	}
	public void setDia1(float dia1) {
		this.dia1 = dia1;
	}
	public float getDia2() {
		return dia2;
	}
	public void setDia2(float dia2) {
		this.dia2 = dia2;
	}
	public float getDia3() {
		return dia3;
	}
	public void setDia3(float dia3) {
		this.dia3 = dia3;
	}
	public float getDia4() {
		return dia4;
	}
	public void setDia4(float dia4) {
		this.dia4 = dia4;
	}
	public float getDia5() {
		return dia5;
	}
	public void setDia5(float dia5) {
		this.dia5 = dia5;
	}
	public float getDia6() {
		return dia6;
	}
	public void setDia6(float dia6) {
		this.dia6 = dia6;
	}
	public float getDia7() {
		return dia7;
	}
	public void setDia7(float dia7) {
		this.dia7 = dia7;
	}
	public float getDia8() {
		return dia8;
	}
	public void setDia8(float dia8) {
		this.dia8 = dia8;
	}
	public float getDia9() {
		return dia9;
	}
	public void setDia9(float dia9) {
		this.dia9 = dia9;
	}
	public float getDia10() {
		return dia10;
	}
	public void setDia10(float dia10) {
		this.dia10 = dia10;
	}
	public float getDia11() {
		return dia11;
	}
	public void setDia11(float dia11) {
		this.dia11 = dia11;
	}
	public float getDia12() {
		return dia12;
	}
	public void setDia12(float dia12) {
		this.dia12 = dia12;
	}
	public float getDia13() {
		return dia13;
	}
	public void setDia13(float dia13) {
		this.dia13 = dia13;
	}
	public float getDia14() {
		return dia14;
	}
	public void setDia14(float dia14) {
		this.dia14 = dia14;
	}
	public float getDia15() {
		return dia15;
	}
	public void setDia15(float dia15) {
		this.dia15 = dia15;
	}
	public float getDia16() {
		return dia16;
	}
	public void setDia16(float dia16) {
		this.dia16 = dia16;
	}
	public float getDia17() {
		return dia17;
	}
	public void setDia17(float dia17) {
		this.dia17 = dia17;
	}
	public float getDia18() {
		return dia18;
	}
	public void setDia18(float dia18) {
		this.dia18 = dia18;
	}
	public float getDia19() {
		return dia19;
	}
	public void setDia19(float dia19) {
		this.dia19 = dia19;
	}
	public float getDia20() {
		return dia20;
	}
	public void setDia20(float dia20) {
		this.dia20 = dia20;
	}
	public float getDia21() {
		return dia21;
	}
	public void setDia21(float dia21) {
		this.dia21 = dia21;
	}
	public float getDia22() {
		return dia22;
	}
	public void setDia22(float dia22) {
		this.dia22 = dia22;
	}
	public float getDia23() {
		return dia23;
	}
	public void setDia23(float dia23) {
		this.dia23 = dia23;
	}
	public float getDia24() {
		return dia24;
	}
	public void setDia24(float dia24) {
		this.dia24 = dia24;
	}
	public float getDia25() {
		return dia25;
	}
	public void setDia25(float dia25) {
		this.dia25 = dia25;
	}
	public float getDia26() {
		return dia26;
	}
	public void setDia26(float dia26) {
		this.dia26 = dia26;
	}
	public float getDia27() {
		return dia27;
	}
	public void setDia27(float dia27) {
		this.dia27 = dia27;
	}
	public float getDia28() {
		return dia28;
	}
	public void setDia28(float dia28) {
		this.dia28 = dia28;
	}
	public float getDia29() {
		return dia29;
	}
	public void setDia29(float dia29) {
		this.dia29 = dia29;
	}
	public float getDia30() {
		return dia30;
	}
	public void setDia30(float dia30) {
		this.dia30 = dia30;
	}
	public float getDia31() {
		return dia31;
	}
	public void setDia31(float dia31) {
		this.dia31 = dia31;
	}
    
    public ResultadosIndicadorDiario() {
    	
    }

	public ResultadosIndicadorDiario(String id, int idIndicador, String mes, int ano, String codigo, String descricao, float dia1, float dia2, float dia3, float dia4, float dia5, float dia6, 
			float dia7, float dia8, float dia9, float dia10, float dia11, float dia12, float dia13, float dia14, float dia15, float dia16, float dia17, float dia18,
			float dia19, float dia20, float dia21, float dia22, float dia23, float dia24, float dia25, float dia26,	float dia27, float dia28, float dia29, float dia30, 
			float dia31) {
		this.id = mes + "-" + ano + "-" + codigo;
		this.idIndicador = idIndicador;
		this.mes = mes;
		this.ano = ano;
		this.codigo = codigo;
		this.descricao = descricao;
		this.dia1 = dia1;
		this.dia2 = dia2;
		this.dia3 = dia3;
		this.dia4 = dia4;
		this.dia5 = dia5;
		this.dia6 = dia6;
		this.dia7 = dia7;
		this.dia8 = dia8;
		this.dia9 = dia9;
		this.dia10 = dia10;
		this.dia11 = dia11;
		this.dia12 = dia12;
		this.dia13 = dia13;
		this.dia14 = dia14;
		this.dia15 = dia15;
		this.dia16 = dia16;
		this.dia17 = dia17;
		this.dia18 = dia18;
		this.dia19 = dia19;
		this.dia20 = dia20;
		this.dia21 = dia21;
		this.dia22 = dia22;
		this.dia23 = dia23;
		this.dia24 = dia24;
		this.dia25 = dia25;
		this.dia26 = dia26;
		this.dia27 = dia27;
		this.dia28 = dia28;
		this.dia29 = dia29;
		this.dia30 = dia30;
		this.dia31 = dia31;
	}

}
