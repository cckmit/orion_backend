package br.com.live.body;

import java.util.List;

import br.com.live.entity.IndicadoresDiario;
import br.com.live.entity.IndicadoresMensal;
import br.com.live.entity.IndicadoresSemanal;

public class BodyIndicadores {
	
	public int id;
	public int idIndicador;
	public String idGrupo;
	public int idArea;
	public int idDepartamento;
	public int idSetor;
	public int idUndMed;
	public String descricaoGrupo;
	public String descricaoArea;
	public String descricaoDepartamento;
	public String descricaoSetor;
	public String nomeIndicador;
	public String descricaoUndMed;
	public int grupoIndicador;
	public int area;
	public int departamento;
	public int setor;
	public int gestorAvaliado;
	public String unidadeMedida;
	public String frequenciaMonitoramento;
	public String fonteDados;
	public String polaridade;
	public int responsavelRegistro;
	public int responsavelPublicacao;
	public String observacao;
	public String variaveis;
	public String formulaCalculo;
	public List<IndicadoresMensal> valoresMensal;
	public List<IndicadoresSemanal> valoresSemanal; 
	public List<IndicadoresDiario> valoresDiario;

}
