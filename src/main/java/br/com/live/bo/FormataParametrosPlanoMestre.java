package br.com.live.bo;

import java.util.ArrayList;
import java.util.List;

import br.com.live.producao.body.BodyParametrosPlanoMestre;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class FormataParametrosPlanoMestre {

	private final static int PLANO1 = 1;
	private final static int PLANO2 = 2;
	private final static int PLANO3 = 3;
	private final static int PLANO4 = 4;
	private final static int PLANO5 = 5;
	private final static int PLANO6 = 6;
	private final static int PLANO7 = 7;
	private final static int PLANO8 = 8;

	private BodyParametrosPlanoMestre parametros;

	public FormataParametrosPlanoMestre(BodyParametrosPlanoMestre parametros) {
		this.parametros = parametros;
	}

	private String formataValue(List<ConteudoChaveNumerica> listConteudo) {

		String texto = "";

		if (listConteudo != null) {

			for (ConteudoChaveNumerica conteudo : listConteudo) {
				if (texto.equalsIgnoreCase(""))
					texto = "(" + Integer.toString(conteudo.value);
				else
					texto = texto + "," + Integer.toString(conteudo.value);
			}

			if (!texto.equalsIgnoreCase(""))
				texto = texto + ")";
		}

		return texto;
	}

	private String formataValueString(List<ConteudoChaveAlfaNum> listConteudo) {

		String texto = "";

		if (listConteudo != null) {

			for (ConteudoChaveAlfaNum conteudo : listConteudo) {
				if (texto.equalsIgnoreCase(""))
					texto = "('" + conteudo.value + "'";
				else
					texto = texto + ",'" + conteudo.value + "'";
			}

			if (!texto.equalsIgnoreCase(""))
				texto = texto + ")";
		}

		return texto;
	}

	public List<ConteudoChaveNumerica> mergeColecoes() {
		List<ConteudoChaveNumerica> agrupColecoes = new ArrayList<ConteudoChaveNumerica>();

		for (ConteudoChaveNumerica conteudo : parametros.colecoes) {
			agrupColecoes.add(conteudo);
		}

		for (ConteudoChaveNumerica conteudo : parametros.colecoesPermanentes) {
			agrupColecoes.add(conteudo);
		}

		return agrupColecoes;
	}

	public String getColecoes() {
		return formataValue(mergeColecoes());
	}

	public String getSubColecoes() {
		return formataValue(parametros.colecoes);
	}

	public String getLinhasProduto() {
		return formataValue(parametros.linhasProduto);
	}

	public String getArtigosProduto() {
		return formataValue(parametros.artigosProduto);
	}

	public String getArtigosCotas() {
		return formataValue(parametros.artigosCotas);
	}

	public String getPublicosAlvo() {
		return formataValue(parametros.publicosAlvos);
	}

	public String getEmbarques() {
		return formataValue(parametros.embarques);
	}

	public String getProdutos() {
		return formataValueString(parametros.produtos);
	}

	public String getCores() {
		return formataValueString(parametros.cores);
	}

	public String getOrigemProdutos() {
		return formataValue(parametros.origProdutos);
	}

	public String getDepositos() {
		return formataValue(parametros.depositos);
	}

	public String getPedidos() {
		return formataValue(parametros.pedidos);
	}

	public String getNaturezas() {
		return formataValue(parametros.naturezas);
	}
	
	public String getPrevisoes( ) {
		return formataValue(parametros.previsoes);
	}

	public int getNrInternoPedido() {
		return parametros.nrInternoPedido;
	}

	public int getPeriodoDemandaInicio() {
		return parametros.perDemInicio01;
	};

	public int getPeriodoDemandaFim() {
		return parametros.perDemFim08;
	};

	public int getPeriodoProcessoInicio() {
		return parametros.perProcInicio01;
	};

	public int getPeriodoProcessoFim() {
		return parametros.perProcFim08;
	};

	public int getPeriodoPlanoDemandaInicio(int plano) {
		switch (plano) {
		case 1:
			return parametros.perDemInicio01;
		case 2:
			return parametros.perDemInicio02;
		case 3:
			return parametros.perDemInicio03;
		case 4:
			return parametros.perDemInicio04;
		case 5:
			return parametros.perDemInicio05;
		case 6:
			return parametros.perDemInicio06;
		case 7:
			return parametros.perDemInicio07;
		case 8:
			return parametros.perDemInicio08;
		default:
			return 0;
		}
	};

	public int getPeriodoPlanoDemandaFim(int plano) {
		switch (plano) {
		case 1:
			return parametros.perDemFim01;
		case 2:
			return parametros.perDemFim02;
		case 3:
			return parametros.perDemFim03;
		case 4:
			return parametros.perDemFim04;
		case 5:
			return parametros.perDemFim05;
		case 6:
			return parametros.perDemFim06;
		case 7:
			return parametros.perDemFim07;
		case 8:
			return parametros.perDemFim08;
		default:
			return 0;
		}
	};

	public int getPeriodoPlanoProcessoInicio(int plano) {
		switch (plano) {
		case 1:
			return parametros.perProcInicio01;
		case 2:
			return parametros.perProcInicio02;
		case 3:
			return parametros.perProcInicio03;
		case 4:
			return parametros.perProcInicio04;
		case 5:
			return parametros.perProcInicio05;
		case 6:
			return parametros.perProcInicio06;
		case 7:
			return parametros.perProcInicio07;
		case 8:
			return parametros.perProcInicio08;
		default:
			return 0;
		}
	};

	public int getPeriodoPlanoProcessoFim(int plano) {
		switch (plano) {
		case 1:
			return parametros.perProcFim01;
		case 2:
			return parametros.perProcFim02;
		case 3:
			return parametros.perProcFim03;
		case 4:
			return parametros.perProcFim04;
		case 5:
			return parametros.perProcFim05;
		case 6:
			return parametros.perProcFim06;
		case 7:
			return parametros.perProcFim07;
		case 8:
			return parametros.perProcFim08;
		default:
			return 0;
		}
	};

	public int getPlanoPeriodoDemanda(int periodo) {

		if (periodo >= parametros.perDemInicio01 && periodo <= parametros.perDemFim01)
			return FormataParametrosPlanoMestre.PLANO1;
		if (periodo >= parametros.perDemInicio02 && periodo <= parametros.perDemFim02)
			return FormataParametrosPlanoMestre.PLANO2;
		if (periodo >= parametros.perDemInicio03 && periodo <= parametros.perDemFim03)
			return FormataParametrosPlanoMestre.PLANO3;
		if (periodo >= parametros.perDemInicio04 && periodo <= parametros.perDemFim04)
			return FormataParametrosPlanoMestre.PLANO4;
		if (periodo >= parametros.perDemInicio05 && periodo <= parametros.perDemFim05)
			return FormataParametrosPlanoMestre.PLANO5;
		if (periodo >= parametros.perDemInicio06 && periodo <= parametros.perDemFim06)
			return FormataParametrosPlanoMestre.PLANO6;
		if (periodo >= parametros.perDemInicio07 && periodo <= parametros.perDemFim07)
			return FormataParametrosPlanoMestre.PLANO7;
		if (periodo >= parametros.perDemInicio08 && periodo <= parametros.perDemFim08)
			return FormataParametrosPlanoMestre.PLANO8;

		return 0;
	}

	public int getPlanoPeriodoProcesso(int periodo) {

		if (periodo >= parametros.perProcInicio01 && periodo <= parametros.perProcFim01)
			return FormataParametrosPlanoMestre.PLANO1;
		if (periodo >= parametros.perProcInicio02 && periodo <= parametros.perProcFim02)
			return FormataParametrosPlanoMestre.PLANO2;
		if (periodo >= parametros.perProcInicio03 && periodo <= parametros.perProcFim03)
			return FormataParametrosPlanoMestre.PLANO3;
		if (periodo >= parametros.perProcInicio04 && periodo <= parametros.perProcFim04)
			return FormataParametrosPlanoMestre.PLANO4;
		if (periodo >= parametros.perProcInicio05 && periodo <= parametros.perProcFim05)
			return FormataParametrosPlanoMestre.PLANO5;
		if (periodo >= parametros.perProcInicio06 && periodo <= parametros.perProcFim06)
			return FormataParametrosPlanoMestre.PLANO6;
		if (periodo >= parametros.perProcInicio07 && periodo <= parametros.perProcFim07)
			return FormataParametrosPlanoMestre.PLANO7;
		if (periodo >= parametros.perProcInicio08 && periodo <= parametros.perProcFim08)
			return FormataParametrosPlanoMestre.PLANO8;

		return 0;
	}

	public boolean consideraDepositos() {
		if (parametros.consideraDepositos == 1)
			return true;
		return false;
	}

	public boolean consideraSemEstoque() {
		if (parametros.mostraProdSemEstoques == 1)
			return true;
		return false;
	}

	public boolean consideraSemDemanda() {
		if (parametros.mostraProdSemPedidos == 1)
			return true;
		return false;
	}

	public boolean consideraPedBloqueados() {
		if (parametros.consideraPedBloqueados == 1)
			return true;
		return false;
	}

	public boolean consideraSemProcesso() {
		if (parametros.mostraProdSemProcessos == 1)
			return true;
		return false;
	}

	public int getPeriodoPadrao() {
		return parametros.periodoPadrao;
	}

	public int getTipoDistribuicao() {
		return parametros.tipoDistribuicao;
	}
	
	public String getDescTipoDistribuicao() {
		
		String descricao = "";

		if (parametros.tipoDistribuicao == 0) descricao = "SEM DISTRIBUIÇÃO";
		if (parametros.tipoDistribuicao == 1) descricao = "POR GRADE PADRÃO";  
		if (parametros.tipoDistribuicao == 2) descricao = "POR GRADE DE VENDA";
		if (parametros.tipoDistribuicao == 3) descricao = "POR GRADE NEGATIVA";
		if (parametros.tipoDistribuicao == 4) descricao = "POR PREVISÃO DE VENDAS";
		
		return descricao;
	}
	
	public int getMultiplicador() { 
		return parametros.multiplicador;
    }

}
