package br.com.live.producao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_cfc_330")
public class RejeicaoPecaPorTecido {
	
	@Id	
	public int id;
	
	@Column(name = "data_rejeicao")
	public Date dataRejeicao;
	
	public int usuario;
	
	@Column(name = "nivel_tecido")
	public String nivelTecido;
	
	@Column(name = "grupo_tecido")
	public String grupoTecido;
	
	@Column(name = "subgru_tecido")
	public String subgruTecido;
	
	@Column(name = "item_tecido")
	public String itemTecido;
	
	public int estagio;
	public int turno;
	
	@Column(name = "ordem_producao")
	public int ordemProducao;
	
	public int periodo;
	
	@Column(name = "nivel_estrutura")
	public String nivelEstrutura;
	
	@Column(name = "grupo_estrutura")
	public String grupoEstrutura;
	
	@Column(name = "subgru_estrutura")
	public String subgruEstrutura;
	
	@Column(name = "item_estrutura")
	public String itemEstrutura;
	
	@Column(name = "parte_peca")
	public String partePeca;
	
	public int quantidade;
	public int motivo;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDataRejeicao() {
		return dataRejeicao;
	}
	public void setDataRejeicao(Date dataRejeicao) {
		this.dataRejeicao = dataRejeicao;
	}
	public int getUsuario() {
		return usuario;
	}
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	public String getNivelTecido() {
		return nivelTecido;
	}
	public void setNivelTecido(String nivelTecido) {
		this.nivelTecido = nivelTecido;
	}
	public String getGrupoTecido() {
		return grupoTecido;
	}
	public void setGrupoTecido(String grupoTecido) {
		this.grupoTecido = grupoTecido;
	}
	public String getSubgruTecido() {
		return subgruTecido;
	}
	public void setSubgruTecido(String subgruTecido) {
		this.subgruTecido = subgruTecido;
	}
	public String getItemTecido() {
		return itemTecido;
	}
	public void setItemTecido(String itemTecido) {
		this.itemTecido = itemTecido;
	}
	public int getEstagio() {
		return estagio;
	}
	public void setEstagio(int estagio) {
		this.estagio = estagio;
	}
	public int getTurno() {
		return turno;
	}
	public void setTurno(int turno) {
		this.turno = turno;
	}
	public int getOrdemProducao() {
		return ordemProducao;
	}
	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public String getNivelEstrutura() {
		return nivelEstrutura;
	}
	public void setNivelEstrutura(String nivelEstrutura) {
		this.nivelEstrutura = nivelEstrutura;
	}
	public String getGrupoEstrutura() {
		return grupoEstrutura;
	}
	public void setGrupoEstrutura(String grupoEstrutura) {
		this.grupoEstrutura = grupoEstrutura;
	}
	public String getSubgruEstrutura() {
		return subgruEstrutura;
	}
	public void setSubgruEstrutura(String subgruEstrutura) {
		this.subgruEstrutura = subgruEstrutura;
	}
	public String getItemEstrutura() {
		return itemEstrutura;
	}
	public void setItemEstrutura(String itemEstrutura) {
		this.itemEstrutura = itemEstrutura;
	}
	public String getPartePeca() {
		return partePeca;
	}
	public void setPartePeca(String partePeca) {
		this.partePeca = partePeca;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getMotivo() {
		return motivo;
	}
	public void setMotivo(int motivo) {
		this.motivo = motivo;
	}
	
	public RejeicaoPecaPorTecido() {
		
	}
	public RejeicaoPecaPorTecido(int id, Date dataRejeicao, int usuario, String nivelTecido, String grupoTecido,
			String subgruTecido, String itemTecido, int estagio, int turno, int ordemProducao, int periodo,
			String nivelEstrutura, String grupoEstrutura, String subgruEstrutura, String itemEstrutura,
			String partePeca, int quantidade, int motivo) {
		
		this.id = id;
		this.dataRejeicao = dataRejeicao;
		this.usuario = usuario;
		this.nivelTecido = nivelTecido;
		this.grupoTecido = grupoTecido;
		this.subgruTecido = subgruTecido;
		this.itemTecido = itemTecido;
		this.estagio = estagio;
		this.turno = turno;
		this.ordemProducao = ordemProducao;
		this.periodo = periodo;
		this.nivelEstrutura = nivelEstrutura;
		this.grupoEstrutura = grupoEstrutura;
		this.subgruEstrutura = subgruEstrutura;
		this.itemEstrutura = itemEstrutura;
		this.partePeca = partePeca;
		this.quantidade = quantidade;
		this.motivo = motivo;
	}
		
}
