package br.com.live.controller;

import br.com.live.entity.Servidor;
import br.com.live.repository.ServidorRepository;
import br.com.live.service.ServidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public List<Servidor> findAllServidores() throws IOException { return servidorService.findAllServidores(); }

    @RequestMapping(value = "/find-servidor-by-id/{id}", method = RequestMethod.GET)
    public Servidor findByIdServidor(@PathVariable("id") int id) {
        return servidorRepository.findById(id);
    }

    @RequestMapping(value = "/save-servidor", method = RequestMethod.POST)
    public void saveServidor(@RequestBody Servidor servidor) {
        servidorService.saveServidor(servidor.id, servidor.nomeServidor, servidor.maquinaFisica, servidor.sistemaOperacional, servidor.ip, servidor.hd, servidor.memoria, servidor.processador, servidor.aplicacoes, servidor.documentacao);
    }

    @RequestMapping(value = "/delete-servidor-by-id/{id}", method = RequestMethod.DELETE)
    public List<Servidor> deleteServidorById(@PathVariable("id") int id) {
        servidorService.deleteById(id);
        return servidorRepository.findAll();
    }
}
