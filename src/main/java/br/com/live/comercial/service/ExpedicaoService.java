package br.com.live.comercial.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.live.comercial.custom.ExpedicaoCustom;
import br.com.live.comercial.entity.ApontamentoDevolucao;
import br.com.live.comercial.entity.BloqueioTitulosForn;
import br.com.live.comercial.entity.CaixasParaEnderecar;
import br.com.live.comercial.entity.CapacidadeArtigoEndereco;
import br.com.live.comercial.entity.ParametrosMapaEndereco;
import br.com.live.comercial.entity.ParametrosMapaEnderecoCaixa;
import br.com.live.comercial.entity.RegrasPrioridadePedido;
import br.com.live.comercial.entity.VariacaoPesoArtigo;
import br.com.live.comercial.entity.VolumesMinutaTransporte;
import br.com.live.comercial.model.CaixasEsteira;
import br.com.live.comercial.model.CestoEndereco;
import br.com.live.comercial.model.ConsultaCaixasNoEndereco;
import br.com.live.comercial.model.ConsultaCapacidadeArtigosEnderecos;
import br.com.live.comercial.model.ConsultaHistAuditoria;
import br.com.live.comercial.model.ConsultaMinutaTransporte;
import br.com.live.comercial.model.ConsultaNotasTagsDevolucao;
import br.com.live.comercial.model.ConsultaRegraPrioridadeTipoCliente;
import br.com.live.comercial.model.ConsultaTag;
import br.com.live.comercial.model.ConsultaTagsEReferenciasMapa;
import br.com.live.comercial.model.ConsultaTransportadora;
import br.com.live.comercial.model.ConsultaVariacaoArtigo;
import br.com.live.comercial.model.DadosModalEndereco;
import br.com.live.comercial.model.EnderecoCesto;
import br.com.live.comercial.model.EnderecoCount;
import br.com.live.comercial.model.ProdutoEnderecar;
import br.com.live.comercial.model.VolumeMinuta;
import br.com.live.comercial.repository.AberturaCaixasRepository;
import br.com.live.comercial.repository.ApontamentoDevolucaoRepository;
import br.com.live.comercial.repository.CapacidadeArtigoEnderecoRepository;
import br.com.live.comercial.repository.ParametrosEnderecoCaixaRepository;
import br.com.live.comercial.repository.ParametrosMapaEndRepository;
import br.com.live.comercial.repository.RegrasPrioridadePedidoRepository;
import br.com.live.comercial.repository.VariacaoPesoArtigoRepository;
import br.com.live.comercial.repository.VolumesMinutaRepository;
import br.com.live.producao.model.AnaliseQualidade;
import br.com.live.producao.model.DadosTagProd;
import br.com.live.produto.model.Embarque;
import br.com.live.produto.model.Produto;
import br.com.live.sistema.entity.Usuario;
import br.com.live.sistema.repository.UsuarioRepository;

import br.com.live.util.entity.Parametros;
import br.com.live.util.repository.ParametrosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.ConverteLista;
import br.com.live.util.FormataData;
import br.com.live.util.StatusGravacao;
import br.com.live.util.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Transactional
public class ExpedicaoService {
	private final ExpedicaoCustom expedicaoCustom;
	private final ParametrosMapaEndRepository parametrosMapaEndRepository;
	private final CapacidadeArtigoEnderecoRepository capacidadeArtigoEnderecoRepository;
	private final AberturaCaixasRepository aberturaCaixasRepository;
	private final UsuarioRepository usuarioRepository;
	private final ParametrosEnderecoCaixaRepository parametrosEnderecoCaixaRepository;
	private final VariacaoPesoArtigoRepository variacaoPesoArtigoRepository;
	private final ReportService reportService;
	private final VolumesMinutaRepository volumesMinutaRepository;
	private final RegrasPrioridadePedidoRepository regrasPrioridadePedidoRepository;
	private final ApontamentoDevolucaoRepository apontamentoDevolucaoRepository;
	private final ParametrosRepository parametrosRepository;

	public static final int CAIXA_ABERTA = 0;
	public static final int CAIXA_FECHADA = 1;
	public static final int ATACADO = 1;
	public static final int ECOMMERCE = 2;

	public ExpedicaoService(ExpedicaoCustom expedicaoCustom, ParametrosMapaEndRepository parametrosMapaEndRepository,
			CapacidadeArtigoEnderecoRepository capacidadeArtigoEnderecoRepository,
			AberturaCaixasRepository aberturaCaixasRepository, UsuarioRepository usuarioRepository,
			ParametrosEnderecoCaixaRepository parametrosEnderecoCaixaRepository,
			VariacaoPesoArtigoRepository variacaoPesoArtigoRepository, ReportService reportService,
			VolumesMinutaRepository volumesMinutaRepository, RegrasPrioridadePedidoRepository regrasPrioridadePedidoRepository,
			ApontamentoDevolucaoRepository apontamentoDevolucaoRepository, ParametrosRepository parametrosRepository) {
		this.expedicaoCustom = expedicaoCustom;
		this.parametrosMapaEndRepository = parametrosMapaEndRepository;
		this.capacidadeArtigoEnderecoRepository = capacidadeArtigoEnderecoRepository;
		this.aberturaCaixasRepository = aberturaCaixasRepository;
		this.usuarioRepository = usuarioRepository;
		this.parametrosEnderecoCaixaRepository = parametrosEnderecoCaixaRepository;
		this.variacaoPesoArtigoRepository = variacaoPesoArtigoRepository;
		this.reportService = reportService;
		this.volumesMinutaRepository = volumesMinutaRepository;
		this.regrasPrioridadePedidoRepository = regrasPrioridadePedidoRepository;
		this.apontamentoDevolucaoRepository = apontamentoDevolucaoRepository;
		this.parametrosRepository = parametrosRepository;
	}

	public List<EnderecoCount> findEnderecoRef(int codDeposito) {
		return expedicaoCustom.findReferenciaEnd(codDeposito);
	}

	public Embarque findGrupoEmbarque(String numeroTag) {
		int periodo = 0;
		int ordem = 0;
		int pacote = 0;

		periodo = Integer.parseInt(numeroTag.substring(0, 4));
		ordem = Integer.parseInt(numeroTag.substring(4, 13));
		pacote = Integer.parseInt(numeroTag.substring(13, 18));

		return expedicaoCustom.findEmbarque(periodo, ordem, pacote);
	}

	public DadosModalEndereco findDadosModalEnd(int deposito, String endereco) {
		return expedicaoCustom.findDadosModalEndereco(deposito, endereco);
	}

	public String gravarEndereco(String numeroTag, String endereco, int idUsuario) {
		String msgErro = "";
		String existeEndereco = "";
		List<DadosTagProd> listTags = new ArrayList<DadosTagProd>();
		int enderecoCorreto = 0;
		int quantPecas = 0;
		int quantEnderecosParaPeca = 0;
		
		Usuario dadosUsuario = usuarioRepository.findByIdUsuario(idUsuario);
		
		if (numeroTag.length() < 22) {
			int numeroCaixa = Integer.parseInt(numeroTag);

			int temProduto = expedicaoCustom.validarExistePecaCaixa(numeroCaixa);
			
			if (temProduto == 0) {
				msgErro = "Caixa não passou pelo novo processo de endereçamento! Será necessário endereçar peça a peça!";
			} else {
				listTags = expedicaoCustom.obterTagsLidosCaixa(numeroCaixa);

				for (DadosTagProd dadosTag : listTags) {
					quantEnderecosParaPeca = expedicaoCustom.validarSeExistemEnderecosParaTag(dadosTag.periodo, dadosTag.ordem, dadosTag.pacote, dadosTag.sequencia);
					enderecoCorreto = expedicaoCustom.validarEnderecoCorreto(dadosTag.periodo, dadosTag.ordem, dadosTag.pacote, dadosTag.sequencia, endereco);
					if (enderecoCorreto != 1 && quantEnderecosParaPeca > 0) {
						msgErro = "Estes TAG's não pertencem ao endereço informado!";
						break;
					}
					expedicaoCustom.gravarEnderecos(dadosTag.periodo, dadosTag.ordem, dadosTag.pacote,
							dadosTag.sequencia, endereco, dadosUsuario.usuarioSystextil);
					insertProductInEstq110(dadosTag.periodo, dadosTag.ordem, dadosTag.pacote, dadosTag.sequencia, endereco);
				}
				
				if (msgErro.equalsIgnoreCase("")) expedicaoCustom.limparCaixa(numeroCaixa);
			}
		} else {
			int periodo = Integer.parseInt(numeroTag.substring(0, 4));
			int ordem = Integer.parseInt(numeroTag.substring(4, 13));
			int pacote = Integer.parseInt(numeroTag.substring(13, 18));
			int sequencia = Integer.parseInt(numeroTag.substring(18, 22));
			
			int flagEmEstoque = expedicaoCustom.validarPecaEmEstoque(periodo, ordem, pacote, sequencia);
			existeEndereco = expedicaoCustom.validarGravacaoEndereco(periodo, ordem, pacote, sequencia);

			if (flagEmEstoque == 1) {
				msgErro = "Situação do TAG diferente de 1 (Em Estoque)!";
				return msgErro;
			}

			if ((existeEndereco != null) && (!existeEndereco.equals("")) && (!existeEndereco.equals("ENDERECAR"))) {
				msgErro = existeEndereco;
			}
			
			if (retornaListaLetraNumero(endereco.substring(0, 1)) < 6) {
				enderecoCorreto = expedicaoCustom.validarEnderecoCorreto(periodo, ordem, pacote, sequencia, endereco);
				quantPecas = expedicaoCustom.verificarPecasNoEndereco(endereco);

				if (enderecoCorreto != 1 && quantPecas > 0) {
					msgErro = "Este TAG não pertence ao endereço informado!";
				}
			}

			if (msgErro.equals("")) {
				expedicaoCustom.gravarEnderecos(periodo, ordem, pacote, sequencia, endereco, dadosUsuario.usuarioSystextil);
				insertProductInEstq110(periodo, ordem, pacote, sequencia, endereco);
				expedicaoCustom.limparTagLidoCaixa(numeroTag);
			}
		}

		return msgErro;
	}

	public void salvarParametros(int deposito, String blocoInicio, String blocoFim, int corredorInicio, int corredorFim,
			int boxInicio, int boxFim, int cestoInicio, int cestoFim) {

		ParametrosMapaEndereco dadosParam;

		dadosParam = parametrosMapaEndRepository.findByDeposito(deposito);

		if (dadosParam == null) {
			dadosParam = new ParametrosMapaEndereco(deposito, blocoInicio, blocoFim, corredorInicio, corredorFim,
					boxInicio, boxFim, cestoInicio, cestoFim);
		} else {
			dadosParam.blocoInicio = blocoInicio;
			dadosParam.blocoFim = blocoFim;
			dadosParam.corredorInicio = corredorInicio;
			dadosParam.corredorFim = corredorFim;
			dadosParam.boxInicio = boxInicio;
			dadosParam.boxFim = boxFim;
			dadosParam.cestoInicio = cestoInicio;
			dadosParam.cestoFim = cestoFim;
		}
		parametrosMapaEndRepository.save(dadosParam);
	}

	public List<String> gerarEnderecosDinamicos(int deposito) {
		ParametrosMapaEndereco dadosParam = parametrosMapaEndRepository.findByDeposito(deposito);

		List<String> enderecos = new ArrayList<String>();

		for (int blocoAtual = retornaListaLetraNumero(dadosParam.blocoInicio); blocoAtual <= retornaListaLetraNumero(
				dadosParam.blocoFim); blocoAtual++) {
			for (int corredorAtual = dadosParam.corredorInicio; corredorAtual <= dadosParam.corredorFim; corredorAtual++) {
				for (int boxAtual = dadosParam.boxInicio; boxAtual <= dadosParam.boxFim; boxAtual++) {
					for (int cestoAtual = dadosParam.cestoInicio; cestoAtual <= dadosParam.cestoFim; cestoAtual++) {
						String endereco = "";

						endereco = retornaListaNumeroLetra(blocoAtual) + String.format("%02d", corredorAtual)
								+ String.format("%02d", boxAtual) + String.format("%02d", cestoAtual);

						enderecos.add(endereco);
					}
				}
			}
		}
		return enderecos;
	}

	public void gravarEnderecosDeposito(int deposito) {
		expedicaoCustom.cleanEnderecos(deposito);

		List<String> enderecos = gerarEnderecosDinamicos(deposito);

		for (String endereco : enderecos) {
			expedicaoCustom.inserirEnderecosDeposito(deposito, endereco, "0", "00000", "000", "000000");
		}
	}

	public int retornaListaLetraNumero(String valorEntrada) {
		Map<String, Integer> letraParaInteger = new HashMap<String, Integer>();

		letraParaInteger.put("A", 1);
		letraParaInteger.put("B", 2);
		letraParaInteger.put("C", 3);
		letraParaInteger.put("D", 4);
		letraParaInteger.put("E", 5);
		letraParaInteger.put("F", 6);
		letraParaInteger.put("G", 7);
		letraParaInteger.put("H", 8);
		letraParaInteger.put("I", 9);
		letraParaInteger.put("J", 10);
		letraParaInteger.put("K", 11);
		letraParaInteger.put("L", 12);
		letraParaInteger.put("M", 13);
		letraParaInteger.put("N", 14);
		letraParaInteger.put("O", 15);
		letraParaInteger.put("P", 16);
		letraParaInteger.put("Q", 17);
		letraParaInteger.put("R", 18);
		letraParaInteger.put("S", 19);
		letraParaInteger.put("T", 20);
		letraParaInteger.put("U", 21);
		letraParaInteger.put("V", 22);
		letraParaInteger.put("W", 23);
		letraParaInteger.put("X", 24);
		letraParaInteger.put("Y", 25);
		letraParaInteger.put("Z", 26);

		int valorRetorno = 0;

		valorRetorno = letraParaInteger.get(valorEntrada);

		return valorRetorno;
	}

	public String retornaListaNumeroLetra(int valorEntrada) {
		Map<Integer, String> integerParaLetra = new HashMap<Integer, String>();

		integerParaLetra.put(1, "A");
		integerParaLetra.put(2, "B");
		integerParaLetra.put(3, "C");
		integerParaLetra.put(4, "D");
		integerParaLetra.put(5, "E");
		integerParaLetra.put(6, "F");
		integerParaLetra.put(7, "G");
		integerParaLetra.put(8, "H");
		integerParaLetra.put(9, "I");
		integerParaLetra.put(10, "J");
		integerParaLetra.put(11, "K");
		integerParaLetra.put(12, "L");
		integerParaLetra.put(13, "M");
		integerParaLetra.put(14, "N");
		integerParaLetra.put(15, "O");
		integerParaLetra.put(16, "P");
		integerParaLetra.put(17, "Q");
		integerParaLetra.put(18, "R");
		integerParaLetra.put(19, "S");
		integerParaLetra.put(20, "T");
		integerParaLetra.put(21, "U");
		integerParaLetra.put(22, "V");
		integerParaLetra.put(23, "W");
		integerParaLetra.put(24, "X");
		integerParaLetra.put(25, "Y");
		integerParaLetra.put(26, "Z");

		String valorRetorno = "";

		valorRetorno = integerParaLetra.get(valorEntrada);

		return valorRetorno;
	}

	public void gravarCapacidades(List<ConsultaCapacidadeArtigosEnderecos> itens) {
		for (ConsultaCapacidadeArtigosEnderecos dadosCapacidade : itens) {
			CapacidadeArtigoEndereco dadosArtigo = null;
			dadosArtigo = capacidadeArtigoEnderecoRepository.findByArtigo(dadosCapacidade.artigo);
			if (dadosArtigo == null) {
				dadosArtigo = new CapacidadeArtigoEndereco(dadosCapacidade.artigo, dadosCapacidade.quantPecCesto,
						dadosCapacidade.perc0, dadosCapacidade.perc1, dadosCapacidade.perc40, dadosCapacidade.perc41,
						dadosCapacidade.perc94, dadosCapacidade.perc95, dadosCapacidade.perc99);
			} else {
				dadosArtigo.qtdePecasCesto = dadosCapacidade.quantPecCesto;
				dadosArtigo.qtdePerc0 = dadosCapacidade.perc0;
				dadosArtigo.qtdePerc1 = dadosCapacidade.perc1;
				dadosArtigo.qtdePerc40 = dadosCapacidade.perc40;
				dadosArtigo.qtdePerc41 = dadosCapacidade.perc41;
				dadosArtigo.qtdePerc94 = dadosCapacidade.perc94;
				dadosArtigo.qtdePerc95 = dadosCapacidade.perc95;
				dadosArtigo.qtdePerc99 = dadosCapacidade.perc99;
			}
			capacidadeArtigoEnderecoRepository.save(dadosArtigo);
		}
	}

	public String abrirCaixa(int codCaixa, int codUsuario, int tipoCaixa) {
		String msgErro = "";
		int numeroCaixa = 0;

		numeroCaixa = expedicaoCustom.validarCaixaAberta(codUsuario);

		if (numeroCaixa == codCaixa)
			return msgErro;

		if (numeroCaixa > 1) {
			msgErro = " Não é possível abrir uma nova Caixa! feche a caixa " + numeroCaixa + " para continuar!";
			return msgErro;
		}

		CaixasParaEnderecar dadosAbertura;

		Date dataAtual = new Date();
		Date dataFinal = null;

		Usuario dadosUsuario = usuarioRepository.findByIdUsuario(codUsuario);

		dadosAbertura = new CaixasParaEnderecar(codCaixa, 0, codUsuario, dataAtual, dataFinal,
				dadosUsuario.usuarioSystextil, "", 0, tipoCaixa);

		aberturaCaixasRepository.save(dadosAbertura);

		return msgErro;
	}

	public void fecharCaixa(int codCaixa) {
		CaixasParaEnderecar dadosAbertura = aberturaCaixasRepository.findByNumeroCaixa(codCaixa);

		if (dadosAbertura.situacaoCaixa != 1) {

			Date dataHoraFim = new Date();

			dadosAbertura.dataHoraFim = dataHoraFim;
			dadosAbertura.situacaoCaixa = 1;

			aberturaCaixasRepository.save(dadosAbertura);

			List<DadosTagProd> dadosCaixasTag = expedicaoCustom.findDadosTagCaixas(codCaixa);

			for (DadosTagProd tagLido : dadosCaixasTag) {
				expedicaoCustom.atualizarSituacaoEndereco(tagLido.periodo, tagLido.ordem, tagLido.pacote,
						tagLido.sequencia);
			}
		}
	}

	public String gravarEnderecoCaixa(int numeroCaixa, String endereco) {
		String msgErro = "";
		int quantEndereco = 0;

		CaixasParaEnderecar dadosCaixa = aberturaCaixasRepository.findByNumeroCaixa(numeroCaixa);

		if (dadosCaixa.endereco != null) {
			if (dadosCaixa.endereco.equalsIgnoreCase(endereco)) {
				dadosCaixa.endereco = "";
				msgErro = "Removido o endereço da caixa " + numeroCaixa;
				return msgErro;
			}
		}

		Parametros params = parametrosRepository.findByIdParametro("QTDE_CAIXAS_EMPILHAR_PRE_ENDERECAMENTO");
		int quantMaxEmpilhamento = params.valorInt;

		quantEndereco = expedicaoCustom.findQuantidadeCaixasNoeEndereco(endereco);
		if (quantEndereco > quantMaxEmpilhamento) {
			msgErro = "Não é permitido colocar mais de " + quantMaxEmpilhamento + " caixas no endereço! ";
			return msgErro;
		}

		if (dadosCaixa.situacaoCaixa == CAIXA_FECHADA) {
			dadosCaixa.endereco = endereco;
		} else {
			msgErro = "Não foi possível endereçar a caixa " + numeroCaixa;
		}

		aberturaCaixasRepository.save(dadosCaixa);

		return msgErro;
	}

	public List<ProdutoEnderecar> findProdutosEnderecarCaixa(int codCaixa) {
		List<ProdutoEnderecar> produtos = expedicaoCustom.findProdutosEnderecar(codCaixa);

		for (ProdutoEnderecar produto : produtos) {
			produto.endereco = ProdutoEnderecar.ENDERECO_INDISPONIVEL;

			CestoEndereco cesto = expedicaoCustom.findEnderecoCesto(produto.nivel, produto.referencia, produto.tamanho,
					produto.cor, produto.deposito);

			if (cesto != null) {
				if ((cesto.qtdeCapacidade - cesto.qtdeOcupado) > 0)
					produto.endereco = cesto.endereco;
			}
		}

		return produtos;
	}

	public void salvarDadosFacilitador(List<Produto> referencias, int deposito, String bloco, int corredor,
			int boxInicio, int boxFim, List<String> produtosSel) {
		List<EnderecoCesto> enderecos = null;
		List<Produto> newProdutos = new ArrayList<Produto>();

		String referenciasSel = ConverteLista.converteListStrToStr(produtosSel);
		List<Produto> listRefSel = expedicaoCustom.ordenarReferencias(referenciasSel);

		newProdutos = filtrarListaSelecionados(referencias, listRefSel);
		List<Produto> produtos = converteListaDeProdutos(newProdutos);

		for (int boxAtual = boxInicio; boxAtual <= boxFim; boxAtual++) {
			int parImpar = boxAtual % 2;

			enderecos = expedicaoCustom.findCestosLivres(deposito, bloco, corredor, boxAtual, parImpar);

			for (EnderecoCesto endereco : enderecos) {
				if (produtos.size() > 0) {
					Produto dadosItem = produtos.get(0);

					String[] prodConcat = dadosItem.id.split("[.]");

					String grupo = prodConcat[0];
					String subGrupo = prodConcat[2];
					String item = prodConcat[1];

					expedicaoCustom.updateEnderecosDeposito(deposito, endereco.endereco, "1", grupo, subGrupo, item);

					produtos.remove(0);
				}
			}
		}
	}

	public List<Produto> converteListaDeProdutos(List<Produto> referencias) {
		List<Produto> newProd = new ArrayList<Produto>();

		for (Produto dadosReferencia : referencias) {
			for (int i = 0; i < dadosReferencia.quantCesto; i++) {
				newProd.add(dadosReferencia);
			}
		}
		return newProd;
	}

	public List<Produto> filtrarListaSelecionados(List<Produto> referencias, List<Produto> produtosSel) {
		List<Produto> newProdutos = new ArrayList<Produto>();

		for (Produto produtoSel : produtosSel) {

			Stream<Produto> stream = referencias.stream().filter(t -> t.grupo.equals(produtoSel.grupo)
					&& t.sub.equals(produtoSel.sub) && t.item.equals(produtoSel.item));
			List<Produto> produtosFiltrados = stream.collect(Collectors.toList());

			newProdutos.add(produtosFiltrados.get(0));
		}
		return newProdutos;
	}

	public String converteListaProduto(List<Produto> produtos) {
		String stringProd = "";

		for (Produto dadosProd : produtos) {
			if (stringProd.equalsIgnoreCase("")) {
				stringProd = "'" + dadosProd.id + "'";
			} else {
				stringProd += ", " + "'" + dadosProd.id + "'";
			}
		}
		return stringProd;
	}

	public List<ConsultaCaixasNoEndereco> consultaCaixasNoEndereco(String endereco) {
		return expedicaoCustom.findCaixas(endereco);
	}

	public void salvarParametrosEnderecoCaixa(int deposito, String ruaInicio, String ruaFim, int boxInicio,
			int boxFim) {
		ParametrosMapaEnderecoCaixa parametros = parametrosEnderecoCaixaRepository.findByDeposito(deposito);

		if (parametros == null) {
			parametros = new ParametrosMapaEnderecoCaixa(deposito, ruaInicio, ruaFim, boxInicio, boxFim);
		} else {
			parametros.ruaInicio = ruaInicio;
			parametros.ruaFim = ruaFim;
			parametros.boxInicio = boxInicio;
			parametros.boxFim = boxFim;
		}
		parametrosEnderecoCaixaRepository.save(parametros);
	}

	public List<ConsultaCaixasNoEndereco> verificarCaixasNoEndereco() {
		return expedicaoCustom.verificaCaixasNoEndereco();
	}

	public List<ConsultaTag> findQuantEnderecos(String nivel, String grupo, String subGrupo, String item,
			int deposito) {
		return expedicaoCustom.obterEnderecos(deposito, nivel, grupo, subGrupo, item);
	}

	public String findProdutoByTag(String numeroTag) {
		int periodo = Integer.parseInt(numeroTag.substring(0, 4));
		int ordem = Integer.parseInt(numeroTag.substring(4, 13));
		int pacote = Integer.parseInt(numeroTag.substring(13, 18));
		int sequencia = Integer.parseInt(numeroTag.substring(18, 22));

		return expedicaoCustom.findProdutoByTag(periodo, ordem, pacote, sequencia);
	}

	public List<ConsultaTag> findHistoricoTag(String numeroTag) {
		int periodo = Integer.parseInt(numeroTag.substring(0, 4));
		int ordem = Integer.parseInt(numeroTag.substring(4, 13));
		int pacote = Integer.parseInt(numeroTag.substring(13, 18));
		int sequencia = Integer.parseInt(numeroTag.substring(18, 22));

		return expedicaoCustom.findHistoricoTag(periodo, ordem, pacote, sequencia);
	}

	public void deleteVariacaoById(long idVariacao) {
		variacaoPesoArtigoRepository.deleteById(idVariacao);
	}

	public List<ConsultaVariacaoArtigo> findVaricaoArtigo() {
		return expedicaoCustom.findVariacaoArtigo();
	}

	public void saveVariacaoPesoArtigo(List<ConsultaVariacaoArtigo> variacoes) {

		VariacaoPesoArtigo variacaoSave;

		for (ConsultaVariacaoArtigo variacao : variacoes) {
			if (variacao.variacao > 0) {
				variacaoSave = variacaoPesoArtigoRepository.findByIdVariacao(variacao.id);

				if (variacaoSave != null) {
					variacaoSave.variacao = variacao.variacao;
				} else {
					variacaoSave = new VariacaoPesoArtigo(variacao.id, variacao.variacao);
				}
				variacaoPesoArtigoRepository.save(variacaoSave);
			}
		}
	}
	
	public Map<String, Object> setParameters(String transportadora, int minuta) {
		ConsultaTransportadora transport = expedicaoCustom.findDadosTransportadora(transportadora);
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("nome", transport.nome);
		parameters.put("endereco", transport.endereco);
		parameters.put("bairro", transport.bairro);
		parameters.put("complemento", transport.complemento);
		parameters.put("cep", transport.cep);
		parameters.put("estado", transport.estado);
		parameters.put("cidade", transport.cidade);
		parameters.put("numMinuta", minuta);
		
		return parameters;
	}

	private int gravarVolumesMinutaAtacado(List<ConsultaMinutaTransporte> notasSelecionadas, int tipoMinuta, String transportadora, String usuario) {
		List<Integer> volumesPedido = new ArrayList<>();
		VolumesMinutaTransporte volumesMinutaSave = null;
		int notaEmpresa1 = 0;
		int minuta = expedicaoCustom.findNextMinuta();

		for (ConsultaMinutaTransporte dadosMinuta : notasSelecionadas) {

			if (tipoMinuta == ATACADO && dadosMinuta.empresa == 100) {
				notaEmpresa1 = expedicaoCustom.retornaNumeroNotaEmpresa1(dadosMinuta.nota);
			} else {
				notaEmpresa1 = dadosMinuta.nota;
			}

			volumesPedido = expedicaoCustom.findVolumesPedido(dadosMinuta.pedido, notaEmpresa1);

			for (Integer volume : volumesPedido) {
				volumesMinutaSave = new VolumesMinutaTransporte(expedicaoCustom.findNextIdVolumesMinuta(),volume, dadosMinuta.pedido,dadosMinuta.nota,
						Integer.toString(dadosMinuta.serie), dadosMinuta.cliente, dadosMinuta.libPaypal, dadosMinuta.pesoBruto, dadosMinuta.valorNota, minuta,
						new Date(), tipoMinuta, transportadora, dadosMinuta.cidade, dadosMinuta.estado, usuario);
				volumesMinutaRepository.saveAndFlush(volumesMinutaSave);
			}
		}
		return minuta;
	}

	public String gerarMinutaTransporteAtacado(List<ConsultaMinutaTransporte> notasSelecionadas, String transportadora, String usuario) throws FileNotFoundException, JRException {
		String nomeRelatorioGerado = "";
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(notasSelecionadas);

		int minuta = gravarVolumesMinutaAtacado(notasSelecionadas, ATACADO, transportadora, usuario);

		Map<String, Object> parameters = setParameters(transportadora, minuta);

		nomeRelatorioGerado = reportService.generateReport("pdf", dataSource, "minuta_transporte", parameters, Integer.toString(minuta), false);

		return nomeRelatorioGerado;
	}

	public String reemitirMinutaTransporte(int numeroMinuta) throws JRException, FileNotFoundException {
		List<ConsultaMinutaTransporte> listMinutas = expedicaoCustom.findMinutaReimpressao(numeroMinuta);
		String transportadora = expedicaoCustom.obterTransportadoraMinutaGerada(numeroMinuta);

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listMinutas);
		Map<String, Object> parameters = setParameters(transportadora, numeroMinuta);

		return reportService.generateReport("pdf", dataSource, "minuta_transporte", parameters, Integer.toString(numeroMinuta), false);
	}
	
	public String gerarMinutaTransporteEcommerce(List<ConsultaMinutaTransporte> notasSelecionadas, String transportadora, String usuario) throws FileNotFoundException, JRException {
		String nomeRelatorioGerado = "";

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(notasSelecionadas);

		int minuta = gravarVolumesMinutaAtacado(notasSelecionadas, ECOMMERCE, transportadora, usuario);

		Map<String, Object> parameters = setParameters(transportadora, minuta);

		nomeRelatorioGerado = reportService.generateReport("pdf", dataSource, "minuta_transporte", parameters, Integer.toString(minuta), false);

		return nomeRelatorioGerado;
	}
	
	public void changeAllocationTAG(String tagNumber, String newAllocation, int idUsuario) {
		int periodo = Integer.parseInt(tagNumber.substring(0, 4));
		int ordem = Integer.parseInt(tagNumber.substring(4, 13));
		int pacote = Integer.parseInt(tagNumber.substring(13, 18));
		int sequencia = Integer.parseInt(tagNumber.substring(18, 22));
		
		Usuario dadosUsuario = usuarioRepository.findByIdUsuario(idUsuario);
		String block = newAllocation.substring(0, 1);

		expedicaoCustom.changeEnderecoTAG(periodo, ordem, pacote, sequencia, newAllocation, dadosUsuario.usuarioSystextil);
		insertProductInEstq110(periodo, ordem, pacote, sequencia, newAllocation);
	}
	
	public int showQuantPartAllocation(String allocation) {
		return expedicaoCustom.showCountPartsAllocation(allocation);
	}
	
	public int salvarlog(String allocation) {
		return expedicaoCustom.showCountPartsAllocation(allocation);
	}
	
	public void clearAllocation(String allocation, String usuarioSystextil) {
		int deposito = 0;

		String bloco = allocation.substring(0, 1);

		if (retornaListaLetraNumero(bloco) >= 6) {
			deposito = 111;
		} else {
			deposito = 4;
		}

		expedicaoCustom.clearAllocationEstq110(allocation, deposito);
		expedicaoCustom.clearAllocation(allocation, usuarioSystextil);
	}
	
	public void clearMultiAllocation(String allocation, int deposito) {
		expedicaoCustom.clearAllocationEstq110(allocation, deposito);
	}
	
	public void clearMultiAllocations(List<String> allocations, String usuarioSystextil) {
		int deposito = 0;
		
		for (String allocation : allocations) {
			String bloco = allocation.substring(0, 1);
			
			if (retornaListaLetraNumero(bloco) >= 6) {
				deposito = 111;
			} else {
				deposito = 4;
			}
			clearAllocation(allocation, usuarioSystextil);
			clearMultiAllocation(allocation, deposito);
		}
	}
	
	public StatusGravacao validateWarehouse(String allocation, String tagNumber) {
		StatusGravacao status = new StatusGravacao(true, "Validado Com Sucesso!");
		int allocationWarehouse = 0;
		
		if ( tagNumber.length() >= 22) {
			int period = Integer.parseInt(tagNumber.substring(0, 4));
			int order = Integer.parseInt(tagNumber.substring(4, 13));
			int orderPackage = Integer.parseInt(tagNumber.substring(13, 18));
			int sequence = Integer.parseInt(tagNumber.substring(18, 22));
			
			int tagWarehouse = expedicaoCustom.findWarehouseTAG(period, order, orderPackage, sequence);
			int situacaoTag = expedicaoCustom.findSituacaoTAG(period, order, orderPackage, sequence);
			
			if (situacaoTag == 4) {
				status = new StatusGravacao(false, "Este TAG já está faturado! não é possível endereçar!");
				return status;
			}
			
			String block = allocation.substring(0, 1);
			
			if (retornaListaLetraNumero(block) >= 6) {
				allocationWarehouse = 111;
			} else {
				allocationWarehouse = 4;
			}
			
			if (tagWarehouse != allocationWarehouse) {
				status = new StatusGravacao(false, "Depósito do TAG diferente do depósito do endereço!");
			}
		}
		return status;
	}
	
	public void insertProductInEstq110(int period, int order, int orderPackage, int sequence, String endereco) {
		ConsultaTag dataTag = expedicaoCustom.findDataTAGNumber(period, order, orderPackage, sequence);
		expedicaoCustom.insertProductInEstq110(dataTag.nivel, dataTag.grupo, dataTag.subGrupo, dataTag.item, dataTag.deposito, endereco);
	}
	
	public StatusGravacao allocateBox(String allocation, int volume, int notaFiscal, String chaveNfe) {
		StatusGravacao status = null;
		
		String volumeComp = "" + volume + "";
		String volumeEdit = volumeComp.substring(0, 7);
		
		status = validateVolume(Integer.parseInt(volumeEdit), notaFiscal, allocation, chaveNfe);
		
		if (status == null) {
			expedicaoCustom.updateBox(Integer.parseInt(volumeEdit), allocation);
			status = new StatusGravacao(true, "Endereçado Com Sucesso!");
		}
		return status;
	}
	
	public StatusGravacao validateVolume(int volume, int notaFiscal, String endereco, String chaveNFe) {
		StatusGravacao status = null;
		int notaFiscalVolume = 0;
		int openOrClose = 0;
		String transpNota = "";
		String transpEndereco = "";
		
		transpEndereco = expedicaoCustom.findTransportadoraEndereco(endereco);
		transpNota = expedicaoCustom.findTransportadoraNotaFiscal(chaveNFe);
		
		openOrClose = expedicaoCustom.validateOrdered(volume);
		if (openOrClose != 1) {
			status = new StatusGravacao(false, "Pedido do volume " + volume + " não foi faturado!", 2);
		}
		notaFiscalVolume = expedicaoCustom.validateNotaFiscall(volume, notaFiscal);
		if (notaFiscalVolume != 1) {
			status = new StatusGravacao(false, "Nota fiscal informada é diferente da Nota fiscal do volume " + volume + "!", 3);
		}
		if ((transpEndereco != null) && (!transpEndereco.equalsIgnoreCase(transpNota))) {
			status = new StatusGravacao(false, "Transportadora da nota fiscal " + notaFiscal + " é diferente da transportadora do endereço " + endereco + "!", 4);
		}
		return status;
	}
	
	public void cleanAllocationVolume(int volume) {
		expedicaoCustom.cleanAllocationVolume(volume);
	}
	
	public StatusGravacao validateVolumeEnderecado(int volume) {
		StatusGravacao status = new StatusGravacao(true, "");
		String volumeAllocated = "";

		String volumeComp = "" + volume + "";
		String volumeEdit = volumeComp.substring(0, 7);

		volumeAllocated = expedicaoCustom.validateVolumeIsAllocated(Integer.parseInt(volumeEdit));
		if (!volumeAllocated.equalsIgnoreCase("")) {
			status = new StatusGravacao(false, "Volume " + volume + " já está endereçado! Deseja limpar o endereço " + volumeAllocated + "?", 1);
		}
		return status;
	}

	public void gravarAuditoria(int codRua, int volume) {
		if (codRua > 0 && volume > 0) {
			expedicaoCustom.gravarAuditoria(codRua, volume);
		}
	}

	public List<ConsultaHistAuditoria> consultaHistoricoAuditoria(String dataInicio, String dataFim) {
		return expedicaoCustom.findAuditoriaByDate(dataInicio, dataFim);
	}

	public void deleteByNota(List<Integer> notasSelect) {
		for (Integer nota : notasSelect) {
			volumesMinutaRepository.deleteByNota(nota);
		}
	}

	public StatusGravacao validarVolumesMinuta(int minuta) {
		StatusGravacao status = new StatusGravacao(true, "Existem volumes a serem lidos", 0);;

		int statusLocalCaixa = expedicaoCustom.validarVolumesMinuta(minuta);
		if (statusLocalCaixa == 3) {
			status = new StatusGravacao(true, "Todos os Volumes da caixa estão com local da caixa 3", statusLocalCaixa);
		} else if (statusLocalCaixa == 7) {
			status = new StatusGravacao(true, "Todos os Volumes da caixa estão com local da caixa 7", statusLocalCaixa);
		} else if (statusLocalCaixa == 9) {
			status = new StatusGravacao(true, "Todos os Volumes da caixa estão com local da caixa 9", statusLocalCaixa);
		}
		return status;
	}

	public StatusGravacao alterarLocalCaixaVolume(int minuta, int volume, int localCaixa) {
		StatusGravacao status = new StatusGravacao(true, "");
		int localCaixaVolume = 0;
		int volumeDevolucao = 0;
		int volumeCancelado = 0;

		String volumeComp = "" + volume + "";
		String volumeEdit = volumeComp.substring(0, 7);

		int volumeDentroDaMinuta = expedicaoCustom.validarVolumeDentroMinuta(minuta, Integer.parseInt(volumeEdit));
		if (volumeDentroDaMinuta == 1) {

			localCaixaVolume = expedicaoCustom.obterLocalCaixaVolume(Integer.parseInt(volumeEdit));
			volumeDevolucao = expedicaoCustom.validaVolumeDevolucao(Integer.parseInt(volumeEdit));
			volumeCancelado = expedicaoCustom.validaVolumeCancelado(Integer.parseInt(volumeEdit));

			if (volumeCancelado > 0) {
				status = new StatusGravacao(false, "ATENÇÃO! Caixa cancelada!");
				return status;
			}

			if (volumeDevolucao != 0) {
				status = new StatusGravacao(false, "ATENÇÃO! Caixa marcada para devolução!");
				return status;
			}

			if (localCaixaVolume == localCaixa) {
				status = new StatusGravacao(false, "Volume já esta com local da caixa " + localCaixa + "" +
						"!");
			} else {
				expedicaoCustom.alterarLocalCaixaVolume(Integer.parseInt(volumeEdit), localCaixa);
			}
		} else {
			status = new StatusGravacao(false, "Volume não pertence a minuta informada!");
		}
		return status;
	}

	public List<CaixasEsteira> obterListaCaixasDisponiveis(int statusCaixa) {
		List<CaixasEsteira> listCaixas = new ArrayList<CaixasEsteira>();
		CaixasEsteira dadosCaixa = null;

		List<Integer> caixas = expedicaoCustom.obterCaixasNaEsteira(statusCaixa);
		for (Integer caixa : caixas) {
			String area = expedicaoCustom.obterAreaCaixa(caixa);
			int quantidade = expedicaoCustom.obterQuantidadePendente(caixa);

			dadosCaixa = new CaixasEsteira(caixa, area, quantidade);
			listCaixas.add(dadosCaixa);
		}
		return listCaixas;
	}

	public List<CaixasEsteira> obterListaCaixasSortidas() {
		return expedicaoCustom.obterCaixasSortidas();
	}

	public void colocarCaixaEsteira(int numeroCaixa) {
		CaixasParaEnderecar dadosCaixa = aberturaCaixasRepository.findByNumeroCaixa(numeroCaixa);

		dadosCaixa.caixaNaEsteira = 1;

		aberturaCaixasRepository.save(dadosCaixa);
	}

	public void retirarCaixaEsteira(int numeroCaixa) {
		CaixasParaEnderecar dadosCaixa = aberturaCaixasRepository.findByNumeroCaixa(numeroCaixa);

		dadosCaixa.caixaNaEsteira = 0;

		aberturaCaixasRepository.save(dadosCaixa);
	}

	public void salvarRegraPrioridadeTipoClientePedido(int tipoCliente, int prioridade) {

		RegrasPrioridadePedido dadosRegra = regrasPrioridadePedidoRepository.findByTipoCliente(tipoCliente);

		if (dadosRegra == null) {
			dadosRegra = new RegrasPrioridadePedido(tipoCliente, prioridade);
		} else {
			dadosRegra.prioridade = prioridade;
		}
		regrasPrioridadePedidoRepository.save(dadosRegra);
	}

	public void deleteRegraPrioridadeTipoClientePedido(int tipoCliente) {
		regrasPrioridadePedidoRepository.deleteByTipoCliente(tipoCliente);
	}

	public List<ConsultaRegraPrioridadeTipoCliente> findAllRegraTipoCliente() {
		return expedicaoCustom.findAllRegrasTipoClientePedido();
	}

	public void limparCaixa(int codCaixa) {
		expedicaoCustom.limparCaixa(codCaixa);
	}

	public List<VolumeMinuta> findVolumesByNotaPedido(int notaFiscal, int pedido) {
		int notaEmpresa1 = expedicaoCustom.retornaNumeroNotaEmpresa1(notaFiscal);

		if (notaEmpresa1 > 0) {
			notaFiscal = notaEmpresa1;
		}
		return expedicaoCustom.findVolumesMinutaPorNotaPedido(notaFiscal, pedido);
	}

	public List<ConsultaMinutaTransporte> consultaRelatorioMinutas(int minuta, String dataInicio, String dataFim) {
		List<ConsultaMinutaTransporte> listMinutas = new ArrayList<>();
		List<ConsultaMinutaTransporte> listMinutasGrid = new ArrayList<>();

		listMinutas = expedicaoCustom.consultaRelatorioMinutas(minuta,dataInicio,dataFim);

		for (ConsultaMinutaTransporte dadosMinuta : listMinutas) {
			int notaFiscal = 0;
			notaFiscal = dadosMinuta.nota;

			int notaEmpresa1 = expedicaoCustom.retornaNumeroNotaEmpresa1(notaFiscal);
			if (notaEmpresa1 > 0) {
				notaFiscal = notaEmpresa1;
			}

			dadosMinuta.status = expedicaoCustom.verificaTodosVolumesAlocadosByNotaAndPedido(notaFiscal, dadosMinuta.pedido);
			listMinutasGrid.add(dadosMinuta);
		}

		return listMinutasGrid;
	}
	
	public List<ConteudoChaveNumerica> findMotivosDevolucao(){
		return expedicaoCustom.findMotivosDevolucao();
	}
	
	public int findQtdePecasLidasByUsuarioNf(int usuario){
		return expedicaoCustom.findQtdePecasLidasByUsuarioNf(usuario);
	}
	
	public boolean saveTagDevolucao(String usuario, int nfDevolucao, int tipoDevolucao, int motivo, int transacao, int codCaixa, 
			String codBarrasTag) {
		
		boolean resultado = false;
		ApontamentoDevolucao dados = null;
		String sysdate = expedicaoCustom.findDataHora();
		String[] dataConcat = sysdate.split("[-]");
		String data = dataConcat[0];
		String hora = dataConcat[1];
		int deposito = 0;
		
		try {
			if(tipoDevolucao == 2) {
				deposito = 211;
				expedicaoCustom.updateDepositoByTag(codBarrasTag, transacao, deposito);
			}
			if(tipoDevolucao == 3) {
				deposito = 6;
				expedicaoCustom.updateDepositoByTag(codBarrasTag, transacao, deposito);
			}
			dados = new ApontamentoDevolucao(apontamentoDevolucaoRepository.findNextID(), FormataData.parseStringToDate(data), hora, usuario, nfDevolucao, tipoDevolucao,
					motivo, transacao, codCaixa, codBarrasTag);
			apontamentoDevolucaoRepository.save(dados);
			resultado = true;
		} catch (Exception e) {
			resultado = false;
		}
		
		return resultado;
	}
	
	public List<ConsultaNotasTagsDevolucao> findDadosDevolucao(){
		return expedicaoCustom.findAllDevolucao();
	}
	
	public List<ConsultaNotasTagsDevolucao> findDadosFiltrados(String dataInicial, String dataFinal){
		return expedicaoCustom.findDadosFiltrados(dataInicial, dataFinal);
	}
	
	public Map<String, Object> setParameters(String periodos, String tipo) {
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("periodos", periodos);
		parameters.put("tipo", tipo);

		return parameters;
	}
	
	public String gerarPdfEtiqueta(List<ConteudoChaveNumerica> periodosProducao, String tipo) throws FileNotFoundException, JRException {
		
		String nomeRelatorioGerado = "";
		String periodos = ConteudoChaveNumerica.parseValueToString(periodosProducao);
		
		Map<String, Object> parameters = setParameters(periodos, tipo);

		nomeRelatorioGerado = reportService.generateReport("pdf", null, "gerar_etiqueta_caixa_azul", parameters, "EtiquetaCaixaAzul", false);
		

		return nomeRelatorioGerado;
	}
	
	public List<ConsultaTagsEReferenciasMapa> findTagsPreEnderecadas(){
		return expedicaoCustom.findTagsPreEnderecadas();
	}
	
	public List<ConsultaTagsEReferenciasMapa> findRefeferenciasPreEnderecadas(){
		return expedicaoCustom.findRefeferenciasPreEnderecadas();
	}
	
	public List<ConsultaTagsEReferenciasMapa> findReferenciaByTag(){
		return expedicaoCustom.findReferenciaByTag();
	}
}