package br.com.live.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Service
@Transactional
public class SequenciamentoDecoracoesService {
	
	private final SequenciamentoDecoracoesCustom sequenciamentoDecoracoesCustom; 
	private final OrdemProducaoCustom ordemProducaoCustom;
	private final ProdutoCustom produtoCustom;
	private final CalendarioCustom calendarioCustom; 
	
	public SequenciamentoDecoracoesService(SequenciamentoDecoracoesCustom sequenciamentoDecoracoesCustom, OrdemProducaoCustom ordemProducaoCustom, ProdutoCustom produtoCustom, CalendarioCustom calendarioCustom) {
		this.sequenciamentoDecoracoesCustom = sequenciamentoDecoracoesCustom;
		this.ordemProducaoCustom = ordemProducaoCustom;
		this.produtoCustom = produtoCustom;
		this.calendarioCustom = calendarioCustom;
	}
	
	public List<DadosSequenciamentoDecoracoes> consultarOrdens(List<String> camposSelParaPriorizacao, int periodoInicial, int periodoFinal, String referencias, String artigos, boolean isSomenteFlat, boolean isDiretoCostura, boolean isPossuiAgrupador) {
		System.out.println("Find Ordens");
		List<OrdemProducao> ordens = ordemProducaoCustom.findOrdensOrdenadasPorPrioridade(camposSelParaPriorizacao, periodoInicial, periodoFinal, SequenciamentoDecoracoesCustom.ESTAGIOS_DISTRIB_DECORACOES, "", referencias, "", artigos, "", isSomenteFlat, isDiretoCostura, false, isPossuiAgrupador);		
		System.out.println("Sequenciar Ordens");
		List<DadosSequenciamentoDecoracoes> ordensSequenciadas = calcularInformacoesDasOrdens(ordens);
		System.out.println("fim - consultarOrdens");
		return ordensSequenciadas;
	}
	
	/*		
	SEQUENCIA_ESTAGIO	CODIGO_ESTAGIO	ESTAGIO_ANTERIOR	ESTAGIO_DEPENDE
	8					31				6					16
	8					15				6					31
	8					9				6					27
	8					10				6					9
	8					70				6					10
	8					52				6					36
	
	Se o estágio depende for 31 deve desconsiderar os estágios posteriores ao 9		
	Quando simultaneo deve trazer uma linha para cada estagio	  
	Quando não for simultaneo deve trazer apenas o próximo
	Se houver estágio posteriores deve mostrar na coluna agrupador				
	*/
	
	private Map<String, Object> organizarProximosEstagios(List<OrdemProducaoEstagios> estagios) {
		List<OrdemProducaoEstagios> proxEstagios = new ArrayList<OrdemProducaoEstagios>();
		Map<String, Object> mapRetorno = new HashMap<String, Object>(); 		 

		int codEstagioDistrib = 0;
		String estagiosAgrupados = "";
		
		for (OrdemProducaoEstagios estagioOP : estagios) {
			EstagioProducao estagio = ordemProducaoCustom.getEstagio(estagioOP.getCodEstagio());
			if ((codEstagioDistrib > 0) && (estagio.estagioAgrupador == 0)) break;				
			if (estagio.estagioAgrupador == 0) codEstagioDistrib = estagioOP.getCodEstagio();
			if (estagio.estagioAgrupador == 0) continue;
			
			if (estagioOP.getCodEstagioDepende() == codEstagioDistrib) proxEstagios.add(estagioOP);
			if (estagioOP.getCodEstagioDepende() != codEstagioDistrib) {
				String concatCodDescEstagio = estagio.getEstagio() + "-" + estagio.descricao;
				if (estagiosAgrupados.isEmpty()) estagiosAgrupados += concatCodDescEstagio;
				else estagiosAgrupados += " + " + concatCodDescEstagio;
			}
		}		
		mapRetorno.put("proxEstagios", proxEstagios);
		mapRetorno.put("estagiosAgrupados", estagiosAgrupados);		
		return mapRetorno;
	}
	
	private List<DadosSequenciamentoDecoracoes> calcularInformacoesDasOrdens(List<OrdemProducao> ordens) {	
		int seqPrioridade = 0;			
		List<DadosSequenciamentoDecoracoes> listOrdensParaDecoracoes = new ArrayList<DadosSequenciamentoDecoracoes>(); 
		
		for (OrdemProducao ordem : ordens) {
			
			System.out.println("OP: " + ordem.ordemProducao);
			
			List<OrdemProducaoEstagios> estagios = sequenciamentoDecoracoesCustom.findEstagiosDecoracoesOrdem(ordem.ordemProducao);			
			
			System.out.println("est. seq: " + estagios.size());
			
			Map<String, Object> dados = organizarProximosEstagios(estagios);						
			List<OrdemProducaoEstagios> proximosEstagios = (List<OrdemProducaoEstagios>) dados.get("proxEstagios");
			String estagiosAgrupados = (String) dados.get("estagiosAgrupados");

			List<OrdemConfeccao> pacotes = ordemProducaoCustom.findAllOrdensConfeccao(ordem.ordemProducao);
						
			//System.out.println(1);
			
			for (OrdemProducaoEstagios estagioOP : proximosEstagios) {				
				//System.out.println(2);
				if (sequenciamentoDecoracoesCustom.isOrdemSequenciada(ordem.ordemProducao, estagioOP.getCodEstagio())) continue;
				
				int quantidade=0;
				int quantidadeTotal=0;
				double tempo=0;
				double tempoTotal=0;
				for (OrdemConfeccao pacote : pacotes) {					
					quantidade = ordemProducaoCustom.getQtdeAProduzirEstagio(pacote.ordemProducao, pacote.ordemConfeccao, estagioOP.getCodEstagio()); 
					tempo =	produtoCustom.getTempoProducaoEstagio("1", pacote.getReferencia(), pacote.getTamanho(), pacote.getCor(), ordem.getNrAlternativa(), ordem.getNrRoteiro(), estagioOP.getCodEstagio());
					quantidadeTotal += quantidade;					
					tempoTotal += (quantidade * tempo);    
				}
				double tempoUnit = tempoTotal / quantidadeTotal;  
				
				seqPrioridade++;
				EstagioProducao estagio = ordemProducaoCustom.getEstagio(estagioOP.getCodEstagio());				
				String cores = ordemProducaoCustom.getCoresOrdem(ordem.getOrdemProducao());
				String endereco = sequenciamentoDecoracoesCustom.findEnderecoDistribuicao(ordem.getOrdemProducao());				
				Date dataEntrada = sequenciamentoDecoracoesCustom.findDataProducaoEstagioAnterior(ordem.getOrdemProducao());						
				
				//System.out.println(3);
				
				DadosSequenciamentoDecoracoes dadosOrdem = new DadosSequenciamentoDecoracoes(seqPrioridade, ordem.getPeriodo(), ordem.getOrdemProducao(), ordem.getReferencia(),
						                                                                ordem.getDescrReferencia(), cores, quantidadeTotal, ordem.getObservacao(), estagioOP.getCodEstagio(),
						                                                                estagio.getDescricao(), estagiosAgrupados, endereco, dataEntrada, tempoUnit, tempoTotal); 
				listOrdensParaDecoracoes.add(dadosOrdem);
			}
		}
		
		return listOrdensParaDecoracoes;
	}	
	
	public void incluirOrdensNoSequenciamento(List<DadosSequenciamentoDecoracoes> dadosOrdem) {
		
		System.out.println("incluirOrdensNoSequenciamento");
		
		for (DadosSequenciamentoDecoracoes dadoOrdem : dadosOrdem) {
			
			System.out.println("Ordem: " + dadoOrdem.getOrdemProducao() + " - Estagio: " + dadoOrdem.getCodEstagioProx());
			
			int id = sequenciamentoDecoracoesCustom.findNextId();
			int sequencia = sequenciamentoDecoracoesCustom.findNextSeqProducao();					
			sequenciamentoDecoracoesCustom.saveSequenciamento(id, sequencia, dadoOrdem.getPeriodo(), dadoOrdem.getOrdemProducao(), dadoOrdem.getReferencia(), dadoOrdem.getCores(),  dadoOrdem.getCodEstagioProx(), dadoOrdem.getQuantidade(), dadoOrdem.getEstagiosAgrupados(), dadoOrdem.getEndereco(), dadoOrdem.getDataEntrada(), dadoOrdem.getTempoUnitario(), dadoOrdem.getTempoTotal(), null, null);
		}
	}	
	
	private Date proximoDia(Date data) {
		Calendario calendario = calendarioCustom.getProximoDiaUtil(data);
		return calendario.getData();
	}
	
	public void calcularSequenciamento(Date dataInicial, List<DadosSequenciamentoDecoracoes> ordens) {		
		double minutosProducaoDia = SequenciamentoDecoracoesCustom.MINUTOS_PRODUCAO_DIA;
		double minutosSaldo = minutosProducaoDia;
		double minutosPlanejar = 0;
		Date dataInicio = dataInicial;
		Date dataTermino = dataInicial;		
		int sequencia = 0;
		
		System.out.println("calcularSequenciamento");
		
		for (DadosSequenciamentoDecoracoes ordem : ordens) {
			sequencia++;
			minutosPlanejar = ordem.getTempoTotal();
			
			while (minutosPlanejar > 0) {							
				if (minutosPlanejar >= minutosSaldo) {
					minutosPlanejar -= minutosSaldo;
					minutosSaldo = minutosProducaoDia;
					dataTermino = proximoDia(dataTermino);
				} else {					
					minutosSaldo -= minutosPlanejar;
					minutosPlanejar = 0;
				}								
			}			
			
			System.out.println("ID " + ordem.getId() + " Seq: " + sequencia + " - Data Inicio: " + dataInicio + " - Data Término: " + dataTermino);
			
			// grava a data inicio e fim no estágio da ordem producao
			sequenciamentoDecoracoesCustom.saveSequenciamento(ordem.getId(), sequencia, dataInicio, dataTermino);						
			
			if (minutosSaldo <= 0) {
				dataInicio = proximoDia(dataTermino);
				dataTermino = dataInicio; 
			} else {
				dataInicio = dataTermino; 
			}
		}
	}	
}