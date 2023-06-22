package br.com.live.sistema.controller;

import br.com.live.sistema.entity.Sistema;
import br.com.live.sistema.model.ConsultaGestaoAtivos;
import br.com.live.sistema.repository.SistemaRepository;
import br.com.live.sistema.service.SistemaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<ConsultaGestaoAtivos> findAllSistemas() {
        return sistemaService.findAllSistemas();
    }

    @RequestMapping(value = "/find-sistema-by-id/{id}", method = RequestMethod.GET)
    public Sistema findByIdSistema(@PathVariable("id") int id) {
        return sistemaRepository.findById(id);
    }

    @RequestMapping(value = "/save-sistema", method = RequestMethod.POST)
    public void saveSistema(@RequestBody Sistema sistema) {
        sistemaService.saveSistema(sistema.id, sistema.nomeSistema, sistema.objetivo, sistema.bancoDeDados, sistema.tipo, sistema.fornecedor, sistema.cnpj, sistema.endereco, 
        		sistema.formaPagto, sistema.temContrato, sistema.contrato, sistema.ambiente, sistema.status, sistema.usuariosAtivos, sistema.capacidadeUsuarios,
        		sistema.gestorResponsavel);
    }

    @RequestMapping(value = "/delete-sistema-by-id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<List<ConsultaGestaoAtivos>> deleteSistemaById(@PathVariable("id") int id) {
    	sistemaService.deleteSistemaById(id);   	
        return new ResponseEntity<> (sistemaService.findAllSistemas(), HttpStatus.OK);
    }
}
