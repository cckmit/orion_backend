package br.com.live.producao.body;

import java.util.List;

import br.com.live.producao.entity.InspecaoQualidade;
import br.com.live.producao.entity.InspecaoQualidadeLanctoMedida;
import br.com.live.producao.entity.InspecaoQualidadeLanctoPeca;
import br.com.live.producao.model.OrdemConfeccao;

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
	public String observacaoFichaTecnica;
	public int qtdePacotesInspecionados;
	public int qtdeMotivosLancados;
	public int qtdeMedidasLancadas;
	public int qtdePecas;
	
	public BodyInspecaoQualidade() {}
	
	public BodyInspecaoQualidade(OrdemConfeccao dadosOrdemConfeccao, String observacaoFichaTecnica) {
		this.dadosOrdemConfeccao = dadosOrdemConfeccao;
		this.observacaoFichaTecnica = observacaoFichaTecnica;
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

	public BodyInspecaoQualidade(int qtdePacotesInspecionados, int qtdeMotivosLancados, int qtdeMedidasLancadas) {
		this.qtdePacotesInspecionados = qtdePacotesInspecionados;
		this.qtdeMotivosLancados = qtdeMotivosLancados;
		this.qtdeMedidasLancadas = qtdeMedidasLancadas;
	}
	
	public BodyInspecaoQualidade(int qtdePacotesInspecionados, int qtdeMotivosLancados, int qtdeMedidasLancadas, int qtdePecas) {
		this.qtdePacotesInspecionados = qtdePacotesInspecionados;
		this.qtdeMotivosLancados = qtdeMotivosLancados;
		this.qtdeMedidasLancadas = qtdeMedidasLancadas;
		this.qtdePecas = qtdePecas;
	}
}
