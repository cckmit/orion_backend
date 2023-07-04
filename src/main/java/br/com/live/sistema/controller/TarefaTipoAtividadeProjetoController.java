package br.com.live.sistema.controller;
import br.com.live.sistema.body.BodyTarefaTipoAtividadeProjeto;
import br.com.live.sistema.entity.TarefaTipoAtividadeProjetoEntity;
import br.com.live.sistema.repository.TarefaTipoAtividadeProjetoRepository;
import br.com.live.sistema.service.TarefaTipoAtividadeProjetoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/tarefa-tipo-atividade-projeto")
public class TarefaTipoAtividadeProjetoController {

    TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository;
    TarefaTipoAtividadeProjetoService tarefaTipoAtividadeProjetoService;

    public TarefaTipoAtividadeProjetoController(TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository, TarefaTipoAtividadeProjetoService tarefaTipoAtividadeProjetoService) {
        this.tarefaTipoAtividadeProjetoRepository = tarefaTipoAtividadeProjetoRepository;
        this.tarefaTipoAtividadeProjetoService = tarefaTipoAtividadeProjetoService;
    }

    @GetMapping("/find-all")
    public List<TarefaTipoAtividadeProjetoEntity> findAllTarefaTipoAtividadeProjeto(){
        return tarefaTipoAtividadeProjetoRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<TarefaTipoAtividadeProjetoEntity> findByIdTarefaTipoAtividadeProjeto(@PathVariable("id") Long id){
        return tarefaTipoAtividadeProjetoRepository.findById(id);
    }

    @GetMapping("/delete-by-id/{id}/{idTipoAtividade}")
    public List<TarefaTipoAtividadeProjetoEntity> deleteByIdTarefaTipoAtividadeProjeto(@PathVariable("id") Long id, @PathVariable("idTipoAtividade") Long idTipoAtividade){
        tarefaTipoAtividadeProjetoRepository.deleteById(id);
        return tarefaTipoAtividadeProjetoRepository.findByTipoAtividadeId(idTipoAtividade);
    }

    @PostMapping("/save")
    public List<TarefaTipoAtividadeProjetoEntity> saveTarefaTipoAtividadeProjeto(@RequestBody TarefaTipoAtividadeProjetoEntity tarefaTipoAtividadeProjeto){
        return tarefaTipoAtividadeProjetoService.saveTarefaTipoAtividadeProjeto(tarefaTipoAtividadeProjeto);
    }

    @PostMapping("/save-ordenacao")
    public List<TarefaTipoAtividadeProjetoEntity> saveOrdenacaoTarefaTipoAtividadeProjeto(@RequestBody BodyTarefaTipoAtividadeProjeto tarefaTipoAtividadeProjetoBody){
        return tarefaTipoAtividadeProjetoService.saveOrdenacaoTarefaTipoAtividadeProjeto(tarefaTipoAtividadeProjetoBody);
    }

    @GetMapping("/find-all-by-tipo-atividade/{idTipoAtividade}")
    public List<TarefaTipoAtividadeProjetoEntity> findAllTarefaByTipoAtividade(@PathVariable("idTipoAtividade") Long idTipoAtividade){
        return tarefaTipoAtividadeProjetoRepository.findByTipoAtividadeId(idTipoAtividade);
    }
}
