package br.com.live.model;

import java.util.Date;

public class ConsultaInspecaoQualidLanctoMedidas {

	public long idInspecao;
	public long idLancamento;
	public int qtdeMedidaForaTolerancia;
	public int tipoMedida;
	public String usuario;
	public Date dataHora;
	
	public long getIdInspecao() {
		return idInspecao;
	}
	public void setIdInspecao(long idInspecao) {
		this.idInspecao = idInspecao;
	}
	public long getIdLancamento() {
		return idLancamento;
	}
	public void setIdLancamento(long idLancamento) {
		this.idLancamento = idLancamento;
	}
	public int getQtdeMedidaForaTolerancia() {
		return qtdeMedidaForaTolerancia;
	}
	public void setQtdeMedidaForaTolerancia(int qtdeMedidaForaTolerancia) {
		this.qtdeMedidaForaTolerancia = qtdeMedidaForaTolerancia;
	}
	public int getTipoMedida() {
		return tipoMedida;
	}
	public void setTipoMedida(int tipoMedida) {
		this.tipoMedida = tipoMedida;
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
