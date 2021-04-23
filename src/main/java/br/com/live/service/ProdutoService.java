package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.Produto;
import br.com.live.entity.ProdutoReferCor;
import br.com.live.model.Alternativa;
import br.com.live.model.AlternativaRoteiroPadrao;
import br.com.live.model.CorProduto;
import br.com.live.model.Embarque;
import br.com.live.model.MarcacaoRisco;
import br.com.live.model.Roteiro;
import br.com.live.util.FiltroProduto;

@Service
public class ProdutoService {

	private final ProdutoCustom produtoRepository;	

	public ProdutoService(ProdutoCustom produtoRepository) {
		this.produtoRepository = produtoRepository;		
	}

	public List<Produto> findProdutosByParameters(FiltroProduto filtro) {		
		return produtoRepository.findProdutosByParameters(filtro);		
	}

	public List<CorProduto> findCoresByParameters(FiltroProduto filtro) {		
		return produtoRepository.findCoresByParameters(filtro);		
	}

	public List<ProdutoReferCor> findItensByParameters(FiltroProduto filtro) {		
		return produtoRepository.findItensByParameters(filtro);		
	}

	public ProdutoReferCor findItemByCodigo(String grupo, String item) {
		return produtoRepository.findItemByCodigo(grupo, item);
	}
	
	public List<Embarque> findEmbarques() {		
		return produtoRepository.findAllEmbarques();		
	}
	
	public List<Alternativa> findAlternativasByCodigo(String grupo, String item) {		
		return produtoRepository.findAlternativasByCodigo(grupo, item);
	}

	public List<Roteiro> findRoteirosByCodigo(String grupo, String item, int alternativa) {		
		return produtoRepository.findRoteirosByCodigo(grupo, item, alternativa);
	}
	
	public AlternativaRoteiroPadrao findAlternativaRoteiroPadraoByCodigo(String grupo, String item) {		
		return produtoRepository.findAlternativaRoteiroPadraoByCodigo(grupo, item);
	}	
	
	public int findSequenciaPrincipalRisco(String grupo, String item, int alternativa) {
		return produtoRepository.findSequenciaPrincipalRisco(grupo, item, alternativa);
	}
	
	public List<MarcacaoRisco> findMarcacoesRisco(String grupo, int risco, int seqRisco, int alternativa) {
		return produtoRepository.findMarcacoesRisco(grupo, risco, seqRisco, alternativa);
	}
}
