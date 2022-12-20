package br.com.live.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_130")
public class CaixasParaEnderecar {
	
	@Id
	@Column(name = "numero_caixa")	
	public int numeroCaixa;
	
	@Column(name = "situacao_caixa")	
	public int situacaoCaixa;

	public int usuario;
	
	@Column(name = "data_hora_inicio")	
	public Date dataHoraInicio;
	
	@Column(name = "data_hora_fim")	
	public Date dataHoraFim;
	
	public String endereco;
	
	@Column(name = "usuario_systextil")	
	public String usuarioSystextil;

	@Column(name = "caixa_na_esteira")
	public int caixaNaEsteira;
	
	public CaixasParaEnderecar() {
	}

	public CaixasParaEnderecar(int numeroCaixa, int situacaoCaixa, int usuario, Date dataHoraInicio, Date dataHoraFim, String usuarioSystextil, String endereco, int caixaNaEsteira) {
		this.numeroCaixa = numeroCaixa;
		this.situacaoCaixa = situacaoCaixa;
		this.usuario = usuario;
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.usuarioSystextil = usuarioSystextil;
		this.endereco = endereco;
		this.caixaNaEsteira = caixaNaEsteira;
	}
}
