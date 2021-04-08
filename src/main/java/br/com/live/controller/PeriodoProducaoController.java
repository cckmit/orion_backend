package br.com.live.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.custom.PeriodoProducaoCustom;

@RestController
@CrossOrigin
@RequestMapping("/periodos-producao")
public class PeriodoProducaoController {

    private PeriodoProducaoCustom periodoProducaoCustom;

    @Autowired
    public PeriodoProducaoController(PeriodoProducaoCustom periodoProducaoCustom) {
          this.periodoProducaoCustom = periodoProducaoCustom;
    }
	
    @RequestMapping(value = "/existe/{periodo}", method = RequestMethod.GET)
    public boolean findByPeriodo(@PathVariable("periodo") int periodo) {                  
        return periodoProducaoCustom.periodoExiste(periodo);
    }
	
}
