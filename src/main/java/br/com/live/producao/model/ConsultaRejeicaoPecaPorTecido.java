package br.com.live.producao.model;

public class ConsultaRejeicaoPecaPorTecido {
	
	public int id;
	public String dataRejeicao;
	public String usuario;
	public String estagio;
	public int codEstagio;
	public int turno;
	public int ordemProducao;
	public int periodo;
	public String nivelEstrutura;
	public String grupoEstrutura;
	public String subgruEstrutura;
	public String itemEstrutura;
	public String partePeca;
	public String tecido;
	public int quantidade;
	public String motivo;
	public int codMotivo;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDataRejeicao() {
		return dataRejeicao;
	}
	public void setDataRejeicao(String dataRejeicao) {
		this.dataRejeicao = dataRejeicao;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEstagio() {
		return estagio;
	}
	public void setEstagio(String estagio) {
		this.estagio = estagio;
	}
	public int getTurno() {
		return turno;
	}
	public void setTurno(int turno) {
		this.turno = turno;
	}
	public int getOrdemProducao() {
		return ordemProducao;
	}
	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public String getNivelEstrutura() {
		return nivelEstrutura;
	}
	public void setNivelEstrutura(String nivelEstrutura) {
		this.nivelEstrutura = nivelEstrutura;
	}
	public String getGrupoEstrutura() {
		return grupoEstrutura;
	}
	public void setGrupoEstrutura(String grupoEstrutura) {
		this.grupoEstrutura = grupoEstrutura;
	}
	public String getSubgruEstrutura() {
		return subgruEstrutura;
	}
	public void setSubgruEstrutura(String subgruEstrutura) {
		this.subgruEstrutura = subgruEstrutura;
	}
	public String getItemEstrutura() {
		return itemEstrutura;
	}
	public void setItemEstrutura(String itemEstrutura) {
		this.itemEstrutura = itemEstrutura;
	}
	public String getPartePeca() {
		return partePeca;
	}
	public void setPartePeca(String partePeca) {
		this.partePeca = partePeca;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}	
	public String getTecido() {
		return tecido;
	}
	public void setTecido(String tecido) {
		this.tecido = tecido;
	}
	public int getCodEstagio() {
		return codEstagio;
	}
	public void setCodEstagio(int codEstagio) {
		this.codEstagio = codEstagio;
	}
	public int getCodMotivo() {
		return codMotivo;
	}
	public void setCodMotivo(int codMotivo) {
		this.codMotivo = codMotivo;
	}
	public ConsultaRejeicaoPecaPorTecido() {
		
	}
	public ConsultaRejeicaoPecaPorTecido(int id, String dataRejeicao, String usuario, String estagio, int turno,
			int ordemProducao, int periodo, String nivelEstrutura, String grupoEstrutura, String subgruEstrutura,
			String itemEstrutura, String tecido, String partePeca, int quantidade, String motivo, int codMotivo, int codEstagio) {
		
		this.id = id;
		this.dataRejeicao = dataRejeicao;
		this.usuario = usuario;
		this.estagio = estagio;
		this.turno = turno;
		this.ordemProducao = ordemProducao;
		this.periodo = periodo;
		this.nivelEstrutura = nivelEstrutura;
		this.grupoEstrutura = grupoEstrutura;
		this.subgruEstrutura = subgruEstrutura;
		this.itemEstrutura = itemEstrutura;
		this.tecido = tecido;
		this.partePeca = partePeca;
		this.quantidade = quantidade;
		this.motivo = motivo;
		this.codMotivo = codMotivo;
		this.codEstagio = codEstagio;
	}

	
}
