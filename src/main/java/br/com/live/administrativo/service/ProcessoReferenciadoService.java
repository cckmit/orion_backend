package br.com.live.administrativo.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.administrativo.custom.ProcessoReferenciadoCustom;
import br.com.live.comercial.model.ConsultaNotaFiscal;
import br.com.live.util.FormataData;
import br.com.live.util.repository.ParametrosRepository;

@Service
@Transactional
public class ProcessoReferenciadoService {
	
	private final ProcessoReferenciadoCustom processoReferenciadoCustom;
	private final ParametrosRepository parametrosRepository;  
	
	public ProcessoReferenciadoService(ProcessoReferenciadoCustom processoReferenciadoCustom, ParametrosRepository parametrosRepository) {
		this.processoReferenciadoCustom = processoReferenciadoCustom;
		this.parametrosRepository = parametrosRepository;
	}
	
	public void atualizarNotas() {
		// Parâmetros
		String empresas = parametrosRepository.findByIdParametro("PROC_REFERENCIADO-EMPRESAS").getValorStr();
		String naturezas = parametrosRepository.findByIdParametro("PROC_REFERENCIADO-NATUREZAS").getValorStr();
		Date dataInicial = parametrosRepository.findByIdParametro("PROC_REFERENCIADO-DATA-INICIAL").getValorDate();
		
		List<ConsultaNotaFiscal> listaNotas = processoReferenciadoCustom.findNotasProcessoReferenciado(empresas, naturezas, dataInicial);

		// OBRF_701
		String registro = parametrosRepository.findByIdParametro("OBRF_701-REG").getValorStr();
		String numProcesso = parametrosRepository.findByIdParametro("OBRF_701-NUM_PROC").getValorStr();
		String regPai = parametrosRepository.findByIdParametro("OBRF_701-REG_PAI").getValorStr();
		int indProcesso = parametrosRepository.findByIdParametro("OBRF_701-IND_PROC").getValorInt();
		int codCred = parametrosRepository.findByIdParametro("OBRF_701-COD_CRED").getValorInt();
		
		// OBRF_702
		String registroJudAdm = parametrosRepository.findByIdParametro("OBRF_702-REG").getValorStr();
		String regPaiJudAdm = parametrosRepository.findByIdParametro("OBRF_702-REG_PAI_1").getValorStr();
		String idSecJud = parametrosRepository.findByIdParametro("OBRF_702-ID_SEC_JUD").getValorStr();
		String idVara = parametrosRepository.findByIdParametro("OBRF_702-ID_VARA").getValorStr();
		String descDecisaoJud = parametrosRepository.findByIdParametro("OBRF_702-DESC_DEC_JUD").getValorStr();
		Date dtSentencaJud = parametrosRepository.findByIdParametro("OBRF_702-DT_SENT_JUD").getValorDate();
		Date dtDecisaoJud = parametrosRepository.findByIdParametro("OBRF_702-DT_DEC_ADM").getValorDate();
		int indNat10acao = parametrosRepository.findByIdParametro("OBRF_702-IND_NAT_10_ACAO").getValorInt();
		int indNat20acao = parametrosRepository.findByIdParametro("OBRF_702-IND_NAT_20_ACAO").getValorInt();				
		int natBcCred = parametrosRepository.findByIdParametro("OBRF_702-NAT_BC_CRED").getValorInt();
		int indOrigCred = parametrosRepository.findByIdParametro("OBRF_702-IND_ORIG_CRED").getValorInt();
						
		System.out.println("INICIO GRAVAÇÃO PROCESSOS REFERENCIADOS");
		// leitura nas notas e gravação das tabelas de processos referenciados.
		for (ConsultaNotaFiscal notaFiscal : listaNotas) {					
			processoReferenciadoCustom.insereProcessoReferenciado(registro, numProcesso, indProcesso, regPai, notaFiscal.getCodEmpresa(), notaFiscal.getNotaFiscal(), notaFiscal.getSerieFiscal(), codCred, FormataData.getMonthOfDate(notaFiscal.getDataEmissao()),FormataData.getYearOfDate(notaFiscal.getDataEmissao()), notaFiscal.getCnpj9(), notaFiscal.getCnpj4(), notaFiscal.getCnpj2(), natBcCred, indOrigCred);
			processoReferenciadoCustom.insereProcessoReferenciadoJudicialAdministrativo(registroJudAdm, numProcesso, indProcesso, regPaiJudAdm, notaFiscal.getCodEmpresa(), notaFiscal.getNotaFiscal(), notaFiscal.getSerieFiscal(), codCred, FormataData.getMonthOfDate(notaFiscal.getDataEmissao()),FormataData.getYearOfDate(notaFiscal.getDataEmissao()), notaFiscal.getCnpj9(), notaFiscal.getCnpj4(), notaFiscal.getCnpj2(), idSecJud, idVara, indNat10acao, indNat20acao, descDecisaoJud, dtSentencaJud, dtDecisaoJud, natBcCred, indOrigCred, regPai);		
		}
		System.out.println("TÉRMINO GRAVAÇÃO PROCESSOS REFERENCIADOS");
	}
}
