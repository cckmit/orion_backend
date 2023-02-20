package br.com.live.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.custom.SequenciamentoDecoracoesCustom;
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
	
	public SequenciamentoDecoracoesService(SequenciamentoDecoracoesCustom sequenciamentoDecoracoesCustom, OrdemProducaoCustom ordemProducaoCustom, ProdutoCustom produtoCustom) {
		this.sequenciamentoDecoracoesCustom = sequenciamentoDecoracoesCustom;
		this.ordemProducaoCustom = ordemProducaoCustom;
		this.produtoCustom = produtoCustom;
	}
	
	public List<OrdemProducao> consultarOrdens(List<String> camposSelParaPriorizacao, int periodoInicial, int periodoFinal, String referencias, String artigos, boolean isSomenteFlat, boolean isDiretoCostura, boolean isPossuiAgrupador) {
		System.out.println("Find Ordens");
		List<OrdemProducao> ordens = ordemProducaoCustom.findOrdensOrdenadasPorPrioridade(camposSelParaPriorizacao, periodoInicial, periodoFinal, SequenciamentoDecoracoesCustom.ESTAGIOS_DISTRIB_DECORACOES, "", referencias, "", artigos, "", isSomenteFlat, isDiretoCostura, false, isPossuiAgrupador);		
		System.out.println("Sequenciar Ordens");
		List<OrdemProducao> ordensSequenciadas = sequenciarOrdensParaDecoracoes(ordens);
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

		System.out.println("organizarProximosEstagios");
		
		int codEstagioDistrib = 0;
		String estagiosAgrupados = "";
		
		for (OrdemProducaoEstagios estagioOP : estagios) {
			
			System.out.println("Estagio: " + estagioOP.getCodEstagio());
			
			EstagioProducao estagio = ordemProducaoCustom.getEstagio(estagioOP.getCodEstagio());
			if ((codEstagioDistrib > 0) && (estagio.estagioAgrupador == 0)) break;			
			if (estagio.estagioAgrupador == 0) codEstagioDistrib = estagioOP.getCodEstagio();
			
			if (estagioOP.getCodEstagioDepende() == codEstagioDistrib) proxEstagios.add(estagioOP);
			if (estagioOP.getCodEstagioDepende() != codEstagioDistrib) {
				if (estagiosAgrupados.isEmpty()) estagiosAgrupados += estagio.descricao;
				else estagiosAgrupados += " + " + estagio.descricao;						 
			}
		}		
		mapRetorno.put("proxEstagios", proxEstagios);
		mapRetorno.put("estagiosAgrupados", estagiosAgrupados);		
		return mapRetorno;
	}
	
	private List<OrdemProducao> sequenciarOrdensParaDecoracoes(List<OrdemProducao> ordens) {
		
		int seqPrioridade = 0;
		List<OrdemProducao> ordensSequenciadas = new ArrayList<OrdemProducao>();
		
		for (OrdemProducao ordem : ordens) {
			seqPrioridade++;
			ordem.setSeqPrioridade(seqPrioridade);
			ordem.setCores(ordemProducaoCustom.getCoresOrdem(ordem.ordemProducao));			

			System.out.println("seqPrioridade: " + seqPrioridade);
			
			List<OrdemProducaoEstagios> estagios = sequenciamentoDecoracoesCustom.findEstagiosDecoracoesOrdem(ordem.ordemProducao);			
			Map<String, Object> dados = organizarProximosEstagios(estagios);
						
			List<OrdemProducaoEstagios> proximosEstagios = (List<OrdemProducaoEstagios>) dados.get("proxEstagios");
			String estagiosAgrupados = (String) dados.get("estagiosAgrupados");

			List<OrdemConfeccao> pacotes = ordemProducaoCustom.findAllOrdensConfeccao(ordem.ordemProducao);
			
			// Confirmar com a AMANDA
			for (OrdemProducaoEstagios estagioOP : proximosEstagios) {
				// BeanUtils.copyProperties(origem, copia, "id", "idPlanoMestre");
				
				System.out.println("OP: " + ordem.getOrdemProducao() + " Estagio: " + estagioOP.getCodEstagio());
				
				int quantidade=0;
				int quantidadeTotal=0;
				double tempo=0;
				double tempoTotal=0;
				for (OrdemConfeccao pacote : pacotes) {					
					quantidade = ordemProducaoCustom.getQtdeAProduzirEstagio(pacote.ordemProducao, pacote.ordemConfeccao, estagioOP.getCodEstagio()); 
					tempo =	produtoCustom.getTempoProducaoEstagio("1", pacote.getReferencia(), pacote.getTamanho(), pacote.getCor(), pacote.nrAlternativa, pacote.getNrRoteiro(), estagioOP.getCodEstagio());
					quantidadeTotal += quantidade;
					tempoTotal += (quantidade * tempo);    
				}
				double tempoUnit = tempoTotal / quantidadeTotal;  
				
				System.out.println("=========================================");
				System.out.println("Prioridade: " + ordem.getSeqPrioridade());
				System.out.println("Periodo: " + ordem.getPeriodo());
				System.out.println("OP: " + ordem.getOrdemProducao());
				System.out.println("Referencia: " + ordem.getReferencia());
				System.out.println("Cor: " + ordem.getCores());
				System.out.println("Descricao: " + ordem.getDescrReferencia());
				System.out.println("Quantidade: " + quantidadeTotal);
				System.out.println("Observacao: " + ordem.getObservacao());
				System.out.println("Prox Estagio: " + estagioOP.getCodEstagio());
				System.out.println("Agrupador: " + estagiosAgrupados);
				System.out.println("Endereco: " + sequenciamentoDecoracoesCustom.findEnderecoDistribuicao(ordem.getOrdemProducao()));
				System.out.println("Data entrada: " + sequenciamentoDecoracoesCustom.findDataProducaoEstagioAnterior(ordem.getOrdemProducao()));
				System.out.println("Tempo Unit: " + tempoUnit);
				System.out.println("Tempo Total: " + tempoTotal);																
			}						
								
			// TODO - Carregar a quantidade em produção no estágio de distrib (pois pode ter haviado algum lançamento de perda em estágios anteriores
			
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