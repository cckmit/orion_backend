package br.com.live.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.body.BodyExpedicao;
import br.com.live.body.BodyMinutaTransporte;
import br.com.live.body.BodyVariacaoPesoArtigo;
import br.com.live.custom.ExpedicaoCustom;
import br.com.live.entity.CaixasParaEnderecar;
import br.com.live.entity.ParametrosMapaEndereco;
import br.com.live.entity.ParametrosMapaEnderecoCaixa;
import br.com.live.model.ConsultaCaixasNoEndereco;
import br.com.live.model.ConsultaCapacidadeArtigosEnderecos;
import br.com.live.model.ConsultaMinutaTransporte;
import br.com.live.model.ConsultaTag;
import br.com.live.model.ConsultaVariacaoArtigo;
import br.com.live.model.DadosModalEndereco;
import br.com.live.model.Embarque;
import br.com.live.model.EnderecoCount;
import br.com.live.model.ProdutoEnderecar;
import br.com.live.repository.AberturaCaixasRepository;
import br.com.live.repository.ParametrosEnderecoCaixaRepository;
import br.com.live.repository.ParametrosMapaEndRepository;
import br.com.live.service.AcertoCalculoDepreciacaoService;
import br.com.live.service.ExpedicaoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import net.sf.jasperreports.engine.JRException;

@RestController
@CrossOrigin
@RequestMapping("/endereco")
public class ExpedicaoController {
	
	private ExpedicaoService enderecoService;
	private ParametrosMapaEndRepository parametrosMapaEndRepository;
	private ExpedicaoCustom expedicaoCustom;
	private AberturaCaixasRepository aberturaCaixasRepository;
	private ParametrosEnderecoCaixaRepository parametrosEnderecoCaixaRepository;
	private AcertoCalculoDepreciacaoService acertoCalculoDepreciacaoService;
	
    @Autowired
    public ExpedicaoController(ExpedicaoService enderecoService, ParametrosMapaEndRepository parametrosMapaEndRepository, ExpedicaoCustom expedicaoCustom, AberturaCaixasRepository aberturaCaixasRepository,
    		ParametrosEnderecoCaixaRepository parametrosEnderecoCaixaRepository, AcertoCalculoDepreciacaoService acertoCalculoDepreciacaoService) {
    	this.enderecoService = enderecoService;
    	this.parametrosMapaEndRepository = parametrosMapaEndRepository;
    	this.expedicaoCustom = expedicaoCustom;
    	this.aberturaCaixasRepository = aberturaCaixasRepository;
    	this.parametrosEnderecoCaixaRepository = parametrosEnderecoCaixaRepository;
    	this.acertoCalculoDepreciacaoService = acertoCalculoDepreciacaoService;
    }

    @RequestMapping(value = "/find-endereco/{deposito}", method = RequestMethod.GET)
    public List<EnderecoCount> findEnderecoRef(@PathVariable("deposito") int deposito) {
        return enderecoService.findEnderecoRef(deposito);
    }
    
    @RequestMapping(value = "/find-dados-embarque/{numeroTag}", method = RequestMethod.GET)
    public Embarque findEnderecoRef(@PathVariable("numeroTag") String numeroTag) {
        return enderecoService.findGrupoEmbarque(numeroTag);
    }
    
    @RequestMapping(value = "/find-dados-modal-endereco/{deposito}/{endereco}", method = RequestMethod.GET)
    public DadosModalEndereco findDadosModalEnd(@PathVariable("deposito") int deposito, @PathVariable("endereco") String endereco) {
        return enderecoService.findDadosModalEnd(deposito, endereco);
    }
    
    @RequestMapping(value = "/find-parametros/{deposito}", method = RequestMethod.GET)
    public ParametrosMapaEndereco findParametros(@PathVariable("deposito") int deposito) {
        return parametrosMapaEndRepository.findByDeposito(deposito);
    }
    
    @RequestMapping(value = "/gravar-endereco", method = RequestMethod.POST)
    public String findEnderecoRef(@RequestBody BodyExpedicao body) {
    	return enderecoService.gravarEndereco(body.numeroTag, body.endereco);
    }
    
    @RequestMapping(value = "/gravar-parametros", method = RequestMethod.POST)
    public void gravaParametros(@RequestBody BodyExpedicao body) {
    	enderecoService.salvarParametros(body.deposito, body.blocoInicio, body.blocoFim, body.corredorInicio, body.corredorFim, body.boxInicio, body.boxFim, body.cestoInicio, body.cestoFim);
    	enderecoService.gravarEnderecosDeposito(body.deposito);
    }
    
    @RequestMapping(value = "/find-all-capacidades-artigos", method = RequestMethod.GET)
    public List<ConsultaCapacidadeArtigosEnderecos> findAllCapacidadesArtigos() {
        return expedicaoCustom.findArtigosEnderecos();
    }
    
    @RequestMapping(value = "/gravar-capacidades", method = RequestMethod.POST)
    public List<ConsultaCapacidadeArtigosEnderecos> gravarCapacidades(@RequestBody BodyExpedicao body) {
    	enderecoService.gravarCapacidades(body.itens);
    	return expedicaoCustom.findArtigosEnderecos();
    }
    
    @RequestMapping(value = "/abrir-caixa", method = RequestMethod.POST)
    public String abrirCaixa(@RequestBody BodyExpedicao body) {
    	return enderecoService.abrirCaixa(body.codCaixa, body.usuario);
    }
    
    @RequestMapping(value = "/find-dados-caixa/{codCaixa}", method = RequestMethod.GET)
    public CaixasParaEnderecar findDadosCaixa(@PathVariable("codCaixa") int codCaixa) {
        return aberturaCaixasRepository.findByNumeroCaixa(codCaixa);
    }
    
    @RequestMapping(value = "/fechar-caixa", method = RequestMethod.POST)
    public CaixasParaEnderecar fecharCaixa(@RequestBody BodyExpedicao body) {
    	enderecoService.fecharCaixa(body.codCaixa);
    	return aberturaCaixasRepository.findByNumeroCaixa(body.codCaixa);
    }
    
    @RequestMapping(value = "/find-produtos-caixa/{codCaixa}", method = RequestMethod.GET)
    public List<ProdutoEnderecar> findProdutosEnderecarCaixa (@PathVariable("codCaixa") int codCaixa) {
    	return enderecoService.findProdutosEnderecarCaixa(codCaixa);
    }
    
    @RequestMapping(value = "/gravar-dados-facilitador", method = RequestMethod.POST)
    public void gravarDadosFacilitador(@RequestBody BodyExpedicao body) {
    	enderecoService.salvarDadosFacilitador(body.referencias, body.deposito, body.blocoInicio, body.corredorInicio, body.boxInicio, body.boxFim, body.produtosSel);
    }
    
    @RequestMapping(value = "/gravar-endereco-caixa", method = RequestMethod.POST)
    public String gravarEnderecoCaixa(@RequestBody BodyExpedicao body) {
    	return enderecoService.gravarEnderecoCaixa(body.codCaixa, body.endereco);
    }
    
    @RequestMapping(value = "/find-caixas-no-endereco/{endereco}", method = RequestMethod.GET)
    public List<ConsultaCaixasNoEndereco> findCaixasNoEndereco (@PathVariable("endereco") String endereco) {
    	return enderecoService.consultaCaixasNoEndereco(endereco);
    }
    
    @RequestMapping(value = "/gravar-parametros-endereco-caixa", method = RequestMethod.POST)
    public void gravarParametrosEnderecoCaixa(@RequestBody BodyExpedicao body) {
    	enderecoService.salvarParametrosEnderecoCaixa(body.deposito, body.ruaInicio, body.ruaFim, body.boxInicio, body.boxFim);
    }
    
    @RequestMapping(value = "/find-parametros-deposito-caixas/{deposito}", method = RequestMethod.GET)
    public ParametrosMapaEnderecoCaixa findCaixasNoEndereco (@PathVariable("deposito") int deposito) {
    	return parametrosEnderecoCaixaRepository.findByDeposito(deposito);
    }
    
    @RequestMapping(value = "/verifica-caixas-endereco", method = RequestMethod.GET)
    public List<ConsultaCaixasNoEndereco> verificaCaixasNoEndereco () {
    	return enderecoService.verificarCaixasNoEndereco();
    }
    
    @RequestMapping(value = "/acertar-calculo-depreciacao", method = RequestMethod.GET)
    public void acertarCalculo () {
    	acertoCalculoDepreciacaoService.inserirMesesCalculo();
    }
    
    @RequestMapping(value = "/find-quant-enderecos/{nivel}/{grupo}/{subGrupo}/{item}/{deposito}", method = RequestMethod.GET)
    public List<ConsultaTag> findQuantEndereco(@PathVariable("nivel") String nivel, @PathVariable("grupo") String grupo, @PathVariable("subGrupo") String subGrupo,
    		@PathVariable("item") String item, @PathVariable("deposito") int deposito) {
    	return enderecoService.findQuantEnderecos(nivel, grupo, subGrupo, item, deposito);
    }
    
    @RequestMapping(value = "/find-produto-by-tag/{numeroTag}", method = RequestMethod.GET)
    public String findProdutoByTag (@PathVariable("numeroTag") String numeroTag) {
    	return enderecoService.findProdutoByTag(numeroTag);
    }
    
    @RequestMapping(value = "/find-historico-by-tag/{numeroTag}", method = RequestMethod.GET)
    public List<ConsultaTag> findHistoricoByTag (@PathVariable("numeroTag") String numeroTag) {
    	return enderecoService.findHistoricoTag(numeroTag);
    }
    
    @RequestMapping(value = "/delete-variacao/{idVariacao}", method = RequestMethod.DELETE)
    public void deleteVariacaoById (@PathVariable("idVariacao") long idVariacao) {
    	enderecoService.deleteVariacaoById(idVariacao);
    }
    
    @RequestMapping(value = "/find-variacao-artigo", method = RequestMethod.GET)
    public List<ConsultaVariacaoArtigo> findVariacaoArtigo() {
    	return enderecoService.findVaricaoArtigo();
    }
    
    @RequestMapping(value = "/save-variacao", method = RequestMethod.POST)
    public List<ConsultaVariacaoArtigo> saveVariacao(@RequestBody BodyVariacaoPesoArtigo body) {
    	enderecoService.saveVariacaoPesoArtigo(body.variacoes);
    	return enderecoService.findVaricaoArtigo();
    }
    
    @RequestMapping(value = "/find-all-transp-async/{leitor}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findVariacaoArtigo(@PathVariable("leitor") String leitor) {
    	return expedicaoCustom.findAllTranspAsync(leitor);
    }
    
    @RequestMapping(value = "/find-all-pedidos-atac-async/{leitor}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllPedidosAtacado(@PathVariable("leitor") String leitor) {
    	return expedicaoCustom.findAllPedidosAtacadoAsync(leitor);
    }
    
    @RequestMapping(value = "/find-all-pedidos-ecom-async/{leitor}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllPedidosEcommerce(@PathVariable("leitor") String leitor) {
    	return expedicaoCustom.findAllPedidosEcommerceAsync(leitor);
    }
    
    @RequestMapping(value = "/find-all-notas-async/{leitor}", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findAllNotasAsync(@PathVariable("leitor") String leitor) {
    	return expedicaoCustom.findAllNotasAsync(leitor);
    }
    
    @RequestMapping(value = "/find-dados-minuta-atacado", method = RequestMethod.POST)
    public List<ConsultaMinutaTransporte> findDadosMinutaAtacado(@RequestBody BodyMinutaTransporte body) {
    	return expedicaoCustom.findDadosMinutaAtacado(body.dataEmiInicio, body.dataEmiFim, body.dataLibPaypalIni, body.dataLibPaypalFim,
    			body.codEmpresa, body.localCaixa, body.transportadora, body.pedido, body.nota);
    }
    
    @RequestMapping(value = "/find-dados-minuta-ecommerce", method = RequestMethod.POST)
    public List<ConsultaMinutaTransporte> findDadosMinutaEcommerce(@RequestBody BodyMinutaTransporte body) {
    	return expedicaoCustom.findDadosMinutaEcommerce(body.dataInicioBox, body.dataFimBox, body.horaInicio, body.horaFim, body.nota, body.transportadora);
    }
    
    @RequestMapping(value = "/gerar-minuta-atacado", method = RequestMethod.POST)
    public String gerarMinutaAtacado(@RequestBody BodyMinutaTransporte body) throws FileNotFoundException, JRException {
    	return enderecoService.gerarMinutaTransporteAtacado(body.dataEmiInicio, body.dataEmiFim, body.dataLibPaypalIni, body.dataLibPaypalFim,
    			body.codEmpresa, body.localCaixa, body.transportadora, body.pedido, body.nota);
    }
    
    @RequestMapping(value = "/gerar-minuta-ecommerce", method = RequestMethod.POST)
    public String gerarMinutaTransporteEcommerce(@RequestBody BodyMinutaTransporte body) throws FileNotFoundException, JRException {
    	return enderecoService.gerarMinutaTransporteEcommerce(body.dataInicioBox, body.dataFimBox, body.horaInicio, body.horaFim, body.nota, body.transportadora);
    }
    
    @RequestMapping(value = "/find-volumes-sem-leitura-ecommerce", method = RequestMethod.POST)
    public List<ConsultaMinutaTransporte> findVolumesSemLeituraEcommerce(@RequestBody BodyMinutaTransporte body) throws FileNotFoundException, JRException {
    	return expedicaoCustom.findVolumesSemLeituraEcom(body.dataInicioBox, body.dataFimBox, body.nota, body.transportadora);
    }
    
    @RequestMapping(value = "/find-volumes-sem-leitura-atacado", method = RequestMethod.POST)
    public List<ConsultaMinutaTransporte> findVolumesSemLeituraAtacado(@RequestBody BodyMinutaTransporte body) throws FileNotFoundException, JRException {
    	return expedicaoCustom.findVolumesSemLeituraAtac(body.dataEmiInicio, body.dataEmiFim, body.codEmpresa, body.transportadora, body.pedido, body.nota);
    }
}
