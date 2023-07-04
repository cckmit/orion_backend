package br.com.live.sistema.controller;

import br.com.live.sistema.entity.TarefaConcluidaAtividadeProjetoEntity;
import br.com.live.sistema.repository.TarefaConcluidaAtividadeProjetoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/tarefa-concluida-atividade-projeto")
public class TarefaConcluidaAtividadeProjetoController {

    TarefaConcluidaAtividadeProjetoRepository tarefaConcluidaAtividadeProjetoRepository;

    public TarefaConcluidaAtividadeProjetoController(TarefaConcluidaAtividadeProjetoRepository tarefaConcluidaAtividadeProjetoRepository) {
        this.tarefaConcluidaAtividadeProjetoRepository = tarefaConcluidaAtividadeProjetoRepository;
    }

    @GetMapping("/find-all")
    public List<TarefaConcluidaAtividadeProjetoEntity> findAllTarefaConcluidaAtividadeProjeto(){
        return tarefaConcluidaAtividadeProjetoRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<TarefaConcluidaAtividadeProjetoEntity> findByIdTarefaConcluidaAtividadeProjeto(@PathVariable("id") Long id){
        return tarefaConcluidaAtividadeProjetoRepository.findById(id);
    }

    @GetMapping("/delete-by-id/{id}")
    public List<TarefaConcluidaAtividadeProjetoEntity> deleteByIdTarefaConcluidaAtividadeProjeto(@PathVariable("id") Long id){
        tarefaConcluidaAtividadeProjetoRepository.deleteById(id);
        return tarefaConcluidaAtividadeProjetoRepository.findAll();
    }

    @PostMapping("/save")
    public List<TarefaConcluidaAtividadeProjetoEntity> saveTarefaConcluidaAtividadeProjeto(@RequestBody TarefaConcluidaAtividadeProjetoEntity tarefaConcluidaAtividadeProjeto){
        if (tarefaConcluidaAtividadeProjeto.getId() == 0) tarefaConcluidaAtividadeProjeto.setId(tarefaConcluidaAtividadeProjetoRepository.findNextId());
        tarefaConcluidaAtividadeProjetoRepository.save(tarefaConcluidaAtividadeProjeto);
        return tarefaConcluidaAtividadeProjetoRepository.findAll();
    }
}
