package br.com.live.producao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_bnf_110")
public class OrdemBeneficiamentoItem {
	
	@Id
	public String id;
	
	public int sequencia;
	public String usuario;
	
	@Column(name = "ordem_producao")
	public int ordemProducao;
	
	@Column(name = "pano_sbg_nivel99")
	public String panoSbgNivel99;
	
	@Column(name = "pano_sbg_grupo")
	public String panoSbgGrupo;
	
	@Column(name = "pano_sbg_subgrupo")
	public String panoSbgSubgrupo;
	                             
	@Column(name = "pano_sbg_item")
	public String panoSbgItem;
	
	@Column(name = "qtde_rolos")
	public float qtdeRolos;
	
	@Column(name = "qtde_quilos")
	public float qtdeQuilos;
	
	@Column(name = "und_medida")
	public String undMedida;
	
	@Column(name = "largura_tecido")
	public float larguraTecido;
	
	public float gramatura;
	public float rendimento;
    
    public int deposito;
    public int alternativaItem;
    public int roteiroItem;
    public String observacao;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSequencia() {
		return sequencia;
	}
	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getOrdemProducao() {
		return ordemProducao;
	}
	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}
	public String getPanoSbgNivel99() {
		return panoSbgNivel99;
	}
	public void setPanoSbgNivel99(String panoSbgNivel99) {
		this.panoSbgNivel99 = panoSbgNivel99;
	}
	public String getPanoSbgGrupo() {
		return panoSbgGrupo;
	}
	public void setPanoSbgGrupo(String panoSbgGrupo) {
		this.panoSbgGrupo = panoSbgGrupo;
	}
	public String getPanoSbgSubgrupo() {
		return panoSbgSubgrupo;
	}
	public void setPanoSbgSubgrupo(String panoSbgSubgrupo) {
		this.panoSbgSubgrupo = panoSbgSubgrupo;
	}
	public String getPanoSbgItem() {
		return panoSbgItem;
	}
	public void setPanoSbgItem(String panoSbgItem) {
		this.panoSbgItem = panoSbgItem;
	}
	public float getQtdeRolos() {
		return qtdeRolos;
	}
	public void setQtdeRolos(float qtdeRolos) {
		this.qtdeRolos = qtdeRolos;
	}
	public float getQtdeQuilos() {
		return qtdeQuilos;
	}
	public String getUndMedida() {
		return undMedida;
	}
	public void setUndMedida(String undMedida) {
		this.undMedida = undMedida;
	}
	public void setQtdeQuilos(float qtdeQuilos) {
		this.qtdeQuilos = qtdeQuilos;
	}
	public float getLarguraTecido() {
		return larguraTecido;
	}
	public void setLarguraTecido(float larguraTecido) {
		this.larguraTecido = larguraTecido;
	}
	public float getGramatura() {
		return gramatura;
	}
	public void setGramatura(float gramatura) {
		this.gramatura = gramatura;
	}
	public float getRendimento() {
		return rendimento;
	}
	public void setRendimento(float rendimento) {
		this.rendimento = rendimento;
	}
	public int getDeposito() {
		return deposito;
	}
	public void setDeposito(int deposito) {
		this.deposito = deposito;
	}	
	public int getAlternativaItem() {
		return alternativaItem;
	}
	public void setAlternativaItem(int alternativaItem) {
		this.alternativaItem = alternativaItem;
	}
	public int getRoteiroItem() {
		return roteiroItem;
	}
	public void setRoteiroItem(int roteiroItem) {
		this.roteiroItem = roteiroItem;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public OrdemBeneficiamentoItem() {
		
	}
	public OrdemBeneficiamentoItem(int sequencia, String usuario, int ordemProducao, String panoSbgNivel99,
			String panoSbgGrupo, String panoSbgSubgrupo, String panoSbgItem, float qtdeRolos, float qtdeQuilos,
			String undMedida, float larguraTecido, float gramatura, float rendimento, int alternativaItem, int roteiroItem, int deposito, String observacao) {
		this.id = usuario + "-" + sequencia;
		this.sequencia = sequencia;
		this.usuario = usuario;
		this.ordemProducao = ordemProducao;
		this.panoSbgNivel99 = panoSbgNivel99;
		this.panoSbgGrupo = panoSbgGrupo;
		this.panoSbgSubgrupo = panoSbgSubgrupo;
		this.panoSbgItem = panoSbgItem;
		this.qtdeRolos = qtdeRolos;
		this.qtdeQuilos = qtdeQuilos;
		this.undMedida = undMedida;
		this.larguraTecido = larguraTecido;
		this.gramatura = gramatura;
		this.rendimento = rendimento;
		this.alternativaItem = alternativaItem;
		this.roteiroItem = roteiroItem;
		this.deposito = deposito;
		this.observacao = observacao;
	}
	
	

}
