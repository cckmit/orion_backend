package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ORION_PARAMETROS")
public class Parametros {

    @Id
    public String id;

    @Column(name = "VALOR_STR")
    public String valorStr;

    @Column(name = "VALOR_INT")
    public int valorInt;

    @Column(name = "VALOR_DOUBLE")
    public float valorDouble;

    @Column(name = "VALOR_DATE")
    public Date valorDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValorStr() {
        return valorStr;
    }

    public void setValorStr(String valorStr) {
        this.valorStr = valorStr;
    }

    public int getValorInt() {
        return valorInt;
    }

    public void setValorInt(int valorInt) {
        this.valorInt = valorInt;
    }

    public float getValorDouble() {
        return valorDouble;
    }

    public void setValorDouble(float valorDouble) {
        this.valorDouble = valorDouble;
    }

    public Date getValorDate() {
        return valorDate;
    }

    public void setValorDate(Date valorDate) {
        this.valorDate = valorDate;
    }

    public Parametros() {}

    public Parametros(String id, String valorStr, int valorInt, float valorDouble, Date valorDate) {
        this.id = id;
        this.valorStr = valorStr;
        this.valorInt = valorInt;
        this.valorDouble = valorDouble;
        this.valorDate = valorDate;
    }
}
