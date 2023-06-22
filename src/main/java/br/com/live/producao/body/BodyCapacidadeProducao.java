package br.com.live.producao.body;

import java.util.List;

import br.com.live.producao.model.EstagioCapacidadeProducao;
import br.com.live.produto.model.ArtigoCapacidadeProducao;

public class BodyCapacidadeProducao {
	
	public int periodo;
	public int estagio;
	public List<EstagioCapacidadeProducao> estagiosCapacidadeProducao;
	public List<ArtigoCapacidadeProducao> artigosCapacidadeProducao;
	
}
