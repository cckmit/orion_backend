package br.com.live.sistema.controller;

import br.com.live.sistema.entity.PlanoNegocioEntity;
import br.com.live.sistema.repository.PlanoNegocioRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/plano-negocio")
public class PlanoNegocioController {

    PlanoNegocioRepository planoNegocioRepository;

    public PlanoNegocioController(PlanoNegocioRepository planoNegocioRepository) {
        this.planoNegocioRepository = planoNegocioRepository;
    }

    @GetMapping("/find-all-plano-negocio")
    public List<PlanoNegocioEntity> findAllPlanoNegocio(){
        return planoNegocioRepository.findAll();
    }

    @DeleteMapping("/delete-plano-negocio/{id}")
    public List<PlanoNegocioEntity> deletePlanoNegocio(@PathVariable("id") Long id){
        planoNegocioRepository.deleteById(id);
        return planoNegocioRepository.findAll();
    }

    @PostMapping("/save-plano-negocio")
    public List<PlanoNegocioEntity> savePlanoNegocio(@RequestBody PlanoNegocioEntity planoNegocio) {
        if (planoNegocio.id == 0) planoNegocio.id = planoNegocioRepository.findNextId();
        planoNegocioRepository.save(planoNegocio);
        return planoNegocioRepository.findAll();
    }
}

