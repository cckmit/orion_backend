package br.com.live.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.SugestaoColetaCustom;
import br.com.live.entity.AreaColeta;
import br.com.live.model.ItemAColetarPorPedido;
import br.com.live.model.SugestaoColeta;
import br.com.live.repository.AreaColetaRepository;

@Service
@Transactional
public class SugestaoColetaService {

	private final SugestaoColetaCustom sugestaoColetaCustom; 
	private final AreaColetaRepository areaColetaRepository;
	
	public SugestaoColetaService(SugestaoColetaCustom sugestaoColetaCustom, AreaColetaRepository areaColetaRepository) {
		this.sugestaoColetaCustom = sugestaoColetaCustom;
		this.areaColetaRepository = areaColetaRepository;
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
	
	public void createLoteColeta(List<SugestaoColeta> pedidosSugeridos) {
		
		System.out.println("*** createLoteColeta ***");				
		
		Map<String, List<ItemAColetarPorPedido>> mapItensPorArea = new HashMap<String, List<ItemAColetarPorPedido>>();
		List<ItemAColetarPorPedido> itensColetarArea = null;
		
		// Separa por area os itens para coleta
		for (SugestaoColeta pedido : pedidosSugeridos) {
			List<ItemAColetarPorPedido> itens = sugestaoColetaCustom.findItensParaColetarByPedido(pedido.pedido);	
			
			for (ItemAColetarPorPedido item : itens) {				
				String area = "SEM AREA";
				AreaColeta areaColeta = areaColetaRepository.findAreaColetaByEndereco(item.getEndereco());				
				if (areaColeta != null) area = areaColeta.getDescricao(); 
				
				if (!mapItensPorArea.containsKey(area)) {
					itensColetarArea = new ArrayList<ItemAColetarPorPedido>();
				} else {
					itensColetarArea = mapItensPorArea.get(area);
				}				
				itensColetarArea.add(item);
				mapItensPorArea.put(area, itensColetarArea);
			}
		}
					
		// Grava informações por area
		for (String area : mapItensPorArea.keySet()) {
			int qtdePecas = 0;
			List<Integer> pedidos = new ArrayList<Integer>();
			List<String> skus = new ArrayList<String>();
			List<String> enderecos = new ArrayList<String>();
			
			itensColetarArea = mapItensPorArea.get(area);			
			for (ItemAColetarPorPedido itemColetar : itensColetarArea) {				
				qtdePecas++;
				if (!pedidos.contains(itemColetar.getPedido())) pedidos.add(itemColetar.getPedido()); 
			    if (!skus.contains(itemColetar.getNivel() + "." + itemColetar.getGrupo() + "." + itemColetar.getSub() + "." + itemColetar.getItem() + "-" + itemColetar.getDeposito()))
			    	skus.add(itemColetar.getNivel() + "." + itemColetar.getGrupo() + "." + itemColetar.getSub() + "." + itemColetar.getItem() + "-" + itemColetar.getDeposito()); 	
			    if (!enderecos.contains(itemColetar.getEndereco())) enderecos.add(itemColetar.getEndereco());			    			
			}			
		}
		
		
		
	}

}
