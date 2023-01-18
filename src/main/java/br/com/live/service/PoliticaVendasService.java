package br.com.live.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import br.com.live.entity.PoliticaVendas;
import br.com.live.repository.PoliticaVendasRepository;

@Transactional
@Service
public class PoliticaVendasService {
	
	private final PoliticaVendasRepository politicaVendasRepository;
	
	public PoliticaVendasService(PoliticaVendasRepository politicaVendasRepository) {
		this.politicaVendasRepository = politicaVendasRepository;
	}
	
	
	public void deleteRegraById(int id) {
		politicaVendasRepository.deleteById(id);
	}
	
	public void saveRegra(int id, int tipo, int formaPagamento, int portador, String cnpj, int codFuncionario, float descCapa, int tipoPedido, int depositoItens,
		float descMaxCliente, float comissao, int condPgto, int naturezaOperacao, float desconto) {
		
		PoliticaVendas dadosRegra = null;
		int cnpj8 = 0;
		int cnpj4 = 0;
		int cnpj2 = 0;
		
		dadosRegra = politicaVendasRepository.findByIdPoliticaVendas(id);
		
		if ((cnpj != null) && (!cnpj.equalsIgnoreCase(""))) {			
			cnpj8 = Integer.parseInt(cnpj.substring(0, 9));
			cnpj4 = Integer.parseInt(cnpj.substring(10, 13));
			cnpj2 = Integer.parseInt(cnpj.substring(13, 15));
		}
		
		if (dadosRegra == null) {
			id = politicaVendasRepository.findNextID();
			dadosRegra = new PoliticaVendas(id, tipo, formaPagamento, portador, cnpj8, cnpj4, cnpj2, codFuncionario, descCapa, tipoPedido, depositoItens, descMaxCliente, comissao, 
					condPgto, naturezaOperacao, desconto);
		} 
		politicaVendasRepository.save(dadosRegra);
	}
}
