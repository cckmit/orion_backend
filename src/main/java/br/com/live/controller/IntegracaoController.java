package br.com.live.controller;

import br.com.live.entity.Integracao;
import br.com.live.model.ConsultaGestaoAtivos;
import br.com.live.repository.IntegracaoRepository;
import br.com.live.service.IntegracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/integracao")
public class IntegracaoController {

    private IntegracaoRepository integracaoRepository;
    private IntegracaoService integracaoService;

    @Autowired
    public IntegracaoController(IntegracaoRepository integracaoRepository, IntegracaoService integracaoService) {
        this.integracaoRepository = integracaoRepository;
        this.integracaoService = integracaoService;
    }

    @RequestMapping(value = "/find-all-integracoes", method = RequestMethod.GET)
    public List<ConsultaGestaoAtivos> findAllIntegracao() {
        return integracaoService.findAllIntegracoes();
    }

    @RequestMapping(value = "/find-integracao-by-id/{id}", method = RequestMethod.GET)
    public Integracao findByIdIntegracao(@PathVariable("id") int id) {
        return integracaoRepository.findById(id);
    }

    @RequestMapping(value = "/save-integracao", method = RequestMethod.POST)
    public void saveIntegracao(@RequestBody Integracao integracao) {
        integracaoService.saveIntegracao(integracao.id, integracao.nomeIntegracao, integracao.objetivo, integracao.tipoIntegracao, integracao.tipoConexao, integracao.sistemaOrigem, 
        		integracao.sistemaDestino, integracao.servidor, integracao.status, integracao.fornecedor, integracao.cnpj, integracao.endereco, integracao.gestorResponsavel);
    }

    @RequestMapping(value = "/delete-integracao-by-id/{id}", method = RequestMethod.DELETE)
    public List<ConsultaGestaoAtivos> deleteIntegracaoById(@PathVariable("id") int id) {
        integracaoService.deleteById(id);
        return integracaoService.findAllIntegracoes();
    }
}
