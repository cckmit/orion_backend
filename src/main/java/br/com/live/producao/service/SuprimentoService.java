package br.com.live.producao.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.administrativo.model.CentroCusto;
import br.com.live.producao.custom.SuprimentoCustom;
import br.com.live.producao.model.RequisicaoAlmoxarifado;
import br.com.live.producao.model.RequisicaoAlmoxarifadoItem;
import br.com.live.produto.custom.ProdutoCustom;
import br.com.live.util.StatusGravacao;
import br.com.live.util.repository.ParametrosRepository;

@Service
@Transactional
public class SuprimentoService {

	private final SuprimentoCustom suprimentoCustom; 
	private final ProdutoCustom produtoCustom; 
	private final ParametrosRepository parametrosRepository;
	
	public SuprimentoService(SuprimentoCustom suprimentoCustom, ProdutoCustom produtoCustom, ParametrosRepository parametrosRepository) {
		this.suprimentoCustom = suprimentoCustom;
		this.produtoCustom = produtoCustom;
		this.parametrosRepository = parametrosRepository;
	}
	
	private String validarRequisicaoAlmox(RequisicaoAlmoxarifado requisicao) {		
		String msgErro = "";		
		CentroCusto centroCusto = suprimentoCustom.findCentroCustoByCodigo(requisicao.getCentroCusto());
		if (requisicao.getEmpresa() != centroCusto.getEmpresa()) msgErro = "Empresa do centro de custo é diferente da empresa da requisição! Empresa da Requisição: " + requisicao.getEmpresa() + " - Empresa Centro Custo: " + centroCusto.getEmpresa();				
		if (!suprimentoCustom.isTemPermissaoRequisitarParaCentroCusto(requisicao.getCentroCusto(), requisicao.getRequisitante())) msgErro = "Requisitante não tem permissão para gerar requisição para esse centro de custo! Requisitante: " + requisicao.getRequisitante();		
		
		for (RequisicaoAlmoxarifadoItem item : requisicao.getListaItens()) {
			if (!produtoCustom.existeProduto(item.getNivel(), item.getGrupo(), item.getSub(), item.getItem()))
				msgErro = "Material não cadastrado! Material: Seq. " + item.getSequencia() + " -> " + item.getNivel() + "." + item.getGrupo() + "." + item.getSub() + "." + item.getItem() + " - " + item.getNarrativa();
		}
		
		return msgErro;
	}
	
	public int gravarCapaRequisicao(int centroCusto, String observacao, String requisitante, int codEmpresa, int numDivisaoProducao) {
		int novaRequisicao = suprimentoCustom.findNextNumeroRequisicao();
		suprimentoCustom.gravarCapaRequisicao(novaRequisicao, centroCusto, observacao, requisitante, codEmpresa, numDivisaoProducao);
		return novaRequisicao;
	}
	
	public void gravarItemRequisicao(int novaRequisicao, String nivel, String grupo, String sub, String item, double quantidade, int codTransacaoAlmox, int deposito, int centroCusto) {
		int novaSequencia = suprimentoCustom.findNextSequenciaItemRequisicao(novaRequisicao);
		String descricaoProduto = produtoCustom.findNarrativaProduto(nivel, grupo, sub, item);
		suprimentoCustom.gravarItemRequisicao(novaRequisicao, novaSequencia, nivel, grupo, sub, item, quantidade, codTransacaoAlmox, deposito, centroCusto, descricaoProduto);
	}
	
	public StatusGravacao efetuarCopiaRequisicaoAlmox(RequisicaoAlmoxarifado requisicao) {
		StatusGravacao status;
		String msg = validarRequisicaoAlmox(requisicao);
		String msgComplementar = "";
		if (!msg.isEmpty()) return new StatusGravacao(false, msg);
			
		try {				
			int depositoPadrao = suprimentoCustom.getDepositoPadraoReqComercial(requisicao.getEmpresa());
			int codTransacaoAlmox = suprimentoCustom.findTransacaoAlmoxarifado(requisicao.getEmpresa());
			int novaRequisicao = gravarCapaRequisicao(requisicao.getCentroCusto(), requisicao.getObservacao(), requisicao.getRequisitante(), requisicao.getEmpresa(), requisicao.getDivisaoProducao());
			
			for (RequisicaoAlmoxarifadoItem item : requisicao.getListaItens()) {
				if (item.getQuantidade() > 0) { 																
					int deposito = item.getDeposito();
					if (deposito == 0) deposito = depositoPadrao;					
					gravarItemRequisicao(novaRequisicao, item.getNivel(), item.getGrupo(), item.getSub(), item.getItem(), item.getQuantidade(), codTransacaoAlmox, deposito, requisicao.getCentroCusto()); 					
				} else {
					msgComplementar = " (Atenção! Alguns itens não foram gravados na requisição por estarem com quantidades zeradas.)";
				}
			}				
			status = new StatusGravacao(true, "Gerada a requisição: " + novaRequisicao + msgComplementar);
		} catch (Exception e) {				
			status = new StatusGravacao(false, e.getMessage());
		}						

		return status; 
	}		
	
	public void geracaoRequisicoesAlmoxParaPedidosComprasIntegrados() {
		int reqAlmoxarifadoAtiva = parametrosRepository.findByIdParametro("REQ-ALMOX-AUTOMATICO-ATIVO").getValorInt();
		int codLocalEntrega = parametrosRepository.findByIdParametro("REQ-ALMOX-AUTOMATICO-LOCAL_ENTREGA").getValorInt();
		Date dataInicial = parametrosRepository.findByIdParametro("REQ-ALMOX-AUTOMATICO-DATA-INICIAL").getValorDate();		
		
		if (reqAlmoxarifadoAtiva == 1) {
			List<Integer> listaPedidosCompras = suprimentoCustom.findPedidosCompraParaGeracaoReqAlmoxarifado(dataInicial, codLocalEntrega);
			gerarRequisicoesAlmoxarifadoParaPedidosCompras(listaPedidosCompras);
		}
	}		
	
	private void gerarRequisicoesAlmoxarifadoParaPedidosCompras(List<Integer> listaPedidosCompras) {
		for (Integer pedido : listaPedidosCompras) {
			
			String observacao = "REQUISIÇÃO GERADA AUTOMATICA";
			int centroCusto = 0; // vem dos itens do pedido compra			
			int codEmpresa = 100 ; // deve vir do parametro
			int numDivisaoProducao = 0;
			String requisitante = ""; // HDOC_030.E_MAIL
			
			int codTransacaoAlmox = 234; // criar parametro
			int deposito = 491; // criar parametro
						
			//gravarCapaRequisicao(int centroCusto, String observacao, String requisitante, int codEmpresa, int numDivisaoProducao);
			//gravarItemRequisicao(int novaRequisicao, String nivel, String grupo, String sub, String item, double quantidade, int codTransacaoAlmox, int deposito, int centroCusto) {
		}		
	}
}
