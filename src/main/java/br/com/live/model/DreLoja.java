package br.com.live.model;

public class DreLoja {
    public long id;
    public int tipoDre;
    public String seqConsulta;
    public String cnpjLoja;
    public int anoDre;
    public int mesDre;
    public String propriedade;
    public double valRealAnoAnt;
    public double percRealAnoAnt;
    public double valOrcado;
    public double percOrcado;
    public double valReal;
    public double percReal;
    public double valDiferencaOrcadoReal;
    public double percDiferencaOrcadoReal;
    public double percDiferencaRealVigAnt;

    public DreLoja(long id, int tipoDre, String seqConsulta, String cnpjLoja, int anoDre, int mesDre, String propriedade, double valRealAnoAnt, double percRealAnoAnt, double valOrcado, double percOrcado, double valReal, double percReal, double valDiferencaOrcadoReal, double percDiferencaOrcadoReal, double percDiferencaRealVigAnt) {
        this.id = id;
        this.tipoDre = tipoDre;
        this.seqConsulta = seqConsulta;
        this.cnpjLoja = cnpjLoja;
        this.anoDre = anoDre;
        this.mesDre = mesDre;
        this.propriedade = propriedade;
        this.valRealAnoAnt = valRealAnoAnt;
        this.percRealAnoAnt = percRealAnoAnt;
        this.valOrcado = valOrcado;
        this.percOrcado = percOrcado;
        this.valReal = valReal;
        this.percReal = percReal;
        this.valDiferencaOrcadoReal = valDiferencaOrcadoReal;
        this.percDiferencaOrcadoReal = percDiferencaOrcadoReal;
        this.percDiferencaRealVigAnt = percDiferencaRealVigAnt;
    }

    public DreLoja(){

    }

    public int getTipoDre() {
        return tipoDre;
    }

    public void setTipoDre(int tipoDre) {
        this.tipoDre = tipoDre;
    }

    public String getSeqConsulta() {
        return seqConsulta;
    }

    public void setSeqConsulta(String seqConsulta) {
        this.seqConsulta = seqConsulta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCnpjLoja() {
        return cnpjLoja;
    }

    public void setCnpjLoja(String cnpjLoja) {
        this.cnpjLoja = cnpjLoja;
    }

    public int getAnoDre() {
        return anoDre;
    }

    public void setAnoDre(int anoDre) {
        this.anoDre = anoDre;
    }

    public int getMesDre() {
        return mesDre;
    }

    public void setMesDre(int mesDre) {
        this.mesDre = mesDre;
    }

    public String getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(String propriedade) {
        this.propriedade = propriedade;
    }

    public double getValRealAnoAnt() {
        return valRealAnoAnt;
    }

    public void setValRealAnoAnt(double valRealAnoAnt) {
        this.valRealAnoAnt = valRealAnoAnt;
    }

    public double getPercRealAnoAnt() {
        return percRealAnoAnt;
    }

    public void setPercRealAnoAnt(double percRealAnoAnt) {
        this.percRealAnoAnt = percRealAnoAnt;
    }

    public double getValOrcado() {
        return valOrcado;
    }

    public void setValOrcado(double valOrcado) {
        this.valOrcado = valOrcado;
    }

    public double getPercOrcado() {
        return percOrcado;
    }

    public void setPercOrcado(double percOrcado) {
        this.percOrcado = percOrcado;
    }

    public double getValReal() {
        return valReal;
    }

    public void setValReal(double valReal) {
        this.valReal = valReal;
    }

    public double getPercReal() {
        return percReal;
    }

    public void setPercReal(double percReal) {
        this.percReal = percReal;
    }

    public double getValDiferencaOrcadoReal() {
        return valDiferencaOrcadoReal;
    }

    public void setValDiferencaOrcadoReal(double valDiferencaOrcadoReal) {
        this.valDiferencaOrcadoReal = valDiferencaOrcadoReal;
    }

    public double getPercDiferencaOrcadoReal() {
        return percDiferencaOrcadoReal;
    }

    public void setPercDiferencaOrcadoReal(double percDiferencaOrcadoReal) {
        this.percDiferencaOrcadoReal = percDiferencaOrcadoReal;
    }

    public double getPercDiferencaRealVigAnt() {
        return percDiferencaRealVigAnt;
    }

    public void setPercDiferencaRealVigAnt(double percDiferencaRealVigAnt) {
        this.percDiferencaRealVigAnt = percDiferencaRealVigAnt;
    }
}
