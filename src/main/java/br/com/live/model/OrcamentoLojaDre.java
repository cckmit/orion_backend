package br.com.live.model;

import java.math.BigDecimal;

public class OrcamentoLojaDre {
    private Long id;
    private String cnpjLoja;
    private int anoOrcamento;
    private int mesOrcamento;
    private int tipoOrcamento;
    private String propriedade;
    private BigDecimal valPropriedade;
    private String seqConsulta;

    public OrcamentoLojaDre(Long id, String cnpjLoja, int anoOrcamento, int mesOrcamento, int tipoOrcamento, String propriedade, BigDecimal valPropriedade, String seqConsulta) {
        this.id = id;
        this.cnpjLoja = cnpjLoja;
        this.anoOrcamento = anoOrcamento;
        this.mesOrcamento = mesOrcamento;
        this.tipoOrcamento = tipoOrcamento;
        this.propriedade = propriedade;
        this.valPropriedade = valPropriedade;
        this.seqConsulta = seqConsulta;
    }

    public OrcamentoLojaDre(){
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

    public BigDecimal getValPropriedade() {
        return valPropriedade;
    }

    public void setValPropriedade(BigDecimal valPropriedade) {
        this.valPropriedade = valPropriedade;
    }
}