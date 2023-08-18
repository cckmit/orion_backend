package br.com.live.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.live.administrativo.service.ProcessoReferenciadoService;

@Component
public class JobProcessoReferenciado {

    private final static int HORA = 3600000;
    private final static int CADA_2HORAS = HORA * 2;
    private final ProcessoReferenciadoService processoReferenciadoService; 
    
    public JobProcessoReferenciado(ProcessoReferenciadoService processoReferenciadoService) {
    	this.processoReferenciadoService = processoReferenciadoService;
    }

    @Scheduled(fixedRate = CADA_2HORAS)
    public void gravarProcessosReferenciados(){        
    	System.out.println("gravarProcessosReferenciados");
    	processoReferenciadoService.atualizarNotas();
    }

}
