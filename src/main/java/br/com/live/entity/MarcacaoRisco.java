package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_vi_marcacoes_risco")
public class MarcacaoRisco {

	@Id		
	public long id;   
    public String grupo;
    
    @Column(name = "tamanho")
    public String sub;
        
    @Column(name = "codigo_risco")
    public int codigoRisco;
    
    @Column(name = "alternativa_item")
    public int alternativa;
    
    @Column(name = "qtde_marc_proj")
    public int qtdeMarcacao;
    
    public int colecao;
 
    public MarcacaoRisco(long id, String grupo, String sub, int codigoRisco, int alternativa, int qtdeMarcacao, int colecao) {
    	this.id = id; 
    	this.grupo = grupo; 
    	this.sub = sub; 
    	this.codigoRisco = codigoRisco; 
    	this.alternativa = alternativa; 
    	this.qtdeMarcacao = qtdeMarcacao; 
    	this.colecao = colecao;
    }
    
}
