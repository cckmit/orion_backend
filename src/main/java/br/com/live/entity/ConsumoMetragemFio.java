package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_085")
public class ConsumoMetragemFio {

	@Id	
    public String id;
    public int sequencia;
    public String referencia;

    @Column(name = "id_referencia")
    public String idReferencia;

	@Column(name = "id_tipo_ponto")
	public int idTipoPonto;

    @Column(name = "id_tipo_fio")
	public int idTipoFio;

	public int pacote;

    @Column(name = "metragem_costura_cm")
	public float metragemCosturaCm;

    @Column(name = "metragem_total")
	public float metragemTotal;

    @Column(name = "metragem_um")
	public float metragemUm;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getSequencia() {
        return sequencia;
    }
    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }
    public String getReferencia() {
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    public String getIdReferencia() {
        return idReferencia;
    }
    public void setIdReferencia(String idReferencia) {
        this.idReferencia = idReferencia;
    }
    public int getIdTipoPonto() {
        return idTipoPonto;
    }
    public void setIdTipoPonto(int idTipoPonto) {
        this.idTipoPonto = idTipoPonto;
    }
    public int getPacote() {
        return pacote;
    }
    public void setPacote(int pacote) {
        this.pacote = pacote;
    }
    public float getMetragemCosturaCm() {
        return metragemCosturaCm;
    }
    public void setMetragemCosturaCm(float metragemCosturaCm) {
        this.metragemCosturaCm = metragemCosturaCm;
    }
    public float getMetragemTotal() {
        return metragemTotal;
    }
    public void setMetragemTotal(float metragemTotal) {
        this.metragemTotal = metragemTotal;
    }
    public float getMetragemUm() {
        return metragemUm;
    }
    public void setMetragemUm(float metragemUm) {
        this.metragemUm = metragemUm;
    }
    public int getIdTipoFio() {
        return idTipoFio;
    }
    public void setIdTipoFio(int idTipoFio) {
        this.idTipoFio = idTipoFio;
    }

    public ConsumoMetragemFio() {
    }

    public ConsumoMetragemFio(int sequencia, String referencia, String idReferencia, int idTipoPonto, int pacote,
            float metragemCosturaCm, float metragemTotal, float metragemUm, int idTipoFio) {
        this.id = idReferencia + "-" + sequencia;
        this.sequencia = sequencia;
        this.referencia = referencia;
        this.idReferencia = idReferencia;
        this.idTipoPonto = idTipoPonto;
        this.pacote = pacote;
        this.metragemCosturaCm = metragemCosturaCm;
        this.metragemTotal = metragemTotal;
        this.metragemUm = metragemUm;
        this.idTipoFio = idTipoFio;
    }

    
}





