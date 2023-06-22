package br.com.live.comercial.body;

import java.util.List;

import br.com.live.comercial.model.ConsultaCapacidadeArtigosEnderecos;
import br.com.live.comercial.model.SugestaoColeta;
import br.com.live.produto.model.Produto;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

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
	public int usuario;
	public String usuarioSystextil;
	public String ruaInicio;
	public String ruaFim;
	public int volume;
	public String notaFiscal;
	
	public List<ConsultaCapacidadeArtigosEnderecos> itens;
	public List<Produto> referencias;
	public List<String> produtosSel;
	public List<String> enderecos;
	
	public String dataEmissaoInicio;
	public String dataEmissaoFim;
	public String dataEmbarqueInicio;
	public String dataEmbarqueFim;
	public List<ConteudoChaveNumerica> empresas;
	public List<ConteudoChaveAlfaNum> clientes;
	public List<ConteudoChaveAlfaNum> transportadoras;
	public List<ConteudoChaveNumerica> representantes;	
	public Long idAreaColeta;
	public String descArea;
	public String endInicioArea;
	public String endFimArea;
	
	public long idLoteArea;
	public long idUsuarioLote;
	public List<SugestaoColeta> pedidosLoteSugColeta;
	public List<ConteudoChaveNumerica> listColetoresArea;
	public List<ConteudoChaveAlfaNum> usuarioMov;
	public String tipoMov;
	public String dataInicio;
	public String dataFim;
	public int rua;
	public int tipoCaixa;

	public int tipoCliente;
	public int prioridade;
	public List<ConteudoChaveNumerica> artigos;
}
