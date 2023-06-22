package br.com.live.producao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_020")
public class PlanoMestrePreOrdem {

	@Id
	public long id;
	
	@Column(name="num_plano_mestre")
	public long idPlanoMestre;
	
	@Column(name="referencia")
	public String grupo;
	
	public int periodo;
	public int alternativa;
	public int roteiro;
	public int quantidade;
	public Date data;  
	public String observacao;
	public int deposito;
    public int situacao;
    public String status;
    
    @Column(name="ordem_gerada")
    public int ordemGerada;
    
    @Column(name="mensagem_gravacao")
    public String mensagemGravacaoOrdem;
}
