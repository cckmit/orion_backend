package br.com.live.model;

public class AtributoRemessaPortadorEmpresa {

    private int calcNrTituloBanco;
    private int codAgencia;
    private int numeroContrato;
    private int contaCorrente;
    private int codCarteira;

    public AtributoRemessaPortadorEmpresa(int calcNrTituloBanco, int codAgencia, int numeroContrato, int contaCorrente, int codCarteira) {
        this.calcNrTituloBanco = calcNrTituloBanco;
        this.codAgencia = codAgencia;
        this.numeroContrato = numeroContrato;
        this.contaCorrente = contaCorrente;
        this.codCarteira = codCarteira;
    }

    public AtributoRemessaPortadorEmpresa(){

    }

    public int getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(int contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public int getCodCarteira() {
        return codCarteira;
    }

    public void setCodCarteira(int codCarteira) {
        this.codCarteira = codCarteira;
    }

    public int getCalcNrTituloBanco() {
        return calcNrTituloBanco;
    }

    public void setCalcNrTituloBanco(int calcNrTituloBanco) {
        this.calcNrTituloBanco = calcNrTituloBanco;
    }

    public int getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(int numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public int getCodAgencia() {
        return codAgencia;
    }

    public void setCodAgencia(int codAgencia) {
        this.codAgencia = codAgencia;
    }
}
