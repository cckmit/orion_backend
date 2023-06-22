package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table( name = "orion_exp_310")
public class AuditoriaTransporte {

    @Id
    public int id;

    @Column(name = "codigo_rua")
    public int codRua;

    public int volume;

    @Column(name = "data_auditoria")
    public Date dataAuditoria;

    public AuditoriaTransporte() {
    }

    public AuditoriaTransporte(int id, int codRua, int volume, Date dataAuditoria) {
        this.id = id;
        this.codRua = codRua;
        this.volume = volume;
        this.dataAuditoria = dataAuditoria;
    }
}
