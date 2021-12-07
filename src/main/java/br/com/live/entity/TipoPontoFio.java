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

    public float getConsumoFio() {
        return consumoFio;
    }

    public void setConsumoFio(float consumoFio) {
        this.consumoFio = consumoFio;
    }

    public TipoPontoFio() {

    }

    public TipoPontoFio(int sequencia, String descricao, int idTipoPonto, float consumoFio) {
        this.id = idTipoPonto + "-" + sequencia;
        this.sequencia = sequencia;
        this.descricao = descricao;
        this.idTipoPonto = idTipoPonto;
        this.consumoFio = consumoFio;
    }
}
