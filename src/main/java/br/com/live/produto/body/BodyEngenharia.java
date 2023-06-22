package br.com.live.produto.body;

import java.util.List;

import br.com.live.produto.entity.Micromovimentos;
import br.com.live.produto.entity.TempoMaquinaCM;
import br.com.live.produto.model.ConsultaOperacaoXMicromovimentos;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodyEngenharia {
	public int id;
	public String descricao;
	public int titulo;
	public int centimetrosCone;
	public int centimetrosCone2;
	public int centimetrosCone3;

	public String maquina;

	public int idTipoPonto;
	public int sequencia;
	
	public int tipoFio1;
	public int tipoFio2;
	public int tipoFio3;

	public float consumoFio;
	public float comprimentoCostura;

	public String idRegistro;
	public String referencia;
	public String idConsumoMet;
	public String observacao;

	public int idTipoFio;
	
	public String codigo;
	public float tempo;
	public float interferencia;
	public boolean editMode;
	
	public List<Micromovimentos> tabImportar;
	
	public long idTempoMaquina;
	public String grupoMaquina;
	public String subGrupoMaquina;
	public float medidaMaquina;
	public float tempoMaquina;
	public float interferenciaMaquina;
	
	public List<TempoMaquinaCM> tabImportarTempoMaq;
	
	public long idOperXMicroMovimento;
	public int codOperacao;
	public int tipo;
	public String idMicromovimento;
	public int idTpMaquina;
	public List<ConteudoChaveAlfaNum> listIdMicromovimento;
	public List<ConteudoChaveNumerica> listIdTempoMaquina;
	public float total;
	public int codOperacaoOrigem;
	public int codOperacaoDestino;
	
	public List<Long> listaId;
	
	public List<String> listaRefenciasFtDigital;
	public List<ConsultaOperacaoXMicromovimentos> listMicromov;
	public String operacao;
	public float tempoNormal;
}
