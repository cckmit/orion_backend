package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "orion_com_290")
public class ValorDescontoClientesImportados {

    @Id
    public long id;

    @Column(name = "cnpj_9")
    public int cnpj9;

    @Column(name = "cnpj_4")
    public int cnpj4;

    @Column(name = "cnpj_2")
    public int cnpj2;

    @Column(name = "data_insercao")
    public Date dataInsercao;

    public float valor;
    public String observacao;
    public String usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(Date dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public ValorDescontoClientesImportados() {
    }

    public ValorDescontoClientesImportados(long id, int cnpj9, int cnpj4, int cnpj2, Date dataInsercao, float valor, String observacao, String usuario) {
        this.id = id;
        this.cnpj9 = cnpj9;
        this.cnpj4 = cnpj4;
        this.cnpj2 = cnpj2;
        this.dataInsercao = dataInsercao;
        this.valor = valor;
        this.observacao = observacao;
        this.usuario = usuario;
    }
}
