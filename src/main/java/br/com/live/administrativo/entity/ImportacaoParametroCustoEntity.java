package br.com.live.administrativo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_cnt_020")
public class ImportacaoParametroCustoEntity {

    @Id
    public String id;

    public int usuario;

    @Column(name = "seq_importacao")
    public int seqImportacao;

    public int empresa;
    public String nivel;
    public String grupo;
    public String sub;
    public String item;

    @Column(name = "tipo_parametro")
    public int tipoParametro;

    public int sequencia;
    public String descricao;
    public float consumo;
    public int estagio;
    public int mes;
    public int ano;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getSeqImportacao() {
        return seqImportacao;
    }

    public void setSeqImportacao(int seqImportacao) {
        this.seqImportacao = seqImportacao;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getTipoParametro() {
        return tipoParametro;
    }

    public void setTipoParametro(int tipoParametro) {
        this.tipoParametro = tipoParametro;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getConsumo() {
        return consumo;
    }

    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }

    public int getEstagio() {
        return estagio;
    }

    public void setEstagio(int estagio) {
        this.estagio = estagio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public ImportacaoParametroCustoEntity() { }

    public ImportacaoParametroCustoEntity(int usuario, int seqImportacao, int empresa, String nivel, String grupo, String sub, String item, int tipoParametro,
                                          int sequencia, String descricao, float consumo, int estagio, int mes, int ano) {
        this.id = usuario + "-" + seqImportacao;
        this.usuario = usuario;
        this.seqImportacao = seqImportacao;
        this.empresa = empresa;
        this.nivel = nivel;
        this.grupo = grupo;
        this.sub = sub;
        this.item = item;
        this.tipoParametro = tipoParametro;
        this.sequencia = sequencia;
        this.descricao = descricao;
        this.consumo = consumo;
        this.estagio = estagio;
        this.mes = mes;
        this.ano = ano;
    }
}
