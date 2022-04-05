package br.com.live.model;

public class SugestaoReservaTecidosReservados {

	public int idOrdem;
	public String nivelTecido;
	public String grupoTecido;
	public String subTecido;
	public String itemTecido;
	public Double qtdeReservado;	
	
	public SugestaoReservaTecidosReservados(int idOrdem, String nivelTecido, String grupoTecido,
			String subTecido, String itemTecido, Double qtdeReservado) {
		super();
		this.idOrdem = idOrdem;
		this.nivelTecido = nivelTecido;
		this.grupoTecido = grupoTecido;
		this.subTecido = subTecido;
		this.itemTecido = itemTecido;
		this.qtdeReservado = qtdeReservado;		
	}
}
