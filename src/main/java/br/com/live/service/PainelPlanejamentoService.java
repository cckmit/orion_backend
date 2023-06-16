package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.PainelPlanejamentoCustom;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Service
@Transactional
public class PainelPlanejamentoService {
	
	private final PainelPlanejamentoCustom painelPlanejamentoCustom;

    public PainelPlanejamentoService(PainelPlanejamentoCustom painelPlanejamentoCustom) {
        this.painelPlanejamentoCustom = painelPlanejamentoCustom;
    }
    
    public List<ConteudoChaveNumerica> findAllColecaoWithPermanentes(){
    	return painelPlanejamentoCustom.findAllColecaoWithPermanentes();
    }
    
    public List<ConteudoChaveNumerica> findAllSubColecoes(){
    	return painelPlanejamentoCustom.findAllSubColecoes();
    }
    
    public List<ConteudoChaveNumerica> findAllLinhaProduto(){
    	return painelPlanejamentoCustom.findAllLinhaProduto();
    }

    public List<ConteudoChaveNumerica> findAllArtigo(){
    	return painelPlanejamentoCustom.findAllArtigo();
    }
    
    public List<ConteudoChaveNumerica> findAllArtigoCotas(String artigoCotas){
    	return painelPlanejamentoCustom.findAllArtigoCotas(artigoCotas);
    }
    
    public List<ConteudoChaveNumerica> findAllContaEstoque(){
    	return painelPlanejamentoCustom.findAllContaEstoque();
    }
    
    public List<ConteudoChaveNumerica> findAllPublicoAlvo(){
    	return painelPlanejamentoCustom.findAllPublicoAlvo();
    }
    
    public List<ConteudoChaveNumerica> findAllSegmento(){
    	return painelPlanejamentoCustom.findAllSegmento();
    }
    
    public List<ConteudoChaveNumerica> findAllFaixaEtaria(){
    	return painelPlanejamentoCustom.findAllFaixaEtaria();
    }
    
    public List<ConteudoChaveAlfaNum> findAllComplemento(){
    	return painelPlanejamentoCustom.findAllComplemento();
    }
    
    public List<ConteudoChaveNumerica> findAllDepositos(){
    	return painelPlanejamentoCustom.findAllDepositos();
    }
    
    public List<ConteudoChaveAlfaNum> findAllPeriodosEmbarque(){
    	return painelPlanejamentoCustom.findAllPeriodosEmbarque();
    }
    
    public List<ConteudoChaveNumerica> findAllPeriodosProducao(){
    	return painelPlanejamentoCustom.findAllPeriodosProducao();
    }
    
    public List<ConteudoChaveNumerica> findAllPeriodosCarteira(){
    	return painelPlanejamentoCustom.findAllPeriodosCarteira();
    }
    
    public List<ConteudoChaveNumerica> findAllNumInterno(){
    	return painelPlanejamentoCustom.findAllNumInterno();
    }
}
