package br.com.live.model;

public class SugestaoReservaMateriaisReservados {

	public int idOrdem;
	public String nivelMaterial;
	public String grupoMaterial;
	public String subMaterial;
	public String itemMaterial;
	public Double qtdeReservado;	
	
	public SugestaoReservaMateriaisReservados(int idOrdem, String nivelTecido, String grupoTecido,
			String subTecido, String itemTecido, Double qtdeReservado) {
		super();
		this.idOrdem = idOrdem;
		this.nivelMaterial = nivelTecido;
		this.grupoMaterial = grupoTecido;
		this.subMaterial = subTecido;
		this.itemMaterial = itemTecido;
		this.qtdeReservado = qtdeReservado;		
	}
}
