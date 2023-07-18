package br.com.live.util.body;

import javax.persistence.Id;

public class BodyAnexo {

    @Id
    private long id;
    private String chave;
    private String nomeArquivo;
    private String tipoArquivo;
    private String programaGerador;
    private int tamanho;
    private String dataRegistro;
    private byte[] dadosArquivo;

    public BodyAnexo(){}

    public BodyAnexo(long id, String chave, String nomeArquivo, String tipoArquivo, String programaGerador, int tamanho, String dataRegistro, byte[] dadosArquivo) {
        this.id = id;
        this.chave = chave;
        this.nomeArquivo = nomeArquivo;
        this.tipoArquivo = tipoArquivo;
        this.programaGerador = programaGerador;
        this.tamanho = tamanho;
        this.dataRegistro = dataRegistro;
        this.dadosArquivo = dadosArquivo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public String getProgramaGerador() {
        return programaGerador;
    }

    public void setProgramaGerador(String programaGerador) {
        this.programaGerador = programaGerador;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public byte[] getDadosArquivo() {
        return dadosArquivo;
    }

    public void setDadosArquivo(byte[] dadosArquivo) {
        this.dadosArquivo = dadosArquivo;
    }
}
