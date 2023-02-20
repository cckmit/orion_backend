package br.com.live.controller;

import br.com.live.body.BodyAtualizaComplemento;
import br.com.live.body.BodyCustos;
import br.com.live.model.CopiaFichaCustos;
import br.com.live.service.CustosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/custos")
public class CustosController {

    private final CustosService custosService;

    @Autowired
    public CustosController(CustosService custosService) {
        this.custosService = custosService;
    }

    @RequestMapping(value = "/find-produtos-destino", method = RequestMethod.POST)
    public List<CopiaFichaCustos> findProdutos(@RequestBody BodyCustos body) {
        return custosService.findProdutosFichaCustos(body.produtoOrigem);
    }

    @RequestMapping(value = "/copiar-produtos-ficha-custos", method = RequestMethod.POST)
    public void copyProdutosFichaCustos(@RequestBody BodyCustos body) {
        custosService.copiarParametrosFichaCustos(body.empresa, body.produtoOrigem, body.tipoParam, body.mesOrigem, body.anoOrigem, body.anoDestino, body.mesDestino, body.listSelecionados);
    }
}
