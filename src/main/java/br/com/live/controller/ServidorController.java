package br.com.live.controller;

import br.com.live.body.BodyGestaoAtivos;
import br.com.live.custom.GestaoAtivosCustom;
import br.com.live.entity.GestaoAtivosOportunidade;
import br.com.live.entity.Servidor;
import br.com.live.model.ConsultaGestaoAtivos;
import br.com.live.repository.GestaoAtivosOportunidadeRepository;
import br.com.live.repository.ServidorRepository;
import br.com.live.service.ServidorService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/servidor")
public class ServidorController {

    private ServidorRepository servidorRepository;
    private ServidorService servidorService;
    private GestaoAtivosOportunidadeRepository gestaoAtivosOportunidadeRepository;

    @Autowired
    public ServidorController(ServidorRepository servidorRepository, ServidorService servidorService, GestaoAtivosOportunidadeRepository gestaoAtivosOportunidadeRepository) {
        this.servidorRepository = servidorRepository;
        this.servidorService = servidorService;
        this.gestaoAtivosOportunidadeRepository = gestaoAtivosOportunidadeRepository;
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
    
    @RequestMapping(value = "/find-oportunidade-by-id/{id}", method = RequestMethod.GET)
    public GestaoAtivosOportunidade findOportunidadeById(@PathVariable("id") String id) {
        return gestaoAtivosOportunidadeRepository.findByIdOp(id);
    }

    @RequestMapping(value = "/save-servidor", method = RequestMethod.POST)
    public void saveServidor(@RequestBody Servidor servidor) {
        servidorService.saveServidor(servidor.id, servidor.nomeServidor, servidor.maquinaFisica, servidor.sistemaOperacional, servidor.ip, servidor.hd, servidor.memoria, 
        		servidor.processador, servidor.aplicacoes, servidor.documentacao, servidor.status, servidor.gestorResponsavel);
    }
 
    @RequestMapping(value = "/save-oportunidade", method = RequestMethod.POST)
    public void saveOportunidade(@RequestBody BodyGestaoAtivos oportunidade) {
        servidorService.saveOportunidade(oportunidade.id, oportunidade.tipo, oportunidade.dataCadastro, oportunidade.prioridade, oportunidade.descricao, oportunidade.objetivo,
        		oportunidade.contextualizacao, oportunidade.descricaoProblema, oportunidade.perguntasEmAberto, oportunidade.riscos);
    }

    @RequestMapping(value = "/delete-servidor-by-id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<List<ConsultaGestaoAtivos>> deleteServidorById(@PathVariable("id") int id) {
        servidorService.deleteById(id);
        return new ResponseEntity<> (servidorService.findAllServidores(), HttpStatus.OK);
    }
}
