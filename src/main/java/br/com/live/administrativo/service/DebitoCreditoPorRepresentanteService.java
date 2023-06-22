package br.com.live.administrativo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.administrativo.custom.DebitoCreditoPorRepresentanteCustom;
import br.com.live.administrativo.entity.DebitoCreditoPorRepresentante;
import br.com.live.administrativo.model.ConsultaDebCredPorRepresentante;
import br.com.live.administrativo.repository.DebitoCreditoPorRepresentanteRepository;
import br.com.live.comercial.entity.ValorDescontoClientesImportados;
import br.com.live.produto.entity.TiposFio;
import br.com.live.util.FormataData;

@Service
@Transactional
public class DebitoCreditoPorRepresentanteService {
	
	private final DebitoCreditoPorRepresentanteCustom debitoCreditoPorRepresentanteCustom;
	private final DebitoCreditoPorRepresentanteRepository debitoCreditoPorRepresentanteRepository;

    public DebitoCreditoPorRepresentanteService(DebitoCreditoPorRepresentanteCustom debitoCreditoPorRepresentanteCustom,
    		DebitoCreditoPorRepresentanteRepository debitoCreditoPorRepresentanteRepository) {
        this.debitoCreditoPorRepresentanteCustom = debitoCreditoPorRepresentanteCustom;
        this.debitoCreditoPorRepresentanteRepository = debitoCreditoPorRepresentanteRepository;
    }
    
    public List<ConsultaDebCredPorRepresentante> findAllDebitoCredito(){
		return debitoCreditoPorRepresentanteCustom.findAllDebitoCredito();
	}
    
    public String findUsuarioById(int id) {
    	return debitoCreditoPorRepresentanteCustom.findUsuarioById(id);
    }
    
    public void saveLancamento(String dataLancto, int mes, int ano, int tipo, String campanha, int representante, String observacao, 
			float valor, int usuario) {
    	
    	DebitoCreditoPorRepresentante dados = null;
    	int nextId = debitoCreditoPorRepresentanteCustom.findNextId();
    	
    	dados = new DebitoCreditoPorRepresentante(nextId, FormataData.parseStringToDate(dataLancto), mes, ano, tipo, campanha, representante, observacao, valor, usuario);
		
		try {
			debitoCreditoPorRepresentanteRepository.save(dados);
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao gravar!");
		}
	}
    
    public void deleteById(int id){ 
    	debitoCreditoPorRepresentanteRepository.deleteById(id);}

}
