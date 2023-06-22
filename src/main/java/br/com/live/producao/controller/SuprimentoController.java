package br.com.live.producao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.administrativo.custom.EmpresaCustom;
import br.com.live.administrativo.model.CentroCusto;
import br.com.live.administrativo.model.Empresa;
import br.com.live.producao.body.BodySuprimento;
import br.com.live.producao.custom.SuprimentoCustom;
import br.com.live.producao.entity.PreRequisicaoAlmoxarifado;
import br.com.live.producao.entity.PreRequisicaoAlmoxarifadoItem;
import br.com.live.producao.model.ConsultaPreRequisicaoAlmoxItem;
import br.com.live.producao.model.DivisaoProducao;
import br.com.live.producao.model.RequisicaoAlmoxarifado;
import br.com.live.producao.repository.PreRequisicaoAlmoxarifadoItemRepository;
import br.com.live.producao.repository.PreRequisicaoAlmoxarifadoRepository;
import br.com.live.producao.service.PreRequisicaoAlmoxarifadoService;
import br.com.live.producao.service.SuprimentoService;
import br.com.live.sistema.entity.Usuario;
import br.com.live.sistema.service.UsuarioService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.StatusGravacao;

@RestController
@CrossOrigin
@RequestMapping("/suprimentos")
public class SuprimentoController {

	private SuprimentoService suprimentoService;
	private SuprimentoCustom suprimentoCustom;
	private EmpresaCustom empresaCustom;
	private PreRequisicaoAlmoxarifadoService preRequisicaoAlmoxarifadoService;
	private PreRequisicaoAlmoxarifadoRepository preRequisicaoAlmoxarifadoRepository;
	private PreRequisicaoAlmoxarifadoItemRepository preRequisicaoAlmoxarifadoRepositoryItem;
	private UsuarioService usuarioService;
	
	@Autowired
    public SuprimentoController (SuprimentoService suprimentoService, SuprimentoCustom suprimentoCustom, EmpresaCustom empresaCustom, PreRequisicaoAlmoxarifadoService preRequisicaoAlmoxarifadoService, PreRequisicaoAlmoxarifadoRepository preRequisicaoAlmoxarifadoRepository, PreRequisicaoAlmoxarifadoItemRepository preRequisicaoAlmoxarifadoRepositoryItem, UsuarioService usuarioService) {
		this.suprimentoService = suprimentoService;
		this.suprimentoCustom = suprimentoCustom;		
		this.empresaCustom = empresaCustom;
		this.preRequisicaoAlmoxarifadoService = preRequisicaoAlmoxarifadoService;
		this.preRequisicaoAlmoxarifadoRepository = preRequisicaoAlmoxarifadoRepository;
		this.preRequisicaoAlmoxarifadoRepositoryItem = preRequisicaoAlmoxarifadoRepositoryItem;
		this.usuarioService = usuarioService;
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

	@RequestMapping(value = "/permissao-requisitar-centro-custo/{centroCusto}/{idUsuarioOrion}", method = RequestMethod.GET)	
	public boolean isTemPermissaoRequisitarParaCentroCusto(@PathVariable("centroCusto") int centroCusto, @PathVariable("idUsuarioOrion") long idUsuarioOrion) {
		Usuario usuario = usuarioService.findByIdUsuario(idUsuarioOrion);
		return suprimentoCustom.isTemPermissaoRequisitarParaCentroCusto(centroCusto, usuario.usuarioSystextil);
	}
	
	@RequestMapping(value = "/find-requisicoes-by-leitor/{leitorRequisicao}", method = RequestMethod.GET)	
	public List<ConteudoChaveAlfaNum> findDivisaoProducao(@PathVariable("leitorRequisicao") int leitorRequisicao) {
		return suprimentoCustom.findRequisicoesAlmoxByLeitorRequisicao(leitorRequisicao);
	}
		
	@RequestMapping(value = "/find-pre-requisicao/{id}", method = RequestMethod.GET)	
	public BodySuprimento findPreRequisicao(@PathVariable("id") long id) {
		BodySuprimento bodyRetorno = new BodySuprimento();
		bodyRetorno.preRequisicaoAlmoxarifado = preRequisicaoAlmoxarifadoRepository.findById(id);
		bodyRetorno.preRequisicaoAlmoxarifadoItens = suprimentoCustom.findItensPreRequisicaoByIdRequisicao(id);
		return bodyRetorno; 
	}	
		
	@RequestMapping(value = "/find-pre-requisicao-item/{id}", method = RequestMethod.GET)	
	public PreRequisicaoAlmoxarifadoItem findPreRequisicaoItem(@PathVariable("id") String idPreRequisicaoItem) {
		return preRequisicaoAlmoxarifadoRepositoryItem.findByIdItemPreRequisicao(idPreRequisicaoItem); 
	}
	
	@RequestMapping(value = "/find-pre-requisicao-itens/{id}", method = RequestMethod.GET)	
	public List<ConsultaPreRequisicaoAlmoxItem> findPreRequisicaoItens(@PathVariable("id") long idPreRequisicao) {
		return suprimentoCustom.findItensPreRequisicaoByIdRequisicao(idPreRequisicao); 
	}	
	
	@RequestMapping(value = "/find-all-pre-requisicoes", method = RequestMethod.GET)	
	public List<PreRequisicaoAlmoxarifado> findAllPreRequisicoes() {
		return preRequisicaoAlmoxarifadoRepository.findAll();
	}	

	@RequestMapping(value = "/find-requisicao-by-origem/{origem}/{id}", method = RequestMethod.GET)	
	public RequisicaoAlmoxarifado findRequisicaoByOrigem(@PathVariable("origem") int origem, @PathVariable("id") long id) {
		return suprimentoCustom.findDadosRequisicaoByOrigem(origem, id);
	}
	
	@RequestMapping(value = "/salvar-pre-requisicao-almox", method = RequestMethod.POST)
	public PreRequisicaoAlmoxarifado savePreRequisicaoAlmoxarifado (@RequestBody BodySuprimento body) {
		return preRequisicaoAlmoxarifadoService.savePreRequisicaoAlmoxarifado(body.preRequisicaoAlmoxarifado.id, body.preRequisicaoAlmoxarifado.descricao, body.preRequisicaoAlmoxarifado.empresa, body.preRequisicaoAlmoxarifado.divisaoProducao, body.preRequisicaoAlmoxarifado.centroCusto);		
	}
	
	@RequestMapping(value = "/salvar-pre-requisicao-almox-item", method = RequestMethod.POST)
	public void savePreRequisicaoAlmoxarifadoItem (@RequestBody BodySuprimento body) {
		preRequisicaoAlmoxarifadoService.savePreRequisicaoAlmoxarifadoItem(body.preRequisicaoAlmoxarifadoItem.id, body.preRequisicaoAlmoxarifadoItem.idPreRequisicao, body.preRequisicaoAlmoxarifadoItem.sequencia, body.preRequisicaoAlmoxarifadoItem.nivel, body.preRequisicaoAlmoxarifadoItem.grupo, body.preRequisicaoAlmoxarifadoItem.sub, body.preRequisicaoAlmoxarifadoItem.item, body.preRequisicaoAlmoxarifadoItem.deposito, body.preRequisicaoAlmoxarifadoItem.quantidade);		
	}
	
	@RequestMapping(value = "/copiar-requisicao", method = RequestMethod.POST)
	public StatusGravacao efetuarCopiaRequisicao(@RequestBody BodySuprimento body) {
		return suprimentoService.efetuarCopiaRequisicaoAlmox(body.requisicaoAlmoxarifado);	
	}
	
	@RequestMapping(value = "/pre-requisicao-almox/{id}", method = RequestMethod.DELETE)
	public List<PreRequisicaoAlmoxarifado> deletePreRequisicaoAlmoxarifado (@PathVariable("id") long id) {
		preRequisicaoAlmoxarifadoService.deletePreRequisicaoAlmoxarifado(id);
		return preRequisicaoAlmoxarifadoRepository.findAll();
	}
	
	@RequestMapping(value = "/pre-requisicao-almox-item/{idPreRequisicao}/{idPreRequisicaoItem}", method = RequestMethod.DELETE)
	public List<ConsultaPreRequisicaoAlmoxItem> deletePreRequisicaoAlmoxarifadoItem (@PathVariable("idPreRequisicao") long idPreRequisicao, @PathVariable("idPreRequisicaoItem") String idPreRequisicaoItem) {
		preRequisicaoAlmoxarifadoRepositoryItem.deleteById(idPreRequisicaoItem);		
		return suprimentoCustom.findItensPreRequisicaoByIdRequisicao(idPreRequisicao);
	}
}