package br.com.live.model;

public class ConsultaDadosRoteiro {

	public int estagio;  
	public int operacao; 
	public int centroCusto;
	public int minutos;
	public int familia; 
	public int seqOperacao; 
	public int seqEstagio; 
	public int estagioDepende; 
	public int pedeProduto; 
	public int tipoOperCmc;
	
	public int getEstagio() {
		return estagio;
	}
	public void setEstagio(int estagio) {
		this.estagio = estagio;
	}
	public int getOperacao() {
		return operacao;
	}
	public void setOperacao(int operacao) {
		this.operacao = operacao;
	}
	public int getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(int centroCusto) {
		this.centroCusto = centroCusto;
	}
	public int getMinutos() {
		return minutos;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	public int getFamilia() {
		return familia;
	}
	public void setFamilia(int familia) {
		this.familia = familia;
	}
	public int getSeqOperacao() {
		return seqOperacao;
	}
	public void setSeqOperacao(int seqOperacao) {
		this.seqOperacao = seqOperacao;
	}
	public int getSeqEstagio() {
		return seqEstagio;
	}
	public void setSeqEstagio(int seqEstagio) {
		this.seqEstagio = seqEstagio;
	}
	public int getEstagioDepende() {
		return estagioDepende;
	}
	public void setEstagioDepende(int estagioDepende) {
		this.estagioDepende = estagioDepende;
	}
	public int getPedeProduto() {
		return pedeProduto;
	}
	public void setPedeProduto(int pedeProduto) {
		this.pedeProduto = pedeProduto;
	}
	public int getTipoOperCmc() {
		return tipoOperCmc;
	}
	public void setTipoOperCmc(int tipoOperCmc) {
		this.tipoOperCmc = tipoOperCmc;
	}
}
