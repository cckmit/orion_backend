package br.com.live.model;

import java.util.List;

import br.com.live.entity.CapacidadeCotasVendasCapa;

public class CapacidadeCotasVendasCapaItens {

	private CapacidadeCotasVendasCapa capa;
	private List<CapacidadeCotasVendasDadosItem> itens;

	public CapacidadeCotasVendasCapaItens(CapacidadeCotasVendasCapa capa, List<CapacidadeCotasVendasDadosItem> itens) {
		this.capa = capa;
		this.itens = itens;
	}

	public CapacidadeCotasVendasCapa getCapa() {
		return capa;
	}

	public void setCapa(CapacidadeCotasVendasCapa capa) {
		this.capa = capa;
	}

	public List<CapacidadeCotasVendasDadosItem> getItens() {
		return itens;
	}

	public void setItens(List<CapacidadeCotasVendasDadosItem> itens) {
		this.itens = itens;
	}
}
