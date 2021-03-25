package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.CapacidadeProducaoCustom;
import br.com.live.entity.CapacidadeProdArtigo;
import br.com.live.entity.CapacidadeProdEstagio;
import br.com.live.model.ArtigoCapacidadeProducao;
import br.com.live.model.EstagioCapacidadeProducao;
import br.com.live.repository.CapacidadeProdArtigoRepository;
import br.com.live.repository.CapacidadeProdEstagioRepository;

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

	public List<EstagioCapacidadeProducao> findEstagios() {
		return capacidadeProducaoCustomRepository.findEstagios();
	}

	public List<ArtigoCapacidadeProducao> findArtigosByEstagio(int estagio) {
		return capacidadeProducaoCustomRepository.findArtigosByEstagio(estagio);
	}

	public List<EstagioCapacidadeProducao> findEstagiosCapacidadeConfigurada() {
		return capacidadeProducaoCustomRepository.findEstagiosCapacidadeConfigurada();
	}

	public List<ArtigoCapacidadeProducao> findArtigosCapacidadeConfigurada() {
		return capacidadeProducaoCustomRepository.findArtigosCapacidadeConfigurada();
	}

	public void saveEstagios(List<EstagioCapacidadeProducao> estagiosCapacidadeProducao) {

		for (EstagioCapacidadeProducao estagio : estagiosCapacidadeProducao) {
			CapacidadeProdEstagio capacidade = new CapacidadeProdEstagio(estagio.estagio, estagio.qtdePecas,
					estagio.qtdeMinutos);
			capacidadeProdEstagioRepository.save(capacidade);
		}

	}

	public void saveArtigos(int estagio, List<ArtigoCapacidadeProducao> artigosCapacidadeProducao) {

		System.out.println("saveArtigos");
		System.out.println("estagio: " + estagio);
		
		for (ArtigoCapacidadeProducao artigo : artigosCapacidadeProducao) {
			System.out.println("artigo: " + artigo.artigo);
			
			CapacidadeProdArtigo capacidade = new CapacidadeProdArtigo(estagio, artigo.artigo, artigo.qtdePecas,
					artigo.qtdeMinutos);
			capacidadeProdArtigoRepository.save(capacidade);
		}

	}
}