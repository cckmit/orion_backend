package br.com.live.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_050")
public class InspecaoQualidadePeca {
	
	@Id
	@Column(name = "id_inspecao")
	public long id;
			
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
	
	@Column(name = "qtde_rejeitaca_pcs")
	public int qtdeRejeitadaPcs;

	@Column(name = "perc_rejeitaca_pcs")
	public int percRejeitadaPcs;
	
	public void atualizaQuantidadesInpecionadas(int qtdeInspecionarPcs, int qtdeRejeitadaPcs) {
		this.qtdeInspecionadaPcs = qtdeInspecionarPcs;
		this.qtdeRejeitadaPcs = qtdeRejeitadaPcs;
		this.percRejeitadaPcs = (int) (((float) qtdeRejeitadaPcs / (float) qtdeInspecionadaPcs) * 100);
	}
	
}
