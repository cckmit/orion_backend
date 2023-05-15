package br.com.live.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_fin_060")
public class FechamentoDevolucaoMostruario {
	
	@Id
	public int id;
	public int representante;
	
    public String estacao;
    public String nivel;
    public String grupo;
    public String subgrupo;
    public String item;
    
    @Column(name = "qtde_enviada")
	public float qtdeEnviada;
    
    @Column(name = "qtde_devolvida")
	public float qtdeDevolvida;
    
    public int diferenca;
    public float valor;
    
    @Column(name = "desc_60_porcento")
	public float desc60Porcento;
    
    @Column(name = "bonus_30_porcento")
	public float bonus30Porcento;
    
    @Column(name = "bonus_100_porcento")
	public float bonus100Porcento;
       
    @Column(name = "valor_cobrado")
	public float valorCobrado;
    
    @Column(name = "tab_col")
	public float tabCol;
    
    @Column(name = "tab_mes")
	public float tabMes;
    
    @Column(name = "tab_seq")
	public float tabSeq;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRepresentante() {
		return representante;
	}

	public void setRepresentante(int representante) {
		this.representante = representante;
	}

	public String getEstacao() {
		return estacao;
	}

	public void setEstacao(String estacao) {
		this.estacao = estacao;
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

	public String getSubgrupo() {
		return subgrupo;
	}

	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public float getQtdeEnviada() {
		return qtdeEnviada;
	}

	public void setQtdeEnviada(float qtdeEnviada) {
		this.qtdeEnviada = qtdeEnviada;
	}

	public float getQtdeDevolvida() {
		return qtdeDevolvida;
	}

	public void setQtdeDevolvida(float qtdeDevolvida) {
		this.qtdeDevolvida = qtdeDevolvida;
	}

	public int getDiferenca() {
		return diferenca;
	}

	public void setDiferenca(int diferenca) {
		this.diferenca = diferenca;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}
	
	public float getDesc60Porcento() {
		return desc60Porcento;
	}

	public void setDesc60Porcento(float desc60Porcento) {
		this.desc60Porcento = desc60Porcento;
	}

	public float getBonus30Porcento() {
		return bonus30Porcento;
	}

	public void setBonus30Porcento(float bonus30Porcento) {
		this.bonus30Porcento = bonus30Porcento;
	}

	public float getBonus100Porcento() {
		return bonus100Porcento;
	}

	public void setBonus100Porcento(float bonus100Porcento) {
		this.bonus100Porcento = bonus100Porcento;
	}

	public float getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(float valorCobrado) {
		this.valorCobrado = valorCobrado;
	}

	public float getTabCol() {
		return tabCol;
	}

	public void setTabCol(float tabCol) {
		this.tabCol = tabCol;
	}

	public float getTabMes() {
		return tabMes;
	}

	public void setTabMes(float tabMes) {
		this.tabMes = tabMes;
	}

	public float getTabSeq() {
		return tabSeq;
	}

	public void setTabSeq(float tabSeq) {
		this.tabSeq = tabSeq;
	}
    
    public FechamentoDevolucaoMostruario() {
    	
    }

	public FechamentoDevolucaoMostruario(int id, int representante, String estacao, String nivel, String grupo,
			String subgrupo, String item, float qtdeEnviada, float qtdeDevolvida, int diferenca, float valor,
			float desc60Porcento, float bonus30Porcento, float bonus100Porcento, float valorCobrado, float tabCol, float tabMes,
			float tabSeq) {
		
		this.id = id;
		this.representante = representante;
		this.estacao = estacao;
		this.nivel = nivel;
		this.grupo = grupo;
		this.subgrupo = subgrupo;
		this.item = item;
		this.qtdeEnviada = qtdeEnviada;
		this.qtdeDevolvida = qtdeDevolvida;
		this.diferenca = diferenca;
		this.valor = valor;
		this.desc60Porcento = desc60Porcento;
		this.bonus30Porcento = bonus30Porcento;
		this.bonus100Porcento = bonus100Porcento;
		this.valorCobrado = valorCobrado;
		this.tabCol = tabCol;
		this.tabMes = tabMes;
		this.tabSeq = tabSeq;
	}

}
