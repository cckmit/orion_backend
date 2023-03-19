package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_010")
public class Integracao {

    @Id
    public int id;

    @Column(name = "nome_integracao")
    public String nomeIntegracao;

    public String objetivo;

    @Column(name = "tipo_integracao")
    public String tipoIntegracao;

    @Column(name = "tipo_conexao")
    public String tipoConexao;

    @Column(name = "sistema_origem")
    public int sistemaOrigem;

    @Column(name = "sistema_destino")
    public int sistemaDestino;

    public int servidor;
    public String fornecedor;
    public String cnpj;
    public String endereco;

    public Integracao(int id, String nomeIntegracao, String objetivo, String tipoIntegracao, String tipoConexao, int sistemaOrigem, int sistemaDestino, int servidor, String fornecedor, String cnpj, String endereco) {
        this.id = id;
        this.nomeIntegracao = nomeIntegracao;
        this.objetivo = objetivo;
        this.tipoIntegracao = tipoIntegracao;
        this.tipoConexao = tipoConexao;
        this.sistemaOrigem = sistemaOrigem;
        this.sistemaDestino = sistemaDestino;
        this.servidor = servidor;
        this.fornecedor = fornecedor;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    public Integracao(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeIntegracao() {
        return nomeIntegracao;
    }

    public void setNomeIntegracao(String nomeIntegracao) {
        this.nomeIntegracao = nomeIntegracao;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getTipoIntegracao() {
        return tipoIntegracao;
    }

    public void setTipoIntegracao(String tipoIntegracao) {
        this.tipoIntegracao = tipoIntegracao;
    }

    public String getTipoConexao() {
        return tipoConexao;
    }

    public void setTipoConexao(String tipoConexao) {
        this.tipoConexao = tipoConexao;
    }

    public int getSistemaOrigem() {
        return sistemaOrigem;
    }

    public void setSistemaOrigem(int sistemaOrigem) {
        this.sistemaOrigem = sistemaOrigem;
    }

    public int getSistemaDestino() {
        return sistemaDestino;
    }

    public void setSistemaDestino(int sistemaDestino) {
        this.sistemaDestino = sistemaDestino;
    }

    public int getServidor() {
        return servidor;
    }

    public void setServidor(int servidor) {
        this.servidor = servidor;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
