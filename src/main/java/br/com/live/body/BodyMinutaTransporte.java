package br.com.live.body;

import java.util.List;

import br.com.live.util.ConteudoChaveNumerica;

public class BodyMinutaTransporte {
	public List<ConteudoChaveNumerica> empresas;
	public List<ConteudoChaveNumerica> localCaixa;
	public String transportadora;
	public int pedido;
	public int nota;
	public int flagTipoMinuta;
	
	public String dataEmiInicio;
	public String dataEmiFim;
	public String dataLibPaypalIni;
	public String dataLibPaypalFim;
	
	public String dataInicioBox;
	public String dataFimBox;
	public String horaInicio;
	public String horaFim;
	
	public int codEmpresa;
	public boolean consideraPedidosCD;
}
