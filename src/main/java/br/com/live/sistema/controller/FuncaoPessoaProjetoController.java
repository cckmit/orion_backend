package br.com.live.sistema.controller;

import br.com.live.sistema.entity.FuncaoPessoaProjetoEntity;
import br.com.live.sistema.repository.FuncaoPessoaProjetoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/funcao-pessoa-projeto")
public class FuncaoPessoaProjetoController {

    FuncaoPessoaProjetoRepository funcaoPessoaRepository;

    public FuncaoPessoaProjetoController(FuncaoPessoaProjetoRepository funcaoPessoaRepository) {
        this.funcaoPessoaRepository = funcaoPessoaRepository;
    }

    @GetMapping("/find-all")
    public List<FuncaoPessoaProjetoEntity> findAllFuncaoPessoa(){
        return funcaoPessoaRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<FuncaoPessoaProjetoEntity> findByIdFuncaoPessoa(@PathVariable("id") Long id){
        return funcaoPessoaRepository.findById(id);
    }

    @GetMapping("/delete-by-id/{id}")
    public List<FuncaoPessoaProjetoEntity> deleteByIdFuncaoPessoa(@PathVariable("id") Long id){

        try {
            funcaoPessoaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Não é possível excluir a entidade devido a restrições de chave estrangeira.");
        }

        return funcaoPessoaRepository.findAll();
    }

    @PostMapping("/save")
    public List<FuncaoPessoaProjetoEntity> saveFuncaoPessoa(@RequestBody FuncaoPessoaProjetoEntity funcaoPessoa){
        if (funcaoPessoa.getId() == 0) funcaoPessoa.setId(funcaoPessoaRepository.findNextId());
        funcaoPessoaRepository.save(funcaoPessoa);
        return funcaoPessoaRepository.findAll();
    }
}
