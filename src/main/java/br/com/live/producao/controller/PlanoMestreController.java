package br.com.live.producao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.producao.body.BodyParametrosPlanoMestre;
import br.com.live.producao.entity.PlanoMestre;
import br.com.live.producao.entity.PlanoMestreParamProgItem;
import br.com.live.producao.entity.PlanoMestreParametros;
import br.com.live.producao.model.ConsultaItensPlanoMestre;
import br.com.live.producao.model.ConsultaItensTamPlanoMestre;
import br.com.live.producao.model.ConsultaPreOrdemProducao;
import br.com.live.producao.model.OcupacaoPlanoMestre;
import br.com.live.producao.model.PreOrdemProducaoIndicadores;
import br.com.live.producao.service.PlanoMestreService;
import br.com.live.produto.model.Produto;
import br.com.live.util.CodigoGrupoItem;
import br.com.live.util.ConteudoChaveNumerica;

@RestController
@CrossOrigin
@RequestMapping("/plano-mestre")
public class PlanoMestreController {

	@Autowired
	private PlanoMestreService planoMestreService;

	@RequestMapping(value = "/gerar", method = RequestMethod.POST)
	public List<PlanoMestre> gerar(@RequestBody BodyParametrosPlanoMestre parametros) {
		return planoMestreService.gerar(parametros);
	}

	@RequestMapping(value = "/copiar", method = RequestMethod.POST)
	public List<PlanoMestre> copiar(@RequestBody BodyParametrosPlanoMestre parametros) {
		return planoMestreService.copiar(parametros.idPlanoMestre);
	}
	
	@RequestMapping(value = "/salvar-situacao", method = RequestMethod.POST)
	public void salvarSituacao(@RequestBody BodyParametrosPlanoMestre parametros) {			
		planoMestreService.salvarSituacao(parametros.idPlanoMestre, parametros.situacaoPlanoMestre);
	}
	
	@RequestMapping(value = "/salvar-itens", method = RequestMethod.POST)
	public List<ConsultaItensPlanoMestre> salvarItens(@RequestBody BodyParametrosPlanoMestre parametros) {
		planoMestreService.salvarItens(parametros.itensPlanoMestre);
		return planoMestreService.findProdutos(parametros.idPlanoMestre);
	}

	@RequestMapping(value = "/salvar-grade", method = RequestMethod.POST)
	public List<ConsultaItensTamPlanoMestre> salvarGrade(@RequestBody BodyParametrosPlanoMestre parametros) {
		planoMestreService.salvarGrade(parametros.idPlanoMestre, CodigoGrupoItem.getGrupo(parametros.codigoGrupoCor),
				CodigoGrupoItem.getItem(parametros.codigoGrupoCor), parametros.gradeTamanhosItem);
		return planoMestreService.findTamanhos(parametros.idPlanoMestre,
				CodigoGrupoItem.getGrupo(parametros.codigoGrupoCor),
				CodigoGrupoItem.getItem(parametros.codigoGrupoCor));
	}

	@RequestMapping(value = "pre-ordens/gerar", method = RequestMethod.POST)
	public List<ConsultaPreOrdemProducao> gerarPreOrdens(@RequestBody BodyParametrosPlanoMestre parametros) {
		planoMestreService.gerarPreOrdens(parametros);
		return planoMestreService.findPreOrdensByIdPlanoMestre(parametros.idPlanoMestre);
	}

	@RequestMapping(value = "pre-ordens/{id}", method = RequestMethod.GET)
	public List<ConsultaPreOrdemProducao> findPreOrdens(@PathVariable("id") long idPlanoMestre) {
		return planoMestreService.findPreOrdensByIdPlanoMestre(idPlanoMestre);
	}

	@RequestMapping(value = "pre-ordens/indicadores/selecionados", method = RequestMethod.POST)
	public PreOrdemProducaoIndicadores findIndicadoresByPreOrdens(@RequestBody BodyParametrosPlanoMestre parametros) {
		return planoMestreService.findIndicadoresByPreOrdens(parametros.idPlanoMestre, parametros.preOrdensSelected);
	}
	
	@RequestMapping(value = "/referencias", method = RequestMethod.POST)
	public List<Produto> findReferencias(@RequestBody List<ConteudoChaveNumerica> planosMestres) {
		return planoMestreService.findAllRefenciasByPlanoMestre(ConteudoChaveNumerica.parseValueToString(planosMestres));
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<PlanoMestre> findAll() {
		return planoMestreService.findAll();
	}

	@RequestMapping(value = "sem-ordens-producao", method = RequestMethod.GET)
	public List<PlanoMestre> findAllPlanosMestreComPreOrdensNaoGeradas() {
		return planoMestreService.findAllPlanosMestreComPreOrdensNaoGeradas();
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
	public List<ConsultaItensTamPlanoMestre> salvarParamProgramacao(@RequestBody BodyParametrosPlanoMestre parametros) {
		planoMestreService.salvarParametrosProgramacaoItem(parametros.idPlanoMestre, CodigoGrupoItem.getGrupo(parametros.codGrupoItemProg), CodigoGrupoItem.getItem(parametros.codGrupoItemProg), parametros.alternativaProg, parametros.roteiroProg, parametros.periodoProg, parametros.multiplicadorProg, parametros.planoInicio, parametros.planoFim);		
		return planoMestreService.findTamanhos(parametros.idPlanoMestre, CodigoGrupoItem.getGrupo(parametros.codGrupoItemProg), CodigoGrupoItem.getItem(parametros.codGrupoItemProg));
	}
	
	@RequestMapping(value = "/ocupacao/calcular", method = RequestMethod.POST)
	public PlanoMestreParametros calcularOcupacao(@RequestBody BodyParametrosPlanoMestre parametros) {		
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
