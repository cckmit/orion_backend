package br.com.live.producao.model;

public class MotivoRejeicao {

	public int codMotivo;
	public String descMotivo;
	public int codEstagio;
	public String descEstagio;
	
	public int getCodMotivo() {
		return codMotivo;
	}
	public void setCodMotivo(int codMotivo) {
		this.codMotivo = codMotivo;
	}
	public String getDescMotivo() {
		return descMotivo;
	}
	public void setDescMotivo(String descMotivo) {
		this.descMotivo = descMotivo;
	}
	public int getCodEstagio() {
		return codEstagio;
	}
	public void setCodEstagio(int codEstagio) {
		this.codEstagio = codEstagio;
	}
	public String getDescEstagio() {
		return descEstagio;
	}
	public void setDescEstagio(String descEstagio) {
		this.descEstagio = descEstagio;
	}
}
