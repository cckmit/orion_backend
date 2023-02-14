package br.com.live.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.custom.SequenciamentoDecoracoesCustom;
import br.com.live.model.OrdemProducao;

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
		List<OrdemProducao> ordens = ordemProducaoCustom.findOrdensOrdenadasPorPrioridade(camposSelParaPriorizacao, periodoInicial, periodoFinal, "9,31", "", referencias, "", artigos, "", isSomenteFlat, isDiretoCostura, false, isPossuiAgrupador);		
		System.out.println("Sequenciar Ordens");
		sequenciarOrdens(ordens);
		return ordens;
	}
	
	private void sequenciarOrdens(List<OrdemProducao> ordens) {
		
		int seqPrioridade = 0;
		
		for (OrdemProducao ordem : ordens) {
			seqPrioridade++;
			ordem.setSeqPrioridade(seqPrioridade);
			ordem.setCores(ordemProducaoCustom.getCoresOrdem(ordem.ordemProducao));
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
			System.out.println("Endereco: OBTER INFO");
			System.out.println("Data entrada: OBTER INFO");
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
	}	
}