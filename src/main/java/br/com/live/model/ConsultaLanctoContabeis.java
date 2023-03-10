package br.com.live.model;

import java.util.Date;

public class ConsultaLanctoContabeis {
	
	public int id;
	public int codEmpresa;
	public int filialLancto;
    public int exercicio;
    public int origem;
    public String contaContabil;
	public int contaReduzida;
	public String debitoCredito;
	public float valorLancto;
	public int centroCusto;
	public int histContabil;
	public String dataLancto;
	public String complHistor1;    
	public String datainsercao;
	public String programa;
	public String usuario;
	public int lote;
	public int numeroLanc;
	public int seqLanc;	  
    public int periodo;
    public int status;
    public String criticas;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCodEmpresa() {
		return codEmpresa;
	}
	public void setCodEmpresa(int codEmpresa) {
		this.codEmpresa = codEmpresa;
	}
	public int getFilialLancto() {
		return filialLancto;
	}
	public void setFilialLancto(int filialLancto) {
		this.filialLancto = filialLancto;
	}
	public int getExercicio() {
		return exercicio;
	}
	public void setExercicio(int exercicio) {
		this.exercicio = exercicio;
	}
	public int getOrigem() {
		return origem;
	}
	public void setOrigem(int origem) {
		this.origem = origem;
	}
	public String getContaContabil() {
		return contaContabil;
	}
	public void setContaContabil(String contaContabil) {
		this.contaContabil = contaContabil;
	}
	public int getContaReduzida() {
		return contaReduzida;
	}
	public void setContaReduzida(int contaReduzida) {
		this.contaReduzida = contaReduzida;
	}
	public String getDebitoCredito() {
		return debitoCredito;
	}
	public void setDebitoCredito(String debitoCredito) {
		this.debitoCredito = debitoCredito;
	}
	public float getValorLancto() {
		return valorLancto;
	}
	public void setValorLancto(float valorLancto) {
		this.valorLancto = valorLancto;
	}
	public int getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(int centroCusto) {
		this.centroCusto = centroCusto;
	}
	public int getHistContabil() {
		return histContabil;
	}
	public void setHistContabil(int histContabil) {
		this.histContabil = histContabil;
	}
	public String getDataLancto() {
		return dataLancto;
	}
	public void setDataLancto(String dataLancto) {
		this.dataLancto = dataLancto;
	}
	public String getComplHistor1() {
		return complHistor1;
	}
	public void setComplHistor1(String complHistor1) {
		this.complHistor1 = complHistor1;
	}
	public String getDatainsercao() {
		return datainsercao;
	}
	public void setDatainsercao(String datainsercao) {
		this.datainsercao = datainsercao;
	}
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getLote() {
		return lote;
	}
	public void setLote(int lote) {
		this.lote = lote;
	}
	public int getNumeroLanc() {
		return numeroLanc;
	}
	public void setNumeroLanc(int numeroLanc) {
		this.numeroLanc = numeroLanc;
	}
	public int getSeqLanc() {
		return seqLanc;
	}
	public void setSeqLanc(int seqLanc) {
		this.seqLanc = seqLanc;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCriticas() {
		return criticas;
	}
	public void setCriticas(String criticas) {
		this.criticas = criticas;
	}
	public ConsultaLanctoContabeis() {
		
	}
	public ConsultaLanctoContabeis(int id, int codEmpresa, int filialLancto, int exercicio, int origem,
			String contaContabil, int contaReduzida, String debitoCredito, float valorLancto, int centroCusto,
			int histContabil, String dataLancto, String complHistor1, String datainsercao, String programa,
			String usuario, int lote, int numeroLanc, int seqLanc, int periodo, int status, String criticas) {
	
		this.id = id;
		this.codEmpresa = codEmpresa;
		this.filialLancto = filialLancto;
		this.exercicio = exercicio;
		this.origem = origem;
		this.contaContabil = contaContabil;
		this.contaReduzida = contaReduzida;
		this.debitoCredito = debitoCredito;
		this.valorLancto = valorLancto;
		this.centroCusto = centroCusto;
		this.histContabil = histContabil;
		this.dataLancto = dataLancto;
		this.complHistor1 = complHistor1;
		this.datainsercao = datainsercao;
		this.programa = programa;
		this.usuario = usuario;
		this.lote = lote;
		this.numeroLanc = numeroLanc;
		this.seqLanc = seqLanc;
		this.periodo = periodo;
		this.status = status;
		this.criticas = criticas;
	}
	
}
