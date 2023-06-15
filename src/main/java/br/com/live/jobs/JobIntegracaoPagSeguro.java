package br.com.live.jobs;

import br.com.live.entity.Parametros;
import br.com.live.repository.ParametrosRepository;
import br.com.live.service.MovimentoPagSeguroService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class JobIntegracaoPagSeguro {

    private final static int SEGUNDO = 1000;
    private final static int MINUTO = SEGUNDO * 60;
    private final static int HORA = MINUTO * 60;

    MovimentoPagSeguroService movimentoPagSeguroService;
    ParametrosRepository parametrosRepository;

    public JobIntegracaoPagSeguro(MovimentoPagSeguroService movimentoPagSeguroService, ParametrosRepository parametrosRepository) {
        this.movimentoPagSeguroService = movimentoPagSeguroService;
        this.parametrosRepository = parametrosRepository;
    }

    @Scheduled(fixedRate = HORA * 5)
    public void gravarMovimentosPagSeguroJob() {
        movimentoPagSeguroService.GravarMovimentosPagSeguroJob();
    }
}
