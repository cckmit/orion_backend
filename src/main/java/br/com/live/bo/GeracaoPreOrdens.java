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
	private String observacaoOrdem;
	private List<ProgramacaoPlanoMestre> programacaoItens;
	Map<String, Integer> mapOrdensAgrupadas;

	List<PreOrdemProducao> ordensCapa;
	List<PreOrdemProducaoItem> ordensItens;

	Map<Integer, PlanoMestrePreOrdem> mapPlanoMestrePreOrdens;
	Map<Integer, PlanoMestrePreOrdemItem> mapPlanoMestrePreOrdensItens;
	List<PlanoMestrePreOrdemItem> listPlanoMestrePreOrdemItem;

	public GeracaoPreOrdens(long idPlanoMestre, int agrupaPorReferencia, int qtdeMaximaOrdem, int qtdeMinimaOrdem,
			int periodoOrdem, String observacaoOrdem, List<ProgramacaoPlanoMestre> programacaoItens) {

		this.idPlanoMestre = idPlanoMestre;
		this.agrupaPorReferencia = agrupaPorReferencia;
		this.qtdeMaximaOrdem = qtdeMaximaOrdem;
		this.qtdeMinimaOrdem = qtdeMinimaOrdem;
		this.periodoOrdem = periodoOrdem;
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
			planoMestrePreOrdem.observacao = observacaoOrdem;
			planoMestrePreOrdem.deposito = 0;
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
		int qtdeOrdens = 0;

		ordensCapa = new ArrayList<PreOrdemProducao>();
		ordensItens = new ArrayList<PreOrdemProducaoItem>();

		for (String chave : mapOrdensAgrupadas.keySet()) {

			quantidade = mapOrdensAgrupadas.get(chave);

			if (quantidade < qtdeMinimaOrdem)
				continue;

			qtdeOrdens = getNrOPsGerar(quantidade);
			setOrdem(chave, qtdeOrdens);
		}
	}

	private void setOrdem(String chave, int qtdeOrdens) {

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
			setOrdem(1, grupo, cor, periodo, alternativa, roteiro, qtdeOrdens);			
		} else if (identificador.equalsIgnoreCase("2")) {			
			grupo = inicio[1];
			cor = inicio[2];
			alternativa = Integer.parseInt(inicio[3]);
			roteiro = Integer.parseInt(inicio[4]);
			periodo = Integer.parseInt(inicio[5]);
			setOrdem(2, grupo, cor, periodo, alternativa, roteiro, qtdeOrdens);
		}
	}

	private void setOrdem(int tipoAgrupamento, String grupo, String cor, int periodo, int alternativa, int roteiro, int qtdeOPs) {

		int qtdeTotalProg = 0;

		PreOrdemProducao preOrdem;
		PreOrdemProducaoItem preOrdemItem;

		for (int i = 0; i < qtdeOPs; i++) {

			qtdeTotalProg = 0;
			
			preOrdem = new PreOrdemProducao();			
			preOrdem.id = getIdPreOrdem();
			preOrdem.grupo = grupo;
			preOrdem.periodo = periodo;
			preOrdem.alternativa = alternativa;
			preOrdem.roteiro = roteiro;

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

				qtdeTotalProg += preOrdemItem.qtdeProgramada;

				ordensItens.add(preOrdemItem);
			}

			preOrdem.qtdeProgramada = qtdeTotalProg;
			ordensCapa.add(preOrdem);
		}
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

	private int getNrOPsGerar(int quantidade) {

		if (quantidade <= 0)
			return 1;

		double qtdeOrdem = quantidade;
		double resultado = qtdeOrdem / qtdeMaximaOrdem;
		int qtdeOPs = (int) resultado;
		double sobra = resultado - qtdeOPs;

		if (sobra > 0.000)
			qtdeOPs++;

		return qtdeOPs;
	}
		
}
