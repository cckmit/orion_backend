package br.com.live.controller;

import br.com.live.body.BodyRelacionamentoLojaDre;
import br.com.live.custom.RelacionamentoLojaDreCustom;
import br.com.live.entity.CentroCustoLojaEntity;
import br.com.live.entity.SupervisorLojaEntity;
import br.com.live.model.ConsultaRelacionamentoLojaDre;
import br.com.live.repository.CentroCustoLojaRepository;
import br.com.live.repository.SupervisorLojaRepository;
import br.com.live.service.RelacionamentoLojaDreService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/relac-loja-dre")
public class RelacionamentoLojaDreController {

    private CentroCustoLojaRepository centroCustoLojaRepository;
    private RelacionamentoLojaDreCustom relacionamentoLojaDreCustom;
    private SupervisorLojaRepository supervisorLojaRepository;
    private RelacionamentoLojaDreService relacionamentoLojaDreService;

    @Autowired
    public RelacionamentoLojaDreController(SupervisorLojaRepository supervisorLojaRepository, RelacionamentoLojaDreCustom relacionamentoLojaDreCustom, CentroCustoLojaRepository centroCustoLojaRepository, RelacionamentoLojaDreService relacionamentoLojaDreService) {
        this.supervisorLojaRepository = supervisorLojaRepository;
        this.relacionamentoLojaDreCustom = relacionamentoLojaDreCustom;
        this.centroCustoLojaRepository = centroCustoLojaRepository;
        this.relacionamentoLojaDreService = relacionamentoLojaDreService;
    }

    @GetMapping("/find-centro-custo-loja-by-id/{id}")
    public Optional<CentroCustoLojaEntity> findCentroCustoLojaById(@PathVariable("id") Long id) {
        return centroCustoLojaRepository.findById(id);
    }

    @GetMapping("/lojas")
    public List<ConteudoChaveAlfaNum> findAllLojas() {
        return relacionamentoLojaDreCustom.findAllLojas();
    }

    @GetMapping("/centro-custos")
    public List<ConteudoChaveNumerica> findAllCentroCustos() {
        return relacionamentoLojaDreCustom.findAllCentroCustos();
    }

    @PostMapping("/save-centro-custo-loja")
    public CentroCustoLojaEntity saveCentroCusto(@RequestBody CentroCustoLojaEntity centroCustoLoja) {
        return centroCustoLojaRepository.save(centroCustoLoja);
    }

    @DeleteMapping("/delete-by-id-centro-custo-loja/{id}")
    public void deleteCentroCustoById(@PathVariable Long id) {
        centroCustoLojaRepository.deleteById(id);
    }


    @GetMapping("/find-all-supervisores")
    public List<SupervisorLojaEntity> findAllSupervisoresLoja() {
        return supervisorLojaRepository.findAll();
    }

    @GetMapping("/find-supervisor-by-id/{cnpjLoja}")
    public Optional<SupervisorLojaEntity> findByIdSupervisorLoja(@PathVariable String cnpjLoja) {
        return supervisorLojaRepository.findById(cnpjLoja);
    }

    @GetMapping("/supervisores")
    public List<ConteudoChaveAlfaNum> findAllSupervisores() {
        return relacionamentoLojaDreCustom.findAllSupervisores();
    }

    @PostMapping("/save-supervisor")
    public SupervisorLojaEntity saveSupervisorLoja(@RequestBody SupervisorLojaEntity supervisorLoja) {
        return supervisorLojaRepository.save(supervisorLoja);
    }

    @DeleteMapping("/delete-by-id-supervisor/{cnpjLoja}")
    public void deleteSupervisorLojaById(@PathVariable String cnpjLoja) {
        supervisorLojaRepository.deleteById(cnpjLoja);
    }

    @GetMapping("/find-all-lojas-relac-dre")
    public List<ConsultaRelacionamentoLojaDre> findAllLojasRelacionadasDre() {
        return relacionamentoLojaDreService.consultaRelacionamentoLojaDre();
    }

    @GetMapping("/find-centro-custo-loja-by-cnpj/{cnpjLoja}")
    public List<ConteudoChaveNumerica> findCentroCustoLojaByCnpj(@PathVariable("cnpjLoja") String cnpjLoja) {
        return relacionamentoLojaDreCustom.findAllCentroCustoLoja(cnpjLoja);
    }

    @DeleteMapping("/delete-loja-relac-by-cnpj/{cnpjLoja}")
    public List<ConsultaRelacionamentoLojaDre> deleteRleacionamentoLojaDreByCnpj(@PathVariable String cnpjLoja) {
        relacionamentoLojaDreService.deleteRleacionamentoLojaDreByCnpj(cnpjLoja);
        return relacionamentoLojaDreService.consultaRelacionamentoLojaDre();
    }

    @PostMapping("/save-relac-loja-dre")
    public List<ConsultaRelacionamentoLojaDre> saveRleacionamentoLojaDre(@RequestBody BodyRelacionamentoLojaDre body) {
        relacionamentoLojaDreService.saveRleacionamentoLojaDre(body.cnpjLoja, body.cnpjSupervisor, body.centroCustos);
        return relacionamentoLojaDreService.consultaRelacionamentoLojaDre();
    }

}
