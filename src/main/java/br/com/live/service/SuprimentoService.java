package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ProdutoCustom;
import br.com.live.custom.SuprimentoCustom;
import br.com.live.model.CentroCusto;
import br.com.live.model.RequisicaoAlmoxarifado;
import br.com.live.model.RequisicaoAlmoxarifadoItem;
import br.com.live.util.StatusGravacao;

@Service
@Transactional
public class SuprimentoService {

	private final SuprimentoCustom suprimentoCustom; 
	private final ProdutoCustom produtoCustom; 
	
	public SuprimentoService(SuprimentoCustom suprimentoCustom, ProdutoCustom produtoCustom) {
		this.suprimentoCustom = suprimentoCustom;
		this.produtoCustom = produtoCustom;
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
	
	public StatusGravacao efetuarCopiaRequisicaoAlmox(RequisicaoAlmoxarifado requisicao) {
		StatusGravacao status;
		String msg = validarRequisicaoAlmox(requisicao);
		String msgComplementar = "";
		if (!msg.isEmpty()) return new StatusGravacao(false, msg);
			
		try {				
			int depositoPadrao = suprimentoCustom.getDepositoPadraoReqComercial(requisicao.getEmpresa());
			int codTransacaoAlmox = suprimentoCustom.findTransacaoAlmoxarifado(requisicao.getEmpresa());
			int novaRequisicao = suprimentoCustom.findNextNumeroRequisicao();		
			suprimentoCustom.gravarCapaRequisicao(novaRequisicao, requisicao.getCentroCusto(), requisicao.getObservacao(), requisicao.getRequisitante(), requisicao.getEmpresa(), requisicao.getDivisaoProducao());
			
			for (RequisicaoAlmoxarifadoItem item : requisicao.getListaItens()) {
				if (item.getQuantidade() > 0) { 
					int novaSequencia = suprimentoCustom.findNextSequenciaItemRequisicao(novaRequisicao);												
					int deposito = item.getDeposito();
					if (deposito == 0) deposito = depositoPadrao;
					suprimentoCustom.gravarItemRequisicao(novaRequisicao, novaSequencia, item.getNivel(), item.getGrupo(), item.getSub(), item.getItem(), item.getQuantidade(), codTransacaoAlmox, deposito, requisicao.getCentroCusto(), item.getNarrativa());					
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
}
