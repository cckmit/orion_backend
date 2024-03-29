package br.com.live.comercial.body;

import java.util.List;

import br.com.live.comercial.model.ConsultaMinutaTransporte;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodyMinutaTransporte {
	public List<ConteudoChaveNumerica> empresas;
	public List<ConteudoChaveNumerica> localCaixa;
	public List<ConteudoChaveAlfaNum> enderecos;
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
	
	public List<ConsultaMinutaTransporte> notasSelecionadas;
	public String horaLibPaypalInicio;
	public String horaLibPaypalFim;
	public String dataInicio;
	public String dataFim;
	public int minuta;
	public List<Integer> notasRemoveSelect;
	public int localCaixaVolume;
	public int volume;
	public String usuario;
}
