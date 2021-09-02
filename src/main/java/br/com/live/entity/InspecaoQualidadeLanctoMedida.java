package br.com.live.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_052")
public class InspecaoQualidadeLanctoMedida {

	@Id
	public long id;
	
	@Column(name = "id_lancamento")
	public long idLancamento;
		
	@Column(name = "id_inspecao")	
	public long idInspecao;
	
	@Column(name = "tipo_medida")
	public int tipoMedida;
	
	public int sequencia;	
	public String descricao;
	
	@Column(name = "medida_padrao")
	public float medidaPadrao;
			
	@Column(name = "toler_maxima")
	public float toleranciaMaxima;
	
	@Column(name = "toler_minima")
	public float toleranciaMinima;
	
	@Column(name = "medida_real")
	public float medidaReal;
	public float variacao;
	
	public String usuario;
	
	@Column(name = "data_hora")
	public Date dataHora;	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdLancamento() {
		return idLancamento;
	}

	public void setIdLancamento(long idLancamento) {
		this.idLancamento = idLancamento;
	}

	public long getIdInspecao() {
		return idInspecao;
	}

	public void setIdInspecao(long idInspecao) {
		this.idInspecao = idInspecao;
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

	public float getMedidaPadrao() {
		return medidaPadrao;
	}

	public void setMedidaPadrao(float medidaPadrao) {
		this.medidaPadrao = medidaPadrao;
	}

	public float getToleranciaMaxima() {
		return toleranciaMaxima;
	}

	public void setToleranciaMaxima(float toleranciaMaxima) {
		this.toleranciaMaxima = toleranciaMaxima;
	}

	public float getToleranciaMinima() {
		return toleranciaMinima;
	}

	public void setToleranciaMinima(float toleranciaMinima) {
		this.toleranciaMinima = toleranciaMinima;
	}

	public float getMedidaReal() {
		return medidaReal;
	}

	public void setMedidaReal(float medidaReal) {
		this.medidaReal = medidaReal;
	}

	public float getVariacao() {
		return variacao;
	}

	public void setVariacao(float variacao) {
		this.variacao = variacao;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
}
