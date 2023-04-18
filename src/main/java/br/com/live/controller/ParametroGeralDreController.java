package br.com.live.controller;

import br.com.live.custom.DreLojaCustom;
import br.com.live.entity.ConciliacaoLojaDreEntity;
import br.com.live.entity.ParametroGeralDreEntity;
import br.com.live.model.ConciliacaoLojaDreConsulta;
import br.com.live.repository.ParametroGeralDreRepository;
import br.com.live.service.ParametroGeralDreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/gera-dre-loja")
public class ParametroGeralDreController {

    private ParametroGeralDreRepository parametroGeralDreRepository;
    private DreLojaCustom dreLojaCustom;
    private ParametroGeralDreService parametroGeralDreService;

    @Autowired
    public ParametroGeralDreController(DreLojaCustom dreLojaCustom, ParametroGeralDreRepository parametroGeralDreRepository, ParametroGeralDreService parametroGeralDreService) {
        this.dreLojaCustom = dreLojaCustom;
        this.parametroGeralDreRepository = parametroGeralDreRepository;
        this.parametroGeralDreService = parametroGeralDreService;
    }

    @GetMapping("/find-all-dre-parametros-gerais")
    public List<ParametroGeralDreEntity> findAllDreParametrosGerais(){
        return parametroGeralDreRepository.findAll();
    }

    @GetMapping("/find-dre-parametro-geral-by-id/{id}")
    public Optional<ParametroGeralDreEntity> findDreParametroGeralById(@PathVariable("id") long id){
        return parametroGeralDreRepository.findById(id);
    }

    @GetMapping("/find-dre-parametro-geral-by-mes-ano/{mes}/{ano}")
    public int findDreParametroGeralByMesAno(@PathVariable("mes") int mes, @PathVariable("ano") int ano){
        return dreLojaCustom.findParametrosDreByMesAno(mes, ano);
    }

    @PostMapping("/save-dre-parametro-geral")
    public List<ParametroGeralDreEntity> saveDreParametroGeral(@RequestBody ParametroGeralDreEntity dreParametroGeralEntity){
        return parametroGeralDreService.saveParametroGeralDre(dreParametroGeralEntity);
    }

    @DeleteMapping("/delete-dre-parametro-geral/{id}/{mesDre}/{anoDre}")
    public List<ParametroGeralDreEntity> deleteDreParametroGeral(@PathVariable("id") long id, @PathVariable("mesDre") int mesDre, @PathVariable("anoDre") int anoDre){
        parametroGeralDreService.deleteDreParametroGeral(id, mesDre, anoDre);
        return parametroGeralDreRepository.findAll();
    }

    @PostMapping("/save-dre-conciliacao-loja")
    public void saveDreConciliacaoLoja(@RequestBody List<ConciliacaoLojaDreEntity> conciliacaoLojaDreEntityList){
        parametroGeralDreService.saveDreConciliacaoLoja(conciliacaoLojaDreEntityList);
    }

    @GetMapping("/find-all-dre-conciliacao-mes-ano/{mesDre}/{anoDre}")
    public List<ConciliacaoLojaDreConsulta> findAllDreConciliacaoDreMesAno(@PathVariable("mesDre") int mesDre, @PathVariable("anoDre") int anoDre){
        return dreLojaCustom.findAllDreConciliacaoDreMesAno(mesDre, anoDre);
    }
}
