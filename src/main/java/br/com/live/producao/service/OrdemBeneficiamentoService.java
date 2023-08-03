package br.com.live.producao.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.live.comercial.model.ConsultaMinutaTransporte;
import br.com.live.comercial.model.ConsultaTransportadora;
import br.com.live.producao.custom.OrdemBeneficiamentoCustom;
import br.com.live.producao.entity.OrdemBeneficiamentoItem;
import br.com.live.producao.model.AnaliseQualidade;
import br.com.live.producao.model.OrdemBeneficiamentoItens;
import br.com.live.producao.repository.OrdemBeneficiamentoItemRepository;
import br.com.live.produto.custom.ProdutoCustom;
import br.com.live.produto.model.Produto;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.StatusGravacao;
import br.com.live.util.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Transactional
@Service
public class OrdemBeneficiamentoService {
	
	private final OrdemBeneficiamentoItemRepository ordemBeneficiamentoItemRepository;
	private final OrdemBeneficiamentoCustom ordemBeneficiamentoCustom;
	private final ProdutoCustom produtoCustom;
	private final ReportService reportService;
	
	public OrdemBeneficiamentoService(OrdemBeneficiamentoItemRepository ordemBeneficiamentoItemRepository, OrdemBeneficiamentoCustom ordemBeneficiamentoCustom, 
			ProdutoCustom produtoCustom, ReportService reportService) {
		
		this.ordemBeneficiamentoItemRepository = ordemBeneficiamentoItemRepository;
		this.ordemBeneficiamentoCustom = ordemBeneficiamentoCustom;
		this.produtoCustom = produtoCustom;
		this.reportService = reportService;
	}
	public StatusGravacao salvarItemOrdem(String id, String usuario, int ordemProducao, String produto, float qtdeRolos, float qtdeQuilos, float larguraTecido, 
			float gramatura, float rendimento, int alternativaItem, int roteiroItem, int deposito, String observacao) {
	
		String[] prodConcat = produto.split("[.]");
		String nivel = prodConcat[0];
		String referencia = prodConcat[1];
		String tamanho = prodConcat[2];
		String cor = prodConcat[3];		
		
		if (!ordemBeneficiamentoCustom.existsRoteiro(nivel, referencia, tamanho, cor, alternativaItem, roteiroItem))
			return new StatusGravacao(false, "Alternativa ou Roteiro não cadastrado para esse Tecido");
		
		OrdemBeneficiamentoItem dadosItem = ordemBeneficiamentoItemRepository.findById(id);
		
		int sequencia = ordemBeneficiamentoCustom.findNextSequencia();
		String undMedida = "";
		
		if(dadosItem == null) {
			dadosItem = new OrdemBeneficiamentoItem(sequencia, usuario, ordemProducao, nivel, referencia, tamanho, cor, qtdeRolos, qtdeQuilos, undMedida, larguraTecido, 
													gramatura, rendimento, alternativaItem, roteiroItem, deposito, observacao);	
		} else {
			dadosItem.ordemProducao = 0;
			dadosItem.panoSbgNivel99 = nivel;
			dadosItem.panoSbgGrupo = referencia;
			dadosItem.panoSbgSubgrupo = tamanho;
			dadosItem.panoSbgItem = cor;
			dadosItem.qtdeRolos = qtdeRolos;
			dadosItem.qtdeQuilos = qtdeQuilos;
			dadosItem.undMedida = undMedida;
			dadosItem.larguraTecido = larguraTecido;
			dadosItem.gramatura = gramatura;
			dadosItem.rendimento = rendimento;
			dadosItem.alternativaItem = alternativaItem;
			dadosItem.roteiroItem = roteiroItem;
			dadosItem.deposito = deposito;
			dadosItem.observacao = observacao;
			
		}
		ordemBeneficiamentoItemRepository.save(dadosItem);	
		return new StatusGravacao(true, "Item Cadastrado com Sucesso!");
	}
	
	public List<OrdemBeneficiamentoItens> deleteItemOrdem(String id, String usuario) {
		ordemBeneficiamentoItemRepository.deleteById(id);
		return ordemBeneficiamentoCustom.findAllItensOrdens(usuario);
	}
	
	public List<ConteudoChaveNumerica> findPeriodoProducaoAno(int periodoProducao){
		return ordemBeneficiamentoCustom.findPeriodoProducaoAno(periodoProducao);
	}
	
	public List<OrdemBeneficiamentoItens> gerarOrdemBeneficiamento(int periodoProducao, Date dataPrograma, Date previsaoTermino, String maquina, int tipoOrdem, String usuario) {
		String msgErro = "";
		List<OrdemBeneficiamentoItens> gerarOrdensBeneficItens = ordemBeneficiamentoCustom.findAllItensOrdens(usuario);
		List<OrdemBeneficiamentoItens> listaOrdensGeradas = new ArrayList<OrdemBeneficiamentoItens>();
		String[] maqConcat = maquina.split("[.]");
		String grupo = maqConcat[0];
		String subGrupo = maqConcat[1];
		String numeroMaquina = maqConcat[2];
		for (OrdemBeneficiamentoItens itens : gerarOrdensBeneficItens) {
			int ordem = ordemBeneficiamentoCustom.findNextOrdemBeneficiamento();
			// Gerando Ordem na Capa
			ordemBeneficiamentoCustom.gerarOrdemBeneficMqop010(periodoProducao, dataPrograma, previsaoTermino, grupo, subGrupo, Integer.parseInt(numeroMaquina), tipoOrdem, ordem);
			// Gerando Ordem para os itens
			String[] prodConcat = itens.produto.split("[.]");
			String nivel = prodConcat[0];
			String referencia = prodConcat[1];
			String tamanho = prodConcat[2];
			String cor_label = prodConcat[3];
			String cor = cor_label.substring(0, 6);
			ordemBeneficiamentoCustom.gerarOrdemBeneficMqop020(nivel, referencia, tamanho, cor, itens.rolos, itens.quilos, itens.largura, itens.gramatura, itens.rendimento,
					itens.periodoProducao, itens.observacao, itens.alternativa, itens.roteiro, ordem);
			ordemBeneficiamentoCustom.gerarOrdemBeneficMqop030(nivel, referencia, tamanho, cor, itens.rolos, itens.quilos, itens.periodoProducao, 
					itens.deposito, itens.alternativa, itens.roteiro, ordem);
			ordemBeneficiamentoCustom.updateOrdemProducao(itens.id, ordem);
			
			Produto tecido = produtoCustom.findProduto(nivel, referencia, tamanho, cor);
			String descricaoProduto = tecido.nivel + "." + tecido.grupo + "." + tecido.sub + "." + tecido.item + " - " + tecido.narrativa;
			listaOrdensGeradas.add(new OrdemBeneficiamentoItens(ordem, descricaoProduto , itens.alternativaItem , itens.roteiroItem));	
		}		
		ordemBeneficiamentoItemRepository.deleteByUsuario(usuario);
		return listaOrdensGeradas;		
	}
	
	public Map<String, Object> setParameters(int ordem, JRBeanCollectionDataSource dataSourceLote, JRBeanCollectionDataSource dataSourceRolos) {
		
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put("ordem", ordem);
		parameters.put("listLote", dataSourceLote);
		parameters.put("listRolos", dataSourceRolos);
		
		return parameters;
	}
	public String gerarPdfLaudo(int ordem) throws FileNotFoundException, JRException {
		
		List<AnaliseQualidade> listaAnalise = ordemBeneficiamentoCustom.findAnalise(ordem);
		List<AnaliseQualidade> listaLote = ordemBeneficiamentoCustom.findInfoLote(ordem);
		List<AnaliseQualidade> listaRolos = ordemBeneficiamentoCustom.findInfoRolos(ordem);
		
		String nomeRelatorioGerado = "";
		
		JRBeanCollectionDataSource dataSourceAnalise = new JRBeanCollectionDataSource(listaAnalise);
		JRBeanCollectionDataSource dataSourceLote = new JRBeanCollectionDataSource(listaLote);
		JRBeanCollectionDataSource dataSourceRolos = new JRBeanCollectionDataSource(listaRolos);

		Map<String, Object> parameters = setParameters(ordem, dataSourceLote, dataSourceRolos);

		nomeRelatorioGerado = reportService.generateReport("pdf", dataSourceAnalise, "analise_qualidade", parameters, "Laudo_Qualidade_" + ordem, false);

		return nomeRelatorioGerado;
	}	
}
