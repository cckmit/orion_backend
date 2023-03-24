package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_001")
public class Servidor {

    @Id
    public int id;

    @Column(name = "nome_servidor")
    public String nomeServidor;

    @Column(name = "maquina_fisica")
    public boolean maquinaFisica;

    @Column(name = "sistema_operacional")
    public String sistemaOperacional;

    public String ip;
    public String hd;
    public String memoria;
    public String processador;
    public String aplicacoes;
    public byte[] documentacao;

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
    public Servidor(){
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
