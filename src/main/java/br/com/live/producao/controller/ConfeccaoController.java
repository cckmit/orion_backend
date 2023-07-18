package br.com.live.producao.controller;

import java.io.FileNotFoundException;
import java.util.List;

import br.com.live.producao.body.BodyConfeccao;
import br.com.live.producao.body.BodyObservacaoPorOp;
import br.com.live.producao.body.BodyTipoObservacao;
import br.com.live.producao.custom.CalendarioCustom;
import br.com.live.producao.custom.ConfeccaoCustom;
import br.com.live.producao.entity.EncolhimentoCad;
import br.com.live.producao.entity.MetasProducao;
import br.com.live.producao.entity.MetasProducaoSemana;
import br.com.live.producao.entity.ObservacaoOrdemPacote;
import br.com.live.producao.entity.Restricoes;
import br.com.live.producao.entity.TipoObservacao;
import br.com.live.producao.model.ConsultaEncolhimentoCad;
import br.com.live.producao.model.ConsultaObservacaoOrdemPacote;
import br.com.live.producao.model.ConsultaRestricoesRolo;
import br.com.live.producao.model.EstagioProducao;
import br.com.live.producao.repository.EncolhimentoCadRepository;
import br.com.live.producao.repository.MetasProducaoRepository;
import br.com.live.producao.repository.MetasProducaoSemanaRepository;
import br.com.live.producao.repository.ObservacaoOrdemPacoteRepository;
import br.com.live.producao.repository.TipoObservacaoRepository;
import br.com.live.producao.service.ConfeccaoService;
import br.com.live.produto.custom.ProdutoCustom;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.comercial.entity.PedidoCustomizado;
import br.com.live.comercial.model.ConsultaPedidoCustomizado;
import br.com.live.comercial.service.PedidosCustomizadosService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/confeccao")
public class ConfeccaoController {

	private ObservacaoOrdemPacoteRepository observacaoOrdemPacoteRepository;
	private TipoObservacaoRepository tipoObservacaoRepository;
	private ConfeccaoService confeccaoService;
	private ConfeccaoCustom confeccaoCustom;
	private MetasProducaoRepository metasProducaoRepository;
	private MetasProducaoSemanaRepository metasProducaoSemanaRepository;
	private CalendarioCustom calendarioCustom;
	private PedidosCustomizadosService pedidosCustomizadosService;
	private EncolhimentoCadRepository encolhimentoCadRepository;
	private ProdutoCustom produtoCustom;

	@Autowired
	public ConfeccaoController(ObservacaoOrdemPacoteRepository observacaoOrdemPacoteRepository,
			CalendarioCustom calendarioCustom, MetasProducaoRepository metasProducaoRepository, 
			MetasProducaoSemanaRepository metasProducaoSemanaRepository, ConfeccaoService confeccaoService, ConfeccaoCustom confeccaoCustom,
			TipoObservacaoRepository tipoObservacaoRepository, PedidosCustomizadosService pedidosCustomizadosService,
			EncolhimentoCadRepository encolhimentoCadRepository, ProdutoCustom produtoCustom) {
		this.observacaoOrdemPacoteRepository = observacaoOrdemPacoteRepository;
		this.tipoObservacaoRepository = tipoObservacaoRepository;
		this.confeccaoService = confeccaoService;
		this.confeccaoCustom = confeccaoCustom;
		this.metasProducaoRepository = metasProducaoRepository;
		this.metasProducaoSemanaRepository = metasProducaoSemanaRepository;
		this.calendarioCustom = calendarioCustom;
		this.pedidosCustomizadosService = pedidosCustomizadosService;
		this.encolhimentoCadRepository = encolhimentoCadRepository;
		this.produtoCustom = produtoCustom;
	}

	@RequestMapping(value = "/find-all-tipo-obs", method = RequestMethod.GET)
	public List<TipoObservacao> findAllTiposObs() {
		return tipoObservacaoRepository.findAll();
	}

	@RequestMapping(value = "/find-tipo-obs-by-id/{id}", method = RequestMethod.GET)
	public TipoObservacao findTipoObsById(@PathVariable("id") long id) {
		return tipoObservacaoRepository.findByIdLong(id);
	}

	@RequestMapping(value = "/save-tipo-observacao", method = RequestMethod.POST)
	public TipoObservacao saveTipoObservacao(@RequestBody BodyTipoObservacao body) {
		return confeccaoService.saveTipoObservacao(body.id, body.descricao);
	}

	@RequestMapping(value = "/delete-by-id/{id}", method = RequestMethod.DELETE)
	public List<TipoObservacao> deleteTipoObservacaoById(@PathVariable("id") long id) {
		confeccaoService.deleteById(id);
		return tipoObservacaoRepository.findAll();
	}

	@RequestMapping(value = "/find-all-observacoes", method = RequestMethod.GET)
	public List<ConsultaObservacaoOrdemPacote> findAllObservacoes() {
		return confeccaoService.findAllObsWithQuantidade();
	}

	@RequestMapping(value = "/find-obs-by-id/{id}", method = RequestMethod.GET)
	public ObservacaoOrdemPacote findObsByIdComposto(@PathVariable("id") String id) {
		return observacaoOrdemPacoteRepository.findByIdComposto(id);
	}

	@RequestMapping(value = "/save-observacao", method = RequestMethod.POST)
	public void saveObservacao(@RequestBody BodyObservacaoPorOp body) {
		confeccaoService.saveObservacao(ConteudoChaveNumerica.parseValueToListInt(body.listEstagio), ConteudoChaveNumerica.parseValueToListInt(body.listOrdemProducao), body.ordemConfeccao, body.tipoObservacao, body.obsAdicional);
	}
	
	@RequestMapping(value = "/find-all-pacotes-by-ordem/{ordemProducao}", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findObsByIdComposto(@PathVariable("ordemProducao") int ordemProducao) {
		return confeccaoService.findAllPacotesOrdem(ordemProducao);
	}
	
	@RequestMapping(value = "/delete-obs-by-id/{id}", method = RequestMethod.DELETE)
	public List<ConsultaObservacaoOrdemPacote> deleteObservacaoById(@PathVariable("id") String id) {
		confeccaoService.deleteObservacaoById(id);
		return confeccaoService.findAllObsWithQuantidade();
	}

	@RequestMapping(value = "/find-all-restricoes", method = RequestMethod.GET)
	public List<Restricoes> findAllRestricoes() {
		return confeccaoService.findAllRestricoes();
	}
	
	@RequestMapping(value = "/find-all-metas-producao", method = RequestMethod.GET)
	public List<MetasProducao> findAllMetasProducao() {
		return confeccaoService.findAllMetasProducao();
	}

	@RequestMapping(value = "/find-restricoes-by-id/{idRestricao}", method = RequestMethod.GET)
	public Restricoes findAllRestricoes(@PathVariable("idRestricao") long idRestricao) {
		return confeccaoService.findRestricaoById(idRestricao);
	}

	@RequestMapping(value = "/save-restricoes", method = RequestMethod.POST)
	public List<Restricoes> saveRestricoes(@RequestBody BodyConfeccao body) {
		confeccaoService.saveRestricoes(body.id, body.descricao);
		return confeccaoService.findAllRestricoes();
	}

	@RequestMapping(value = "/delete-restricoes-by-id/{idRestricao}", method = RequestMethod.DELETE)
	public List<Restricoes> deleteRestricao(@PathVariable("idRestricao") long idRestricao) {
		confeccaoService.deleteByIdRestricao(idRestricao);
		return confeccaoService.findAllRestricoes();
	}

	@RequestMapping(value = "/delete-restricoes-rolo-by-id/{idSeq}", method = RequestMethod.DELETE)
	public List<ConsultaRestricoesRolo> deleteRestricaoRolo(@PathVariable("idSeq") long idSeq) {
		confeccaoService.deleteBySeqRestricao(idSeq);
		return confeccaoCustom.findAllRestricoesPorRolo();
	}

	@RequestMapping(value = "/find-options-restricao", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findOptionRestricao() {
		return confeccaoCustom.findOptionsRestricao();
	}

	@RequestMapping(value = "/find-option-leitor-ordens/{leitor}", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findOrdensLeitor(@PathVariable("leitor") String leitor) {
		return confeccaoCustom.findOptionLeitorOrdensBeneficiamento(leitor);
	}

	@RequestMapping(value = "/find-option-leitor-rolos/{leitor}", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findRolosLeitor(@PathVariable("leitor") String leitor) {
		return confeccaoCustom.findOptionLeitorRolos(leitor);
	}

	@RequestMapping(value = "/consulta-restricoes-teste", method = RequestMethod.GET)
		public List<ConsultaRestricoesRolo> consultaRestricoesRolos() {
		return confeccaoCustom.findAllRestricoesPorRolo();
	}

	@RequestMapping(value = "/save-restricoes-por-rolo", method = RequestMethod.POST)
	public void saveRestricoesPorRolo(@RequestBody BodyConfeccao body) {
		confeccaoService.proxySaveRestricoesRolo(body.rolos, body.restricoes);
	}

	@RequestMapping(value = "/save-restricoes-por-ordens", method = RequestMethod.POST)
	public void saveRestricoesPorOrdens(@RequestBody BodyConfeccao body) {
		confeccaoService.proxySaveRestricoesPorOrdemBenef(body.ordens, body.restricoes);
	}
	
	@RequestMapping(value = "/find-pedidos-custom/{solicitacao}", method = RequestMethod.GET)
    public List<ConsultaPedidoCustomizado> findAllPainelPedCustomById(@PathVariable("solicitacao") int solicitacao) {
    	return confeccaoCustom.findAllPainelPedCustomBySolic(solicitacao);
    }
	
	@RequestMapping(value = "/find-pedidos-custom", method = RequestMethod.GET)
    public List<ConsultaPedidoCustomizado> findAllPainelPedCustomByDate() {
    	return confeccaoCustom.findAllPainelPedCustomByDate();
    }

	@RequestMapping(value = "/find-dias-uteis/{mes}/{ano}", method = RequestMethod.GET)
	public int findDiasUteis(@PathVariable("mes") int mes, @PathVariable("ano") int ano) {
		return calendarioCustom.findDiasUteis(mes, ano);
	}
	
	@RequestMapping(value = "/find-metas-producao/{idMeta}", method = RequestMethod.GET)
    public MetasProducao findMetasProducao(@PathVariable("idMeta") String idMeta) {
    	return metasProducaoRepository.findByIdMeta(idMeta);
    }
	
	@RequestMapping(value = "/find-metas-producao-semana/{idMeta}", method = RequestMethod.GET)
    public List<MetasProducaoSemana> findMetasProducaoSemana(@PathVariable("idMeta") String idMeta) {
    	return metasProducaoSemanaRepository.findByIdMes(idMeta);
    }
	
	@RequestMapping(value = "/save-meta-producao", method = RequestMethod.POST)
	public String saveMetaProducao(@RequestBody BodyConfeccao body) {
		return confeccaoService.saveMetaProducao(body.idMetaMes, body.mesMeta, body.anoMeta , body.codEstagio, body.metaMes, body.diasUteis, body.metaDiaria, body.metaAjustada, body.numeroExpedidor, body.metaExpedidor);
	}
		
	@RequestMapping(value = "/save-meta-producao-semana", method = RequestMethod.POST)
	public void saveMetaSemana(@RequestBody BodyConfeccao body) {
		confeccaoService.saveMetaSemana(body.idMes, body.mesMeta, body.anoMeta, body.metaReal, body.metaAjustada);
		
	}       
	@RequestMapping(value = "/delete-meta/{idMeta}", method = RequestMethod.DELETE)
    public String deleteMetasById(@PathVariable("idMeta") String idMeta) {                  
        return confeccaoService.deleteMetasById(idMeta);
    }
	
	@RequestMapping(value = "/delete-meta-semana/{idMeta}", method = RequestMethod.DELETE)
    public String deleteMetasSemanaById(@PathVariable("idMeta") String idMeta) {                  
        return confeccaoService.deleteMetasSemanaById(idMeta);
    }
	
	@RequestMapping(value = "/delete-encolhimento-cad/{id}", method = RequestMethod.DELETE)
    public String deleteEncolhimentoById(@PathVariable("id") int id) {                  
        return encolhimentoCadRepository.deleteById(id);
    }
	
	@RequestMapping(value = "/find-all-estagios", method = RequestMethod.GET)
	public List<EstagioProducao> findAllEstagios() {
		return confeccaoCustom.findAllEstagio();
	}
	
	@RequestMapping(value = "/find-all-estagios-metas", method = RequestMethod.GET)
	public List<EstagioProducao> findAllEstagiosMetas() {
		return confeccaoCustom.findAllEstagioMetas();
	}
	
	@RequestMapping(value = "/find-all-pedidos-person", method = RequestMethod.GET)
	public List<PedidoCustomizado> findAllPedidos() {
		return confeccaoCustom.findAllPedidosCustom();
	}
	
	@RequestMapping(value = "/load-pedidos-personalizados", method = RequestMethod.GET)
	public void loadPedidosPersonalizados(){
		pedidosCustomizadosService.loadPedidosPersonalizados();
	}
	
	@RequestMapping(value = "/email-pedidos-personalizados/{id}", method = RequestMethod.POST)
	public void emailPedidosPersonalizados(@PathVariable("id") long id){
		pedidosCustomizadosService.enviarEmail(id);
	}
	
	@RequestMapping(value = "/gerar-ordem-pedidos-personalizados", method = RequestMethod.POST)
	public void gerarOrdemPedidosPersonalizados(@RequestBody BodyConfeccao body){
		pedidosCustomizadosService.gerarOrdem(body.solicitacao, body.periodoProducao, body.alternativa, body.roteiro);
	}

	@RequestMapping(value = "/find-all-pacotes-by-ordem-sem-opc-todos/{ordemProducao}", method = RequestMethod.GET)
	public List<ConteudoChaveNumerica> findAllPacotesByOrdem(@PathVariable("ordemProducao") int ordemProducao) {
		return confeccaoService.findAllPacotesOrdemSemOpcTodos(ordemProducao);
	}

	// GERA RELATÓRIO JASPER COM AS ETIQUETAS DE DECORAÇÃO
	@RequestMapping(value = "/gerar-etiquetas-decoracao", method = RequestMethod.POST)
	public String gerarEtiquetasDecoracao(@RequestBody BodyConfeccao body) throws JRException, FileNotFoundException {
		return confeccaoService.gerarEtiquetasDecoracao(body.ordemProducao, body.pacotes, body.estagios);
	}
	
	@RequestMapping(value = "/carregar-encolhimento-cad", method = RequestMethod.GET)
	public List<ConsultaEncolhimentoCad> carregarEncolhimentoCad() {
		return confeccaoCustom.carregarEncolhimentoCad();
	}
	
	@RequestMapping(value = "/carregar-encolhimento-cad-prototipo", method = RequestMethod.GET)
	public List<ConsultaEncolhimentoCad> carregarEncolhimentoCadPrototipo() {
		return confeccaoCustom.carregarEncolhimentoCadPrototipo();
	}
	
	@RequestMapping(value = "/find-all-tecido/{produto}", method = RequestMethod.GET)
	public List<ConteudoChaveAlfaNum> findAllTecido(@PathVariable("produto") String produto) {
		return produtoCustom.findTecidos(produto);
	}
	
	@RequestMapping(value = "/find-all-item/{id}", method = RequestMethod.GET)
	public EncolhimentoCad findAllById(@PathVariable("id") int id) {
		return encolhimentoCadRepository.findById(id);
	}
	
	@RequestMapping(value = "/save-encolhimento-cad", method = RequestMethod.POST)
	public void saveEncolhimentoCad(@RequestBody BodyConfeccao body) {
		confeccaoService.saveEncolhimentoCad(body.idCadastro, body.usuario, body.dataRegistro, body.tecido, body.largAcomodacao, body.compAcomodacao,
				body.largTermo, body.compTermo, body.largEstampa, body.compEstampa, body.largEstampaPoli, body.compEstampaPoli, body.largPolimerizadeira,
				body.compPolimerizadeira, body.largEstampaPrensa, body.compEstampaPrensa, body.observacao, body.tipo);
	}
	
	@RequestMapping(value = "/calcula-media-por-produto/{produto}/{tipo}", method = RequestMethod.GET)
	public List<ConsultaEncolhimentoCad> findAllById(@PathVariable("produto") String produto, @PathVariable("tipo") int tipo) {
		return confeccaoService.calcularMediaPorProduto(produto, tipo);
	}

}
