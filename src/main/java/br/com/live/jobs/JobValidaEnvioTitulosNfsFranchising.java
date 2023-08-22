package br.com.live.jobs;

import br.com.live.administrativo.service.TituloPagamentoNfsFranchisingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobValidaEnvioTitulosNfsFranchising {

    private final static int SEGUNDO = 1000;
    private final static int MINUTO = SEGUNDO * 60;
    private final static int HORA = MINUTO * 60;
    private final static int DIA = HORA * 24;

    TituloPagamentoNfsFranchisingService tituloPagamentoNfsFranchisingService;

    public JobValidaEnvioTitulosNfsFranchising(TituloPagamentoNfsFranchisingService tituloPagamentoNfsFranchisingService) {
        this.tituloPagamentoNfsFranchisingService = tituloPagamentoNfsFranchisingService;
    }

    @Scheduled(fixedRate = MINUTO)
    public void atualizarRetornoTitulosNfsFranchising() throws Exception {
        tituloPagamentoNfsFranchisingService.atualizarRetornoTitulosNfsFranchising();
    }
}
