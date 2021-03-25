package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.Deposito;
import br.com.live.repository.DepositoRepository;

@RestController
@CrossOrigin
@RequestMapping("/depositos")
public class DepositoController {

    private DepositoRepository depositoRepository;

    @Autowired
    public DepositoController(DepositoRepository depositoRepository) {
          this.depositoRepository = depositoRepository;
    }
	
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Deposito> findAll() {
          return depositoRepository.findAll();
    }
	
}
