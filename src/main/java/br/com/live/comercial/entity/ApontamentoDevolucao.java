package br.com.live.comercial.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_exp_200")
public class ApontamentoDevolucao {
	
	@Id
	public int id;
	public Date data;
	public String hora;
	public int usuario;
	
	@Column(name = "nf_devolucao")
	public int nfDevolucao;
	
	@Column(name = "tipo_devolucao")
	public int tipoDevolucao;
	
	@Column(name = "cod_motivo")
	public int codMotivo;
	
	@Column(name = "cod_transacao")
	public int codTransacao;
	
	@Column(name = "cod_caixa")
	public int codCaixa;
	
	@Column(name = "cod_barras_tag")
	public String codBarrasTag;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public int getUsuario() {
		return usuario;
	}
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	public int getNfDevolucao() {
		return nfDevolucao;
	}
	public void setNfDevolucao(int nfDevolucao) {
		this.nfDevolucao = nfDevolucao;
	}
	public int getTipoDevolucao() {
		return tipoDevolucao;
	}
	public void setTipoDevolucao(int tipoDevolucao) {
		this.tipoDevolucao = tipoDevolucao;
	}
	public int getCodMotivo() {
		return codMotivo;
	}
	public void setCodMotivo(int codMotivo) {
		this.codMotivo = codMotivo;
	}
	public int getCodTransacao() {
		return codTransacao;
	}
	public void setCodTransacao(int codTransacao) {
		this.codTransacao = codTransacao;
	}
	public int getCodCaixa() {
		return codCaixa;
	}
	public void setCodCaixa(int codCaixa) {
		this.codCaixa = codCaixa;
	}
	public String getCodBarrasTag() {
		return codBarrasTag;
	}
	public void setCodBarrasTag(String codBarrasTag) {
		this.codBarrasTag = codBarrasTag;
	}
	
	public ApontamentoDevolucao() {
		
	}
	public ApontamentoDevolucao(int id, Date data, String hora, int usuario, int nfDevolucao, int tipoDevolucao,
			int codMotivo, int codTransacao, int codCaixa, String codBarrasTag) {
		
		this.id = id;
		this.data = data;
		this.hora = hora;
		this.usuario = usuario;
		this.nfDevolucao = nfDevolucao;
		this.tipoDevolucao = tipoDevolucao;
		this.codMotivo = codMotivo;
		this.codTransacao = codTransacao;
		this.codCaixa = codCaixa;
		this.codBarrasTag = codBarrasTag;
	}
	
}
