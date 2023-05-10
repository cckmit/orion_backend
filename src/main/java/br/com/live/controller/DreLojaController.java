package br.com.live.controller;

import br.com.live.body.BodyDreLoja;
import br.com.live.body.BodyDrePdf;
import br.com.live.custom.DreLojaCustom;
import br.com.live.model.ConciliacaoLojaDre;
import br.com.live.model.DreLoja;
import br.com.live.model.DreLojaConsulta;
import br.com.live.service.DreLojaService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/gera-dre-loja")
public class DreLojaController {

    DreLojaCustom dreLojaCustom;
    DreLojaService dreLojaService;

    @Autowired
    public DreLojaController(DreLojaCustom dreLojaCustom, DreLojaService dreLojaService) {
        this.dreLojaCustom = dreLojaCustom;
        this.dreLojaService = dreLojaService;
    }

    @GetMapping("/find-all-dre-loja")
    public List<DreLojaConsulta> findAllDreLoja(){
        return dreLojaCustom.findAllDreLoja();
    }

    @DeleteMapping("/delete-dre-loja/{cnpjLoja}/{mesDre}/{anoDre}")
    public List<DreLojaConsulta> deleteDreLoja(@PathVariable("cnpjLoja") String cnpjLoja, @PathVariable("mesDre") int mesDre, @PathVariable("anoDre") int anoDre){
        dreLojaCustom.deleteDreLoja(cnpjLoja, mesDre, anoDre);
        System.out.println(cnpjLoja +","+mesDre+","+anoDre);
        return dreLojaCustom.findAllDreLoja();
    }

    @PostMapping("/gravar-dre-loja")
    public List<DreLojaConsulta> gravarDreLoja(@RequestBody BodyDreLoja bodyDreLoja){
        dreLojaService.gravarDreLojas(bodyDreLoja.mesDre, bodyDreLoja.anoDre, bodyDreLoja.cnpjLojaList);
        return dreLojaCustom.findAllDreLoja();
    }

    @GetMapping("/find-all-fields-dre-loja-by-cnpj-mes-ano/{cnpjLoja}/{mesDre}/{anoDre}")
    public List<DreLoja> findAllFieldsDreLojaByCnpjAno(@PathVariable("cnpjLoja") String cnpjLoja, @PathVariable("mesDre") int mesDre, @PathVariable("anoDre") int anoDre){
        return dreLojaCustom.findAllFieldsDreLojaByCnpjMesAno(cnpjLoja, mesDre, anoDre);
    }

    @GetMapping("/find-conciliacao-loja-dre-cnpj-mes-ano/{cnpjLoja}/{mesDre}/{anoDre}")
    public ConciliacaoLojaDre findConciliacaoLojaDreCnpjMesAno(@PathVariable("cnpjLoja") String cnpjLoja, @PathVariable("mesDre") int mesDre, @PathVariable("anoDre") int anoDre){
        return dreLojaCustom.findConciliacaoLojaDreCnpjMesAno(cnpjLoja, mesDre, anoDre);
    }

    @RequestMapping(value = "/gerar-pdf-dre", method = RequestMethod.POST)
    public String gerarPdfDre(@RequestBody BodyDrePdf body) throws JRException, FileNotFoundException {
        return dreLojaService.gerarPdfDre(body.cnpjLoja, body.nomeLoja, body.mesDre, body.anoDre);
    }
}
