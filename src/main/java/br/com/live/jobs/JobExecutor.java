package br.com.live.jobs;

import br.com.live.service.TituloPagamentoService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class JobExecutor {

    private final static int SEGUNDO = 1000;
    private final static int MINUTO = 60000;
    private final static int HORA = 3600000;
    private final static int DIA = HORA * 24;

    @Scheduled(fixedRate = MINUTO)
    public void gerarTitulosNFSePrefeitura(){
        //tituloPagamentoService.gerarTitulosNFSePrefeituraJob();
    }
}
