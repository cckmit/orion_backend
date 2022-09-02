package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_eng_260")
public class OperacaoXMicromovimentos {
	
	@Id		
	public long id;
	
	@Column(name = "cod_operacao")
	public int codOperacao;
	
	public int sequencia;
	public int tipo;
	
	@Column(name = "id_micromovimento")
	public String idMicromovimento;
	
	@Column(name = "id_tempo_maquina")
	public int idTempoMaquina;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getCodOperacao() {
		return codOperacao;
	}

	public void setCodOperacao(int codOperacao) {
		this.codOperacao = codOperacao;
	}

	public int getSequencia() {
		return sequencia;
	}

	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getCodMicromovimento() {
		return idMicromovimento;
	}

	public void setCodMicromovimento(String idMicromovimento) {
		this.idMicromovimento = idMicromovimento;
	}

	public int getIdTempoMaquina() {
		return idTempoMaquina;
	}

	public void setIdTempoMaquina(int idTempoMaquina) {
		this.idTempoMaquina = idTempoMaquina;
	}

	public OperacaoXMicromovimentos() {
	}

	public OperacaoXMicromovimentos(long id, int codOperacao, int sequencia, int tipo, String idMicromovimento, int idTempoMaquina) {
		this.id = id;
		this.codOperacao = codOperacao;
		this.sequencia = sequencia;
		this.tipo = tipo;
		this.idMicromovimento = idMicromovimento;
		this.idTempoMaquina = idTempoMaquina;
	}
}
