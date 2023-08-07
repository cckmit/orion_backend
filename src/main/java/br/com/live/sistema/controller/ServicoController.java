package br.com.live.sistema.controller;

import br.com.live.sistema.entity.Servico;
import br.com.live.sistema.model.ConsultaGestaoAtivos;
import br.com.live.sistema.repository.ServicoRepository;
import br.com.live.sistema.service.ServicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/servico")
public class ServicoController {

    private ServicoRepository servicoRepository;
    private ServicoService servicoService;

    @Autowired
    public ServicoController(ServicoRepository servicoRepository, ServicoService servicoService) {
        this.servicoRepository = servicoRepository;
        this.servicoService = servicoService;
    }

    @RequestMapping(value = "/find-all-servicos", method = RequestMethod.GET)
    public List<ConsultaGestaoAtivos> findAllServicos() {
    	return servicoService.findAllServicos(); 
    }

    @RequestMapping(value = "/find-servico-by-id/{id}", method = RequestMethod.GET)
    public Servico findServicoById(@PathVariable("id") int id) {
        return servicoRepository.findById(id);
    }

    @RequestMapping(value = "/save-servico", method = RequestMethod.POST)
    public void saveServico(@RequestBody Servico servico) {
        servicoService.saveServico(servico.id, servico.nomeServico, servico.objetivo, servico.timeResponsavel, servico.disponibilidade, servico.tecnicosFornecedores,
        		servico.gestorResponsavel, servico.status);
    }

    @RequestMapping(value = "/delete-servico-by-id/{id}", method = RequestMethod.DELETE)
    public List<Servico> deleteServicoById(@PathVariable("id") int id) {
        servicoService.deleteById(id);
        return servicoRepository.findAll();
    }

}
