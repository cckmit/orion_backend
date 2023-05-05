package br.com.live.controller;

import br.com.live.custom.OrcamentoLojaDreCustom;
import br.com.live.entity.OrcamentoLojaDreEntity;
import br.com.live.model.ConsultaOrcamentoLojaDre;
import br.com.live.model.OrcamentoLojaDre;
import br.com.live.repository.OrcamentoLojaDreRepository;
import br.com.live.service.OrcamentoLojaDreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/orcamento-loja-dre")
public class OrcamentoLojaDreController {

    OrcamentoLojaDreRepository orcamentoLojaDreRepository;
    OrcamentoLojaDreCustom orcamentoLojaDreCustom;
    OrcamentoLojaDreService orcamentoLojaDreService;

    @Autowired
    public OrcamentoLojaDreController(OrcamentoLojaDreRepository orcamentoLojaDreRepository, OrcamentoLojaDreCustom orcamentoLojaDreCustom, OrcamentoLojaDreService orcamentoLojaDreService) {
        this.orcamentoLojaDreRepository = orcamentoLojaDreRepository;
        this.orcamentoLojaDreCustom = orcamentoLojaDreCustom;
        this.orcamentoLojaDreService = orcamentoLojaDreService;
    }

    @GetMapping("/find-field-orcamento-loja-by-id/{id}")
    public Optional<OrcamentoLojaDreEntity> findOrcamentoLojaById(@PathVariable("id") long id){
        return orcamentoLojaDreRepository.findById(id);
    }

    @GetMapping("/find-all-orcamento-loja")
    public List<ConsultaOrcamentoLojaDre> findAllOrcamentoLoja(){
        return orcamentoLojaDreCustom.findAllOrcamentoLojaDre();
    }

    @GetMapping("/find-all-fields-orcamento-loja-by-cnpj-ano/{cnpjLoja}/{anoOrcamento}")
    public List<OrcamentoLojaDre> findAllFieldsOrcamentoLojaByCnpjAno(@PathVariable("cnpjLoja") String cnpjLoja, @PathVariable("anoOrcamento") int anoOrcamento){
        return orcamentoLojaDreCustom.findAllFieldsOrcamentoLojaByCnpjAno(cnpjLoja, anoOrcamento);
    }

    @GetMapping("/find-orcamento-loja-by-cnpj-ano/{cnpjLoja}/{anoOrcamento}")
    public ConsultaOrcamentoLojaDre findOrcamentoLojaDre(@PathVariable("cnpjLoja") String cnpjLoja, @PathVariable("anoOrcamento") int anoOrcamento){
        return orcamentoLojaDreCustom.findOrcamentoLojaDre(cnpjLoja, anoOrcamento);
    }

    @PostMapping("/save-field-orcamento-loja")
    public OrcamentoLojaDreEntity saveFieldOrcamentoLoja(@RequestBody OrcamentoLojaDreEntity orcamentoLojaDreEntity){
        return orcamentoLojaDreRepository.save(orcamentoLojaDreEntity);
    }

    @PostMapping("/save-orcamento-loja")
    public void saveOrcamentoLoja(@RequestBody List<OrcamentoLojaDreEntity> orcamentoLojaDreList){
        orcamentoLojaDreService.saveOrcamentoLojaDre(orcamentoLojaDreList);
    }

    @DeleteMapping("/delete-field-orcamento-loja-by-id/{id}")
    public void deleteFieldOrcamentoLoja(@PathVariable("id") long id){
        orcamentoLojaDreRepository.deleteById(id);
    }

    @DeleteMapping("/delete-orcamento-loja-by-cnpj-ano/{cnpjLoja}/{anoOrcamento}")
    public void deleteOrcamentoLojaByCnpjAno(@PathVariable("cnpjLoja") String cnpjLoja, @PathVariable("anoOrcamento") int anoOrcamento){
        orcamentoLojaDreService.deleteOrcamentoLojaByCnpjAno(cnpjLoja, anoOrcamento);
    }
}
