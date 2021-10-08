package br.com.live.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.body.BodyInspecaoQualidade;
import br.com.live.custom.InspecaoQualidadeCustom;
import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.entity.InspecaoQualidade;
import br.com.live.entity.InspecaoQualidadeLanctoMedida;
import br.com.live.entity.InspecaoQualidadeLanctoPeca;
import br.com.live.entity.Usuario;
import br.com.live.model.ConsultaInspecaoQualidLanctoMedidas;
import br.com.live.model.ConsultaInspecaoQualidLanctoPecas;
import br.com.live.model.MotivoRejeicao;
import br.com.live.model.OrdemConfeccao;
import br.com.live.model.TipoMedida;
import br.com.live.repository.InspecaoQualidadeLanctoMedidaRepository;
import br.com.live.repository.InspecaoQualidadeLanctoPecaRepository;
import br.com.live.repository.InspecaoQualidadeRepository;
import br.com.live.repository.UsuarioRepository;
import br.com.live.util.FormataData;

@Service
@Transactional
public class InspecaoQualidadeService {

	private final InspecaoQualidadeRepository inspecaoQualidadeRepository;
	private final InspecaoQualidadeLanctoPecaRepository inspecaoQualidadeLanctoPecaRepository;
	private final InspecaoQualidadeLanctoMedidaRepository inspecaoQualidadeLanctoMedidaRepository;
	private final InspecaoQualidadeCustom inspecaoQualidadeCustom;
	private final OrdemProducaoCustom ordemProducaoCustom;
	private final UsuarioRepository usuarioRepository;

	public InspecaoQualidadeService(InspecaoQualidadeRepository inspecaoQualidadeRepository,
			InspecaoQualidadeLanctoPecaRepository inspecaoQualidadeLanctoPecaRepository,
			InspecaoQualidadeLanctoMedidaRepository inspecaoQualidadeLanctoMedidaRepository,
			InspecaoQualidadeCustom inspecaoQualidadeCustom,
			OrdemProducaoCustom ordemProducaoCustom,
			UsuarioRepository usuarioRepository) {
		this.inspecaoQualidadeRepository = inspecaoQualidadeRepository;
		this.inspecaoQualidadeLanctoPecaRepository = inspecaoQualidadeLanctoPecaRepository;
		this.inspecaoQualidadeLanctoMedidaRepository = inspecaoQualidadeLanctoMedidaRepository;
		this.inspecaoQualidadeCustom = inspecaoQualidadeCustom;
		this.ordemProducaoCustom = ordemProducaoCustom;
		this.usuarioRepository = usuarioRepository;
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
	
	public List<TipoMedida> findTiposMedidasByReferencia(String referencia) {
		return inspecaoQualidadeCustom.findTiposMedidasByReferencia(referencia);
	}
	
 	public List<InspecaoQualidadeLanctoMedida> findMedidasByOrdemProducaoConfeccaoTipoMedida(int ordemProducao, int ordemConfeccao, int tipoMedida) {
		List<InspecaoQualidadeLanctoMedida> lancamentos;		
		OrdemConfeccao dadosOrdemConfeccao = ordemProducaoCustom.findOrdemConfeccaoByOrdemProducaoConfeccao(ordemProducao, ordemConfeccao);
		lancamentos = inspecaoQualidadeCustom.findMedidasByReferenciaTamanhoTipo(dadosOrdemConfeccao.referencia, dadosOrdemConfeccao.tamanho, tipoMedida);					
		return lancamentos; 
	}
	
	public List<InspecaoQualidadeLanctoMedida> findMedidasByOrdemProducaoConfeccaoIdInspecaoIdLancamento(int ordemProducao, int ordemConfeccao, int idInspecao, int idLancamento) {
		List<InspecaoQualidadeLanctoMedida> lancamentos;
		lancamentos = inspecaoQualidadeCustom.findMedidasByIdInspecaoIdLancamento(idInspecao, idLancamento);						
		return lancamentos; 
	}
	
	public List<MotivoRejeicao> findAllMotivos() {
		return inspecaoQualidadeCustom.findAllMotivos();		
	}
	
	public String findTerceiroByOrdemPacoteEstagio(int ordemProducao, int ordemConfeccao, int estagio) {
		return inspecaoQualidadeCustom.findTerceiroByOrdemPacoteEstagio(ordemProducao, ordemConfeccao, estagio);
	}
	
	public List<InspecaoQualidade> findInspecoesQualidadeByOrdemEstagioTipo(int ordemProducao, int ordemConfeccao, int codEstagio, int tipo) {
		return inspecaoQualidadeRepository.findAllByOrdemProdConfecEstagioTipo(ordemProducao, ordemConfeccao, codEstagio, tipo);		
	}
	
	public List<ConsultaInspecaoQualidLanctoPecas> findLancamentoPecasByIdInspecao(long idInspecao) {
		return inspecaoQualidadeCustom.findLancamentoPecasByIdInspecao(idInspecao);
	}

	public List<ConsultaInspecaoQualidLanctoMedidas> findLancamentoMedidasByIdInspecao(long idInspecao) {
		return inspecaoQualidadeCustom.findLancamentoMedidasByIdInspecao(idInspecao);
	}
	
	public InspecaoQualidade findInspecaoQualidadeById(long id) {
		return inspecaoQualidadeRepository.findById(id);
	}
	
	public List<Usuario> findUsuariosLiberaInspecao() {		
		return usuarioRepository.findByLiberaInspecaoQualidade(1);
	}	
	
	public InspecaoQualidade gravaInspecaoQualidade(InspecaoQualidade inspecaoQualidadePeca, String data) {
		
		InspecaoQualidade inspecao = inspecaoQualidadeRepository.findById(inspecaoQualidadePeca.id);
		
		if (inspecao == null) {
			inspecao = new InspecaoQualidade();
			inspecao.id = inspecaoQualidadeCustom.findNextIdInspecao();
			inspecao.tipo = inspecaoQualidadePeca.tipo;
			inspecao.codEstagio = inspecaoQualidadePeca.codEstagio;
			inspecao.data = FormataData.parseStringToDate(data);
			inspecao.usuario = inspecaoQualidadePeca.usuario;
			inspecao.turno = inspecaoQualidadePeca.turno;
			inspecao.ordemProducao = inspecaoQualidadePeca.ordemProducao;
			inspecao.ordemConfeccao = inspecaoQualidadePeca.ordemConfeccao;
			inspecao.periodo = inspecaoQualidadePeca.periodo;
			inspecao.percInspecionarPcs = inspecaoQualidadePeca.percInspecionarPcs;
			inspecao.qtdeInspecionarPcs = inspecaoQualidadePeca.qtdeInspecionarPcs;
			inspecao.tipoInspecao = inspecaoQualidadePeca.tipoInspecao;
			inspecao.status = 1; // LIBERADO
			inspecao = inspecaoQualidadeRepository.saveAndFlush(inspecao);
		}
				
		return inspecao;
	}
	
	public InspecaoQualidade saveInspecaoQualidadePeca(InspecaoQualidade inspecaoQualidadePeca, InspecaoQualidadeLanctoPeca inspecaoQualidadePecaLancto, String data) {
		
		InspecaoQualidade inspecao = gravaInspecaoQualidade(inspecaoQualidadePeca, data);
				
		InspecaoQualidadeLanctoPeca lancamento = new InspecaoQualidadeLanctoPeca();		
		lancamento.id = inspecaoQualidadeCustom.findNextIdLancamentoPeca();
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
	 
	public InspecaoQualidade saveInspecaoQualidadeMedida(InspecaoQualidade inspecaoQualidadePeca, List<InspecaoQualidadeLanctoMedida> inspecaoQualidadeLanctoMedidas, String data) {
		
		InspecaoQualidade inspecao = gravaInspecaoQualidade(inspecaoQualidadePeca, data);
		
		long id = 0;
		long idLancamento = 0; 
		
		for (InspecaoQualidadeLanctoMedida lancamento : inspecaoQualidadeLanctoMedidas) {
			
			if (idLancamento == 0) {
				if (lancamento.idLancamento > 0) {				
					idLancamento = lancamento.idLancamento;
				} else {
					idLancamento = inspecaoQualidadeCustom.findNextIdLancamentoMedida(inspecao.id);
				}
			}
						
			id = inspecaoQualidadeCustom.findNextIdMedida(inspecao.id, idLancamento, lancamento.sequencia);
			InspecaoQualidadeLanctoMedida medida = inspecaoQualidadeLanctoMedidaRepository.findById(id);
			
			if (medida == null) {				
				medida = new InspecaoQualidadeLanctoMedida();
				
				medida.id = id;
				medida.idLancamento = idLancamento;
				medida.idInspecao = inspecao.id;
				medida.sequencia = lancamento.sequencia;
				medida.descricao = lancamento.descricao;
				medida.medidaPadrao = lancamento.medidaPadrao;
				medida.toleranciaMaxima = lancamento.toleranciaMaxima;
				medida.toleranciaMinima = lancamento.toleranciaMinima;
				medida.medidaReal = lancamento.medidaReal;
				medida.variacao = lancamento.variacao;
				medida.usuario = lancamento.usuario;
				medida.dataHora = new Date();
			}
			
			medida.medidaReal = lancamento.medidaReal;
			medida.variacao = lancamento.variacao;
			medida.usuario = lancamento.usuario;
			medida.dataHora = new Date();
			
			inspecaoQualidadeLanctoMedidaRepository.saveAndFlush(medida);
		}		
		
		inspecao.atualizaQuantidadesInpecionadas(inspecaoQualidadeCustom.getQtdeInspecionadaMedida(inspecao.id), inspecaoQualidadeCustom.getQtdeRejeitadaMedida(inspecao.id));		
		inspecaoQualidadeRepository.saveAndFlush(inspecao);		
		
		return inspecao;
	}
		
	public InspecaoQualidade deleteInspecaoQualidadeLanctoPeca(long idInspecao, long idLancamento) {
		inspecaoQualidadeLanctoPecaRepository.deleteById(idLancamento);		
		inspecaoQualidadeLanctoPecaRepository.flush();
		InspecaoQualidade inspecao = inspecaoQualidadeRepository.findById(idInspecao);
		inspecao.atualizaQuantidadesInpecionadas(inspecaoQualidadeCustom.getQtdeInspecionadaPeca(inspecao.id), inspecaoQualidadeCustom.getQtdeRejeitadaPeca(inspecao.id));		
		inspecaoQualidadeRepository.saveAndFlush(inspecao);		
		return inspecao; 
	}	
		
	public InspecaoQualidade deleteInspecaoQualidadeLanctoMedida(long idInspecao, long idLancamento) {
		inspecaoQualidadeLanctoMedidaRepository.deleteByIdInspecaoAndIdLancamento(idInspecao, idLancamento);
		inspecaoQualidadeLanctoMedidaRepository.flush();		
		InspecaoQualidade inspecao = inspecaoQualidadeRepository.findById(idInspecao);
		inspecao.atualizaQuantidadesInpecionadas(inspecaoQualidadeCustom.getQtdeInspecionadaMedida(inspecao.id), inspecaoQualidadeCustom.getQtdeRejeitadaMedida(inspecao.id));		
		inspecaoQualidadeRepository.saveAndFlush(inspecao);		
		return inspecao; 
	}		
	
	public List<InspecaoQualidade> deleteInspecaoQualidade(long idInspecao) {		
		InspecaoQualidade inspecao = findInspecaoQualidadeById(idInspecao);
		int ordemProducao = inspecao.ordemProducao; 
		int ordemConfeccao = inspecao.ordemConfeccao;
		int codEstagio = inspecao.codEstagio;
		int tipo= inspecao.tipo;		
		inspecaoQualidadeLanctoPecaRepository.deleteByIdInspecao(idInspecao);
		inspecaoQualidadeLanctoMedidaRepository.deleteByIdInspecao(idInspecao);
		inspecaoQualidadeRepository.deleteById(idInspecao);
		inspecaoQualidadeLanctoPecaRepository.flush();
		inspecaoQualidadeLanctoMedidaRepository.flush();
		inspecaoQualidadeRepository.flush();
		return findInspecoesQualidadeByOrdemEstagioTipo(ordemProducao, ordemConfeccao, codEstagio, tipo);
	}
	
	public InspecaoQualidade liberarInspecaoQualidade(long idInspecao, String usuario, String observacao) {
		InspecaoQualidade inspecao = findInspecaoQualidadeById(idInspecao);
		inspecao.status = 3; // Liberado com observação
		inspecao.usuarioLiberacao = usuario;
		inspecao.statusObservacao = observacao;		
		return inspecao; 
	}
}
