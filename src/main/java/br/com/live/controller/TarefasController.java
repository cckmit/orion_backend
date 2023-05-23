package br.com.live.controller;

import java.util.List;

import br.com.live.model.*;
import br.com.live.util.ConteudoChaveNumerica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.live.body.BodyLancamentoHoras;
import br.com.live.body.BodyTarefas;
import br.com.live.custom.TarefasCustom;
import br.com.live.entity.MetasPorUsuario;
import br.com.live.entity.Tarefas;
import br.com.live.repository.TarefasRepository;
import br.com.live.service.TarefasService;

@RestController
@CrossOrigin
@RequestMapping("/tarefas")
public class TarefasController {

	private TarefasRepository tarefasRepository;
	private TarefasService tarefasService;
	private TarefasCustom tarefasCustom;

	@Autowired
	public TarefasController(TarefasRepository tarefasRepository, TarefasService tarefasService,
			TarefasCustom tarefasCustom) {
		this.tarefasRepository = tarefasRepository;
		this.tarefasService = tarefasService;
		this.tarefasCustom = tarefasCustom;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Tarefas> findAll() {
		return tarefasRepository.findAll();
	}

	@RequestMapping(value = "/grid-tarefas/{listarAbertos}/{idUsuario}", method = RequestMethod.GET)
	public List<ConsultaGridTarefas> findAllTarefasGridConsulta(@PathVariable("listarAbertos") boolean listarAbertos, @PathVariable("idUsuario") int idUsuario) {
		return tarefasCustom.findAllTarefasGridConsulta(listarAbertos, idUsuario);
	}

	@RequestMapping(value = "/{idTarefa}", method = RequestMethod.GET)
	public Tarefas findByIdUsuario(@PathVariable("idTarefa") int idTarefa) {
		return tarefasRepository.findByIdTarefa(idTarefa);
	}

	@RequestMapping(value = "/find-all/{idUsuario}", method = RequestMethod.GET)
	public List<ConsultaDadosLancHoras> findAllByIdUsuario(@PathVariable("idUsuario") int idUsuario) {
		return tarefasCustom.findAllTarefas(idUsuario);
	}

	@RequestMapping(value = "/{idTarefa}/{listarAbertos}/{idUsuario}", method = RequestMethod.DELETE)
	public List<ConsultaGridTarefas> deleteById(@PathVariable("idTarefa") int idTarefa,
			@PathVariable("listarAbertos") boolean listarAbertos, @PathVariable("idUsuario") int idUsuario) {
		tarefasRepository.deleteById(idTarefa);
		return tarefasCustom.findAllTarefasGridConsulta(listarAbertos, idUsuario);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public Tarefas saveTarefas(@RequestBody BodyTarefas body) {
		return tarefasService.saveTarefas(body.id, body.anexo, body.assunto, body.origem, body.sistema, body.situacao,
				body.tempoEstimado, body.titulo, body.usuarioAtribuido, body.usuarioSolicitante, body.dataPrevista,
				body.numDocInterno, body.numDocFornecedor, body.tarefaPrincipal);
	}

	@RequestMapping(value = "/lancamento-horas/{idTarefa}", method = RequestMethod.GET)
	public List<ConsultaHorasTarefa> findAllLancamentosByIdTarefa(@PathVariable("idTarefa") int idTarefa) {
		return tarefasCustom.findAllLancamentosTarefa(idTarefa);
	}

	@RequestMapping(value = "/lancamento-horas", method = RequestMethod.POST)
	public List<ConsultaHorasTarefa> saveLancamentoHoras(@RequestBody BodyLancamentoHoras body) {
		tarefasService.saveLancamentoHoras(body.idUsuario, body.idTarefa, body.dataLancamento, body.descricao,
				body.tempoGasto);
		return tarefasCustom.findAllLancamentosTarefa(body.idTarefa);
	}

	@RequestMapping(value = "/lancamento-horas/{idLancamento}", method = RequestMethod.DELETE)
	public List<ConsultaHorasTarefa> deleteById(@PathVariable("idLancamento") String idLancamento) {
		return tarefasService.deleteLancamento(idLancamento);
	}

	@RequestMapping(value = "/fechar-tarefa/{idTarefa}/{listarAbertos}/{usuarioAtual}", method = RequestMethod.GET)
	public List<ConsultaDadosLancHoras> fecharTarefa(@PathVariable("idTarefa") int idTarefa,
			@PathVariable("listarAbertos") boolean listarAbertos, @PathVariable("usuarioAtual") int usuarioAtual) {
		tarefasService.fecharTarefa(idTarefa);
		return tarefasCustom.findAllTarefas(usuarioAtual, listarAbertos);
	}

	@RequestMapping(value = "/consultar-atribuidas/{idUsuario}/{listarAbertos}", method = RequestMethod.GET)
	public List<ConsultaDadosLancHoras> consultarAtribuidas(@PathVariable("idUsuario") int idUsuario,
			@PathVariable("listarAbertos") boolean listarAbertos) {
		return tarefasCustom.findAllTarefas(idUsuario, listarAbertos);
	}

	@RequestMapping(value = "/consultar-horas-lancadas", method = RequestMethod.POST)
	public List<ConsultaHorasLancadas> consultarAtribuidas(@RequestBody BodyTarefas body) {
		return tarefasCustom.findLancamentoHoras(body.usuarios, body.dataInicio, body.dataFim);
	}

	@RequestMapping(value = "/consultar-extrato-horas-lancadas-origem", method = RequestMethod.POST)
	public List<ConsultaHorasLancadas> consultarExtratoHorasPorOrigem(@RequestBody BodyTarefas body) {
		return tarefasService.obterHorasLancadas(body.usuarios, body.dataInicio, body.dataFim);
	}

	@RequestMapping(value = "/find-metas-by-usuario/{idUsuario}", method = RequestMethod.GET)
	public List<MetasPorUsuario> findMetasByUsuario(@PathVariable("idUsuario") int idUsuario) {
		return tarefasService.findMetasLancadoEfetivo(idUsuario);
	}

	@RequestMapping(value = "/salvar-metas-horas-usuario", method = RequestMethod.POST)
	public List<MetasPorUsuario> salvarMetasHorasUsuario(@RequestBody BodyTarefas body) {
		tarefasService.salvarMetasPorUsuarios(body.codUsuario, body.metas);
		return tarefasService.findMetasLancadoEfetivo(body.codUsuario);
	}
	
	@RequestMapping(value = "/find-tarefas-principais", method = RequestMethod.POST)
	public List<ReturnTarefasPrincipais> findTarefasPrincipais(@RequestBody BodyTarefas body) {
		return tarefasCustom.obterTarefasPrincipais(body.usuarios);
	}
	
	@RequestMapping(value = "/obter-valores-metas-horas-usuario", method = RequestMethod.POST)
	public IndicesMetasUsuario obterMetasLancadoValoresHorasUsuario(@RequestBody BodyTarefas body) {
		return tarefasService.obterMetaLancadoValoresHorasUsuario(body.usuarios, body.dataInicio, body.dataFim, body.tipoMeta);
	}
	
	@RequestMapping(value = "/obter-lista-grid-meta-disponivel", method = RequestMethod.POST)
	public List<IndicesMetasUsuario> obterListaGridMetaDisponivel(@RequestBody BodyTarefas body) {
		return tarefasService.obterListaGridDisponivel(body.usuarios, body.dataInicio, body.dataFim, body.tipoMeta);
	}

	@GetMapping("/obter-lista-dia-lancamento-horas/{idUsuario}/{dataInicio}/{dataFim}")
	public List<ConsultaControleLancamentoHoras> consultaHorasLancadasDia(@PathVariable("idUsuario") int idUsuario, @PathVariable("dataInicio") String dataInicio, @PathVariable("dataFim") String dataFim) {
		return tarefasCustom.consultaHorasLancadasDia(idUsuario, dataInicio, dataFim);
	}

	@GetMapping("/consultar-lancamentos-dia/{idUsuario}/{dataLancamento}")
	public List<ConsultaHorasLancadas> consultaLancamentosDia(@PathVariable("idUsuario") int idUsuario, @PathVariable("dataLancamento") String dataLancamento) {
		return tarefasCustom.consultaLancamentosDia(idUsuario, dataLancamento);
	}

	@GetMapping("/consultar-tarefas-atribuidas-chave/{idUsuario}/{listarAbertos}")
	public List<ConteudoChaveNumerica> consultarTarefasAtribuidas(@PathVariable("idUsuario") int idUsuario, @PathVariable("listarAbertos") boolean listarAbertos) {
		return tarefasCustom.findAllTarefasAtribuidas(idUsuario, listarAbertos);
	}
}
