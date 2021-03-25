package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.ColecaoPermanente;
import br.com.live.repository.ColecaoPermanenteRepository;

@RestController
@CrossOrigin
@RequestMapping("/colecoes-permanentes")
public class ColecaoPermanenteController {

    private ColecaoPermanenteRepository colecaoPermanenteRepository;

    @Autowired
    public ColecaoPermanenteController(ColecaoPermanenteRepository colecaoPermanenteRepository) {
          this.colecaoPermanenteRepository = colecaoPermanenteRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ColecaoPermanente> findAll() {
          return colecaoPermanenteRepository.findAll();
    }
	
}

