package br.com.live.sistema.controller;

import br.com.live.sistema.entity.PessoaEnvolvidaProjetoEntity;
import br.com.live.sistema.repository.PessoaEnvolvidaProjetoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/pessoa-envolvida-projeto")
public class PessoaEnvolvidaProjetoController {

    PessoaEnvolvidaProjetoRepository pessoaEnvolvidaProjetoRepository;

    public PessoaEnvolvidaProjetoController(PessoaEnvolvidaProjetoRepository pessoaEnvolvidaProjetoRepository) {
        this.pessoaEnvolvidaProjetoRepository = pessoaEnvolvidaProjetoRepository;
    }

    @GetMapping("/find-all")
    public List<PessoaEnvolvidaProjetoEntity> findAllPessoaEnvolvidaProjeto(){
        return pessoaEnvolvidaProjetoRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<PessoaEnvolvidaProjetoEntity> findByIdPessoaEnvolvidaProjeto(@PathVariable("id") Long id){
        return pessoaEnvolvidaProjetoRepository.findById(id);
    }

    @GetMapping("/delete-by-id/{id}/{idProjeto}")
    public List<PessoaEnvolvidaProjetoEntity> deleteByIdPessoaEnvolvidaProjeto(@PathVariable("id") Long id, @PathVariable("idProjeto") Long idProjeto){

        try {
            pessoaEnvolvidaProjetoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Não é possível excluir a entidade devido a restrições de chave estrangeira.");
        }

        return pessoaEnvolvidaProjetoRepository.findAllByIdProjeto(idProjeto);
    }

    @PostMapping("/save")
    public List<PessoaEnvolvidaProjetoEntity> savePessoaEnvolvidaProjeto(@RequestBody PessoaEnvolvidaProjetoEntity pessoaEnvolvidaProjeto){
        if (pessoaEnvolvidaProjeto.getId() == 0) pessoaEnvolvidaProjeto.setId(pessoaEnvolvidaProjetoRepository.findNextId());
        pessoaEnvolvidaProjetoRepository.save(pessoaEnvolvidaProjeto);
        return pessoaEnvolvidaProjetoRepository.findAllByIdProjeto(pessoaEnvolvidaProjeto.getIdProjeto());
    }

    @GetMapping("/find-all-by-projeto/{idProjeto}")
    public List<PessoaEnvolvidaProjetoEntity> findByIProjeto(@PathVariable("idProjeto") Long idProjeto){
        return pessoaEnvolvidaProjetoRepository.findAllByIdProjeto(idProjeto);
    }
}
