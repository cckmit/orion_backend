package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_084")
public class ConsumoFiosLinhas {

	@Id	
    public String id;

    public String referencia;

	@Column(name = "id_tipo_ponto")
	public int idTipoPonto;

    public int sequencia;
	
    @Column(name = "comprimento_costura")
	public float comprimentoCostura;

    
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getIdTipoPonto() {
        return idTipoPonto;
    }

    public void setIdTipoPonto(int idTipoPonto) {
        this.idTipoPonto = idTipoPonto;
    }

    public float getComprimentoCostura() {
        return comprimentoCostura;
    }

    public void setComprimentoCostura(float comprimentoCostura) {
        this.comprimentoCostura = comprimentoCostura;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public ConsumoFiosLinhas() {
		
	}

    public ConsumoFiosLinhas(String referencia, int idTipoPonto, int sequencia, float comprimentoCostura) {
        this.id = referencia + "-" + idTipoPonto + "-" + sequencia;
        this.referencia = referencia;
        this.idTipoPonto = idTipoPonto;
        this.comprimentoCostura = comprimentoCostura;
        this.sequencia = sequencia;
    }
}
