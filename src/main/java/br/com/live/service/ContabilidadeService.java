package br.com.live.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.live.custom.ContabilidadeCustom;
import br.com.live.entity.LancamentoContabeisImport;
import br.com.live.model.ConsultaLanctoContabeis;
import br.com.live.model.RetornoLancamentoCont;
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
	
	public String validarEmpresa(int empresa){
		
		int extistsEmpresa = contabilidadeCustom.findEmpresa(empresa);
		String mensagem = "";
		
		if(extistsEmpresa == 0) {
			mensagem = "Empresa não Cadastrada";						
		}
		return mensagem;
	}
	
	public String validarCentroCusto(int centroCusto){
		
		int extistsCCusto = contabilidadeCustom.findCentroCusto(centroCusto);
		String mensagem = "";
		
		if(extistsCCusto == 0) {
			mensagem = "Centro de Custo não Cadastrado";						
		}
		return mensagem;
	}
	
	public String validarOrigem(int origem){
		
		int extistsOrigem = contabilidadeCustom.findOrigem(origem);
		String mensagem = "";
		
		if(extistsOrigem == 0) {
			mensagem = "Origem não Cadastrada";						
		}
		return mensagem;
	}
	
	public String validarContaContabil(int contaReduzida){
		
		int extistsContaReduzida = contabilidadeCustom.findContaContabil(contaReduzida);
		String mensagem = "";
		
		if(extistsContaReduzida == 0) {
			mensagem = "Conta Contábil não Cadastrada";						
		}
		return mensagem;
	}
	
	public String validarHistoricoContabil(int historicoContabil){
		
		int extistsHistContabil = contabilidadeCustom.findHistoricoContabil(historicoContabil);
		String mensagem = "";
		
		if(extistsHistContabil == 0) {
			mensagem = "Histórico Contábil não Cadastrado";						
		}
		return mensagem;
	}
	
	public RetornoLancamentoCont importarLancamentosContabeis(List<ConsultaLanctoContabeis> listLancto, String usuario, String datainsercao) {
		// Deletando tudo na Tabela orion_cnt_010
		lanctoContabilImportacaoRepository.deleteAll();
		RetornoLancamentoCont listRetorno = null;
		
		LancamentoContabeisImport lanctoContabil = null;
		int sequencia = 1;
		int status = 1;
		String criticas = "";
		String[] periodoConcat = datainsercao.split("[-]");
        String periodo = periodoConcat[1];
        
		for (ConsultaLanctoContabeis dadosLancto : listLancto) {
			int id = contabilidadeCustom.findNextId();
			String origem = validarOrigem(dadosLancto.origem);
			String contaReduzida = validarContaContabil(dadosLancto.contaReduzida);
			String empresa = validarEmpresa(dadosLancto.filialLancto);
			String centroCusto = validarCentroCusto(dadosLancto.centroCusto);
			String histContabil = validarHistoricoContabil(dadosLancto.histContabil);
			if(origem  == "" && contaReduzida  == "" && empresa == "" && centroCusto == "" && histContabil == "") {
				status = 0;
			}
			try {
				criticas = (empresa + "/" + origem + "/" + contaReduzida + "/" + centroCusto + "/" + histContabil);				
				lanctoContabil = new LancamentoContabeisImport(id, dadosLancto.filialLancto, dadosLancto.exercicio, dadosLancto.origem, dadosLancto.contaReduzida,
						dadosLancto.debitoCredito, dadosLancto.valorLancto, dadosLancto.centroCusto, dadosLancto.histContabil, FormataData.parseStringToDate(dadosLancto.dataLancto), dadosLancto.complHistor1,
						FormataData.parseStringToDate(datainsercao), usuario, 0, 0, sequencia, Integer.parseInt(periodo), status, criticas);
				lanctoContabilImportacaoRepository.saveAndFlush(lanctoContabil);
				sequencia += 1;				
			} catch (Exception e) {
				System.out.println(e);
			}
			status = 1;
		}
		listRetorno = new RetornoLancamentoCont(contabilidadeCustom.findStatusByLancto(usuario), contabilidadeCustom.findAllLanctoContabeis(usuario));
		return listRetorno;
	}
	
	public int salvarSystextil(String usuario) {
		
		int mensagem = 0;
		List<LancamentoContabeisImport> listDados =  lanctoContabilImportacaoRepository.findByUser(usuario);
		int lote = contabilidadeCustom.findNextLote();
		int numLancto = contabilidadeCustom.findNextNumLancto();
		String programa = "Orion";
		
		try {
			for (LancamentoContabeisImport dados : listDados) {
				String contaContabil = contabilidadeCustom.findContaContabByContaRed(dados.contaReduzida);
				contabilidadeCustom.inserirLanctoContabilSystextil(dados.filialLancto, dados.filialLancto, dados.exercicio, dados.origem, contaContabil, dados.contaReduzida, 
						dados.debitoCredito, dados.valorLancto, dados.centroCusto, dados.histContabil, dados.dataLancto, dados.complHistor1, dados.datainsercao, programa, 
						dados.usuario, lote, numLancto, dados.seqLanc, dados.periodo, dados.status);
			}
			mensagem = 1;
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return mensagem;
	}
}
