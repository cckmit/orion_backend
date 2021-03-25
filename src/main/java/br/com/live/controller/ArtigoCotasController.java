package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.ArtigoCotas;
import br.com.live.repository.ArtigoCotasRepository;

@RestController
@CrossOrigin
@RequestMapping("/artigoscotas")
public class ArtigoCotasController {

    private ArtigoCotasRepository artigoCotasRepository;

    @Autowired
    public ArtigoCotasController(ArtigoCotasRepository artigoCotasRepository) {
          this.artigoCotasRepository = artigoCotasRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ArtigoCotas> findAll() {
          return artigoCotasRepository.findAll();
    }
	
}
