package br.com.live.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.live.entity.PlanoMestrePreOrdem;
import br.com.live.entity.PlanoMestrePreOrdemItem;
import br.com.live.model.PreOrdemProducao;
import br.com.live.model.PreOrdemProducaoItem;
import br.com.live.model.ProgramacaoPlanoMestre;

public class GeracaoPreOrdens {

	private long idPlanoMestre;
	private int agrupaPorReferencia;
	private int qtdeMaximaOrdem;
	private int qtdeMinimaOrdem;
	private int periodoOrdem;
	private int depositoOrdem;
	private String observacaoOrdem;
	private List<ProgramacaoPlanoMestre> programacaoItens;
	Map<String, Integer> mapOrdensAgrupadas;

	List<PreOrdemProducao> ordensCapa;
	List<PreOrdemProducaoItem> ordensItens;

	Map<Integer, PlanoMestrePreOrdem> mapPlanoMestrePreOrdens;
	Map<Integer, PlanoMestrePreOrdemItem> mapPlanoMestrePreOrdensItens;
	List<PlanoMestrePreOrdemItem> listPlanoMestrePreOrdemItem;

	public GeracaoPreOrdens(long idPlanoMestre, int agrupaPorReferencia, int qtdeMaximaOrdem, int qtdeMinimaOrdem,
			int periodoOrdem, int depositoOrdem, String observacaoOrdem,
			List<ProgramacaoPlanoMestre> programacaoItens) {

		this.idPlanoMestre = idPlanoMestre;
		this.agrupaPorReferencia = agrupaPorReferencia;
		this.qtdeMaximaOrdem = qtdeMaximaOrdem;
		this.qtdeMinimaOrdem = qtdeMinimaOrdem;
		this.periodoOrdem = periodoOrdem;
		this.depositoOrdem = depositoOrdem;
		this.observacaoOrdem = observacaoOrdem;
		this.programacaoItens = programacaoItens;

		agruparOrdens();
		gerarPreOrdens();
		putMapPreOrdens();
	}

	public Map<Integer, PlanoMestrePreOrdem> getMapPreOrdens() {
		return mapPlanoMestrePreOrdens;
	}

	public List<PlanoMestrePreOrdemItem> getListPreOrdemItens(Integer idMapPreOrdem) {

		List<PlanoMestrePreOrdemItem> listPlanoMestrePreOrdemItem = new ArrayList<PlanoMestrePreOrdemItem>();

		for (PreOrdemProducaoItem ordemItem : ordensItens) {
			if (ordemItem.id != idMapPreOrdem)
				continue;

			PlanoMestrePreOrdemItem planoMestrePreOrdemItem = new PlanoMestrePreOrdemItem();
			planoMestrePreOrdemItem.idPlanoMestre = idPlanoMestre;
			planoMestrePreOrdemItem.sub = ordemItem.sub;
			planoMestrePreOrdemItem.item = ordemItem.item;
			planoMestrePreOrdemItem.quantidade = ordemItem.qtdeProgramada;

			listPlanoMestrePreOrdemItem.add(planoMestrePreOrdemItem);
		}

		return listPlanoMestrePreOrdemItem;
	}

	private void putMapPreOrdens() {

		mapPlanoMestrePreOrdens = new HashMap<Integer, PlanoMestrePreOrdem>();

		for (PreOrdemProducao ordemCapa : ordensCapa) {

			PlanoMestrePreOrdem planoMestrePreOrdem = new PlanoMestrePreOrdem();

			planoMestrePreOrdem.idPlanoMestre = idPlanoMestre;
			planoMestrePreOrdem.grupo = ordemCapa.grupo;
			planoMestrePreOrdem.periodo = ordemCapa.periodo;
			planoMestrePreOrdem.alternativa = ordemCapa.alternativa;
			planoMestrePreOrdem.roteiro = ordemCapa.roteiro;
			planoMestrePreOrdem.quantidade = ordemCapa.qtdeProgramada;
			planoMestrePreOrdem.data = new Date();
			planoMestrePreOrdem.deposito = depositoOrdem;
			planoMestrePreOrdem.observacao = observacaoOrdem;
			planoMestrePreOrdem.situacao = 0;

			mapPlanoMestrePreOrdens.put(ordemCapa.id, planoMestrePreOrdem);
		}
	}

	private String getChaveAgrupadora(ProgramacaoPlanoMestre item) {

		String codAgrupador;

		if (agrupaPorReferencia > 0)
			codAgrupador = "1" + "." + item.grupo;
		else
			codAgrupador = "2" + "." + item.grupo + "." + item.item;

		if (periodoOrdem > 0)
			item.periodo = periodoOrdem;

		codAgrupador += "." + item.alternativa + "." + item.roteiro + "." + item.periodo;

		return codAgrupador;
	}

	private void agruparOrdens() {

		String codAgrupador = "";
		Integer quantidade;

		mapOrdensAgrupadas = new HashMap<String, Integer>();

		for (ProgramacaoPlanoMestre item : programacaoItens) {

			if (item.qtdeProgramada <= 0)
				continue;

			codAgrupador = getChaveAgrupadora(item);

			quantidade = item.qtdeProgramada;

			if (mapOrdensAgrupadas.containsKey(codAgrupador))
				quantidade += mapOrdensAgrupadas.get(codAgrupador);

			mapOrdensAgrupadas.put(codAgrupador, quantidade);
		}
	}

	private void gerarPreOrdens() {

		Integer quantidade;

		ordensCapa = new ArrayList<PreOrdemProducao>();
		ordensItens = new ArrayList<PreOrdemProducaoItem>();

		for (String chave : mapOrdensAgrupadas.keySet()) {

			quantidade = mapOrdensAgrupadas.get(chave);

			if (quantidade < qtdeMinimaOrdem)
				continue;
			
			setOrdem(chave);
		}
	}

	private void setOrdem(String chave) {

		String grupo = "";
		String cor = "";
		int alternativa = 0;
		int roteiro = 0;
		int periodo = 0;

		String[] inicio = chave.split("[.]");
		String identificador = inicio[0];

		if (identificador.equalsIgnoreCase("1")) {
			grupo = inicio[1];
			alternativa = Integer.parseInt(inicio[2]);
			roteiro = Integer.parseInt(inicio[3]);
			periodo = Integer.parseInt(inicio[4]);
			setOrdem(1, grupo, cor, periodo, alternativa, roteiro);
		} else if (identificador.equalsIgnoreCase("2")) {
			grupo = inicio[1];
			cor = inicio[2];
			alternativa = Integer.parseInt(inicio[3]);
			roteiro = Integer.parseInt(inicio[4]);
			periodo = Integer.parseInt(inicio[5]);
			setOrdem(2, grupo, cor, periodo, alternativa, roteiro);
		}
	}

	private void setOrdem(int tipoAgrupamento, String grupo, String cor, int periodo, int alternativa, int roteiro) {

		int qtdeTotalProg = 0;
		int qtdeProgramada = 0;
		int qtdeProgramar = 0;		
		PreOrdemProducaoItem preOrdemItem;
		List<PreOrdemProducaoItem> listaItens = new ArrayList<PreOrdemProducaoItem>();

		for (ProgramacaoPlanoMestre item : programacaoItens) {

			if ((!chaveValida(tipoAgrupamento, grupo, cor, alternativa, roteiro, periodo, item))
					|| (item.qtdeProgramada == 0))
				continue;

			qtdeProgramada = item.qtdeProgramada;
			qtdeProgramar = 0;			

			while (qtdeProgramada > 0) {

				if ((qtdeTotalProg + qtdeProgramada) <= qtdeMaximaOrdem)
					qtdeProgramar = qtdeProgramada;
				else {
					qtdeProgramar = qtdeProgramada - ((qtdeTotalProg + qtdeProgramada) - qtdeMaximaOrdem);					

					if (qtdeProgramar <= 0) {

						if (listaItens.size() > 0) {
							populaListOrdens(grupo, cor, periodo, alternativa, roteiro, listaItens);
						}

						listaItens.clear();
						qtdeTotalProg = 0;
					}
				}

				if (qtdeProgramar <= 0)
					continue;

				preOrdemItem = new PreOrdemProducaoItem();
				preOrdemItem.grupo = item.grupo;
				preOrdemItem.sub = item.sub;
				preOrdemItem.item = item.item;
				preOrdemItem.alternativa = item.alternativa;
				preOrdemItem.roteiro = item.roteiro;
				preOrdemItem.periodo = item.periodo;
				preOrdemItem.qtdeProgramada = qtdeProgramar;

				qtdeTotalProg += qtdeProgramar;
				qtdeProgramada -= qtdeProgramar;
				
				listaItens.add(preOrdemItem);
			}
		}

		if (listaItens.size() > 0) {
			populaListOrdens(grupo, cor, periodo, alternativa, roteiro, listaItens);
		}
	}

	private void populaListOrdens(String grupo, String cor, int periodo, int alternativa, int roteiro,
			List<PreOrdemProducaoItem> listaItens) {

		int qtdeTotalProg = 0;
		int idOrdem = 0;

		idOrdem = getIdPreOrdem();

		PreOrdemProducao preOrdem = new PreOrdemProducao();
		preOrdem.id = idOrdem;
		preOrdem.grupo = grupo;
		preOrdem.periodo = periodo;
		preOrdem.alternativa = alternativa;
		preOrdem.roteiro = roteiro;

		for (PreOrdemProducaoItem item : listaItens) {

			PreOrdemProducaoItem preOrdemItem;

			preOrdemItem = new PreOrdemProducaoItem();
			preOrdemItem.id = idOrdem;
			preOrdemItem.grupo = item.grupo;
			preOrdemItem.sub = item.sub;
			preOrdemItem.item = item.item;
			preOrdemItem.alternativa = item.alternativa;
			preOrdemItem.roteiro = item.roteiro;
			preOrdemItem.periodo = item.periodo;
			preOrdemItem.qtdeProgramada = item.qtdeProgramada;

			qtdeTotalProg += preOrdemItem.qtdeProgramada;

			ordensItens.add(preOrdemItem);
		}

		preOrdem.qtdeProgramada = qtdeTotalProg;
		ordensCapa.add(preOrdem);
	}

	private int getIdPreOrdem() {
		return (ordensCapa.size() + 1);
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
}
