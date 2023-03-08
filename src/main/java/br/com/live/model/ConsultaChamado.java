package br.com.live.model;

import java.util.Date;

public class ConsultaChamado {

    public int codChamado;
    public String tituloChamado;
    public int codRequerente;
    public int codTecnico;
    public int codArea;
    public int codSetor;
    public int codDepartamento;
    public boolean impacto;
    public String descricaoChamado;
    public Date dataChamado;
    public String nomeRequerente;
    public String nomeTecnico;
    public String descricaoArea;
    public String descricaoDepartamento;
    public String descricaoSetor;

    public ConsultaChamado(){

    }

    public ConsultaChamado(int codChamado, String tituloChamado, int codRequerente, int codTecnico, int codArea, int codSetor,
                           int codDepartamento, boolean impacto, String descricaoChamado, Date dataChamado, String nomeRequerente,
                           String nomeTecnico, String descricaoArea, String descricaoDepartamento, String descricaoSetor) {
        this.codChamado = codChamado;
        this.tituloChamado = tituloChamado;
        this.codRequerente = codRequerente;
        this.codTecnico = codTecnico;
        this.codArea = codArea;
        this.codSetor = codSetor;
        this.codDepartamento = codDepartamento;
        this.impacto = impacto;
        this.descricaoChamado = descricaoChamado;
        this.dataChamado = dataChamado;
        this.nomeRequerente = nomeRequerente;
        this.nomeTecnico = nomeTecnico;
        this.descricaoArea = descricaoArea;
        this.descricaoDepartamento = descricaoDepartamento;
        this.descricaoSetor = descricaoSetor;
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

    public int getCodRequerente() {
        return codRequerente;
    }

    public void setCodRequerente(int codRequerente) {
        this.codRequerente = codRequerente;
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

    public boolean isImpacto() {
        return impacto;
    }

    public void setImpacto(boolean impacto) {
        this.impacto = impacto;
    }

    public String getDescricaoChamado() {
        return descricaoChamado;
    }

    public void setDescricaoChamado(String descricaoChamado) {
        this.descricaoChamado = descricaoChamado;
    }

    public Date getDataChamado() {
        return dataChamado;
    }

    public void setDataChamado(Date dataChamado) {
        this.dataChamado = dataChamado;
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
}
