package br.com.live.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.Notificacao;
import br.com.live.entity.RequisicaoTecidos;
import br.com.live.entity.RequisicaoTecidosItem;
import br.com.live.repository.RequisicaoTecidosItemRepository;
import br.com.live.repository.RequisicaoTecidosRepository;

@Service
@Transactional
public class RequisicaoTecidosService {

	private final RequisicaoTecidosRepository requisicaoTecidosRepository;
	private final RequisicaoTecidosItemRepository requisicaoTecidosItemRepository;
	private final ProdutoCustom produtoCustom;
	private final NotificacaoService notificacaoService;
	private final ConfiguracoesService configuracoes;
	private final EmailService email;

	public RequisicaoTecidosService(RequisicaoTecidosRepository requisicaoTecidosRepository,
			RequisicaoTecidosItemRepository requisicaoTecidosItemRepository, ProdutoCustom produtoCustom,
			NotificacaoService notificacaoService, ConfiguracoesService configuracoes,
			EmailService email) {
		this.requisicaoTecidosRepository = requisicaoTecidosRepository;
		this.requisicaoTecidosItemRepository = requisicaoTecidosItemRepository;
		this.produtoCustom = produtoCustom;
		this.notificacaoService = notificacaoService;
		this.configuracoes = configuracoes;
		this.email = email;
	}

	public void confirmarRequisicao(long id) {
		RequisicaoTecidos requisicao = requisicaoTecidosRepository.findById(id);
		requisicao.situacao = 2;
		requisicaoTecidosRepository.save(requisicao);
	}

	public RequisicaoTecidos saveRequisicao(long id, String descricao, int situacao, String usuario) {

		RequisicaoTecidos requisicao = requisicaoTecidosRepository.findById(id);

		if (requisicao == null) {
			requisicao = new RequisicaoTecidos(requisicaoTecidosRepository.findNextId(), descricao.toUpperCase(),
					situacao, usuario, new Date());
		} else {
			requisicao.descricao = descricao.toUpperCase();
			if (requisicao.situacao < 2) // Atualiza status apenas se ainda não foi confirmado programação (situacao 2).
				requisicao.situacao = situacao;
		}
		requisicaoTecidosRepository.save(requisicao);

		return requisicao;
	}

	public RequisicaoTecidos liberarRequisicao(long id) {

		RequisicaoTecidos requisicao = requisicaoTecidosRepository.findById(id);

		if (requisicao.situacao < 2)
			requisicao.situacao = 1; // Liberada

		requisicaoTecidosRepository.save(requisicao);

		System.out.println("url front-end: " + configuracoes.getUrlFrontEnd());
		
		String assunto = "Requisição de Tecidos " + id;
		String corpoEmail = "<h4> Uma nova requisi&ccedil;&atilde;o de tecidos (n&uacute;mero <bold>" + id
				+ "</bold>) foi liberada pelo PCP! <br/> Clique <a href='http://" + configuracoes.getUrlFrontEnd()
				+ "/requisicao-tecidos-liberados'> aqui </a> para acessar a consulta de requisi&ccedil&otilde;es.</h4>";

		List<String> emails = notificacaoService.findEmailByTipoNotificacao(Notificacao.REQUISICAO_TECIDOS);

		for (String destinatario : emails) {
			if (destinatario != null) {
				
				System.out.println("Envio de Email");
				System.out.println("destinatario: " + destinatario);
				System.out.println("remetente: " + configuracoes.getRemetenteEmail());
				System.out.println("smtp: " + configuracoes.getSmtpEmail());
				System.out.println("porta: " + configuracoes.getPortaEmail());
				System.out.println("senha: " + configuracoes.getSenhaEmail());
				
				email.enviar(assunto, corpoEmail, destinatario);
			}
		}

		return requisicao;
	}

	public void saveRequisicaoItem(long id, long idRequisicao, String nivel, String grupo, String sub, String item,
			int alternativa, int roteiro, double quantidade) {

		if (produtoCustom.existsRoteiroAltRotMaiorZero(nivel, grupo, sub, item, alternativa, roteiro)) {
			RequisicaoTecidosItem requisicaoItem = requisicaoTecidosItemRepository.findById(id);
			if (requisicaoItem == null) {
				requisicaoItem = new RequisicaoTecidosItem(requisicaoTecidosItemRepository.findNextId(), idRequisicao,
						requisicaoTecidosItemRepository.findNextSequencia(idRequisicao), nivel, grupo, sub, item,
						alternativa, roteiro, quantidade);
			} else {
				requisicaoItem.nivel = nivel;
				requisicaoItem.grupo = grupo;
				requisicaoItem.sub = sub;
				requisicaoItem.item = item;
				requisicaoItem.alternativa = alternativa;
				requisicaoItem.roteiro = roteiro;
				requisicaoItem.quantidade = quantidade;
			}
			requisicaoTecidosItemRepository.save(requisicaoItem);
		}
	}

	public void deleteRequisicao(long idRequisicao) {
		requisicaoTecidosItemRepository.deleteByIdRequisicao(idRequisicao);
		requisicaoTecidosRepository.deleteById(idRequisicao);
	}

	public void deleteRequisicaoItem(long idRequisicaoItem) {
		requisicaoTecidosItemRepository.deleteById(idRequisicaoItem);
	}
}
