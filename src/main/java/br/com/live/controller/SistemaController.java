package br.com.live.controller;

import br.com.live.entity.Sistema;
import br.com.live.repository.SistemaRepository;
import br.com.live.service.SistemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/sistema")
public class SistemaController {

    private SistemaRepository sistemaRepository;
    private SistemaService sistemaService;

    @Autowired
    public SistemaController(SistemaRepository sistemaRepository, SistemaService sistemaService) {
        this.sistemaRepository = sistemaRepository;
        this.sistemaService = sistemaService;
    }

    @RequestMapping(value = "/find-all-sistemas", method = RequestMethod.GET)
    public List<Sistema> findAllSistemas() { return sistemaRepository.findAll(); }

    @RequestMapping(value = "/find-sistema-by-id/{id}", method = RequestMethod.GET)
    public Sistema findByIdSistema(@PathVariable("id") int id) {
        return sistemaRepository.findById(id);
    }

    @RequestMapping(value = "/save-sistema", method = RequestMethod.POST)
    public void saveSistema(@RequestBody Sistema sistema) {
        sistemaService.saveSistema(sistema.id, sistema.nomeSistema, sistema.objetivo, sistema.bancoDeDados, sistema.tipo, sistema.fornecedor, sistema.cnpj, sistema.endereco, sistema.formaPagto, sistema.temContrato, sistema.contrato, sistema.ambiente, sistema.usuariosAtivos, sistema.capacidadeUsuarios);
    }

    @RequestMapping(value = "/delete-sistema-by-id/{id}", method = RequestMethod.DELETE)
    public List<Sistema> deleteSistemaById(@PathVariable("id") int id) {
        sistemaService.deleteById(id);
        return sistemaRepository.findAll();
    }
}
