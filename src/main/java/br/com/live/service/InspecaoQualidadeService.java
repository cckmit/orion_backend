package br.com.live.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.InspecaoQualidadeCustom;
import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.entity.InspecaoQualidadePeca;
import br.com.live.entity.InspecaoQualidadePecaLancto;
import br.com.live.model.MotivoRejeicao;
import br.com.live.model.OrdemConfeccao;
import br.com.live.repository.InspecaoQualidadeMedidaRepository;
import br.com.live.repository.InspecaoQualidadePecaLanctoRepository;
import br.com.live.repository.InspecaoQualidadePecaRepository;
import br.com.live.util.BodyInspecaoQualidade;
import br.com.live.util.FormataData;

@Service
@Transactional
public class InspecaoQualidadeService {

	private final InspecaoQualidadePecaRepository inspecaoQualidadePecaRepository;
	private final InspecaoQualidadePecaLanctoRepository inspecaoQualidadePecaLanctoRepository;
	private final InspecaoQualidadeMedidaRepository inspecaoQualidadeMedidasRepository;
	private final InspecaoQualidadeCustom inspecaoQualidadeCustom;
	private final OrdemProducaoCustom ordemProducaoCustom;

	public InspecaoQualidadeService(InspecaoQualidadePecaRepository inspecaoQualidadeRepository,
			InspecaoQualidadePecaLanctoRepository inspecaoQualidadePecasRepository,
			InspecaoQualidadeMedidaRepository inspecaoQualidadeMedidasRepository,
			InspecaoQualidadeCustom inspecaoQualidadeCustom,
			OrdemProducaoCustom ordemProducaoCustom) {
		this.inspecaoQualidadePecaRepository = inspecaoQualidadeRepository;
		this.inspecaoQualidadePecaLanctoRepository = inspecaoQualidadePecasRepository;
		this.inspecaoQualidadeMedidasRepository = inspecaoQualidadeMedidasRepository;
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
	
	public InspecaoQualidadePeca saveInspecaoQualidadePeca(InspecaoQualidadePeca inspecaoQualidadePeca, InspecaoQualidadePecaLancto inspecaoQualidadePecaLancto, String data) {
				
		InspecaoQualidadePeca inspecao = inspecaoQualidadePecaRepository.findById(inspecaoQualidadePeca.id);
		
		if (inspecao == null) {
			inspecao = new InspecaoQualidadePeca();
			inspecao.id = inspecaoQualidadeCustom.findNextIdInspecaoPeca();
			inspecao.data = FormataData.parseStringToDate(data);
			inspecao.usuario = inspecaoQualidadePeca.usuario;
			inspecao.turno = inspecaoQualidadePeca.turno;
			inspecao.ordemProducao = inspecaoQualidadePeca.ordemProducao;
			inspecao.ordemConfeccao = inspecaoQualidadePeca.ordemConfeccao;
			inspecao.periodo = inspecaoQualidadePeca.periodo;
			inspecao.percInspecionarPcs = inspecaoQualidadePeca.percInspecionarPcs;
			inspecao.qtdeInspecionarPcs = inspecaoQualidadePeca.qtdeInspecionarPcs;			
			inspecao = inspecaoQualidadePecaRepository.saveAndFlush(inspecao);
		}
		
		InspecaoQualidadePecaLancto lancamento = new InspecaoQualidadePecaLancto();		
		lancamento.id = inspecaoQualidadeCustom.findNextIdInspecaoPecaLancamento();
		lancamento.idInspecao = inspecao.id;
		lancamento.codMotivo = inspecaoQualidadePecaLancto.codMotivo;
		lancamento.quantidade = inspecaoQualidadePecaLancto.quantidade;
		lancamento.usuario = inspecaoQualidadePecaLancto.usuario;
		lancamento.dataHora = new Date();
		inspecaoQualidadePecaLanctoRepository.saveAndFlush(lancamento);
		
		inspecao.atualizaQuantidadesInpecionadas(inspecaoQualidadeCustom.getQtdeInspecionadaPeca(inspecao.id), inspecaoQualidadeCustom.getQtdeRejeitadaPeca(inspecao.id));
		inspecaoQualidadePecaRepository.saveAndFlush(inspecao);		
		
		return inspecao;
	}
}
