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
	private String bullet1;
	private String bullet2;
	private String bullet3;
	private String bullet4;
	private String bullet5;
	private String bullet6;
	private String bullet7;

	
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

	public String getBullet1() {
		return bullet1;
	}

	public void setBullet1(String bullet1) {
		this.bullet1 = bullet1;
	}

	public String getBullet2() {
		return bullet2;
	}

	public void setBullet2(String bullet2) {
		this.bullet2 = bullet2;
	}

	public String getBullet3() {
		return bullet3;
	}

	public void setBullet3(String bullet3) {
		this.bullet3 = bullet3;
	}

	public String getBullet4() {
		return bullet4;
	}

	public void setBullet4(String bullet4) {
		this.bullet4 = bullet4;
	}

	public String getBullet5() {
		return bullet5;
	}

	public void setBullet5(String bullet5) {
		this.bullet5 = bullet5;
	}

	public String getBullet6() {
		return bullet6;
	}

	public void setBullet6(String bullet6) {
		this.bullet6 = bullet6;
	}

	public String getBullet7() {
		return bullet7;
	}

	public void setBullet7(String bullet7) {
		this.bullet7 = bullet7;
	}

	public DadosTagChina() {
	}
	
	public DadosTagChina(String referencia, String tamanho, String cor, String descCor, String descReferencia,
			String qrCode, String codBarrasEan, int quantidade, float preco, String atributo, String codBarrasProd, int sequencia, String tamanhoMedida,
						 String bullet1, String bullet2, String bullet3, String bullet4, String bullet5, String bullet6, String bullet7) {

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
		this.bullet1 = bullet1;
		this.bullet2 = bullet2;
		this.bullet3 = bullet3;
		this.bullet4 = bullet4;
		this.bullet5 = bullet5;
		this.bullet6 = bullet6;
		this.bullet7 = bullet7;
	}
}
