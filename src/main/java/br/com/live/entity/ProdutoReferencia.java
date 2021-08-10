package br.com.live.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_vi_produtos")
public class ProdutoReferencia {

	@Id		
	public String id;
	public String grupo;
    public String descricao;    
    public int colecao;
    public int permanente;
    
    public ProdutoReferencia () {
    	
    }
    
    public ProdutoReferencia (String id, String grupo, String descricao, int colecao, int permanente) {
    	this.id=id;        
    	this.grupo=grupo;
    	this.descricao=descricao;
    	this.colecao=colecao;
    	this.permanente=permanente;
    }
     
}
