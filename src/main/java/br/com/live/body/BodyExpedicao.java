package br.com.live.body;

import java.util.List;

import br.com.live.model.ConsultaCapacidadeArtigosEnderecos;
import br.com.live.model.Produto;

public class BodyExpedicao {
	public String numeroTag;
	public String endereco;
	public int deposito;
	public String blocoInicio;
	public String blocoFim;
	public int corredorInicio;
	public int corredorFim;
	public int boxInicio;
	public int boxFim;
	public int cestoInicio;
	public int cestoFim;
	public int codCaixa;
	public String usuario;
	
	public List<ConsultaCapacidadeArtigosEnderecos> itens;
	public List<Produto> referencias;
	public List<String> produtosSel;
}
