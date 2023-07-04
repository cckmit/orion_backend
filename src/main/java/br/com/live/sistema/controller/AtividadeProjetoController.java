package br.com.live.sistema.controller;

import br.com.live.sistema.body.BodyAtividadeProjeto;
import br.com.live.sistema.entity.AtividadeProjetoEntity;
import br.com.live.sistema.repository.AtividadeProjetoRepository;
import br.com.live.sistema.service.AtividadeProjetoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/atividade-projeto")
public class AtividadeProjetoController {

    AtividadeProjetoRepository atividadeProjetoRepository;
    AtividadeProjetoService atividadeProjetoService;

    public AtividadeProjetoController(AtividadeProjetoRepository atividadeProjetoRepository, AtividadeProjetoService atividadeProjetoService) {
        this.atividadeProjetoRepository = atividadeProjetoRepository;
        this.atividadeProjetoService = atividadeProjetoService;
    }

    @GetMapping("/find-all")
    public List<AtividadeProjetoEntity> findAllAtividadeProjeto(){
        return atividadeProjetoRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<AtividadeProjetoEntity> findByIdAtividadeProjeto(@PathVariable("id") Long id){
        return atividadeProjetoRepository.findById(id);
    }

    @GetMapping("/find-all-by-projeto/{idProjeto}")
    public List<AtividadeProjetoEntity> findByIProjeto(@PathVariable("idProjeto") Long idProjeto){
        return atividadeProjetoRepository.findAllByIdProjeto(idProjeto);
    }

    @GetMapping("/delete-by-id/{id}/{idProjeto}")
    public List<AtividadeProjetoEntity> deleteByIdAtividadeProjeto(@PathVariable("id") Long id, @PathVariable("idProjeto") Long idProjeto){
        atividadeProjetoRepository.deleteById(id);
        return atividadeProjetoRepository.findAllByIdProjeto(idProjeto);
    }

    @PostMapping("/save")
    public List<AtividadeProjetoEntity> saveAtividadeProjeto(@RequestBody BodyAtividadeProjeto atividadeProjeto){
        return atividadeProjetoService.saveAtividadeProjeto(atividadeProjeto);
    }
}