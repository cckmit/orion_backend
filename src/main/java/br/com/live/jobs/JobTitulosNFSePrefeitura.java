package br.com.live.jobs;

import br.com.live.administrativo.custom.TituloPagamentoCustom;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.live.administrativo.service.TituloPagamentoService;

@Component
public class JobTitulosNFSePrefeitura {

    private final static int SEGUNDO = 1000;
    private final static int MINUTO = SEGUNDO * 60;
    private final static int HORA = MINUTO * 60;
    private final static int DIA = HORA * 24;

    TituloPagamentoService tituloPagamentoService;
    TituloPagamentoCustom tituloPagamentoCustom;

    public JobTitulosNFSePrefeitura(TituloPagamentoService tituloPagamentoService, TituloPagamentoCustom tituloPagamentoCustom) {
        this.tituloPagamentoService = tituloPagamentoService;
        this.tituloPagamentoCustom = tituloPagamentoCustom;
    }

    @Scheduled(fixedRate = HORA)
    public void gerarTitulosNFSePrefeitura(){
        //tituloPagamentoCustom.gerarNumeroLote();
        tituloPagamentoService.gerarTitulosNFSePrefeituraJob();
    }
}
