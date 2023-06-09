package br.com.live.jobs;

import br.com.live.service.TituloPagamentoService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class JobProcessoReferenciado {

    private final static int HORA = 3600000;
    private final static int DOZE_EM_DOZE_HORAS = HORA * 12;

    public JobProcessoReferenciado() {        
    }

    @Scheduled(fixedRate = DOZE_EM_DOZE_HORAS)
    public void gravarProcessosReferenciados(){        
    	System.out.println("gravarProcessosReferenciados");    	    	
    }

}
