package br.com.live.sistema.controller;

import br.com.live.sistema.entity.TarefaAtividadeProjetoEntity;
import br.com.live.sistema.repository.TarefaAtividadeProjetoRepository;
import br.com.live.sistema.service.TarefaAtividadeProjetoService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/tarefa-atividade-projeto")
public class TarefaAtividadeProjetoController {

    TarefaAtividadeProjetoRepository tarefaAtividadeProjetoRepository;
    TarefaAtividadeProjetoService tarefaAtividadeProjetoService;

    public TarefaAtividadeProjetoController(TarefaAtividadeProjetoRepository tarefaAtividadeProjetoRepository, TarefaAtividadeProjetoService tarefaAtividadeProjetoService) {
        this.tarefaAtividadeProjetoRepository = tarefaAtividadeProjetoRepository;
        this.tarefaAtividadeProjetoService = tarefaAtividadeProjetoService;
    }

    @GetMapping("/find-all")
    public List<TarefaAtividadeProjetoEntity> findAllTarefaAtividadeProjeto(){
        return tarefaAtividadeProjetoService.findAll(0L);
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<TarefaAtividadeProjetoEntity> findByIdTarefaAtividadeProjeto(@PathVariable("id") Long id){
        return tarefaAtividadeProjetoRepository.findById(id);
    }

    @GetMapping("/find-all-by-projeto-atividade/{idProjeto}")
    public List<TarefaAtividadeProjetoEntity> findByIdProjetoAtividade(@PathVariable("idProjeto") Long idProjeto, @PathVariable("idAtividade") Long idAtividade){
        return tarefaAtividadeProjetoService.findAll(idProjeto);
    }

    @GetMapping("/find-all-by-projeto/{idProjeto}")
    public List<TarefaAtividadeProjetoEntity> findByIdProjeto(@PathVariable("idProjeto") Long idProjeto){
        return tarefaAtividadeProjetoService.findAll(idProjeto);
    }

    @GetMapping("/delete-by-id/{id}/{idProjeto}/{idAtividade}")
    public List<TarefaAtividadeProjetoEntity> deleteByIdTarefaAtividadeProjeto(@PathVariable("id") Long id, @PathVariable("idProjeto") Long idProjeto,@PathVariable("idAtividade") Long idAtividade){
        return tarefaAtividadeProjetoService.deleteByIdTarefaAtividadeProjeto(id, idProjeto, idAtividade);
    }

    @PostMapping("/save")
    public List<TarefaAtividadeProjetoEntity> saveTarefaAtividadeProjeto(@RequestBody TarefaAtividadeProjetoEntity tarefaAtividadeProjeto) throws ParseException {
        return tarefaAtividadeProjetoService.saveTarefaAtividadeProjeto(tarefaAtividadeProjeto);
    }

}
