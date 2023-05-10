package br.com.live.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.body.BodyOcupacaoCarteira;
import br.com.live.custom.MetasDoOrcamentoCustom;
import br.com.live.custom.OcupacaoCarteiraCustom;
import br.com.live.model.MetaOrcamentoPorMesAno;
import br.com.live.model.ResumoOcupacaoCarteiraPorCanalVenda;
import br.com.live.model.ResumoOcupacaoCarteiraPorModalidade;

@Service
@Transactional
public class OcupacaoCarteiraService {

	private final OcupacaoCarteiraCustom ocupacaoCarteiraCustom;
	private final MetasDoOrcamentoCustom metasDoOrcamentoCustom;
	
	public OcupacaoCarteiraService(OcupacaoCarteiraCustom ocupacaoCarteiraCustom, MetasDoOrcamentoCustom metasDoOrcamentoCustom) {
		this.ocupacaoCarteiraCustom = ocupacaoCarteiraCustom;
		this.metasDoOrcamentoCustom = metasDoOrcamentoCustom;
	}
	
	public BodyOcupacaoCarteira consultar(String valorResumir, int mes, int ano, String tipoOrcamento, boolean pedidosDisponibilidade, boolean pedidosProgramados, boolean pedidosProntaEntrega) {				                                                																		
		List<ResumoOcupacaoCarteiraPorCanalVenda> resumoCarteiraPorCanaisVarejo = consultarOcupacaoCarteiraPorCanal(valorResumir, mes, ano, tipoOrcamento, pedidosDisponibilidade, pedidosProgramados, pedidosProntaEntrega, OcupacaoCarteiraCustom.MODALIDADE_VAREJO);
		List<ResumoOcupacaoCarteiraPorCanalVenda> resumoCarteiraPorCanaisAtacado = consultarOcupacaoCarteiraPorCanal(valorResumir, mes, ano, tipoOrcamento, pedidosDisponibilidade, pedidosProgramados, pedidosProntaEntrega, OcupacaoCarteiraCustom.MODALIDADE_ATACADO);		
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorVarejo = agruparPorModalidade(OcupacaoCarteiraCustom.MODALIDADE_VAREJO, resumoCarteiraPorCanaisVarejo);
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraPorAtacado = agruparPorModalidade(OcupacaoCarteiraCustom.MODALIDADE_ATACADO, resumoCarteiraPorCanaisAtacado);	
		ResumoOcupacaoCarteiraPorModalidade resumoOcupacaoCarteiraTotal = somar(resumoOcupacaoCarteiraPorVarejo, resumoOcupacaoCarteiraPorAtacado);
		return new BodyOcupacaoCarteira(resumoCarteiraPorCanaisVarejo, resumoCarteiraPorCanaisAtacado, resumoOcupacaoCarteiraPorVarejo, resumoOcupacaoCarteiraPorAtacado, resumoOcupacaoCarteiraTotal);
	}
	
	private ResumoOcupacaoCarteiraPorModalidade agruparPorModalidade(String tipoModalidade, List<ResumoOcupacaoCarteiraPorCanalVenda> listCanais) {
		double valorOrcado = 0; 
		double valorReal = 0; 
		double valorConfirmar = 0; 
		double valorTotal = 0; 
		double percentual = 0;

		for (ResumoOcupacaoCarteiraPorCanalVenda canal : listCanais) {
			valorOrcado += canal.getValorOrcado(); 
			valorReal += canal.getValorReal(); 
			valorConfirmar += canal.getValorConfirmar(); 
		}
		valorTotal = valorReal + valorConfirmar; 
		percentual = valorOrcado > 0 ? ((valorTotal / valorOrcado) * 100) : 0;
		
		return new ResumoOcupacaoCarteiraPorModalidade(tipoModalidade, valorOrcado, valorReal, valorConfirmar, valorTotal, percentual);
	}
	
	private ResumoOcupacaoCarteiraPorModalidade somar(ResumoOcupacaoCarteiraPorModalidade varejo, ResumoOcupacaoCarteiraPorModalidade atacado) {
		double valorOrcado = varejo.getValorOrcado() + atacado.getValorOrcado(); 
		double valorReal = varejo.getValorReal() + atacado.getValorReal(); 
		double valorConfirmar = varejo.getValorConfirmar() + atacado.getValorConfirmar(); 
		double valorTotal = valorReal + valorConfirmar; 
		double percentual = valorOrcado > 0 ? ((valorTotal / valorOrcado) * 100) : 0;		
		return new ResumoOcupacaoCarteiraPorModalidade("AMBOS", valorOrcado, valorReal, valorConfirmar, valorTotal, percentual);
	}
	
	private List<ResumoOcupacaoCarteiraPorCanalVenda> consultarOcupacaoEmValor(int mes, int ano, String tipoPedido, int tipoClassificao, int tipoMeta, String tipoModalidade) {
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoEmValor = null;
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoConfirmarEmValor = null;	
		// Carrega os valores apenas para atacado.
		if (tipoModalidade.equalsIgnoreCase(OcupacaoCarteiraCustom.MODALIDADE_ATACADO) ) {
			listOcupacaoEmValor = ocupacaoCarteiraCustom.consultarCarteiraPorValor(mes, ano, tipoPedido, tipoClassificao, tipoMeta);
			listOcupacaoConfirmarEmValor = ocupacaoCarteiraCustom.consultarCarteiraConfirmarPorValor(mes, ano, tipoPedido, tipoClassificao, tipoMeta);				
		}				
		return atualizarDadosOrcadoVersusRealizado(mes, ano, tipoMeta, tipoModalidade, listOcupacaoEmValor, listOcupacaoConfirmarEmValor); 
	}	
	private List<ResumoOcupacaoCarteiraPorCanalVenda> consultarOcupacaoEmQuantidade(int mes, int ano, String tipoPedido, int tipoClassificao, int tipoMeta, String tipoModalidade) {
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoEmQtde = ocupacaoCarteiraCustom.consultarCarteiraPorQuantidade(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoConfirmarEmQtde = ocupacaoCarteiraCustom.consultarCarteiraConfirmarPorQuantidade(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);				
		return atualizarDadosOrcadoVersusRealizado(mes, ano, tipoMeta, tipoModalidade, listOcupacaoEmQtde, listOcupacaoConfirmarEmQtde); 
	}	

	private List<ResumoOcupacaoCarteiraPorCanalVenda> consultarOcupacaoEmMinutos(int mes, int ano, String tipoPedido, int tipoClassificao, int tipoMeta, String tipoModalidade) {
		double minutosFabricacao = metasDoOrcamentoCustom.findMinutosPorPecaByTipoMetaAnoMes(tipoMeta, mes, ano);
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacao = ocupacaoCarteiraCustom.consultarCarteiraPorMinutos(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade, minutosFabricacao);
		List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoConfirmar = ocupacaoCarteiraCustom.consultarCarteiraConfirmarPorMinutos(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade, minutosFabricacao);						
		return atualizarDadosOrcadoVersusRealizado(mes, ano, tipoMeta, tipoModalidade, listOcupacao, listOcupacaoConfirmar); 
	}	
	
	private List<ResumoOcupacaoCarteiraPorCanalVenda> atualizarDadosOrcadoVersusRealizado(int mes, int ano, int tipoMeta, String tipoModalidade,  List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacao, List<ResumoOcupacaoCarteiraPorCanalVenda> listOcupacaoConfirmar) {
		Map<String, ResumoOcupacaoCarteiraPorCanalVenda> mapOcupacao = new HashMap<String, ResumoOcupacaoCarteiraPorCanalVenda>();
	    List<ResumoOcupacaoCarteiraPorCanalVenda> listResumoPorCanal = new ArrayList<ResumoOcupacaoCarteiraPorCanalVenda>();
		List<MetaOrcamentoPorMesAno> metasCadastradas = metasDoOrcamentoCustom.findMetasOrcamentoByTipoMetaMesAno(tipoMeta, mes, ano, tipoModalidade);		
		
	    for (MetaOrcamentoPorMesAno orcado : metasCadastradas) {
	    	ResumoOcupacaoCarteiraPorCanalVenda resumo = new ResumoOcupacaoCarteiraPorCanalVenda();
	    	resumo.setCanal(orcado.getCanal());
	    	resumo.setValorOrcado(orcado.getValor());
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
	
	private List<ResumoOcupacaoCarteiraPorCanalVenda> consultarOcupacaoCarteiraPorCanal(String tipoOcupacao, int mes, int ano, String tipoOrcamento, boolean pedidosDisponibilidade, boolean pedidosProgramados, boolean pedidosProntaEntrega, String tipoModalidade) {		
		String tipoPedido = "";
		int tipoClassificao = 0;
		
		if (pedidosProgramados) tipoPedido = "0";   
		if (pedidosProntaEntrega) tipoPedido = tipoPedido.isEmpty() ? "1" : "0,1";
		// se estiver marcado apenas pedidos de disponibilidade deve considerar pedidos programados e pronta entrega.
		if (tipoPedido.isEmpty()) tipoPedido = pedidosDisponibilidade ? "0,1" : "9"; 		
		if ((pedidosDisponibilidade)&(!pedidosProgramados)&(!pedidosProntaEntrega)) tipoClassificao = OcupacaoCarteiraCustom.CLASSIFICACAO_DISPONIBILIDADE;
		
		List<ResumoOcupacaoCarteiraPorCanalVenda> listDados = null;
				
		if (tipoOcupacao.equalsIgnoreCase(OcupacaoCarteiraCustom.OCUPACAO_EM_VALORES)) {
			int tipoMeta = tipoOrcamento.equalsIgnoreCase(OcupacaoCarteiraCustom.META_ORCADA) ? MetasDoOrcamentoCustom.METAS_FATURAMENTO : MetasDoOrcamentoCustom.METAS_FATURAMENTO_REALINHADO;
			listDados = consultarOcupacaoEmValor(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		} else if (tipoOcupacao.equalsIgnoreCase(OcupacaoCarteiraCustom.OCUPACAO_EM_PECAS)) {			
			int tipoMeta = tipoOrcamento.equalsIgnoreCase(OcupacaoCarteiraCustom.META_ORCADA) ? MetasDoOrcamentoCustom.METAS_FAT_EM_PECAS : MetasDoOrcamentoCustom.METAS_FAT_EM_PECAS_REALINHADO;
			listDados = consultarOcupacaoEmQuantidade(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		} else if (tipoOcupacao.equalsIgnoreCase(OcupacaoCarteiraCustom.OCUPACAO_EM_MINUTOS)) {
			int tipoMeta = tipoOrcamento.equalsIgnoreCase(OcupacaoCarteiraCustom.META_ORCADA) ? MetasDoOrcamentoCustom.METAS_FAT_EM_MINUTOS : MetasDoOrcamentoCustom.METAS_FAT_EM_MINUTOS_REALINHADO;
			listDados = consultarOcupacaoEmMinutos(mes, ano, tipoPedido, tipoClassificao, tipoMeta, tipoModalidade);
		}
		
	    return listDados; 
	}		
}
