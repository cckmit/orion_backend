package br.com.live.controller;

import br.com.live.custom.EstabelecimentoPagSeguroLojaCustom;
import br.com.live.entity.EstabelecimentoPagSeguroLojaEntity;
import br.com.live.model.ConsultaEstabelecimentoPagSeguroLoja;
import br.com.live.repository.EstabelecimentoPagSeguroLojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("relacionamento-estabelecimento-pagseguro-loja")
public class EstabelecimentoPagSeguroLojaController {

    public EstabelecimentoPagSeguroLojaRepository estabelecimentoPagSeguroLojaRepository;
    public EstabelecimentoPagSeguroLojaCustom estabelecimentoPagSeguroLojaCustom;

    @Autowired
    public EstabelecimentoPagSeguroLojaController(EstabelecimentoPagSeguroLojaRepository estabelecimentoPagSeguroLojaRepository, EstabelecimentoPagSeguroLojaCustom estabelecimentoPagSeguroLojaCustom) {
        this.estabelecimentoPagSeguroLojaRepository = estabelecimentoPagSeguroLojaRepository;
        this.estabelecimentoPagSeguroLojaCustom = estabelecimentoPagSeguroLojaCustom;
    }

    @GetMapping("/find-estabelecimentos")
    public List<ConsultaEstabelecimentoPagSeguroLoja> findAllEstabelecimentos(){
        return estabelecimentoPagSeguroLojaCustom.findAllEstabelecimento();
    }

    @GetMapping("/find-estabelecimentos-by-id/{idEstabelecimento}")
    public Optional<EstabelecimentoPagSeguroLojaEntity> findEstabelecimentosById(@PathVariable("idEstabelecimento") long idEstabelecimento){
        return estabelecimentoPagSeguroLojaRepository.findById(idEstabelecimento);
    }

    @PostMapping("/save-estabelecimento")
    public EstabelecimentoPagSeguroLojaEntity saveEstabelecimento(@RequestBody EstabelecimentoPagSeguroLojaEntity estabelecimentoPagSeguro) {
        return estabelecimentoPagSeguroLojaRepository.save(estabelecimentoPagSeguro);
    }

    @DeleteMapping("/delete-by-id-estabelecimento/{idEstabelecimento}")
    public void deleteEstabelecimento(@PathVariable("idEstabelecimento") long idEstabelecimento) {
        estabelecimentoPagSeguroLojaRepository.deleteById(idEstabelecimento);
    }
}
