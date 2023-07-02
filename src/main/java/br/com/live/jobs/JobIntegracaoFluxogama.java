package br.com.live.jobs;

import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.live.produto.service.IntegracaoFluxogamaService;
import br.com.live.util.entity.Parametros;
import br.com.live.util.repository.ParametrosRepository;

@Component
public class JobIntegracaoFluxogama {
    private final static int SEGUNDO = 1000;
    private final static int MINUTO = SEGUNDO * 60;
    private final static int ATIVO = 1;
    
    IntegracaoFluxogamaService integracaoFluxogamaService;
    ParametrosRepository parametrosRepository;
    
    public JobIntegracaoFluxogama(IntegracaoFluxogamaService integracaoFluxogamaService, ParametrosRepository parametrosRepository) {
    	this.integracaoFluxogamaService = integracaoFluxogamaService;
        this.parametrosRepository = parametrosRepository;
    }

    @Scheduled(fixedRate = MINUTO * 5)
    public void gravarReferenciasIntegracaoFluxogama() throws IOException{
        Parametros params = parametrosRepository.findByIdParametro("INTEGRACAO_FLUXOGAMA_ATIVO");

        if (params.valorInt == ATIVO) {
            integracaoFluxogamaService.obterDadosFluxogamaJob();
        } else {
            System.out.println("Integração Pausada!");
        }
    }
}
