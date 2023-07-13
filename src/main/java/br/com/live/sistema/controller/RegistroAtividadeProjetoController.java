package br.com.live.sistema.controller;

import br.com.live.sistema.body.BodyRegistroAtividadeProjeto;
import br.com.live.sistema.entity.RegistroAtividadeProjetoEntity;
import br.com.live.sistema.repository.RegistroAtividadeProjetoRepository;
import br.com.live.sistema.service.RegistroAtividadeProjetoService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/registro-atividade-projeto")
public class RegistroAtividadeProjetoController {

    RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository;
    RegistroAtividadeProjetoService registroAtividadeProjetoService;

    public RegistroAtividadeProjetoController(RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository, RegistroAtividadeProjetoService registroAtividadeProjetoService) {
        this.registroAtividadeProjetoRepository = registroAtividadeProjetoRepository;
        this.registroAtividadeProjetoService = registroAtividadeProjetoService;
    }

    @GetMapping("/find-all")
    public List<BodyRegistroAtividadeProjeto> findAllRegistroAtividadeProjeto(){
        return registroAtividadeProjetoService.findAll(0l);
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<RegistroAtividadeProjetoEntity> findByIdRegistroAtividadeProjeto(@PathVariable("id") Long id){
        return registroAtividadeProjetoRepository.findById(id);
    }

    @GetMapping("/find-all-by-projeto/{idProjeto}")
    public List<BodyRegistroAtividadeProjeto> findByIProjeto(@PathVariable("idProjeto") Long idProjeto){
        return registroAtividadeProjetoService.findAll(idProjeto);
    }

    @GetMapping("/delete-by-id/{id}/{idProjeto}")
    public List<BodyRegistroAtividadeProjeto> deleteByIdRegistroAtividadeProjeto(@PathVariable("id") Long id, @PathVariable("idProjeto") Long idProjeto){
        registroAtividadeProjetoRepository.deleteById(id);
        return registroAtividadeProjetoService.findAll(idProjeto);
    }

    @PostMapping("/save")
    public List<BodyRegistroAtividadeProjeto> saveRegistroAtividadeProjeto(@RequestBody BodyRegistroAtividadeProjeto registroAtividadeProjeto) throws ParseException {
        return registroAtividadeProjetoService.saveRegistroAtividadeProjeto(registroAtividadeProjeto);
    }

    @GetMapping("/valida-registro-apontado/{idProjeto}")
    public int validaRegistroAtividadeProjetoApontado(@PathVariable("idProjeto") Long idProjeto){
        return registroAtividadeProjetoService.validaRegistroAtividadeProjetoApontado(idProjeto);
    }
}