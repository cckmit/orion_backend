package br.com.live.util.controller;

import br.com.live.util.body.BodyAnexo;
import br.com.live.util.entity.Anexo;
import br.com.live.util.repository.AnexoRepository;
import br.com.live.util.service.AnexoService;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/anexos")
public class AnexoController {

    AnexoRepository anexoRepository;
    AnexoService anexoService;

    public AnexoController(AnexoRepository anexoRepository, AnexoService anexoService) {
        this.anexoRepository = anexoRepository;
        this.anexoService = anexoService;
    }

    @GetMapping("/find-by-chave/{chave}")
    public List<BodyAnexo> findByChave(@PathVariable("chave") String chave){
        return anexoService.findByChave(chave);
    }

    @PostMapping("/delete-multiple-id/{chave}")
    public List<BodyAnexo> deleteByMultipleIdAnexo(@PathVariable("chave") String chave, @RequestBody List<Anexo> anexoIds) {
        return anexoService.deleteByMultipleIdAnexo(chave, anexoIds);
    }

    @PostMapping("/save/{chave}")
    public List<BodyAnexo> saveAnexo(@PathVariable("chave") String chave, @RequestBody BodyAnexo bodyAnexo) throws ParseException {
        return anexoService.saveAnexo(chave, bodyAnexo);
    }
}
