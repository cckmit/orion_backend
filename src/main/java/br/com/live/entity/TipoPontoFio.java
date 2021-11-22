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

    @Column(name = "id_tipo_ponto")
    public int idTipoPonto;

    @Column(name = "tipo_fio")
    public int tipoFio;

    @Column(name = "consumo_fio")
    public float consumoFio;

    public TipoPontoFio() {

    }

    public TipoPontoFio(int sequencia, int idTipoPonto, int tipoFio, float consumoFio) {
        this.id = idTipoPonto + "-" + sequencia;
        this.sequencia = sequencia;
        this.idTipoPonto = idTipoPonto;
        this.tipoFio = tipoFio;
        this.consumoFio = consumoFio;
    }
}
