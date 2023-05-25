package br.com.live.model;

public class ConsultaIndicadores {
	
	public int id;
	public String nome;
	public String grupo;
	public String area;
	public String dpto;
	public String setor;
	public String undMedida;
	public int freqMonitoramento;
	public int fonteDados;
	public int polaridade;
	public String respPublic;
	public String obs;
	public String formula;
	public int situacao;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDpto() {
		return dpto;
	}
	public void setDpto(String dpto) {
		this.dpto = dpto;
	}
	public String getSetor() {
		return setor;
	}
	public void setSetor(String setor) {
		this.setor = setor;
	}
	public String getUndMedida() {
		return undMedida;
	}
	public void setUndMedida(String undMedida) {
		this.undMedida = undMedida;
	}
	public int getFreqMonitoramento() {
		return freqMonitoramento;
	}
	public void setFreqMonitoramento(int freqMonitoramento) {
		this.freqMonitoramento = freqMonitoramento;
	}
	public int getFonteDados() {
		return fonteDados;
	}
	public void setFonteDados(int fonteDados) {
		this.fonteDados = fonteDados;
	}
	public int getPolaridade() {
		return polaridade;
	}
	public void setPolaridade(int polaridade) {
		this.polaridade = polaridade;
	}
	public String getRespPublic() {
		return respPublic;
	}
	public void setRespPublic(String respPublic) {
		this.respPublic = respPublic;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public int getSituacao() {
		return situacao;
	}
	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}
	public ConsultaIndicadores() {
		
	}
	public ConsultaIndicadores(int id, String nome, String grupo, String area, String dpto, String setor,
			String undMedida, int freqMonitoramento, int fonteDados, int polaridade, String respPublic, String obs,
			String formula, int situacao) {
		
		this.id = id;
		this.nome = nome;
		this.grupo = grupo;
		this.area = area;
		this.dpto = dpto;
		this.setor = setor;
		this.undMedida = undMedida;
		this.freqMonitoramento = freqMonitoramento;
		this.fonteDados = fonteDados;
		this.polaridade = polaridade;
		this.respPublic = respPublic;
		this.obs = obs;
		this.formula = formula;
		this.situacao = situacao;
	}	

}
