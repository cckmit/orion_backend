package br.com.live.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.TarefasCustom;
import br.com.live.entity.LancamentoHoras;
import br.com.live.entity.MetasPorUsuario;
import br.com.live.entity.Tarefas;
import br.com.live.model.ConsultaHorasLancadas;
import br.com.live.model.ConsultaHorasTarefa;
import br.com.live.model.IndicesMetasUsuario;
import br.com.live.repository.LancamentoHorasRepository;
import br.com.live.repository.MetasPorUsuarioRepository;
import br.com.live.repository.TarefasRepository;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;

@Service
@Transactional
public class TarefasService {

	private final TarefasRepository tarefasRepository;
	private final TarefasCustom tarefasCustom;
	private final LancamentoHorasRepository lancamentoHorasRepository;
	private final MetasPorUsuarioRepository metasPorUsuarioRepository;

	public TarefasService(TarefasRepository tarefasRepository, TarefasCustom tarefasCustom,
			LancamentoHorasRepository lancamentoHorasRepository, MetasPorUsuarioRepository metasPorUsuarioRepository) {
		this.tarefasRepository = tarefasRepository;
		this.tarefasCustom = tarefasCustom;
		this.lancamentoHorasRepository = lancamentoHorasRepository;
		this.metasPorUsuarioRepository = metasPorUsuarioRepository;
	}

	public Tarefas findUsuarios(int idTarefa) {
		return tarefasRepository.findByIdTarefa(idTarefa);
	}

	public Tarefas saveTarefas(int idTarefa, long anexo, String assunto, int origem, int sistema, int situacao,
			float tempoEstimado, String titulo, int usuarioAtribuido, int usuarioSolicitante, String dataPrevista,
			int numDocInterno, int numDocFornecedor, boolean tarefaPrincipal) {

		Tarefas dadosTarefa = null;

		// EDIÇÃO
		if (idTarefa > 0) {
			dadosTarefa = tarefasRepository.findByIdTarefa(idTarefa);

			dadosTarefa.anexo = anexo;
			dadosTarefa.assunto = assunto;
			dadosTarefa.origem = origem;
			dadosTarefa.sistema = sistema;
			dadosTarefa.situacao = situacao;
			dadosTarefa.tempoEstimado = tempoEstimado;
			dadosTarefa.titulo = titulo;
			dadosTarefa.usuarioAtribuido = usuarioAtribuido;
			dadosTarefa.dataPrevista = FormataData.parseStringToDate(dataPrevista);
			dadosTarefa.numDocInterno = numDocInterno;
			dadosTarefa.numDocFornecedor = numDocFornecedor;
			dadosTarefa.tarefaPrincipal = tarefaPrincipal;

			// INSERÇÃO
		} else {
			idTarefa = tarefasCustom.findNextIdTarefa();

			dadosTarefa = new Tarefas(idTarefa, sistema, origem, usuarioSolicitante, usuarioAtribuido, titulo, assunto,
					situacao, anexo, tempoEstimado, FormataData.parseStringToDate(dataPrevista), numDocInterno,
					numDocFornecedor, tarefaPrincipal);
		}

		tarefasRepository.save(dadosTarefa);

		return dadosTarefa;
	}

	public void saveLancamentoHoras(int idUsuario, int idTarefa, String dataLancamento, String descricao,
			float tempoGasto) {

		LancamentoHoras dadosLancamento = null;
		int sequencia;

		sequencia = tarefasCustom.findNextSequenciaLanc(idTarefa);
		
		dadosLancamento = new LancamentoHoras(idUsuario, idTarefa, descricao, tempoGasto,
				FormataData.parseStringToDate(dataLancamento), sequencia);

		lancamentoHorasRepository.save(dadosLancamento);
	}

	public List<ConsultaHorasTarefa> deleteLancamento(String idLancamento) {

		LancamentoHoras dadosLancamento = lancamentoHorasRepository.findByIdLancamento(idLancamento);

		lancamentoHorasRepository.deleteById(idLancamento);
		lancamentoHorasRepository.flush();

		return tarefasCustom.findAllLancamentosTarefa(dadosLancamento.idTarefa);
	}

	public void fecharTarefa(int idTarefa) {

		Tarefas dadosTarefa = null;

		dadosTarefa = tarefasRepository.findByIdTarefa(idTarefa);

		dadosTarefa.situacao = 1;

		tarefasRepository.save(dadosTarefa);
	}

	public List<ConsultaHorasLancadas> obterHorasLancadas(List<ConteudoChaveNumerica> usuarios, String dataInicio,
			String dataFim) {
		List<ConsultaHorasLancadas> dadosHorasLanc = new ArrayList<ConsultaHorasLancadas>();
		List<ConsultaHorasLancadas> ListDadosFormat = new ArrayList<ConsultaHorasLancadas>();
		
		float horaPerc = 0;
		float percAprov = 0;
		float totalHoras = 0;

		dadosHorasLanc = tarefasCustom.findTotalHorasOrigemLanc(usuarios, dataInicio, dataFim);
		totalHoras = tarefasCustom.findTotalHorasLancadasMes(usuarios, dataInicio, dataFim);

		for (ConsultaHorasLancadas hora : dadosHorasLanc) {

			horaPerc = hora.horaTotalOrigem * 100;
			percAprov = horaPerc / totalHoras;

			hora.percentual = percAprov;

			ListDadosFormat.add(hora);
		}
		return dadosHorasLanc;
	}

	public List<MetasPorUsuario> findMetasLancadoEfetivo(int codUsuario) {
		List<MetasPorUsuario> listMetasUser = new ArrayList<MetasPorUsuario>();

		MetasPorUsuario dadosMetasLancado = tarefasCustom.findValoresMetasHorasUsuario(codUsuario, 1);
		MetasPorUsuario dadosMetasEfetivo = tarefasCustom.findValoresMetasHorasUsuario(codUsuario, 2);

		
		if (dadosMetasLancado == null) {
			dadosMetasLancado = new MetasPorUsuario(codUsuario, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			listMetasUser.add(dadosMetasLancado);
		} else {
			listMetasUser.add(dadosMetasLancado);
		}

		if (dadosMetasEfetivo == null) {
			dadosMetasEfetivo = new MetasPorUsuario(codUsuario, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			listMetasUser.add(dadosMetasEfetivo);
		} else {
			listMetasUser.add(dadosMetasEfetivo);
		}
		
		return listMetasUser;
	}
	
	public void salvarMetasPorUsuarios(int codUsuario, List<MetasPorUsuario> metas) {
		
		for (MetasPorUsuario dadosMeta : metas) {
			String id = codUsuario + "-" + dadosMeta.tipoMeta;
			
			MetasPorUsuario metaGravada = metasPorUsuarioRepository.findByIdMeta(id);
			
			if (metaGravada == null) {
				metaGravada = new MetasPorUsuario(codUsuario, dadosMeta.tipoMeta, dadosMeta.mes1, dadosMeta.mes2, dadosMeta.mes3, dadosMeta.mes4, dadosMeta.mes5, dadosMeta.mes6, dadosMeta.mes7, dadosMeta.mes8, dadosMeta.mes9, dadosMeta.mes10, dadosMeta.mes11, dadosMeta.mes12);
			} else {
				metaGravada.mes1 = dadosMeta.mes1;
				metaGravada.mes2 = dadosMeta.mes2;
				metaGravada.mes3 = dadosMeta.mes3;
				metaGravada.mes4 = dadosMeta.mes4;
				metaGravada.mes5 = dadosMeta.mes5;
				metaGravada.mes6 = dadosMeta.mes6;
				metaGravada.mes7 = dadosMeta.mes7;
				metaGravada.mes8 = dadosMeta.mes8;
				metaGravada.mes9 = dadosMeta.mes9;
				metaGravada.mes10 = dadosMeta.mes10;
				metaGravada.mes11 = dadosMeta.mes11;
				metaGravada.mes12 = dadosMeta.mes12;
			}
			
			metasPorUsuarioRepository.save(metaGravada);
		}
	}
	
	public IndicesMetasUsuario obterMetaLancadoValoresHorasUsuario(List<ConteudoChaveNumerica> usuarios, String dataInicio, String dataFim, int tipoMeta) {
		
		float totalHoras = 0;
		
		if (tipoMeta == 1) {
			totalHoras = tarefasCustom.obterTotalHorasLancadas(usuarios, dataInicio, dataFim);
		} else {
			totalHoras = tarefasCustom.obterTotalHorasLancadasEfetivo(usuarios, dataInicio, dataFim);
		}
		
		String[] dataInicioSplit = dataInicio.split("[-]");
		String mes = dataInicioSplit [1];
		
		float totalMetaLancado = tarefasCustom.obterMetaLancadoMesUsuarioByMes(usuarios, mes, tipoMeta);
		
		float valorPerc = totalHoras * 100;
		float percIndice = 0;
		
		if (totalMetaLancado > 0) {
			percIndice = valorPerc / totalMetaLancado;
		}
		
		IndicesMetasUsuario dadosMeta = new IndicesMetasUsuario(tipoMeta, totalHoras, totalMetaLancado, percIndice);
		
		return dadosMeta;
	}
	
	public List<IndicesMetasUsuario> obterListaGridDisponivel(List<ConteudoChaveNumerica> usuarios, String dataInicio, String dataFim, int tipoMeta) {
		IndicesMetasUsuario dadosMetas = obterMetaLancadoValoresHorasUsuario(usuarios, dataInicio, dataFim, tipoMeta);
		
		List<IndicesMetasUsuario> listMetasGrid = new ArrayList<IndicesMetasUsuario>();
		
		listMetasGrid.add(dadosMetas);
		
		return listMetasGrid;
	}
	
}
