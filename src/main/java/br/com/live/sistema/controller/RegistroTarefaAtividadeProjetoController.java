package br.com.live.sistema.controller;

import br.com.live.sistema.body.BodyRegistroTarefaAtividadeProjeto;
import br.com.live.sistema.entity.RegistroTarefaAtividadeProjetoEntity;
import br.com.live.sistema.repository.RegistroTarefaAtividadeProjetoRepository;
import br.com.live.sistema.service.RegistroTarefaAtividadeProjetoService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/registro-tarefa-atividade-projeto")
public class RegistroTarefaAtividadeProjetoController {

    RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository;
    RegistroTarefaAtividadeProjetoService registroTarefaAtividadeProjetoService;

    public RegistroTarefaAtividadeProjetoController(RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository, RegistroTarefaAtividadeProjetoService registroTarefaAtividadeProjetoService) {
        this.registroTarefaAtividadeProjetoRepository = registroTarefaAtividadeProjetoRepository;
        this.registroTarefaAtividadeProjetoService = registroTarefaAtividadeProjetoService;
    }

    @GetMapping("/find-all")
    public List<BodyRegistroTarefaAtividadeProjeto> findAllRegistroTarefaAtividadeProjeto(){
        return registroTarefaAtividadeProjetoService.findAll(0L);
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<RegistroTarefaAtividadeProjetoEntity> findByIdRegistroTarefaAtividadeProjeto(@PathVariable("id") Long id){
        return registroTarefaAtividadeProjetoRepository.findById(id);
    }

    @GetMapping("/find-all-by-projeto-atividade/{idProjeto}")
    public List<BodyRegistroTarefaAtividadeProjeto> findByIProjetoAtividade(@PathVariable("idProjeto") Long idProjeto, @PathVariable("idRegistroAtividade") Long idRegistroAtividade){
        return registroTarefaAtividadeProjetoService.findAll(idProjeto);
    }

    @GetMapping("/find-all-by-projeto/{idProjeto}")
    public List<BodyRegistroTarefaAtividadeProjeto> findByIProjeto(@PathVariable("idProjeto") Long idProjeto){
        return registroTarefaAtividadeProjetoService.findAll(idProjeto);
    }

    @GetMapping("/delete-by-id/{id}/{idProjeto}")
    public List<BodyRegistroTarefaAtividadeProjeto> deleteByIdRegistroTarefaAtividadeProjeto(@PathVariable("id") Long id, @PathVariable("idProjeto") Long idProjeto){
        registroTarefaAtividadeProjetoRepository.deleteById(id);
        return registroTarefaAtividadeProjetoService.findAll(idProjeto);
    }

    @PostMapping("/save")
    public List<BodyRegistroTarefaAtividadeProjeto> saveRegistroTarefaAtividadeProjeto(@RequestBody BodyRegistroTarefaAtividadeProjeto registroTarefaAtividadeProjeto) throws ParseException {
        return registroTarefaAtividadeProjetoService.saveRegistroTarefaAtividadeProjeto(registroTarefaAtividadeProjeto);
    }
}
