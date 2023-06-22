package br.com.live.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.live.administrativo.service.ProcessoReferenciadoService;

@Component
public class JobProcessoReferenciado {

    private final static int HORA = 3600000;
    private final static int DOZE_EM_DOZE_HORAS = HORA * 12;
    private final ProcessoReferenciadoService processoReferenciadoService; 
    
    public JobProcessoReferenciado(ProcessoReferenciadoService processoReferenciadoService) {
    	this.processoReferenciadoService = processoReferenciadoService;
    }

    @Scheduled(fixedRate = DOZE_EM_DOZE_HORAS)
    public void gravarProcessosReferenciados(){        
    	System.out.println("gravarProcessosReferenciados");
    	processoReferenciadoService.atualizarNotas();
    }

}
