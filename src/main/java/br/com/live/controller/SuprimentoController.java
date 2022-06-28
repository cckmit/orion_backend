package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodySuprimento;
import br.com.live.custom.EmpresaCustom;
import br.com.live.custom.SuprimentoCustom;
import br.com.live.entity.PreRequisicaoAlmoxarifado;
import br.com.live.entity.PreRequisicaoAlmoxarifadoItem;
import br.com.live.model.CentroCusto;
import br.com.live.model.DivisaoProducao;
import br.com.live.model.Empresa;
import br.com.live.repository.PreRequisicaoAlmoxarifadoItemRepository;
import br.com.live.repository.PreRequisicaoAlmoxarifadoRepository;
import br.com.live.service.PreRequisicaoAlmoxarifadoService;

@RestController
@CrossOrigin
@RequestMapping("/suprimentos")
public class SuprimentoController {

	private SuprimentoCustom suprimentoCustom;
	private EmpresaCustom empresaCustom;
	private PreRequisicaoAlmoxarifadoService preRequisicaoAlmoxarifadoService;
	private PreRequisicaoAlmoxarifadoRepository preRequisicaoAlmoxarifadoRepository;
	private PreRequisicaoAlmoxarifadoItemRepository preRequisicaoAlmoxarifadoRepositoryItem;
	
	@Autowired
    public SuprimentoController (SuprimentoCustom suprimentoCustom, EmpresaCustom empresaCustom, PreRequisicaoAlmoxarifadoService preRequisicaoAlmoxarifadoService, PreRequisicaoAlmoxarifadoRepository preRequisicaoAlmoxarifadoRepository, PreRequisicaoAlmoxarifadoItemRepository preRequisicaoAlmoxarifadoRepositoryItem) {
		this.suprimentoCustom = suprimentoCustom;		
		this.empresaCustom = empresaCustom;
		this.preRequisicaoAlmoxarifadoService = preRequisicaoAlmoxarifadoService;
		this.preRequisicaoAlmoxarifadoRepository = preRequisicaoAlmoxarifadoRepository;
		this.preRequisicaoAlmoxarifadoRepositoryItem = preRequisicaoAlmoxarifadoRepositoryItem;
	}

	@RequestMapping(value = "/empresas", method = RequestMethod.GET)	
	public List<Empresa> findEmpresas() {
		return empresaCustom.findEmpresasAtivas();
	}
	
	@RequestMapping(value = "/centros-custos", method = RequestMethod.GET)	
	public List<CentroCusto> findCentroCusto() {
		return suprimentoCustom.findCentroCusto();
	}
	
	@RequestMapping(value = "/divisao-producao", method = RequestMethod.GET)	
	public List<DivisaoProducao> findDivisaoProducao() {
		return suprimentoCustom.findDivisaoProducao();
	}
	
	@RequestMapping(value = "/find-requisicao/{id}", method = RequestMethod.GET)	
	public BodySuprimento findPreRequisicao(@PathVariable("id") long id) {
		BodySuprimento bodyRetorno = new BodySuprimento();
		bodyRetorno.preRequisicaoAlmoxarifado = preRequisicaoAlmoxarifadoRepository.findById(id);
		bodyRetorno.preRequisicaoAlmoxarifadoItens = preRequisicaoAlmoxarifadoRepositoryItem.findByIdPreRequisicao(id);		
		return bodyRetorno; 
	}	

	@RequestMapping(value = "/find-requisicao-item/{id}", method = RequestMethod.GET)	
	public PreRequisicaoAlmoxarifadoItem findPreRequisicao(@PathVariable("id") String idPreRequisicaoItem) {
		return preRequisicaoAlmoxarifadoRepositoryItem.findByIdItemPreRequisicao(idPreRequisicaoItem); 
	}	
	
	@RequestMapping(value = "/find-all-requisicoes", method = RequestMethod.GET)	
	public List<PreRequisicaoAlmoxarifado> findAllPreRequisicoes() {
		return preRequisicaoAlmoxarifadoRepository.findAll();
	}	

	@RequestMapping(value = "/salvar-pre-requisicao-almox", method = RequestMethod.POST)
	public PreRequisicaoAlmoxarifado savePreRequisicaoAlmoxarifado (@RequestBody BodySuprimento body) {
		return preRequisicaoAlmoxarifadoService.savePreRequisicaoAlmoxarifado(body.preRequisicaoAlmoxarifado.id, body.preRequisicaoAlmoxarifado.descricao, body.preRequisicaoAlmoxarifado.empresa, body.preRequisicaoAlmoxarifado.divisaoProducao, body.preRequisicaoAlmoxarifado.centroCusto);		
	}
	
	@RequestMapping(value = "/salvar-pre-requisicao-almox-item", method = RequestMethod.POST)
	public void savePreRequisicaoAlmoxarifadoItem (@RequestBody BodySuprimento body) {
		preRequisicaoAlmoxarifadoService.savePreRequisicaoAlmoxarifadoItem(body.preRequisicaoAlmoxarifadoItem.id, body.preRequisicaoAlmoxarifadoItem.idPreRequisicao, body.preRequisicaoAlmoxarifadoItem.sequencia, body.preRequisicaoAlmoxarifadoItem.nivel, body.preRequisicaoAlmoxarifadoItem.grupo, body.preRequisicaoAlmoxarifadoItem.sub, body.preRequisicaoAlmoxarifadoItem.item, body.preRequisicaoAlmoxarifadoItem.deposito, body.preRequisicaoAlmoxarifadoItem.quantidade);		
	}
	
	@RequestMapping(value = "/requisicao-almox/{id}", method = RequestMethod.DELETE)
	public List<PreRequisicaoAlmoxarifado> deletePreRequisicaoAlmoxarifado (@PathVariable("id") long id) {
		preRequisicaoAlmoxarifadoService.deletePreRequisicaoAlmoxarifado(id);
		return preRequisicaoAlmoxarifadoRepository.findAll();
	}
}
