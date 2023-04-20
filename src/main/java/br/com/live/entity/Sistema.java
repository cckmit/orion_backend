package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ti_005")
public class Sistema {

    @Id
    public int id;

    @Column(name = "nome_sistema")
    public String nomeSistema;

    public String objetivo;

    @Column(name = "banco_de_dados")
    public String bancoDeDados;

    public String tipo;
    public String fornecedor;
    public String cnpj;
    public String endereco;

    @Column(name = "forma_pagto")
    public String formaPagto;

    @Column(name = "tem_contrato")
    public boolean temContrato;

    public byte[] contrato;
    public String ambiente;
    public String status;

    @Column(name = "usuarios_ativos")
    public int usuariosAtivos;

    @Column(name = "capacidade_usuarios")
    public int capacidadeUsuarios;
    
    @Column(name = "gestor_responsavel")
    public int gestorResponsavel;

    public Sistema(int id, String nomeSistema, String objetivo, String bancoDeDados, String tipo, String fornecedor, String cnpj, String endereco, String formaPagto, 
    		boolean temContrato, byte[] contrato, String ambiente, String status, int usuariosAtivos, int capacidadeUsuarios, int gestorResponsavel) {
        this.id = id;
        this.nomeSistema = nomeSistema;
        this.objetivo = objetivo;
        this.bancoDeDados = bancoDeDados;
        this.tipo = tipo;
        this.fornecedor = fornecedor;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.formaPagto = formaPagto;
        this.temContrato = temContrato;
        this.contrato = contrato;
        this.ambiente = ambiente;
        this.status = status;
        this.usuariosAtivos = usuariosAtivos;
        this.capacidadeUsuarios = capacidadeUsuarios;
        this.gestorResponsavel = gestorResponsavel;
    }

    public Sistema(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeSistema() {
        return nomeSistema;
    }

    public void setNomeSistema(String nomeSistema) {
        this.nomeSistema = nomeSistema;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getBancoDeDados() {
        return bancoDeDados;
    }

    public void setBancoDeDados(String bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getFormaPagto() {
        return formaPagto;
    }

    public void setFormaPagto(String formaPagto) {
        this.formaPagto = formaPagto;
    }

    public boolean isTemContrato() {
        return temContrato;
    }

    public void setTemContrato(boolean temContrato) {
        this.temContrato = temContrato;
    }

    public byte[] getContrato() {
        return contrato;
    }

    public void setContrato(byte[] contrato) {
        this.contrato = contrato;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUsuariosAtivos() {
        return usuariosAtivos;
    }

    public void setUsuariosAtivos(int usuariosAtivos) {
        this.usuariosAtivos = usuariosAtivos;
    }

    public int getCapacidadeUsuarios() {
        return capacidadeUsuarios;
    }

    public void setCapacidadeUsuarios(int capacidadeUsuarios) {
        this.capacidadeUsuarios = capacidadeUsuarios;
    }

	public int getGestorResponsavel() {
		return gestorResponsavel;
	}

	public void setGestorResponsavel(int gestorResponsavel) {
		this.gestorResponsavel = gestorResponsavel;
	}
    
}
