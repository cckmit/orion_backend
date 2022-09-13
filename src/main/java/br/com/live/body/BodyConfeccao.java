package br.com.live.body;

import br.com.live.util.ConteudoChaveNumerica;

import java.util.Date;
import java.util.List;

public class BodyConfeccao {
    public long id;
    public String descricao;
    public List<ConteudoChaveNumerica> restricoes;
    public List<ConteudoChaveNumerica> rolos;
    public List<ConteudoChaveNumerica> ordens;
	public String dataMeta;
	public String idMetaMes;
	public long idMetaSemana;
	public int mes;
	public int ano;
	public int codEstagio;
	public int metaMes;
	public int diasUteis;
	public int metaDiaria;
	public int metaAjustada;
	public String idMes;
	public int nrSemana;
	public Date dataInicio;
	public Date dataFim;
	public int metaReal;
	public int metaRealTurno;
	public int metaAjustadaTurno;	
}
