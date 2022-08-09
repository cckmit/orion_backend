package br.com.live.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ProdutoCustom;
import br.com.live.custom.TabelaPrecoCustom;
import br.com.live.model.ImportarTabPreco;
import br.com.live.model.RetornoValidacaoTabelaPreco;
import br.com.live.util.ConteudoChaveAlfaNum;

@Service
@Transactional
public class TabelaPrecoService {

	private final TabelaPrecoCustom tabelaPrecoCustom;
	private final ProdutoCustom produtoCustom;

	public TabelaPrecoService(TabelaPrecoCustom tabelaPrecoCustom, ProdutoCustom produtoCustom) {
		this.tabelaPrecoCustom = tabelaPrecoCustom;
		this.produtoCustom = produtoCustom;
	}

	public RetornoValidacaoTabelaPreco validarTabPreco(List<ImportarTabPreco> itensTabela) {
		List<ImportarTabPreco> itensEditados = new ArrayList<ImportarTabPreco>();

		int habilitaExec = 1;
		int existeRef = 0;

		for (ImportarTabPreco item : itensTabela) {
			String corValidar = "Sem Cor";

			if (!item.cor.equalsIgnoreCase("undefined") && (!item.cor.equalsIgnoreCase("0"))) {
				corValidar = item.cor;
			}

			existeRef = tabelaPrecoCustom.validarReferenciaCorCadastrada(item.referencia, corValidar);

			if (existeRef == 1) {
				item.status = 1;
			} else {
				item.status = 0;
				habilitaExec = 0;
			}
			item.cor = corValidar;
			itensEditados.add(item);
		}
		RetornoValidacaoTabelaPreco retorno = new RetornoValidacaoTabelaPreco(itensEditados, habilitaExec);
		return retorno;
	}

	public void atualizarPrecoTabela(List<ImportarTabPreco> itensTabela, String tabelaPreco, int nivelImportacao) {
		List<ConteudoChaveAlfaNum> tamanhos = new ArrayList<ConteudoChaveAlfaNum>();
		String[] tabPrecoConcat = tabelaPreco.split("[.]");

		String coluna = tabPrecoConcat[0];
		String mes = tabPrecoConcat[1];
		String sequencia = tabPrecoConcat[2];

		for (ImportarTabPreco itemPreco : itensTabela) {

			if (nivelImportacao != 2) {
				insereTabelaPreco(Integer.parseInt(coluna), Integer.parseInt(mes), Integer.parseInt(sequencia), 1, "1",
						itemPreco.referencia, "000", "000000", 0, itemPreco.valor);
			}

			if (!itemPreco.cor.equals("Sem Cor")) {
				tamanhos = produtoCustom.findTamanhosByGrupo("1", itemPreco.referencia);
				for (ConteudoChaveAlfaNum tamanho : tamanhos) {
					insereTabelaPreco(Integer.parseInt(coluna), Integer.parseInt(mes), Integer.parseInt(sequencia), 2,
							"1", itemPreco.referencia, tamanho.label, "000000", 0, 0);
					insereTabelaPreco(Integer.parseInt(coluna), Integer.parseInt(mes), Integer.parseInt(sequencia), 4,
							"1", itemPreco.referencia, tamanho.label, itemPreco.cor, 0, itemPreco.valor);
				}
			}
		}
	}

	public void insereTabelaPreco(int coluna, int mes, int sequencia, int nivelPreco, String nivel, String grupo,
			String subGrupo, String item, int serie, float valor) {
		tabelaPrecoCustom.insereTabelaPreco(coluna, mes, sequencia, nivelPreco, nivel, grupo, subGrupo, item, serie,
				valor);
	}
}
