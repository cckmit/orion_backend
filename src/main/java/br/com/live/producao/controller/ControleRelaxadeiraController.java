package br.com.live.producao.controller;

import br.com.live.producao.body.BodyControleRelaxadeira;
import br.com.live.producao.model.ConsultaControleRelaxadeira;
import br.com.live.producao.service.ControleRelaxadeiraService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/controle-relaxadeira")
public class ControleRelaxadeiraController {

    final ControleRelaxadeiraService controleRelaxadeiraService;

    public ControleRelaxadeiraController(ControleRelaxadeiraService controleRelaxadeiraService) {
        this.controleRelaxadeiraService = controleRelaxadeiraService;
    }

    @RequestMapping(value = "/consultar-nao-sincronizados/{status}", method = RequestMethod.GET)
    public List<ConsultaControleRelaxadeira> findLancamentoMedidasByIdInspecao(@PathVariable("status") int status) {
        return controleRelaxadeiraService.consultaNaoSincronizados(status);
    }
    
    @RequestMapping(value = "/find-informacoes-rolo/{codigoRolo}", method = RequestMethod.GET)
    public ConsultaControleRelaxadeira findInformacoesRolo(@PathVariable("codigoRolo") int codigoRolo) {
        return controleRelaxadeiraService.findInformacoesRolo(codigoRolo);
    }

    @RequestMapping(value = "/incluir-rolos", method = RequestMethod.POST)
    public List<ConsultaControleRelaxadeira> incluirRolosControleRelaxe(@RequestBody BodyControleRelaxadeira body) {
        controleRelaxadeiraService.salvarRolosControleRelaxadeira(body.status ? 1 : 2, body.codRolo, body.motivosSelect, body.quantidade, body.perdaMetros, body.codAnalista,
                body.largura, body.gramatura, body.metragem);
        return controleRelaxadeiraService.consultaNaoSincronizados(1);
    }

    @RequestMapping(value = "/salvar-sincronizar-motivos", method = RequestMethod.POST)
    public void salvarSincronizarMotivos() {
        controleRelaxadeiraService.salvarSincronizarRegistrosMotivos();
    }

    @RequestMapping(value = "/excluir-rolos/{idRevisao}", method = RequestMethod.DELETE)
    public List<ConsultaControleRelaxadeira> deleteRoloControleRelaxe(@PathVariable("idRevisao") String idRevisao) {
        controleRelaxadeiraService.deleteLancamentoById(idRevisao);
        return controleRelaxadeiraService.consultaNaoSincronizados(1);
    }

    @RequestMapping(value = "/consultar-relatorio-sincronizados/{dataInicio}/{dataFim}/{statusSinc}", method = RequestMethod.GET)
    public List<ConsultaControleRelaxadeira> findLancamentoMedidasByIdInspecao(@PathVariable("dataInicio") String dataInicio, @PathVariable("dataFim") String dataFim, @PathVariable("statusSinc") int statusSinc) {
        return controleRelaxadeiraService.consultaRelatorioRelaxadeira(dataInicio, dataFim, statusSinc);
    }
}
