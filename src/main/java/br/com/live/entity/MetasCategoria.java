package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_140")
public class MetasCategoria {
	
	@Id
	public String id;
	
	@Column(name = "cod_estacao")
	public long codEstacao;
	
	@Column(name = "cod_representante")
	public int codRepresentante;
	
	@Column(name = "tipo_meta")
	public int tipoMeta;
	
	@Column(name = "valor_categoria_1")
	public float valorCategoria1;
	
	@Column(name = "valor_categoria_2")
	public float valorCategoria2;
	
	@Column(name = "valor_categoria_3")
	public float valorCategoria3;
	
	@Column(name = "valor_categoria_4")
	public float valorCategoria4;
	
	@Column(name = "valor_categoria_5")
	public float valorCategoria5;
	
	@Column(name = "valor_categoria_6")
	public float valorCategoria6;
	
	@Column(name = "valor_categoria_7")
	public float valorCategoria7;
	
	@Column(name = "valor_categoria_8")
	public float valorCategoria8;
	
	@Column(name = "valor_categoria_9")
	public float valorCategoria9;
	
	@Column(name = "valor_categoria_10")
	public float valorCategoria10;
	
	public MetasCategoria() {
		
	}

	public MetasCategoria(long codEstacao, int codRepresentante, int tipoMeta, float valorCategoria1,
			float valorCategoria2, float valorCategoria3, float valorCategoria4, float valorCategoria5,
			float valorCategoria6, float valorCategoria7, float valorCategoria8, float valorCategoria9,
			float valorCategoria10) {
		this.id = codEstacao + "-" + codRepresentante + "-" + tipoMeta;
		this.codEstacao = codEstacao;
		this.codRepresentante = codRepresentante;
		this.tipoMeta = tipoMeta;
		this.valorCategoria1 = valorCategoria1;
		this.valorCategoria2 = valorCategoria2;
		this.valorCategoria3 = valorCategoria3;
		this.valorCategoria4 = valorCategoria4;
		this.valorCategoria5 = valorCategoria5;
		this.valorCategoria6 = valorCategoria6;
		this.valorCategoria7 = valorCategoria7;
		this.valorCategoria8 = valorCategoria8;
		this.valorCategoria9 = valorCategoria9;
		this.valorCategoria10 = valorCategoria10;
	}
}
