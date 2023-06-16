package br.com.live.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.com.live.custom.FechamentoComissaoCustom;
import br.com.live.custom.PoliticaVendasCustom;
import br.com.live.custom.TabelaPrecoCustom;
import br.com.live.entity.LancamentoContabeisImport;
import br.com.live.entity.PoliticaVendas;
import br.com.live.model.ConsultaPoliticaVendas;
import br.com.live.model.DivergenciasPoliticaVendas;
import br.com.live.repository.PoliticaVendasRepository;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.FormataData;

@Transactional
@Service
public class PoliticaVendasService {
	
	private final PoliticaVendasRepository politicaVendasRepository;
	private final TabelaPrecoCustom tabelaPrecoCustom;
	private final PoliticaVendasCustom politicaVendasCustom;
	private final FechamentoComissaoCustom fechamentoComissaoCustom;
	
	public PoliticaVendasService(PoliticaVendasRepository politicaVendasRepository, TabelaPrecoCustom tabelaPrecoCustom, PoliticaVendasCustom politicaVendasCustom,
			FechamentoComissaoCustom fechamentoComissaoCustom) {
		this.politicaVendasRepository = politicaVendasRepository;
		this.tabelaPrecoCustom = tabelaPrecoCustom;
		this.politicaVendasCustom = politicaVendasCustom;
		this.fechamentoComissaoCustom = fechamentoComissaoCustom;
	}
	
	
	public void deleteRegraById(int id) {
		politicaVendasRepository.deleteById(id);
	}
	
	public List<ConteudoChaveAlfaNum> findAllEstacoes(){
		return politicaVendasCustom.findAllEstacoes();
	}
	
	public List<DivergenciasPoliticaVendas> findPedidosDivergencias(boolean regra1, boolean regra2, boolean regra3, boolean regra4, boolean regra5,
			boolean regra6, boolean regra7, boolean regra8, boolean regra9, boolean regra10){
		
		String opcoes = "11,12,14";
		if (regra1) opcoes += (opcoes.isEmpty()) ? "1" : ",1";
		if (regra2) opcoes += (opcoes.isEmpty()) ? "2" : ",2";
		if (regra3) opcoes += (opcoes.isEmpty()) ? "3" : ",3";
		if (regra4) opcoes += (opcoes.isEmpty()) ? "4" : ",4";
		if (regra5) opcoes += (opcoes.isEmpty()) ? "5" : ",5";
		if (regra6) opcoes += (opcoes.isEmpty()) ? "6" : ",6";
		if (regra7) opcoes += (opcoes.isEmpty()) ? "7" : ",7";
		if (regra8) opcoes += (opcoes.isEmpty()) ? "8" : ",8";
		if (regra9) opcoes += (opcoes.isEmpty()) ? "9" : ",9";
		if (regra10) opcoes += (opcoes.isEmpty()) ? "10" : ",10";		
		
		return politicaVendasCustom.findPedidosDivergencias(opcoes);
	}
	
	public List<DivergenciasPoliticaVendas> findDivergenciasGrupoEmbarque(String estacao){
		 
		String[] tabPrecoConcat = estacao.split("[-]");
		int tabCol = Integer.parseInt(tabPrecoConcat[0]);
		int tabMes = Integer.parseInt(tabPrecoConcat[1]);
		int tabSeq = Integer.parseInt(tabPrecoConcat[2]);
		
		return politicaVendasCustom.findDivergenciasGrupoEmbarque(tabCol, tabMes, tabSeq);
	}
	
	public void saveRegra(int id, int tipo, int formaPagamento, int portador, String cnpj, int codFuncionario, float descCapa, int tipoPedido, int depositoItens,
		float descMaxCliente, float comissao, int condPgto, int tipoCliente, int naturezaOperacao, float desconto, String tabelaPreco) {
		
		PoliticaVendas dadosRegra = null;
		int cnpj8 = 0;
		int cnpj4 = 0;
		int cnpj2 = 0;
		
		int tabCol = 0;
		int tabMes = 0;
		int tabSeq = 0;
		
		dadosRegra = politicaVendasRepository.findByIdPoliticaVendas(id);
		
		if ((cnpj != null) && (!cnpj.equalsIgnoreCase(""))) {			
			cnpj8 = Integer.parseInt(cnpj.substring(0, 9));
			cnpj4 = Integer.parseInt(cnpj.substring(10, 13));
			cnpj2 = Integer.parseInt(cnpj.substring(13, 15));
		}
		
		if ((tabelaPreco != null) && (!tabelaPreco.equalsIgnoreCase(""))) {			
			String[] tabPrecoConcat = tabelaPreco.split("[.]");
			tabCol = Integer.parseInt(tabPrecoConcat[0]);
			tabMes = Integer.parseInt(tabPrecoConcat[1]);
			tabSeq = Integer.parseInt(tabPrecoConcat[2]);
		}
		
		if (dadosRegra == null) {
			id = politicaVendasRepository.findNextID();
			dadosRegra = new PoliticaVendas(id, tipo, formaPagamento, portador, cnpj8, cnpj4, cnpj2, codFuncionario, descCapa, tipoPedido, depositoItens, descMaxCliente, comissao, 
					condPgto, tipoCliente, naturezaOperacao, desconto, tabCol, tabMes, tabSeq);
		} 
		politicaVendasRepository.save(dadosRegra);
	}
	
	public List<ConteudoChaveAlfaNum> findAllTabelasAsync(String leitor){
		return tabelaPrecoCustom.findAllTabelasAsync(leitor);
	}
	
	public ConsultaPoliticaVendas importarRegras(List<ConsultaPoliticaVendas> listRegras, int tipo) {
		
		// Deletando registros no BD do Tipo selecionado
		politicaVendasRepository.deleteByTipo(tipo);
		
		PoliticaVendas regras = null;
		
		for (ConsultaPoliticaVendas dados : listRegras) {
			
			try {
				regras = new PoliticaVendas(politicaVendasRepository.findNextID(), tipo, dados.formaPagamento, dados.portador, dados.cnpj9, dados.cnpj4, dados.cnpj2,
						dados.codFuncionario, dados.descCapa, dados.tipoPedido, dados.depositoItens, dados.descMaxCliente, dados.comissao, dados.condPagamento,
						dados.tipoCliente, dados.naturezaOp, dados.desconto, dados.tabCol, dados.tabMes, dados.tabSeq);	
				politicaVendasRepository.saveAndFlush(regras);
			} catch (Exception e) {
				System.out.println(e);
			}
		}		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
