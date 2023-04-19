package br.com.live.controller;

import br.com.live.custom.ContaContabilConsiderarDreCustom;
import br.com.live.entity.ContaContabilConsiderarDreEntity;
import br.com.live.model.ConsultaContaContabilConsiderarDre;
import br.com.live.repository.ContaContabilConsiderarDreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/conta-contabil-considerar-dre")
public class ContaContabilConsiderarDreController {

    ContaContabilConsiderarDreRepository contaContabilConsiderarDreRepository;
    ContaContabilConsiderarDreCustom contaContabilConsiderarDreCustom;

    @Autowired
    public ContaContabilConsiderarDreController(ContaContabilConsiderarDreRepository contaContabilConsiderarDreRepository, ContaContabilConsiderarDreCustom contaContabilConsiderarDreCustom) {
        this.contaContabilConsiderarDreRepository = contaContabilConsiderarDreRepository;
        this.contaContabilConsiderarDreCustom = contaContabilConsiderarDreCustom;
    }

    @GetMapping("/find-all-conta-contabil-considerar-dre")
    public List<ContaContabilConsiderarDreEntity> findAllContaContabilConsiderarDre() {
        return contaContabilConsiderarDreRepository.findAll();
    }

    @GetMapping("/find-conta-contabil-considerar-dre-by-id/{contaContabil}")
    public Optional<ContaContabilConsiderarDreEntity> findContaConbailConsiderarDreById(@PathVariable("contaContabil") int contaContabil){
        return contaContabilConsiderarDreRepository.findById(contaContabil);
    }

    @GetMapping("/find-all-conta-contabil")
    public List<ConsultaContaContabilConsiderarDre> findAllContaContabil(){
        return contaContabilConsiderarDreCustom.findAllContaContabil();
    }

    @PostMapping("/save-conta-contabil-considerar-dre")
    public void saveContaContabilConsiderarDre(@RequestBody ContaContabilConsiderarDreEntity contaContabilConsiderarDre){
        if (!contaContabilConsiderarDre.gastoVariavel && !contaContabilConsiderarDre.custoOcupacao && !contaContabilConsiderarDre.depreciacao && !contaContabilConsiderarDre.despesaGeral && !contaContabilConsiderarDre.despesaFolha){
            contaContabilConsiderarDreRepository.deleteById(contaContabilConsiderarDre.contaContabil);
        } else{
            contaContabilConsiderarDreRepository.save(contaContabilConsiderarDre);
        }
    }

    @DeleteMapping("/delete-conta-contabil-considerar-dre/{contaContabil}")
    public void deleteContaContabilConsiderarDre(@PathVariable("contaContabil") int contaContabil){
        contaContabilConsiderarDreRepository.deleteById(contaContabil);
    }
}
