package br.com.live.producao.model;

import java.util.List;

public class PainelListaPrioridades {
	public List<PainelListaPrioridadesCarteiraPedidos> carteiraPedidos; 
	public List<PainelListaPrioridadesAnaliseCarteira> analiseCarteira; 
	public List<PainelListaPrioridadesOrdensEmProducao> ordensEmProducao;
	public List<PainelListaPrioridadesEmProducaoPorEstagio> ordensPorEstagio;
	
	public PainelListaPrioridades(List<PainelListaPrioridadesCarteiraPedidos> carteiraPedidos, List<PainelListaPrioridadesAnaliseCarteira> analiseCarteira, List<PainelListaPrioridadesOrdensEmProducao> ordensEmProducao, List<PainelListaPrioridadesEmProducaoPorEstagio> ordensPorEstagio) {
		this.carteiraPedidos = carteiraPedidos;  
		this.analiseCarteira = analiseCarteira; 
		this.ordensEmProducao = ordensEmProducao;
		this.ordensPorEstagio = ordensPorEstagio;
	}
}
