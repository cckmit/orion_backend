package br.com.live.sistema.controller;

import br.com.live.sistema.model.CronogramaRealizadoProjeto;
import br.com.live.sistema.service.CronogramaRealizadoProjetoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cronograma-realizado-projeto")
public class CronogramaRealizadoProjetoController {

    CronogramaRealizadoProjetoService cronogramaRealizadoProjetoService;

    public CronogramaRealizadoProjetoController(CronogramaRealizadoProjetoService cronogramaRealizadoProjetoService) {
        this.cronogramaRealizadoProjetoService = cronogramaRealizadoProjetoService;
    }

    @GetMapping("/find-cronograma-realizado-projeto/{idProjeto}")
    public List<CronogramaRealizadoProjeto> findCronogramaRealizadoProjeto(@PathVariable("idProjeto") Long idProjeto){
        return cronogramaRealizadoProjetoService.findCronogramaRealizadoProjeto(idProjeto);
    }
}
