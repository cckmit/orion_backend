package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "orion_ti_040")
public class ProjetoEntity {

    @Id
    private Long id;
    private int codProjeto;
    private String descricao;

    @Column(name = "data_criacao")
    private Date dataCriacao;
    private String area;
    private String departamento;
    private String setor;

    @Column(name = "origem_projeto")
    private String origemProjeto;

    @Column(name = "sub_origem_projeto")
    private String subOrigemProjeto;
    private String status;

    @Column(name = "objetivo_projeto")
    private String objetivoProjeto;

    private String contextualizacao;

    @Column(name = "descricao_problema")
    private String descricaoproblema;

    @Column(name = "perguntas_aberta")
    private String perguntasAberta;

    private String riscos;

    public ProjetoEntity(Long id, int codProjeto, String descricao, Date dataCriacao, String area, String departamento, String setor, String origemProjeto, String subOrigemProjeto, String status) {
        this.id = id;
        this.codProjeto = codProjeto;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.area = area;
        this.departamento = departamento;
        this.setor = setor;
        this.origemProjeto = origemProjeto;
        this.subOrigemProjeto = subOrigemProjeto;
        this.status = status;
    }

    public ProjetoEntity(Long id, int codProjeto, String descricao, Date dataCriacao, String area, String departamento, String setor, String origemProjeto, String subOrigemProjeto, String status, String objetivoProjeto, String contextualizacao, String descricaoproblema, String perguntasAberta, String riscos) {
        this.id = id;
        this.codProjeto = codProjeto;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.area = area;
        this.departamento = departamento;
        this.setor = setor;
        this.origemProjeto = origemProjeto;
        this.subOrigemProjeto = subOrigemProjeto;
        this.status = status;
        this.objetivoProjeto = objetivoProjeto;
        this.contextualizacao = contextualizacao;
        this.descricaoproblema = descricaoproblema;
        this.perguntasAberta = perguntasAberta;
        this.riscos = riscos;
    }

    public ProjetoEntity(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodProjeto() {
        return codProjeto;
    }

    public void setCodProjeto(int codProjeto) {
        this.codProjeto = codProjeto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getOrigemProjeto() {
        return origemProjeto;
    }

    public void setOrigemProjeto(String origemProjeto) {
        this.origemProjeto = origemProjeto;
    }

    public String getSubOrigemProjeto() {
        return subOrigemProjeto;
    }

    public void setSubOrigemProjeto(String subOrigemProjeto) {
        this.subOrigemProjeto = subOrigemProjeto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
