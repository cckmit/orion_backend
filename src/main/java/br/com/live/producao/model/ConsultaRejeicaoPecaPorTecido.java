package br.com.live.producao.model;

public class ConsultaRejeicaoPecaPorTecido {
	
	public int id;
	public String dataRejeicao;
	public String usuario;
	public String nivel_tecido;
	public String grupo_tecido;
	public String subgru_tecido;
	public String item_tecido;
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
	public String getNivel_tecido() {
		return nivel_tecido;
	}
	public void setNivel_tecido(String nivel_tecido) {
		this.nivel_tecido = nivel_tecido;
	}
	public String getGrupo_tecido() {
		return grupo_tecido;
	}
	public void setGrupo_tecido(String grupo_tecido) {
		this.grupo_tecido = grupo_tecido;
	}
	public String getSubgru_tecido() {
		return subgru_tecido;
	}
	public void setSubgru_tecido(String subgru_tecido) {
		this.subgru_tecido = subgru_tecido;
	}
	public String getItem_tecido() {
		return item_tecido;
	}
	public void setItem_tecido(String item_tecido) {
		this.item_tecido = item_tecido;
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
	
	public ConsultaRejeicaoPecaPorTecido(int id, String dataRejeicao, String usuario, String nivel_tecido,
			String grupo_tecido, String subgru_tecido, String item_tecido, String estagio, int codEstagio, int turno,
			int ordemProducao, int periodo, String nivelEstrutura, String grupoEstrutura, String subgruEstrutura,
			String itemEstrutura, String partePeca, String tecido, int quantidade, String motivo, int codMotivo) {
		super();
		this.id = id;
		this.dataRejeicao = dataRejeicao;
		this.usuario = usuario;
		this.nivel_tecido = nivel_tecido;
		this.grupo_tecido = grupo_tecido;
		this.subgru_tecido = subgru_tecido;
		this.item_tecido = item_tecido;
		this.estagio = estagio;
		this.codEstagio = codEstagio;
		this.turno = turno;
		this.ordemProducao = ordemProducao;
		this.periodo = periodo;
		this.nivelEstrutura = nivelEstrutura;
		this.grupoEstrutura = grupoEstrutura;
		this.subgruEstrutura = subgruEstrutura;
		this.itemEstrutura = itemEstrutura;
		this.partePeca = partePeca;
		this.tecido = tecido;
		this.quantidade = quantidade;
		this.motivo = motivo;
		this.codMotivo = codMotivo;
	}
	
}
