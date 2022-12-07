package br.com.live.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ComercialCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.BloqueioTitulosForn;
import br.com.live.entity.MetasCategoria;
//import br.com.live.entity.TpClienteXTabPreco;
//import br.com.live.entity.TpClienteXTabPrecoItem;
import br.com.live.model.ConsultaTitulosBloqForn;
import br.com.live.model.Produto;
import br.com.live.repository.BloqueioTitulosFornRepository;
import br.com.live.repository.MetasCategoriaRepository;
//import br.com.live.repository.TpClienteXTabPrecoItemRepository;
//import br.com.live.repository.TpClienteXTabPrecoRepository;
import br.com.live.util.StatusGravacao;
import ch.qos.logback.core.net.SyslogOutputStream;

@Service
@Transactional
public class ComercialService {
	
	private final BloqueioTitulosFornRepository bloqueioTitulosFornRepository;
	private final ComercialCustom comercialCustom;
	private final ProdutoCustom produtoCustom;
	private final MetasCategoriaRepository metasCategoriaRepository;
	//private final TpClienteXTabPrecoRepository tpClienteXTabPrecoRepository;
	//private final TpClienteXTabPrecoItemRepository tpClienteXTabPrecoItemRepository;
	
	public ComercialService(BloqueioTitulosFornRepository bloqueioTitulosFornRepository, ComercialCustom comercialCustom, ProdutoCustom produtoCustom, MetasCategoriaRepository metasCategoriaRepository) {
		this.bloqueioTitulosFornRepository = bloqueioTitulosFornRepository;
		this.comercialCustom = comercialCustom;
		this.produtoCustom = produtoCustom;
		this.metasCategoriaRepository = metasCategoriaRepository; 
		//this.tpClienteXTabPrecoRepository = tpClienteXTabPrecoRepository;
		//this.tpClienteXTabPrecoItemRepository = tpClienteXTabPrecoItemRepository;
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
	/*
	public List<TpClienteXTabPreco> findAllRelacionamento() {
		return tpClienteXTabPrecoRepository.findAll();
	}
	
	public void deleteItemRelacionamento(int idItem) {
		tpClienteXTabPrecoItemRepository.deleteById(idItem);
	}
	
	public void deleteRelacionamento(String idCapa) {
		tpClienteXTabPrecoItemRepository.deleteByIdCapa(idCapa);
	}
	
	public void deleteRelacCapa(String idCapa) {
		tpClienteXTabPrecoRepository.deleteById(idCapa);
	}
	*/
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
	public void importarMetasCategoria(long codEstacao, int tipoMeta, List<MetasCategoria> tabImportar) {
		metasCategoriaRepository.deleteByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);		
		for (MetasCategoria metas : tabImportar) {					
			MetasCategoria newMetas = new MetasCategoria(metas.codEstacao, metas.codRepresentante, metas.tipoMeta, metas.valorCategoria1, metas.valorCategoria2, metas.valorCategoria3, 
					metas.valorCategoria4, metas.valorCategoria5, metas.valorCategoria6, metas.valorCategoria7, metas.valorCategoria8, metas.valorCategoria9, metas.valorCategoria10);
			metasCategoriaRepository.save(newMetas);
		}		
	}
	
	public List<MetasCategoria> findAllMetasCategoria() {
		return metasCategoriaRepository.findAll();
	}
	
	/*
	public String saveRelacionamento(String id, int catalogo, int tipoCliente, String tabela, int numDias, int numInterno) {
		
		TpClienteXTabPreco dadosRelac = tpClienteXTabPrecoRepository.findByIdTpCliTabPreco(id);
		String[] tabelaConcat = tabela.split("[.]");

		String col = tabelaConcat[0];
		String mes = tabelaConcat[1];
		String seq = tabelaConcat[2];
		
		if (dadosRelac == null) {
			dadosRelac = new TpClienteXTabPreco(catalogo, tipoCliente, Integer.parseInt(col), Integer.parseInt(mes), Integer.parseInt(seq), numDias, numInterno);
		} else {
			dadosRelac.numDias = numDias;
			dadosRelac.numInterno = numInterno;
		}
		tpClienteXTabPrecoRepository.save(dadosRelac);
		return dadosRelac.id;
	}
	
	private boolean existsTpClienteXTabPrecoItemParaPeriodo(long idItem, String idCapa, Date periodoIni, Date periodoFim) {		
		TpClienteXTabPrecoItem dadosItem = tpClienteXTabPrecoItemRepository.findTabByData(idItem, idCapa, periodoIni, periodoFim);
		
		if (dadosItem != null) return true; 
		return false;
	}	
	
	public StatusGravacao saveRelacionamentoItem(long id, String idCapa, int catalogo, int tipoCliente, String tabela, Date periodoIni, Date periodoFim) {
		
		TpClienteXTabPrecoItem dadosItem = tpClienteXTabPrecoItemRepository.findByIdTpCliTabPrecoItem(id);

		long idItem = 0; 
		if (dadosItem != null) idItem = dadosItem.idItem;
		
		if (existsTpClienteXTabPrecoItemParaPeriodo(idItem, idCapa, periodoIni, periodoFim))
			return new StatusGravacao(false, "Já existe tabela de preço cadastrada para o período informado!");		
		
		String[] tableConcat = tabela.split("[.]");

		String col = tableConcat[0];
		String mes = tableConcat[1];
		String seq = tableConcat[2];
		
		if (dadosItem == null) { 
			dadosItem = new TpClienteXTabPrecoItem(tpClienteXTabPrecoItemRepository.findNextId(), idCapa, catalogo, tipoCliente, Integer.parseInt(col), Integer.parseInt(mes), Integer.parseInt(seq), periodoIni, periodoFim);
		} else {
			dadosItem.idCapa = idCapa;
			dadosItem.colTabEntr = Integer.parseInt(col);
			dadosItem.mesTabEntr = Integer.parseInt(mes);
			dadosItem.seqTabEntr = Integer.parseInt(seq);
			dadosItem.periodoIni = periodoIni;
			dadosItem.periodoFim = periodoFim;	
		}
		tpClienteXTabPrecoItemRepository.save(dadosItem);
		return new StatusGravacao(true, "");		
	}
	*/
}
