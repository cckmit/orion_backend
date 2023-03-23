package br.com.live.model;

import java.util.Date;

public class ConsultaChamado {

    public int codChamado;
    public String tituloChamado;
    public int codTecnico;
    public int codArea;
    public int codSetor;
    public int codDepartamento;
    public int impacto;
    public String descricaoChamado;
    public String nomeRequerente;
    public String nomeTecnico;
    public String descricaoArea;
    public String descricaoDepartamento;
    public String descricaoSetor;
    public Date dataInicioTriagem;
    public Date dataFimTriagem;
    public Date dataInicioAnalise;
    public Date dataFimAnalise;
    public Date dataInicioAprovEscopo;
    public Date dataFimAprovEscopo;
    public Date dataInicioOrcamento;
    public Date dataFimOrcamento;
    public Date dataInicioFilaDesenvForn;
    public Date dataFimFilaDesenvForn;
    public Date dataInicioDesenvForn;
    public Date dataFimDesenvForn;
    public Date dataInicioFilaDesenvInt;
    public Date dataFimFilaDesenvInt;
    public Date dataInicioDesenvInt;
    public Date dataFimDesenvInt;
    public Date dataInicioQualidadeTestes;
    public Date dataFimQualidadeTestes;
    public Date dataInicioEntrega;
    public Date dataFimEntrega;

    public ConsultaChamado(){

    }

    public ConsultaChamado(int codChamado, String tituloChamado, int codTecnico, int codArea, int codSetor, int codDepartamento,
                           int impacto, String descricaoChamado, String nomeRequerente, String nomeTecnico, String descricaoArea,
                           String descricaoDepartamento, String descricaoSetor, Date dataInicioTriagem, Date dataFimTriagem,
                           Date dataInicioAnalise, Date dataFimAnalise, Date dataInicioAprovEscopo, Date dataFimAprovEscopo,
                           Date dataInicioOrcamento, Date dataFimOrcamento, Date dataInicioFilaDesenvForn, Date dataFimFilaDesenvForn,
                           Date dataInicioDesenvForn, Date dataFimDesenvForn, Date dataInicioFilaDesenvInt, Date dataFimFilaDesenvInt,
                           Date dataInicioDesenvInt, Date dataFimDesenvInt, Date dataInicioQualidadeTestes, Date dataFimQualidadeTestes,
                           Date dataInicioEntrega, Date dataFimEntrega) {
        this.codChamado = codChamado;
        this.tituloChamado = tituloChamado;
        this.codTecnico = codTecnico;
        this.codArea = codArea;
        this.codSetor = codSetor;
        this.codDepartamento = codDepartamento;
        this.impacto = impacto;
        this.descricaoChamado = descricaoChamado;
        this.nomeRequerente = nomeRequerente;
        this.nomeTecnico = nomeTecnico;
        this.descricaoArea = descricaoArea;
        this.descricaoDepartamento = descricaoDepartamento;
        this.descricaoSetor = descricaoSetor;
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

    public int getCodChamado() {
        return codChamado;
    }

    public void setCodChamado(int codChamado) {
        this.codChamado = codChamado;
    }

    public String getTituloChamado() {
        return tituloChamado;
    }

    public void setTituloChamado(String tituloChamado) {
        this.tituloChamado = tituloChamado;
    }

    public int getCodTecnico() {
        return codTecnico;
    }

    public void setCodTecnico(int codTecnico) {
        this.codTecnico = codTecnico;
    }

    public int getCodArea() {
        return codArea;
    }

    public void setCodArea(int codArea) {
        this.codArea = codArea;
    }

    public int getCodSetor() {
        return codSetor;
    }

    public void setCodSetor(int codSetor) {
        this.codSetor = codSetor;
    }

    public int getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(int codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public int getImpacto() {
        return impacto;
    }

    public void setImpacto(int impacto) {
        this.impacto = impacto;
    }

    public String getDescricaoChamado() {
        return descricaoChamado;
    }

    public void setDescricaoChamado(String descricaoChamado) {
        this.descricaoChamado = descricaoChamado;
    }

    public String getNomeRequerente() {
        return nomeRequerente;
    }

    public void setNomeRequerente(String nomeRequerente) {
        this.nomeRequerente = nomeRequerente;
    }

    public String getNomeTecnico() {
        return nomeTecnico;
    }

    public void setNomeTecnico(String nomeTecnico) {
        this.nomeTecnico = nomeTecnico;
    }

    public String getDescricaoArea() {
        return descricaoArea;
    }

    public void setDescricaoArea(String descricaoArea) {
        this.descricaoArea = descricaoArea;
    }

    public String getDescricaoDepartamento() {
        return descricaoDepartamento;
    }

    public void setDescricaoDepartamento(String descricaoDepartamento) {
        this.descricaoDepartamento = descricaoDepartamento;
    }

    public String getDescricaoSetor() {
        return descricaoSetor;
    }

    public void setDescricaoSetor(String descricaoSetor) {
        this.descricaoSetor = descricaoSetor;
    }

    public Date getDataInicioTriagem() {
        return dataInicioTriagem;
    }

    public void setDataInicioTriagem(Date dataInicioTriagem) {
        this.dataInicioTriagem = dataInicioTriagem;
    }

    public Date getDataFimTriagem() {
        return dataFimTriagem;
    }

    public void setDataFimTriagem(Date dataFimTriagem) {
        this.dataFimTriagem = dataFimTriagem;
    }

    public Date getDataInicioAnalise() {
        return dataInicioAnalise;
    }

    public void setDataInicioAnalise(Date dataInicioAnalise) {
        this.dataInicioAnalise = dataInicioAnalise;
    }

    public Date getDataFimAnalise() {
        return dataFimAnalise;
    }

    public void setDataFimAnalise(Date dataFimAnalise) {
        this.dataFimAnalise = dataFimAnalise;
    }

    public Date getDataInicioAprovEscopo() {
        return dataInicioAprovEscopo;
    }

    public void setDataInicioAprovEscopo(Date dataInicioAprovEscopo) {
        this.dataInicioAprovEscopo = dataInicioAprovEscopo;
    }

    public Date getDataFimAprovEscopo() {
        return dataFimAprovEscopo;
    }

    public void setDataFimAprovEscopo(Date dataFimAprovEscopo) {
        this.dataFimAprovEscopo = dataFimAprovEscopo;
    }

    public Date getDataInicioOrcamento() {
        return dataInicioOrcamento;
    }

    public void setDataInicioOrcamento(Date dataInicioOrcamento) {
        this.dataInicioOrcamento = dataInicioOrcamento;
    }

    public Date getDataFimOrcamento() {
        return dataFimOrcamento;
    }

    public void setDataFimOrcamento(Date dataFimOrcamento) {
        this.dataFimOrcamento = dataFimOrcamento;
    }

    public Date getDataInicioFilaDesenvForn() {
        return dataInicioFilaDesenvForn;
    }

    public void setDataInicioFilaDesenvForn(Date dataInicioFilaDesenvForn) {
        this.dataInicioFilaDesenvForn = dataInicioFilaDesenvForn;
    }

    public Date getDataFimFilaDesenvForn() {
        return dataFimFilaDesenvForn;
    }

    public void setDataFimFilaDesenvForn(Date dataFimFilaDesenvForn) {
        this.dataFimFilaDesenvForn = dataFimFilaDesenvForn;
    }

    public Date getDataInicioDesenvForn() {
        return dataInicioDesenvForn;
    }

    public void setDataInicioDesenvForn(Date dataInicioDesenvForn) {
        this.dataInicioDesenvForn = dataInicioDesenvForn;
    }

    public Date getDataFimDesenvForn() {
        return dataFimDesenvForn;
    }

    public void setDataFimDesenvForn(Date dataFimDesenvForn) {
        this.dataFimDesenvForn = dataFimDesenvForn;
    }

    public Date getDataInicioFilaDesenvInt() {
        return dataInicioFilaDesenvInt;
    }

    public void setDataInicioFilaDesenvInt(Date dataInicioFilaDesenvInt) {
        this.dataInicioFilaDesenvInt = dataInicioFilaDesenvInt;
    }

    public Date getDataFimFilaDesenvInt() {
        return dataFimFilaDesenvInt;
    }

    public void setDataFimFilaDesenvInt(Date dataFimFilaDesenvInt) {
        this.dataFimFilaDesenvInt = dataFimFilaDesenvInt;
    }

    public Date getDataInicioDesenvInt() {
        return dataInicioDesenvInt;
    }

    public void setDataInicioDesenvInt(Date dataInicioDesenvInt) {
        this.dataInicioDesenvInt = dataInicioDesenvInt;
    }

    public Date getDataFimDesenvInt() {
        return dataFimDesenvInt;
    }

    public void setDataFimDesenvInt(Date dataFimDesenvInt) {
        this.dataFimDesenvInt = dataFimDesenvInt;
    }

    public Date getDataInicioQualidadeTestes() {
        return dataInicioQualidadeTestes;
    }

    public void setDataInicioQualidadeTestes(Date dataInicioQualidadeTestes) {
        this.dataInicioQualidadeTestes = dataInicioQualidadeTestes;
    }

    public Date getDataFimQualidadeTestes() {
        return dataFimQualidadeTestes;
    }

    public void setDataFimQualidadeTestes(Date dataFimQualidadeTestes) {
        this.dataFimQualidadeTestes = dataFimQualidadeTestes;
    }

    public Date getDataInicioEntrega() {
        return dataInicioEntrega;
    }

    public void setDataInicioEntrega(Date dataInicioEntrega) {
        this.dataInicioEntrega = dataInicioEntrega;
    }

    public Date getDataFimEntrega() {
        return dataFimEntrega;
    }

    public void setDataFimEntrega(Date dataFimEntrega) {
        this.dataFimEntrega = dataFimEntrega;
    }
}