package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.PlanoMestrePreOrdem;
import br.com.live.entity.PlanoMestrePreOrdemItem;
import br.com.live.model.ConsultaDadosCompEstrutura;
import br.com.live.model.ConsultaDadosEstrutura;
import br.com.live.model.ConsultaDadosFilete;
import br.com.live.model.ConsultaDadosRoteiro;
import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.PreOrdemProducao;
import br.com.live.repository.PlanoMestrePreOrdemItemRepository;
import br.com.live.repository.PlanoMestrePreOrdemRepository;

@Service
@Transactional
public class OrdemProducaoService {
	 
	private final PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository;
	private final PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository;	 
	private final OrdemProducaoCustom ordemProducaoCustom;
	private final ProdutoCustom produtoCustom;

	public OrdemProducaoService(PlanoMestrePreOrdemRepository planoMestrePreOrdemRepository, PlanoMestrePreOrdemItemRepository planoMestrePreOrdemItemRepository, OrdemProducaoCustom ordemProducaoCustom, ProdutoCustom produtoCustom) {
		this.planoMestrePreOrdemRepository = planoMestrePreOrdemRepository;
		this.planoMestrePreOrdemItemRepository = planoMestrePreOrdemItemRepository;		
		this.ordemProducaoCustom = ordemProducaoCustom;
		this.produtoCustom = produtoCustom;
	}	
	
	private void gravarStatusPreOrdem(PlanoMestrePreOrdem preOrdem, String erro) {		
		// CRIAR CAMPO PARA GRAVAR O STATUS DA GERAÇÃO
		System.out.println("gravarStatusPreOrdem: " + erro);
	}
	
	// TODO - TRATAR ERRO... RETORNAR FALSE
	private int gravarCapa(PlanoMestrePreOrdem preOrdem) {
		int idOrdemProducao = ordemProducaoCustom.findNextIdOrdem();
		ordemProducaoCustom.gravarCapa(idOrdemProducao, preOrdem.grupo, preOrdem.periodo, preOrdem.alternativa, preOrdem.roteiro, preOrdem.quantidade, preOrdem.observacao);
		return idOrdemProducao;
	}
	
	private void gravarTamanhoCor(int idOrdemProducao, PlanoMestrePreOrdemItem preOrdemItem) {
		int ordemTamanho = produtoCustom.findOrdemTamanho(preOrdemItem.sub);
		ordemProducaoCustom.gravarTamanhoCor(idOrdemProducao, preOrdemItem.sub, preOrdemItem.item, preOrdemItem.quantidade, ordemTamanho);
	}
	
	private void gravarMarcacaoTamanho(int idOrdemProducao, PlanoMestrePreOrdem preOrdem, PlanoMestrePreOrdemItem preOrdemItem) {		
		int riscoPadrao = produtoCustom.findRiscoPadraoByCodigo(preOrdem.grupo);
		int seqRisco = produtoCustom.findMenorSeqRisco(preOrdem.grupo, preOrdem.alternativa);
		int ordemTamanho = produtoCustom.findOrdemTamanho(preOrdemItem.sub);		
		int qtdeMarcacoesTamanho = produtoCustom.findQtdeMarcacoesTamanho(preOrdem.grupo, preOrdemItem.sub, riscoPadrao, seqRisco, preOrdem.alternativa);				
		ordemProducaoCustom.gravarMarcacaoTamanho(idOrdemProducao, preOrdemItem.sub, qtdeMarcacoesTamanho, ordemTamanho);		
	}	
	
	// TODO - TRATAR ERRO... RETORNAR FALSE
	private void gravarPacotesConfeccao(int idOrdemProducao, PlanoMestrePreOrdem preOrdem, PlanoMestrePreOrdemItem preOrdemItem) {		
		int idPacote;
		int qtdeLote;
		int estagioAnterior;
		int salvaEstagio;
		int salvaSequencia;
		int ultimoEstagio = 0;
		int qtdeTotalProgItem = preOrdemItem.quantidade;
		int loteFabricacao = produtoCustom.findLoteFabricacao(preOrdem.grupo, preOrdemItem.sub);
		
		List<ConsultaDadosRoteiro> listaDadosRoteiro = produtoCustom.findDadosRoteiro(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, preOrdem.alternativa, preOrdem.roteiro) ; 
		
		while (qtdeTotalProgItem > 0) {
			
			idPacote = ordemProducaoCustom.findNextIdPacote(preOrdem.periodo);
			
			if (qtdeTotalProgItem > loteFabricacao)
				qtdeLote = loteFabricacao;
			else qtdeLote = qtdeTotalProgItem;
			
			qtdeTotalProgItem -= loteFabricacao;
			
			estagioAnterior = 0;
			salvaEstagio = 0;
			salvaSequencia = 0;
			ultimoEstagio = 0;
			
			for (ConsultaDadosRoteiro dadosRoteiro : listaDadosRoteiro) {
				
				if ((salvaEstagio != dadosRoteiro.estagio)&&(dadosRoteiro.pedeProduto != 1)&&(dadosRoteiro.tipoOperCmc == 0)) {

					if (dadosRoteiro.seqEstagio != salvaSequencia)
						estagioAnterior = salvaEstagio;
					
					if (dadosRoteiro.seqEstagio == 0)
						estagioAnterior = salvaEstagio;
					
					ordemProducaoCustom.gravarPacoteConfeccao(preOrdem.periodo, idPacote, dadosRoteiro.estagio, idOrdemProducao, preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, qtdeLote, 
							estagioAnterior, dadosRoteiro.familia, dadosRoteiro.seqOperacao, dadosRoteiro.estagioDepende, dadosRoteiro.seqEstagio); 
					
					salvaSequencia = dadosRoteiro.seqEstagio;
					salvaEstagio = dadosRoteiro.estagio;
				}
			}
			
			ultimoEstagio = salvaEstagio;
		}
		
		ordemProducaoCustom.atualizarUltimoEstagioOrdem(idOrdemProducao, ultimoEstagio);
	}
	
	private void gravarDadosTecidos(int idOrdemProducao, PlanoMestrePreOrdem preOrdem, PlanoMestrePreOrdemItem preOrdemItem) {
		
		String subTecido = "";
		String itemTecido = "";
		double consumoTecido = 0.0;		
		double qtdeKgProg = 0.0;
		double metrosTecido = 0.0;
		double larguraRisco = 0.0;
		double larguraFilete = 0.0;
		double metrosOrdem = 0.0;
		double qtdeTotMetrosTecido = 0.0;
		double tirasLargura = 0.0;
		double qtdePerdas = 0.0;
		int qtdeMarcacoesTamanho = 0;
		int riscoPadrao = produtoCustom.findRiscoPadraoByCodigo(preOrdem.grupo);
		
		ConsultaDadosCompEstrutura dadosComponente;
		ConsultaDadosFilete dadosFileteEstrutura;
		ConsultaDadosFilete dadosFileteRisco;
		ConsultaDadosFilete dadosFileteTecido;
		
		List<ConsultaDadosEstrutura> listaDadosEstrutura = produtoCustom.findDadosEstrutura(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, preOrdem.alternativa);
				
		for (ConsultaDadosEstrutura dadosEstrutura : listaDadosEstrutura) {
			subTecido = dadosEstrutura.subComp;
			itemTecido = dadosEstrutura.itemComp;
			consumoTecido = dadosEstrutura.consumo;			
			
			if ((subTecido.equalsIgnoreCase("000"))||(consumoTecido == 0.000000)) {
				dadosComponente = produtoCustom.findDadosComponenteEstrutura(preOrdem.grupo, preOrdemItem.sub, dadosEstrutura.itemItem, dadosEstrutura.sequencia, preOrdem.alternativa);
				subTecido = dadosComponente.sub;
				consumoTecido = dadosComponente.consumo; 
			}
			
			if (itemTecido.equalsIgnoreCase("000000")) {
				dadosComponente = produtoCustom.findDadosComponenteEstrutura(preOrdem.grupo, dadosEstrutura.subItem, preOrdemItem.item, dadosEstrutura.sequencia, preOrdem.alternativa);	
				itemTecido = dadosComponente.item;				
			}
		
			qtdeMarcacoesTamanho = produtoCustom.findQtdeMarcacoesTamanho(preOrdem.grupo, preOrdemItem.sub, riscoPadrao, dadosEstrutura.sequencia, preOrdem.alternativa);
			
			ordemProducaoCustom.gravarCapaEnfesto(idOrdemProducao, preOrdem.grupo, dadosEstrutura.sequencia, preOrdem.alternativa, preOrdem.roteiro);
			ordemProducaoCustom.gravarMarcacoesEnfesto(idOrdemProducao, preOrdemItem.sub, dadosEstrutura.sequencia, qtdeMarcacoesTamanho);			
			
			qtdeKgProg = (consumoTecido * (float) preOrdemItem.quantidade);
			metrosTecido = 0.0; 
			qtdeTotMetrosTecido = 0.0;
			
			dadosFileteEstrutura = produtoCustom.findDadosFileteEstrutura(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, dadosEstrutura.sequencia, preOrdem.alternativa);
			
			if (dadosFileteEstrutura.tipoCorte == 2) {				
				dadosFileteRisco = produtoCustom.findDadosFileteRisco(preOrdem.grupo, riscoPadrao, dadosEstrutura.sequencia, preOrdem.alternativa);
				
				larguraFilete = dadosFileteEstrutura.larguraFilete; 
				larguraRisco = dadosFileteRisco.larguraRisco;
				
				if (larguraRisco == 0.000) {				
					dadosFileteTecido = produtoCustom.findDadosFileteTecidos(preOrdem.grupo, preOrdemItem.sub);
					
					if (dadosFileteTecido.tubularAberto == 2) larguraRisco = dadosFileteTecido.larguraTecido * 2;
					
					if (larguraRisco == 0.000) larguraRisco = 1.000;
					if (larguraFilete == 0.000) larguraFilete = 1.000;
					
					metrosOrdem = ((double) preOrdemItem.quantidade * dadosFileteEstrutura.comprimentoFilete);					
					
					tirasLargura = larguraRisco / larguraFilete;
					
					if (tirasLargura == 0.000) tirasLargura = 1.000; 
					
					metrosTecido = metrosOrdem / tirasLargura; 
					
					if (dadosEstrutura.percPerdas > 0.000) {						
						qtdePerdas = (dadosEstrutura.percPerdas * metrosTecido) / 100;
						metrosTecido += qtdePerdas;  
					}
					
					qtdeTotMetrosTecido += metrosTecido;
		            
				}				
			}
		}
	}
	
	// TODO - TRATAR ERRO... RETORNAR FALSE
	private void gravarDadosItem(int idOrdemProducao, PlanoMestrePreOrdem preOrdem, PlanoMestrePreOrdemItem preOrdemItem) {
		boolean existeEstrutura = produtoCustom.existsEstrutura(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, preOrdem.alternativa);
		boolean existeRoteiro = produtoCustom.existsRoteiro(preOrdem.grupo, preOrdemItem.sub, preOrdemItem.item, preOrdem.alternativa, preOrdem.roteiro);
		
		gravarTamanhoCor(idOrdemProducao, preOrdemItem);
		gravarMarcacaoTamanho(idOrdemProducao, preOrdem, preOrdemItem);
		gravarPacotesConfeccao(idOrdemProducao, preOrdem, preOrdemItem);			
		gravarDadosTecidos(idOrdemProducao, preOrdem, preOrdemItem);		
	}
	
	public void gerarOrdens(List<ConsultaPreOrdemProducao> preOrdens) {
		
		int idOrdemProducao;
		String erro = ""; // TODO - Melhorar a validação de erro - usar uma variavel fora do metodo
		
		for (ConsultaPreOrdemProducao consultaPreOrdem : preOrdens) {			
			System.out.println("Pré ordens: " + consultaPreOrdem.id + " - " + consultaPreOrdem.referencia);
			
			PlanoMestrePreOrdem preOrdem = planoMestrePreOrdemRepository.findById(consultaPreOrdem.id);
			List<PlanoMestrePreOrdemItem> preOrdemItens = planoMestrePreOrdemItemRepository.findByIdOrdem(consultaPreOrdem.id);
			
			
			
			if (produtoCustom.isProdutoComprado(preOrdem.grupo)) 
				erro = "Referência é um produto comprado!";
			
			if (!erro.equalsIgnoreCase(""))
				gravarStatusPreOrdem(preOrdem, erro);
			
			if (!erro.equalsIgnoreCase("")) continue;
			
			
			
			
			idOrdemProducao = gravarCapa(preOrdem);
			
			for (PlanoMestrePreOrdemItem preOrdemItem : preOrdemItens) {
				//gravarDadosItem(idOrdemProducao, preOrdemItem);
			}
			
			
			
			
			
			// Validar se a referencia é comprada - OK			
			// Gravar capa da ordem (pcpc_020) - OK			
			// Gravar tamanhos (pcpc_021) - OK
			// Validar alternativa / roteiro
			
			
			// Gravar pacotes enfestos (pcpc_040)
			
			// Gravar tamanhos peças (pcpc_025)

			// Gravar enfestos de tecidos (pcpc_030)
			
			// Gravar camadas (pcpc_032)
			
			
			
			
		}		
	}
	
}
