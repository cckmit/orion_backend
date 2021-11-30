package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_083")
public class TipoPontoFio {

    @Id
    public String id;

    public int sequencia;
    public String descricao;

    @Column(name = "id_tipo_ponto")
    public int idTipoPonto;

    @Column(name = "tipo_fio_1")
    public int tipoFio1;

    @Column(name = "tipo_fio_2")
    public int tipoFio2;

    @Column(name = "tipo_fio_3")
    public int tipoFio3;

    @Column(name = "consumo_fio")
    public float consumoFio;
    
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdTipoPonto() {
        return idTipoPonto;
    }

    public void setIdTipoPonto(int idTipoPonto) {
        this.idTipoPonto = idTipoPonto;
    }

    public int getTipoFio1() {
        return tipoFio1;
    }

    public void setTipoFio1(int tipoFio1) {
        this.tipoFio1 = tipoFio1;
    }

    public int getTipoFio2() {
        return tipoFio2;
    }

    public void setTipoFio2(int tipoFio2) {
        this.tipoFio2 = tipoFio2;
    }

    public int getTipoFio3() {
        return tipoFio3;
    }

    public void setTipoFio3(int tipoFio3) {
        this.tipoFio3 = tipoFio3;
    }

    public float getConsumoFio() {
        return consumoFio;
    }

    public void setConsumoFio(float consumoFio) {
        this.consumoFio = consumoFio;
    }

    public TipoPontoFio() {

    }

    public TipoPontoFio(int sequencia, String descricao, int idTipoPonto, int tipoFio1, int tipoFio2, int tipoFio3,
            float consumoFio) {
        this.id = idTipoPonto + "-" + sequencia;
        this.sequencia = sequencia;
        this.descricao = descricao;
        this.idTipoPonto = idTipoPonto;
        this.tipoFio1 = tipoFio1;
        this.tipoFio2 = tipoFio2;
        this.tipoFio3 = tipoFio3;
        this.consumoFio = consumoFio;
    }
}
