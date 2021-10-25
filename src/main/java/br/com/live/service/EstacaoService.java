package br.com.live.service;

import org.springframework.stereotype.Service;

import br.com.live.entity.Estacao;
import br.com.live.repository.EstacaoRepository;

@Service
public class EstacaoService {
	
	private final EstacaoRepository estacaoRepository;	

	public EstacaoService(EstacaoRepository estacaoRepository) {
		this.estacaoRepository = estacaoRepository;		
	}
	
	public void saveEstacao(int codEstacao, String descricao, int catalogo) {
		 
		Estacao estacao = estacaoRepository.findByCodEstacao(codEstacao);
		
		System.out.println("chegou aqui!");
		
		if (estacao == null) {
			estacao = new Estacao(codEstacao, descricao, catalogo);
		} else {
			estacao.catalogo = catalogo;
			estacao.descricao = descricao;
		}
		
		estacaoRepository.save(estacao);
	}
}
