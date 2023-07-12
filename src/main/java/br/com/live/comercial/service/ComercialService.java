package br.com.live.comercial.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.live.comercial.model.*;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.FormataData;
import br.com.live.util.FormataString;
import br.com.live.util.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.comercial.custom.ComercialCustom;
import br.com.live.comercial.entity.BloqueioTitulosForn;
import br.com.live.comercial.entity.CanaisDeDistribuicao;
import br.com.live.comercial.entity.ControleDescontoCliente;
import br.com.live.comercial.entity.FaturamentoLiveClothing;
import br.com.live.comercial.entity.MetasCategoria;
import br.com.live.comercial.entity.PedidosGravadosComDesconto;
import br.com.live.comercial.entity.TipoClientePorCanal;
import br.com.live.comercial.entity.TpClienteXTabPreco;
import br.com.live.comercial.entity.TpClienteXTabPrecoItem;
import br.com.live.comercial.entity.ValorDescontoClientesImportados;
import br.com.live.comercial.repository.BloqueioTitulosFornRepository;
import br.com.live.comercial.repository.CanaisDeDistribuicaoRepository;
import br.com.live.comercial.repository.ControleDescontoClienteRepository;
import br.com.live.comercial.repository.FaturamentoLiveClothingRepository;
import br.com.live.comercial.repository.MetasCategoriaRepository;
import br.com.live.comercial.repository.PedidosGravadosComDescontoRepository;
import br.com.live.comercial.repository.TipoClientePorCanalRepository;
import br.com.live.comercial.repository.TpClienteXTabPrecoItemRepository;
import br.com.live.comercial.repository.TpClienteXTabPrecoRepository;
import br.com.live.comercial.repository.ValorDescontoClientesImpRepository;
import br.com.live.produto.custom.ProdutoCustom;
import br.com.live.produto.model.Produto;
import br.com.live.util.FormataData;

import br.com.live.util.StatusGravacao;

@Service
@Transactional
public class ComercialService {
	
	private final BloqueioTitulosFornRepository bloqueioTitulosFornRepository;
	private final ComercialCustom comercialCustom;
	private final ProdutoCustom produtoCustom;
	private final MetasCategoriaRepository metasCategoriaRepository;
	private final TpClienteXTabPrecoRepository tpClienteXTabPrecoRepository;
	private final TpClienteXTabPrecoItemRepository tpClienteXTabPrecoItemRepository;
	private final ValorDescontoClientesImpRepository valorDescontoClientesImpRepository;
	private final PedidosGravadosComDescontoRepository pedidosGravadosComDescontoRepository;
	private final ControleDescontoClienteRepository controleDescontoClienteRepository;
	private final FaturamentoLiveClothingRepository faturamentoLiveClothingRepository;
	private final CanaisDeDistribuicaoRepository canaisDeDistribuicaoRepository;
	private final TipoClientePorCanalRepository tipoClientePorCanalRepository;
	private final EmailService emailService;
  
	public ComercialService(BloqueioTitulosFornRepository bloqueioTitulosFornRepository, ComercialCustom comercialCustom, ProdutoCustom produtoCustom, 
			MetasCategoriaRepository metasCategoriaRepository, TpClienteXTabPrecoRepository tpClienteXTabPrecoRepository, TpClienteXTabPrecoItemRepository tpClienteXTabPrecoItemRepository, 
			ValorDescontoClientesImpRepository valorDescontoClientesImpRepository, PedidosGravadosComDescontoRepository pedidosGravadosComDescontoRepository, 
			ControleDescontoClienteRepository controleDescontoClienteRepository, FaturamentoLiveClothingRepository faturamentoLiveClothingRepository, 
			CanaisDeDistribuicaoRepository canaisDeDistribuicaoRepository, TipoClientePorCanalRepository tipoClientePorCanalRepository,	EmailService emailService) {

		this.bloqueioTitulosFornRepository = bloqueioTitulosFornRepository;
		this.comercialCustom = comercialCustom;
		this.produtoCustom = produtoCustom;
		this.metasCategoriaRepository = metasCategoriaRepository; 
		this.tpClienteXTabPrecoRepository = tpClienteXTabPrecoRepository;
		this.tpClienteXTabPrecoItemRepository = tpClienteXTabPrecoItemRepository;
		this.valorDescontoClientesImpRepository = valorDescontoClientesImpRepository;
		this.pedidosGravadosComDescontoRepository = pedidosGravadosComDescontoRepository;
		this.controleDescontoClienteRepository = controleDescontoClienteRepository;
    	this.faturamentoLiveClothingRepository = faturamentoLiveClothingRepository;
    	this.canaisDeDistribuicaoRepository = canaisDeDistribuicaoRepository;
    	this.tipoClientePorCanalRepository = tipoClientePorCanalRepository;
		this.emailService = emailService;
	}
	
	public List<ConsultaTitulosBloqForn> findAllFornBloq() {
		return comercialCustom.findAllTitulosBloqForn();
	}
	
	public BloqueioTitulosForn findForncedorBloq(String idForn) {
		String[] fornecedorConcat = idForn.split("[.]");

		String fornecedor9 = fornecedorConcat[0];
		String fornecedor4 = fornecedorConcat[1];
		String fornecedor2 = fornecedorConcat[2];
		
		return bloqueioTitulosFornRepository.findByIdBloq(Integer.parseInt(fornecedor9) + Integer.parseInt(fornecedor4) + Integer.parseInt(fornecedor2));
	}
	
	public void saveBloqueio(String fornecedor, String motivo, boolean editMode) {
		
		String[] fornecedorConcat = fornecedor.split("[.]");

		String fornecedor9 = fornecedorConcat[0];
		String fornecedor4 = fornecedorConcat[1];
		String fornecedor2 = fornecedorConcat[2];
		
		BloqueioTitulosForn dadosBloqueio = bloqueioTitulosFornRepository.findByIdBloq(Integer.parseInt(fornecedor9) + Integer.parseInt(fornecedor4) + Integer.parseInt(fornecedor2));
		
		if (dadosBloqueio == null) {
			dadosBloqueio = new BloqueioTitulosForn(Integer.parseInt(fornecedor9), Integer.parseInt(fornecedor4), Integer.parseInt(fornecedor2), new Date(), null, motivo);
		} else {
			dadosBloqueio.motivo = motivo;
			
			if ((dadosBloqueio.dataDesbloqueio != null) && (!editMode)) {
				dadosBloqueio.dataDesbloqueio = null;
				dadosBloqueio.dataBloqueio = new Date();
			}
		}
		bloqueioTitulosFornRepository.save(dadosBloqueio);
	}
	public List<TpClienteXTabPreco> findAllRelacionamento() {
		return tpClienteXTabPrecoRepository.findAll();
	}
	
	public void deleteItemRelacionamento(int idItem) {
		tpClienteXTabPrecoItemRepository.deleteById(idItem);
	}
	
	public void deleteRelacionamento(String idCapa) {
		tpClienteXTabPrecoItemRepository.deleteByIdCapa(idCapa);
	}
	
	public void deleteRelacCapa(String idCapa) {
		tpClienteXTabPrecoRepository.deleteById(idCapa);
	}
	
	public List<CanaisDeDistribuicao> findAllCanaisDistribuicao(){
		return canaisDeDistribuicaoRepository.findAll();
	}
	
	public List<ConsultaTipoClientePorCanal> findTipoClienteSemCanal(){
		return comercialCustom.findTipoClienteSemCanal();
	}
	
	public void deleteCanaisDistribuicao(int idCanal) {
		canaisDeDistribuicaoRepository.deleteById(idCanal);
	}
	
	public void deleteTipoClienteCanal(int id) {
		tipoClientePorCanalRepository.deleteById(id);
	}
	
	public void liberarBloqueio(String fornecedor) {
		String[] fornecedorConcat = fornecedor.split("[.]");

		String fornecedor9 = fornecedorConcat[0];
		String fornecedor4 = fornecedorConcat[1];
		String fornecedor2 = fornecedorConcat[2];
		
		BloqueioTitulosForn dadosBloqueio = bloqueioTitulosFornRepository.findByIdBloq(Integer.parseInt(fornecedor9) + Integer.parseInt(fornecedor4) + Integer.parseInt(fornecedor2));
		dadosBloqueio.dataDesbloqueio = new Date();
		bloqueioTitulosFornRepository.save(dadosBloqueio);
	}
	
	public void saveProdutosIntegracaoEcom(String referencia, String tamanho, String cor) {
		List<Produto> produtos = produtoCustom.findProdutos("1", referencia, tamanho, cor);
	
		for (Produto produto : produtos) {
			String codigoSku = produto.nivel + "." + produto.grupo + "." + produto.sub + "." + produto.item;
			comercialCustom.gravaEnvioProdEcommerce(codigoSku);
		}
	}
	public void importarMetasCategoria(long codEstacao, int tipoMeta, List<MetasCategoria> tabImportar) {
		metasCategoriaRepository.deleteByCodEstacaoAndTipoMeta(codEstacao, tipoMeta);		
		for (MetasCategoria metas : tabImportar) {					
			MetasCategoria newMetas = new MetasCategoria(metas.codEstacao, metas.codRepresentante, metas.tipoMeta, metas.valorCategoria1, metas.valorCategoria2, metas.valorCategoria3, 
					metas.valorCategoria4, metas.valorCategoria5, metas.valorCategoria6, metas.valorCategoria7, metas.valorCategoria8, metas.valorCategoria9, metas.valorCategoria10);
			metasCategoriaRepository.save(newMetas);
		}		
	}
	
	public List<MetasCategoria> findAllMetasCategoria() {
		return metasCategoriaRepository.findAll();
	}
	
	public String saveRelacionamento(String id, int catalogo, int tipoCliente, String tabela, int numDias, int numInterno) {
		
		TpClienteXTabPreco dadosRelac = tpClienteXTabPrecoRepository.findByIdTpCliTabPreco(id);
		String[] tabelaConcat = tabela.split("[.]");

		String col = tabelaConcat[0];
		String mes = tabelaConcat[1];
		String seq = tabelaConcat[2];
		
		if (dadosRelac == null) {
			dadosRelac = new TpClienteXTabPreco(catalogo, tipoCliente, Integer.parseInt(col), Integer.parseInt(mes), Integer.parseInt(seq), numDias, numInterno);
		} else {
			dadosRelac.numDias = numDias;
			dadosRelac.numInterno = numInterno;
		}
		tpClienteXTabPrecoRepository.save(dadosRelac);
		return dadosRelac.id;
	}
	
	private boolean existsTpClienteXTabPrecoItemParaPeriodo(long idItem, String idCapa, Date periodoIni, Date periodoFim) {		
		TpClienteXTabPrecoItem dadosItem = tpClienteXTabPrecoItemRepository.findTabByData(idItem, idCapa, periodoIni, periodoFim);
		
		if (dadosItem != null) return true; 
		return false;
	}	
	
	public StatusGravacao saveRelacionamentoItem(long id, String idCapa, int catalogo, int tipoCliente, String tabela, Date periodoIni, Date periodoFim) {
		
		TpClienteXTabPrecoItem dadosItem = tpClienteXTabPrecoItemRepository.findByIdTpCliTabPrecoItem(id);

		long idItem = 0; 
		if (dadosItem != null) idItem = dadosItem.idItem;
		
		if (existsTpClienteXTabPrecoItemParaPeriodo(idItem, idCapa, periodoIni, periodoFim))
			return new StatusGravacao(false, "Já existe tabela de preço cadastrada para o período informado!");		
		
		String[] tableConcat = tabela.split("[.]");

		String col = tableConcat[0];
		String mes = tableConcat[1];
		String seq = tableConcat[2];
		
		if (dadosItem == null) { 
			dadosItem = new TpClienteXTabPrecoItem(tpClienteXTabPrecoItemRepository.findNextId(), idCapa, catalogo, tipoCliente, Integer.parseInt(col), Integer.parseInt(mes), Integer.parseInt(seq), periodoIni, periodoFim);
		} else {
			dadosItem.idCapa = idCapa;
			dadosItem.colTabEntr = Integer.parseInt(col);
			dadosItem.mesTabEntr = Integer.parseInt(mes);
			dadosItem.seqTabEntr = Integer.parseInt(seq);
			dadosItem.periodoIni = periodoIni;
			dadosItem.periodoFim = periodoFim;	
		}
		tpClienteXTabPrecoItemRepository.save(dadosItem);
		return new StatusGravacao(true, "");		
	}
	
	public FaturamentoLiveClothing findFatLiveClothingById(int idfaturamento) {
		return faturamentoLiveClothingRepository.findByIdFaturamento(idfaturamento);
	}
	
	public CanaisDeDistribuicao findCanalgById(int id) {
		return canaisDeDistribuicaoRepository.findByIdCanal(id);
	}
	
	public List<ConsultaTipoClientePorCanal> findTipoClienteByCanal(int id) {
		return comercialCustom.findTipoClienteByCanal(id);
	}
	
	public void saveFatLiveClothing(int idFaturamento, String loja, String data, int quantidade, int tickets, float conversao, float valorDolar, float valorReal) {
		
		FaturamentoLiveClothing dadosFat = faturamentoLiveClothingRepository.findById(idFaturamento);
		
		if (dadosFat == null) {
			int id = faturamentoLiveClothingRepository.findNextID(); 
			dadosFat = new FaturamentoLiveClothing(id, loja, FormataData.parseStringToDate(data), quantidade, tickets, conversao, valorDolar, valorReal);	
		} else {
			 dadosFat.loja = loja;
			 dadosFat.data = FormataData.parseStringToDate(data);
			 dadosFat.tickets = tickets;
			 dadosFat.conversao = conversao;
			 dadosFat.valorDolar = valorDolar;
			 dadosFat.valorReal = valorReal;
		}
		faturamentoLiveClothingRepository.save(dadosFat);
	}

	public void saveTipoClientePorCanal(int idTpCli, int idCanal, int tipoCliente) {
		
		TipoClientePorCanal dados = tipoClientePorCanalRepository.findById(idTpCli);
		
		if (dados == null) {
			int id = tipoClientePorCanalRepository.findNextID(); 
			dados = new TipoClientePorCanal(id, idCanal, tipoCliente);	
		}
		tipoClientePorCanalRepository.save(dados);
	}

	public void saveCanaisDistribuicao(int id, String descricao, String modalidade) {
		
		CanaisDeDistribuicao dadosCanais = canaisDeDistribuicaoRepository.findByIdCanal(id);
		
		if (dadosCanais == null) {
			int newId = canaisDeDistribuicaoRepository.findNextID(); 
			dadosCanais = new CanaisDeDistribuicao(newId, descricao, modalidade);	
		} else {
			dadosCanais.descricao = descricao;
			dadosCanais.modalidade = modalidade;
			
		}
		canaisDeDistribuicaoRepository.save(dadosCanais);
	}
	
	public void deleteFatLiveClothing(int idFaturamento) {
		faturamentoLiveClothingRepository.deleteById(idFaturamento);
	}
	
	public List<ConteudoChaveAlfaNum> findTipoClienteLive(){
		return comercialCustom.findTipoClienteLive();
	}
  
	public StatusGravacao saveDescontosClientesImportados(List<DescontoClientesImportados> listClientes, String usuario) {
		StatusGravacao status = new StatusGravacao(true, "Planilha importada com sucesso! <br />");
		String errosImportacao = "";

		for (DescontoClientesImportados cliente : listClientes) {
			int validarImportacao = 0;
			ValorDescontoClientesImportados descontoCliente = null;

			int cnpj9 = Integer.parseInt(cliente.cnpjCliente.substring(0,8));
			int cnpj4 = Integer.parseInt(cliente.cnpjCliente.substring(8,12));
			int cnpj2 = Integer.parseInt(cliente.cnpjCliente.substring(12,14));

			// VALIDAR IMPORTAÇÃO
			validarImportacao = comercialCustom.validarImportacaoDescontos(cnpj9, cnpj4, cnpj2, cliente.dataInsercao, cliente.valor);

			if (validarImportacao != 0) {
				errosImportacao += " Cliente " + String.format("%08d", cnpj9) + String.format("%04d", cnpj4) + String.format("%02d", cnpj2) +
						" já possui desconto importado para data de " + cliente.dataInsercao + "! \n";
			} else  {
				try {
					gravarControleDesconto(cnpj9, cnpj4, cnpj2, cliente.valor);
					descontoCliente = new ValorDescontoClientesImportados(comercialCustom.findNextIdImpDescClientes(), cnpj9,cnpj4,cnpj2, FormataData.parseStringToDate(cliente.dataInsercao), cliente.valor, usuario);
					valorDescontoClientesImpRepository.saveAndFlush(descontoCliente);
				} catch (Exception e) {
					System.out.println("Ocorreu um erro ao gravar o desconto!");
				}
			}
		}
		if (!errosImportacao.equalsIgnoreCase("")) {
			status = new StatusGravacao(false, errosImportacao);
		}
		return status;
	}

	public void gravarControleDesconto(int cnpj9, int cnpj4, int cnpj2, float valorDesconto) {
		ControleDescontoCliente controleDesconto = null;

		controleDesconto = controleDescontoClienteRepository.findByIdControle(cnpj9 + "-" + cnpj4 + "-" + cnpj2);
		if (controleDesconto != null) {
			controleDesconto.valorDesconto = controleDesconto.valorDesconto + valorDesconto;
		} else {
			controleDesconto = new ControleDescontoCliente(cnpj9, cnpj4, cnpj2, valorDesconto);
		}
		controleDescontoClienteRepository.saveAndFlush(controleDesconto);
	}

	public List<PedidosComDescontoAConfirmar> prepararPedidosParaAplicarDesconto() {
		List<ClientesImportados> listClientes = new ArrayList<>();
		List<PedidosComDescontoAConfirmar> listPedidosComDesconto = new ArrayList<>();

		// Busca todos os clientes que possuem valor de desconto maior que 0 - orion_com_290
		listClientes = comercialCustom.findClientesImportados();

		for (ClientesImportados dadosCliente : listClientes) {
			List<ConsultaPedidosPorCliente> listPedidos = new ArrayList<>();

			ControleDescontoCliente controleDesconto = controleDescontoClienteRepository.findByIdControle(dadosCliente.cnpj9 + "-" + dadosCliente.cnpj4 + "-" + dadosCliente.cnpj2);
			float totalDesconto = controleDesconto.valorDesconto;

			if (totalDesconto <= 0) continue;

			calcularDescontoPedidos(listPedidosComDesconto, dadosCliente, totalDesconto);
		}
		return listPedidosComDesconto;
	}

	private void calcularDescontoPedidos(List<PedidosComDescontoAConfirmar> listPedidosComDesconto, ClientesImportados dadosCliente, float totalDesconto) {
		List<ConsultaPedidosPorCliente> listPedidos;
		// Busca todos os pedidos em aberto com valor saldo maior que R$2500, para o cliente em contexto, ordenando por data e valor
		listPedidos = comercialCustom.findPedidosPorCliente(dadosCliente.cnpj9, dadosCliente.cnpj4, dadosCliente.cnpj2);

		for (ConsultaPedidosPorCliente dadosPedido : listPedidos) {
			float valorDescCalculado = totalDesconto;
			String obsPedido = " Desconto Total de " + valorDescCalculado;
			
			// Verifica se o pedido é FRANCHISING -- Aplica somente 50% do desconto
			if (dadosPedido.natureza == 421 || dadosPedido.natureza == 422) {
				totalDesconto = totalDesconto / 2;
				valorDescCalculado = totalDesconto;
				obsPedido = " Desconto Total de " + valorDescCalculado;
			}

			boolean aplicaDesconto = validarDescontoPedido(dadosPedido.valorSaldo, valorDescCalculado);
			if (!aplicaDesconto) {
				valorDescCalculado = calcularDescontoMaximoPedido(dadosPedido.valorSaldo);
				obsPedido = "Desconto Parcial de " + valorDescCalculado;
			}

			// Inserir os Pedidos que irão aparecer no Grid
			String cnpjEdit = String.format("%08d", dadosPedido.cnpj9) + String.format("%04d",dadosPedido.cnpj4) + String.format("%02d",dadosPedido.cnpj2);
			
			PedidosComDescontoAConfirmar pedidos = new PedidosComDescontoAConfirmar(dadosPedido.cnpj9, dadosPedido.cnpj4, dadosPedido.cnpj2, cnpjEdit, dadosPedido.valorSaldo,
					valorDescCalculado, obsPedido, dadosPedido.dataEmbarque, dadosPedido.pedido);
			listPedidosComDesconto.add(pedidos);

			totalDesconto -= valorDescCalculado;
			if (totalDesconto <= 0) break;
		}
	}

	public boolean validarDescontoPedido(float valorPedido, float valorDesconto) {
		boolean aplicaDesconto = false;

		float percentualAplicado = (valorDesconto * 100) / valorPedido;

		if (percentualAplicado <= 25) {
			aplicaDesconto = true;
		}
		return aplicaDesconto;
	}

	public float calcularDescontoMaximoPedido(float valorPedido) {
		float valorCalculado = 0;

		valorCalculado = (valorPedido * 25) / 100;

		return valorCalculado;
	}

	public StatusGravacao aplicarDescontoEspecialPedidos(List<DescontoClientesImportados> listPedidosConfirmados, String usuario, String observacao) {
		PedidosGravadosComDesconto pedidosGravados = null;
		StatusGravacao status = new StatusGravacao(true, "Processo Conluído com Sucesso!");

		for (DescontoClientesImportados dadosPedido : listPedidosConfirmados) {
			ControleDescontoCliente dadosDesconto;
			PedidosGravadosComDesconto pedidosConfirmados;

			int naturezaPedido = comercialCustom.findNaturezaPedido(dadosPedido.pedido);

			int cnpj9 = Integer.parseInt(dadosPedido.cnpjCliente.substring(0,8));
			int cnpj4 = Integer.parseInt(dadosPedido.cnpjCliente.substring(8,12));
			int cnpj2 = Integer.parseInt(dadosPedido.cnpjCliente.substring(12,14));

			dadosDesconto = controleDescontoClienteRepository.findByIdControle(cnpj9 + "-" + cnpj4 + "-" + cnpj2);
			pedidosConfirmados = pedidosGravadosComDescontoRepository.findByIdPedido(dadosPedido.pedido);

			if (pedidosConfirmados != null) {
				status = new StatusGravacao(false, "Pedido " + dadosPedido.pedido + " já foi confirmado! Favor recalcular os descontos!");
				return status;
			}

			if (dadosDesconto.valorDesconto > 0) {
				comercialCustom.atualizarDescontoEspecialPedido(dadosPedido.valor, dadosPedido.observacao, dadosPedido.pedido, observacao);

				// Verifica se o pedido é FRANCHISING
				if (naturezaPedido == 421 || naturezaPedido == 422) {
					dadosPedido.valor = dadosPedido.valor * 2;
				}

				Date dataAtual = new Date();

				pedidosGravados = new PedidosGravadosComDesconto(dadosPedido.pedido, cnpj9, cnpj4, cnpj2, FormataData.parseStringToDate(dadosPedido.dataInsercao), dadosPedido.valor, dadosPedido.observacao, usuario, dataAtual);
				atualizarControleDesconto(cnpj9, cnpj4, cnpj2, dadosPedido.valor);

				pedidosGravadosComDescontoRepository.save(pedidosGravados);
				enviarEmailDescontoPedido(dadosPedido.pedido, dadosPedido.valor);
			}
		}
		return status;
	}

	public void enviarEmailDescontoPedido(int pedido, float valorDesconto) {
		ConsultaEmailClienteCashback dadosCliente = comercialCustom.findEmailPedidoCliente(pedido);

		DecimalFormat formato = new DecimalFormat("0.00");
		String valorFormatado = formato.format(valorDesconto);

		String corpoEmail = FormataString.convertUtf8(" Crédito concedido no pedido ") + dadosCliente.pedidoCliente + FormataString.convertUtf8(" no valor de R$") + valorFormatado + " " + FormataString.convertUtf8("referente ao Cashback + URL.") + " <br/> " + FormataString.convertUtf8("Att, Comercial LIVE!");

		emailService.enviar("Desconto Pedido: " + dadosCliente.pedidoCliente, corpoEmail, dadosCliente.emailCliente);
	}

	public void atualizarControleDesconto(int cnpj9, int cnpj4, int cnpj2, float valorAtual) {
		ControleDescontoCliente controleDesconto = controleDescontoClienteRepository.findByIdControle(cnpj9 + "-" + cnpj4 + "-" + cnpj2);
		if (controleDesconto != null) {
			controleDesconto.valorDesconto -= valorAtual;
			controleDescontoClienteRepository.saveAndFlush(controleDesconto);
		}
	}

	public List<DescontoClientesImportados> buscarHistoricoImportacoes() {
		return comercialCustom.buscarHistoricoImportacoes();
	}

	public List<ConsultaPedidosPorCliente> buscarHistoricoDescontos() {
		return comercialCustom.buscarHistoricoDescontos();
	}

	public List<DescontoClientesImportados> buscarSaldosClientes() {
		return comercialCustom.buscarSaldosClientes();
	}

	public void aplicarSaldosDescontoPedidos(List<ConsultaPedidosPorCliente> pedidosSelect, int cnpj9, int cnpj4, int cnpj2, String usuario, String observacao) {
		ControleDescontoCliente dadosDesconto;
		List<DescontoClientesImportados> listPedidosComDesconto = new ArrayList<>();

		dadosDesconto = controleDescontoClienteRepository.findByIdControle(cnpj9 + "-" + cnpj4 + "-" + cnpj2);

		for (ConsultaPedidosPorCliente dadosPedido : pedidosSelect) {
			float valorDescCalculado = dadosDesconto.valorDesconto / pedidosSelect.size();

			String obsPedido = " Desconto Total de " + valorDescCalculado;

			if (dadosPedido.natureza == 421 || dadosPedido.natureza == 422) {
				valorDescCalculado = valorDescCalculado / 2;
				obsPedido = " Desconto Total de " + valorDescCalculado;
			}

			boolean aplicaDesconto = validarDescontoPedido(dadosPedido.valorSaldo, valorDescCalculado);
			if (!aplicaDesconto) {
				valorDescCalculado = calcularDescontoMaximoPedido(dadosPedido.valorSaldo);
				obsPedido = "Desconto Parcial de " + valorDescCalculado;
			}

			String cnpjEdit = String.format("%08d", dadosPedido.cnpj9) + String.format("%04d",dadosPedido.cnpj4) + String.format("%02d",dadosPedido.cnpj2);

			DescontoClientesImportados pedidos = new DescontoClientesImportados(0,cnpjEdit,valorDescCalculado, FormataData.parseDateToString(dadosPedido.dataEmbarque), obsPedido, dadosPedido.pedido, usuario);
			listPedidosComDesconto.add(pedidos);
		}

		aplicarDescontoEspecialPedidos(listPedidosComDesconto, usuario, observacao);
	}
}
