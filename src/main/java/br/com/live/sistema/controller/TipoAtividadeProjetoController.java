package br.com.live.sistema.controller;

import br.com.live.sistema.entity.TipoAtividadeProjetoEntity;
import br.com.live.sistema.model.TipoAtividadeProjetoConsulta;
import br.com.live.sistema.repository.TipoAtividadeProjetoRepository;
import br.com.live.sistema.service.TipoAtividadeProjetoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tipo-atividade-projeto")
public class TipoAtividadeProjetoController {

    TipoAtividadeProjetoRepository tipoAtividadeProjetoRepository;
    TipoAtividadeProjetoService tipoAtividadeProjetoService;

    public TipoAtividadeProjetoController(TipoAtividadeProjetoRepository tipoAtividadeProjetoRepository, TipoAtividadeProjetoService tipoAtividadeProjetoService) {
        this.tipoAtividadeProjetoRepository = tipoAtividadeProjetoRepository;
        this.tipoAtividadeProjetoService = tipoAtividadeProjetoService;
    }

    @GetMapping("/find-all")
    public List<TipoAtividadeProjetoConsulta> findAllTipoAtividadeProjeto(){
        return tipoAtividadeProjetoService.findAllTipoAtividadeProjeto();
    }

    @GetMapping("/find-by-id/{id}")
    public TipoAtividadeProjetoConsulta findByIdTipoAtividadeProjeto(@PathVariable("id") Long id){
        return tipoAtividadeProjetoService.findByIdTipoAtividadeProjeto(id);
    }

    @GetMapping("/find-tempo-estimado-by-id/{id}")
    public double findTempoEstimadoById(@PathVariable("id") Long id){
        return tipoAtividadeProjetoService.findTempoEstimadoById(id);
    }

    @GetMapping("/delete-by-id/{id}")
    public List<TipoAtividadeProjetoConsulta> deleteByIdTipoAtividadeProjeto(@PathVariable("id") Long id){
        try {
            tipoAtividadeProjetoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Não é possível excluir a entidade devido a restrições de chave estrangeira.");
        }
        return tipoAtividadeProjetoService.findAllTipoAtividadeProjeto();
    }

    @PostMapping("/save")
    public List<TipoAtividadeProjetoConsulta> saveTipoAtividadeProjeto(@RequestBody TipoAtividadeProjetoEntity tipoAtividadeProjeto){
        if (tipoAtividadeProjeto.getId() == 0) tipoAtividadeProjeto.setId(tipoAtividadeProjetoRepository.findNextId());
        tipoAtividadeProjetoRepository.save(tipoAtividadeProjeto);
        return tipoAtividadeProjetoService.findAllTipoAtividadeProjeto();
    }
}
