package br.com.live.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.SugestaoColetaCustom;
import br.com.live.entity.AreaColeta;
import br.com.live.entity.LoteSugestaoColeta;
import br.com.live.entity.LoteSugestaoColetaPorArea;
import br.com.live.entity.LoteSugestaoColetaPorAreaItem;
import br.com.live.model.ConsultaSugestaoColetaPorLoteArea;
import br.com.live.model.ItemAColetarPorPedido;
import br.com.live.model.SugestaoColeta;
import br.com.live.repository.AreaColetaRepository;
import br.com.live.repository.LoteSugestaoColetaPorAreaItemRepository;
import br.com.live.repository.LoteSugestaoColetaPorAreaRepository;
import br.com.live.repository.LoteSugestaoColetaPorColetorRepository;
import br.com.live.repository.LoteSugestaoColetaRepository;
import br.com.live.util.StatusPesquisa;

@Service
@Transactional
public class SugestaoColetaService {

	private final SugestaoColetaCustom sugestaoColetaCustom;
	private final AreaColetaRepository areaColetaRepository;
	private final LoteSugestaoColetaRepository loteSugestaoColetaRepository;
	private final LoteSugestaoColetaPorAreaRepository loteSugestaoColetaPorAreaRepository;
	private final LoteSugestaoColetaPorAreaItemRepository loteSugestaoColetaPorAreaItemRepository;
	private final LoteSugestaoColetaPorColetorRepository loteSugestaoColetaPorColetorRepository;

	public SugestaoColetaService(SugestaoColetaCustom sugestaoColetaCustom, AreaColetaRepository areaColetaRepository,
			LoteSugestaoColetaRepository loteSugestaoColetaRepository,
			LoteSugestaoColetaPorAreaRepository loteSugestaoColetaPorAreaRepository,
			LoteSugestaoColetaPorAreaItemRepository loteSugestaoColetaPorAreaItemRepository,
			LoteSugestaoColetaPorColetorRepository loteSugestaoColetaPorColetorRepository) {
		this.sugestaoColetaCustom = sugestaoColetaCustom;
		this.areaColetaRepository = areaColetaRepository;
		this.loteSugestaoColetaRepository = loteSugestaoColetaRepository;
		this.loteSugestaoColetaPorAreaRepository = loteSugestaoColetaPorAreaRepository;
		this.loteSugestaoColetaPorAreaItemRepository = loteSugestaoColetaPorAreaItemRepository;
		this.loteSugestaoColetaPorColetorRepository = loteSugestaoColetaPorColetorRepository;
	}

	public void saveAreaColeta(long id, String descricao, String enderecoInicio, String enderecoFim) {
		AreaColeta area = areaColetaRepository.findById(id);

		if (area == null) {
			id = areaColetaRepository.findNextId();
			area = new AreaColeta(id, descricao, enderecoInicio, enderecoFim);
		} else {
			area.setDescricao(descricao);
			area.setEnderecoInicio(enderecoInicio);
			area.setEnderecoFim(enderecoFim);
		}
		areaColetaRepository.save(area);
	}

	public void deleteAreaColeta(long id) {
		areaColetaRepository.deleteById(id);
	}

	public void cleanLoteColetaNaoLiberadoByUsuario(long idUsuario) {
		LoteSugestaoColeta lote = loteSugestaoColetaRepository.findLoteNaoLiberadoByUsuario(idUsuario);
		if (lote != null) {
			List<LoteSugestaoColetaPorArea> areas = loteSugestaoColetaPorAreaRepository.findAreasByLote(lote.getId());
			for (LoteSugestaoColetaPorArea area : areas) {
				loteSugestaoColetaPorAreaItemRepository.deleteByIdLoteArea(area.getId());
				loteSugestaoColetaPorColetorRepository.deleteByIdLoteArea(area.getId());
			}
			loteSugestaoColetaPorAreaRepository.deleteByIdLote(lote.getId());
			loteSugestaoColetaRepository.deleteById(lote.getId());
		}
	}

	public void createLoteColeta(long idUsuarioLote, List<SugestaoColeta> pedidosSugeridos) {

		cleanLoteColetaNaoLiberadoByUsuario(idUsuarioLote);
		
		Map<Long, List<ItemAColetarPorPedido>> mapItensPorArea = new HashMap<Long, List<ItemAColetarPorPedido>>();
		List<ItemAColetarPorPedido> itensColetarArea = null;

		/* Gravar o lote */
		LoteSugestaoColeta lote = new LoteSugestaoColeta(loteSugestaoColetaRepository.findNextId(), 0, idUsuarioLote);
		loteSugestaoColetaRepository.save(lote);

		for (SugestaoColeta pedido : pedidosSugeridos) {
			List<ItemAColetarPorPedido> itens = sugestaoColetaCustom.findItensParaColetarByPedido(pedido.pedido);
			for (ItemAColetarPorPedido item : itens) {
				long area = 999999;
				AreaColeta areaColeta = areaColetaRepository.findAreaColetaByEndereco(item.getEndereco());
				if (areaColeta != null)
					area = areaColeta.getId();
				if (!mapItensPorArea.containsKey(area)) {
					itensColetarArea = new ArrayList<ItemAColetarPorPedido>();
					mapItensPorArea.put(area, itensColetarArea);
				} else {
					itensColetarArea = mapItensPorArea.get(area);
				}
				itensColetarArea.add(item);
			}
		}

		for (Long idArea : mapItensPorArea.keySet()) {
			itensColetarArea = mapItensPorArea.get(idArea);
			/* Gravar as áreas de coletas do lote */
			LoteSugestaoColetaPorArea loteArea = new LoteSugestaoColetaPorArea(
					loteSugestaoColetaPorAreaRepository.findNextId(), lote.getId(), idArea);
			loteSugestaoColetaPorAreaRepository.save(loteArea);

			for (ItemAColetarPorPedido item : itensColetarArea) {
				/* Gravar o item de cada área */				
				LoteSugestaoColetaPorAreaItem itemColetar = new LoteSugestaoColetaPorAreaItem(
						loteSugestaoColetaPorAreaItemRepository.findNextId(), loteArea.getId(), item.getPedido(),
						item.getNivel(), item.getGrupo(), item.getSub(), item.getItem(), item.getEndereco(),
						item.getQtdeColetar());
				loteSugestaoColetaPorAreaItemRepository.save(itemColetar);				
			}
		}
	}
	
	public StatusPesquisa findSugestaoColetaParaLiberarByIdUsuario(long idUsuario) {
		boolean encontrou = false;
		List<ConsultaSugestaoColetaPorLoteArea> dados = sugestaoColetaCustom.findSugestaoColetaParaLiberarByIdUsuario(idUsuario);
		if (dados != null) encontrou = true;
		return new StatusPesquisa(encontrou, dados);
	}
}