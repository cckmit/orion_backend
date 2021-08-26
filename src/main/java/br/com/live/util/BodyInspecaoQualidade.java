package br.com.live.util;

import br.com.live.entity.InspecaoQualidadePeca;
import br.com.live.entity.InspecaoQualidadePecaLancto;
import br.com.live.model.OrdemConfeccao;

public class BodyInspecaoQualidade {

	public OrdemConfeccao dadosOrdemConfeccao;
	public boolean ordemValida;	
	public String dataInspecao;
	public InspecaoQualidadePeca inspecaoQualidadePeca;
	public InspecaoQualidadePecaLancto inspecaoQualidadePecaLancto;
	
	public BodyInspecaoQualidade() {}
	
	public BodyInspecaoQualidade(OrdemConfeccao dadosOrdemConfeccao) {
		this.dadosOrdemConfeccao = dadosOrdemConfeccao;
		this.ordemValida = (dadosOrdemConfeccao.ordemProducao > 0);
	}
	
	public BodyInspecaoQualidade(InspecaoQualidadePeca inspecaoQualidadePeca, InspecaoQualidadePecaLancto inspecaoQualidadePecaLancto) {
		this.inspecaoQualidadePeca = inspecaoQualidadePeca;
		this.inspecaoQualidadePecaLancto = inspecaoQualidadePecaLancto;
	}	
}
