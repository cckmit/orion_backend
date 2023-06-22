package br.com.live.administrativo.body;

import java.util.List;

import br.com.live.administrativo.model.ConsultaFechamentoComissoes;
import br.com.live.util.ConteudoChaveAlfaNum;

public class BodyFinanceiro {
	
	public int id;
	public int mes;
	public int ano;
	public List<ConteudoChaveAlfaNum> listRepresentante;
	public List<ConsultaFechamentoComissoes> listFechamento;
	public List<ConsultaFechamentoComissoes> listFechamento2;
	public List<ConsultaFechamentoComissoes> tabImportDevMostruario;
	public String estacao;
	public float valorAReceber;

}
