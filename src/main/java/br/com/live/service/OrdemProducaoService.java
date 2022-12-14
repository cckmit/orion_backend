package br.com.live.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.model.ConsultaDadosRoteiro;
import br.com.live.model.DadosGeracaoOrdemProducao;
import br.com.live.model.DadosGeracaoOrdemProducaoItem;
import br.com.live.model.NecessidadeTecidos;
import br.com.live.util.StatusGravacao;

@Service
@Transactional
public class OrdemProducaoService {
	
	private final OrdemProducaoCustom ordemProducaoCustom;
	private final ProdutoCustom produtoCustom;
	
	public OrdemProducaoService(OrdemProducaoCustom ordemProducaoCustom, ProdutoCustom produtoCustom) {
		super();
		this.ordemProducaoCustom = ordemProducaoCustom;
		this.produtoCustom = produtoCustom;
	}

	private String validarDados(DadosGeracaoOrdemProducao ordem) {
		boolean existeEstrutura = true;
		boolean existeRoteiro = true;
		boolean roteiroSequenciado = true;

		if (ordem.getPeriodo() == 0) return "Período de produção não informado!";
		if (produtoCustom.isProdutoComprado(ordem.getReferencia())) return "Referência é um produto comprado!";
		for (DadosGeracaoOrdemProducaoItem item : ordem.getGradeTamanhosCores()) {
			existeEstrutura = produtoCustom.existsEstrutura(ordem.getReferencia(), item.getTamanho(), item.getCor(), ordem.getAlternativa());
			existeRoteiro = produtoCustom.existsRoteiro("1", ordem.getReferencia(), item.getTamanho(), item.getCor(), ordem.getAlternativa(), ordem.getRoteiro());
			roteiroSequenciado = produtoCustom.roteiroSequenciado(ordem.getReferencia(), item.getTamanho(), item.getCor(), ordem.getAlternativa(), ordem.getRoteiro());
			if ((!existeEstrutura)||(!existeRoteiro)||(roteiroSequenciado)) break;
		}		
		if (!existeEstrutura) return "Não existe estrutura para a alternativa!";			
		if (!existeRoteiro) return "Não existe roteiro para a alternativa e roteiro!";		
		if (!roteiroSequenciado) return "Roteiro do produto não está sequenciado!";						
		return "";
	}
	
	private void gravarDadosItem(int idOrdem, String tamanho, String cor, int quantidade) {
		int ordemTamanho = produtoCustom.findOrdemTamanho(tamanho);
		ordemProducaoCustom.gravarTamanhoCor(idOrdem, tamanho, cor, quantidade, ordemTamanho);
	}
	
	private void gravarPacotesConfeccao(int idOrdemProducao, int periodo, String referencia, String tamanho, String cor, int alternativa, int roteiro, int quantidade) {		
		int idPacote;
		int qtdeLote;
		int estagioAnterior;
		int salvaEstagio;
		int salvaSequencia;
		int ultimoEstagio = 0;
		int qtdeTotalProgItem = quantidade;
		int loteFabricacao = produtoCustom.findLoteFabricacao(referencia, tamanho);
		
		List<ConsultaDadosRoteiro> listaDadosRoteiro = produtoCustom.findDadosRoteiro(referencia, tamanho, cor, alternativa, roteiro) ; 
		
		while (qtdeTotalProgItem > 0) {
			
			idPacote = ordemProducaoCustom.findNextIdPacote(periodo);
			
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
					
					ordemProducaoCustom.gravarPacoteConfeccao(periodo, idPacote, dadosRoteiro.estagio, idOrdemProducao, referencia, tamanho, cor, qtdeLote, 
							estagioAnterior, dadosRoteiro.familia, dadosRoteiro.seqOperacao, dadosRoteiro.estagioDepende, dadosRoteiro.seqEstagio); 
					 
					salvaSequencia = dadosRoteiro.seqEstagio;
					salvaEstagio = dadosRoteiro.estagio;
				}
			}
			
			ultimoEstagio = salvaEstagio;
		}		
		ordemProducaoCustom.atualizarUltimoEstagioOrdem(idOrdemProducao, ultimoEstagio);
	}
	
	private void gravarDadosTecidos(int idOrdemProducao, String referencia, String tamanho, String cor, int alternativa, int roteiro, int quantidade) {
		
		List<NecessidadeTecidos> tecidos = produtoCustom.calcularNecessidadeTecido(referencia, tamanho, cor, alternativa, quantidade);

		for (NecessidadeTecidos tecido : tecidos) {
			ordemProducaoCustom.gravarCapaEnfesto(idOrdemProducao, referencia, tecido.getSequencia(), alternativa, roteiro);
			ordemProducaoCustom.gravarTecidosEnfesto(idOrdemProducao, cor, tecido.getNivel(), tecido.getGrupo(), tecido.getSub(), tecido.getItem(), tecido.getSequencia(), tecido.getQtdeKg(), tecido.getQtdeMetros());			
		}		
	}
	
	private int gravarCapa(String referencia, int periodo, int alternativa, int roteiro, int quantidade, String observacao1, String observacao2) {
		int id = ordemProducaoCustom.findNextIdOrdem();
		ordemProducaoCustom.gravarCapa(id, referencia, periodo, alternativa, roteiro, quantidade, observacao1, observacao2);		
		return id;
	}
	
	private void gravarItem(int idOrdem, int periodo, String referencia, String tamanho, String cor, int alternativa, int roteiro, int quantidade) {				
		gravarDadosItem(idOrdem, tamanho, cor, quantidade);
		gravarPacotesConfeccao(idOrdem, periodo, referencia, tamanho, cor, alternativa, roteiro, quantidade);
		gravarDadosTecidos(idOrdem, referencia, tamanho, cor, alternativa, roteiro, quantidade);		
	}
	
	public StatusGravacao gerarOrdemProducao(DadosGeracaoOrdemProducao ordem) {
		String retornoValidacao = validarDados(ordem);		
		if (!retornoValidacao.isEmpty()) return new StatusGravacao(false, retornoValidacao);
		
		StatusGravacao status = null; 
		int idOrdemProducao = 0;
		
		try {														
			idOrdemProducao = gravarCapa(ordem.getReferencia(), ordem.getPeriodo(), ordem.getAlternativa(), ordem.getRoteiro(), ordem.getQuantidade(), ordem.getObservacao1(), ordem.getObservacao2());
			for (DadosGeracaoOrdemProducaoItem item : ordem.getGradeTamanhosCores()) {
				gravarItem(idOrdemProducao, ordem.getPeriodo(), ordem.getReferencia(), item.getTamanho(), item.getCor(), ordem.getAlternativa(), ordem.getRoteiro(), item.getQuantidade());
			}
			status = new StatusGravacao(true, "Ordem gerada com sucesso!", idOrdemProducao);
		} catch (Exception e) {				
			status = new StatusGravacao(false, "Não foi possível gerar a ordem de produção!", e.getMessage());			
			if (idOrdemProducao > 0) ordemProducaoCustom.excluirOrdemProducao(idOrdemProducao);				
		}						
		return status;		
	}	
}