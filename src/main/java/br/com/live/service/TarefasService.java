package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.TarefasCustom;
import br.com.live.entity.LancamentoHoras;
import br.com.live.entity.Tarefas;
import br.com.live.model.ConsultaHorasTarefa;
import br.com.live.repository.LancamentoHorasRepository;
import br.com.live.repository.TarefasRepository;
import br.com.live.util.FormataData;

@Service
@Transactional
public class TarefasService {

	private final TarefasRepository tarefasRepository;
	private final TarefasCustom tarefasCustom;
	private final LancamentoHorasRepository lancamentoHorasRepository;

	public TarefasService(TarefasRepository tarefasRepository, TarefasCustom tarefasCustom,
			LancamentoHorasRepository lancamentoHorasRepository) {
		this.tarefasRepository = tarefasRepository;
		this.tarefasCustom = tarefasCustom;
		this.lancamentoHorasRepository = lancamentoHorasRepository;
	}

	public Tarefas findUsuarios(int idTarefa) {
		return tarefasRepository.findByIdTarefa(idTarefa);
	}

	public Tarefas saveTarefas(int idTarefa, long anexo, String assunto, int origem, int sistema, int situacao,
			float tempoEstimado, String titulo, int usuarioAtribuido, int usuarioSolicitante,
			String dataPrevista, int numDocInterno, int numDocFornecedor) {

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

			// INSERÇÃO
		} else {
			idTarefa = tarefasCustom.findNextIdTarefa();

			dadosTarefa = new Tarefas(idTarefa, sistema, origem, usuarioSolicitante, usuarioAtribuido, titulo,
					assunto, situacao, anexo, tempoEstimado, FormataData.parseStringToDate(dataPrevista),numDocInterno, numDocFornecedor);
		}

		tarefasRepository.save(dadosTarefa);

		return dadosTarefa;
	}

	public void saveLancamentoHoras(int idUsuario, int idTarefa, String dataLancamento,
			String descricao, float tempoGasto) {

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

}
