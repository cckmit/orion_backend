package br.com.live.producao.body;

import br.com.live.util.ConteudoChaveNumerica;

import java.util.Date;
import java.util.List;

public class BodyConfeccao {
    public long id;
    public int idCadastro;
    public String descricao;
    public List<ConteudoChaveNumerica> restricoes;
    public List<ConteudoChaveNumerica> rolos;
    public List<ConteudoChaveNumerica> ordens;
	public String dataMeta;
	public String idMetaMes;
	public long idMetaSemana;
	public int mesMeta;
	public int anoMeta;
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
	public int numeroExpedidor;
	public int metaExpedidor;
	public int solicitacao;
	public Date dataRegistro;
	public int periodoProducao;
	public int alternativa;
	public int roteiro;
	public int usuario;
	public String tecido;
	public float largAcomodacao;
	public float compAcomodacao;
	public float largTermo;
	public float compTermo;
	public float largEstampa;
	public float compEstampa; 
	public float largEstampaPoli;
	public float compEstampaPoli;
	public float largPolimerizadeira;
	public float compPolimerizadeira;
	public float largEstampaPrensa;
	public float compEstampaPrensa;
	public String observacao;

	public int ordemProducao;
	public List<ConteudoChaveNumerica> pacotes;
	public List<ConteudoChaveNumerica> estagios;
}
