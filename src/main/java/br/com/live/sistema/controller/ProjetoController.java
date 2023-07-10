package br.com.live.sistema.controller;
import br.com.live.sistema.body.BodyProjeto;
import br.com.live.sistema.entity.ProjetoEntity;
import br.com.live.sistema.model.BriefingProjeto;
import br.com.live.sistema.model.EscopoProjeto;
import br.com.live.sistema.model.TermoAberturaProjeto;
import br.com.live.sistema.repository.ProjetoRepository;
import br.com.live.sistema.service.ProjetoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/projetos")
public class ProjetoController {

    ProjetoRepository projetoRepository;
    ProjetoService projetoService;

    public ProjetoController(ProjetoRepository projetoRepository, ProjetoService projetoService) {
        this.projetoRepository = projetoRepository;
        this.projetoService = projetoService;
    }

    @GetMapping("/find-all")
    public List<ProjetoEntity> findAllProjetos(){
        return projetoRepository.findAll();
    }

    @GetMapping("/find-by-id/{id}")
    public Optional<ProjetoEntity> findByIdProjeto(@PathVariable("id") Long id){
        return projetoRepository.findById(id);
    }

    @GetMapping("/delete-by-id/{id}")
    public List<ProjetoEntity> deleteByIdProjeto(@PathVariable("id") Long id){
        projetoRepository.deleteById(id);
        return projetoRepository.findAll();
    }

    @PostMapping("/save")
    public List<ProjetoEntity> saveProjeto(@RequestBody BodyProjeto projetoBody){
        return projetoService.saveProjeto(projetoBody);
    }

    @GetMapping("/find-briefing-id/{id}")
    public BriefingProjeto findBriefingProjeto(@PathVariable("id") Long id){
        return projetoService.findBriefingProjeto(id);
    }

    @PostMapping("/save-briefing")
    public void saveBriefing(@RequestBody BriefingProjeto briefingProjeto){
        projetoService.saveBriefing(briefingProjeto);
    }

    @GetMapping("/find-termo-abertura-id/{id}")
    public TermoAberturaProjeto findTermoAberturaProjeto(@PathVariable("id") Long id){
        return projetoService.findTermoAberturaProjeto(id);
    }

    @PostMapping("/save-termo-abertura")
    public void saveTermoAbertura(@RequestBody TermoAberturaProjeto termoAberturaProjeto){
        projetoService.saveTermoAberturaProjeto(termoAberturaProjeto);
    }

    @GetMapping("/find-escopo-id/{id}")
    public EscopoProjeto findEscopoProjeto(@PathVariable("id") Long id){
        return projetoService.findEscopoProjeto(id);
    }

    @PostMapping("/save-escopo")
    public void saveEscopo(@RequestBody EscopoProjeto escopoProjeto){
        projetoService.saveEscopo(escopoProjeto);
    }
}