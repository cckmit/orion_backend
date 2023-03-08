package br.com.live.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.live.custom.ContabilidadeCustom;
import br.com.live.entity.LancamentoContabeisImport;
import br.com.live.model.ConsultaLanctoContabeis;
import br.com.live.repository.LanctoContabilImportacaoRepository;
import br.com.live.util.FormataData;


@Transactional
@Service
public class ContabilidadeService {
	
	private final LanctoContabilImportacaoRepository lanctoContabilImportacaoRepository;
	private final ContabilidadeCustom contabilidadeCustom;
	
	
	
	public ContabilidadeService(LanctoContabilImportacaoRepository lanctoContabilImportacaoRepository, ContabilidadeCustom contabilidadeCustom) {
		this.lanctoContabilImportacaoRepository = lanctoContabilImportacaoRepository;
		this.contabilidadeCustom = contabilidadeCustom;
	}
	
	//public List<ConsultaLanctoContabeis> validarOrigem(int origem){
		
	//	int extistsOrigem = contabilidadeCustom.findOrigem(origem);
		
	//	if(exi) {
			
	//	}
	//}
	
	public void importarLancamentosContabeis(List<ConsultaLanctoContabeis> listLancto, String usuario, String datainsercao) {
		// Deletando tudo na Tabela orion_cnt_010
		lanctoContabilImportacaoRepository.deleteAll();
		
		LancamentoContabeisImport lanctoContabil = null;
		int lote = contabilidadeCustom.findNextLote();
		int numLancto = contabilidadeCustom.findNextNumLancto();
		int sequencia = 1;
		
		String[] periodoConcat = datainsercao.split("[-]");
        String periodo = periodoConcat[1];
        
		for (ConsultaLanctoContabeis dadosLancto : listLancto) {
			int id = contabilidadeCustom.findNextId();			
			lanctoContabil = new LancamentoContabeisImport(id, dadosLancto.filialLancto, dadosLancto.exercicio, dadosLancto.origem, dadosLancto.contaReduzida,
					dadosLancto.debitoCredito, dadosLancto.valorLancto, dadosLancto.centroCusto, dadosLancto.histContabil, dadosLancto.dataLancto, dadosLancto.complHistor1,
					FormataData.parseStringToDate(datainsercao), usuario, lote, numLancto, sequencia, Integer.parseInt(periodo) );
			lanctoContabilImportacaoRepository.saveAndFlush(lanctoContabil);
			sequencia += 1;
		}
	}
}
