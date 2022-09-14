package br.com.live.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ComercialCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.BloqueioTitulosForn;
import br.com.live.model.ConsultaTitulosBloqForn;
import br.com.live.model.Produto;
import br.com.live.repository.BloqueioTitulosFornRepository;

@Service
@Transactional
public class ComercialService {
	
	private final BloqueioTitulosFornRepository bloqueioTitulosFornRepository;
	private final ComercialCustom comercialCustom;
	private final ProdutoCustom produtoCustom;
	
	public ComercialService(BloqueioTitulosFornRepository bloqueioTitulosFornRepository, ComercialCustom comercialCustom, ProdutoCustom produtoCustom) {
		this.bloqueioTitulosFornRepository = bloqueioTitulosFornRepository;
		this.comercialCustom = comercialCustom;
		this.produtoCustom = produtoCustom;
	}
	
	public List<ConsultaTitulosBloqForn> findAllFornBloq() {
		return comercialCustom.findAllTitulosBloqForn();
	}
	
	public BloqueioTitulosForn findForncedorBloq(String idForn) {
		String[] fornecedorConcat = idForn.split("[.]");

		String fornecedor9 = fornecedorConcat[0];
		String fornecedor4 = fornecedorConcat[1];
		String fornecedor2 = fornecedorConcat[2];
		
		return bloqueioTitulosFornRepository.findByIdBloq(Integer.parseInt(fornecedor9) + Integer.parseInt(fornecedor4) + Integer.parseInt(fornecedor2));
	}
	
	public void saveBloqueio(String fornecedor, String motivo, boolean editMode) {
		
		String[] fornecedorConcat = fornecedor.split("[.]");

		String fornecedor9 = fornecedorConcat[0];
		String fornecedor4 = fornecedorConcat[1];
		String fornecedor2 = fornecedorConcat[2];
		
		BloqueioTitulosForn dadosBloqueio = bloqueioTitulosFornRepository.findByIdBloq(Integer.parseInt(fornecedor9) + Integer.parseInt(fornecedor4) + Integer.parseInt(fornecedor2));
		
		if (dadosBloqueio == null) {
			dadosBloqueio = new BloqueioTitulosForn(Integer.parseInt(fornecedor9), Integer.parseInt(fornecedor4), Integer.parseInt(fornecedor2), new Date(), null, motivo);
		} else {
			dadosBloqueio.motivo = motivo;
			
			if ((dadosBloqueio.dataDesbloqueio != null) && (!editMode)) {
				dadosBloqueio.dataDesbloqueio = null;
				dadosBloqueio.dataBloqueio = new Date();
			}
		}
		bloqueioTitulosFornRepository.save(dadosBloqueio);
	}
	
	public void liberarBloqueio(String fornecedor) {
		String[] fornecedorConcat = fornecedor.split("[.]");

		String fornecedor9 = fornecedorConcat[0];
		String fornecedor4 = fornecedorConcat[1];
		String fornecedor2 = fornecedorConcat[2];
		
		BloqueioTitulosForn dadosBloqueio = bloqueioTitulosFornRepository.findByIdBloq(Integer.parseInt(fornecedor9) + Integer.parseInt(fornecedor4) + Integer.parseInt(fornecedor2));
		dadosBloqueio.dataDesbloqueio = new Date();
		bloqueioTitulosFornRepository.save(dadosBloqueio);
	}
	
	public void saveProdutosIntegracaoEcom(String referencia, String tamanho, String cor) {
		List<Produto> produtos = produtoCustom.findProdutos("1", referencia, tamanho, cor);
	
		for (Produto produto : produtos) {
			String codigoSku = produto.nivel + "." + produto.grupo + "." + produto.sub + "." + produto.item;
			comercialCustom.gravaEnvioProdEcommerce(codigoSku);
		}
	}
}
