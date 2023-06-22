package br.com.live.administrativo.controller;

import br.com.live.administrativo.body.BodyCustos;
import br.com.live.administrativo.service.CustosService;
import br.com.live.produto.model.CopiaFichaCustos;

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
        custosService.copiarParametrosFichaCustos(body.empresa, body.produtoOrigem, body.tipoParam, body.listSelecionados);
    }
}
