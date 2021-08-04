package br.com.live.service;

import java.util.ArrayList;
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
		
		boolean distribuirMinutosCapac = true;
		
		CapacidadeCotasVendasCapa dadosCapacidadeCapa = null;

		dadosCapacidadeCapa = capacidadeCotasVendasRepository.findByPeriodoColecaoLinha(periodo, colecao, linha);

		// EDIÇÃO
		if (dadosCapacidadeCapa != null) {

			if (dadosCapacidadeCapa.minDistribuir == minDistribuir) distribuirMinutosCapac = false;
			
			dadosCapacidadeCapa.periodo = periodo;
			dadosCapacidadeCapa.colecao = colecao;
			dadosCapacidadeCapa.linha = linha;
			dadosCapacidadeCapa.minDistribuir = minDistribuir;

			// INSERÇÃO
		} else {
			dadosCapacidadeCapa = new CapacidadeCotasVendasCapa(periodo, linha, colecao, minDistribuir);
		}

		capacidadeCotasVendasRepository.save(dadosCapacidadeCapa);

		// Se alterou os minutos a distribuir, o sistema deve distribuir novamente
		if (distribuirMinutosCapac) distribuirMinutos(colecao, minDistribuir, modelos);
		
		saveModelos(dadosCapacidadeCapa.id, modelos);
	}

	private void saveModelos(String idCapacidade, List<ProdutosCapacidadeProd> modelos) {
		
		capacidadeCotasVendasItensRepository.deleteByIdCapa(idCapacidade);
		
		for (ProdutosCapacidadeProd modelo : modelos) {			
			//System.out.println("modelo.getTempoUnitario(): " + modelo.getTempoUnitario() + " modelo.getMinutos(): " + modelo.getMinutos() + " modelo.getPecas(): " + modelo.getPecas());
			//System.out.println("Modelo: " + modelo.getModelo() + " - min unit: " + modelo.getTempoUnitario() + " - minutos: " + modelo.getMinutos() + " - pecas" + modelo.getPecas());
			
			CapacidadeCotasVendasItens capacidadeCotasItens = new CapacidadeCotasVendasItens(idCapacidade, modelo.getModelo(), modelo.getTempoUnitario(),modelo.getMinutos(),modelo.getPecas());
			capacidadeCotasVendasItensRepository.save(capacidadeCotasItens);			
		}
	}
	
	private void distribuirMinutos(int colecao, int minDistribuir, List<ProdutosCapacidadeProd> modelos) {
		
		System.out.println("distribuirMinutos - minDistribuir: " + minDistribuir + " - qtde modelos: " + modelos.size());

		int qtdePecas;
		float qtdeMinutos;
		float minutosUnitario;
		float minutosPadrao = 0;
		
		if (modelos.size() > 0) minutosPadrao = (float) ((float) minDistribuir / (float) modelos.size());
						
		System.out.println("minutosPadrao: " + minutosPadrao + " minDistribuir: " + minDistribuir + " modelos.size(): " + modelos.size());
		
		for (ProdutosCapacidadeProd modelo : modelos) {
			minutosUnitario = capacidadeCotasVendasCustom.findTempoUnitarioByReferenciaColecao(modelo.getModelo(), colecao);
			
			qtdePecas = 0;
			
			if (minutosUnitario > 0.0000) qtdePecas = (int )(minutosPadrao / minutosUnitario);							
			
			System.out.println(modelo.getModelo() + " minutosPadrao: " + minutosPadrao + " - minutosUnitario: " + minutosUnitario + " PECAS: " + qtdePecas);
			
			if (qtdePecas > 0) qtdeMinutos = ((float) qtdePecas * minutosUnitario);
			else qtdeMinutos = minutosPadrao;
			
			modelo.setTempoUnitario(minutosUnitario);
			modelo.setPecas(qtdePecas);
			modelo.setMinutos(qtdeMinutos);			
		}			
	}
	
	
}
