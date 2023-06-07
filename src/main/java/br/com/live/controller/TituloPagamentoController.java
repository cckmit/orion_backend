package br.com.live.controller;

import br.com.live.custom.TituloPagamentoCustom;
import br.com.live.model.TituloPagamentoIntegrado;
import br.com.live.service.TituloPagamentoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/titulo-pagamento")
public class TituloPagamentoController {

    private TituloPagamentoCustom tituloPagamentoCustom;

    public TituloPagamentoController(TituloPagamentoService tituloPagamentoService, TituloPagamentoCustom tituloPagamentoCustom) {
        this.tituloPagamentoCustom = tituloPagamentoCustom;
    }

    @GetMapping("find-titulos-integrados/{dataEmissaoInicio}/{dataEmissaoFim}/{cnpjCliente}/{numDuplicata}")
    public List<TituloPagamentoIntegrado> findTitulosIntegradosSystextil(@PathVariable("dataEmissaoInicio") String dataEmissaoInicio, @PathVariable("dataEmissaoFim") String dataEmissaoFim, @PathVariable("cnpjCliente") String cnpjCliente, @PathVariable("numDuplicata") int numDuplicata){
        return tituloPagamentoCustom.findTitulosIntegradosSystextil(dataEmissaoInicio, dataEmissaoFim, cnpjCliente, numDuplicata);
    }

    @GetMapping("find-all-lojas")
    public List<ConteudoChaveAlfaNum> findAllLojas(){
        return tituloPagamentoCustom.findAllLojas();
    }
}
