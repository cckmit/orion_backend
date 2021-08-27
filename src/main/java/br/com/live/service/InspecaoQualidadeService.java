package br.com.live.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.InspecaoQualidadeCustom;
import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.entity.InspecaoQualidade;
import br.com.live.entity.InspecaoQualidadeLanctoPeca;
import br.com.live.model.ConsultaInspecaoQualidLanctoPecas;
import br.com.live.model.MotivoRejeicao;
import br.com.live.model.OrdemConfeccao;
import br.com.live.repository.InspecaoQualidadeLanctoMedidaRepository;
import br.com.live.repository.InspecaoQualidadeLanctoPecaRepository;
import br.com.live.repository.InspecaoQualidadeRepository;
import br.com.live.util.BodyInspecaoQualidade;
import br.com.live.util.FormataData;

@Service
@Transactional
public class InspecaoQualidadeService {

	private final InspecaoQualidadeRepository inspecaoQualidadeRepository;
	private final InspecaoQualidadeLanctoPecaRepository inspecaoQualidadeLanctoPecaRepository;
	private final InspecaoQualidadeLanctoMedidaRepository inspecaoQualidadeLanctoMedidaRepository;
	private final InspecaoQualidadeCustom inspecaoQualidadeCustom;
	private final OrdemProducaoCustom ordemProducaoCustom;

	public InspecaoQualidadeService(InspecaoQualidadeRepository inspecaoQualidadeRepository,
			InspecaoQualidadeLanctoPecaRepository inspecaoQualidadeLanctoPecaRepository,
			InspecaoQualidadeLanctoMedidaRepository inspecaoQualidadeLanctoMedidaRepository,
			InspecaoQualidadeCustom inspecaoQualidadeCustom,
			OrdemProducaoCustom ordemProducaoCustom) {
		this.inspecaoQualidadeRepository = inspecaoQualidadeRepository;
		this.inspecaoQualidadeLanctoPecaRepository = inspecaoQualidadeLanctoPecaRepository;
		this.inspecaoQualidadeLanctoMedidaRepository = inspecaoQualidadeLanctoMedidaRepository;
		this.inspecaoQualidadeCustom = inspecaoQualidadeCustom;
		this.ordemProducaoCustom = ordemProducaoCustom;
	}

	private int[] parseTalaoToArrayDados(String talao) {
		int periodo = 0;
		int ordemConfeccao = 0;
		int ordemProducao = 0;

		if (talao.contains(".")) {
			String[] codigoSeparado = talao.split("[.]");
			ordemProducao = Integer.parseInt(codigoSeparado[0]);
			ordemConfeccao = Integer.parseInt(codigoSeparado[1]);
		} else {
			if (talao.length() == 9) {
				periodo = Integer.parseInt(talao.substring(0, 4));
				ordemConfeccao = Integer.parseInt(talao.substring(4));
			}
		}

		return new int[] { periodo, ordemConfeccao, ordemProducao };
	}

	public BodyInspecaoQualidade findOrdemConfeccaoByTalao(String talao) {
					
		int[] dadosTalao = parseTalaoToArrayDados(talao);		
		int periodo = dadosTalao[0];
		int ordemConfeccao = dadosTalao[1];
		int ordemProducao = dadosTalao[2];
				
		OrdemConfeccao dadosOrdemConfeccao = ordemProducaoCustom.findOrdemConfeccaoByOrdProdPeriodoOrdConfec(ordemProducao, periodo, ordemConfeccao);		
		BodyInspecaoQualidade bodyRetorno = new BodyInspecaoQualidade(dadosOrdemConfeccao);
		
		return bodyRetorno;
	}
	
	public List<MotivoRejeicao> findAllMotivos() {
		return inspecaoQualidadeCustom.findAllMotivos();		
	}
	
	public List<InspecaoQualidade> findInspecoesQualidadeByOrdemAndEstagio(int ordemProducao, int ordemConfeccao, int codEstagio) {
		return inspecaoQualidadeRepository.findAllByOrdemProdConfecEstagio(ordemProducao, ordemConfeccao, codEstagio);		
	}
	
	public List<ConsultaInspecaoQualidLanctoPecas> findLancamentoPecasByIdInspecao(long idInspecao) {
		return inspecaoQualidadeCustom.findLancamentoPecasByIdInspecao(idInspecao);
	}
	
	public InspecaoQualidade findInspecaoQualidadeById(long id) {
		return inspecaoQualidadeRepository.findById(id);
	}
	
	public InspecaoQualidade saveInspecaoQualidadePeca(InspecaoQualidade inspecaoQualidadePeca, InspecaoQualidadeLanctoPeca inspecaoQualidadePecaLancto, String data) {
				
		InspecaoQualidade inspecao = inspecaoQualidadeRepository.findById(inspecaoQualidadePeca.id);
		
		if (inspecao == null) {
			inspecao = new InspecaoQualidade();
			inspecao.id = inspecaoQualidadeCustom.findNextIdInspecao();
			inspecao.data = FormataData.parseStringToDate(data);
			inspecao.usuario = inspecaoQualidadePeca.usuario;
			inspecao.turno = inspecaoQualidadePeca.turno;
			inspecao.ordemProducao = inspecaoQualidadePeca.ordemProducao;
			inspecao.ordemConfeccao = inspecaoQualidadePeca.ordemConfeccao;
			inspecao.periodo = inspecaoQualidadePeca.periodo;
			inspecao.percInspecionarPcs = inspecaoQualidadePeca.percInspecionarPcs;
			inspecao.qtdeInspecionarPcs = inspecaoQualidadePeca.qtdeInspecionarPcs;			
			inspecao = inspecaoQualidadeRepository.saveAndFlush(inspecao);
		}
		
		InspecaoQualidadeLanctoPeca lancamento = new InspecaoQualidadeLanctoPeca();		
		lancamento.id = inspecaoQualidadeCustom.findNextIdInspecaoLanctoPeca();
		lancamento.idInspecao = inspecao.id;
		lancamento.codMotivo = inspecaoQualidadePecaLancto.codMotivo;
		lancamento.quantidade = inspecaoQualidadePecaLancto.quantidade;
		lancamento.usuario = inspecaoQualidadePecaLancto.usuario;
		lancamento.dataHora = new Date();
		inspecaoQualidadeLanctoPecaRepository.saveAndFlush(lancamento);
		
		inspecao.atualizaQuantidadesInpecionadas(inspecaoQualidadeCustom.getQtdeInspecionadaPeca(inspecao.id), inspecaoQualidadeCustom.getQtdeRejeitadaPeca(inspecao.id));		
		inspecaoQualidadeRepository.saveAndFlush(inspecao);		
		
		return inspecao;
	}
}
