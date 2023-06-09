package br.com.live.jobs;

import java.io.IOException;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.live.service.IntegracaoFluxogamaService;

@Component
@EnableScheduling
public class JobIntegracaoFluxogama {
    private final static int SEGUNDO = 1000;
    private final static int MINUTO = SEGUNDO * 60;
    
    IntegracaoFluxogamaService integracaoFluxogamaService;
    
    public JobIntegracaoFluxogama(IntegracaoFluxogamaService integracaoFluxogamaService) {
    	this.integracaoFluxogamaService = integracaoFluxogamaService;
    }
    
    @Scheduled(fixedRate = MINUTO * 5)
    public void gravarReferenciasIntegracaoFluxogama() throws IOException{
    	//integracaoFluxogamaService.obterDadosFluxogamaJob();
    }
}
