package br.com.live.comercial.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.comercial.custom.SugestaoColetaCustom;
import br.com.live.comercial.entity.AreaColeta;
import br.com.live.comercial.entity.LoteSugestaoColeta;
import br.com.live.comercial.entity.LoteSugestaoColetaPorArea;
import br.com.live.comercial.entity.LoteSugestaoColetaPorAreaItem;
import br.com.live.comercial.entity.LoteSugestaoColetaPorColetor;
import br.com.live.comercial.model.ConsultaSugestaoColetaPorLoteArea;
import br.com.live.comercial.model.ItemAColetarPorPedido;
import br.com.live.comercial.model.SugestaoColeta;
import br.com.live.comercial.repository.AreaColetaRepository;
import br.com.live.comercial.repository.LoteSugestaoColetaPorAreaItemRepository;
import br.com.live.comercial.repository.LoteSugestaoColetaPorAreaRepository;
import br.com.live.comercial.repository.LoteSugestaoColetaPorColetorRepository;
import br.com.live.comercial.repository.LoteSugestaoColetaRepository;
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

	@Deprecated
	public void cleanLoteColetaNaoLiberadoByUsuario(long idUsuario) {
		/*LoteSugestaoColeta lote = loteSugestaoColetaRepository.findLoteNaoLiberadoByUsuario(idUsuario);
		if (lote != null) {
			List<LoteSugestaoColetaPorArea> areas = loteSugestaoColetaPorAreaRepository.findAreasByLote(lote.getId());
			for (LoteSugestaoColetaPorArea area : areas) {
				loteSugestaoColetaPorAreaItemRepository.deleteByIdLoteArea(area.getId());
				loteSugestaoColetaPorColetorRepository.deleteByIdLoteArea(area.getId());
			}
			loteSugestaoColetaPorAreaRepository.deleteByIdLote(lote.getId());
			loteSugestaoColetaRepository.deleteById(lote.getId());
		}*/
	}

	public long createLoteColeta(long idUsuarioLote, List<SugestaoColeta> pedidosSugeridos) {

		//cleanLoteColetaNaoLiberadoByUsuario(idUsuarioLote);
		
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
		return lote.getId();
	}
	
	public StatusPesquisa findSugestaoColetaParaLiberarByIdUsuario(long idUsuario, long idLote) {
		boolean encontrou = false;
		List<ConsultaSugestaoColetaPorLoteArea> dados = sugestaoColetaCustom.findSugestaoColetaParaLiberarByIdUsuario(idUsuario, idLote);
		if (dados.size() > 0) encontrou = true;
		return new StatusPesquisa(encontrou, dados);
	}
	
	public void saveColetoresByArea(long idLoteArea, List<Integer> listIdColetores) {	
		loteSugestaoColetaPorColetorRepository.deleteByIdLoteArea(idLoteArea);
		for (Integer idColetor : listIdColetores) {
			LoteSugestaoColetaPorColetor coletor = new LoteSugestaoColetaPorColetor(loteSugestaoColetaPorColetorRepository.findNextId(), idLoteArea, idColetor);
			loteSugestaoColetaPorColetorRepository.save(coletor);
		}
	}

	public List<LoteSugestaoColetaPorAreaItem> findPedidosByIdAreaColeta(long idArea, boolean listarNaArea, long idLote) {
		if (listarNaArea) {
			return sugestaoColetaCustom.findPedidosSomenteEmUmaNaArea(idArea, idLote);
		} else {
			return sugestaoColetaCustom.findAllPedidosByArea(idArea, idLote);
		}
	}

	public List<LoteSugestaoColeta> findAllLotesByUsuario(long idUsuario) {
		return loteSugestaoColetaRepository.findLotesNaoLiberadosByUsuario(idUsuario);
	}
}