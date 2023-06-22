package br.com.live.producao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.producao.custom.PeriodoProducaoCustom;
import br.com.live.producao.model.PeriodoProducao;

@RestController
@CrossOrigin
@RequestMapping("/periodos-producao")
public class PeriodoProducaoController {

    private PeriodoProducaoCustom periodoProducaoCustom;

    @Autowired
    public PeriodoProducaoController(PeriodoProducaoCustom periodoProducaoCustom) {
          this.periodoProducaoCustom = periodoProducaoCustom;
    }

    @RequestMapping(value = "/demanda", method = RequestMethod.GET)
    public List<PeriodoProducao> findPeriodosDemanda() {                  
        return periodoProducaoCustom.findPeriodosDemanda();
    }

    @RequestMapping(value = "/producao", method = RequestMethod.GET)
    public List<PeriodoProducao> findPeriodosProducao() {                  
        return periodoProducaoCustom.findPeriodosProducao();
    }
    
    @RequestMapping(value = "/existe/{periodo}", method = RequestMethod.GET)
    public boolean findByPeriodo(@PathVariable("periodo") int periodo) {                  
        return periodoProducaoCustom.periodoExiste(periodo);
    }	
}
