package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.PublicoAlvo;
import br.com.live.repository.PublicoAlvoRepository;

@RestController
@CrossOrigin
@RequestMapping("/publicos")
public class PublicoAlvoController {

    private PublicoAlvoRepository publicoAlvoRepository;

    @Autowired
    public PublicoAlvoController(PublicoAlvoRepository publicoAlvoRepository) {
          this.publicoAlvoRepository = publicoAlvoRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<PublicoAlvo> findByTipo() {
          return publicoAlvoRepository.findAll(); 
    }
	
}

