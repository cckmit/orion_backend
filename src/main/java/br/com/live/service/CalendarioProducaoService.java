package br.com.live.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.bo.CalculoCalendarioProducao;
import br.com.live.custom.CalendarioProducaoCustom;
import br.com.live.custom.PeriodoProducaoCustom;
import br.com.live.entity.ConfiguracaoEstagios;
import br.com.live.entity.EstagiosParametros;
import br.com.live.entity.ParametrosCalendario;
import br.com.live.model.EstagiosConfigCalend;
import br.com.live.model.EstagiosConfigurados;
import br.com.live.model.CalendarioEstagiosProducao;
import br.com.live.model.CalendarioPeriodoProducao;
import br.com.live.model.LayoutCabecalhoCalendProd;
import br.com.live.model.LayoutCalendarioPorArea;
import br.com.live.model.LayoutCalendarioProducao;
import br.com.live.model.LayoutCorpoCalendProd;
import br.com.live.model.PeriodoProducao;
import br.com.live.model.PeriodoProducaoArea;
import br.com.live.repository.ConfigEstagiosRepository;
import br.com.live.repository.EstagiosParametrosRepository;
import br.com.live.repository.ParametrosCalendarioRepository;
import br.com.live.util.FormataData;

@Service
@Transactional
public class CalendarioProducaoService {

	private final ConfigEstagiosRepository configEstagiosRepository;
	private final CalendarioProducaoCustom calendarioProducaoCustom;
	private final EstagiosParametrosRepository estagiosParametrosRepository;
	private final ParametrosCalendarioRepository parametrosCalendarioRepository;
	private final PeriodoProducaoCustom periodoProducaoCustom; 
	
	public CalendarioProducaoService(ConfigEstagiosRepository configEstagiosRepository,
			CalendarioProducaoCustom calendarioProducaoCustom,
			EstagiosParametrosRepository estagiosParametrosRepository,
			ParametrosCalendarioRepository parametrosCalendarioRepository,
			PeriodoProducaoCustom periodoProducaoCustom) {
		this.configEstagiosRepository = configEstagiosRepository;
		this.calendarioProducaoCustom = calendarioProducaoCustom;
		this.estagiosParametrosRepository = estagiosParametrosRepository;
		this.parametrosCalendarioRepository = parametrosCalendarioRepository;
		this.periodoProducaoCustom = periodoProducaoCustom;
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

	private List<CalendarioPeriodoProducao> calcularCalendarioProducao(int anoCalendario) {
		ParametrosCalendario parametrosCalendario = parametrosCalendarioRepository.findByAnoCalendario(anoCalendario);
		List<EstagiosConfigCalend> estagios = calendarioProducaoCustom.findEstagiosByAnoCalendario(anoCalendario);
		return CalculoCalendarioProducao.geracaoCalendario(anoCalendario, parametrosCalendario, estagios);		
	}
	
	public LayoutCalendarioProducao geracaoCalendario(int anoCalendario) {
		List<CalendarioPeriodoProducao> periodos = calcularCalendarioProducao(anoCalendario);
		return parseLayout(periodos);
	}

	private LayoutCalendarioProducao parseLayout(List<CalendarioPeriodoProducao> geracaoCalendario) {

		List<PeriodoProducao> periodos = new ArrayList<PeriodoProducao>();
		List<LayoutCabecalhoCalendProd> cabecalho = new ArrayList<LayoutCabecalhoCalendProd>();
		List<LayoutCorpoCalendProd> corpo = new ArrayList<LayoutCorpoCalendProd>();

		int contador = 0;

		for (CalendarioPeriodoProducao dadosNaoConvert : geracaoCalendario) {
			
			contador++;
			
			for (CalendarioEstagiosProducao estagiosNaoConvert : dadosNaoConvert.estagios) {

				if (contador <= 1) {
					cabecalho.add(new LayoutCabecalhoCalendProd(
							calendarioProducaoCustom.getDescricaoEstagio(estagiosNaoConvert.codigoEstagio),
							FormataData.getDescDayOfWeek(estagiosNaoConvert.diaSemanaInicio),
							FormataData.getDescDayOfWeek(estagiosNaoConvert.diaSemanaFim)));
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
	
	@SuppressWarnings("rawtypes")
	private Map<Integer, Map> calcularCalendarioPorArea(int empresa, int anoCalendario) {
		List<CalendarioPeriodoProducao> periodos = calcularCalendarioProducao(anoCalendario);
		return CalculoCalendarioProducao.calcularPeriodoAreas(empresa, periodos);		
	}
	
	@SuppressWarnings("rawtypes")
	public List<LayoutCalendarioPorArea> geracaoCalendarioPorArea(int empresa, int anoCalendario) {
		Map<Integer, Map> mapPeriodos = calcularCalendarioPorArea(empresa, anoCalendario);
		return parseLayout(mapPeriodos);
	}	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<LayoutCalendarioPorArea> parseLayout (Map<Integer, Map> mapPeriodos) {		

		LayoutCalendarioPorArea periodoArea;
		PeriodoProducaoArea area01;
		PeriodoProducaoArea area06;
		PeriodoProducaoArea area09;
		PeriodoProducaoArea area02;
		PeriodoProducaoArea area04;
		PeriodoProducaoArea area07;
		Map<Integer, PeriodoProducaoArea> mapAreaPeriodo;
		List<LayoutCalendarioPorArea> calendariosPorArea = new ArrayList<LayoutCalendarioPorArea>();
		
		for (Integer periodo : mapPeriodos.keySet()) {
			mapAreaPeriodo = mapPeriodos.get(periodo);

			area01 = mapAreaPeriodo.get(1);
			area06 = mapAreaPeriodo.get(6);
			area09 = mapAreaPeriodo.get(9);
			area02 = mapAreaPeriodo.get(2);
			area04 = mapAreaPeriodo.get(4);
			area07 = mapAreaPeriodo.get(7);
			
			periodoArea = new LayoutCalendarioPorArea();
			periodoArea.periodo = periodo;

			periodoArea.dataInicioProducaoArea01 = FormataData.parseDateToString(area01.getDataProdInicio());
			periodoArea.dataFimProducaoArea01 = FormataData.parseDateToString(area01.getDataProdFim());
			periodoArea.dataInicioFaturamentoArea01 = FormataData.parseDateToString(area01.getDataFatInicio());
			periodoArea.dataFimFaturamentoArea01 = FormataData.parseDateToString(area01.getDataFatFim());

			/*
			System.out.println("*** PARSE ***");			
			if ((periodo == 2200)||(periodo == 2201)) {
				System.out.println(periodo);
				System.out.println("periodoArea.dataInicioProducaoArea01: " + periodoArea.dataInicioProducaoArea01);
				System.out.println("periodoArea.dataFimProducaoArea01: " + periodoArea.dataFimProducaoArea01);
				System.out.println("periodoArea.dataInicioFaturamentoArea01: " + periodoArea.dataInicioFaturamentoArea01);
				System.out.println("periodoArea.dataFimFaturamentoArea01: " + periodoArea.dataFimFaturamentoArea01);
			}
			*/			
			
			periodoArea.dataInicioProducaoArea06 = FormataData.parseDateToString(area06.getDataProdInicio());
			periodoArea.dataFimProducaoArea06 = FormataData.parseDateToString(area06.getDataProdFim());
			periodoArea.dataInicioFaturamentoArea06 = FormataData.parseDateToString(area06.getDataFatInicio());
			periodoArea.dataFimFaturamentoArea06 = FormataData.parseDateToString(area06.getDataFatFim());

			periodoArea.dataInicioProducaoArea09 = FormataData.parseDateToString(area09.getDataProdInicio());
			periodoArea.dataFimProducaoArea09 = FormataData.parseDateToString(area09.getDataProdFim());
			periodoArea.dataInicioFaturamentoArea09 = FormataData.parseDateToString(area09.getDataFatInicio());
			periodoArea.dataFimFaturamentoArea09 = FormataData.parseDateToString(area09.getDataFatFim());

			periodoArea.dataInicioProducaoArea02 = FormataData.parseDateToString(area02.getDataProdInicio());
			periodoArea.dataFimProducaoArea02 = FormataData.parseDateToString(area02.getDataProdFim());
			periodoArea.dataInicioFaturamentoArea02 = FormataData.parseDateToString(area02.getDataFatInicio());
			periodoArea.dataFimFaturamentoArea02 = FormataData.parseDateToString(area02.getDataFatFim());

			periodoArea.dataInicioProducaoArea04 = FormataData.parseDateToString(area04.getDataProdInicio());
			periodoArea.dataFimProducaoArea04 = FormataData.parseDateToString(area04.getDataProdFim());
			periodoArea.dataInicioFaturamentoArea04 = FormataData.parseDateToString(area04.getDataFatInicio());
			periodoArea.dataFimFaturamentoArea04 = FormataData.parseDateToString(area04.getDataFatFim());

			periodoArea.dataInicioProducaoArea07 = FormataData.parseDateToString(area07.getDataProdInicio());
			periodoArea.dataFimProducaoArea07 = FormataData.parseDateToString(area07.getDataProdFim());
			periodoArea.dataInicioFaturamentoArea07 = FormataData.parseDateToString(area07.getDataFatInicio());
			periodoArea.dataFimFaturamentoArea07 = FormataData.parseDateToString(area07.getDataFatFim());
			
			calendariosPorArea.add(periodoArea);			
		}
		return calendariosPorArea;
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ParametrosCalendario gravarPeriodoProducaoCalculado(int anoCalendario) {
		// Por padrão sempre gravará os períodos para as 3 empresas 1, 500 e 600.
		int [] empresas = {1, 500, 600};
		int [] areas = {1, 6, 9, 2, 4, 7};

		System.out.println("gravarPeriodoProducaoCalculado"); 
		
		for (int i = 0; i < empresas.length; i++) {
			Map<Integer, Map> mapPeriodos = calcularCalendarioPorArea(empresas[i], anoCalendario);	

			for (Integer periodo : mapPeriodos.keySet()) {
				Map<Integer, PeriodoProducaoArea> mapAreaPeriodo = mapPeriodos.get(periodo);
				//if (periodo != 2200) continue; // TODO - GRAVAR APENAS O PERIODO 2200 PARA TESTE
				System.out.println("PERIODO: " + periodo + " - mapAreaPeriodo: " + mapAreaPeriodo.size());				
				for (int j = 0; j < areas.length; j++) {				
					PeriodoProducaoArea area = mapAreaPeriodo.get(areas[j]);
					periodoProducaoCustom.savePeriodoProducao(area.getEmpresa(), area.getArea(), area.getPeriodo(), area.getDataProdInicio(), area.getDataProdFim(), area.getDataFatInicio(), area.getDataFatFim(), area.getDataLimite(), area.getQuinzena());
				}				
			}
		}
		
		ParametrosCalendario parametrosCalendario = parametrosCalendarioRepository.findByAnoCalendario(anoCalendario);
		parametrosCalendario.situacao = 1;
		parametrosCalendarioRepository.save(parametrosCalendario);
		
		return parametrosCalendario;
	}
	
	public void deleteAnoCalendario(int anoCalendario) {
		estagiosParametrosRepository.deleteByAnoCalendario(anoCalendario);
		parametrosCalendarioRepository.deleteByAnoCalendario(anoCalendario);
	}
	
}
