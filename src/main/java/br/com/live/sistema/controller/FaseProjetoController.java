package br.com.live.sistema.controller;

import br.com.live.sistema.entity.FaseProjetoEntity;
import br.com.live.sistema.repository.FaseProjetoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/fase-projeto")
public class FaseProjetoController {

    FaseProjetoRepository faseProjetoRepository;

    public FaseProjetoController(FaseProjetoRepository faseProjetoRepository) {
        this.faseProjetoRepository = faseProjetoRepository;
    }

    @GetMapping("/find-all")
    public List<FaseProjetoEntity> findAllFaseProjeto(){
        return faseProjetoRepository.findAllOrderByDescricao();
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<FaseProjetoEntity> findByIdFaseProjeto(@PathVariable("id") Long id){
        return faseProjetoRepository.findById(id);
    }

    @GetMapping("/delete-by-id/{id}")
    public List<FaseProjetoEntity> deleteByIdFaseProjeto(@PathVariable("id") Long id){

        try {
            faseProjetoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Não é possível excluir a entidade devido a restrições de chave estrangeira.");
        }

        return faseProjetoRepository.findAllOrderByDescricao();
    }

    @PostMapping("/save")
    public List<FaseProjetoEntity> saveFaseProjeto(@RequestBody FaseProjetoEntity faseProjeto){
        if (faseProjeto.getId() == 0) faseProjeto.setId(faseProjetoRepository.findNextId());
        faseProjetoRepository.save(faseProjeto);
        return faseProjetoRepository.findAllOrderByDescricao();
    }
}
