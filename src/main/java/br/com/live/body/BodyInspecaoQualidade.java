package br.com.live.body;

import java.util.List;

import br.com.live.entity.InspecaoQualidade;
import br.com.live.entity.InspecaoQualidadeLanctoMedida;
import br.com.live.entity.InspecaoQualidadeLanctoPeca;
import br.com.live.model.OrdemConfeccao;

public class BodyInspecaoQualidade {

	public OrdemConfeccao dadosOrdemConfeccao;
	public boolean ordemValida;	
	public String dataInspecao;
	public InspecaoQualidade inspecaoQualidade;
	public InspecaoQualidadeLanctoPeca inspecaoQualidadeLanctoPeca;
	public List<InspecaoQualidadeLanctoMedida> inspecaoQualidadeLanctoMedidas;
	public long idInspecaoLiberar;
	public String usuarioLiberador;
	public String observacaoLiberacao;
	
	public BodyInspecaoQualidade() {}
	
	public BodyInspecaoQualidade(OrdemConfeccao dadosOrdemConfeccao) {
		this.dadosOrdemConfeccao = dadosOrdemConfeccao;
		this.ordemValida = (dadosOrdemConfeccao.ordemProducao > 0);
	}
	
	public BodyInspecaoQualidade(InspecaoQualidade inspecaoQualidade, InspecaoQualidadeLanctoPeca inspecaoQualidadeLanctoPeca) {
		this.inspecaoQualidade = inspecaoQualidade;
		this.inspecaoQualidadeLanctoPeca = inspecaoQualidadeLanctoPeca;
	}
	
	public BodyInspecaoQualidade(InspecaoQualidade inspecaoQualidade, List<InspecaoQualidadeLanctoMedida> inspecaoQualidadeLanctoMedidas) {
		this.inspecaoQualidade = inspecaoQualidade;
		this.inspecaoQualidadeLanctoMedidas = inspecaoQualidadeLanctoMedidas;
	}	
}
