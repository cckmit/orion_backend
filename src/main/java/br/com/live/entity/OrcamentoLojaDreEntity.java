package br.com.live.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="orion_fin_015")
public class OrcamentoLojaDreEntity {

    @Id
    public Long id;

    @Column(name = "cnpj_loja")
    public String cnpjLoja;

    @Column(name = "ano_orcamento")
    public int anoOrcamento;

    @Column(name = "mes_orcamento")
    public int mesOrcamento;

    @Column(name = "tipo_orcamento")
    public int tipoOrcamento;

    @Column(name = "propriedade")
    public String propriedade;

    @Column(name = "val_propriedade")
    public BigDecimal valPropriedade;

    @Column(name = "seq_consulta")
    public String seqConsulta;

    public OrcamentoLojaDreEntity(Long id, String cnpjLoja, int anoOrcamento, int mesOrcamento, int tipoOrcamento, String propriedade, BigDecimal valPropriedade, String seqConsulta) {
        this.id = id;
        this.cnpjLoja = cnpjLoja;
        this.anoOrcamento = anoOrcamento;
        this.mesOrcamento = mesOrcamento;
        this.tipoOrcamento = tipoOrcamento;
        this.propriedade = propriedade;
        this.valPropriedade = valPropriedade;
        this.seqConsulta = seqConsulta;
    }

    public OrcamentoLojaDreEntity(){
    }
}
