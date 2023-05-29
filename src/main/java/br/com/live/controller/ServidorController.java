package br.com.live.controller;

import br.com.live.entity.Servidor;
import br.com.live.model.ConsultaGestaoAtivos;
import br.com.live.repository.ServidorRepository;
import br.com.live.service.ServidorService;
import br.com.live.util.ConteudoChaveAlfaNum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/servidor")
public class ServidorController {

    private ServidorRepository servidorRepository;
    private ServidorService servidorService;

    @Autowired
    public ServidorController(ServidorRepository servidorRepository, ServidorService servidorService) {
        this.servidorRepository = servidorRepository;
        this.servidorService = servidorService;
    }

    @RequestMapping(value = "/find-all-servidores", method = RequestMethod.GET)
    public List<ConsultaGestaoAtivos> findAllServidores() { 
    	return servidorService.findAllServidores(); 
    }
    
    @RequestMapping(value = "/find-usuario/{usuario}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findUser(@PathVariable("usuario") String usuario) {
        return servidorService.findUser(usuario);
    }

    @RequestMapping(value = "/find-servidor-by-id/{id}", method = RequestMethod.GET)
    public Servidor findByIdServidor(@PathVariable("id") int id) {
        return servidorRepository.findById(id);
    }

    @RequestMapping(value = "/save-servidor", method = RequestMethod.POST)
    public void saveServidor(@RequestBody Servidor servidor) {
        servidorService.saveServidor(servidor.id, servidor.nomeServidor, servidor.maquinaFisica, servidor.sistemaOperacional, servidor.ip, servidor.hd, servidor.memoria, 
        		servidor.processador, servidor.aplicacoes, servidor.documentacao, servidor.status, servidor.gestorResponsavel);
    }

    @RequestMapping(value = "/delete-servidor-by-id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<List<ConsultaGestaoAtivos>> deleteServidorById(@PathVariable("id") int id) {
        servidorService.deleteById(id);
        return new ResponseEntity<> (servidorService.findAllServidores(), HttpStatus.OK);
    }
}
