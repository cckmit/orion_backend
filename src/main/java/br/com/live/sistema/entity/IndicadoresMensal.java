package br.com.live.sistema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ind_010")
public class IndicadoresMensal {
	
	@Id
	public String id;
	
	@Column(name = "id_indicador")
	public int idIndicador;
	public String variaveis;
	public int ano;
	public String codigo;
	public String descricao;
	public float janeiro;
	public float fevereiro;
	public float marco;
	public float abril;
	public float maio;
	public float junho;
	public float julho;
	public float agosto;
	public float setembro;
	public float outubro;
	public float novembro;
	public float dezembro;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIdIndicador() {
		return idIndicador;
	}
	public void setIdIndicador(int idIndicador) {
		this.idIndicador = idIndicador;
	}
	public String getVariaveis() {
		return variaveis;
	}
	public void setVariaveis(String variaveis) {
		this.variaveis = variaveis;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getJaneiro() {
		return janeiro;
	}
	public void setJaneiro(float janeiro) {
		this.janeiro = janeiro;
	}
	public float getFevereiro() {
		return fevereiro;
	}
	public void setFevereiro(float fevereiro) {
		this.fevereiro = fevereiro;
	}
	public float getMarco() {
		return marco;
	}
	public void setMarco(float marco) {
		this.marco = marco;
	}
	public float getAbril() {
		return abril;
	}
	public void setAbril(float abril) {
		this.abril = abril;
	}
	public float getMaio() {
		return maio;
	}
	public void setMaio(float maio) {
		this.maio = maio;
	}
	public float getJunho() {
		return junho;
	}
	public void setJunho(float junho) {
		this.junho = junho;
	}
	public float getJulho() {
		return julho;
	}
	public void setJulho(float julho) {
		this.julho = julho;
	}
	public float getAgosto() {
		return agosto;
	}
	public void setAgosto(float agosto) {
		this.agosto = agosto;
	}
	public float getSetembro() {
		return setembro;
	}
	public void setSetembro(float setembro) {
		this.setembro = setembro;
	}
	public float getOutubro() {
		return outubro;
	}
	public void setOutubro(float outubro) {
		this.outubro = outubro;
	}
	public float getNovembro() {
		return novembro;
	}
	public void setNovembro(float novembro) {
		this.novembro = novembro;
	}
	public float getDezembro() {
		return dezembro;
	}
	public void setDezembro(float dezembro) {
		this.dezembro = dezembro;
	}
	
	public IndicadoresMensal() {
		
	}
	
	public IndicadoresMensal(int idIndicador, String variaveis, int ano, String codigo, String descricao, float janeiro, float fevereiro, float marco,
			float abril, float maio, float junho, float julho, float agosto, float setembro, float outubro,
			float novembro, float dezembro) {
		this.id = idIndicador + "-" + ano + "-" + codigo.toUpperCase();
		this.idIndicador = idIndicador;
		this.variaveis = variaveis;
		this.ano = ano;
		this.codigo = codigo.toUpperCase();
		this.descricao = descricao;
		this.janeiro = janeiro;
		this.fevereiro = fevereiro;
		this.marco = marco;
		this.abril = abril;
		this.maio = maio;
		this.junho = junho;
		this.julho = julho;
		this.agosto = agosto;
		this.setembro = setembro;
		this.outubro = outubro;
		this.novembro = novembro;
		this.dezembro = dezembro;
	}  
}
