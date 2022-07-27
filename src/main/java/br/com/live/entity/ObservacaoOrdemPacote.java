package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_cfc_220")
public class ObservacaoOrdemPacote {
	
	  	@Id
	    public String id;
	    public int estagio;
	    
	    @Column(name = "ordem_producao")
	    public int ordemProducao;

	    @Column(name = "ordem_confeccao")
	    public int ordemConfeccao;
	    
	    @Column(name = "tipo_observacao")
	    public int tipoObservacao;
	    
	    @Column(name = "observacao_adicional")
	    public String observacaoAdicional;
	    
	    public ObservacaoOrdemPacote() {
	    	
	    }

		public ObservacaoOrdemPacote(int estagio, int ordemProducao, int ordemConfeccao,
				int tipoObservacao, String observacaoAdicional) {
			this.id = estagio + "-" + ordemProducao + "-" + ordemConfeccao;
			this.estagio = estagio;
			this.ordemProducao = ordemProducao;
			this.ordemConfeccao = ordemConfeccao;
			this.tipoObservacao = tipoObservacao;
			this.observacaoAdicional = observacaoAdicional;
		}
}
