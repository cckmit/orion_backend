package br.com.live.jobs;

import br.com.live.service.TituloPagamentoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobTitulosNFSePrefeitura {

    private final static int SEGUNDO = 1000;
    private final static int MINUTO = SEGUNDO * 60;
    private final static int HORA = MINUTO * 60;
    private final static int DIA = HORA * 24;

    TituloPagamentoService tituloPagamentoService;

    public JobTitulosNFSePrefeitura(TituloPagamentoService tituloPagamentoService) {
        this.tituloPagamentoService = tituloPagamentoService;
    }

    @Scheduled(fixedRate = HORA)
    public void gerarTitulosNFSePrefeitura(){
    	System.out.println("gerarTitulosNFSePrefeitura");
        tituloPagamentoService.gerarTitulosNFSePrefeituraJob();
    }

}
