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

    @Column(name = "cod_tecnico")
    public int codTecnico;

    @Column(name = "cod_area")
    public int codArea;

    @Column(name = "cod_departamento")
    public int codDepartamento;

    @Column(name = "cod_setor")
    public int codSetor;

    public int impacto;

    @Column(name = "descricao_chamado")
    public String descricaoChamado;

    @Column(name = "data_chamado")
    public Date dataChamado;

    @Column(name = "nome_requerente")
    public String nomeRequerente;

    @Column(name = "data_analise")
    public Date dataAnalise;

    @Column(name = "data_entrega_des")
    public Date dataEntregaDes;

    @Column(name = "data_entrega_usuario")
    public Date dataEntregaUsuario;

    public Chamado(){

    }

    public Chamado(int codChamado, String tituloChamado, int codTecnico, int codArea, int codDepartamento, int codSetor, int impacto, String descricaoChamado, Date dataChamado, String nomeRequerente, Date dataAnalise, Date dataEntregaDes, Date dataEntregaUsuario) {
        this.codChamado = codChamado;
        this.tituloChamado = tituloChamado;
        this.codTecnico = codTecnico;
        this.codArea = codArea;
        this.codDepartamento = codDepartamento;
        this.codSetor = codSetor;
        this.impacto = impacto;
        this.descricaoChamado = descricaoChamado;
        this.dataChamado = dataChamado;
        this.nomeRequerente = nomeRequerente;
        this.dataAnalise = dataAnalise;
        this.dataEntregaDes = dataEntregaDes;
        this.dataEntregaUsuario = dataEntregaUsuario;
    }
}