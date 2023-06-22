package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_072")
public class MetasPorRepresentante {

	@Id	
	public String id;
	
	@Column(name = "cod_estacao")
	public long codEstacao;
	
	@Column(name = "tipo_meta")
	public int tipoMeta;
	
	@Column(name = "descricao_rep")
	public String descricaoRep;
                
	public float meta;
	
	@Column(name = "cod_representante")
	public int codRepresentante;
    
	public MetasPorRepresentante() {
		
	}

	public MetasPorRepresentante(long codEstacao, int codRepresentante, int tipoMeta, float meta, String descricaoRep) {
		this.id = codEstacao + "-" + codRepresentante + "-" + tipoMeta;
		this.codEstacao = codEstacao;
		this.tipoMeta = tipoMeta;
		this.meta = meta;
		this.codRepresentante = codRepresentante;
		this.descricaoRep = descricaoRep;
	}
}