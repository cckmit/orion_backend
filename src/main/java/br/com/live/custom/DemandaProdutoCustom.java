package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.live.entity.DemandaProduto;
import br.com.live.entity.DemandaProdutoPlano;
import br.com.live.util.FormataParametrosPlanoMestre;
import br.com.live.util.ParametrosPlanoMestre;

@Repository
public class DemandaProdutoCustom {

	private final EntityManager manager;

	public DemandaProdutoCustom(EntityManager manager) {
		this.manager = manager;
	}

	private List<DemandaProdutoPlano> parseDemandaProdutoPlano(List<DemandaProduto> listDemandaProduto,
			FormataParametrosPlanoMestre parametrosFormatados) {

		List<DemandaProdutoPlano> listDemandaProdutoPlano = new ArrayList<DemandaProdutoPlano>();

		for (DemandaProduto produto : listDemandaProduto) {

			DemandaProdutoPlano produtoPlano = new DemandaProdutoPlano(produto.id, produto.nivel, produto.grupo,
					produto.sub, produto.item, produto.quantidade, produto.periodo,
					parametrosFormatados.getPlanoPeriodoDemanda(produto.periodo), produto.colecao, produto.linha,
					produto.artigo, produto.artigoCotas, produto.origem, produto.permanente, produto.natureza,
					produto.nrInterno, produto.pedido, produto.embarque, produto.situacaoVenda);

			listDemandaProdutoPlano.add(produtoPlano);
		}

		return listDemandaProdutoPlano;
	}

	public List<DemandaProdutoPlano> findByParameters(ParametrosPlanoMestre parametros) {

		FormataParametrosPlanoMestre parametrosFormatados = new FormataParametrosPlanoMestre(parametros);

		String query = "select new br.com.live.entity.DemandaProduto (d.id, d.nivel, d.grupo, d.sub, d.item, d.quantidade, d.periodo, d.colecao, d.linha, d.artigo, d.artigoCotas, d.origem, d.permanente, d.natureza, d.nrInterno, d.pedido, d.embarque, d.situacaoVenda) from DemandaProduto d ";
		String condicao = "where ";

		if (parametrosFormatados.getPeriodoDemandaInicio() > 0) {
			query += condicao + " d.periodo between " + parametrosFormatados.getPeriodoDemandaInicio() + " and "
					+ parametrosFormatados.getPeriodoDemandaFim() + " ";
			condicao = " and ";
		}

		if (!parametrosFormatados.getPedidos().equalsIgnoreCase("")) {
			query += condicao + " d.pedido in " + parametrosFormatados.getPedidos();
			condicao = " and ";
		}
		
		if (!parametrosFormatados.getNaturezas().equalsIgnoreCase("")) {
			query += condicao + " d.natureza in " + parametrosFormatados.getNaturezas();
			condicao = " and ";
		}		

		if (!parametrosFormatados.getColecoes().equalsIgnoreCase("")) {
			query += condicao + " d.colecao in " + parametrosFormatados.getColecoes();
			condicao = " and ";
		}

		if (!parametrosFormatados.getLinhasProduto().equalsIgnoreCase("")) {
			query += condicao + " d.linha in " + parametrosFormatados.getLinhasProduto();
			condicao = " and ";
		}

		if (!parametrosFormatados.getArtigosProduto().equalsIgnoreCase("")) {
			query += condicao + " d.artigo in " + parametrosFormatados.getArtigosProduto();
			condicao = " and ";
		}

		if (!parametrosFormatados.getArtigosCotas().equalsIgnoreCase("")) {
			query += condicao + " d.artigoCotas in " + parametrosFormatados.getArtigosCotas();
			condicao = " and ";
		}

		if (!parametrosFormatados.getSubColecoes().equalsIgnoreCase("")) {
			query += condicao + " ( d.permanente = 1 and exists (select 1 from SubColecao s where s.colecao in "
					+ parametrosFormatados.getSubColecoes();
			query += " and s.grupo = d.grupo ";
			query += " and s.sub = d.sub ";
			query += " and s.item = d.item ) or d.permanente = 0 ) ";
			condicao = " and ";
		}

		if (!parametrosFormatados.getPublicosAlvo().equalsIgnoreCase("")) {
			query += condicao + " exists (select 1 from PublicoAlvoProduto pa where pa.grupo = d.grupo ";
			query += " and pa.codigo in " + parametrosFormatados.getPublicosAlvo() + " ) ";
			condicao = " and ";
		}

		if (!parametrosFormatados.getProdutos().equalsIgnoreCase("")) {
			query += condicao + " d.grupo in " + parametrosFormatados.getProdutos();
			condicao = " and ";
		}
				
		if (!parametrosFormatados.getEmbarques().equalsIgnoreCase("")) {
			query += condicao + " d.embarque in " + parametrosFormatados.getEmbarques();
			condicao = " and ";
		}
		
		if (!parametrosFormatados.getCores().equalsIgnoreCase("")) {
			query += condicao + " d.item in " + parametrosFormatados.getCores();
			condicao = " and ";
		}

		if (!parametrosFormatados.getOrigemProdutos().equalsIgnoreCase("")) {
			query += condicao + " d.origem in " + parametrosFormatados.getOrigemProdutos();
			condicao = " and ";
		}

		if (parametrosFormatados.getNrInternoPedido() > 0) {
			query += condicao + " d.nrInterno = " + parametrosFormatados.getNrInternoPedido();
			condicao = " and ";
		}		
		
		if (!parametrosFormatados.consideraPedBloqueados()) {
			query += condicao + " d.situacaoVenda = 0 " ;
			condicao = " and ";			
		}
		
		if (!parametrosFormatados.consideraSemDemanda()) {
			query += condicao + " d.quantidade > 0.00 ";
		}

		System.out.println("Demandas - query: " + query);
 
		var q = manager.createQuery(query, DemandaProduto.class);

		return parseDemandaProdutoPlano(q.getResultList(), parametrosFormatados);
	}

}
