package br.com.live.sistema.controller;

import br.com.live.sistema.entity.TipoAtividadeProjetoEntity;
import br.com.live.sistema.repository.TipoAtividadeProjetoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/tipo-atividade-projeto")
public class TipoAtividadeProjetoController {

    TipoAtividadeProjetoRepository tipoAtividadeProjetoRepository;

    public TipoAtividadeProjetoController(TipoAtividadeProjetoRepository tipoAtividadeProjetoRepository) {
        this.tipoAtividadeProjetoRepository = tipoAtividadeProjetoRepository;
    }

    @GetMapping("/find-all")
    public List<TipoAtividadeProjetoEntity> findAllTipoAtividadeProjeto(){
        return tipoAtividadeProjetoRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<TipoAtividadeProjetoEntity> findByIdTipoAtividadeProjeto(@PathVariable("id") Long id){
        return tipoAtividadeProjetoRepository.findById(id);
    }

    @GetMapping("/delete-by-id/{id}")
    public List<TipoAtividadeProjetoEntity> deleteByIdTipoAtividadeProjeto(@PathVariable("id") Long id){
        tipoAtividadeProjetoRepository.deleteById(id);
        return tipoAtividadeProjetoRepository.findAll();
    }

    @PostMapping("/save")
    public List<TipoAtividadeProjetoEntity> saveTipoAtividadeProjeto(@RequestBody TipoAtividadeProjetoEntity tipoAtividadeProjeto){
        if (tipoAtividadeProjeto.getId() == 0) tipoAtividadeProjeto.setId(tipoAtividadeProjetoRepository.findNextId());
        tipoAtividadeProjetoRepository.save(tipoAtividadeProjeto);
        return tipoAtividadeProjetoRepository.findAll();
    }
}
