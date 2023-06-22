package br.com.live.sistema.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="orion_adm_001")
public class Tarefas {
	
	@Id
	public int id;
	public int sistema;
	public int origem;
	
	@Column(name="usuario_solicitante")
	public int usuarioSolicitante;
	
	@Column(name="usuario_atribuido")
	public int usuarioAtribuido;
	
	public String titulo;
	public String assunto;
	public int situacao;
	public long anexo;
		
	@Column(name="tempo_estimado")
	public float tempoEstimado;
	
	@Column(name="data_prevista")
	public Date dataPrevista;
	
	@Column(name="num_doc_interno")
	public int numDocInterno;
	
	@Column(name="num_doc_fornecedor")
	public int numDocFornecedor;
	
	@Column(name="tarefa_principal")
	public boolean tarefaPrincipal;
	
	public Tarefas() {
		
	}
	
	public Tarefas(int id, int sistema, int origem, int usuarioSolicitante, int usuarioAtribuido, String titulo, String assunto, int situacao, long anexo, float tempoEstimado, Date dataPrevista, int numDocInterno, int numDocFornecedor, boolean tarefaPrincipal) {
		this.id = id;
		this.sistema = sistema;
		this.origem =origem;
		this.usuarioAtribuido = usuarioAtribuido;
		this.usuarioSolicitante =usuarioSolicitante;
		this.titulo = titulo;
		this.assunto = assunto;
		this.situacao = situacao;
		this.anexo = anexo;
		this.tempoEstimado = tempoEstimado;
		this.dataPrevista = dataPrevista;
		this.numDocInterno = numDocInterno;
		this.numDocFornecedor = numDocFornecedor;
		this.tarefaPrincipal = tarefaPrincipal;
		
	}
}