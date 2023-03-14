package br.com.live.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.CalendarioCustom;
import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.custom.SequenciamentoDecoracoesCustom;
import br.com.live.model.Calendario;
import br.com.live.model.DadosSequenciamentoDecoracoes;
import br.com.live.model.EstagioProducao;
import br.com.live.model.OrdemConfeccao;
import br.com.live.model.OrdemProducao;
import br.com.live.model.OrdemProducaoEstagios;
import br.com.live.model.SugestaoReservaPorOrdemMaterial;
import br.com.live.util.MensagemErroException;

@Service
@Transactional
public class SequenciamentoDecoracoesService {

	private final SequenciamentoDecoracoesCustom sequenciamentoDecoracoesCustom;
	private final OrdemProducaoCustom ordemProducaoCustom;
	private final ProdutoCustom produtoCustom;
	private final CalendarioCustom calendarioCustom;

	public SequenciamentoDecoracoesService(SequenciamentoDecoracoesCustom sequenciamentoDecoracoesCustom,
			OrdemProducaoCustom ordemProducaoCustom, ProdutoCustom produtoCustom, CalendarioCustom calendarioCustom) {
		this.sequenciamentoDecoracoesCustom = sequenciamentoDecoracoesCustom;
		this.ordemProducaoCustom = ordemProducaoCustom;
		this.produtoCustom = produtoCustom;
		this.calendarioCustom = calendarioCustom;
	}

	public List<DadosSequenciamentoDecoracoes> consultarOrdens(List<String> camposSelParaPriorizacao,
			int periodoInicial, int periodoFinal, int estagioDistrib, String referencias, String artigos,
			boolean isSomenteFlat, boolean isDiretoCostura, boolean isPossuiAgrupador) {
		System.out.println("Find Ordens");
		List<OrdemProducao> ordens = ordemProducaoCustom.findOrdensOrdenadasPorPrioridade(camposSelParaPriorizacao,
				periodoInicial, periodoFinal, Integer.toString(estagioDistrib), "", referencias, "", artigos, "",
				isSomenteFlat, isDiretoCostura, false, isPossuiAgrupador);
		System.out.println("Sequenciar Ordens");
		List<DadosSequenciamentoDecoracoes> ordensSequenciadas = calcularInformacoesDasOrdens(ordens, estagioDistrib);
		System.out.println("fim - consultarOrdens");
		return ordensSequenciadas;
	}

	/*
	 * SEQUENCIA_ESTAGIO CODIGO_ESTAGIO ESTAGIO_ANTERIOR ESTAGIO_DEPENDE 8 31 6 16 8
	 * 15 6 31 8 9 6 27 8 10 6 9 8 70 6 10 8 52 6 36
	 * 
	 * Se o estágio depende for 31 deve desconsiderar os estágios posteriores ao 9
	 * Quando simultaneo deve trazer uma linha para cada estagio Quando não for
	 * simultaneo deve trazer apenas o próximo Se houver estágio posteriores deve
	 * mostrar na coluna agrupador
	 */

	private String sequenciarEstagios(List<OrdemProducaoEstagios> estagios, List<OrdemProducaoEstagios> proxEstagios) {
		String estagiosAgrupados = "";
		int codEstagioDistrib = 0;

		for (OrdemProducaoEstagios estagioOP : estagios) {
			EstagioProducao estagio = ordemProducaoCustom.getEstagio(estagioOP.getCodEstagio());
			if ((codEstagioDistrib > 0) && (estagio.estagioAgrupador == 0))
				break;
			if (estagio.estagioAgrupador == 0)
				codEstagioDistrib = estagioOP.getCodEstagio();
			if (estagio.estagioAgrupador == 0)
				continue;

			if (estagioOP.getCodEstagioDepende() == codEstagioDistrib)
				proxEstagios.add(estagioOP);
			if (estagioOP.getCodEstagioDepende() != codEstagioDistrib) {
				String concatCodDescEstagio = estagio.getEstagio() + "-" + estagio.descricao;
				if (estagiosAgrupados.isEmpty())
					estagiosAgrupados += concatCodDescEstagio;
				else
					estagiosAgrupados += " + " + concatCodDescEstagio;
			}
		}
		return estagiosAgrupados;
	}

	private List<DadosSequenciamentoDecoracoes> calcularInformacoesDasOrdens(List<OrdemProducao> ordens,
			int estagioDistrib) {
		int seqPrioridade = 0;
		List<DadosSequenciamentoDecoracoes> listOrdensParaDecoracoes = new ArrayList<DadosSequenciamentoDecoracoes>();

		for (OrdemProducao ordem : ordens) {
			List<OrdemProducaoEstagios> estagios = sequenciamentoDecoracoesCustom
					.findEstagiosDecoracoesOrdem(ordem.ordemProducao, estagioDistrib);
			List<OrdemProducaoEstagios> proxEstagios = new ArrayList<OrdemProducaoEstagios>();
			String estagiosAgrupados = sequenciarEstagios(estagios, proxEstagios);
			List<OrdemConfeccao> pacotes = ordemProducaoCustom.findAllOrdensConfeccao(ordem.ordemProducao);

			for (OrdemProducaoEstagios estagioOP : proxEstagios) {
				if (sequenciamentoDecoracoesCustom.isOrdemSequenciada(ordem.ordemProducao, estagioOP.getCodEstagio()))
					continue;

				int quantidade = 0;
				int quantidadeTotal = 0;
				double tempo = 0;
				double tempoTotal = 0;
				for (OrdemConfeccao pacote : pacotes) {
					quantidade = ordemProducaoCustom.getQtdeAProduzirEstagio(pacote.ordemProducao,
							pacote.ordemConfeccao, estagioOP.getCodEstagio());
					tempo = produtoCustom.getTempoProducaoEstagio("1", pacote.getReferencia(), pacote.getTamanho(),
							pacote.getCor(), ordem.getNrAlternativa(), ordem.getNrRoteiro(), estagioOP.getCodEstagio());
					quantidadeTotal += quantidade;
					tempoTotal += (quantidade * tempo);
				}
				double tempoUnit = tempoTotal / quantidadeTotal;

				seqPrioridade++;
				EstagioProducao estagio = ordemProducaoCustom.getEstagio(estagioOP.getCodEstagio());
				String cores = ordemProducaoCustom.getCoresOrdem(ordem.getOrdemProducao());
				String endereco = sequenciamentoDecoracoesCustom.findEnderecoDistribuicao(ordem.getOrdemProducao());
				Date dataEntrada = sequenciamentoDecoracoesCustom
						.findDataProducaoEstagioAnterior(ordem.getOrdemProducao());

				DadosSequenciamentoDecoracoes dadosOrdem = new DadosSequenciamentoDecoracoes(seqPrioridade,
						ordem.getPeriodo(), ordem.getOrdemProducao(), ordem.getReferencia(), ordem.getDescrReferencia(),
						cores, quantidadeTotal, ordem.getObservacao(), estagioOP.getCodEstagio(),
						estagio.getDescricao(), estagiosAgrupados, endereco, dataEntrada, tempoUnit, tempoTotal);
				listOrdensParaDecoracoes.add(dadosOrdem);
			}
		}

		return listOrdensParaDecoracoes;
	}

	public void incluirOrdensNoSequenciamento(List<DadosSequenciamentoDecoracoes> dadosOrdem) {
		System.out.println("incluirOrdensNoSequenciamento");
		List<Integer> listEstagiosParaIncluirOrdens = new ArrayList<Integer>();
		
		// guarda os estágios, para poder colocar as ordens nas filas dos respectivos estágios
		for (DadosSequenciamentoDecoracoes dadoOrdem : dadosOrdem) 
			if (!listEstagiosParaIncluirOrdens.contains(dadoOrdem.getCodEstagioProx())) listEstagiosParaIncluirOrdens.add(dadoOrdem.getCodEstagioProx());		
		
		// coloca as ordens na fila de cada estágio
		for (Integer codEstagio : listEstagiosParaIncluirOrdens) {
			Stream<DadosSequenciamentoDecoracoes> stream = dadosOrdem.stream().filter(d -> d.getCodEstagioProx() == codEstagio);
			List<DadosSequenciamentoDecoracoes> filtro = stream.collect(Collectors.toList()); 		
			
			String listOrdens = parseOrdensToStr(filtro);
			int sequencia = sequenciamentoDecoracoesCustom.findLastSeqProducaoByEstagioDesconsiderandoOrdens(codEstagio, listOrdens);	
			
			for (DadosSequenciamentoDecoracoes dadoOrdem : filtro) {
				System.out.println("Ordem: " + dadoOrdem.getOrdemProducao() + " - Estagio: " + dadoOrdem.getCodEstagioProx());
				int id = sequenciamentoDecoracoesCustom.findNextId();
				sequencia++;
				
				sequenciamentoDecoracoesCustom.saveSequenciamento(id, sequencia, dadoOrdem.getPeriodo(),
						dadoOrdem.getOrdemProducao(), dadoOrdem.getReferencia(), dadoOrdem.getCores(),
						dadoOrdem.getCodEstagioProx(), dadoOrdem.getQuantidade(), dadoOrdem.getEstagiosAgrupados(),
						dadoOrdem.getEndereco(), dadoOrdem.getDataEntrada(), dadoOrdem.getTempoUnitario(),
						dadoOrdem.getTempoTotal(), null, null);
			}			
		}
	}

	private String parseOrdensToStr(List<DadosSequenciamentoDecoracoes> ordens) {
		String listOrdens = "";
		for (DadosSequenciamentoDecoracoes ordem : ordens) listOrdens += listOrdens.isEmpty() ? ordem.getOrdemProducao() : "," + ordem.getOrdemProducao();
		return listOrdens;
	}
	
	private Date proximoDia(Date data) {
		Calendario calendario = calendarioCustom.getProximoDiaUtil(data);
		return calendario.getData();
	}

	private void calcularOcupacaoOrdem(Date dataInicio, Date dataTermino, double minutosPlanejar,
			double minutosRestantesNoDia) {
		while (minutosPlanejar > 0) {
			if (minutosPlanejar >= minutosRestantesNoDia) {
				minutosPlanejar -= minutosRestantesNoDia;
				minutosRestantesNoDia = SequenciamentoDecoracoesCustom.MINUTOS_PRODUCAO_DIA;
				dataInicio = proximoDia(dataInicio);
			} else {
				minutosRestantesNoDia -= minutosPlanejar;
				minutosPlanejar = 0;
			}
		}
	}

	private void calcularProximoDiaDisponivel(double minutosRestantesNoDia, Date dataInicio, Date dataTermino) {
		if (minutosRestantesNoDia <= 0) {
			dataInicio = proximoDia(dataTermino);
			dataTermino = dataInicio;
		} else {
			dataInicio = dataTermino;
		}
	}

	public void calcularSequenciamento(int codEstagioSequenciar, Date dataInicial,
			List<DadosSequenciamentoDecoracoes> ordens) {
		double minutosProducaoDia = SequenciamentoDecoracoesCustom.MINUTOS_PRODUCAO_DIA;
		double minutosRestantesNoDia = minutosProducaoDia;
		double minutosPlanejar = 0;
		Date dataInicio = dataInicial;
		Date dataTermino = dataInicial;

		System.out.println("calcularSequenciamento: " + codEstagioSequenciar);
		
		// concatena as ordens numa string para desconsiderar as mesmas na busca da próxima sequencia dispónivel.
		String listOrdens = parseOrdensToStr(ordens); 
		
		int sequencia = sequenciamentoDecoracoesCustom.findLastSeqProducaoByEstagioDesconsiderandoOrdens(codEstagioSequenciar, listOrdens);
		
		for (DadosSequenciamentoDecoracoes ordem : ordens) {
			sequencia ++;
			System.out.println("OP: " + ordem.getOrdemProducao() + " Próxima seq: " + sequencia);
			// deve sequenciar apenas as ordens do estágio marcado para sequenciar
			if (ordem.getCodEstagioProx() != codEstagioSequenciar)
				continue;
			// deve sequenciar apenas as ordens que ainda não foram confirmadas
			if (ordem.getConfirmado() == 1)
				continue;
			
			minutosPlanejar = ordem.getTempoTotal();
			calcularOcupacaoOrdem(dataInicio, dataTermino, minutosPlanejar, minutosRestantesNoDia);
			System.out.println("ID " + ordem.getId() + " Seq: " + sequencia + " - Data Inicio: " + dataInicio + " - Data Término: " + dataTermino);
			// grava a data inicio e fim no estágio da ordem producao
			sequenciamentoDecoracoesCustom.saveSequenciamento(ordem.getId(), sequencia, dataInicio, dataTermino);
			calcularProximoDiaDisponivel(minutosRestantesNoDia, dataInicio, dataTermino);
		}
	}

	public void confirmarSequenciamento(int codEstagio, List<DadosSequenciamentoDecoracoes> ordens) {
		System.out.println("confirmarSequenciamento");		
		for (DadosSequenciamentoDecoracoes ordem : ordens) {
			System.out.println("Ordem: " + ordem.getOrdemProducao());
			if (ordem.getDataInicio() == null)
				continue;
			System.out.println("Salvou: " + ordem.getOrdemProducao());
			sequenciamentoDecoracoesCustom.saveSequenciamento(ordem.getId(), SequenciamentoDecoracoesCustom.ORDEM_CONFIRMADA);
		}
	}

	public void removerOrdemProducaoSequenciamento(int id) {
		DadosSequenciamentoDecoracoes ordemSequenciada = sequenciamentoDecoracoesCustom
				.findSequenciamentoDecoracoesById(id);
		// não permitir remover ordens confirmadas		
		if (ordemSequenciada.getConfirmado() == SequenciamentoDecoracoesCustom.ORDEM_CONFIRMADA)
			throw new MensagemErroException("Ordem de produção confirmada para o estágio!");		
		// não permitir remover ordens com o estágio de distribuição baixado
		if (sequenciamentoDecoracoesCustom.estagioDistribuicaoEmAberto(ordemSequenciada.getOrdemProducao(),ordemSequenciada.getCodEstagioProx()))
			throw new MensagemErroException("Ordem de produção não possui estágio de distribuição em aberto!");		
		sequenciamentoDecoracoesCustom.deleteOrdemProducao(id);
	}	
}