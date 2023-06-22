package br.com.live.administrativo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_cnt_010")
public class LancamentoContabeisImport {
	
	@Id
	public int id;
	
	@Column(name = "filial_lancto")
	public int filialLancto;
	
    public int exercicio;
    public int origem;     
    
    @Column(name = "conta_reduzida")
	public int contaReduzida;
    
    @Column(name = "debito_credito")
	public String debitoCredito;
    
    @Column(name = "valor_lancto")
	public float valorLancto;
    
    @Column(name = "centro_custo")
	public int centroCusto;
    
    @Column(name = "hist_contabil")
	public int histContabil;
    
    @Column(name = "data_lancto")
	public Date dataLancto;
    
    @Column(name = "compl_histor1")
	public String complHistor1;
    
	public Date datainsercao;
	public String usuario;
	public int lote;
	
	@Column(name = "numero_lanc")
	public int numeroLanc;
	
	@Column(name = "seq_lanc")
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
	public Date getDataLancto() {
		return dataLancto;
	}
	public void setDataLancto(Date dataLancto) {
		this.dataLancto = dataLancto;
	}
	public String getComplHistor1() {
		return complHistor1;
	}
	public void setComplHistor1(String complHistor1) {
		this.complHistor1 = complHistor1;
	}
	public Date getDatainsercao() {
		return datainsercao;
	}
	public void setDatainsercao(Date datainsercao) {
		this.datainsercao = datainsercao;
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
	public LancamentoContabeisImport() {
		
	}
	public LancamentoContabeisImport(int id, int filialLancto, int exercicio, int origem,
			int contaReduzida, String debitoCredito, float valorLancto, int centroCusto, int histContabil,
			Date dataLancto, String complHistor1, Date datainsercao, String usuario, int lote, int numeroLanc,
			int seqLanc, int periodo, int status, String criticas) {
		this.id = id;
		this.filialLancto = filialLancto;
		this.exercicio = exercicio;
		this.origem = origem;
		this.contaReduzida = contaReduzida;
		this.debitoCredito = debitoCredito.toUpperCase();
		this.valorLancto = valorLancto;
		this.centroCusto = centroCusto;
		this.histContabil = histContabil;
		this.dataLancto = dataLancto;
		this.complHistor1 = complHistor1;
		this.datainsercao = datainsercao;
		this.usuario = usuario;
		this.lote = lote;
		this.numeroLanc = numeroLanc;
		this.seqLanc = seqLanc;
		this.periodo = periodo;
		this.status = status;
		this.criticas = criticas;
	}
	
}
