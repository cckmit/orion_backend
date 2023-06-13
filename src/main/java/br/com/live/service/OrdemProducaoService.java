package br.com.live.service;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.live.custom.OrdemProducaoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.Usuario;
import br.com.live.model.ConsultaDadosRoteiro;
import br.com.live.model.DadosGeracaoOrdemProducao;
import br.com.live.model.DadosGeracaoOrdemProducaoItem;
import br.com.live.model.DadosTagChina;
import br.com.live.model.EstagioProducao;
import br.com.live.model.NecessidadeTecidos;
import br.com.live.model.OrdemConfeccao;
import br.com.live.model.OrdemProducao;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.StatusGravacao;

@Service
@Transactional
public class OrdemProducaoService {
	
	private final OrdemProducaoCustom ordemProducaoCustom;
	private final ProdutoCustom produtoCustom;
	private final UsuarioService usuarioService;
	
	public OrdemProducaoService(OrdemProducaoCustom ordemProducaoCustom, ProdutoCustom produtoCustom, UsuarioService usuarioService) {
		super();
		this.ordemProducaoCustom = ordemProducaoCustom;
		this.produtoCustom = produtoCustom;
		this.usuarioService = usuarioService;
	}

	public List<EstagioProducao> findAllEstagios() {
		return ordemProducaoCustom.findAllEstagios();
	}

	public List<EstagioProducao> findAllEstagiosDecoracaoOrdemProducao(int ordemProducao) {
		return ordemProducaoCustom.findAllEstagiosDecoracaoOrdemProducao(ordemProducao);
	}
	
	public List<OrdemProducao> findAllTagsExportacaoChina() {
		return ordemProducaoCustom.findAllTagsExportacaoChina();
	}
	
	public List<OrdemConfeccao> findAllPacotes(int ordemProducao) {
		return ordemProducaoCustom.findAllOrdensConfeccao(ordemProducao);
	}
	
	public List<DadosTagChina> findDadosTag(List<ConteudoChaveNumerica> ordemProducao) {
		return ordemProducaoCustom.findDadosTagChina(ConteudoChaveNumerica.parseValueToString(ordemProducao));
	}
	
	public void baixarEstagioProducao(int ordemProducao, int estagio, long idUsuarioOrion) {		
		Usuario usuario = usuarioService.findByIdUsuario(idUsuarioOrion);
		int codUsuarioSystextil = usuarioService.findCodigoUsuarioSystextil(idUsuarioOrion);		
		List<OrdemConfeccao> pacotes = ordemProducaoCustom.findAllOrdensConfeccao(ordemProducao);		
		for (OrdemConfeccao pacote : pacotes) {
			ordemProducaoCustom.gravarProducaoEstagio(pacote.ordemProducao, pacote.periodo, pacote.ordemConfeccao, estagio, pacote.qtdePecas, codUsuarioSystextil, usuario.usuarioSystextil);
		}
	}
	
	public void gravarSeqPrioridadeDia(int ordemProducao, boolean urgente) {
		int sequencia = 0; 
		if (!urgente) { 
			sequencia = ordemProducaoCustom.findUltimaSeqPrioridadeDia();
			sequencia++;
		}		  		
		ordemProducaoCustom.gravarSeqPrioridadeDia(ordemProducao, sequencia);
	}
	
	public int findQtdePecasApontadaNoDiaPorEstagioUsuario(int codEstagio) {		
		return ordemProducaoCustom.findQtdePecasApontadaNoDia(codEstagio);
	}	

	public int findQtdePecasApontadaNoDiaPorEstagioArtigos(int codEstagio, boolean consideraArtigos, String artigos, Date dataInicial, Date dataFinal) {		
		return ordemProducaoCustom.findQtdePecasApontadaNoDiaPorArtigo(codEstagio, consideraArtigos, artigos, dataInicial, dataFinal);
	}	

	public double findQtdeMinutosApontadoNoDiaPorEstagioArtigos(int codEstagio, boolean consideraArtigos, String artigos, Date dataInicial, Date dataFinal) {		
		return ordemProducaoCustom.findQtdeMinutosApontadoNoDiaPorArtigo(codEstagio, consideraArtigos, artigos, dataInicial, dataFinal);
	}	
	
	public int findQtdePecasFlatApontadaNoDia(int codEstagio, Date dataInicial, Date dataFinal) {
		return ordemProducaoCustom.findQtdePecasFlatApontadaNoDia(codEstagio, dataInicial, dataFinal);
	}

	public double findQtdeMinutosFlatApontadaNoDia(int codEstagio, Date dataInicial, Date dataFinal) {
		return ordemProducaoCustom.findQtdeMinutosFlatApontadaNoDia(codEstagio, dataInicial, dataFinal);
	}

	public void gravarObservacao(int ordemProducao, String observacao) {
		ordemProducaoCustom.gravarObservacao(ordemProducao, observacao);
	}
	
	public List<ConteudoChaveAlfaNum> findAllOrdensAsync(int estagio, String searchVar) {
		return ordemProducaoCustom.findOrdensForAsync(estagio, searchVar);
	}
	
	public List<ConteudoChaveAlfaNum> findAllOrdensAsyncComEstagio(List<ConteudoChaveAlfaNum> estagio, String searchVar) {
		return ordemProducaoCustom.findOrdensForAsyncComEstagio(estagio, searchVar);
	}

	public List<ConteudoChaveAlfaNum> findOrdensForAsyncEstagioDecoracao(String searchVar) {
		return ordemProducaoCustom.findOrdensForAsyncEstagioDecoracao(searchVar);
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
	
	private int gravarCapa(String referencia, int periodo, int alternativa, int roteiro, int quantidade, String observacao1, String observacao2, int pedido, int colecao) {
		int id = ordemProducaoCustom.findNextIdOrdem();
		ordemProducaoCustom.gravarCapa(id, referencia, periodo, alternativa, roteiro, quantidade, observacao1, observacao2, pedido, colecao);		
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
			idOrdemProducao = gravarCapa(ordem.getReferencia(), ordem.getPeriodo(), ordem.getAlternativa(), ordem.getRoteiro(), ordem.getQuantidade(), ordem.getObservacao1(), ordem.getObservacao2(), ordem.getPedido(), ordem.getColecaoPlanoMestre());
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