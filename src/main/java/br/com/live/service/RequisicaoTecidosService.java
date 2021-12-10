package br.com.live.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.RequisicaoTecidos;
import br.com.live.entity.RequisicaoTecidosItem;
import br.com.live.repository.RequisicaoTecidosItemRepository;
import br.com.live.repository.RequisicaoTecidosRepository;

@Service
@Transactional
public class RequisicaoTecidosService {

	private final RequisicaoTecidosRepository requisicaoTecidosRepository;
	private final RequisicaoTecidosItemRepository requisicaoTecidosItemRepository;
	private final ProdutoCustom produtoCustom;

	public RequisicaoTecidosService(RequisicaoTecidosRepository requisicaoTecidosRepository,
			RequisicaoTecidosItemRepository requisicaoTecidosItemRepository, ProdutoCustom produtoCustom) {
		this.requisicaoTecidosRepository = requisicaoTecidosRepository;
		this.requisicaoTecidosItemRepository = requisicaoTecidosItemRepository;
		this.produtoCustom = produtoCustom;
	}

	public void confirmarRequisicao(long id) {
		RequisicaoTecidos requisicao = requisicaoTecidosRepository.findById(id);
		requisicao.situacao = 2;
		requisicaoTecidosRepository.save(requisicao);
	}
	
	public RequisicaoTecidos saveRequisicao(long id, String descricao, int situacao, String usuario) {

		RequisicaoTecidos requisicao = requisicaoTecidosRepository.findById(id);

		if (requisicao == null) {
			requisicao = new RequisicaoTecidos(requisicaoTecidosRepository.findNextId(), descricao.toUpperCase(), situacao, usuario, new Date());
		} else {
			requisicao.descricao = descricao.toUpperCase();
			requisicao.situacao = situacao;
		}
		requisicaoTecidosRepository.save(requisicao);
		return requisicao;
	}

	public void saveRequisicaoItem(long id, long idRequisicao, String nivel, String grupo, String sub, String item,
			int alternativa, int roteiro, double quantidade) {

		if (produtoCustom.existsRoteiro(nivel, grupo, sub, item, alternativa, roteiro)) {
			RequisicaoTecidosItem requisicaoItem = requisicaoTecidosItemRepository.findById(id);
			if (requisicaoItem == null) {
				requisicaoItem = new RequisicaoTecidosItem(requisicaoTecidosItemRepository.findNextId(), idRequisicao,
						requisicaoTecidosItemRepository.findNextSequencia(idRequisicao), nivel, grupo, sub, item,
						alternativa, roteiro, quantidade);
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
	}

	public void deleteRequisicao(long idRequisicao) {
		requisicaoTecidosItemRepository.deleteByIdRequisicao(idRequisicao);
		requisicaoTecidosRepository.deleteById(idRequisicao);
	}

	public void deleteRequisicaoItem(long idRequisicaoItem) {
		requisicaoTecidosItemRepository.deleteById(idRequisicaoItem);
	}
}
