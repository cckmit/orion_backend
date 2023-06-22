package br.com.live.sistema.entity;

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

    @Column(name = "nome_requerente")
    public String nomeRequerente;

    @Column(name = "data_inicio_triagem")
    public Date dataInicioTriagem;

    @Column(name = "data_fim_triagem")
    public Date dataFimTriagem;

    @Column(name = "data_inicio_analise")
    public Date dataInicioAnalise;

    @Column(name = "data_fim_analise")
    public Date dataFimAnalise;

    @Column(name = "data_inicio_aprov_escopo")
    public Date dataInicioAprovEscopo;

    @Column(name = "data_fim_aprov_escopo")
    public Date dataFimAprovEscopo;

    @Column(name = "data_inicio_orcamento")
    public Date dataInicioOrcamento;

    @Column(name = "data_fim_orcamento")
    public Date dataFimOrcamento;

    @Column(name = "data_inicio_fila_desenv_forn")
    public Date dataInicioFilaDesenvForn;

    @Column(name = "data_fim_fila_desenv_forn")
    public Date dataFimFilaDesenvForn;

    @Column(name = "data_inicio_desenv_forn")
    public Date dataInicioDesenvForn;

    @Column(name = "data_fim_desenv_forn")
    public Date dataFimDesenvForn;

    @Column(name = "data_inicio_fila_desenv_int")
    public Date dataInicioFilaDesenvInt;

    @Column(name = "data_fim_fila_desenv_int")
    public Date dataFimFilaDesenvInt;

    @Column(name = "data_inicio_desenv_int")
    public Date dataInicioDesenvInt;

    @Column(name = "data_fim_desenv_int")
    public Date dataFimDesenvInt;

    @Column(name = "data_inicio_qualidade_testes")
    public Date dataInicioQualidadeTestes;

    @Column(name = "data_fim_qualidade_testes")
    public Date dataFimQualidadeTestes;

    @Column(name = "data_inicio_entrega")
    public Date dataInicioEntrega;

    @Column(name = "data_fim_entrega")
    public Date dataFimEntrega;

    public Chamado(){

    }

    public Chamado(int codChamado, String tituloChamado, int codTecnico, int codArea, int codDepartamento, int codSetor, int impacto,
                   String descricaoChamado, String nomeRequerente, Date dataInicioTriagem, Date dataFimTriagem, Date dataInicioAnalise,
                   Date dataFimAnalise, Date dataInicioAprovEscopo, Date dataFimAprovEscopo, Date dataInicioOrcamento, Date dataFimOrcamento,
                   Date dataInicioFilaDesenvForn, Date dataFimFilaDesenvForn, Date dataInicioDesenvForn, Date dataFimDesenvForn,
                   Date dataInicioFilaDesenvInt, Date dataFimFilaDesenvInt, Date dataInicioDesenvInt, Date dataFimDesenvInt,
                   Date dataInicioQualidadeTestes, Date dataFimQualidadeTestes, Date dataInicioEntrega, Date dataFimEntrega) {
        this.codChamado = codChamado;
        this.tituloChamado = tituloChamado;
        this.codTecnico = codTecnico;
        this.codArea = codArea;
        this.codDepartamento = codDepartamento;
        this.codSetor = codSetor;
        this.impacto = impacto;
        this.descricaoChamado = descricaoChamado;
        this.nomeRequerente = nomeRequerente;
        this.dataInicioTriagem = dataInicioTriagem;
        this.dataFimTriagem = dataFimTriagem;
        this.dataInicioAnalise = dataInicioAnalise;
        this.dataFimAnalise = dataFimAnalise;
        this.dataInicioAprovEscopo = dataInicioAprovEscopo;
        this.dataFimAprovEscopo = dataFimAprovEscopo;
        this.dataInicioOrcamento = dataInicioOrcamento;
        this.dataFimOrcamento = dataFimOrcamento;
        this.dataInicioFilaDesenvForn = dataInicioFilaDesenvForn;
        this.dataFimFilaDesenvForn = dataFimFilaDesenvForn;
        this.dataInicioDesenvForn = dataInicioDesenvForn;
        this.dataFimDesenvForn = dataFimDesenvForn;
        this.dataInicioFilaDesenvInt = dataInicioFilaDesenvInt;
        this.dataFimFilaDesenvInt = dataFimFilaDesenvInt;
        this.dataInicioDesenvInt = dataInicioDesenvInt;
        this.dataFimDesenvInt = dataFimDesenvInt;
        this.dataInicioQualidadeTestes = dataInicioQualidadeTestes;
        this.dataFimQualidadeTestes = dataFimQualidadeTestes;
        this.dataInicioEntrega = dataInicioEntrega;
        this.dataFimEntrega = dataFimEntrega;
    }
}