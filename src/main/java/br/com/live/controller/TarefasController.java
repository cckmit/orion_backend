package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.custom.TarefasCustom;
import br.com.live.entity.LancamentoHoras;
import br.com.live.entity.Tarefas;
import br.com.live.model.ConsultaDadosLancHoras;
import br.com.live.repository.LancamentoHorasRepository;
import br.com.live.repository.TarefasRepository;
import br.com.live.service.TarefasService;
import br.com.live.util.BodyLancamentoHoras;
import br.com.live.util.BodyTarefas;

@RestController
@CrossOrigin
@RequestMapping("/tarefas")
public class TarefasController {
	
    private TarefasRepository tarefasRepository;
    private TarefasService tarefasService;
    private TarefasCustom tarefasCustom;
    private LancamentoHorasRepository lancamentoHorasRepository;

    @Autowired
    public TarefasController(TarefasRepository tarefasRepository, TarefasService tarefasService, TarefasCustom tarefasCustom, LancamentoHorasRepository lancamentoHorasRepository) {
          this.tarefasRepository = tarefasRepository;
          this.tarefasService = tarefasService;
          this.tarefasCustom = tarefasCustom;
          this.lancamentoHorasRepository = lancamentoHorasRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Tarefas> findAll() {
          return tarefasRepository.findAll(); 
    }
    
	@RequestMapping(value = "/{idTarefa}", method = RequestMethod.GET)
	public Tarefas findByIdUsuario(@PathVariable("idTarefa") int idTarefa) {
		return tarefasRepository.findByIdTarefa(idTarefa);
	}
	
	@RequestMapping(value = "/find-all/{idUsuario}", method = RequestMethod.GET)
	public List<ConsultaDadosLancHoras> findAllByIdUsuario(@PathVariable("idUsuario") int idUsuario) {
		return tarefasCustom.findAllTarefas(idUsuario);
	}
	
	@RequestMapping(value = "/{idTarefa}", method = RequestMethod.DELETE)
	public List<Tarefas> deleteById(@PathVariable("idTarefa") int idTarefa) {
		tarefasRepository.deleteById(idTarefa);
		return findAll();
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Tarefas saveTarefas(@RequestBody BodyTarefas body) {
		return tarefasService.saveTarefas(body.id, body.anexo, body.assunto, body.origem, body.sistema, body.situacao, body.tempoEstimado, body.tipo, body.titulo, body.usuarioAtribuido, body.usuarioSolicitante, body.dataPrevista);
	}
	
	@RequestMapping(value = "/lancamento-horas/{idTarefa}/{idUsuario}", method = RequestMethod.GET)
	public List<LancamentoHoras> findAllByIdUsuario(@PathVariable("idTarefa") int idTarefa, @PathVariable("idUsuario") int idUsuario) {
		return lancamentoHorasRepository.findByIdTarefaAndIdUsuario(idTarefa, idUsuario);
	}
	
	@RequestMapping(value = "/lancamento-horas", method = RequestMethod.POST)
	public List<LancamentoHoras> saveLancamentoHoras(@RequestBody BodyLancamentoHoras body) {
		return tarefasService.saveLancamentoHoras(body.idUsuario, body.idTarefa, body.dataLancamento, body.descricao, body.tempoGasto);
	}
	
	@RequestMapping(value = "/lancamento-horas/{idLancamento}", method = RequestMethod.DELETE)
	public List<LancamentoHoras> deleteById(@PathVariable("idLancamento") String idLancamento) {
		return tarefasService.deleteLancamento(idLancamento); 
	}
	
	@RequestMapping(value = "/fechar-tarefa/{idTarefa}/{listarAbertos}/{usuarioAtual}", method = RequestMethod.GET)
	public List<ConsultaDadosLancHoras> fecharTarefa(@PathVariable("idTarefa") int idTarefa, @PathVariable("listarAbertos") boolean listarAbertos, @PathVariable("usuarioAtual") int usuarioAtual) {
		tarefasService.fecharTarefa(idTarefa);
		return tarefasCustom.findAllTarefas(usuarioAtual, listarAbertos);
	}
	
	@RequestMapping(value = "/consultar-atribuidas/{idUsuario}/{listarAbertos}", method = RequestMethod.GET)
	public List<ConsultaDadosLancHoras> consultarAtribuidas(@PathVariable("idUsuario") int idUsuario, @PathVariable("listarAbertos") boolean listarAbertos) {
		return tarefasCustom.findAllTarefas(idUsuario, listarAbertos); 
	}
}
