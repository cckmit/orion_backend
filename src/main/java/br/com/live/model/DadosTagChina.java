package br.com.live.model;

public class DadosTagChina {
	
	private String referencia;
	private String tamanho;
	private String cor;
	private String descCor;
	private String descReferencia;
	private String qrCode;
	private String codBarrasEan;
	private int quantidade;
	private float preco;
	private String atributo;
	private String codBarrasProd;
	private int sequencia;
	private String tamanhoMedida;
	
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getDescCor() {
		return descCor;
	}
	public void setDescCor(String descCor) {
		this.descCor = descCor;
	}
	public String getDescReferencia() {
		return descReferencia;
	}
	public void setDescReferencia(String descReferencia) {
		this.descReferencia = descReferencia;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getCodBarrasEan() {
		return codBarrasEan;
	}
	public void setCodBarrasEan(String codBarrasEan) {
		this.codBarrasEan = codBarrasEan;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	public String getAtributo() {
		return atributo;
	}
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}
	public String getCodBarrasProd() {
		return codBarrasProd;
	}
	public void setCodBarrasProd(String codBarrasProd) {
		this.codBarrasProd = codBarrasProd;
	}
	
	public int getSequencia() {
		return sequencia;
	}
	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}
	
	public String getTamanhoMedida() {
		return tamanhoMedida;
	}
	public void setTamanhoMedida(String tamanhoMedida) {
		this.tamanhoMedida = tamanhoMedida;
	}
	public DadosTagChina() {
		
	}
	
	public DadosTagChina(String referencia, String tamanho, String cor, String descCor, String descReferencia,
			String qrCode, String codBarrasEan, int quantidade, float preco, String atributo, String codBarrasProd, int sequencia, String tamanhoMedida) {

		this.referencia = referencia;
		this.tamanho = tamanho;
		this.cor = cor;
		this.descCor = descCor;
		this.descReferencia = descReferencia;
		this.qrCode = qrCode;
		this.codBarrasEan = codBarrasEan;
		this.quantidade = quantidade;
		this.preco = preco;
		this.atributo = atributo;
		this.codBarrasProd = codBarrasProd + String.format("%04d", sequencia);
		this.sequencia = sequencia;
		this.tamanhoMedida = tamanhoMedida;
	}
}
