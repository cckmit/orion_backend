package br.com.live.sistema.model;

public class CustoOrcamentoProjeto {

    private long id;
    private long idProjeto;
    private String item;
    private int quantidade;
    private double valorUnitario;
    private String pagarPara;

    public CustoOrcamentoProjeto(long id, long idProjeto, String item, int quantidade, double valorUnitario, String pagarPara) {
        this.id = id;
        this.idProjeto = idProjeto;
        this.item = item;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.pagarPara = pagarPara;
    }

    public CustoOrcamentoProjeto(){}

    public long getId() {
        return id;
    }

    public long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getPagarPara() {
        return pagarPara;
    }

    public void setPagarPara(String pagarPara) {
        this.pagarPara = pagarPara;
    }
}
