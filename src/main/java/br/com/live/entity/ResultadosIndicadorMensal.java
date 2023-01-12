package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_ind_060")
public class ResultadosIndicadorMensal {
	
	@Id
	private String id;
	
	@Column(name = "id_indicador")
	public int idIndicador;
	
	private int ano;
	private String codigo;
	private String descricao;
	private float janeiro;
	private float fevereiro;
	private float marco;
	private float abril;
	private float maio;
	private float junho;
	private float julho;
	private float agosto;
	private float setembro;
	private float outubro;
	private float novembro;
	private float dezembro;
	
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
	
	public ResultadosIndicadorMensal() {
		
	}
	public ResultadosIndicadorMensal(String id, int idIndicador, int ano, String codigo, String descricao, float janeiro, float fevereiro,
			float marco, float abril, float maio, float junho, float julho, float agosto, float setembro, float outubro,
			float novembro, float dezembro) {
		this.id = id;
		this.idIndicador = idIndicador;
		this.ano = ano;
		this.codigo = codigo;
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
