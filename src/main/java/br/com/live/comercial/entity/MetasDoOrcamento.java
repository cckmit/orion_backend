package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_150")
public class MetasDoOrcamento {
	
	@Id
	public String id;
	
	public String descricao;
	public String modalidade;
	public int ano;
	
	@Column(name = "tipo_meta")
	public int tipoMeta;
	
	@Column(name = "valor_mes_1")
	public float valorMes1;
	
	@Column(name = "valor_mes_2")
	public float valorMes2;
	
	@Column(name = "valor_mes_3")
	public float valorMes3;
	
	@Column(name = "valor_mes_4")
	public float valorMes4;
	
	@Column(name = "valor_mes_5")
	public float valorMes5;
	
	@Column(name = "valor_mes_6")
	public float valorMes6;
	
	@Column(name = "valor_mes_7")
	public float valorMes7;
	
	@Column(name = "valor_mes_8")
	public float valorMes8;
	
	@Column(name = "valor_mes_9")
	public float valorMes9;
	
	@Column(name = "valor_mes_10")
	public float valorMes10;
	
	@Column(name = "valor_mes_11")
	public float valorMes11;
	
	@Column(name = "valor_mes_12")
	public float valorMes12;
	
	public MetasDoOrcamento() {
		
	}

	public MetasDoOrcamento(String descricao, String modalidade, int ano, int tipoMeta, float valorMes1, float valorMes2,
			float valorMes3, float valorMes4, float valorMes5, float valorMes6, float valorMes7, float valorMes8,
			float valorMes9, float valorMes10, float valorMes11, float valorMes12) {
		this.id = descricao + "-" + ano + "-" + tipoMeta;
		this.descricao = descricao;
		this.modalidade = modalidade;
		this.ano = ano;
		this.tipoMeta = tipoMeta;
		this.valorMes1 = valorMes1;
		this.valorMes2 = valorMes2;
		this.valorMes3 = valorMes3;
		this.valorMes4 = valorMes4;
		this.valorMes5 = valorMes5;
		this.valorMes6 = valorMes6;
		this.valorMes7 = valorMes7;
		this.valorMes8 = valorMes8;
		this.valorMes9 = valorMes9;
		this.valorMes10 = valorMes10;
		this.valorMes11 = valorMes11;
		this.valorMes12 = valorMes12;
	}
}
