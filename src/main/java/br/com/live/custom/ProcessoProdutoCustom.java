package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.live.entity.ProcessoProduto;
import br.com.live.entity.ProcessoProdutoPlano;
import br.com.live.util.FormataParametrosPlanoMestre;
import br.com.live.util.ParametrosPlanoMestre;

@Repository
public class ProcessoProdutoCustom {

	private final EntityManager manager;

	public ProcessoProdutoCustom(EntityManager manager) {
		this.manager = manager;
	}

	private List<ProcessoProdutoPlano> parseProcessoProdutoPlano(List<ProcessoProduto> listProcessoProduto,
			FormataParametrosPlanoMestre parametrosFormatados) {

		List<ProcessoProdutoPlano> listProcessoProdutoPlano = new ArrayList<ProcessoProdutoPlano>();

		for (ProcessoProduto produto : listProcessoProduto) {

			ProcessoProdutoPlano produtoPlano = new ProcessoProdutoPlano(produto.id, produto.nivel, produto.grupo,
					produto.sub, produto.item, produto.quantidade, produto.periodo,
					parametrosFormatados.getPlanoPeriodoProcesso(produto.periodo), produto.colecao, produto.linha,
					produto.artigo, produto.artigoCotas, produto.origem, produto.permanente, produto.embarque);

			listProcessoProdutoPlano.add(produtoPlano);
		}

		return listProcessoProdutoPlano;
	}

	public List<ProcessoProdutoPlano> findByParameters(ParametrosPlanoMestre parametros) {

		FormataParametrosPlanoMestre parametrosFormatados = new FormataParametrosPlanoMestre(parametros);

		String query = "select new br.com.live.entity.ProcessoProduto (p.id, p.nivel, p.grupo, p.sub, p.item, p.quantidade, p.periodo, p.colecao, p.linha, p.artigo, p.artigoCotas, p.origem, p.permanente, p.embarque) from ProcessoProduto p ";
		String condicao = "where ";

		if (parametrosFormatados.getPeriodoProcessoInicio() > 0) {
			query += condicao + " p.periodo between " + parametrosFormatados.getPeriodoProcessoInicio() + " and "
					+ parametrosFormatados.getPeriodoProcessoFim() + " ";
			condicao = " and ";
		}

		if (!parametrosFormatados.getColecoes().equalsIgnoreCase("")) {
			query += condicao + " p.colecao in " + parametrosFormatados.getColecoes();
			condicao = " and ";
		}

		if (!parametrosFormatados.getLinhasProduto().equalsIgnoreCase("")) {
			query += condicao + " p.linha in " + parametrosFormatados.getLinhasProduto();
			condicao = " and ";
		}

		if (!parametrosFormatados.getArtigosProduto().equalsIgnoreCase("")) {
			query += condicao + " p.artigo in " + parametrosFormatados.getArtigosProduto();
			condicao = " and ";
		}

		if (!parametrosFormatados.getArtigosCotas().equalsIgnoreCase("")) {
			query += condicao + " p.artigoCotas in " + parametrosFormatados.getArtigosCotas();
			condicao = " and ";
		}

		if (!parametrosFormatados.getSubColecoes().equalsIgnoreCase("")) {
			query += condicao + " ( p.permanente = 1 and exists (select 1 from SubColecao s where s.colecao in "
					+ parametrosFormatados.getSubColecoes();
			query += " and s.grupo = p.grupo ";
			query += " and s.sub = p.sub ";
			query += " and s.item = p.item ) or p.permanente = 0 ) ";
			condicao = " and ";
		}

		if (!parametrosFormatados.getPublicosAlvo().equalsIgnoreCase("")) {
			query += condicao + " exists (select 1 from PublicoAlvoProduto pa where pa.grupo = p.grupo ";
			query += " and pa.codigo in " + parametrosFormatados.getPublicosAlvo() + " ) ";
			condicao = " and ";
		}

		if (!parametrosFormatados.getEmbarques().equalsIgnoreCase("")) {
			query += condicao + " p.embarque in " + parametrosFormatados.getEmbarques();
			condicao = " and ";
		}

		if (!parametrosFormatados.getProdutos().equalsIgnoreCase("")) {
			query += condicao + " p.grupo in " + parametrosFormatados.getProdutos();
			condicao = " and ";
		}

		if (!parametrosFormatados.getCores().equalsIgnoreCase("")) {
			query += condicao + " p.item in " + parametrosFormatados.getCores();
			condicao = " and ";
		}

		if (!parametrosFormatados.getOrigemProdutos().equalsIgnoreCase("")) {
			query += condicao + " p.origem in " + parametrosFormatados.getOrigemProdutos();
			condicao = " and ";
		}

		if (!parametrosFormatados.consideraSemProcesso()) {
			query += condicao + " p.quantidade > 0.00 ";
			condicao = " and ";
		}
		
		if (!parametrosFormatados.getPrevisoes().equalsIgnoreCase("")) {
			query += condicao + " exists (select 1 from PrevisaoVendasItem v where v.idPrevisaoVendas in " + parametrosFormatados.getPrevisoes()
	              + " and v.grupo = p.grupo "
			      + " and v.item = p.item)";
		}

		System.out.println("Processos - query: " + query);

		var q = manager.createQuery(query, ProcessoProduto.class);

		return parseProcessoProdutoPlano(q.getResultList(), parametrosFormatados);
	}

}
