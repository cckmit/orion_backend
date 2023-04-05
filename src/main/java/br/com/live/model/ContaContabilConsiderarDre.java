package br.com.live.model;

public class ContaContabilConsiderarDre {

    private int contaContabil;
    private boolean gastoVariavel;
    private boolean custoOcupacao;
    private boolean despesaFolha;
    private boolean despesaGeral;
    private boolean depreciacao;

    public ContaContabilConsiderarDre(int contaContabil, boolean gastoVariavel, boolean custoOcupacao, boolean despesaFolha, boolean despesaGeral, boolean depreciacao) {
        this.contaContabil = contaContabil;
        this.gastoVariavel = gastoVariavel;
        this.custoOcupacao = custoOcupacao;
        this.despesaFolha = despesaFolha;
        this.despesaGeral = despesaGeral;
        this.depreciacao = depreciacao;
    }

    public ContaContabilConsiderarDre(){
    }

    public int getContaContabil() {
        return contaContabil;
    }

    public void setContaContabil(int contaContabil) {
        this.contaContabil = contaContabil;
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
