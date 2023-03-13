package br.com.live.model;

public class Servidor {
    private int id;
    private String nomeServidor;
    private int maquinaFisica;
    private String sistemaOperacional;
    private String ip;
    private int hd;
    private int memoria;
    private String processador;
    private String aplicacoes;
    private byte[] documentacao;

    public Servidor(int id, String nomeServidor, int maquinaFisica, String sistemaOperacional, String ip, int hd, int memoria, String processador, String aplicacoes, byte[] documentacao) {
        this.id = id;
        this.nomeServidor = nomeServidor;
        this.maquinaFisica = maquinaFisica;
        this.sistemaOperacional = sistemaOperacional;
        this.ip = ip;
        this.hd = hd;
        this.memoria = memoria;
        this.processador = processador;
        this.aplicacoes = aplicacoes;
        this.documentacao = documentacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeServidor() {
        return nomeServidor;
    }

    public void setNomeServidor(String nomeServidor) {
        this.nomeServidor = nomeServidor;
    }

    public int getMaquinaFisica() {
        return maquinaFisica;
    }

    public void setMaquinaFisica(int maquinaFisica) {
        this.maquinaFisica = maquinaFisica;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getHd() {
        return hd;
    }

    public void setHd(int hd) {
        this.hd = hd;
    }

    public int getMemoria() {
        return memoria;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getAplicacoes() {
        return aplicacoes;
    }

    public void setAplicacoes(String aplicacoes) {
        this.aplicacoes = aplicacoes;
    }

    public byte[] getDocumentacao() {
        return documentacao;
    }

    public void setDocumentacao(byte[] documentacao) {
        this.documentacao = documentacao;
    }
}
