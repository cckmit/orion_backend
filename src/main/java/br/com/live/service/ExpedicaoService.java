package br.com.live.service;

import java.util.List;

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
}

