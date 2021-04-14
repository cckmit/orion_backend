package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "orion_015")
@SequenceGenerator(name = "ID_ORION_015", sequenceName = "ID_ORION_015", initialValue = 1, allocationSize = 1)
public class ProdutoPlanoMestre {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ORION_015")
	public long id;

	@Column(name = "num_plano_mestre")
	public long idPlanoMestre;

	public String nivel;
	public String grupo;
	public String sub;
	public String item;

	@Column(name = "qtde_previsao")
	public int qtdePrevisao;

	@Column(name = "qtde_estoque")
	public int qtdeEstoque;

	@Column(name = "qtde_dem_plano1")
	public int qtdeDemPlano1;

	@Column(name = "qtde_dem_plano2")
	public int qtdeDemPlano2;

	@Column(name = "qtde_dem_plano3")
	public int qtdeDemPlano3;

	@Column(name = "qtde_dem_plano4")
	public int qtdeDemPlano4;

	@Column(name = "qtde_dem_plano5")
	public int qtdeDemPlano5;

	@Column(name = "qtde_dem_plano6")
	public int qtdeDemPlano6;

	@Column(name = "qtde_dem_plano7")
	public int qtdeDemPlano7;

	@Column(name = "qtde_dem_plano8")
	public int qtdeDemPlano8;

	@Column(name = "qtde_proc_plano1")
	public int qtdeProcPlano1;

	@Column(name = "qtde_proc_plano2")
	public int qtdeProcPlano2;

	@Column(name = "qtde_proc_plano3")
	public int qtdeProcPlano3;

	@Column(name = "qtde_proc_plano4")
	public int qtdeProcPlano4;

	@Column(name = "qtde_proc_plano5")
	public int qtdeProcPlano5;

	@Column(name = "qtde_proc_plano6")
	public int qtdeProcPlano6;

	@Column(name = "qtde_proc_plano7")
	public int qtdeProcPlano7;

	@Column(name = "qtde_proc_plano8")
	public int qtdeProcPlano8;

	@Column(name = "qtde_saldo_plano1")
	public int qtdeSaldoPlano1;

	@Column(name = "qtde_saldo_plano2")
	public int qtdeSaldoPlano2;

	@Column(name = "qtde_saldo_plano3")
	public int qtdeSaldoPlano3;

	@Column(name = "qtde_saldo_plano4")
	public int qtdeSaldoPlano4;

	@Column(name = "qtde_saldo_plano5")
	public int qtdeSaldoPlano5;

	@Column(name = "qtde_saldo_plano6")
	public int qtdeSaldoPlano6;

	@Column(name = "qtde_saldo_plano7")
	public int qtdeSaldoPlano7;

	@Column(name = "qtde_saldo_plano8")
	public int qtdeSaldoPlano8;

	@Column(name = "qtde_dem_acumulado")
	public int qtdeDemAcumulado;

	@Column(name = "qtde_proc_acumulado")
	public int qtdeProcAcumulado;

	@Column(name = "qtde_saldo_acumulado")
	public int qtdeSaldoAcumulado;
	
	@Column(name = "qtde_sugestao")
	public int qtdeSugestao;
	
	@Column(name = "qtde_equalizado_sugestao")
	public int qtdeEqualizadoSugestao;
	
	@Column(name = "qtde_dif_sugestao")
	public int qtdeDiferencaSugestao;
	
	@Column(name = "qtde_programada")
	public int qtdeProgramada;		
	
	public ProdutoPlanoMestre() {
		this.id = 0;
		this.idPlanoMestre = 0;

		this.nivel = "";
		this.grupo = "";
		this.sub = "";
		this.item = "";

		this.qtdePrevisao = 0;
		this.qtdeEstoque = 0;

		this.qtdeDemPlano1 = 0;
		this.qtdeDemPlano2 = 0;
		this.qtdeDemPlano3 = 0;
		this.qtdeDemPlano4 = 0;
		this.qtdeDemPlano5 = 0;
		this.qtdeDemPlano6 = 0;
		this.qtdeDemPlano7 = 0;
		this.qtdeDemPlano8 = 0;

		this.qtdeProcPlano1 = 0;
		this.qtdeProcPlano2 = 0;
		this.qtdeProcPlano3 = 0;
		this.qtdeProcPlano4 = 0;
		this.qtdeProcPlano5 = 0;
		this.qtdeProcPlano6 = 0;
		this.qtdeProcPlano7 = 0;
		this.qtdeProcPlano8 = 0;

		this.qtdeSaldoPlano1 = 0;
		this.qtdeSaldoPlano2 = 0;
		this.qtdeSaldoPlano3 = 0;
		this.qtdeSaldoPlano4 = 0;
		this.qtdeSaldoPlano5 = 0;
		this.qtdeSaldoPlano6 = 0;
		this.qtdeSaldoPlano7 = 0;
		this.qtdeSaldoPlano8 = 0;

		this.qtdeDemAcumulado = 0;
		this.qtdeProcAcumulado = 0;
		this.qtdeSaldoAcumulado = 0;
		
		this.qtdeSugestao = 0;				
		this.qtdeEqualizadoSugestao = 0;				
		this.qtdeDiferencaSugestao = 0;				
		this.qtdeProgramada = 0;		
	}

	public ProdutoPlanoMestre(String nivel, String grupo, String sub, String item) {
		this();

		this.nivel = nivel;
		this.grupo = grupo;
		this.sub = sub;
		this.item = item;
	}

	public String getCodProduto() {
		return this.nivel + "." + this.grupo + "." + this.sub + "." + this.item;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdPlanoMestre() {
		return idPlanoMestre;
	}

	public void setIdPlanoMestre(long idPlanoMestre) {
		this.idPlanoMestre = idPlanoMestre;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getQtdePrevisao() {
		return qtdePrevisao;
	}

	public void setQtdePrevisao(int qtdePrevisao) {
		this.qtdePrevisao = qtdePrevisao;
	}

	public int getQtdeEstoque() {
		return qtdeEstoque;
	}

	public void setQtdeEstoque(int qtdeEstoque) {
		this.qtdeEstoque = qtdeEstoque;
	}

	public int getQtdeDemPlano1() {
		return qtdeDemPlano1;
	}

	public void setQtdeDemPlano1(int qtdeDemPlano1) {
		this.qtdeDemPlano1 = qtdeDemPlano1;
	}

	public int getQtdeDemPlano2() {
		return qtdeDemPlano2;
	}

	public void setQtdeDemPlano2(int qtdeDemPlano2) {
		this.qtdeDemPlano2 = qtdeDemPlano2;
	}

	public int getQtdeDemPlano3() {
		return qtdeDemPlano3;
	}

	public void setQtdeDemPlano3(int qtdeDemPlano3) {
		this.qtdeDemPlano3 = qtdeDemPlano3;
	}

	public int getQtdeDemPlano4() {
		return qtdeDemPlano4;
	}

	public void setQtdeDemPlano4(int qtdeDemPlano4) {
		this.qtdeDemPlano4 = qtdeDemPlano4;
	}

	public int getQtdeDemPlano5() {
		return qtdeDemPlano5;
	}

	public void setQtdeDemPlano5(int qtdeDemPlano5) {
		this.qtdeDemPlano5 = qtdeDemPlano5;
	}

	public int getQtdeDemPlano6() {
		return qtdeDemPlano6;
	}

	public void setQtdeDemPlano6(int qtdeDemPlano6) {
		this.qtdeDemPlano6 = qtdeDemPlano6;
	}

	public int getQtdeDemPlano7() {
		return qtdeDemPlano7;
	}

	public void setQtdeDemPlano7(int qtdeDemPlano7) {
		this.qtdeDemPlano7 = qtdeDemPlano7;
	}

	public int getQtdeDemPlano8() {
		return qtdeDemPlano8;
	}

	public void setQtdeDemPlano8(int qtdeDemPlano8) {
		this.qtdeDemPlano8 = qtdeDemPlano8;
	}

	public int getQtdeProcPlano1() {
		return qtdeProcPlano1;
	}

	public void setQtdeProcPlano1(int qtdeProcPlano1) {
		this.qtdeProcPlano1 = qtdeProcPlano1;
	}

	public int getQtdeProcPlano2() {
		return qtdeProcPlano2;
	}

	public void setQtdeProcPlano2(int qtdeProcPlano2) {
		this.qtdeProcPlano2 = qtdeProcPlano2;
	}

	public int getQtdeProcPlano3() {
		return qtdeProcPlano3;
	}

	public void setQtdeProcPlano3(int qtdeProcPlano3) {
		this.qtdeProcPlano3 = qtdeProcPlano3;
	}

	public int getQtdeProcPlano4() {
		return qtdeProcPlano4;
	}

	public void setQtdeProcPlano4(int qtdeProcPlano4) {
		this.qtdeProcPlano4 = qtdeProcPlano4;
	}

	public int getQtdeProcPlano5() {
		return qtdeProcPlano5;
	}

	public void setQtdeProcPlano5(int qtdeProcPlano5) {
		this.qtdeProcPlano5 = qtdeProcPlano5;
	}

	public int getQtdeProcPlano6() {
		return qtdeProcPlano6;
	}

	public void setQtdeProcPlano6(int qtdeProcPlano6) {
		this.qtdeProcPlano6 = qtdeProcPlano6;
	}

	public int getQtdeProcPlano7() {
		return qtdeProcPlano7;
	}

	public void setQtdeProcPlano7(int qtdeProcPlano7) {
		this.qtdeProcPlano7 = qtdeProcPlano7;
	}

	public int getQtdeProcPlano8() {
		return qtdeProcPlano8;
	}

	public void setQtdeProcPlano8(int qtdeProcPlano8) {
		this.qtdeProcPlano8 = qtdeProcPlano8;
	}

	public int getQtdeSaldoPlano1() {
		return qtdeSaldoPlano1;
	}

	public void setQtdeSaldoPlano1(int qtdeSaldoPlano1) {
		this.qtdeSaldoPlano1 = qtdeSaldoPlano1;
	}

	public int getQtdeSaldoPlano2() {
		return qtdeSaldoPlano2;
	}

	public void setQtdeSaldoPlano2(int qtdeSaldoPlano2) {
		this.qtdeSaldoPlano2 = qtdeSaldoPlano2;
	}

	public int getQtdeSaldoPlano3() {
		return qtdeSaldoPlano3;
	}

	public void setQtdeSaldoPlano3(int qtdeSaldoPlano3) {
		this.qtdeSaldoPlano3 = qtdeSaldoPlano3;
	}

	public int getQtdeSaldoPlano4() {
		return qtdeSaldoPlano4;
	}

	public void setQtdeSaldoPlano4(int qtdeSaldoPlano4) {
		this.qtdeSaldoPlano4 = qtdeSaldoPlano4;
	}

	public int getQtdeSaldoPlano5() {
		return qtdeSaldoPlano5;
	}

	public void setQtdeSaldoPlano5(int qtdeSaldoPlano5) {
		this.qtdeSaldoPlano5 = qtdeSaldoPlano5;
	}

	public int getQtdeSaldoPlano6() {
		return qtdeSaldoPlano6;
	}

	public void setQtdeSaldoPlano6(int qtdeSaldoPlano6) {
		this.qtdeSaldoPlano6 = qtdeSaldoPlano6;
	}

	public int getQtdeSaldoPlano7() {
		return qtdeSaldoPlano7;
	}

	public void setQtdeSaldoPlano7(int qtdeSaldoPlano7) {
		this.qtdeSaldoPlano7 = qtdeSaldoPlano7;
	}

	public int getQtdeSaldoPlano8() {
		return qtdeSaldoPlano8;
	}

	public void setQtdeSaldoPlano8(int qtdeSaldoPlano8) {
		this.qtdeSaldoPlano8 = qtdeSaldoPlano8;
	}

	public int getQtdeDemAcumulado() {
		return qtdeDemAcumulado;
	}

	public void setQtdeDemAcumulado(int qtdeDemAcumulado) {
		this.qtdeDemAcumulado = qtdeDemAcumulado;
	}

	public int getQtdeProcAcumulado() {
		return qtdeProcAcumulado;
	}

	public void setQtdeProcAcumulado(int qtdeProcAcumulado) {
		this.qtdeProcAcumulado = qtdeProcAcumulado;
	}

	public int getQtdeSaldoAcumulado() {
		return qtdeSaldoAcumulado;
	}

	public void setQtdeSaldoAcumulado(int qtdeSaldoAcumulado) {
		this.qtdeSaldoAcumulado = qtdeSaldoAcumulado;
	}

	public int getQtdeSugestao() {
		return qtdeSugestao;
	}

	public void setQtdeSugestao(int qtdeSugestao) {
		this.qtdeSugestao = qtdeSugestao;
	}

	public int getQtdeEqualizadoSugestao() {
		return qtdeEqualizadoSugestao;
	}

	public void setQtdeEqualizadoSugestao(int qtdeEqualizadoSugestao) {
		this.qtdeEqualizadoSugestao = qtdeEqualizadoSugestao;
	}

	public int getQtdeDiferencaSugestao() {
		return qtdeDiferencaSugestao;
	}

	public void setQtdeDiferencaSugestao(int qtdeDiferencaSugestao) {
		this.qtdeDiferencaSugestao = qtdeDiferencaSugestao;
	}

	public int getQtdeProgramada() {
		return qtdeProgramada;
	}

	public void setQtdeProgramada(int qtdeProgramada) {
		this.qtdeProgramada = qtdeProgramada;
	}
	
}