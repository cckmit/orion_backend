package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.entity.PlanoMestre;
import br.com.live.entity.PlanoMestreParametros;
import br.com.live.model.ConsultaPreOrdemProducao;
import br.com.live.model.OcupacaoPlanoMestre;
import br.com.live.model.ConsultaItensPlanoMestre;
import br.com.live.model.ConsultaItensTamPlanoMestre;
import br.com.live.model.PreOrdemProducaoIndicadores;
import br.com.live.entity.PlanoMestreParamProgItem;
import br.com.live.service.PlanoMestreService;
import br.com.live.util.CodigoGrupoItem;
import br.com.live.util.ParametrosPlanoMestre;

@RestController
@CrossOrigin
@RequestMapping("/plano-mestre")
public class PlanoMestreController {

	@Autowired
	private PlanoMestreService planoMestreService;

	@RequestMapping(value = "/gerar", method = RequestMethod.POST)
	public List<PlanoMestre> gerar(@RequestBody ParametrosPlanoMestre parametros) {
		return planoMestreService.gerar(parametros);
	}

	@RequestMapping(value = "/copiar", method = RequestMethod.POST)
	public List<PlanoMestre> copiar(@RequestBody ParametrosPlanoMestre parametros) {
		return planoMestreService.copiar(parametros.idPlanoMestre);
	}
	
	@RequestMapping(value = "/salvar-situacao", method = RequestMethod.POST)
	public void salvarSituacao(@RequestBody ParametrosPlanoMestre parametros) {			
		planoMestreService.salvarSituacao(parametros.idPlanoMestre, parametros.situacaoPlanoMestre);
	}
	
	@RequestMapping(value = "/salvar-itens", method = RequestMethod.POST)
	public List<ConsultaItensPlanoMestre> salvarItens(@RequestBody ParametrosPlanoMestre parametros) {
		planoMestreService.salvarItens(parametros.itensPlanoMestre);
		return planoMestreService.findProdutos(parametros.idPlanoMestre);
	}

	@RequestMapping(value = "/salvar-grade", method = RequestMethod.POST)
	public List<ConsultaItensTamPlanoMestre> salvarGrade(@RequestBody ParametrosPlanoMestre parametros) {
		planoMestreService.salvarGrade(parametros.idPlanoMestre, CodigoGrupoItem.getGrupo(parametros.codigoGrupoCor),
				CodigoGrupoItem.getItem(parametros.codigoGrupoCor), parametros.gradeTamanhosItem);
		return planoMestreService.findTamanhos(parametros.idPlanoMestre,
				CodigoGrupoItem.getGrupo(parametros.codigoGrupoCor),
				CodigoGrupoItem.getItem(parametros.codigoGrupoCor));
	}

	@RequestMapping(value = "pre-ordens/gerar", method = RequestMethod.POST)
	public List<ConsultaPreOrdemProducao> gerarPreOrdens(@RequestBody ParametrosPlanoMestre parametros) {
		planoMestreService.gerarPreOrdens(parametros);
		return planoMestreService.findPreOrdensByIdPlanoMestre(parametros.idPlanoMestre);
	}

	@RequestMapping(value = "pre-ordens/{id}", method = RequestMethod.GET)
	public List<ConsultaPreOrdemProducao> findPreOrdens(@PathVariable("id") long idPlanoMestre) {
		return planoMestreService.findPreOrdensByIdPlanoMestre(idPlanoMestre);
	}

	@RequestMapping(value = "pre-ordens/indicadores/selecionados", method = RequestMethod.POST)
	public PreOrdemProducaoIndicadores findIndicadoresByPreOrdens(@RequestBody ParametrosPlanoMestre parametros) {
		return planoMestreService.findIndicadoresByPreOrdens(parametros.idPlanoMestre, parametros.preOrdensSelected);
	}
		
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<PlanoMestre> findAll() {
		return planoMestreService.findAll();
	}

	@RequestMapping(value = "/produtos/{id}", method = RequestMethod.GET)
	public List<ConsultaItensPlanoMestre> findProdutos(@PathVariable("id") long idPlanoMestre) {
		return planoMestreService.findProdutos(idPlanoMestre);
	}

	@RequestMapping(value = "/tamanhos/{id}/{codigo}", method = RequestMethod.GET)
	public List<ConsultaItensTamPlanoMestre> findTamanhos(@PathVariable("id") long idPlanoMestre,
			@PathVariable("codigo") String codigo) {
		return planoMestreService.findTamanhos(idPlanoMestre, CodigoGrupoItem.getGrupo(codigo),
				CodigoGrupoItem.getItem(codigo));
	}

	@RequestMapping(value = "/parametros/{id}", method = RequestMethod.GET)
	public PlanoMestreParametros findParametros(@PathVariable("id") long idPlanoMestre) {
		return planoMestreService.findParametros(idPlanoMestre);
	}

	@RequestMapping(value = "/param-prog-item/{id}/{codigo}", method = RequestMethod.GET)
	public PlanoMestreParamProgItem findParamProgItem(@PathVariable("id") long idPlanoMestre,
			@PathVariable("codigo") String codigo) {
		return planoMestreService.findParamProgItem(idPlanoMestre, CodigoGrupoItem.getGrupo(codigo),
				CodigoGrupoItem.getItem(codigo));
	}

	@RequestMapping(value = "/salvar-param-programacao", method = RequestMethod.POST)
	public List<ConsultaItensTamPlanoMestre> salvarParamProgramacao(@RequestBody ParametrosPlanoMestre parametros) {
		planoMestreService.salvarParametrosProgramacaoItem(parametros.idPlanoMestre, CodigoGrupoItem.getGrupo(parametros.codGrupoItemProg), CodigoGrupoItem.getItem(parametros.codGrupoItemProg), parametros.alternativaProg, parametros.roteiroProg, parametros.periodoProg, parametros.multiplicadorProg);		
		return planoMestreService.findTamanhos(parametros.idPlanoMestre, CodigoGrupoItem.getGrupo(parametros.codGrupoItemProg), CodigoGrupoItem.getItem(parametros.codGrupoItemProg));
	}
	
	@RequestMapping(value = "/ocupacao/calcular", method = RequestMethod.POST)
	public PlanoMestreParametros calcularOcupacao(@RequestBody ParametrosPlanoMestre parametros) {		
		return planoMestreService.calcularOcupacaoPlano(parametros.idPlanoMestre, parametros.periodoOcupacaoInicio, parametros.periodoOcupacaoFim);		
	}	
	
	@RequestMapping(value = "/ocupacao-estagio/{id}/{estagio}", method = RequestMethod.GET)
	public OcupacaoPlanoMestre findOcupacaoEstagio(@PathVariable("id") long idPlanoMestre,
			@PathVariable("estagio") int estagio) {
		return planoMestreService.findOcupacaoCalculadaByPlanoEstagio(idPlanoMestre, estagio);
	}

	@RequestMapping(value = "/ocupacao-artigos/{id}/{estagio}", method = RequestMethod.GET)
	public List<OcupacaoPlanoMestre> findOcupacaoArtigosEstagio(@PathVariable("id") long idPlanoMestre,
			@PathVariable("estagio") int estagio) {
		return planoMestreService.findOcupacaoCalculadaArtigoByPlanoEstagio(idPlanoMestre, estagio);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public List<PlanoMestre> deletePlanoMestre(@PathVariable("id") long idPlanoMestre) {
		return planoMestreService.delete(idPlanoMestre);
	}
	
	@RequestMapping(value = "/zerar-quantidades/{idPlanoMestre}", method = RequestMethod.GET)
	public List<ConsultaItensPlanoMestre> zerarQuantidades(@PathVariable("idPlanoMestre") long idPlanoMestre) {
		planoMestreService.zerarQtdesSugestaoCancelamento(idPlanoMestre);
		return planoMestreService.findProdutos(idPlanoMestre);
	}

}
