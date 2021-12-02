package br.com.live.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.entity.RequisicaoTecidos;
import br.com.live.entity.RequisicaoTecidosItem;
import br.com.live.repository.RequisicaoTecidosItemRepository;
import br.com.live.repository.RequisicaoTecidosRepository;

@Service
@Transactional
public class RequisicaoTecidosService {

	private final RequisicaoTecidosRepository requisicaoTecidosRepository;
	private final RequisicaoTecidosItemRepository requisicaoTecidosItemRepository; 
	
	public RequisicaoTecidosService(RequisicaoTecidosRepository requisicaoTecidosRepository, RequisicaoTecidosItemRepository requisicaoTecidosItemRepository) {
		this.requisicaoTecidosRepository = requisicaoTecidosRepository;
		this.requisicaoTecidosItemRepository = requisicaoTecidosItemRepository;
	}
	
	public RequisicaoTecidos saveRequisicao(long id, String descricao, int situacao, String usuario) {
				
		RequisicaoTecidos requisicao = requisicaoTecidosRepository.findById(id);
		
		if (requisicao == null) { 		
			requisicao = new RequisicaoTecidos(requisicaoTecidosRepository.findNextId(), descricao, situacao, usuario); 			
		} else {
			requisicao.descricao = descricao;
			requisicao.situacao = situacao;
		}		
		requisicaoTecidosRepository.save(requisicao);
		return requisicao;
	}
	
	public void saveRequisicaoItem(long id, long idRequisicao, String nivel, String grupo, String sub, String item, int alternativa, int roteiro, double quantidade) {
	
		RequisicaoTecidosItem requisicaoItem = requisicaoTecidosItemRepository.findById(id);
		
		if (requisicaoItem == null) {
			requisicaoItem = new RequisicaoTecidosItem(requisicaoTecidosItemRepository.findNextId(), idRequisicao, requisicaoTecidosItemRepository.findNextSequencia(idRequisicao), nivel, grupo, sub, item, alternativa, roteiro, quantidade); 
		} else {
			requisicaoItem.nivel = nivel;
			requisicaoItem.grupo = grupo;
			requisicaoItem.sub = sub;
			requisicaoItem.item = item;
			requisicaoItem.alternativa = alternativa;
			requisicaoItem.roteiro = roteiro;
			requisicaoItem.quantidade = quantidade;
		}
		requisicaoTecidosItemRepository.save(requisicaoItem);
	}
	
	public void deleteRequisicao(long idRequisicao) {
		requisicaoTecidosItemRepository.deleteByIdRequisicao(idRequisicao);
		requisicaoTecidosRepository.deleteById(idRequisicao);		
	}
	
	public void deleteRequisicaoItem(long idRequisicaoItem) {
		requisicaoTecidosItemRepository.deleteById(idRequisicaoItem);				
	}
}
