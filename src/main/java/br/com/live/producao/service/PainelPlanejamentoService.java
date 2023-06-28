package br.com.live.producao.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.producao.custom.PainelPlanejamentoCustom;
import br.com.live.producao.model.ConsultaPainelPlanejamento;
import br.com.live.producao.model.ConsultaPainelPlanejamentoListas;
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
    
    public List<ConteudoChaveNumerica> findAllArtigoCotas(){
    	return painelPlanejamentoCustom.findAllArtigoCotas();
    }
    
    public List<ConteudoChaveNumerica> findAllOrdensProducao(int ordemProducao){
    	return painelPlanejamentoCustom.findAllOrdensProducao(ordemProducao);
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
    
    public List<ConteudoChaveNumerica> findAllPeriodoAReceber(){
    	return painelPlanejamentoCustom.findAllPeriodoAReceber();
    }
    
    public List<ConteudoChaveNumerica> findAllPeriodosCarteira(){
    	return painelPlanejamentoCustom.findAllPeriodosCarteira();
    }
    
    public List<ConteudoChaveNumerica> findAllNumInterno(){
    	return painelPlanejamentoCustom.findAllNumInterno();
    }
    
    public List<ConteudoChaveNumerica> findAllEstagios(){
    	return painelPlanejamentoCustom.findAllEstagios();
    }
    
    public ConsultaPainelPlanejamentoListas findAcabados(List<ConteudoChaveNumerica> listColecao, List<ConteudoChaveNumerica> listSubColecao,
            List<ConteudoChaveNumerica> listLinhaProduto, List<ConteudoChaveNumerica> listArtigo, List<ConteudoChaveNumerica> listArtigoCota, List<ConteudoChaveNumerica> listContaEstoq,
            List<ConteudoChaveNumerica> listPublicoAlvo, List<ConteudoChaveNumerica> listSegmento, List<ConteudoChaveNumerica> listFaixaEtaria, List<ConteudoChaveAlfaNum> listComplemento,
            List<ConteudoChaveNumerica> listDeposito, List<ConteudoChaveAlfaNum> listPerEmbarque, List<ConteudoChaveNumerica> listPerProducao, List<ConteudoChaveNumerica> listPerCarteira,
            List<ConteudoChaveNumerica> listNumInterno, int bloqueado) {
    	
    	List<ConsultaPainelPlanejamento> listaPainelPlanejamento = painelPlanejamentoCustom.findAcabadosPlanejamento(ConteudoChaveNumerica.parseValueToString(listColecao), ConteudoChaveNumerica.parseValueToString(listSubColecao),
        		ConteudoChaveNumerica.parseValueToString(listLinhaProduto), ConteudoChaveNumerica.parseValueToString(listArtigo), ConteudoChaveNumerica.parseValueToString(listArtigoCota),
        		ConteudoChaveNumerica.parseValueToString(listContaEstoq), ConteudoChaveNumerica.parseValueToString(listPublicoAlvo), ConteudoChaveNumerica.parseValueToString(listSegmento),
        		ConteudoChaveNumerica.parseValueToString(listFaixaEtaria), ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listDeposito),
        		ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), ConteudoChaveNumerica.parseValueToString(listPerProducao), ConteudoChaveNumerica.parseValueToString(listPerCarteira),
        		ConteudoChaveNumerica.parseValueToString(listNumInterno), bloqueado);
    	
    	List<ConsultaPainelPlanejamento> listaPainelDetalharEstoque = painelPlanejamentoCustom.findAcabadosDetalharEstoque(ConteudoChaveNumerica.parseValueToString(listColecao), ConteudoChaveNumerica.parseValueToString(listSubColecao),
        		ConteudoChaveNumerica.parseValueToString(listLinhaProduto), ConteudoChaveNumerica.parseValueToString(listArtigo), ConteudoChaveNumerica.parseValueToString(listArtigoCota),
        		ConteudoChaveNumerica.parseValueToString(listContaEstoq), ConteudoChaveNumerica.parseValueToString(listPublicoAlvo), ConteudoChaveNumerica.parseValueToString(listSegmento),
        		ConteudoChaveNumerica.parseValueToString(listFaixaEtaria), ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listDeposito),
        		ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), ConteudoChaveNumerica.parseValueToString(listPerProducao), ConteudoChaveNumerica.parseValueToString(listPerCarteira),
        		ConteudoChaveNumerica.parseValueToString(listNumInterno), bloqueado);
    	
    	List<ConsultaPainelPlanejamento> listaPainelDetalharCarteira = painelPlanejamentoCustom.findAcabadosDetalharCarteira(ConteudoChaveNumerica.parseValueToString(listColecao), ConteudoChaveNumerica.parseValueToString(listSubColecao),
    	        		ConteudoChaveNumerica.parseValueToString(listLinhaProduto), ConteudoChaveNumerica.parseValueToString(listArtigo), ConteudoChaveNumerica.parseValueToString(listArtigoCota),
    	        		ConteudoChaveNumerica.parseValueToString(listContaEstoq), ConteudoChaveNumerica.parseValueToString(listPublicoAlvo), ConteudoChaveNumerica.parseValueToString(listSegmento),
    	        		ConteudoChaveNumerica.parseValueToString(listFaixaEtaria), ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listDeposito),
    	        		ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), ConteudoChaveNumerica.parseValueToString(listPerProducao), ConteudoChaveNumerica.parseValueToString(listPerCarteira),
    	         		ConteudoChaveNumerica.parseValueToString(listNumInterno), bloqueado);
    	
    	List<ConsultaPainelPlanejamento> listaPainelDetalharOrdens = painelPlanejamentoCustom.findAcabadosDetalharOrdens(ConteudoChaveNumerica.parseValueToString(listColecao), ConteudoChaveNumerica.parseValueToString(listSubColecao),
        		ConteudoChaveNumerica.parseValueToString(listLinhaProduto), ConteudoChaveNumerica.parseValueToString(listArtigo), ConteudoChaveNumerica.parseValueToString(listArtigoCota),
        		ConteudoChaveNumerica.parseValueToString(listContaEstoq), ConteudoChaveNumerica.parseValueToString(listPublicoAlvo), ConteudoChaveNumerica.parseValueToString(listSegmento),
        		ConteudoChaveNumerica.parseValueToString(listFaixaEtaria), ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listDeposito),
        		ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), ConteudoChaveNumerica.parseValueToString(listPerProducao), ConteudoChaveNumerica.parseValueToString(listPerCarteira),
        		ConteudoChaveNumerica.parseValueToString(listNumInterno), bloqueado);
    			
    			
    	ConsultaPainelPlanejamentoListas result = new ConsultaPainelPlanejamentoListas(listaPainelPlanejamento, listaPainelDetalharEstoque, listaPainelDetalharCarteira, listaPainelDetalharOrdens, new ArrayList<>(), new ArrayList<>(),
    			new ArrayList<>(), new ArrayList<>());

        return result;
    }
    
    public ConsultaPainelPlanejamentoListas findMateriais(List<ConteudoChaveAlfaNum> listComplemento, List<ConteudoChaveNumerica> listContaEstoq, List<ConteudoChaveAlfaNum> listPerEmbarque,
    		List<ConteudoChaveNumerica> listDeposito, List<ConteudoChaveNumerica> listPerAReceber, List<ConteudoChaveNumerica> listPerReserva, List<ConteudoChaveNumerica> listOrdemProducao,
    		List<ConteudoChaveNumerica> listEstagio) {
    	
    	System.out.println("Entrou Service");
    	List<ConsultaPainelPlanejamento> listaMateriaisPlanejamento = painelPlanejamentoCustom.findMateriaisPlanejamento(ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listContaEstoq),
    			ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), ConteudoChaveNumerica.parseValueToString(listDeposito), ConteudoChaveNumerica.parseValueToString(listPerAReceber), 
        		ConteudoChaveNumerica.parseValueToString(listPerReserva), ConteudoChaveNumerica.parseValueToString(listOrdemProducao), 
        		ConteudoChaveNumerica.parseValueToString(listEstagio));
    	System.out.println("Entrou 2 Service");
    	List<ConsultaPainelPlanejamento> listaMateriaisDetalharEstoque = painelPlanejamentoCustom.findMateriaisDetalharEstoque(ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listContaEstoq),
    			ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), ConteudoChaveNumerica.parseValueToString(listDeposito), ConteudoChaveNumerica.parseValueToString(listPerAReceber), 
        		ConteudoChaveNumerica.parseValueToString(listPerReserva), ConteudoChaveNumerica.parseValueToString(listOrdemProducao), 
        		ConteudoChaveNumerica.parseValueToString(listEstagio));
    	System.out.println("Entrou 3 Service");
    	List<ConsultaPainelPlanejamento> listaMateriaisDetalharOrdens = painelPlanejamentoCustom.findMateriaisDetalharOrdens(ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listContaEstoq),
    			ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), ConteudoChaveNumerica.parseValueToString(listDeposito), ConteudoChaveNumerica.parseValueToString(listPerAReceber), 
        		ConteudoChaveNumerica.parseValueToString(listPerReserva), ConteudoChaveNumerica.parseValueToString(listOrdemProducao), 
        		ConteudoChaveNumerica.parseValueToString(listEstagio));
    	System.out.println("Entrou 4 Service");
    	List<ConsultaPainelPlanejamento> listaMateriaislDetalharOrdens = painelPlanejamentoCustom.findMateriaisDetalharCompras(ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listContaEstoq),
    			ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), ConteudoChaveNumerica.parseValueToString(listDeposito), ConteudoChaveNumerica.parseValueToString(listPerAReceber), 
        		ConteudoChaveNumerica.parseValueToString(listPerReserva), ConteudoChaveNumerica.parseValueToString(listOrdemProducao), 
        		ConteudoChaveNumerica.parseValueToString(listEstagio));
    	
    	
    	ConsultaPainelPlanejamentoListas result = new ConsultaPainelPlanejamentoListas(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 
    			listaMateriaisPlanejamento, listaMateriaisDetalharEstoque, listaMateriaisDetalharOrdens, listaMateriaislDetalharOrdens);
    	
    	return result;
    }
    
}
