package br.com.live.comercial.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_com_300")
public class FaturamentoLiveClothing {
	
	@Id
	public int id;
	
	public String loja;
	public Date data;
	public int quantidade;
	public int tickets;
	public float conversao;
	
	@Column(name = "valor_dolar")
	public float valorDolar;
	
	@Column(name = "valor_real")
	public float valorReal;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoja() {
		return loja;
	}
	public void setLoja(String loja) {
		this.loja = loja;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getTickets() {
		return tickets;
	}
	public void setTickets(int tickets) {
		this.tickets = tickets;
	}
	public float getConversao() {
		return conversao;
	}
	public void setConversao(float conversao) {
		this.conversao = conversao;
	}
	public float getValorDolar() {
		return valorDolar;
	}
	public void setValorDolar(float valorDolar) {
		this.valorDolar = valorDolar;
	}
	public float getValorReal() {
		return valorReal;
	}
	public void setValorReal(float valorReal) {
		this.valorReal = valorReal;
	}
	
	public FaturamentoLiveClothing() {
		
	}
	public FaturamentoLiveClothing(int id, String loja, Date data, int quantidade, int tickets, float conversao,
			float valorDolar, float valorReal) {
		this.id = id;
		this.loja = loja;
		this.data = data;
		this.quantidade = quantidade;
		this.tickets = tickets;
		this.conversao = conversao;
		this.valorDolar = valorDolar;
		this.valorReal = valorReal;
	}
}
