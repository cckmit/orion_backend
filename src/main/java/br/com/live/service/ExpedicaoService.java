package br.com.live.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ExpedicaoCustom;
import br.com.live.entity.AberturaCaixas;
import br.com.live.entity.CapacidadeArtigoEndereco;
import br.com.live.entity.ParametrosMapaEndereco;
import br.com.live.model.CestoEndereco;
import br.com.live.model.ConsultaCapacidadeArtigosEnderecos;
import br.com.live.model.DadosModalEndereco;
import br.com.live.model.DadosTagProd;
import br.com.live.model.Embarque;
import br.com.live.model.EnderecoCount;
import br.com.live.model.ProdutoEnderecar;
import br.com.live.repository.AberturaCaixasRepository;
import br.com.live.repository.CapacidadeArtigoEnderecoRepository;
import br.com.live.repository.ParametrosMapaEndRepository;

@Service
@Transactional
public class ExpedicaoService {
	private final ExpedicaoCustom enderecosCustom;
	private final ParametrosMapaEndRepository parametrosMapaEndRepository;
	private final CapacidadeArtigoEnderecoRepository capacidadeArtigoEnderecoRepository;
	private final AberturaCaixasRepository aberturaCaixasRepository;

	public ExpedicaoService(ExpedicaoCustom enderecosCustom, ParametrosMapaEndRepository parametrosMapaEndRepository,
			CapacidadeArtigoEnderecoRepository capacidadeArtigoEnderecoRepository,
			AberturaCaixasRepository aberturaCaixasRepository) {
		this.enderecosCustom = enderecosCustom;
		this.parametrosMapaEndRepository = parametrosMapaEndRepository;
		this.capacidadeArtigoEnderecoRepository = capacidadeArtigoEnderecoRepository;
		this.aberturaCaixasRepository = aberturaCaixasRepository;
	}

	public List<EnderecoCount> findEnderecoRef(int codDeposito) {
		return enderecosCustom.findReferenciaEnd(codDeposito);
	}

	public Embarque findGrupoEmbarque(String numeroTag) {
		int periodo = 0;
		int ordem = 0;
		int pacote = 0;

		periodo = Integer.parseInt(numeroTag.substring(0, 4));
		ordem = Integer.parseInt(numeroTag.substring(4, 13));
		pacote = Integer.parseInt(numeroTag.substring(13, 18));

		return enderecosCustom.findEmbarque(periodo, ordem, pacote);
	}

	public DadosModalEndereco findDadosModalEnd(int deposito, String endereco) {
		return enderecosCustom.findDadosModalEndereco(deposito, endereco);
	}

	public String gravarEndereco(String numeroTag, String endereco) {

		String msgErro = "";
		String existeEndereco = "";

		int periodo = Integer.parseInt(numeroTag.substring(0, 4));
		int ordem = Integer.parseInt(numeroTag.substring(4, 13));
		int pacote = Integer.parseInt(numeroTag.substring(13, 18));
		int sequencia = Integer.parseInt(numeroTag.substring(18, 22));

		int flagEmEstoque = enderecosCustom.validarPecaEmEstoque(periodo, ordem, pacote, sequencia);
		existeEndereco = enderecosCustom.validarGravacaoEndereco(periodo, ordem, pacote, sequencia);

		if (flagEmEstoque == 1) {
			msgErro = "Este TAG já foi faturado!";
		}

		if ((existeEndereco != null) && (!existeEndereco.equals("")) && (!existeEndereco.equals("ENDERECAR"))) {
			msgErro = "Este TAG já foi endereçado!";
		}

		if (msgErro.equals("")) {
			enderecosCustom.gravarEnderecos(periodo, ordem, pacote, sequencia, endereco);
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

	public void gerarEnderecosDinamicos(int deposito) {
		ParametrosMapaEndereco dadosParam = parametrosMapaEndRepository.findByDeposito(deposito);

		for (int blocoAtual = retornaListaLetraNumero(dadosParam.blocoInicio); blocoAtual <= retornaListaLetraNumero(
				dadosParam.blocoFim); blocoAtual++) {
			for (int corredorAtual = dadosParam.corredorInicio; corredorAtual <= dadosParam.corredorFim; corredorAtual++) {
				for (int boxAtual = dadosParam.boxInicio; boxAtual <= dadosParam.boxFim; boxAtual++) {
					for (int cestoAtual = dadosParam.cestoInicio; cestoAtual <= dadosParam.cestoFim; cestoAtual++) {
						String endereco = "";
						endereco = retornaListaNumeroLetra(blocoAtual) + String.format("%02d", corredorAtual)
								+ String.format("%02d", boxAtual) + String.format("%02d", cestoAtual);
						enderecosCustom.inserirEnderecosDeposito(deposito, endereco);
					}
				}
			}
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

	public String abrirCaixa(int codCaixa, String usuario) {
		String msgErro = "";
		int numeroCaixa = 0;

		numeroCaixa = enderecosCustom.validarCaixaAberta();

		if (numeroCaixa == codCaixa)
			return msgErro;

		if (numeroCaixa > 1) {
			msgErro = " Não é possível abrir uma nova Caixa! feche a caixa " + numeroCaixa + " para continuar!";
			return msgErro;
		}

		AberturaCaixas dadosAbertura;

		Date dataAtual = new Date();
		Date dataFinal = null;

		dadosAbertura = new AberturaCaixas(codCaixa, 0, usuario, dataAtual, dataFinal);

		aberturaCaixasRepository.save(dadosAbertura);

		return msgErro;
	}

	public void fecharCaixa(int codCaixa) {
		AberturaCaixas dadosAbertura = aberturaCaixasRepository.findByNumeroCaixa(codCaixa);

		if (dadosAbertura.situacaoCaixa != 1) {

			Date dataHoraFim = new Date();

			dadosAbertura.dataHoraFim = dataHoraFim;
			dadosAbertura.situacaoCaixa = 1;

			aberturaCaixasRepository.save(dadosAbertura);
			
			List<DadosTagProd> dadosCaixasTag = enderecosCustom.findDadosTagCaixas(codCaixa);
			
			for (DadosTagProd tagLido : dadosCaixasTag) {
				enderecosCustom.atualizarSituacaoEndereco(tagLido.periodo, tagLido.ordem, tagLido.pacote, tagLido.sequencia);
			}
		}
	}
	
	public List<ProdutoEnderecar> findProdutosEnderecarCaixa (int codCaixa) {
		List<ProdutoEnderecar> produtos = enderecosCustom.findProdutosEnderecar(codCaixa);
		
		for (ProdutoEnderecar produto : produtos) {			
			produto.endereco = ProdutoEnderecar.ENDERECO_INDISPONIVEL;
			
			CestoEndereco cesto = enderecosCustom.findEnderecoCesto(produto.nivel, produto.referencia, produto.tamanho, produto.cor, produto.deposito);
			
			if (cesto != null) {
				if (cesto.qtdeCapacDisponivel <= 0) produto.endereco = cesto.endereco;
			}
		}
		
		return produtos;
	}
}
