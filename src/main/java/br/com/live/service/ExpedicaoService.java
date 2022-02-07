package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.ExpedicaoCustom;
import br.com.live.model.DadosModalEndereco;
import br.com.live.model.Embarque;
import br.com.live.model.EnderecoCount;

@Service
@Transactional
public class ExpedicaoService {
	private final ExpedicaoCustom enderecosCustom;
	
	public ExpedicaoService(ExpedicaoCustom enderecosCustom) {
		this.enderecosCustom = enderecosCustom;
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
}

