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
    public int maquinaFisica;

    @Column(name = "sistema_operacional")
    public String sistemaOperacional;

    public String ip;
    public int hd;
    public int memoria;
    public String processador;
    public String aplicacoes;
    public byte[] documentacao;

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
