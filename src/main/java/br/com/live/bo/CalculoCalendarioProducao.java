package br.com.live.bo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.live.entity.ParametrosCalendario;
import br.com.live.model.EstagiosConfigCalend;
import br.com.live.model.PeriodoProducaoArea;
import br.com.live.util.ConvertePeriodo;
import br.com.live.util.FormataData;
import br.com.live.model.CalendarioEstagiosProducao;
import br.com.live.model.CalendarioPeriodoProducao;

public class CalculoCalendarioProducao {
	
	public static List<CalendarioPeriodoProducao> geracaoCalendario(int anoCalendario, ParametrosCalendario parametrosCalendario, List<EstagiosConfigCalend> estagios) {

		int estagioSet = 0;
		Date dataInicioSet = null;
		Date dataFimSet = null;
		int leadSet = 0;
		EstagiosConfigCalend estMontagem = null;

		Map<Integer, EstagiosConfigCalend> estagiosAnteriores = new HashMap<Integer, EstagiosConfigCalend> ();
		
		List<CalendarioPeriodoProducao> periodosGeracao = new ArrayList<CalendarioPeriodoProducao>();

		int contador = parametrosCalendario.periodoInicio;

		while (contador <= parametrosCalendario.periodoFim) {

			List<CalendarioEstagiosProducao> estagiosGeracao = new ArrayList<CalendarioEstagiosProducao>();

			for (EstagiosConfigCalend estagio : estagios) {

				EstagiosConfigCalend estagioAnterior = estagiosAnteriores.get(estagio.getEstagio());

				if (estagio.getEstagio() == 20) {
					estMontagem = estagiosAnteriores.get(97);
				}

				if (estagioAnterior == null) {
					estagioSet = estagio.getEstagio();
					dataInicioSet = estagio.getDataInicioDate();
					dataFimSet = estagio.getDataFimDate();
					leadSet = estagio.getLead();
				} else {
					estagioSet = estagio.getEstagio();
					leadSet = estagio.getLead();

					if ((estagio.getEstagio() == 20) && (estMontagem != null)) {
						dataInicioSet = retornaDiaUtil(estMontagem.getDataInicioDate(), 1, parametrosCalendario.consideraSabado,
								parametrosCalendario.consideraDomingo);
						dataFimSet = retornaDiaUtil(estMontagem.getDataInicioDate(), estagio.getLead() + 1, parametrosCalendario.consideraSabado,
								parametrosCalendario.consideraDomingo);
					} else {
						dataInicioSet = retornaDiaUtil(estagioAnterior.getDataFimDate(), 1, parametrosCalendario.consideraSabado,
								parametrosCalendario.consideraDomingo);
						dataFimSet = retornaDiaUtil(dataInicioSet, estagio.getLead(), parametrosCalendario.consideraSabado,
								parametrosCalendario.consideraDomingo);
					}

					estagio.setDataFimDate(dataFimSet);
					estagio.setDataInicioDate(dataInicioSet);
				}

				CalendarioEstagiosProducao dadosGeracao = new CalendarioEstagiosProducao(estagioSet, dataInicioSet,
						dataFimSet, FormataData.getDayOfWeek(dataInicioSet), leadSet, FormataData.getDayOfWeek(dataFimSet));

				estagiosGeracao.add(dadosGeracao);
				estagiosAnteriores.put(estagio.getEstagio(), estagio);
			}

			CalendarioPeriodoProducao dadosPeriodos = new CalendarioPeriodoProducao(contador, estagiosGeracao);

			periodosGeracao.add(dadosPeriodos);

			contador++;
		}

		return periodosGeracao;
	}

	private static Date retornaDiaUtil(Date dataAlteracao, int lead, boolean consideraSabado, boolean consideraDomingo) {

		Date dataEditada = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataAlteracao);
		calendar.add(Calendar.DATE, +lead);

		dataEditada = calendar.getTime();
		calendar.setTime(dataEditada);

		int day = calendar.get(Calendar.DAY_OF_WEEK);

		if ((day == 1) && (consideraDomingo == false)) {
			calendar.add(Calendar.DATE, +1);
		}

		if ((day == 7) && (consideraSabado == false)) {
			calendar.add(Calendar.DATE, +2);
		}

		return calendar.getTime();
	}
	
	private static PeriodoProducaoArea calcularArea01Confeccao(int empresa, CalendarioPeriodoProducao periodo, int quinzena) {
		CalendarioEstagiosProducao estagioInicial = periodo.estagios.get(0);
		CalendarioEstagiosProducao estagioFinal = periodo.estagios.get(periodo.estagios.size()-1);
		
		Date dataInicioProducao = estagioInicial.dataInicio;
		Date dataFimProducao = estagioFinal.dataFim;		
		Date dataInicioFaturamento = FormataData.getNextDay(FormataData.SEGUNDA, dataFimProducao);
		Date dataFimFaturamento = FormataData.getNextDay(FormataData.SEXTA, dataInicioFaturamento);
		
		return new PeriodoProducaoArea(ConvertePeriodo.parse(periodo.codigoPeriodo, empresa), 1, empresa, dataInicioProducao, dataFimProducao, dataInicioFaturamento, dataFimFaturamento, dataFimProducao, quinzena);
	}
	
	private static PeriodoProducaoArea calcularArea06Vendas(int empresa, PeriodoProducaoArea periodoArea01, int quinzena) {
		
		Date dataFimProducao = periodoArea01.getDataProdInicio();
		Date dataInicioProducao = FormataData.getSumDate(dataFimProducao, -6);
		Date dataFimFaturamento = dataFimProducao;
		Date dataInicioFaturamento = dataInicioProducao;
		
		return new PeriodoProducaoArea(ConvertePeriodo.parse(periodoArea01.getPeriodo(), empresa), 6, empresa, dataInicioProducao, dataFimProducao, dataInicioFaturamento, dataFimFaturamento, dataFimProducao, quinzena);
	}

	private static PeriodoProducaoArea calcularArea09Compras(int empresa, PeriodoProducaoArea periodoArea06, int quinzena) {
		
		Date dataFimProducao = FormataData.getSumDate(periodoArea06.getDataProdInicio(), -3);
		Date dataInicioProducao = FormataData.getPreviousDay(FormataData.SEGUNDA, dataFimProducao);
		Date dataFimFaturamento = dataFimProducao;
		Date dataInicioFaturamento = dataInicioProducao;
		
		return new PeriodoProducaoArea(ConvertePeriodo.parse(periodoArea06.getPeriodo(), empresa), 9, empresa, dataInicioProducao, dataFimProducao, dataInicioFaturamento, dataFimFaturamento, dataFimProducao, quinzena);
	}

	private static PeriodoProducaoArea calcularArea02Beneficiamento(int empresa, PeriodoProducaoArea periodoArea09, int quinzena) {
		
		Date dataFimFaturamento = periodoArea09.getDataFatFim();
		Date dataInicioFaturamento = periodoArea09.getDataFatInicio();
		Date dataFimProducao = FormataData.getSumDate(dataInicioFaturamento, -3);
		Date dataInicioProducao = FormataData.getSumDate(dataFimProducao, -31);
		
		return new PeriodoProducaoArea(ConvertePeriodo.parse(periodoArea09.getPeriodo(), empresa), 2, empresa, dataInicioProducao, dataFimProducao, dataInicioFaturamento, dataFimFaturamento, dataFimProducao, quinzena);
	}

	private static PeriodoProducaoArea calcularArea04Tecelagem(int empresa, PeriodoProducaoArea periodoArea02, int quinzena) {
		
		Date dataFimFaturamento = FormataData.getSumDate(periodoArea02.getDataProdInicio(), -1);
		Date dataInicioFaturamento = FormataData.getSumDate(dataFimFaturamento, -7);		
		Date dataFimProducao = FormataData.getSumDate(dataInicioFaturamento, -7);
		Date dataInicioProducao = FormataData.getSumDate(dataFimProducao, -7);
		
		return new PeriodoProducaoArea(ConvertePeriodo.parse(periodoArea02.getPeriodo(), empresa), 4, empresa, dataInicioProducao, dataFimProducao, dataInicioFaturamento, dataFimFaturamento, dataFimProducao, quinzena);
	}

	private static PeriodoProducaoArea calcularArea07Fios(int empresa, PeriodoProducaoArea periodoArea04, int quinzena) {
		
		Date dataFimFaturamento = FormataData.getSumDate(periodoArea04.getDataProdInicio(), -1);
		Date dataInicioFaturamento = FormataData.getSumDate(dataFimFaturamento, -7);		
		Date dataFimProducao = FormataData.getSumDate(dataInicioFaturamento, -1);
		Date dataInicioProducao = FormataData.getSumDate(dataFimProducao, -20);
		
		return new PeriodoProducaoArea(ConvertePeriodo.parse(periodoArea04.getPeriodo(), empresa), 7, empresa, dataInicioProducao, dataFimProducao, dataInicioFaturamento, dataFimFaturamento, dataFimProducao, quinzena);
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<Integer, Map> calcularPeriodoAreas(int empresa, List<CalendarioPeriodoProducao> periodos) {		
		Map<Integer, PeriodoProducaoArea> mapAreaPeriodo;	
		Map<Integer, Map> mapPeriodos = new HashMap<Integer, Map>();
		
		PeriodoProducaoArea periodoArea01Confeccao;
		PeriodoProducaoArea periodoArea06Vendas;
		PeriodoProducaoArea periodoArea09Compras;
		PeriodoProducaoArea periodoArea02Beneficiamento;
		PeriodoProducaoArea periodoArea04Tecelagem;
		PeriodoProducaoArea periodoArea07Fios;
		int contador = 0;
		int quinzena = 1;
		
		for (CalendarioPeriodoProducao periodo : periodos) {
			contador++;						
			periodoArea01Confeccao = calcularArea01Confeccao(empresa, periodo, quinzena);
			periodoArea06Vendas = calcularArea06Vendas(empresa, periodoArea01Confeccao, quinzena);
			periodoArea09Compras = calcularArea09Compras(empresa, periodoArea06Vendas, quinzena);
			periodoArea02Beneficiamento = calcularArea02Beneficiamento(empresa, periodoArea09Compras, quinzena);
			periodoArea04Tecelagem = calcularArea04Tecelagem(empresa, periodoArea02Beneficiamento, quinzena);
			periodoArea07Fios = calcularArea07Fios(empresa, periodoArea04Tecelagem, quinzena);		
			
			mapAreaPeriodo = new HashMap<Integer, PeriodoProducaoArea>();
			mapAreaPeriodo.put(1, periodoArea01Confeccao);
			mapAreaPeriodo.put(6, periodoArea06Vendas);
			mapAreaPeriodo.put(9, periodoArea09Compras);
			mapAreaPeriodo.put(2, periodoArea02Beneficiamento);
			mapAreaPeriodo.put(4, periodoArea04Tecelagem);
			mapAreaPeriodo.put(7, periodoArea07Fios);
			
			mapPeriodos.put(ConvertePeriodo.parse(periodo.codigoPeriodo, empresa), mapAreaPeriodo);
			if (contador % 2 == 0) quinzena++;
		}
		
		return mapPeriodos;
	}
	
	public static boolean existsAreaEmpresa(int empresa, int area) {		
		if ((empresa == 1) && (area == 1 || area == 2 || area == 4 || area == 6 || area == 7 || area == 9)) return true; 
		if ((empresa == 100) && (area == 1 || area == 6 || area == 9)) return true;
		if ((empresa == 500) && (area == 1 || area == 2 || area == 7 || area == 9)) return true;
		if ((empresa == 600) && (area == 2 || area == 4 || area == 7 || area == 9)) return true;		
		return false;
	}
}
