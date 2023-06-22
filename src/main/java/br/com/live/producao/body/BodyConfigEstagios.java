package br.com.live.producao.body;

import java.util.List;

import br.com.live.producao.entity.ConfiguracaoEstagios;

public class BodyConfigEstagios {
	
	public List<ConfiguracaoEstagios> estagios;
	
	public int lead;
	public int sequencia;
	public String dataInicio;
	public String dataFim;
	
	public int anoCalendario;
	public int periodoInicio;
	public int periodoFim;
	
	public boolean consideraSabado;
	public boolean consideraDomingo;
	public boolean consideraFeriado;
}
