package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.PedidoVendaCustom;
import br.com.live.entity.PercComissaoOrgPedido;
import br.com.live.model.PedidoVenda;
import br.com.live.repository.PercComissaoOrgPedidoRepository;

@Service
@Transactional
public class PedidoVendaService {

	private final PercComissaoOrgPedidoRepository percComissaoOrgPedidoRepository;
	private final PedidoVendaCustom pedidoVendaCustom;

	public PedidoVendaService(PercComissaoOrgPedidoRepository percComissaoOrgPedidoRepository,
			PedidoVendaCustom pedidoVendaCustom) {
		this.percComissaoOrgPedidoRepository = percComissaoOrgPedidoRepository;
		this.pedidoVendaCustom = pedidoVendaCustom;
	}

	public void salvarComissaoOrigem(List<PedidoVenda> comissaoOrigensPed) {
		PercComissaoOrgPedido comissaoOrigemPed;

		pedidoVendaCustom.deleteAllOrigens();

		for (PedidoVenda dadosComissao : comissaoOrigensPed) {
			if (dadosComissao.percComissao > 0) {
				comissaoOrigemPed = new PercComissaoOrgPedido(dadosComissao.id, dadosComissao.percComissao);
				percComissaoOrgPedidoRepository.save(comissaoOrigemPed);
			}
		}
	}
}
