package br.com.live.comercial.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.comercial.body.BodyOcupacaoCarteira;
import br.com.live.comercial.custom.MetasDoOrcamentoCustom;
import br.com.live.comercial.custom.OcupacaoCarteiraCustom;
import br.com.live.comercial.custom.PedidoVendaCustom;
import br.com.live.comercial.model.MetaOrcamentoPorMesAno;
import br.com.live.comercial.model.PedidoVenda;
import br.com.live.comercial.model.ResumoOcupacaoCarteiraPorCanalVenda;
import br.com.live.comercial.model.ResumoOcupacaoCarteiraPorModalidade;
import br.com.live.comercial.model.ResumoOcupacaoCarteiraPorPedido;
import br.com.live.producao.custom.CapacidadeProducaoCustom;
import br.com.live.producao.model.CapacidadeEmMinutosMes;

@Service
@Transactional
public class OcupacaoCarteiraService {

	private final OcupacaoCarteiraCustom ocupacaoCarteiraCustom;
	private final MetasDoOrcamentoCustom metasDoOrcamentoCustom;
	private final CapacidadeProducaoCustom capacidadeProducaoCustom;
	private final PedidoVendaCustom pedidoVendaCustom; 
	
	public OcupacaoCarteiraService(OcupacaoCarteiraCustom ocupacaoCarteiraCustom, MetasDoOrcamentoCustom metasDoOrcamentoCustom, CapacidadeProducaoCustom capacidadeProducaoCustom, PedidoVendaCustom pedidoVendaCustom) {
		this.ocupacaoCarteiraCustom = ocupacaoCarteiraCustom;
		this.metasDoOrcamentoCustom = metasDoOrcamentoCustom;
		this.capacidadeProducaoCustom = capacidadeProducaoCustom;
		this.pedidoVendaCustom = pedidoVendaCustom;
	}
	
	public BodyOcupacaoCarteira consultar(String valorResumir, int mes, int ano, String tipoOrcamento, boolean pedidosDisponibilidade, boolean pedidosProgramados, boolean pedidosProntaEntrega) {				                                                																				
		// consulta os dados separando os valores de varejo e atacado.
		Map<String, Object> dadosVarejo = consultarOcupacaoCarteiraPorCanal(valorResumir, mes, ano, tipoOrcamento, pedidosDisponibilidade, pedidosProgramados, pedidosProntaEntrega, OcupacaoCarteiraCustom.MODALIDADE_VAREJO); 
		Map<String, Object> dadosAtacado = consultarOcupacaoCarteiraPorCanal(valorResumir, mes, ano, tipoOrcamento, pedidosDisponibilidade, pedidosProgramados, pedidosProntaEntrega, OcupacaoCarteiraCustom.MODALIDADE_ATACADO); 				
		
		// extrai os dados consultados de Varejo em listas de canais e pedidos 
		List<ResumoOcupacaoCarteiraPorCanalVenda> resumoCarteiraPorCanaisVarejo = (List<ResumoOcupacaoCarteiraPorCanalVenda>) dadosVarejo.get("PorCanais");					
		List<ResumoOcupacaoCarteiraPorPedido> resumoCarteiraPorPedidosVarejo = (List<ResumoOcupacaoCarteiraPorPedido>) dadosVarejo.get("PorPedidos");
		List<ResumoOcupacaoCarteiraPorPedido> resumoCarteiraPorPedidosConfirmarVarejo = (List<ResumoOcupacaoCarteiraPorPedido>) dadosVarejo.get("PorPedidosConfirmar");								
		
		// extrai os dados consultados de Atacado em listas de canais e pedidos
		List<ResumoOcupacaoCarteiraPorCanalVenda> resumoCarteiraPorCanaisAtacado = (List<ResumoOcupacaoCarteiraPorCanalVenda>) dadosAtacado.get("PorCanais");
		List<ResumoOcupacaoCarteiraPorPedido> resumoCarteiraPorPedidosAtacado = (List<ResumoOcupacaoCarteiraPorPedido>) dadosAtacado.get("PorPedidos");
		List<ResumoOcupacaoCarteiraPorPedido> resumoCarteiraPorPedidosConfirmarAtacado = (List<ResumoOcupacaoCarteiraPorPedido>) dadosAtacado.get("PorPedidosConfirmar");		

		// agrupa os valores para listar as informações no frontend
		List<PedidoVenda> resumoCarteiraPorPedidos = agruparListaDePedidos(resumoCarteiraPorPedidosAtacado, resumoCarteiraPorPedidosVarejo, resumoCarteiraPorPedidosConfirmarAtacado, resumoCarteiraPorPedidosConfirmarVarejo); 			
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorVarejo = agruparPorModalidade(OcupacaoCarteiraCustom.MODALIDADE_VAREJO, resumoCarteiraPorCanaisVarejo);
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorAtacado = agruparPorModalidade(OcupacaoCarteiraCustom.MODALIDADE_ATACADO, resumoCarteiraPorCanaisAtacado);	
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraTotal = somar(resumoOcupacaoCarteiraPorVarejo, resumoOcupacaoCarteiraPorAtacado, mes, ano, valorResumir);
		return new BodyOcupacaoCarteira(resumoCarteiraPorCanaisVarejo, resumoCarteiraPorCanaisAtacado, resumoOcupacaoCarteiraPorVarejo, resumoOcupacaoCarteiraPorAtacado, resumoOcupacaoCarteiraTotal, resumoCarteiraPorPedidos);
	}
	
	private void agruparLista(List<PedidoVenda> listaAgrupar, List<ResumoOcupacaoCarteiraPorPedido> listaPedidos) {
		if (listaPedidos != null) {
			for (ResumoOcupacaoCarteiraPorPedido resumoPorPedido : listaPedidos) {				
				// Aqui faz uma troca da classe ResumoOcupacaoCarteiraPorPedido pela PedidoVenda (pois essa tem os campos necessários para mostrar em tela, tais como cliente, data embarque, etc).  
				PedidoVenda pedidoVenda = pedidoVendaCustom.findPedidoVenda(resumoPorPedido.getPedidoVenda());
				// Seta o valor do pedido, conforme valor objeto por esse processo (que pode ser valor, quantidade ou minutos).
				
				pedidoVenda.setValorTotal(resumoPorPedido.getValorTotal());
				listaAgrupar.add(pedidoVenda);
			}
		}			
	}
	
	private List<PedidoVenda> agruparListaDePedidos(List<ResumoOcupacaoCarteiraPorPedido> listPedidosAtacado, List<ResumoOcupacaoCarteiraPorPedido> listPedidosVarejo, List<ResumoOcupacaoCarteiraPorPedido> listPedidosConfirmarAtacado, List<ResumoOcupacaoCarteiraPorPedido> listPedidosConfirmarVarejo) {
		List<PedidoVenda> lista = new ArrayList<PedidoVenda>();
		agruparLista(lista, listPedidosAtacado);
		agruparLista(lista, listPedidosVarejo);
		agruparLista(lista, listPedidosConfirmarAtacado);
		agruparLista(lista, listPedidosConfirmarVarejo);				
		return lista;		
	}	
	
	private ResumoOcupacaoCarteiraPorModalidade agruparPorModalidade(String tipoModalidade, List<ResumoOcupacaoCarteiraPorCanalVenda> listCanais) {
		double valorOrcado = 0; 
		double valorReal = 0; 
		double valorConfirmar = 0; 
		for (ResumoOcupacaoCarteiraPorCanalVenda canal : listCanais) {
			valorOrcado += canal.getValorOrcado(); 
			valorReal += canal.getValorReal(); 
			valorConfirmar += canal.getValorConfirmar(); 
		}		
		return new ResumoOcupacaoCarteiraPorModalidade(tipoModalidade, valorOrcado, valorReal, valorConfirmar);
	}
	
	private ResumoOcupacaoCarteiraPorModalidade somar(ResumoOcupacaoCarteiraPorModalidade varejo, ResumoOcupacaoCarteiraPorModalidade atacado, int mes, int ano, String tipoOcupacao) {
		double valorOrcado = 0;		
		// quando a ocupacao for em minutos, deve ser considerado o tempo total do mes, pois não existe rateio por canal.
		if (tipoOcupacao.equalsIgnoreCase(OcupacaoCarteiraCustom.OCUPACAO_EM_MINUTOS)) {
			CapacidadeEmMinutosMes capacidadeEmMinutos = capacidadeProducaoCustom.findCapacidadeEmMinutosByMesAno(mes, ano);
			valorOrcado = capacidadeEmMinutos.getQtdeMinutos();
		}
		else valorOrcado = varejo.getValorOrcado() + atacado.getValorOrcado();			
		double valorReal = varejo.getValorReal() + atacado.getValorReal(); 
		double valorConfirmar = varejo.getValorConfirmar() + atacado.getValorConfirmar(); 
		return new ResumoOcupacaoCarteiraPorModalidade("AMBOS", valorOrcado, valorReal, valorConfirmar);
	}
	
	private List<ResumoOcupacaoCarteiraPorCanalVenda> agruparDadosPedidosPorCanal(List<ResumoOcupacaoCarteiraPorPedido> listaPedidos) {				 		
		Map<String, ResumoOcupacaoCarteiraPorCanalVenda> mapCanais = new HashMap<String, ResumoOcupacaoCarteiraPorCanalVenda>();
		List<ResumoOcupacaoCarteiraPorCanalVenda> listaCanais = new ArrayList<ResumoOcupacaoCarteiraPorCanalVenda>();
		
		for (ResumoOcupacaoCarteiraPorPedido pedido : listaPedidos) {			
			if (mapCanais.containsKey(pedido.getCanal())) {
				ResumoOcupacaoCarteiraPorCanalVenda canalAtual = mapCanais.get(pedido.getCanal());
				canalAtual.setValorOrcado(canalAtual.getValorOrcado() + pedido.getValorOrcado());
				canalAtual.setValorReal(canalAtual.getValorReal() + pedido.getValorReal());
				canalAtual.setValorConfirmar(canalAtual.getValorConfirmar() + pedido.getValorConfirmar());				
				mapCanais.put(pedido.getCanal(), canalAtual);
			} else {
				ResumoOcupacaoCarteiraPorCanalVenda canalAtual = new ResumoOcupacaoCarteiraPorCanalVenda(pedido.getCanal(), pedido.getValorOrcado(), pedido.getValorReal(), pedido.getValorConfirmar()); 
				mapCanais.put(pedido.getCanal(), canalAtual);
			}
		}		
		for (String canal : mapCanais.keySet()) {
			ResumoOcupacaoCarteiraPorCanalVenda resumoPorCanal = mapCanais.get(canal);
			listaCanais.add(resumoPorCanal);
        }		
		return listaCanais;
	}
		
	private Map<String, Object> consultarOcupacaoEmValor(int mes, int ano, String tipoPedido, int tipoClassificao, int tipoMeta, String tipoModalidade) {
		List<ResumoOcupacaoCarteiraPorPedido> listOcupacaoEmValor = null;
		List<ResumoOcupacaoCarteiraPorPedido> listOcupacaoConfirmarEmValor = null;
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoPorCanalEmValor = null;
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoPorCanalConfirmarEmValor = null;	
		// Carrega os valores apenas para atacado.
		if (tipoModalidade.equalsIgnoreCase(OcupacaoCarteiraCustom.MODALIDADE_ATACADO) ) {
			listOcupacaoEmValor = ocupacaoCarteiraCustom.consultarCarteiraPorValor(mes, ano, tipoPedido, tipoClassificao, tipoMeta);
			listOcupacaoConfirmarEmValor = ocupacaoCarteiraCustom.consultarCarteiraConfirmarPorValor(mes, ano, tipoPedido, tipoClassificao, tipoMeta);				
			listOcupacaoPorCanalEmValor = agruparDadosPedidosPorCanal(listOcupacaoEmValor);
			listOcupacaoPorCanalConfirmarEmValor = agruparDadosPedidosPorCanal(listOcupacaoConfirmarEmValor);	
		}				 	
		List<ResumoOcupacaoCarteiraPorCanalVenda> dadosPorCanal = atualizarDadosOrcadoVersusRealizado(mes, ano, tipoMeta, tipoModalidade, listOcupacaoPorCanalEmValor, listOcupacaoPorCanalConfirmarEmValor, false);
		Map<String, Object> retorno = new HashMap<String, Object>();
		retorno.put("PorCanais", dadosPorCanal);
		retorno.put("PorPedidos", listOcupacaoEmValor);
		retorno.put("PorPedidosConfirmar", listOcupacaoConfirmarEmValor);
		return retorno; 
	}	
	
	private Map<String, Object> consultarOcupacaoEmQuantidade(int mes, int ano, String tipoPedido, int tipoClassificao, int tipoMeta, String tipoModalidade) {
		List<ResumoOcupacaoCarteiraPorPedido> listOcupacaoEmQtde = ocupacaoCarteiraCustom.consultarCarteiraPorQuantidade(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		List<ResumoOcupacaoCarteiraPorPedido> listOcupacaoConfirmarEmQtde = ocupacaoCarteiraCustom.consultarCarteiraConfirmarPorQuantidade(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);				
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoPorCanalEmQtde = agruparDadosPedidosPorCanal(listOcupacaoEmQtde);
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoPorCanalConfirmarEmQtde = agruparDadosPedidosPorCanal(listOcupacaoConfirmarEmQtde);	
		List<ResumoOcupacaoCarteiraPorCanalVenda> dadosPorCanal = atualizarDadosOrcadoVersusRealizado(mes, ano, tipoMeta, tipoModalidade, listOcupacaoPorCanalEmQtde, listOcupacaoPorCanalConfirmarEmQtde, false);
		Map<String, Object> retorno = new HashMap<String, Object>();
		retorno.put("PorCanais", dadosPorCanal);
		retorno.put("PorPedidos", listOcupacaoEmQtde);
		retorno.put("PorPedidosConfirmar", listOcupacaoConfirmarEmQtde);
		return retorno; 
	}	

	private Map<String, Object> consultarOcupacaoEmMinutos(int mes, int ano, String tipoPedido, int tipoClassificao, int tipoMeta, String tipoModalidade) {		
		List<ResumoOcupacaoCarteiraPorPedido> listOcupacao = ocupacaoCarteiraCustom.consultarCarteiraPorMinutos(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		List<ResumoOcupacaoCarteiraPorPedido> listOcupacaoConfirmar = ocupacaoCarteiraCustom.consultarCarteiraConfirmarPorMinutos(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);						
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoPorCanal= agruparDadosPedidosPorCanal(listOcupacao);
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoPorCanalConfirmar = agruparDadosPedidosPorCanal(listOcupacaoConfirmar);	
		List<ResumoOcupacaoCarteiraPorCanalVenda> dadosPorCanal = atualizarDadosOrcadoVersusRealizado(mes, ano, tipoMeta, tipoModalidade, listOcupacaoPorCanal, listOcupacaoPorCanalConfirmar, true);
		Map<String, Object> retorno = new HashMap<String, Object>();
		retorno.put("PorCanais", dadosPorCanal);
		retorno.put("PorPedidos", listOcupacao);
		retorno.put("PorPedidosConfirmar", listOcupacaoConfirmar);
		return retorno; 

	}	
	
	private List<ResumoOcupacaoCarteiraPorCanalVenda> atualizarDadosOrcadoVersusRealizado(int mes, int ano, int tipoMeta, String tipoModalidade,  List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacao, List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoConfirmar, boolean isMinutos) {
		Map<String, ResumoOcupacaoCarteiraPorCanalVenda> mapOcupacao = new HashMap<String, ResumoOcupacaoCarteiraPorCanalVenda>();
	    List<ResumoOcupacaoCarteiraPorCanalVenda> listResumoPorCanal = new ArrayList<ResumoOcupacaoCarteiraPorCanalVenda>();
		List<MetaOrcamentoPorMesAno> metasCadastradas = metasDoOrcamentoCustom.findMetasOrcamentoByTipoMetaMesAno(tipoMeta, mes, ano, tipoModalidade);		
		
	    for (MetaOrcamentoPorMesAno orcado : metasCadastradas) {
	    	ResumoOcupacaoCarteiraPorCanalVenda resumo = new ResumoOcupacaoCarteiraPorCanalVenda();
	    	resumo.setCanal(orcado.getCanal());
	    	if (isMinutos) resumo.setValorOrcado(0);
	    	else resumo.setValorOrcado(orcado.getValor());
	    	mapOcupacao.put(resumo.getCanal(), resumo);	    	
	    }
		if (listOcupacao != null) {
			for (ResumoOcupacaoCarteiraPorCanalVenda ocupacao : listOcupacao) {
				ResumoOcupacaoCarteiraPorCanalVenda resumo = mapOcupacao.get(ocupacao.getCanal()); 	
				resumo.setValorReal(ocupacao.getValorReal());
			}		
 		}
		if (listOcupacaoConfirmar != null) {
			for (ResumoOcupacaoCarteiraPorCanalVenda ocupacaoConfirmar : listOcupacaoConfirmar) {
				ResumoOcupacaoCarteiraPorCanalVenda resumo = mapOcupacao.get(ocupacaoConfirmar.getCanal()); 	
				resumo.setValorConfirmar(ocupacaoConfirmar.getValorConfirmar());
			}		
		}
		for (String canal : mapOcupacao.keySet()) {
			listResumoPorCanal.add(mapOcupacao.get(canal));
		}		
		return listResumoPorCanal;
	}
	
	private Map<String, Object> consultarOcupacaoCarteiraPorCanal(String tipoOcupacao, int mes, int ano, String tipoOrcamento, boolean pedidosDisponibilidade, boolean pedidosProgramados, boolean pedidosProntaEntrega, String tipoModalidade) {		
		String tipoPedido = "";
		int tipoClassificao = 0;
		
		if (pedidosProgramados) tipoPedido = "0";   
		if (pedidosProntaEntrega) tipoPedido = tipoPedido.isEmpty() ? "1" : "0,1";
		// se estiver marcado apenas pedidos de disponibilidade deve considerar pedidos programados e pronta entrega.
		if (tipoPedido.isEmpty()) tipoPedido = pedidosDisponibilidade ? "0,1" : "9"; 		
		if ((pedidosDisponibilidade)&(!pedidosProgramados)&(!pedidosProntaEntrega)) tipoClassificao = OcupacaoCarteiraCustom.CLASSIFICACAO_DISPONIBILIDADE;
		
		Map<String, Object> listDados = null;		
		
		if (tipoOcupacao.equalsIgnoreCase(OcupacaoCarteiraCustom.OCUPACAO_EM_VALORES)) {
			int tipoMeta = tipoOrcamento.equalsIgnoreCase(OcupacaoCarteiraCustom.META_ORCADA) ? MetasDoOrcamentoCustom.METAS_FATURAMENTO : MetasDoOrcamentoCustom.METAS_FATURAMENTO_REALINHADO;
			listDados = consultarOcupacaoEmValor(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		} else if (tipoOcupacao.equalsIgnoreCase(OcupacaoCarteiraCustom.OCUPACAO_EM_PECAS)) {			
			int tipoMeta = tipoOrcamento.equalsIgnoreCase(OcupacaoCarteiraCustom.META_ORCADA) ? MetasDoOrcamentoCustom.METAS_FAT_EM_PECAS : MetasDoOrcamentoCustom.METAS_FAT_EM_PECAS_REALINHADO;
			listDados = consultarOcupacaoEmQuantidade(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		} else if (tipoOcupacao.equalsIgnoreCase(OcupacaoCarteiraCustom.OCUPACAO_EM_MINUTOS)) {
			// não existe meta em minutos por canal, então nesse caso utilizamos o tipo de meta de peças.
			int tipoMeta = tipoOrcamento.equalsIgnoreCase(OcupacaoCarteiraCustom.META_ORCADA) ? MetasDoOrcamentoCustom.METAS_FAT_EM_PECAS : MetasDoOrcamentoCustom.METAS_FAT_EM_PECAS_REALINHADO;
			listDados = consultarOcupacaoEmMinutos(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		}
		
	    return listDados; 
	}		
}
