package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="orion_adm_010")
public class Chamado {

    @Id
    @Column(name = "cod_chamado")
    public int codChamado;

    @Column(name = "titulo_chamado")
    public String tituloChamado;

    @Column(name = "cod_requerente")
    public int codRequerente;

    @Column(name = "cod_tecnico")
    public int codTecnico;

    @Column(name = "cod_area")
    public int codArea;

    @Column(name = "cod_departamento")
    public int codDepartamento;

    @Column(name = "cod_setor")
    public int codSetor;

    public boolean impacto;

    @Column(name = "descricao_chamado")
    public String descricaoChamado;

    @Column(name = "data_chamado")
    public Date dataChamado;

    public Chamado(){

    }

    public Chamado(int codChamado, String tituloChamado, int codRequerente, int codTecnico, int codArea, int codDepartamento, int codSetor, boolean impacto, String descricaoChamado, Date dataChamado) {
        this.codChamado = codChamado;
        this.tituloChamado = tituloChamado;
        this.codRequerente = codRequerente;
        this.codTecnico = codTecnico;
        this.codArea = codArea;
        this.codDepartamento = codDepartamento;
        this.codSetor = codSetor;
        this.impacto = impacto;
        this.descricaoChamado = descricaoChamado;
        this.dataChamado = dataChamado;
    }
}
