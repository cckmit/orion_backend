package br.com.live.util;

import java.util.List;

import br.com.live.entity.ConfiguracaoEstagios;

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
