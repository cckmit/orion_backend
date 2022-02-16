package br.com.live.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ExpedicaoCustom;
import br.com.live.entity.ParametrosMapaEndereco;
import br.com.live.model.DadosModalEndereco;
import br.com.live.model.Embarque;
import br.com.live.model.EnderecoCount;
import br.com.live.repository.ParametrosMapaEndRepository;

@Service
@Transactional
public class ExpedicaoService {
	private final ExpedicaoCustom enderecosCustom;
	private final ParametrosMapaEndRepository parametrosMapaEndRepository;
	
	public ExpedicaoService(ExpedicaoCustom enderecosCustom, ParametrosMapaEndRepository parametrosMapaEndRepository) {
		this.enderecosCustom = enderecosCustom;
		this.parametrosMapaEndRepository = parametrosMapaEndRepository;
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
	
	public DadosModalEndereco findDadosModalEnd (int deposito, String endereco) {
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
		
		if ((existeEndereco != null) && (!existeEndereco.equals(""))) {
			msgErro = "Este TAG já foi endereçado!";
		}
		
		if (msgErro.equals("")) {
			enderecosCustom.gravarEnderecos(periodo, ordem, pacote, sequencia, endereco);
		}
		
		return msgErro;
	}
	
	public void salvarParametros(int deposito, String blocoInicio, String blocoFim, int corredorInicio, int corredorFim, int boxInicio, int boxFim, int cestoInicio, int cestoFim) {
		
		ParametrosMapaEndereco dadosParam;
		
		dadosParam = parametrosMapaEndRepository.findByDeposito(deposito);
		
		if (dadosParam == null) {
			dadosParam = new ParametrosMapaEndereco(deposito, blocoInicio, blocoFim, corredorInicio, corredorFim, boxInicio, boxFim, cestoInicio, cestoFim);
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
		
		enderecosCustom.cleanEnderecos(deposito);
		
		for(int blocoAtual=retornaListaLetraNumero(dadosParam.blocoInicio) ; blocoAtual <= retornaListaLetraNumero(dadosParam.blocoFim); blocoAtual++){
			 for (int corredorAtual = dadosParam.corredorInicio ; corredorAtual <= dadosParam.corredorFim; corredorAtual++){
				 for (int boxAtual = dadosParam.boxInicio; boxAtual <= dadosParam.boxFim; boxAtual++){
					 for (int cestoAtual = dadosParam.cestoInicio; cestoAtual <= dadosParam.cestoFim; cestoAtual++){
						 String endereco = "";
		                 endereco = retornaListaNumeroLetra(blocoAtual) + String.format("%02d", corredorAtual) + String.format("%02d", boxAtual) + String.format("%02d", cestoAtual);
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
}

