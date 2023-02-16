package br.com.live.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.custom.SequenciamentoDecoracoesCustom;
import br.com.live.model.OrdemProducao;
import br.com.live.model.OrdemProducaoEstagios;

@Service
@Transactional
public class SequenciamentoDecoracoesService {
	
	private final SequenciamentoDecoracoesCustom sequenciamentoDecoracoesCustom; 
	private final OrdemProducaoCustom ordemProducaoCustom;
	
	public SequenciamentoDecoracoesService(SequenciamentoDecoracoesCustom sequenciamentoDecoracoesCustom, OrdemProducaoCustom ordemProducaoCustom) {
		this.sequenciamentoDecoracoesCustom = sequenciamentoDecoracoesCustom;
		this.ordemProducaoCustom = ordemProducaoCustom;
	}
	
	public List<OrdemProducao> consultarOrdens(List<String> camposSelParaPriorizacao, int periodoInicial, int periodoFinal, String referencias, String artigos, boolean isSomenteFlat, boolean isDiretoCostura, boolean isPossuiAgrupador) {
		System.out.println("Find Ordens");
		List<OrdemProducao> ordens = ordemProducaoCustom.findOrdensOrdenadasPorPrioridade(camposSelParaPriorizacao, periodoInicial, periodoFinal, SequenciamentoDecoracoesCustom.ESTAGIOS_DISTRIB_DECORACOES, "", referencias, "", artigos, "", isSomenteFlat, isDiretoCostura, false, isPossuiAgrupador);		
		System.out.println("Sequenciar Ordens");
		List<OrdemProducao> ordensSequenciadas = sequenciarOrdensParaDecoracoes(ordens);
		return ordensSequenciadas;
	}
	
	// TODO - REMOVER
	private boolean isEstagiosSimultaneos(List<OrdemProducaoEstagios> estagios) {		
		boolean simultaneos = true;
		int codEstagioDepende = 0;		
		for (OrdemProducaoEstagios estagio : estagios) {
			if ((codEstagioDepende > 0) && (codEstagioDepende != estagio.getCodEstagioDepende())) simultaneos = false;
			codEstagioDepende = estagio.getCodEstagioDepende(); 
		}		
		return simultaneos;
	}
	
	private String getEstagiosAgrupados(List<OrdemProducaoEstagios> estagios) {
		String estagiosAgrupados="";		
		return estagiosAgrupados;
	}
	
	/*	
	
	SEQUENCIA_ESTAGIO	CODIGO_ESTAGIO	ESTAGIO_ANTERIOR	ESTAGIO_DEPENDE
	8					31				6					16
	8					15				6					31
	8					9				6					27
	8					10				6					9
	8					70				6					10
	8					52				6					36
		
	*/
	
	private Map<Integer, List<OrdemProducaoEstagios>> organizarProximosEstagiosPorDependencia(List<OrdemProducaoEstagios> estagios) {
		List<OrdemProducaoEstagios> proxEstagios;
		Map<Integer, List<OrdemProducaoEstagios>> mapEstagios = new HashMap<Integer, List<OrdemProducaoEstagios>>();
		
		for (OrdemProducaoEstagios estagio : estagios) {			
			if (mapEstagios.containsKey(estagio.getCodEstagioDepende())) 
				proxEstagios = mapEstagios.get(estagio.getCodEstagioDepende());
			else proxEstagios = new ArrayList<OrdemProducaoEstagios>();
			
			proxEstagios.add(estagio);			
			mapEstagios.put(estagio.getCodEstagioDepende(), proxEstagios);
		}
		return mapEstagios;
	}
	
	private List<OrdemProducao> sequenciarOrdensParaDecoracoes(List<OrdemProducao> ordens) {
		
		int seqPrioridade = 0;
		List<OrdemProducao> ordensSequenciadas = new ArrayList<OrdemProducao>();
		
		for (OrdemProducao ordem : ordens) {
			seqPrioridade++;
			ordem.setSeqPrioridade(seqPrioridade);
			ordem.setCores(ordemProducaoCustom.getCoresOrdem(ordem.ordemProducao));			
			
			List<OrdemProducaoEstagios> estagios = sequenciamentoDecoracoesCustom.findProximosEstagiosDecoracoesOrdem(ordem.ordemProducao);			
			Map<Integer, List<OrdemProducaoEstagios>> proximosEstagiosPorDependencia = organizarProximosEstagiosPorDependencia(estagios);
			
			// Confirmar com a AMANDA
			for (Integer estagioDepende : proximosEstagiosPorDependencia.keySet()) {
				List<OrdemProducaoEstagios> estagiosSeguintes = proximosEstagiosPorDependencia.get(estagioDepende);
				for (OrdemProducaoEstagios estagio : estagiosSeguintes) {
				}				
			}						
						
			// BeanUtils.copyProperties(origem, copia, "id", "idPlanoMestre");
			System.out.println("=========================================");
			System.out.println("Prioridade: " + ordem.getSeqPrioridade());
			System.out.println("Periodo: " + ordem.getPeriodo());
			System.out.println("OP: " + ordem.getOrdemProducao());
			System.out.println("Referencia: " + ordem.getReferencia());
			System.out.println("Cor: " + ordem.getCores());
			System.out.println("Descricao: " + ordem.getDescrReferencia());
			System.out.println("Quantidade: " + ordem.getQtdePecasProgramada());			
			System.out.println("Observacao: " + ordem.getObservacao());
			System.out.println("Prox Estagio: OBTER INFO");
			System.out.println("Agrupador: OBTER INFO");
			System.out.println("Endereco: OBTER INFO => " + sequenciamentoDecoracoesCustom.findEnderecoDistribuicao(ordem.getOrdemProducao()));
			System.out.println("Data entrada: OBTER INFO" + sequenciamentoDecoracoesCustom.findDataProducaoEstagioAnterior(ordem.getOrdemProducao()));
			System.out.println("Tempo Unit: OBTER INFO");
			System.out.println("Tempo Total: OBTER INFO");					
		
			/*
			•	Próximo estágio: Qual o próximo estágio da ordem de produção. 
			Quando a ordem de produção possuir mais de um estágio de deco-ração 
			e for simultâneo, deverá apresentar duas linhas, com os está-gios diferentes
			
			•	Agrupador: Deverá apresentar os estágios de decoração que tem na ordem de produção. 
			Exemplo: A ordem tem o estágio 07 e 10 = Ter-mo + Estampa. Para isso, o sistema precisa seguir a lógica: 
			Verificar quais estágios a ordem de produção possui, verificar na tela de Ca-dastro de Estágios se possui algum estágio da ordem cadastrado lá e com base nisso trazer o agrupador. 
			Ex: Estamparia + Termo Painéis.
			
			•	Endereço: Deverá ser pego o endereço cadastrado na tabela (dist_050) de acordo com o estágio informado na hora de realizar a consulta.			
			•	Data de entrada: O dia em que a ordem de produção entrou no está-gio, ou seja, considerar o dia da baixa do estágio anterior.
			*/					
		}	
		
		return null;
	}	
}