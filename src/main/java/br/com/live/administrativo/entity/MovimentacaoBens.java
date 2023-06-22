package br.com.live.administrativo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_100")
public class MovimentacaoBens {
	
	@Id	
	public int sequencia;
	
	@Column(name = "id_bem")	
	public int idBem;
	
	@Column(name = "tipo_movimentacao")	
	public int tipoMovimentacao;
	
	@Column(name = "cnpj_origem")	
	public String cnpjOrigem;
	
	@Column(name = "cnpj_destino")	
	public String cnpjDestino;
	
	@Column(name = "data_envio")	
	public Date dataEnvio;
	
	@Column(name = "nota_fiscal")	
	public String notaFiscal;
	
	public String observacao;
	public int usuario;

	public MovimentacaoBens() {
	}

	public MovimentacaoBens(int sequencia, int idBem, int tipoMovimentacao, String cnpjOrigem, String cnpjDestino,
			Date dataEnvio, String notaFiscal, String observacao, int usuario) {	
		this.sequencia = sequencia;
		this.idBem = idBem;
		this.tipoMovimentacao = tipoMovimentacao;
		this.cnpjOrigem = cnpjOrigem;
		this.cnpjDestino = cnpjDestino;
		this.dataEnvio = dataEnvio;
		this.notaFiscal = notaFiscal;
		this.observacao = observacao;
		this.usuario = usuario;
	}
}
