package br.com.live.sistema.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_020")
public class GestaoAtivosOportunidade {
	
	@Id	
	public String id;
	public int tipo;
	
	@Column(name = "id_ativo")
	public int idAtivo;
	
	public int sequencia;
	
	@Column(name = "data_cadastro")
	public Date dataCadastro;
	
	public int prioridade;
	public String descricao;
	public String objetivo;
	public String contextualizacao;
	
	@Column(name = "descricao_problema")
	public String descricaoProblema;
	
	@Column(name = "perguntas_em_aberto")
	public String perguntasEmAberto;
	
	public String riscos;
	
	public String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public int getIdAtivo() {
		return idAtivo;
	}

	public void setIdAtivo(int idAtivo) {
		this.idAtivo = idAtivo;
	}

	public int getSequencia() {
		return sequencia;
	}

	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getContextualizacao() {
		return contextualizacao;
	}

	public void setContextualizacao(String contextualizacao) {
		this.contextualizacao = contextualizacao;
	}

	public String getDescricaoProblema() {
		return descricaoProblema;
	}

	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}

	public String getPerguntasEmAberto() {
		return perguntasEmAberto;
	}

	public void setPerguntasEmAberto(String perguntasEmAberto) {
		this.perguntasEmAberto = perguntasEmAberto;
	}

	public String getRiscos() {
		return riscos;
	}

	public void setRiscos(String riscos) {
		this.riscos = riscos;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public GestaoAtivosOportunidade() {
		
	}

	public GestaoAtivosOportunidade(String id, int tipo, int idAtivo, int sequencia, Date dataCadastro, int prioridade, String descricao, String objetivo,
			String contextualizacao, String descricaoProblema, String perguntasEmAberto, String riscos, String status) {
		
		this.id = id ;
		this.tipo = tipo;
		this.idAtivo = idAtivo;
		this.sequencia = sequencia;
		this.dataCadastro = dataCadastro;
		this.prioridade = prioridade;
		this.descricao = descricao;
		this.objetivo = objetivo;
		this.contextualizacao = contextualizacao;
		this.descricaoProblema = descricaoProblema;
		this.perguntasEmAberto = perguntasEmAberto;
		this.riscos = riscos;
		this.status = status;
	}

}
