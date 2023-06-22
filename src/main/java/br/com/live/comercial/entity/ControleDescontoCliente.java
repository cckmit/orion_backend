package br.com.live.comercial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_com_292")
public class ControleDescontoCliente {

    @Id
    @Column(name = "id_controle")
    public String idControle;

    @Column(name = "cnpj_9")
    public int cnpj9;

    @Column(name = "cnpj_4")
    public int cnpj4;

    @Column(name = "cnpj_2")
    public int cnpj2;

    @Column(name = "valor_desconto")
    public float valorDesconto;

    public String getIdControle() {
        return idControle;
    }

    public void setIdControle(String idControle) {
        this.idControle = idControle;
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

    public float getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(float valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public ControleDescontoCliente() {
    }

    public ControleDescontoCliente(int cnpj9, int cnpj4, int cnpj2, float valorDesconto) {
        this.idControle = cnpj9 + "-" + cnpj4 + "-" + cnpj2;
        this.cnpj9 = cnpj9;
        this.cnpj4 = cnpj4;
        this.cnpj2 = cnpj2;
        this.valorDesconto = valorDesconto;
    }
}
