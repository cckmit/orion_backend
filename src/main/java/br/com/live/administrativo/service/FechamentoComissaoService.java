package br.com.live.administrativo.service;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.administrativo.custom.FechamentoComissaoCustom;
import br.com.live.administrativo.entity.DevolucaoMostruario;
import br.com.live.administrativo.entity.FechamentoDevolucaoMostruario;
import br.com.live.administrativo.entity.LancamentoContabeisImport;
import br.com.live.administrativo.entity.ParcelaMostruario;
import br.com.live.administrativo.model.ConsultaFechamentoComissoes;
import br.com.live.administrativo.model.ConsultaLanctoContabeis;
import br.com.live.administrativo.model.RetornoLancamentoCont;
import br.com.live.administrativo.repository.DevolucaoMostruarioRepository;
import br.com.live.administrativo.repository.FechamentoDevolucaoMostruarioRepository;
import br.com.live.administrativo.repository.ParcelaMostruarioRepository;
import br.com.live.sistema.model.ConsultaChamado;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;
import br.com.live.util.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Transactional
public class FechamentoComissaoService {

	private final static int LINHA_BEACH = 53;
	private final static int LINHA_FITNESS = 52;

	private final FechamentoComissaoCustom financeiroCustom;
	private final ReportService reportService;
	private final DevolucaoMostruarioRepository devolucaoMostruarioRepository;
	private final FechamentoDevolucaoMostruarioRepository fechamentoDevolucaoMostruarioRepository;
	private final ParcelaMostruarioRepository parcelaMostruarioRepository;

	@Autowired
	FechamentoComissaoService(FechamentoComissaoCustom financeiroCustom, ReportService reportService,
			DevolucaoMostruarioRepository devolucaoMostruarioRepository,
			FechamentoDevolucaoMostruarioRepository fechamentoDevolucaoMostruarioRepository, ParcelaMostruarioRepository parcelaMostruarioRepository) {
		this.financeiroCustom = financeiroCustom;
		this.reportService = reportService;
		this.devolucaoMostruarioRepository = devolucaoMostruarioRepository;
		this.fechamentoDevolucaoMostruarioRepository = fechamentoDevolucaoMostruarioRepository;
		this.parcelaMostruarioRepository = parcelaMostruarioRepository;
	}

	public List<ConsultaFechamentoComissoes> findTitulosAtrasadosAnalitico(int mes, int ano, List<ConteudoChaveAlfaNum> listRepresentante) {
		String dataInicio = findDataInicioCobrancaAtrasadas(mes, ano);
		return financeiroCustom.findTitulosAtrasadosAnalitico(dataInicio, listRepresentante);
	}

	public List<ConsultaFechamentoComissoes> findLancamentosFaturamento(int mes, int ano, List<ConteudoChaveAlfaNum> listRepresentante) {
		String mesComZero = "";
		if (mes < 10) {
			mesComZero = "0" + mes;
		}
		;
		return financeiroCustom.findLancamentosFaturamento(mesComZero, ano, listRepresentante);
	}

	public List<ConsultaFechamentoComissoes> findLancamentosBaixaTitulos(int mes, int ano, List<ConteudoChaveAlfaNum> listRepresentante) {
		String mesComZero = "";
		if (mes < 10) {
			mesComZero = "0" + mes;
		}
		;
		return financeiroCustom.findLancamentosBaixaTitulos(mesComZero, ano, listRepresentante);
	}

	public List<ConsultaFechamentoComissoes> findTitulosAtrasadosSintetico(int mes, int ano, List<ConteudoChaveAlfaNum> listRepresentante) {
		String dataInicio = findDataInicioCobrancaAtrasadas(mes, ano);
		String dataAnterior = findMesAnteriorDatInicio(dataInicio);
		return financeiroCustom.findTitulosAtrasadosSintetico(dataInicio, dataAnterior, listRepresentante);
	}

	public String findMesAnteriorDatInicio(String dataInicio) {
		String dataAnterior = "";
		int mesAnterior = 0;

		String[] dataAnt = dataInicio.split("[/]");
		int dia = Integer.parseInt(dataAnt[0]);
		int mesAtual = Integer.parseInt(dataAnt[1]);
		int ano = Integer.parseInt(dataAnt[2]);

		if (mesAtual == 1) {
			mesAnterior = 12;
			ano = ano - 1;
		} else {
			mesAnterior = mesAtual - 1;
		}
		;

		if (mesAnterior == 2) {
			dia = 28;
		} else if (mesAnterior == 1 || mesAnterior == 3 || mesAnterior == 5 || mesAnterior == 7 || mesAnterior == 8
				|| mesAnterior == 10 || mesAnterior == 12) {
			dia = 31;
		} else {
			dia = 30;
		}
		;
		dataAnterior = dia + "/" + mesAnterior + "/" + ano;
		return dataAnterior;
	}

	public String findDataInicioCobrancaAtrasadas(int mes, int ano) {

		int diaInicio = 0;
		int mesInicio = 0;
		int anoInicio = 0;
		String dataInicio = "";

		if (mes == 1) {
			mesInicio = 11;
			anoInicio = ano - 1;
		} else if (mes == 2) {
			mesInicio = 12;
			anoInicio = ano - 1;
		} else {
			mesInicio = mes - 2;
			anoInicio = ano;
		}
		;

		if (mesInicio == 2) {
			diaInicio = 28;
		} else if (mesInicio == 1 || mesInicio == 3 || mesInicio == 5 || mesInicio == 7 || mesInicio == 8
				|| mesInicio == 10 || mesInicio == 12) {
			diaInicio = 31;
		} else {
			diaInicio = 30;
		}
		;
		if (mesInicio < 10) {
			dataInicio = diaInicio + "/" + "0" + mesInicio + "/" + anoInicio;
		} else {
			dataInicio = diaInicio + "/" + mesInicio + "/" + anoInicio;
		}
		;

		return dataInicio;
	}

	public List<ConteudoChaveAlfaNum> findAllEstacoes() {
		return financeiroCustom.findAllEstacoes();
	}

	public List<ConsultaFechamentoComissoes> findBonusPorRepresentante(int mes, int ano, List<ConteudoChaveAlfaNum> listRepresentante) {

		float totalFaturado = 0;
		float valorProporcional = 0;
		String mesComZero = "";
		if (mes < 10) {
			mesComZero = "0" + mes;
		}
		;
		float metaFitness = financeiroCustom.findMetaPorRespresentanteFitness(listRepresentante, mesComZero, ano); 
		// Buscando a Meta do Representante pra coleção na linha Fitness
		float metaBeach = financeiroCustom.findMetaPorRespresentanteBeach(listRepresentante, mesComZero, ano); 
		// Buscando a Meta do Representante pra coleção na linha Beach
		// Descobrindo a porcentagem de cada meta representa na meta total
		float totalMeta = metaFitness + metaBeach;
		float porcLinhaFitness = (metaFitness * 100) / totalMeta;
		float porcLinhaBeach = 100 - porcLinhaFitness;
		// ------------------------------------------------
		float percAtingidoFitness = financeiroCustom.findPercAtingidoFitness(mesComZero, ano, listRepresentante);
		float percAtingidoBeach = financeiroCustom.findPercAtingidoBeach(mesComZero, ano, listRepresentante);

		if (percAtingidoFitness >= 100) {
			totalFaturado = financeiroCustom.findTotalFaturadoPorRepresentanteNoMes(mesComZero, ano, listRepresentante);
			valorProporcional = totalFaturado * (percAtingidoFitness / 100);
		}
		if (percAtingidoBeach >= 100) {
			totalFaturado = financeiroCustom.findTotalFaturadoPorRepresentanteNoMes(mesComZero, ano, listRepresentante);
			valorProporcional = totalFaturado * (percAtingidoBeach / 100);
		}
		if (percAtingidoFitness >= 100 && percAtingidoBeach >= 100) {
			totalFaturado = financeiroCustom.findTotalFaturadoPorRepresentanteNoMes(mesComZero, ano, listRepresentante);
			valorProporcional = totalFaturado;
		}
		List<ConteudoChaveAlfaNum> listEstados = financeiroCustom.findUf(listRepresentante);
		List<ConteudoChaveAlfaNum> listSubRegiao = financeiroCustom.findSubRegiao(listRepresentante);

		String estado = ConteudoChaveAlfaNum.parseValueToString(listEstados).replace(",", " /");
		estado = estado.replace("'", "");

		String regiao = ConteudoChaveAlfaNum.parseValueToString(listSubRegiao).replace(",", " /");
		regiao = regiao.replace("'", "");

		return financeiroCustom.findBonusPorRepresentante(mesComZero, ano, listRepresentante, totalFaturado,
				porcLinhaFitness, porcLinhaBeach, percAtingidoFitness, percAtingidoBeach, valorProporcional, estado,
				regiao, metaFitness, metaBeach);
	}

	public List<ConsultaFechamentoComissoes> findDevolucaoPorRepresentante(int mes, int ano, List<ConteudoChaveAlfaNum> listRepresentante) {

		String mesComZero = "";
		if (mes < 10) {
			mesComZero = "0" + mes;
		}
		;
		return financeiroCustom.findDevolucoes(mesComZero, ano, listRepresentante);
	}

	public List<ConsultaFechamentoComissoes> findLanctoManuaisPorRepresentante(int mes, int ano, List<ConteudoChaveAlfaNum> listRepresentante) {

		String mesComZero = "";
		if (mes < 10) {
			mesComZero = "0" + mes;
		}
		;
		return financeiroCustom.findLanctoManuaisPorRepresentante(mesComZero, ano, listRepresentante);
	}

	public List<ConsultaFechamentoComissoes> findTotaisLanctoManuaisPorRepresentante(int mes, int ano, List<ConteudoChaveAlfaNum> listRepresentante) {

		String mesComZero = "";
		if (mes < 10) {
			mesComZero = "0" + mes;
		};
		return financeiroCustom.findTotaisLanctoManuaisPorRepresentante(mesComZero, ano, listRepresentante);
	}

	public List<ConsultaFechamentoComissoes> findMostruarioAdquirido(int mes, int ano,	List<ConteudoChaveAlfaNum> listRepresentante, String estacao) {
		
		String tabPreco = financeiroCustom.findTabPrecoEstacao(estacao);
		String[] tabPrecoConcat = tabPreco.split("[-]");
		int tabCol = Integer.parseInt(tabPrecoConcat[0]);
		int tabMes = Integer.parseInt(tabPrecoConcat[1]);
		int tabSeq = Integer.parseInt(tabPrecoConcat[2]);
		
		String mesComZero = "";
		if (mes < 10) {
			mesComZero = "0" + mes;
		};
		
		String dataInicial = financeiroCustom.findDataInicialMostruario(mesComZero, ano);
		Date dataFinal = FormataData.getFinalDay(mes, ano);		
		return financeiroCustom.findPedidoVendaMostruario(listRepresentante, dataInicial, FormataData.parseDateToString(dataFinal), tabCol, tabMes, tabSeq);
	}

	public List<ConsultaFechamentoComissoes> findMostruarioDevolvido(List<ConteudoChaveAlfaNum> listRepresentante, String estacao) {
		return financeiroCustom.findItensDevolvidos(listRepresentante, estacao);
	}
	
	public List<ConsultaFechamentoComissoes> findMostruarioSintetico(List<ConteudoChaveAlfaNum> listRepresentante, String estacao) {
		return financeiroCustom.findMostruarioSintetico(listRepresentante, estacao);
	}
	
	public List<ConsultaFechamentoComissoes> ExistsMostruarioDevolvido(List<ConteudoChaveAlfaNum> listRepresentante, int mes, int ano, String estacao) {
		
		int registros = financeiroCustom.findMostruarioEstacaoPorRepres(listRepresentante, estacao);
		
		if(registros == 0) {
			return findMostruarioAnalitico(listRepresentante, mes, ano, estacao);
		} else {
			return financeiroCustom.findItensMostruarioAnalitico(listRepresentante, estacao);
		}
	}

	public List<ConsultaFechamentoComissoes> findMostruarioAnalitico(List<ConteudoChaveAlfaNum> listRepresentante, int mes, int ano, String estacao) {
		
		String mesComZero = "";
		if (mes < 10) {
			mesComZero = "0" + mes;
		};

		FechamentoDevolucaoMostruario fechamentoMostruario = null;
		ParcelaMostruario parcelaMostruario = null;
		int qtdeDevolvida = 0;
		int diferenca = 0;
		float valor = 0;
		float desc60Porc = 0;
		float bonus30Porc = 0;
		float bonus100Porc = 0;
		float valorCobrado = 0;
		float somaValorCobrado = 0;
		float parcela = 0;
		int sequencia = 1;
		float metaFitness = financeiroCustom.findMetaPorRepresentanteEstacaoFitness(listRepresentante, estacao); // Buscando Meta do Representante pra coleção na linha Fitness
		float metaBeach = financeiroCustom.findMetaPorRepresentanteEstacaoBeach(listRepresentante, estacao); // Buscando Meta do Representante pra coleção na linha Beach
		// ------------------------------------------------------------------------------------------------------
		int menorAnoInicioFitness = financeiroCustom.findAnoInicioEstacaoFitness(estacao);  // Achar a Data Inicial da estação
		String dtInicioEstFitness = financeiroCustom.findDtInicioEstacaoFitness(estacao, menorAnoInicioFitness);
		int maiorAnoFimFitness = financeiroCustom.findAnoFimEstacaoFitness(estacao); // Achar a Data final da Estação
		String dtFimEstFitness = financeiroCustom.findDtFimEstacaoFitness(estacao, maiorAnoFimFitness);
		// -------------------------------------------------------------------------------------------------------
		int menorAnoInicioBeach = financeiroCustom.findAnoInicioEstacaoBeach(estacao); // Achar a Data Inicial da estação
		String dtInicioEstBeach = financeiroCustom.findDtInicioEstacaoBeach(estacao, menorAnoInicioBeach);
		int maiorAnoFimBeach = financeiroCustom.findAnoFimEstacaoBeach(estacao); // Achar a Data final da Estação
		String dtFimEstBeach = financeiroCustom.findDtFimEstacaoBeach(estacao, maiorAnoFimBeach);
		// -------------------------------------------------------------------------------------------------------
		// ----------------- Pegando Somente 1 Cod de Representante
		String codRepresentante = ConteudoChaveAlfaNum.parseValueToString(listRepresentante);
		codRepresentante = codRepresentante.substring(codRepresentante.indexOf(",") + 1);
		codRepresentante = codRepresentante.replace("'", "").strip();
		int codRepres = Integer.parseInt(codRepresentante);
		// ----------------------------------------------------------
		String dataInicial = financeiroCustom.findDataInicialMostruario(mesComZero, ano);
		Date dataFinal = FormataData.getFinalDay(mes, ano);
		// ----------------------------------------------------------
		String tabPreco = financeiroCustom.findTabPrecoEstacao(estacao);
		String[] tabPrecoConcat = tabPreco.split("[-]");
		int tabCol = Integer.parseInt(tabPrecoConcat[0]);
		int tabMes = Integer.parseInt(tabPrecoConcat[1]);
		int tabSeq = Integer.parseInt(tabPrecoConcat[2]);
		
		List<ConsultaFechamentoComissoes> listaMsotruarioEnviado = financeiroCustom.findPedidoVendaMostruario(listRepresentante, dataInicial, 
				FormataData.parseDateToString(dataFinal), tabCol, tabMes, tabSeq);

		// Buscando o Percentual atingido no periodo na linha Fitness
		float percAtingFitness = financeiroCustom.findPercAtingidoEstacaoFitness(dtInicioEstFitness, dtFimEstFitness,	tabCol, tabMes, tabSeq, metaFitness, listRepresentante);
		// Buscando o Percentual atingido no periodo na linha Beach
		float percAtingBeach = financeiroCustom.findPercAtingidoEstacaoBeach(dtInicioEstBeach, dtFimEstBeach, tabCol, tabMes, tabSeq, metaBeach, listRepresentante);
		
		for (ConsultaFechamentoComissoes itens : listaMsotruarioEnviado) {
			int id = fechamentoDevolucaoMostruarioRepository.findNextID();
			float precoItem = financeiroCustom.findPrecoProduto(tabCol, tabMes, tabSeq, itens.nivel, itens.grupo, itens.subGrupo, itens.item);
			qtdeDevolvida = financeiroCustom.findQtdeDevolvida(estacao, codRepres, itens.nivel, itens.grupo, itens.subGrupo, itens.item);
			diferenca = itens.quantidade - qtdeDevolvida;
			valor = precoItem * diferenca;
			desc60Porc = (float) (valor * 0.40);
			if (itens.linhaProduto == LINHA_FITNESS) {
				if (percAtingFitness >= 100 && percAtingFitness <= 119) {
					bonus30Porc = (float) (desc60Porc * 0.70);
				} else if (percAtingFitness >= 120) {
					bonus100Porc = (float) (desc60Porc - (desc60Porc * 0.70));
				} else {
					bonus30Porc = 0;
				}
				;
			} else if (itens.linhaProduto == LINHA_BEACH) {
				if (percAtingBeach >= 100 && percAtingBeach <= 119) {
					bonus30Porc = (float) (desc60Porc * 0.70);
				} else if (percAtingBeach >= 120) {
					bonus100Porc = (float) (desc60Porc - (desc60Porc * 0.70));
				} else {
					bonus30Porc = 0;
				}
				;
			};
			valorCobrado = valor - (desc60Porc + bonus30Porc + bonus100Porc); // Gravar no BD
			fechamentoMostruario = new FechamentoDevolucaoMostruario(id, codRepres, estacao, itens.nivel, itens.grupo, itens.subGrupo, itens.item, itens.quantidade, 
					qtdeDevolvida, diferenca, valor, desc60Porc, bonus30Porc, bonus100Porc, valorCobrado, itens.tabCol, itens.tabMes, itens.tabSeq);
			fechamentoDevolucaoMostruarioRepository.saveAndFlush(fechamentoMostruario);
			qtdeDevolvida = 0;
			diferenca = 0;
			valor = 0;
			desc60Porc = 0;
			bonus30Porc = 0;
		}
		somaValorCobrado = financeiroCustom.findTotalCobradoPorRepres(listRepresentante, estacao);
		parcela = somaValorCobrado / 6;
		for (int i = 1; i <= 6; i++) {
			parcelaMostruario = new ParcelaMostruario(parcelaMostruarioRepository.findNextId(), codRepres, estacao, sequencia, parcela, 0);
			parcelaMostruarioRepository.saveAndFlush(parcelaMostruario);
			sequencia += 1;
		}
		
		return financeiroCustom.findItensMostruarioAnalitico(listRepresentante, estacao);
	}

	public int findCargoRepresentante(List<ConteudoChaveAlfaNum> listRepresentante) {
		return financeiroCustom.findCargoRepresentante(listRepresentante);
	}

	public int pagarParcelaMostruario(int id) {
		return financeiroCustom.pagarParcelaMostruario(id);
	}
	
	public String gerarPdf(List<ConteudoChaveAlfaNum> representante, List<ConsultaFechamentoComissoes> fechamento,
			List<ConsultaFechamentoComissoes> fechamento2, int mes, int ano, float valorAReceber)
			throws JRException, FileNotFoundException {

		String nomeRelatorioGerado = "";
		String nomeRepresentante = "";

		for (ConteudoChaveAlfaNum dadosRepresentante : representante) {
			if (representante.size() == 1) {
				nomeRepresentante = dadosRepresentante.label;
				nomeRepresentante = nomeRepresentante.substring(nomeRepresentante.indexOf(" ") + 2);
			} else {
				if (!nomeRepresentante.isEmpty()) {
					nomeRepresentante += " / ";
				}
				String nomeCompleto = dadosRepresentante.label;
				String nomeSemCodigo = nomeCompleto.substring(nomeCompleto.indexOf(" ") + 2);
				nomeRepresentante += nomeSemCodigo;
			}
		}

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(fechamento);
		JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(fechamento2);

		Map<String, Object> parameters = setParameters(nomeRepresentante, mes, ano, dataSource2, valorAReceber);

		nomeRelatorioGerado = reportService.generateReport("pdf", dataSource, "fechamento_comissoes", parameters,
				"FechamentoRepresentante", false);

		return nomeRelatorioGerado;
	}

	public Map<String, Object> setParameters(String nomeRepresentante, int mesNum, int ano,
			JRBeanCollectionDataSource dataSource2, float valorAReceber) {
		String mes = "";

		String[] meses = { "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO",
				"OUTUBRO", "NOVEMBRO", "DEZEMBRO" };

		mes = meses[mesNum - 1];

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("mes", mes);
		parameters.put("ano", ano);
		parameters.put("nomeRepresentante", nomeRepresentante);
		parameters.put("listPdf", dataSource2);
		parameters.put("valorAReceber", valorAReceber);

		return parameters;
	}

	public void importarDevolucoesMostruario(List<ConteudoChaveAlfaNum> listRepresentante, String estacao, List<ConsultaFechamentoComissoes> listDevMostruario) {

		DevolucaoMostruario devolucaoMostruario = null;
		String tabPreco = "";
		float preco = 0;
		// ----------------- Pegando Somente 1 Cod de Representante
		String codRepresentante = ConteudoChaveAlfaNum.parseValueToString(listRepresentante);
		codRepresentante = codRepresentante.substring(codRepresentante.indexOf(",") + 1);
		codRepresentante = codRepresentante.replace("'", "").strip();
		int codRepres = Integer.parseInt(codRepresentante);
		// -----------------
		int linhaProduto = 0;

		for (ConsultaFechamentoComissoes dados : listDevMostruario) {

			int id = devolucaoMostruarioRepository.findNextId();
			tabPreco = financeiroCustom.findTabPrecoEstacao(estacao);
			String[] tabPrecoConcat = tabPreco.split("[-]");
			int col = Integer.parseInt(tabPrecoConcat[0]);
			int mes = Integer.parseInt(tabPrecoConcat[1]);
			int seq = Integer.parseInt(tabPrecoConcat[2]);
			preco = financeiroCustom.findPrecoProduto(col, mes, seq, dados.nivel, dados.grupo, dados.subGrupo, dados.item);
			linhaProduto = financeiroCustom.findLinhaProduto(dados.nivel, dados.grupo);
			devolucaoMostruario = new DevolucaoMostruario(id, codRepres, estacao, dados.nivel, dados.grupo,	dados.subGrupo, dados.item, linhaProduto, 
					dados.quantidade, col, mes, seq, preco,	preco * dados.quantidade);
			devolucaoMostruarioRepository.saveAndFlush(devolucaoMostruario);
		}
	}

}
