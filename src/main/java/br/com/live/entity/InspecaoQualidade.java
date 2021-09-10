package br.com.live.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_050")
public class InspecaoQualidade {
	
	@Id
	@Column(name = "id_inspecao")
	public long id;
			
	public int tipo;
	public Date data;
	public int turno;
	public String usuario;	
	
	@Column(name = "cod_estagio")
	public int codEstagio;

	public int periodo;
	
	@Column(name = "ordem_producao")
	public int ordemProducao;
	
	@Column(name = "ordem_confeccao")
	public int ordemConfeccao;
	
	@Column(name = "perc_inspecionar_pcs")
	public int percInspecionarPcs;
	
	@Column(name = "qtde_inspecionar_pcs")
	public int qtdeInspecionarPcs;

	@Column(name = "qtde_inspecionada_pcs")
	public int qtdeInspecionadaPcs;
	
	@Column(name = "qtde_rejeitada_pcs")
	public int qtdeRejeitadaPcs;
	
	@Column(name = "perc_rejeitada_pcs")
	public int percRejeitadaPcs;
	
	public void atualizaQuantidadesInpecionadas(int qtdeInspecionarPcs, int qtdeRejeitadaPcs) {
		this.qtdeInspecionadaPcs = qtdeInspecionarPcs;
		this.qtdeRejeitadaPcs = qtdeRejeitadaPcs;
		this.percRejeitadaPcs = (int) (((float) qtdeRejeitadaPcs / (float) qtdeInspecionadaPcs) * 100);
	}	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getCodEstagio() {
		return codEstagio;
	}

	public void setCodEstagio(int codEstagio) {
		this.codEstagio = codEstagio;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public int getOrdemProducao() {
		return ordemProducao;
	}

	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}

	public int getOrdemConfeccao() {
		return ordemConfeccao;
	}

	public void setOrdemConfeccao(int ordemConfeccao) {
		this.ordemConfeccao = ordemConfeccao;
	}

	public int getPercInspecionarPcs() {
		return percInspecionarPcs;
	}

	public void setPercInspecionarPcs(int percInspecionarPcs) {
		this.percInspecionarPcs = percInspecionarPcs;
	}

	public int getQtdeInspecionarPcs() {
		return qtdeInspecionarPcs;
	}

	public void setQtdeInspecionarPcs(int qtdeInspecionarPcs) {
		this.qtdeInspecionarPcs = qtdeInspecionarPcs;
	}

	public int getQtdeInspecionadaPcs() {
		return qtdeInspecionadaPcs;
	}

	public void setQtdeInspecionadaPcs(int qtdeInspecionadaPcs) {
		this.qtdeInspecionadaPcs = qtdeInspecionadaPcs;
	}

	public int getQtdeRejeitadaPcs() {
		return qtdeRejeitadaPcs;
	}

	public void setQtdeRejeitadaPcs(int qtdeRejeitadaPcs) {
		this.qtdeRejeitadaPcs = qtdeRejeitadaPcs;
	}

	public int getPercRejeitadaPcs() {
		return percRejeitadaPcs;
	}

	public void setPercRejeitadaPcs(int percRejeitadaPcs) {
		this.percRejeitadaPcs = percRejeitadaPcs;
	}

}