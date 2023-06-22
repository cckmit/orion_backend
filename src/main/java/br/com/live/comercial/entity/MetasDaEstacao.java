package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_071")
public class MetasDaEstacao {

	@Id	
	public String id;
	
	@Column(name = "cod_estacao")
	public long codEstacao;
	
	public int mes;
	
	@Column(name = "tipo_meta")
	public int tipoMeta;
                
	public int ano;
	
	@Column(name = "perc_distribuicao")
	public float percDistribuicao;
    
	public MetasDaEstacao() {
		
	}

	public MetasDaEstacao(long codEstacao, int mes, int ano, int tipoMeta, float percDistribuicao) {
		this.id = codEstacao + "-" + mes + "-" + ano + "-" + tipoMeta;
		this.codEstacao = codEstacao;
		this.mes = mes;
		this.ano = ano;
		this.tipoMeta = tipoMeta;
		this.percDistribuicao = percDistribuicao;
	}
}
