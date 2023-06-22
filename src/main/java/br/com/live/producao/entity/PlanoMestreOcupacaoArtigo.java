package br.com.live.producao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orion_026")
public class PlanoMestreOcupacaoArtigo {

	@Id
	public String id;

	@Column(name = "num_plano_mestre")
	public long idPlanoMestre;
	public int estagio;
	public int artigo;

	@Column(name = "qtde_capacidade_pecas")
	public int qtdeCapacidadePecas;

	@Column(name = "qtde_plano_pecas")
	public int qtdePlanoPecas;

	@Column(name = "qtde_programado_pecas")
	public int qtdeProgramadoPecas;

	@Column(name = "perc_ocupacao_pecas")
	public double percOcupacaoPecas;

	@Column(name = "qtde_falta_sobra_pecas")
	public int qtdeFaltaSobraPecas;

	@Column(name = "qtde_capacidade_minutos")
	public int qtdeCapacidadeMinutos;

	@Column(name = "qtde_plano_minutos")
	public double qtdePlanoMinutos;

	@Column(name = "qtde_programado_minutos")
	public double qtdeProgramadoMinutos;

	@Column(name = "perc_ocupacao_minutos")
	public double percOcupacaoMinutos;

	@Column(name = "qtde_falta_sobra_minutos")
	public double qtdeFaltaSobraMinutos;

	public PlanoMestreOcupacaoArtigo() {

	}

	public PlanoMestreOcupacaoArtigo(long idPlanoMestre, int estagio, int artigo, int qtdeCapacidadePecas, int qtdePlanoPecas,
			int qtdeProgramadoPecas, double percOcupacaoPecas, int qtdeFaltaSobraPecas, int qtdeCapacidadeMinutos,
			double qtdePlanoMinutos, double qtdeProgramadoMinutos, double percOcupacaoMinutos, double qtdeFaltaSobraMinutos) {

		this.id = idPlanoMestre + "-" + estagio + "-" + artigo;
		this.idPlanoMestre = idPlanoMestre;
		this.estagio = estagio;
		this.artigo = artigo;
		this.qtdeCapacidadePecas = qtdeCapacidadePecas;
		this.qtdePlanoPecas = qtdePlanoPecas;
		this.qtdeProgramadoPecas = qtdeProgramadoPecas;
		this.percOcupacaoPecas = percOcupacaoPecas;
		this.qtdeFaltaSobraPecas = qtdeFaltaSobraPecas;
		this.qtdeCapacidadeMinutos = qtdeCapacidadeMinutos;
		this.qtdePlanoMinutos = qtdePlanoMinutos;
		this.qtdeProgramadoMinutos = qtdeProgramadoMinutos;
		this.percOcupacaoMinutos = percOcupacaoMinutos;
		this.qtdeFaltaSobraMinutos = qtdeFaltaSobraMinutos;		
		
	}
}