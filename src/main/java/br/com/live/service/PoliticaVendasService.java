package br.com.live.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.com.live.custom.TabelaPrecoCustom;
import br.com.live.entity.LancamentoContabeisImport;
import br.com.live.entity.PoliticaVendas;
import br.com.live.model.ConsultaPoliticaVendas;
import br.com.live.repository.PoliticaVendasRepository;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.FormataData;

@Transactional
@Service
public class PoliticaVendasService {
	
	private final PoliticaVendasRepository politicaVendasRepository;
	private final TabelaPrecoCustom tabelaPrecoCustom;
	
	public PoliticaVendasService(PoliticaVendasRepository politicaVendasRepository, TabelaPrecoCustom tabelaPrecoCustom) {
		this.politicaVendasRepository = politicaVendasRepository;
		this.tabelaPrecoCustom = tabelaPrecoCustom;
	}
	
	
	public void deleteRegraById(int id) {
		politicaVendasRepository.deleteById(id);
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
