package br.com.live.sistema.body;

import java.util.List;

import br.com.live.sistema.model.SalvarTipoEmailBi;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodyProgramaBi {
	public String id;
	public String areaModulo;
	public int atividade;
	public String descricao;
	public String ferramenta;
	public String frequencia;
	public String extrator;
	public String planilha;
	public String help;
	public List<SalvarTipoEmailBi> tiposEmail;	
	public List<Long> listaIdsUsuarios;
	
	public List<ConteudoChaveAlfaNum> listaAreasModulos;
	public List<ConteudoChaveNumerica> listaUsuarios;	
}
