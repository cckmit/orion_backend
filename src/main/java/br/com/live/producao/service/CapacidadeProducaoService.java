package br.com.live.producao.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.producao.custom.CapacidadeProducaoCustom;
import br.com.live.producao.entity.CapacidadeProdArtigo;
import br.com.live.producao.entity.CapacidadeProdEstagio;
import br.com.live.producao.model.EstagioCapacidadeProducao;
import br.com.live.producao.repository.CapacidadeProdArtigoRepository;
import br.com.live.producao.repository.CapacidadeProdEstagioRepository;
import br.com.live.produto.model.ArtigoCapacidadeProducao;

@Service
@Transactional
public class CapacidadeProducaoService {

	private final CapacidadeProducaoCustom capacidadeProducaoCustomRepository;
	private final CapacidadeProdEstagioRepository capacidadeProdEstagioRepository;
	private final CapacidadeProdArtigoRepository capacidadeProdArtigoRepository;

	public CapacidadeProducaoService(CapacidadeProducaoCustom capacidadeProducaoCustomRepository,
			CapacidadeProdEstagioRepository capacidadeProdEstagioRepository,
			CapacidadeProdArtigoRepository capacidadeProdArtigoRepository) {
		this.capacidadeProducaoCustomRepository = capacidadeProducaoCustomRepository;
		this.capacidadeProdEstagioRepository = capacidadeProdEstagioRepository;
		this.capacidadeProdArtigoRepository = capacidadeProdArtigoRepository;
	}

	public List<EstagioCapacidadeProducao> findEstagios(int periodo) { 
		return capacidadeProducaoCustomRepository.findEstagios(periodo);
	}

	public EstagioCapacidadeProducao findCapacidadeByEstagio(int periodo, int estagio) {
		return capacidadeProducaoCustomRepository.findCapacidadeByEstagio(periodo, estagio);
	}
	
	public List<ArtigoCapacidadeProducao> findArtigosByEstagio(int periodo, int estagio) {
		return capacidadeProducaoCustomRepository.findArtigosByEstagio(periodo, estagio);
	}

	public List<EstagioCapacidadeProducao> findEstagiosCapacidadeConfigurada() {
		return capacidadeProducaoCustomRepository.findEstagiosCapacidadeConfigurada();
	}

	public List<ArtigoCapacidadeProducao> findArtigosCapacidadeConfigurada() {
		return capacidadeProducaoCustomRepository.findArtigosCapacidadeConfigurada();
	}

	public void saveEstagios(int periodo, List<EstagioCapacidadeProducao> estagiosCapacidadeProducao) {
		for (EstagioCapacidadeProducao estagio : estagiosCapacidadeProducao) {
			CapacidadeProdEstagio capacidade = new CapacidadeProdEstagio(periodo, estagio.estagio, estagio.qtdePecas,
					estagio.qtdeMinutos);
			capacidadeProdEstagioRepository.save(capacidade);
		}

	}

	public void saveArtigos(int periodo, int estagio, List<ArtigoCapacidadeProducao> artigosCapacidadeProducao) {
		for (ArtigoCapacidadeProducao artigo : artigosCapacidadeProducao) {
			
			CapacidadeProdArtigo capacidade = new CapacidadeProdArtigo(periodo, estagio, artigo.artigo, artigo.qtdePecas,
					artigo.qtdeMinutos);
			capacidadeProdArtigoRepository.save(capacidade);
		}

	}
}