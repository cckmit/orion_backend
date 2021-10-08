package br.com.live.bo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.live.entity.ParametrosCalendario;
import br.com.live.model.EstagiosConfigCalend;
import br.com.live.model.CalendarioEstagiosProducao;
import br.com.live.model.CalendarioPeriodoProducao;

public class CalculoCalendarioProducao {
	
	public static List<CalendarioPeriodoProducao> geracaoCalendario(int anoCalendario, ParametrosCalendario parametrosCalendario, List<EstagiosConfigCalend> estagios) {

		int estagioSet = 0;
		Date dataInicioSet = null;
		Date dataFimSet = null;
		int leadSet = 0;

		Map<Integer, EstagiosConfigCalend> estagiosAnteriores = new HashMap<Integer, EstagiosConfigCalend> ();
		
		List<CalendarioPeriodoProducao> periodosGeracao = new ArrayList<CalendarioPeriodoProducao>();

		int contador = parametrosCalendario.periodoInicio;

		while (contador <= parametrosCalendario.periodoFim) {

			List<CalendarioEstagiosProducao> estagiosGeracao = new ArrayList<CalendarioEstagiosProducao>();

			for (EstagiosConfigCalend estagio : estagios) {
				
				EstagiosConfigCalend estagioAnterior = estagiosAnteriores.get(estagio.getEstagio());
				
				if (estagioAnterior == null) {
					estagioSet = estagio.getEstagio();
					dataInicioSet = estagio.getDataInicioDate();
					dataFimSet = estagio.getDataFimDate();
					leadSet = estagio.getLead();
				} else {
					estagioSet = estagio.getEstagio();
					leadSet = estagio.getLead();
					dataInicioSet = retornaDiaUtil(estagioAnterior.getDataFimDate(), 1, parametrosCalendario.consideraSabado,
							parametrosCalendario.consideraDomingo);
					dataFimSet = retornaDiaUtil(dataInicioSet, estagio.getLead(), parametrosCalendario.consideraSabado,
							parametrosCalendario.consideraDomingo);
					
					
					estagio.setDataFimDate(dataFimSet);
					estagio.setDataInicioDate(dataInicioSet);
				}

				CalendarioEstagiosProducao dadosGeracao = new CalendarioEstagiosProducao(estagioSet, dataInicioSet,
						dataFimSet, retornaDiaSemana(dataInicioSet), leadSet, retornaDiaSemana(dataFimSet));

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

	private static int retornaDiaSemana(Date data) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		int day = calendar.get(Calendar.DAY_OF_WEEK);

		return day;
	}
}
