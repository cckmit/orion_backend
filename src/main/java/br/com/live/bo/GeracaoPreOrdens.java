package br.com.live.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.live.model.PreOrdemProducao;
import br.com.live.model.PreOrdemProducaoItem;
import br.com.live.model.ProgramacaoPlanoMestre;

public class GeracaoPreOrdens {

	private int agrupaPorReferencia;
	private int qtdeMaximaOrdem;
	private int qtdeMinimaOrdem;
	private List<ProgramacaoPlanoMestre> programacaoItens;
	Map<String, Integer> mapOrdensAgrupadas;

	List<PreOrdemProducao> ordensCapa;
	List<PreOrdemProducaoItem> ordensItens;

	public GeracaoPreOrdens(int agrupaPorReferencia, int qtdeMaximaOrdem, int qtdeMinimaOrdem,
			List<ProgramacaoPlanoMestre> programacaoItens) {

		this.agrupaPorReferencia = agrupaPorReferencia;
		this.qtdeMaximaOrdem = qtdeMaximaOrdem;
		this.qtdeMinimaOrdem = qtdeMinimaOrdem;
		this.programacaoItens = programacaoItens;

		agrupar();
		gerar();

	}

	private String getCodigo(ProgramacaoPlanoMestre item) {

		String codAgrupador;

		if (agrupaPorReferencia > 0)
			codAgrupador = "1" + "." + item.grupo;
		else
			codAgrupador = "2" + "." + item.grupo + "." + item.item;

		codAgrupador += "." + item.alternativa + "." + item.roteiro + "." + item.periodo;

		return codAgrupador;
	}

	private void agrupar() {

		String codAgrupador = "";
		Integer quantidade;

		mapOrdensAgrupadas = new HashMap<String, Integer>();

		for (ProgramacaoPlanoMestre item : programacaoItens) {

			codAgrupador = getCodigo(item);

			quantidade = item.qtdeProgramada;

			if (mapOrdensAgrupadas.containsKey(codAgrupador))
				quantidade = mapOrdensAgrupadas.get(codAgrupador);

			mapOrdensAgrupadas.put(codAgrupador, quantidade);
		}
	}

	private void gerar() {

		Integer quantidade;
		int qtdeOrdens = 0;

		for (String chave : mapOrdensAgrupadas.keySet()) {

			quantidade = mapOrdensAgrupadas.get(chave);

			if (quantidade < qtdeMinimaOrdem)
				continue;

			qtdeOrdens = getNrOPsGerar(quantidade);
			setOrdem(chave, qtdeOrdens);
		}
	}

	private void setOrdem(String chave, int qtdeOrdens) {

		PreOrdemProducao preOrdem;

		String[] inicio = chave.split("[.]");
		String identificador = inicio[0];
		String cor = "";

		if (identificador.equalsIgnoreCase("1")) {
			preOrdem = new PreOrdemProducao();
			preOrdem.grupo = inicio[1];
			preOrdem.alternativa = Integer.parseInt(inicio[2]);
			preOrdem.roteiro = Integer.parseInt(inicio[3]);
			preOrdem.qtdeProgramada = 0;
			setOrdem(1, preOrdem, "", qtdeOrdens);
		} else if (identificador.equalsIgnoreCase("2")) {
			preOrdem = new PreOrdemProducao();
			preOrdem.grupo = inicio[1];
			cor = inicio[2];
			preOrdem.alternativa = Integer.parseInt(inicio[3]);
			preOrdem.roteiro = Integer.parseInt(inicio[4]);
			preOrdem.qtdeProgramada = 0;
			setOrdem(2, preOrdem, cor, qtdeOrdens);
		}
	}

	private void setOrdem(int tipoAgrupamento, PreOrdemProducao preOrdem, String cor, int qtdeOPs) {

		int qtdeTotalProg = 0;

		PreOrdemProducaoItem preOrdemItem;

		for (int i = 0; i < qtdeOPs; i++) {

			preOrdem.id = ordensCapa.size();

			for (ProgramacaoPlanoMestre item : programacaoItens) {

				if (!chaveValida(tipoAgrupamento, preOrdem.grupo, cor, preOrdem.alternativa, preOrdem.roteiro,
						preOrdem.periodo, item))
					continue;

				preOrdemItem = new PreOrdemProducaoItem();
				preOrdemItem.id = preOrdem.id;
				preOrdemItem.grupo = item.grupo;
				preOrdemItem.sub = item.sub;
				preOrdemItem.item = item.item;
				preOrdemItem.alternativa = item.alternativa;
				preOrdemItem.roteiro = item.roteiro;
				preOrdemItem.periodo = item.periodo;
				preOrdemItem.qtdeProgramada = (item.qtdeProgramada / qtdeOPs);

				qtdeTotalProg += item.qtdeProgramada;

				ordensItens.add(preOrdemItem);
			}

			preOrdem.qtdeProgramada = qtdeTotalProg;
			ordensCapa.add(preOrdem);
		}
	}

	private boolean chaveValida(int tipoChave, String grupo, String cor, int alternativa, int roteiro, int periodo,
			ProgramacaoPlanoMestre item) {

		boolean isValida = false;

		if ((tipoChave == 1) && (grupo.equalsIgnoreCase(item.grupo)) && (alternativa == item.alternativa)
				&& (roteiro == item.roteiro) && (periodo == item.periodo))
			isValida = true;

		if ((tipoChave == 2) && (grupo.equalsIgnoreCase(item.grupo)) && (cor.equalsIgnoreCase(item.item))
				&& (alternativa == item.alternativa) && (roteiro == item.roteiro) && (periodo == item.periodo))
			isValida = true;

		return isValida;
	}

	private int getNrOPsGerar(int quantidade) {

		double qtdeOrdem = quantidade;
		double resultado = qtdeOrdem / qtdeMaximaOrdem;
		int qtdeOPs = (int) resultado;
		double sobra = resultado - qtdeOPs;

		if (sobra > 0.000)
			qtdeOPs++;

		return qtdeOPs;
	}
}
