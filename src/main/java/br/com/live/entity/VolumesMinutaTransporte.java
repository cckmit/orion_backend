package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "orion_exp_320")
public class VolumesMinutaTransporte {

    @Id
    public long id;
    public int minuta;
    public int volume;
    public int pedido;
    public int nota;
    public String serie;
    public String cliente;

    @Column(name = "data_liberacao")
    public Date dataLiberacao;

    @Column(name = "peso_bruto")
    public Float pesoBruto;

    @Column(name = "valor_nota")
    public Float valorNota;

    @Column(name = "data_hora_geracao")
    public Date dataHoraGeracao;

    @Column(name = "tipo_minuta")
    public int tipoMinuta;

    public String transportadora;
    public String cidade;
    public String estado;
    public String usuario;

    public VolumesMinutaTransporte() {

    }

    public VolumesMinutaTransporte(long id, int volume, int pedido, int nota, String serie, String cliente, Date dataLiberacao, Float pesoBruto, Float valorNota, int minuta,
                                   Date dataHoraGeracao, int tipoMinuta, String transportadora, String cidade, String estado, String usuario) {
        this.id = id;
        this.volume = volume;
        this.pedido = pedido;
        this.nota = nota;
        this.serie = serie;
        this.cliente = cliente;
        this.dataLiberacao = dataLiberacao;
        this.pesoBruto = pesoBruto;
        this.valorNota = valorNota;
        this.minuta = minuta;
        this.dataHoraGeracao = dataHoraGeracao;
        this.tipoMinuta = tipoMinuta;
        this.transportadora = transportadora;
        this.cidade = cidade;
        this.estado = estado;
        this.usuario = usuario;
    }
}
