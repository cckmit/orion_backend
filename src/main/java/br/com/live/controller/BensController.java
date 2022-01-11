package br.com.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyBens;
import br.com.live.custom.BensCustom;
import br.com.live.custom.EmpresaCustom;
import br.com.live.model.Bens;
import br.com.live.model.ConsultaMovimentacoes;
import br.com.live.entity.MovimentacaoBens;
import br.com.live.entity.TipoMovimentacao;
import br.com.live.model.Empresa;
import br.com.live.repository.MovimentacaoBensRepository;
import br.com.live.repository.TipoMovimentacaoRepository;
import br.com.live.service.BensService;

@RestController
@CrossOrigin
@RequestMapping("/patrimonial")
public class BensController {
	
	private TipoMovimentacaoRepository tipoMovimentacaoRepository;
	private MovimentacaoBensRepository movimentacaoBensRepository;
	private BensService bensService;
	private BensCustom bensCustom;
	private EmpresaCustom empresaCustom;
	
	 @Autowired
	    public BensController(TipoMovimentacaoRepository tipoMovimentacaoRepository, BensService bensService, BensCustom bensCustom, EmpresaCustom empresaCustom, MovimentacaoBensRepository movimentacaoBensRepository) {
	          this.tipoMovimentacaoRepository = tipoMovimentacaoRepository;
	          this.movimentacaoBensRepository = movimentacaoBensRepository;
	          this.bensService = bensService;
	          this.bensCustom = bensCustom;
	          this.empresaCustom = empresaCustom;
	    }
		
	    @RequestMapping(value = "/tipos-movimentacao", method = RequestMethod.GET)
	    public List<TipoMovimentacao> findAll() {
	          return tipoMovimentacaoRepository.findAll();
	    }
	    
	    @RequestMapping(value = "/find-all-movimentacao", method = RequestMethod.GET)
	    public List<ConsultaMovimentacoes> findAllMovimentacao() {
	          return bensCustom.findAllMovimentacoes();
	    }
	    
	    @RequestMapping(value = "/find-movimentacoes-by-bem/{idBem}", method = RequestMethod.GET)
	    public List<ConsultaMovimentacoes> findMovimentacoesByBem(@PathVariable("idBem") int idBem) {
	          return bensCustom.findMovimentacoesByBem(idBem);
	    }
	    
	    @RequestMapping(value = "/find-by-sequencia-movimentacao/{sequencia}", method = RequestMethod.GET)
	    public MovimentacaoBens findBySsequenciaMovimentacao(@PathVariable("sequencia") int sequencia) {
	          return movimentacaoBensRepository.findBySequencia(sequencia);
	    }
	    
	    @RequestMapping(value = "/find-by-id/{idTipoMovimentacao}", method = RequestMethod.GET)
	    public TipoMovimentacao findByBens(@PathVariable("idTipoMovimentacao") int idTipoMovimentacao) {
	          return tipoMovimentacaoRepository.findById(idTipoMovimentacao);
	    }
	    
	    @RequestMapping(value = "/save", method = RequestMethod.POST)
	    public TipoMovimentacao saveTipoMovimentacao(@RequestBody BodyBens body) {
	          return bensService.saveTipoMovimentacao(body.id, body.descricao);
	    }
	    
	    @RequestMapping(value = "/gerar-movimentacao", method = RequestMethod.POST)
	    public void gerarMovimentacao(@RequestBody BodyBens body) {
	    	bensService.gerarMovimentacao(body.sequencia, body.tipoMovimentacao, body.cnpjOrigem, body.cnpjDestino, body.dataEnvio, body.notaFiscal, body.observacao, body.bens, body.usuario);
	    }
	    
	    @RequestMapping(value = "/alterar-movimentacao", method = RequestMethod.POST)
	    public void alterarMovimentacao(@RequestBody BodyBens body) {
	    	bensService.alterarMovimentacao(body.sequencia, body.tipoMovimentacao, body.cnpjOrigem, body.cnpjDestino, body.dataEnvio, body.notaFiscal, body.observacao);
	    }
	    
	    @RequestMapping(value = "/delete/{idTipoMovimentacao}", method = RequestMethod.DELETE)
	    public List<TipoMovimentacao> deleteById(@PathVariable("idTipoMovimentacao") int idTipoMovimentacao) {
	    	bensService.deleteById(idTipoMovimentacao);
	    	return tipoMovimentacaoRepository.findAll();
	    }
	    
	    @RequestMapping(value = "/delete-by-sequencia/{sequencia}", method = RequestMethod.DELETE)
	    public List<ConsultaMovimentacoes> deleteBySequencia(@PathVariable("sequencia") int sequencia) {
	    	bensService.deleteBySequencia(sequencia);
	    	return bensCustom.findAllMovimentacoes();
	    }
	    
	    @RequestMapping(value = "/empresas", method = RequestMethod.GET)
	    public List<Empresa> findAllEmpresas() {
	          return empresaCustom.findAllEmpresas();
	    }
	    
	    @RequestMapping(value = "/find-all-bens", method = RequestMethod.GET)
	    public List<Bens> findAllBens() {
	          return bensCustom.findAllBens();
	    }
}
