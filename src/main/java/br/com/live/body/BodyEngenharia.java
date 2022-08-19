package br.com.live.body;

import java.util.List;

import br.com.live.entity.Micromovimentos;

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
	
	public String idTempoMaquina;
	public String grupoMaquina;
	public String subGrupoMaquina;
	public float medidaMaquina;
	public float tempoMaquina;
	public float interferenciaMaquina;
	
}
