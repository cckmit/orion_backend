package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_fin_010")
public class ContaContabilConsiderarDre {

    @Id
    @Column(name = "conta_contabil")
    public int contaContabil;

    @Column(name = "gasto_variavel")
    public boolean gastoVariavel;

    @Column(name = "custo_ocupacao")
    public boolean custoOcupacao;

    @Column(name = "despesa_folha")
    public boolean despesaFolha;

    @Column(name = "despesa_geral")
    public boolean despesaGeral;

    public boolean depreciacao;

    public ContaContabilConsiderarDre(int contaContabil, boolean gastoVariavel, boolean custoOcupacao, boolean despesaFolha, boolean despesaGeral, boolean depreciacao) {
        this.contaContabil = contaContabil;
        this.gastoVariavel = gastoVariavel;
        this.custoOcupacao = custoOcupacao;
        this.despesaFolha = despesaFolha;
        this.despesaGeral = despesaGeral;
        this.depreciacao = depreciacao;
    }

    public ContaContabilConsiderarDre(){
    }
}
