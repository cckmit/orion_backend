package br.com.live.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import br.com.live.producao.service.SuprimentoService;

@Component
public class JobRequisicaoAlmoxarifado {

    private final static int SEGUNDO = 1000;
    private final static int MINUTO = SEGUNDO * 60;
    private final static int HORA = MINUTO * 60;

	private final SuprimentoService suprimentoService;
	
	public JobRequisicaoAlmoxarifado(SuprimentoService suprimentoService) {
		this.suprimentoService = suprimentoService;
	}
	
	/*
    @Scheduled(fixedRate = HORA)
    public void gerarRequisicaoAlmoxParaPedCompraIntegrado() {
        suprimentoService.geracaoRequisicoesAlmoxParaPedidosComprasIntegrados();
    }
    */	
}
