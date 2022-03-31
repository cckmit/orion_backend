package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.TrocaCorCustom;
import br.com.live.model.TrocaCor;

@Service
@Transactional
public class TrocaCorService {
	
	private final TrocaCorCustom trocaCorCustom;

	public TrocaCorService(TrocaCorCustom trocaCorCustom) {
		this.trocaCorCustom = trocaCorCustom;
	}
	
	public TrocaCor findGrupoOrdemProducao(int ordemProducao) {
		return trocaCorCustom.findGrupoOrdemProducao(ordemProducao);
	}
	
	public List<TrocaCor> findTamanhosByRef(String nivel, String grupo) {
		List<TrocaCor> listTamanhos = trocaCorCustom.findTamanhosByReferencia(nivel, grupo);
		return listTamanhos;
	}
	
	public List<TrocaCor> findCoresByTamanhos(String nivel, String grupo, String subGrupo) {
		List<TrocaCor> listCores = trocaCorCustom.findCoresByTamanho(nivel, grupo, subGrupo);
		return listCores;
	}
	
	public void confirmarTrocaCor(int ordemProducao, String subGrupo, String item) {
		trocaCorCustom.updateCorRolosPreparados(ordemProducao, subGrupo, item);
		trocaCorCustom.updateCorPlanejProduto(ordemProducao, subGrupo, item);
		trocaCorCustom.updateCorDestinoOrdemBenef(ordemProducao, subGrupo, item);
		trocaCorCustom.updateCorTecidoOrdemBenef(ordemProducao, subGrupo, item);
	}
	
	public boolean validarOrdemProduzida(int ordemProducao) {
		return trocaCorCustom.validarOrdemProduzida(ordemProducao);
	}
}
