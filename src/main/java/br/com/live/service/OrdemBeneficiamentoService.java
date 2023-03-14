package br.com.live.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.live.custom.OrdemBeneficiamentoCustom;
import br.com.live.custom.ProdutoCustom;
import br.com.live.entity.OrdemBeneficiamentoItem;
import br.com.live.model.AnaliseQualidade;
import br.com.live.model.OrdemBeneficiamentoItens;
import br.com.live.model.Produto;
import br.com.live.repository.OrdemBeneficiamentoItemRepository;
import br.com.live.util.StatusGravacao;


@Transactional
@Service
public class OrdemBeneficiamentoService {
	
	private final OrdemBeneficiamentoItemRepository ordemBeneficiamentoItemRepository;
	private final OrdemBeneficiamentoCustom ordemBeneficiamentoCustom;
	private final ProdutoCustom produtoCustom;
	
	public OrdemBeneficiamentoService(OrdemBeneficiamentoItemRepository ordemBeneficiamentoItemRepository, OrdemBeneficiamentoCustom ordemBeneficiamentoCustom, 
			ProdutoCustom produtoCustom) {
		
		this.ordemBeneficiamentoItemRepository = ordemBeneficiamentoItemRepository;
		this.ordemBeneficiamentoCustom = ordemBeneficiamentoCustom;
		this.produtoCustom = produtoCustom;
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
	
	//public String gerarPdfLaudo(List<ConsultaChamado> chamados, String dataInicio, String dataFim) throws JRException, FileNotFoundException {

	//	String nomeRelatorioGerado = "";

	//	JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(chamados);

	//	Map<String, Object> parameters = setParameters(dataInicio, dataFim);

	//	nomeRelatorioGerado = reportService.generateReport("pdf", dataSource, "chamados", parameters, "chamados", false);

	//	return nomeRelatorioGerado;
	//	}

	//public Map<String, Object> setParameters(String dataInicio, String dataFim) {

	//	Map<String, Object> parameters = new HashMap<>();
	//	parameters.put("dataInicio", dataInicio);
	//	parameters.put("dataFim", dataFim);

	//	return parameters;
	//	}

	//public void deleteByCodChamado(int codChamado) {
	//	chamadoRepository.deleteByCodChamado(codChamado);
	//	}
	
}
