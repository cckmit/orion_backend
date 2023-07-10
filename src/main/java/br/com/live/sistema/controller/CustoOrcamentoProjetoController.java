package br.com.live.sistema.controller;

import br.com.live.sistema.entity.CustoOrcamentoProjetoEntity;
import br.com.live.sistema.repository.CustoOrcamentoProjetoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/custo-orcamento")
public class CustoOrcamentoProjetoController {

    CustoOrcamentoProjetoRepository custoOrcamentoProjetoRepository;

    public CustoOrcamentoProjetoController(CustoOrcamentoProjetoRepository custoOrcamentoProjetoRepository) {
        this.custoOrcamentoProjetoRepository = custoOrcamentoProjetoRepository;
    }

    @GetMapping("/find-all")
    public List<CustoOrcamentoProjetoEntity> findAllCustoOrcamentoProjeto(){
        return custoOrcamentoProjetoRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<CustoOrcamentoProjetoEntity> findByIdCustoOrcamentoProjeto(@PathVariable("id") Long id){
        return custoOrcamentoProjetoRepository.findById(id);
    }

    @GetMapping("/delete-by-id/{id}/{idProjeto}")
    public List<CustoOrcamentoProjetoEntity> deleteByIdCustoOrcamentoProjeto(@PathVariable("id") Long id, @PathVariable("idProjeto") Long idProjeto){

        try {
            custoOrcamentoProjetoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Não é possível excluir a entidade devido a restrições de chave estrangeira.");
        }

        return custoOrcamentoProjetoRepository.findAllByIdProjeto(idProjeto);
    }

    @PostMapping("/save")
    public List<CustoOrcamentoProjetoEntity> saveCustoOrcamentoProjeto(@RequestBody CustoOrcamentoProjetoEntity custoOrcamentoProjeto){
        if (custoOrcamentoProjeto.getId() == 0) custoOrcamentoProjeto.setId(custoOrcamentoProjetoRepository.findNextId());
        custoOrcamentoProjetoRepository.save(custoOrcamentoProjeto);
        return custoOrcamentoProjetoRepository.findAllByIdProjeto(custoOrcamentoProjeto.getIdProjeto());
    }

    @GetMapping("/find-all-by-projeto/{idProjeto}")
    public List<CustoOrcamentoProjetoEntity> findByIProjeto(@PathVariable("idProjeto") Long idProjeto){
        return custoOrcamentoProjetoRepository.findAllByIdProjeto(idProjeto);
    }
}
