package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_082")
public class TipoPonto {

    @Id
    public int id;

    public String descricao;

    @Column(name = "grupo_maquina")
    public String grupoMaquina;

    @Column(name = "sub_grupo_maquina")
    public String subGrupoMaquina;

    public TipoPonto() {

    }

    public TipoPonto(int id, String descricao, String grupoMaquina, String subGrupoMaquina) {
        this.id = id;
        this.descricao = descricao;
        this.grupoMaquina = grupoMaquina;
        this.subGrupoMaquina = subGrupoMaquina;
    }
}
