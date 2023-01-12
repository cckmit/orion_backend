package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_ind_110")
public class Indicadores {
	
	@Id
	public long id;
	
	@Column(name = "nome_indicador")	
	public String nomeIndicador;
	
	@Column(name = "grupo_indicador")	
	public int grupoIndicador;
	
	public int area;
	public int departamento;
	public int setor;
    
	@Column(name = "gestor_avaliado")	
	public int gestorAvaliado;
	
	@Column(name = "unidade_medida")	
	public String unidadeMedida;
	
	@Column(name = "frequencia_monitoramento")	
	public String frequenciaMonitoramento;
    
	@Column(name = "fonte_dados")	
	public String fonteDados;
	
    public String polaridade;
    
    @Column(name = "responsavel_registro")	
	public int responsavelRegistro;
    
    @Column(name = "responsavel_publicacao")	
	public int responsavelPublicacao;
    
    public String observacao;
    
    @Column(name = "formula_calculo")	
	public String formulaCalculo;
    public String variaveis;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNomeIndicador() {
		return nomeIndicador;
	}
	public void setNomeIndicador(String nomeIndicador) {
		this.nomeIndicador = nomeIndicador;
	}
	public int getGrupoIndicador() {
		return grupoIndicador;
	}
	public void setGrupoIndicador(int grupoIndicador) {
		this.grupoIndicador = grupoIndicador;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getDepartamento() {
		return departamento;
	}
	public void setDepartamento(int departamento) {
		this.departamento = departamento;
	}
	public int getSetor() {
		return setor;
	}
	public void setSetor(int setor) {
		this.setor = setor;
	}
	public int getGestorAvaliado() {
		return gestorAvaliado;
	}
	public void setGestorAvaliado(int gestorAvaliado) {
		this.gestorAvaliado = gestorAvaliado;
	}
	public String getUnidadeMedida() {
		return unidadeMedida;
	}
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	public String getFrequenciaMonitoramento() {
		return frequenciaMonitoramento;
	}
	public void setFrequenciaMonitoramento(String frequenciaMonitoramento) {
		this.frequenciaMonitoramento = frequenciaMonitoramento;
	}
	public String getFonteDados() {
		return fonteDados;
	}
	public void setFonteDados(String fonteDados) {
		this.fonteDados = fonteDados;
	}
	public String getPolaridade() {
		return polaridade;
	}
	public void setPolaridade(String polaridade) {
		this.polaridade = polaridade;
	}
	public int getResponsavelRegistro() {
		return responsavelRegistro;
	}
	public void setResponsavelRegistro(int responsavelRegistro) {
		this.responsavelRegistro = responsavelRegistro;
	}	
	public int getResponsavelPublicacao() {
		return responsavelPublicacao;
	}
	public void setResponsavelPublicacao(int responsavelPublicacao) {
		this.responsavelPublicacao = responsavelPublicacao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getFormulaCalculo() {
		return formulaCalculo;
	}
	public void setFormulaCalculo(String formulaCalculo) {
		this.formulaCalculo = formulaCalculo;
	}    
    public String getVariaveis() {
		return variaveis;
	}
	public void setVariaveis(String variaveis) {
		this.variaveis = variaveis;
	}
	public Indicadores() {
    	
    }
	public Indicadores(long id, String nomeIndicador, int grupoIndicador, int area, int departamento, int setor,
			int gestorAvaliado, String unidadeMedida, String frequenciaMonitoramento, String fonteDados,
			String polaridade, int responsavelRegistro, int responsavelPublicacao, String observacao, String formulaCalculo, String variaveis) {
		this.id = id;
		this.nomeIndicador = nomeIndicador;
		this.grupoIndicador = grupoIndicador;
		this.area = area;
		this.departamento = departamento;
		this.setor = setor;
		this.gestorAvaliado = gestorAvaliado;
		this.unidadeMedida = unidadeMedida;
		this.frequenciaMonitoramento = frequenciaMonitoramento;
		this.fonteDados = fonteDados;
		this.polaridade = polaridade;
		this.responsavelRegistro = responsavelRegistro;
		this.responsavelPublicacao = responsavelPublicacao;
		this.observacao = observacao;
		this.formulaCalculo = formulaCalculo;
		this.variaveis = variaveis;
	}
}
