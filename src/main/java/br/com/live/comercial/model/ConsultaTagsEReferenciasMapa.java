package br.com.live.comercial.model;

public class ConsultaTagsEReferenciasMapa {
	
	public String tag;
	public String endereco;
	public String referencia;
	
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}	
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	public ConsultaTagsEReferenciasMapa() {
		
	}
	
	public ConsultaTagsEReferenciasMapa(String tag, String endereco, String referencia) {
		
		this.tag = tag;
		this.endereco = endereco;
		this.referencia = referencia;
	}
	
}
