package br.com.live.util.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name="orion_anexos")
public class Anexo {

    @Id
    private long id;
    private String chave;

    @Column(name="nome_arquivo")
    private String nomeArquivo;

    @Column(name="tipo_arquivo")
    private String tipoArquivo;

    @Column(name="programa_gerador")
    private String programaGerador;
    private int tamanho;

    @Column(name="data_registro")
    private Date dataRegistro;

    @Column(name="dados_arquivo")
    private byte[] dadosArquivo;

    public Anexo(){}

    public Anexo(long id, String chave, String nomeArquivo, String tipoArquivo, String programaGerador, int tamanho, Date dataRegistro, byte[] dadosArquivo) {
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

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public byte[] getDadosArquivo() {
        return dadosArquivo;
    }

    public void setDadosArquivo(byte[] dadosArquivo) {
        this.dadosArquivo = dadosArquivo;
    }
}
