package br.com.live.administrativo.model;

public class OrcamentoLojaDre {
    public Long id;
    public String cnpjLoja;
    public int anoOrcamento;
    public int mesOrcamento;
    public int tipoOrcamento;
    public String propriedade;
    public double valPropriedade;
    public String seqConsulta;
    public int contaContabil;

    public OrcamentoLojaDre(Long id, String cnpjLoja, int anoOrcamento, int mesOrcamento, int tipoOrcamento, String propriedade, double valPropriedade, String seqConsulta, int contaContabil) {
        this.id = id;
        this.cnpjLoja = cnpjLoja;
        this.anoOrcamento = anoOrcamento;
        this.mesOrcamento = mesOrcamento;
        this.tipoOrcamento = tipoOrcamento;
        this.propriedade = propriedade;
        this.valPropriedade = valPropriedade;
        this.seqConsulta = seqConsulta;
        this.contaContabil = contaContabil;
    }

    public OrcamentoLojaDre(){
    }

    public int getContaContabil() {
        return contaContabil;
    }

    public void setContaContabil(int contaContabil) {
        this.contaContabil = contaContabil;
    }

    public String getSeqConsulta() {
        return seqConsulta;
    }

    public void setSeqConsulta(String seqConsulta) {
        this.seqConsulta = seqConsulta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpjLoja() {
        return cnpjLoja;
    }

    public void setCnpjLoja(String cnpjLoja) {
        this.cnpjLoja = cnpjLoja;
    }

    public int getAnoOrcamento() {
        return anoOrcamento;
    }

    public void setAnoOrcamento(int anoOrcamento) {
        this.anoOrcamento = anoOrcamento;
    }

    public int getMesOrcamento() {
        return mesOrcamento;
    }

    public void setMesOrcamento(int mesOrcamento) {
        this.mesOrcamento = mesOrcamento;
    }

    public int getTipoOrcamento() {
        return tipoOrcamento;
    }

    public void setTipoOrcamento(int tipoOrcamento) {
        this.tipoOrcamento = tipoOrcamento;
    }

    public String getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(String propriedade) {
        this.propriedade = propriedade;
    }

    public double getValPropriedade() {
        return valPropriedade;
    }

    public void setValPropriedade(double valPropriedade) {
        this.valPropriedade = valPropriedade;
    }
}