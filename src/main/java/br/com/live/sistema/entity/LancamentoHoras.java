package br.com.live.sistema.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="orion_adm_002")
public class LancamentoHoras {
	
	@Id
	public String id;
	
	@Column(name="id_usuario")
	public int idUsuario;
	
	@Column(name="id_tarefa")
	public int idTarefa;
	
	@Column(name="sequencia_lancamento")
	public int seqLancamento;
	
	public String descricao;
	public float tempoGasto;
	
	@Column(name="data_lancamento")
	public Date dataLancamento;
	
	public LancamentoHoras() {
	
	}
	
	public LancamentoHoras(int idUsuario, int idTarefa, String descricao, float tempoGasto, Date dataLancamento, int seqLancamento) {
		this.id = seqLancamento + "-" + idUsuario + "-" + idTarefa;
		this.seqLancamento = seqLancamento;
		this.idUsuario = idUsuario;
		this.idTarefa =idTarefa;
		this.descricao = descricao;
		this.tempoGasto = tempoGasto;
		this.dataLancamento = dataLancamento;
	}
}