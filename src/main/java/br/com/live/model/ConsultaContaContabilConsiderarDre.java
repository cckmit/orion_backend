package br.com.live.model;

public class ConsultaContaContabilConsiderarDre {

    private int contaContabil;
    String descricaoContaContabil;
    private boolean gastoVariavel;
    private boolean custoOcupacao;
    private boolean despesaFolha;
    private boolean despesaGeral;
    private boolean depreciacao;

    public ConsultaContaContabilConsiderarDre(int contaContabil, String descricaoContaContabil, boolean gastoVariavel, boolean custoOcupacao, boolean despesaFolha, boolean despesaGeral, boolean depreciacao) {
        this.contaContabil = contaContabil;
        this.descricaoContaContabil = descricaoContaContabil;
        this.gastoVariavel = gastoVariavel;
        this.custoOcupacao = custoOcupacao;
        this.despesaFolha = despesaFolha;
        this.despesaGeral = despesaGeral;
        this.depreciacao = depreciacao;
    }

    public ConsultaContaContabilConsiderarDre(){
    }

    public int getContaContabil() {
        return contaContabil;
    }

    public void setContaContabil(int contaContabil) {
        this.contaContabil = contaContabil;
    }

    public String getDescricaoContaContabil() {
        return descricaoContaContabil;
    }

    public void setDescricaoContaContabil(String descricaoContaContabil) {
        this.descricaoContaContabil = descricaoContaContabil;
    }

    public boolean isGastoVariavel() {
        return gastoVariavel;
    }

    public void setGastoVariavel(boolean gastoVariavel) {
        this.gastoVariavel = gastoVariavel;
    }

    public boolean isCustoOcupacao() {
        return custoOcupacao;
    }

    public void setCustoOcupacao(boolean custoOcupacao) {
        this.custoOcupacao = custoOcupacao;
    }

    public boolean isDespesaFolha() {
        return despesaFolha;
    }

    public void setDespesaFolha(boolean despesaFolha) {
        this.despesaFolha = despesaFolha;
    }

    public boolean isDespesaGeral() {
        return despesaGeral;
    }

    public void setDespesaGeral(boolean despesaGeral) {
        this.despesaGeral = despesaGeral;
    }

    public boolean isDepreciacao() {
        return depreciacao;
    }

    public void setDepreciacao(boolean depreciacao) {
        this.depreciacao = depreciacao;
    }
}
