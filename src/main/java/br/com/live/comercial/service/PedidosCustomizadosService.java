package br.com.live.comercial.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.comercial.entity.PedidoCustomizado;
import br.com.live.comercial.model.ConsultaPedidoCustomizado;
import br.com.live.comercial.repository.PedidoCustomizadoRepository;
import br.com.live.producao.custom.ConfeccaoCustom;
import br.com.live.producao.model.DadosGeracaoOrdemProducao;
import br.com.live.producao.service.OrdemProducaoService;
import br.com.live.util.StatusGravacao;
import br.com.live.util.service.EmailService;

@Service
@Transactional
public class PedidosCustomizadosService {
	
	private final EmailService email;
	private final PedidoCustomizadoRepository pedidoCustomizadoRepository;
	private final OrdemProducaoService ordemProducaoService;
	private final ConfeccaoCustom confeccaoCustom;

	public PedidosCustomizadosService(EmailService email, PedidoCustomizadoRepository pedidoCustomizadoRepository, OrdemProducaoService ordemProducaoService, ConfeccaoCustom confeccaoCustom) {
		
		this.email = email;
		this.pedidoCustomizadoRepository = pedidoCustomizadoRepository; 
		this.ordemProducaoService = ordemProducaoService;
		this.confeccaoCustom = confeccaoCustom;
	}
	
	public void enviarEmail(long id) {
		
		List<PedidoCustomizado> prodCustom = pedidoCustomizadoRepository.findByIdPedidosCustom(id);
		
		int pedido = 0;
		String referencia = "";
		String tamanho = "";
		String cor = "";
		int solicitacao = 0;
		int numOrdem = 0;
		
		for (PedidoCustomizado pedCustom : prodCustom) {
			
			pedido = pedCustom.pedidoVenda;
			referencia = pedCustom.cdItPeGrupo;
			tamanho = pedCustom.cdItPeSubgrupo;
			cor = pedCustom.cdItPeItem;
		    solicitacao = pedCustom.solicitacao;
		    numOrdem = pedCustom.ordemProducao;
		}		
		String assunto = "Pedido Personalizado ";
		String corpoEmail = " Dados do Pedido: <br /> "
				+ " Pedido:       " + pedido + " <br /> "
				+ " Referência:   " + referencia + " <br /> "
				+ " Tamanho:      " + tamanho + " <br /> "
				+ " Cor:          " + cor + " <br /> "
				+ " Solicitação:  " + solicitacao + " <br /> "
				+ " Número Ordem: " + numOrdem + " <br /> "
				+ " Imagem: " ;
				
		email.enviar(assunto, corpoEmail, "marcos.ti@liveoficial.com.br");
	}
	
	public void gerarOrdem(int solicitacao, int periodoProducao, int alternativa, int roteiro) {
		
		List<PedidoCustomizado> preOrdem =  pedidoCustomizadoRepository.findBySolicitacao(solicitacao);
		
		for (PedidoCustomizado item : preOrdem) {
			try {
				if (item.ordemProducao == 0) {
					DadosGeracaoOrdemProducao dadosOrdem = new DadosGeracaoOrdemProducao(item.cdItPeGrupo, periodoProducao, alternativa, roteiro, "", "", item.pedidoVenda);
					dadosOrdem.addItem(item.cdItPeSubgrupo, item.cdItPeItem, item.qtdePedida);
					StatusGravacao status = ordemProducaoService.gerarOrdemProducao(dadosOrdem);
					if (!status.isConcluido()) throw new Exception(status.getMensagemCompleta());	
					int idOrdem = (Integer) status.getObjetoRetorno();
					item.ordemProducao = idOrdem;
					pedidoCustomizadoRepository.save(item);
				}		
			} catch (Exception e) {	
				System.out.println(e);						
			}				 
		}
	}
	public void loadPedidosPersonalizados() {
		
		List<ConsultaPedidoCustomizado> pedidos = confeccaoCustom.findPedidosCustomizado();	
		List<PedidoCustomizado> carregado = pedidoCustomizadoRepository.findAllByDate();
		List<PedidoCustomizado> solicitacaAtual = confeccaoCustom.findSolicitacaoMaxDia();
		
		long id = 0;
		int numSolicitacao = 0;
		id = pedidoCustomizadoRepository.findNextId();
		if (carregado.size() == 0) {
			numSolicitacao = pedidoCustomizadoRepository.findNextSolicit();
		} else {
			System.out.println("Entrou no IF 2° Condição");
			for (PedidoCustomizado solicit : solicitacaAtual) {
				System.out.println("Entrou no For");
				numSolicitacao = solicit.solicitacao;
			}
		}
		for (ConsultaPedidoCustomizado pedido : pedidos) {
			
			PedidoCustomizado pedidoCustomizado = new PedidoCustomizado(id, numSolicitacao, pedido.pedidoVenda, pedido.dataEmisVenda, pedido.dataEntrVenda, pedido.cliPedCgcCli9, 
					pedido.cliPedCgcCli4, pedido.cliPedCgcCli2, pedido.cdItPeGrupo, pedido.cdItPeSubgrupo, pedido.cdItPeItem, pedido.codigoDeposito, pedido.qtdePedida, 
					pedido.caminhoArquivo, pedido.ordemProducao, pedido.periodo, pedido.situacao, pedido.selecao, pedido.dataRegistro, pedido.alternativa, pedido.roteiro, 
					pedido.seqItemPedido, pedido.flagImagem, pedido.ordemTamanho);
			pedidoCustomizadoRepository.save(pedidoCustomizado);
			id += 1;
		}
		}
}
