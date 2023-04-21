package br.com.live.controller;

import br.com.live.entity.Servico;
import br.com.live.model.ConsultaGestaoAtivos;
import br.com.live.repository.ServicoRepository;
import br.com.live.service.ServicoService;
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
        		servico.gestorResponsavel);
    }

    @RequestMapping(value = "/delete-servico-by-id/{id}", method = RequestMethod.DELETE)
    public List<Servico> deleteServicoById(@PathVariable("id") int id) {
        servicoService.deleteById(id);
        return servicoRepository.findAll();
    }

}
