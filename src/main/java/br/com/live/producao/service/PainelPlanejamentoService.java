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
    
    public List<ConteudoChaveAlfaNum> findAllReferencia(String referencia){
    	return painelPlanejamentoCustom.findAllReferencia(referencia);
    }
    
    public List<ConteudoChaveAlfaNum> findAllTamanho(){
    	return painelPlanejamentoCustom.findAllTamanho();
    }
    
    public List<ConteudoChaveAlfaNum> findAllCor(String cor){
    	return painelPlanejamentoCustom.findAllCor(cor);
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
    
    public List<ConteudoChaveNumerica> findAllPeriodoAReceber(int periodo){
    	return painelPlanejamentoCustom.findAllPeriodoAReceber(periodo);
    }
    
    public List<ConteudoChaveNumerica> findAllPeriodoReserva(int periodo){
    	return painelPlanejamentoCustom.findAllPeriodoReserva(periodo);
    }
    
    public List<ConteudoChaveNumerica> findAllPeriodosCarteira(){
    	return painelPlanejamentoCustom.findAllPeriodosCarteira();
    }
    
    public List<ConteudoChaveNumerica> findAllNumInterno(){
    	return painelPlanejamentoCustom.findAllNumInterno();
    }
    
    public int findPeriodoInicial(){
    	return painelPlanejamentoCustom.findPeriodoInicialAno();
    }
    
    public int findPeriodoAtual(){
    	return painelPlanejamentoCustom.findPeriodoAtual();
    }
    
    public int findCarteiraInicial(){
    	return painelPlanejamentoCustom.findCarteiraInicialAno();
    }
    
    public int findCarteiraAtual(){
    	return painelPlanejamentoCustom.findCarteiraAtual();
    }
    
    public List<ConteudoChaveNumerica> findAllEstagios(){
    	return painelPlanejamentoCustom.findAllEstagios();
    }
    
    public ConsultaPainelPlanejamentoListas findAcabados(List<ConteudoChaveAlfaNum> listReferencia, List<ConteudoChaveAlfaNum> listTamanho, List<ConteudoChaveAlfaNum> listCor,
    		List<ConteudoChaveNumerica> listColecao, List<ConteudoChaveNumerica> listSubColecao, List<ConteudoChaveNumerica> listLinhaProduto, List<ConteudoChaveNumerica> listArtigo, 
    		List<ConteudoChaveNumerica> listArtigoCota, List<ConteudoChaveNumerica> listContaEstoq, List<ConteudoChaveNumerica> listPublicoAlvo, List<ConteudoChaveNumerica> listSegmento, 
    		List<ConteudoChaveNumerica> listFaixaEtaria, List<ConteudoChaveAlfaNum> listComplemento, List<ConteudoChaveNumerica> listDeposito, List<ConteudoChaveAlfaNum> listPerEmbarque, 
    		String periodoProdInicio, String periodoProdFim, String periodoCartInicio, String periodoCartFim, List<ConteudoChaveNumerica> listNumInterno, int bloqueado) {
    	
    	List<ConsultaPainelPlanejamento> listaPainelPlanejamento = painelPlanejamentoCustom.findAcabadosPlanejamento(ConteudoChaveAlfaNum.parseValueToString(listReferencia), 
    			ConteudoChaveAlfaNum.parseValueToString(listTamanho), ConteudoChaveAlfaNum.parseValueToString(listCor), ConteudoChaveNumerica.parseValueToString(listColecao), 
    			ConteudoChaveNumerica.parseValueToString(listSubColecao), ConteudoChaveNumerica.parseValueToString(listLinhaProduto), ConteudoChaveNumerica.parseValueToString(listArtigo), 
    			ConteudoChaveNumerica.parseValueToString(listArtigoCota),	ConteudoChaveNumerica.parseValueToString(listContaEstoq), ConteudoChaveNumerica.parseValueToString(listPublicoAlvo),
    			ConteudoChaveNumerica.parseValueToString(listSegmento), ConteudoChaveNumerica.parseValueToString(listFaixaEtaria), ConteudoChaveAlfaNum.parseValueToString(listComplemento), 
    			ConteudoChaveNumerica.parseValueToString(listDeposito), ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), periodoProdInicio, periodoProdFim,
    			periodoCartInicio, periodoCartFim, ConteudoChaveNumerica.parseValueToString(listNumInterno), bloqueado);
    	
    	List<ConsultaPainelPlanejamento> listaPainelDetalharEstoque = painelPlanejamentoCustom.findAcabadosDetalharEstoque(ConteudoChaveAlfaNum.parseValueToString(listReferencia), 
    			ConteudoChaveAlfaNum.parseValueToString(listTamanho), ConteudoChaveAlfaNum.parseValueToString(listCor), ConteudoChaveNumerica.parseValueToString(listColecao), 
    			ConteudoChaveNumerica.parseValueToString(listSubColecao), ConteudoChaveNumerica.parseValueToString(listLinhaProduto), ConteudoChaveNumerica.parseValueToString(listArtigo), 
    			ConteudoChaveNumerica.parseValueToString(listArtigoCota),	ConteudoChaveNumerica.parseValueToString(listContaEstoq), ConteudoChaveNumerica.parseValueToString(listPublicoAlvo),
    			ConteudoChaveNumerica.parseValueToString(listSegmento), ConteudoChaveNumerica.parseValueToString(listFaixaEtaria), ConteudoChaveAlfaNum.parseValueToString(listComplemento), 
    			ConteudoChaveNumerica.parseValueToString(listDeposito), ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), periodoProdInicio, periodoProdFim,
    			periodoCartInicio, periodoCartFim, ConteudoChaveNumerica.parseValueToString(listNumInterno), bloqueado);
    	
    	List<ConsultaPainelPlanejamento> listaPainelDetalharCarteira = painelPlanejamentoCustom.findAcabadosDetalharCarteira(ConteudoChaveAlfaNum.parseValueToString(listReferencia), 
    			ConteudoChaveAlfaNum.parseValueToString(listTamanho), ConteudoChaveAlfaNum.parseValueToString(listCor), ConteudoChaveNumerica.parseValueToString(listColecao), 
    			ConteudoChaveNumerica.parseValueToString(listSubColecao), ConteudoChaveNumerica.parseValueToString(listLinhaProduto), ConteudoChaveNumerica.parseValueToString(listArtigo), 
    			ConteudoChaveNumerica.parseValueToString(listArtigoCota),	ConteudoChaveNumerica.parseValueToString(listContaEstoq), ConteudoChaveNumerica.parseValueToString(listPublicoAlvo),
    			ConteudoChaveNumerica.parseValueToString(listSegmento), ConteudoChaveNumerica.parseValueToString(listFaixaEtaria), ConteudoChaveAlfaNum.parseValueToString(listComplemento), 
    			ConteudoChaveNumerica.parseValueToString(listDeposito), ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), periodoProdInicio, periodoProdFim,
    			periodoCartInicio, periodoCartFim, ConteudoChaveNumerica.parseValueToString(listNumInterno), bloqueado);
    	
    	List<ConsultaPainelPlanejamento> listaPainelDetalharOrdens = painelPlanejamentoCustom.findAcabadosDetalharOrdens(ConteudoChaveAlfaNum.parseValueToString(listReferencia), 
    			ConteudoChaveAlfaNum.parseValueToString(listTamanho), ConteudoChaveAlfaNum.parseValueToString(listCor), ConteudoChaveNumerica.parseValueToString(listColecao), 
    			ConteudoChaveNumerica.parseValueToString(listSubColecao), ConteudoChaveNumerica.parseValueToString(listLinhaProduto), ConteudoChaveNumerica.parseValueToString(listArtigo), 
    			ConteudoChaveNumerica.parseValueToString(listArtigoCota),	ConteudoChaveNumerica.parseValueToString(listContaEstoq), ConteudoChaveNumerica.parseValueToString(listPublicoAlvo),
    			ConteudoChaveNumerica.parseValueToString(listSegmento), ConteudoChaveNumerica.parseValueToString(listFaixaEtaria), ConteudoChaveAlfaNum.parseValueToString(listComplemento), 
    			ConteudoChaveNumerica.parseValueToString(listDeposito), ConteudoChaveAlfaNum.parseValueToString(listPerEmbarque), periodoProdInicio, periodoProdFim,
    			periodoCartInicio, periodoCartFim, ConteudoChaveNumerica.parseValueToString(listNumInterno), bloqueado);
    			
    			
    	ConsultaPainelPlanejamentoListas result = new ConsultaPainelPlanejamentoListas(listaPainelPlanejamento, listaPainelDetalharEstoque, listaPainelDetalharCarteira, listaPainelDetalharOrdens, new ArrayList<>(), new ArrayList<>(),
    			new ArrayList<>(), new ArrayList<>());

        return result;
    }
    
    public ConsultaPainelPlanejamentoListas findMateriais(List<ConteudoChaveAlfaNum> listNivel, List<ConteudoChaveAlfaNum> listReferencia, List<ConteudoChaveAlfaNum> listTamanho,
    		List<ConteudoChaveAlfaNum> listCor,	List<ConteudoChaveAlfaNum> listComplemento, List<ConteudoChaveNumerica> listContaEstoq,
    		List<ConteudoChaveNumerica> listDeposito, String periodoAReceberInicio, String periodoAReceberFim, String periodoReservaInicio, String periodoReservaFim, 
    		List<ConteudoChaveNumerica> listOrdemProducao, List<ConteudoChaveNumerica> listEstagio) {
    	
    	List<ConsultaPainelPlanejamento> listaMateriaisPlanejamento = painelPlanejamentoCustom.findMateriaisPlanejamento(ConteudoChaveAlfaNum.parseValueToString(listNivel),
    			ConteudoChaveAlfaNum.parseValueToString(listReferencia), ConteudoChaveAlfaNum.parseValueToString(listTamanho), ConteudoChaveAlfaNum.parseValueToString(listCor), 
    			ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listContaEstoq), 
    			ConteudoChaveNumerica.parseValueToString(listDeposito), periodoAReceberInicio, periodoAReceberFim, periodoReservaInicio, periodoReservaFim, 
    			ConteudoChaveNumerica.parseValueToString(listOrdemProducao), ConteudoChaveNumerica.parseValueToString(listEstagio));
    	
    	List<ConsultaPainelPlanejamento> listaMateriaisDetalharEstoque = painelPlanejamentoCustom.findMateriaisDetalharEstoque(ConteudoChaveAlfaNum.parseValueToString(listNivel),
    			ConteudoChaveAlfaNum.parseValueToString(listReferencia), ConteudoChaveAlfaNum.parseValueToString(listTamanho), ConteudoChaveAlfaNum.parseValueToString(listCor), 
    			ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listContaEstoq), 
    			ConteudoChaveNumerica.parseValueToString(listDeposito), periodoAReceberInicio, periodoAReceberFim, periodoReservaInicio, periodoReservaFim, 
    			ConteudoChaveNumerica.parseValueToString(listOrdemProducao), ConteudoChaveNumerica.parseValueToString(listEstagio));
    	
    	List<ConsultaPainelPlanejamento> listaMateriaisDetalharOrdens = painelPlanejamentoCustom.findMateriaisDetalharOrdens(ConteudoChaveAlfaNum.parseValueToString(listNivel),
    			ConteudoChaveAlfaNum.parseValueToString(listReferencia), ConteudoChaveAlfaNum.parseValueToString(listTamanho), ConteudoChaveAlfaNum.parseValueToString(listCor), 
    			ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listContaEstoq), 
    			ConteudoChaveNumerica.parseValueToString(listDeposito), periodoAReceberInicio, periodoAReceberFim, periodoReservaInicio, periodoReservaFim, 
    			ConteudoChaveNumerica.parseValueToString(listOrdemProducao), ConteudoChaveNumerica.parseValueToString(listEstagio));
    	
    	List<ConsultaPainelPlanejamento> listaMateriaislDetalharOrdens = painelPlanejamentoCustom.findMateriaisDetalharCompras(ConteudoChaveAlfaNum.parseValueToString(listNivel),
    			ConteudoChaveAlfaNum.parseValueToString(listReferencia), ConteudoChaveAlfaNum.parseValueToString(listTamanho), ConteudoChaveAlfaNum.parseValueToString(listCor), 
    			ConteudoChaveAlfaNum.parseValueToString(listComplemento), ConteudoChaveNumerica.parseValueToString(listContaEstoq), ConteudoChaveNumerica.parseValueToString(listDeposito),
    			ConteudoChaveNumerica.parseValueToString(listOrdemProducao), ConteudoChaveNumerica.parseValueToString(listEstagio));
    	
    	
    	ConsultaPainelPlanejamentoListas result = new ConsultaPainelPlanejamentoListas(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 
    			listaMateriaisPlanejamento, listaMateriaisDetalharEstoque, listaMateriaisDetalharOrdens, listaMateriaislDetalharOrdens);
    	
    	return result;
    }
    
}
