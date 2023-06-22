package br.com.live.producao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_bnf_100")
public class OrdemBeneficiamentoCapa {
	
	@Id
	public long id;
	
	@Column(name = "periodo_producao")
	public int periodoProducao;
	
	@Column(name = "data_programa")
	public Date dataPrograma;
	 
	@Column(name = "previsao_termino")
	public Date previsaoTermino;
	
	@Column(name = "grupo_maquina")
	public String grupoMaquina;
	
	@Column(name = "subgrupo_maquina")
	public String subgrupoMaquina;
	
	@Column(name = "numero_maquina")
	public int numeroMaquina;
    
	@Column(name = "tipo_ordem")
	public int tipoOrdem;
	
	public String observacao;
    public int alternativa;
    public int roteiro;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getPeriodoProducao() {
		return periodoProducao;
	}
	public void setPeriodoProducao(int periodoProducao) {
		this.periodoProducao = periodoProducao;
	}
	public Date getDataPrograma() {
		return dataPrograma;
	}
	public void setDataPrograma(Date dataPrograma) {
		this.dataPrograma = dataPrograma;
	}
	public Date getPrevisaoTermino() {
		return previsaoTermino;
	}
	public void setPrevisaoTermino(Date previsaoTermino) {
		this.previsaoTermino = previsaoTermino;
	}
	public String getGrupoMaquina() {
		return grupoMaquina;
	}
	public void setGrupoMaquina(String grupoMaquina) {
		this.grupoMaquina = grupoMaquina;
	}
	public String getSubgrupoMaquina() {
		return subgrupoMaquina;
	}
	public void setSubgrupoMaquina(String subgrupoMaquina) {
		this.subgrupoMaquina = subgrupoMaquina;
	}
	public int getNumeroMaquina() {
		return numeroMaquina;
	}
	public void setNumeroMaquina(int numeroMaquina) {
		this.numeroMaquina = numeroMaquina;
	}
	public int getTipoOrdem() {
		return tipoOrdem;
	}
	public void setTipoOrdem(int tipoOrdem) {
		this.tipoOrdem = tipoOrdem;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public int getAlternativa() {
		return alternativa;
	}
	public void setAlternativa(int alternativa) {
		this.alternativa = alternativa;
	}
	public int getRoteiro() {
		return roteiro;
	}
	public void setRoteiro(int roteiro) {
		this.roteiro = roteiro;
	} 
    
    public OrdemBeneficiamentoCapa() {
    	
    }
	public OrdemBeneficiamentoCapa(long id, int periodoProducao, Date dataPrograma, Date previsaoTermino,
			String grupoMaquina, String subgrupoMaquina, int numeroMaquina, int tipoOrdem, String observacao, int alternativa, int roteiro) {
		this.id = id;
		this.periodoProducao = periodoProducao;
		this.dataPrograma = dataPrograma;
		this.previsaoTermino = previsaoTermino;
		this.grupoMaquina = grupoMaquina;
		this.subgrupoMaquina = subgrupoMaquina;
		this.numeroMaquina = numeroMaquina;
		this.tipoOrdem = tipoOrdem;
		this.observacao = observacao;
		this.alternativa = alternativa;
		this.roteiro = roteiro;
	}
    
}
