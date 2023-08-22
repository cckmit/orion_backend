package br.com.live.administrativo.controller;

import br.com.live.administrativo.model.LogTituloNfsFranchising;
import br.com.live.administrativo.model.TituloPagamentoNfsFranchisingConsulta;
import br.com.live.administrativo.service.TituloPagamentoNfsFranchisingService;
import br.com.live.util.ConteudoChaveAlfaNum;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/titulo-pagamento-franchising")
public class TituloPagamentoNfsFranchisingController {

    private TituloPagamentoNfsFranchisingService tituloPagamentoNfsFranchisingService;

    public TituloPagamentoNfsFranchisingController(TituloPagamentoNfsFranchisingService tituloPagamentoNfsFranchisingService) {
        this.tituloPagamentoNfsFranchisingService = tituloPagamentoNfsFranchisingService;
    }

    @GetMapping("find-all/{dataEmissaoInicio}/{dataEmissaoFim}/{situacao}")
    public List<TituloPagamentoNfsFranchisingConsulta> findAll(@PathVariable("dataEmissaoInicio") String dataEmissaoInicio, @PathVariable("dataEmissaoFim") String dataEmissaoFim, @PathVariable("situacao") int situacao){
        return tituloPagamentoNfsFranchisingService.findTitulosPagamentoNfsFranchising(dataEmissaoInicio, dataEmissaoFim, situacao);
    }

    @PostMapping("/integrar-nfs-portal-prefeitura")
    public void integrarNfsFranchising(@RequestBody List<String> listTitulosSelecionados) throws Exception {
        tituloPagamentoNfsFranchisingService.integrarNfsFranchising(listTitulosSelecionados);
    }

    @GetMapping("find-all-historico/{dataEmissaoInicio}/{dataEmissaoFim}/{cnpj}/{numDuplicata}")
    public List<LogTituloNfsFranchising> findAllHistoricoTitulosPagamentoNfsFranchising(@PathVariable("dataEmissaoInicio") String dataEmissaoInicio, @PathVariable("dataEmissaoFim") String dataEmissaoFim, @PathVariable("cnpj") String cnpj, @PathVariable("numDuplicata") String numDuplicata){
        return tituloPagamentoNfsFranchisingService.findAllHistoricoTitulosPagamentoNfsFranchising(dataEmissaoInicio, dataEmissaoFim, cnpj, numDuplicata);
    }

    @GetMapping("find-all-lojas-historico")
    public List<ConteudoChaveAlfaNum> findAllLojasHistoricoNfsFranchising(){
        return tituloPagamentoNfsFranchisingService.findAllLojasHistoricoNfsFranchising();
    }

}
