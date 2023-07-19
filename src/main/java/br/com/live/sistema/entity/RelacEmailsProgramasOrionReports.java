package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_bi_150")
public class RelacEmailsProgramasOrionReports {

    @Id
    public String id;

    @Column(name = "id_programa")
    public String idPrograma;

    public String email;

    public RelacEmailsProgramasOrionReports() {}

    public RelacEmailsProgramasOrionReports(String idPrograma, String email) {
        this.id = idPrograma + "-" + email;
        this.idPrograma = idPrograma;
        this.email = email;
    }
}
