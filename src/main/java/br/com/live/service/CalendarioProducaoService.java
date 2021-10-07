package br.com.live.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.bo.CalculoCalendarioProducao;
import br.com.live.custom.CalendarioProducaoCustom;
import br.com.live.entity.ConfiguracaoEstagios;
import br.com.live.entity.EstagiosParametros;
import br.com.live.entity.ParametrosCalendario;
import br.com.live.model.EstagiosConfigCalend;
import br.com.live.model.EstagiosConfigurados;
import br.com.live.model.GeracaoCalendarioEstagios;
import br.com.live.model.GeracaoCalendarioPeriodos;
import br.com.live.model.PeriodoProducao;
import br.com.live.repository.ConfigEstagiosRepository;
import br.com.live.repository.EstagiosParametrosRepository;
import br.com.live.repository.ParametrosCalendarioRepository;
import br.com.live.util.FormataData;
import br.com.live.util.LayoutCabecalhoCalendProd;
import br.com.live.util.LayoutCalendarioProducao;
import br.com.live.util.LayoutCorpoCalendProd;

@Service
@Transactional
public class CalendarioProducaoService {

	private final ConfigEstagiosRepository configEstagiosRepository;
	private final CalendarioProducaoCustom calendarioProducaoCustom;
	private final EstagiosParametrosRepository estagiosParametrosRepository;
	private final ParametrosCalendarioRepository parametrosCalendarioRepository;
	
	public CalendarioProducaoService(ConfigEstagiosRepository configEstagiosRepository,
			CalendarioProducaoCustom calendarioProducaoCustom,
			EstagiosParametrosRepository estagiosParametrosRepository,
			ParametrosCalendarioRepository parametrosCalendarioRepository) {
		this.configEstagiosRepository = configEstagiosRepository;
		this.calendarioProducaoCustom = calendarioProducaoCustom;
		this.estagiosParametrosRepository = estagiosParametrosRepository;
		this.parametrosCalendarioRepository = parametrosCalendarioRepository;
	}

	public void salvarConfiguracaoEstagios(List<ConfiguracaoEstagios> estagios) {

		configEstagiosRepository.deleteAll();

		for (ConfiguracaoEstagios dadosEstagio : estagios) {

			ConfiguracaoEstagios estagioSave = new ConfiguracaoEstagios(dadosEstagio.sequencia, dadosEstagio.estagio,
					dadosEstagio.lead);

			configEstagiosRepository.save(estagioSave);
		}

	}

	public List<EstagiosConfigurados> findAllEstagiosConfig() {
		return calendarioProducaoCustom.findEstagiosConfigurados();
	}

	public List<EstagiosConfigurados> retornaDadosGridAbaDadosGerais(int anoCalendario) {

		List<EstagiosConfigurados> estagiosParam = calendarioProducaoCustom.findEstagiosParametro(anoCalendario);

		if (estagiosParam.isEmpty()) {
			estagiosParam = calendarioProducaoCustom.findEstagiosConfigurados();

			for (EstagiosConfigurados dadosEstagio : estagiosParam) {

				EstagiosParametros estagiosSalvos = new EstagiosParametros(dadosEstagio.getSequencia(), anoCalendario,
						dadosEstagio.getEstagio(), dadosEstagio.getLead(), null, null);
				ParametrosCalendario parametrosCalendario = new ParametrosCalendario(anoCalendario, 0, 0, false, false,
						false);

				estagiosParametrosRepository.save(estagiosSalvos);
				parametrosCalendarioRepository.save(parametrosCalendario);
			}
		}
		return estagiosParam;
	}

	public EstagiosConfigurados retornaDadosSequencia(int anoCalendario, int sequencia) {

		EstagiosConfigurados dadosEstagioParam = calendarioProducaoCustom.findSequenciaEstagiosParametro(anoCalendario,
				sequencia);

		if (dadosEstagioParam.getSequencia() == 0) {
			dadosEstagioParam = calendarioProducaoCustom.findSequenciaEstagiosConfigurados(sequencia);
		}

		return dadosEstagioParam;
	}

	public void editarEstagio(int sequencia, int anoCalendario, int lead, String dataInicio, String dataFim) {

		EstagiosParametros dadosEstagioParam = estagiosParametrosRepository.findByAnoCalendarioSequencia(anoCalendario,
				sequencia);

		dadosEstagioParam.dataFim = FormataData.parseStringToDate(dataFim);
		dadosEstagioParam.dataInicio = FormataData.parseStringToDate(dataInicio);
		dadosEstagioParam.lead = lead;

		estagiosParametrosRepository.saveAndFlush(dadosEstagioParam);
	}

	public void saveParametros(int anoCalendario, int periodoInicio, int periodoFim, boolean consideraSabado,
			boolean consideraDomingo, boolean consideraFeriado) {

		ParametrosCalendario parametrosCalendario = parametrosCalendarioRepository.findByAnoCalendario(anoCalendario);

		parametrosCalendario.consideraDomingo = consideraDomingo;
		parametrosCalendario.consideraFeriado = consideraFeriado;
		parametrosCalendario.consideraSabado = consideraSabado;

		parametrosCalendario.periodoInicio = periodoInicio;
		parametrosCalendario.periodoFim = periodoFim;

		parametrosCalendarioRepository.saveAndFlush(parametrosCalendario);
	}

	public LayoutCalendarioProducao geracaoCalendario(int anoCalendario) {

		ParametrosCalendario parametrosCalendario = parametrosCalendarioRepository.findByAnoCalendario(anoCalendario);
		List<EstagiosConfigCalend> estagios = calendarioProducaoCustom.findEstagiosByAnoCalendario(anoCalendario);

		List<GeracaoCalendarioPeriodos> periodosGeracao = CalculoCalendarioProducao.geracaoCalendario(anoCalendario, parametrosCalendario, estagios);

		return parseLayout(periodosGeracao);
	}

	public LayoutCalendarioProducao parseLayout(List<GeracaoCalendarioPeriodos> geracaoCalendario) {

		List<PeriodoProducao> periodos = new ArrayList<PeriodoProducao>();
		List<LayoutCabecalhoCalendProd> cabecalho = new ArrayList<LayoutCabecalhoCalendProd>();
		List<LayoutCorpoCalendProd> corpo = new ArrayList<LayoutCorpoCalendProd>();

		int contador = 0;

		for (GeracaoCalendarioPeriodos dadosNaoConvert : geracaoCalendario) {
			
			contador++;
			
			for (GeracaoCalendarioEstagios estagiosNaoConvert : dadosNaoConvert.estagios) {

				if (contador <= 1) {
					cabecalho.add(new LayoutCabecalhoCalendProd(
							calendarioProducaoCustom.getDescricaoEstagio(estagiosNaoConvert.codigoEstagio),
							retornaDescricaoDia(estagiosNaoConvert.diaSemanaInicio),
							retornaDescricaoDia(estagiosNaoConvert.diaSemanaFim)));
				}
				corpo.add(new LayoutCorpoCalendProd(dadosNaoConvert.codigoPeriodo,
						FormataData.parseDateToString(estagiosNaoConvert.dataInicio),
						FormataData.parseDateToString(estagiosNaoConvert.dataFim)));
			}

			PeriodoProducao periodoNaoConvert = new PeriodoProducao();
			periodoNaoConvert.setPeriodo(dadosNaoConvert.codigoPeriodo);

			periodos.add(periodoNaoConvert);
		}

		return new LayoutCalendarioProducao(periodos, cabecalho, corpo);
	}
	
	public String retornaDescricaoDia(int diaDaSemana) {

		String descDia = "";

		if (diaDaSemana == 1)
			descDia = "DOMINGO";
		if (diaDaSemana == 2)
			descDia = "SEGUNDA";
		if (diaDaSemana == 3)
			descDia = "TERCA";
		if (diaDaSemana == 4)
			descDia = "QUARTA";
		if (diaDaSemana == 5)
			descDia = "QUINTA";
		if (diaDaSemana == 6)
			descDia = "SEXTA";
		if (diaDaSemana == 7)
			descDia = "SABADO";

		return descDia;
	}


}
