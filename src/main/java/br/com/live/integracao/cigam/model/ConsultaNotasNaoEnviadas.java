package br.com.live.integracao.cigam.model;

public class ConsultaNotasNaoEnviadas {
    public int notaFiscal;
    public int serieFiscal;
    public int cnpj9;
    public int cnpj4;
    public int cnpj2;
    public String numeroDanfe;
    public byte[] arquivoXml;

    public int getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(int notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public int getSerieFiscal() {
        return serieFiscal;
    }

    public void setSerieFiscal(int serieFiscal) {
        this.serieFiscal = serieFiscal;
    }

    public int getCnpj9() {
        return cnpj9;
    }

    public void setCnpj9(int cnpj9) {
        this.cnpj9 = cnpj9;
    }

    public int getCnpj4() {
        return cnpj4;
    }

    public void setCnpj4(int cnpj4) {
        this.cnpj4 = cnpj4;
    }

    public int getCnpj2() {
        return cnpj2;
    }

    public void setCnpj2(int cnpj2) {
        this.cnpj2 = cnpj2;
    }

    public String getNumeroDanfe() {
        return numeroDanfe;
    }

    public void setNumeroDanfe(String numeroDanfe) {
        this.numeroDanfe = numeroDanfe;
    }

    public byte[] getArquivoXml() {
        return arquivoXml;
    }

    public void setArquivoXml(byte[] arquivoXml) {
        this.arquivoXml = arquivoXml;
    }

    public ConsultaNotasNaoEnviadas() {}

    public ConsultaNotasNaoEnviadas(int notaFiscal, int serieFiscal, int cnpj9, int cnpj4, int cnpj2, String numeroDanfe, byte[] arquivoXml) {
        this.notaFiscal = notaFiscal;
        this.serieFiscal = serieFiscal;
        this.cnpj9 = cnpj9;
        this.cnpj4 = cnpj4;
        this.cnpj2 = cnpj2;
        this.numeroDanfe = numeroDanfe;
        this.arquivoXml = arquivoXml;
    }
}
