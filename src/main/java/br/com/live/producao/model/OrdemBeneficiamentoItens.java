package br.com.live.producao.model;

public class OrdemBeneficiamentoItens {
	
	public String id;
	public int ordemProducao;
	public String produto;
	public float rolos;
	public float quilos;
	public float largura;
	public float gramatura;
	public float rendimento;
	public int periodoProducao;
	public String maquina;
	public String um;
	public int tipoOrdem;
	public String observacao;
    public int alternativa;
    public int roteiro;
    public int deposito;
    public String descDeposito;
    public int alternativaItem;
    public int roteiroItem;    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getOrdemProducao() {
		return ordemProducao;
	}
	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public float getRolos() {
		return rolos;
	}
	public void setRolos(float rolos) {
		this.rolos = rolos;
	}
	public float getQuilos() {
		return quilos;
	}
	public void setQuilos(float quilos) {
		this.quilos = quilos;
	}
	public float getLargura() {
		return largura;
	}
	public void setLargura(float largura) {
		this.largura = largura;
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
	public int getPeriodoProducao() {
		return periodoProducao;
	}
	public void setPeriodoProducao(int periodoProducao) {
		this.periodoProducao = periodoProducao;
	}
	public String getMaquina() {
		return maquina;
	}
	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}	
	public String getUm() {
		return um;
	}
	public void setUm(String um) {
		this.um = um;
	}
	public int getTipoOrdem() {
		return tipoOrdem;
	}
	public void setTipoOrdem(int tipoOrdem) {
		this.tipoOrdem = tipoOrdem;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public int getAlternativa() {
		return alternativa;
	}
	public void setAlternativa(int alternativa) {
		this.alternativa = alternativa;
	}
	public int getRoteiro() {
		return roteiro;
	}
	public void setRoteiro(int roteiro) {
		this.roteiro = roteiro;
	}
	public int getDeposito() {
		return deposito;
	}
	public void setDeposito(int deposito) {
		this.deposito = deposito;
	}
	public String getDescDeposito() {
		return descDeposito;
	}
	public void setDescDeposito(String descDeposito) {
		this.descDeposito = descDeposito;
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
	public OrdemBeneficiamentoItens() {
		
	}
	
	public OrdemBeneficiamentoItens(String id, int ordemProducao, String produto, float rolos, float quilos,
			float largura, float gramatura, float rendimento, int periodoProducao, String maquina, String um,
			int tipoOrdem, String observacao, int alternativa, int roteiro, int deposito, String descDeposito, int alternativaItem, int roteiroItem) {
		this.id = id;
		this.ordemProducao = ordemProducao;
		this.produto = produto;
		this.rolos = rolos;
		this.quilos = quilos;
		this.largura = largura;
		this.gramatura = gramatura;
		this.rendimento = rendimento;
		this.periodoProducao = periodoProducao;
		this.maquina = maquina;
		this.um = um;
		this.tipoOrdem = tipoOrdem;
		this.observacao = observacao;
		this.alternativa = alternativa;
		this.roteiro = roteiro;
		this.deposito = deposito;
		this.descDeposito = descDeposito;
		this.alternativaItem = alternativaItem;
		this.roteiroItem = roteiroItem;
	}
	
	public OrdemBeneficiamentoItens(int ordemProducao, String produto, int alternativaItem, int roteiroItem) {
		super();
		this.ordemProducao = ordemProducao;
		this.produto = produto;
		this.alternativaItem = alternativaItem;
		this.roteiroItem = roteiroItem;
	}
}
