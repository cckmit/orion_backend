package br.com.live.sistema.model;

public class Servidor {
    private int id;
    private String nomeServidor;
    private boolean maquinaFisica;
    private String sistemaOperacional;
    private String ip;
    private String hd;
    private String memoria;
    private String processador;
    private String aplicacoes;
    private byte[] documentacao;

    public Servidor(int id, String nomeServidor, boolean maquinaFisica, String sistemaOperacional, String ip, String hd, String memoria, String processador, String aplicacoes, byte[] documentacao) {
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

    public boolean getMaquinaFisica() {
        return maquinaFisica;
    }

    public void setMaquinaFisica(boolean maquinaFisica) {
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

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
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
