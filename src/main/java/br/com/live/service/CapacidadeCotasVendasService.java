package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.CapacidadeCotasVendasCustom;
import br.com.live.entity.CapacidadeCotasVendasCapa;
import br.com.live.entity.CapacidadeCotasVendasItens;
import br.com.live.model.CapacidadesCotasVendas;
import br.com.live.model.Categoria;
import br.com.live.model.ProdutosCapacidadeProd;
import br.com.live.repository.CapacidadeCotasVendasItensRepository;
import br.com.live.repository.CapacidadeCotasVendasRepository;

@Service
@Transactional
public class CapacidadeCotasVendasService {

	private final CapacidadeCotasVendasCustom capacidadeCotasVendasCustom;
	private final CapacidadeCotasVendasRepository capacidadeCotasVendasRepository;
	private final CapacidadeCotasVendasItensRepository capacidadeCotasVendasItensRepository;

	public CapacidadeCotasVendasService(CapacidadeCotasVendasCustom capacidadeCotasVendasCustom,
			CapacidadeCotasVendasRepository capacidadeCotasVendasRepository,
			CapacidadeCotasVendasItensRepository capacidadeCotasVendasItensRepository) {
		this.capacidadeCotasVendasCustom = capacidadeCotasVendasCustom;
		this.capacidadeCotasVendasRepository = capacidadeCotasVendasRepository;
		this.capacidadeCotasVendasItensRepository = capacidadeCotasVendasItensRepository;
	}

	public List<CapacidadesCotasVendas> findAllCapacidadesCotasVendas() {
		return capacidadeCotasVendasCustom.findAllCapacidadesCotasVendas();
	}

	public List<Categoria> findCategoriasProd() {
		return capacidadeCotasVendasCustom.findCategoriasProd();
	}

	public List<ProdutosCapacidadeProd> findProdutosByCategoriaLinha(int colecao, int linha, int periodo, boolean listarComQtde) {
		return capacidadeCotasVendasCustom.findProdutosByCategoriaLinha(colecao, linha, periodo, listarComQtde);
	}
	
	public List<ProdutosCapacidadeProd> findProdutosByIdCapacidadeCotas(String idCapacidadeCotas) {
		return capacidadeCotasVendasCustom.findModelosByidCapacidadeCotas(idCapacidadeCotas);
	}

	public void deleteById(String idCapacidadeCotas) {
		capacidadeCotasVendasRepository.deleteById(idCapacidadeCotas);
		capacidadeCotasVendasItensRepository.deleteByIdCapa(idCapacidadeCotas);
	}

	public void saveCapacidadeCotasVendas(int periodo, int colecao, int linha, List<ProdutosCapacidadeProd> modelos, int minDistribuir) {
		
		CapacidadeCotasVendasCapa dadosCapacidadeCapa = null;

		dadosCapacidadeCapa = capacidadeCotasVendasRepository.findByPeriodoColecaoLinha(periodo, colecao, linha);

		// EDIÇÃO
		if (dadosCapacidadeCapa != null) {

			dadosCapacidadeCapa.periodo = periodo;
			dadosCapacidadeCapa.colecao = colecao;
			dadosCapacidadeCapa.linha = linha;
			dadosCapacidadeCapa.minDistribuir = minDistribuir;

			// INSERÇÃO
		} else {
			dadosCapacidadeCapa = new CapacidadeCotasVendasCapa(periodo, linha, colecao, minDistribuir);
		}

		capacidadeCotasVendasRepository.save(dadosCapacidadeCapa);
		
		saveModelos(dadosCapacidadeCapa.id, modelos);
	}

	private void saveModelos(String idCapacidade, List<ProdutosCapacidadeProd> modelos) {
		
		capacidadeCotasVendasItensRepository.deleteByIdCapa(idCapacidade);
		
		for (ProdutosCapacidadeProd modelo : modelos) {
			if ((modelo.getMinutos() > 0) || (modelo.getPecas() > 0)) { 
				CapacidadeCotasVendasItens capacidadeCotasItens = new CapacidadeCotasVendasItens(idCapacidade, modelo.getModelo(), modelo.getTempoUnitario(),modelo.getMinutos(),modelo.getPecas());
				capacidadeCotasVendasItensRepository.save(capacidadeCotasItens);
			}
		}

	}
}
