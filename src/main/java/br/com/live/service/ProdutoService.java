package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.live.body.BodyFiltroProduto;
import br.com.live.custom.ExpedicaoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.ProdutoReferencia;
import br.com.live.entity.ProdutoReferenciaCor;
import br.com.live.model.Alternativa;
import br.com.live.model.AlternativaRoteiroPadrao;
import br.com.live.model.AtualizaComplementoRef;
import br.com.live.model.ConsultaItemSugestaoCancelProducao;
import br.com.live.model.ConsultaTag;
import br.com.live.model.CorProduto;
import br.com.live.model.Embarque;
import br.com.live.model.MarcacaoRisco;
import br.com.live.model.Produto;
import br.com.live.model.Roteiro;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Service
public class ProdutoService {

	private final ProdutoCustom produtoRepository;
	private final ExpedicaoCustom expedicaoCustom;

	public ProdutoService(ProdutoCustom produtoRepository, ExpedicaoCustom expedicaoCustom) {
		this.produtoRepository = produtoRepository;
		this.expedicaoCustom = expedicaoCustom;
	}
	
	public List<Produto> findProdutosComRoteiroByNiveis(String niveis) {
		return produtoRepository.findProdutosComRoteiroByNiveis(niveis);
	}	
	
	public  List<CorProduto> findCoresByNivelGrupoSub(String nivel, String grupo, String sub) {
		return produtoRepository.findCoresByNivelGrupoSub(nivel, grupo, sub);
	}
	
	public List<ProdutoReferencia> findProdutosByParameters(BodyFiltroProduto filtro) {		
		return produtoRepository.findProdutosByParameters(filtro);		
	}

	public List<CorProduto> findCoresByParameters(BodyFiltroProduto filtro) {		
		return produtoRepository.findCoresByParameters(filtro);		
	}

	public List<ProdutoReferenciaCor> findItensByParameters(BodyFiltroProduto filtro) {		
		return produtoRepository.findItensByParameters(filtro);		
	}

	public List<ConsultaItemSugestaoCancelProducao> findItensSugestaoCancelamentoByParameters(BodyFiltroProduto filtro) {
		return produtoRepository.findItensSugestaoCancelProducaoByParameters(filtro);
	}
	
	public ProdutoReferenciaCor findItemByCodigo(String grupo, String item) {
		return produtoRepository.findItemByCodigo(grupo, item);
	}
	
	public List<Embarque> findEmbarques() {		
		return produtoRepository.findAllEmbarquesBasi();		
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
	
	public boolean existsRoteiro(String nivel, String grupo, String sub, String item, int alternativa, int roteiro) {
		return produtoRepository.existsRoteiro(nivel, grupo, sub, item, alternativa, roteiro);
	}

	public boolean existsRoteiroRequisicao(String nivel, String grupo, String sub, String item, int alternativa, int roteiro) {
		return produtoRepository.existsRoteiroAltRotMaiorZero(nivel, grupo, sub, item, alternativa, roteiro);
	}
	
	public List<Produto> findProdutosByColecaoAndArtigo(List<ConteudoChaveNumerica> colecoes, List<ConteudoChaveNumerica> artigos, int boxFim, int cestoFim) {
		int totalRegistros = boxFim * cestoFim;
		return produtoRepository.findProdutosByColecaoAndArtigo(ConteudoChaveNumerica.parseValueToString(colecoes), ConteudoChaveNumerica.parseValueToString(artigos), totalRegistros);
	}
	
	public boolean existeProduto(String nivel, String grupo, String subGrupo, String item) {
		return produtoRepository.existeProduto(nivel, grupo, subGrupo, item);
	}
	
	public List<ConteudoChaveNumerica> findCategorias() {
		return produtoRepository.findCategorias();
	}

	public ConsultaTag findDadosByTag(String numeroTag) {

		int periodo = Integer.parseInt(numeroTag.substring(0, 4));
		int ordem = Integer.parseInt(numeroTag.substring(4, 13));
		int pacote = Integer.parseInt(numeroTag.substring(13, 18));
		int sequencia = Integer.parseInt(numeroTag.substring(18, 22));

		ConsultaTag dadosTag = produtoRepository.findDadosTagByTagAndDeposito(periodo, ordem, pacote, sequencia);
		
		if ((dadosTag.endereco == null)) {
			dadosTag.endereco = " ";
		} else {
			dadosTag.endereco = dadosTag.endereco + " (" + expedicaoCustom.obterQuantidadeEndereco(dadosTag.endereco, dadosTag.nivel, dadosTag.grupo, dadosTag.subGrupo, dadosTag.item, dadosTag.deposito) + ")";
		}
		
		return dadosTag;
	}
	
	public ConsultaTag findDadosRefByProdutoAndDeposito(String nivel, String grupo, String subGrupo, String item, int deposito) {
		ConsultaTag dadosTag = produtoRepository.findDadosTagByReferencia(deposito, nivel, grupo, subGrupo, item);
		List<ConsultaTag> listEnderecos = expedicaoCustom.obterEnderecos(deposito, nivel, grupo, subGrupo, item);
		
		String enderecosConcat = "";
		
		for (ConsultaTag endereco : listEnderecos) {
			if (enderecosConcat.equals("")) {
				enderecosConcat = endereco.endereco + " (" + expedicaoCustom.obterQuantidadeEndereco(endereco.endereco, nivel, grupo, subGrupo, item, deposito) + ")";
			} else {
				enderecosConcat = enderecosConcat + ", " + endereco.endereco + " (" + expedicaoCustom.obterQuantidadeEndereco(endereco.endereco, nivel, grupo, subGrupo, item, deposito) + ")";
			}
		}
		dadosTag.endereco = enderecosConcat;
		
		return dadosTag;
	}
	
	public Produto findProduto(String nivel, String grupo, String sub, String item) {
		return produtoRepository.findProduto(nivel, grupo, sub, item);
	}
	
	public List<ConteudoChaveAlfaNum> findTamanhosByGrupo(String nivel, String grupo) {
		return produtoRepository.findTamanhosByGrupo(nivel, grupo);
	}
	
	public List<ConteudoChaveAlfaNum> findCoresByTamanho(String nivel, String grupo, String subGrupo) {
		return produtoRepository.findCoresByTamanho(nivel, grupo, subGrupo);
	}
	
	public List<ConteudoChaveAlfaNum> findProdutosByLeitorProduto(String leitor) {
		return produtoRepository.findProdutosByLeitorProduto(leitor);
	}
	
	public List<ConteudoChaveAlfaNum> findGruposByLeitorProduto(String leitor) {
		return produtoRepository.findGruposByLeitorProduto(leitor);
	}
	
	public int atualizaComplemento(List<AtualizaComplementoRef> refsAlterar, int complemento) {
		int quantRefsAlteradas = 0;
		
		for (AtualizaComplementoRef dadosRef : refsAlterar ) {
			if (!dadosRef.referencia.equals("")) {
				quantRefsAlteradas++;
				produtoRepository.atualizaComplemento("1", dadosRef.referencia, dadosRef.tamanho, dadosRef.cor, complemento);
			}
		}
		return quantRefsAlteradas;
	}
	
	public int atualizaComplementoByColecao(int colecao, int complemento) {
		produtoRepository.atualizaComplementoByColecao(colecao, complemento);
		return produtoRepository.findTotalProdutosInColecao(colecao);
	}
}
