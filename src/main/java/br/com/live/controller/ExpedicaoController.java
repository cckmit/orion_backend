package br.com.live.controller;

import java.io.FileNotFoundException;
import java.util.List;

import br.com.live.entity.VolumesMinutaTransporte;
import br.com.live.model.*;
import br.com.live.repository.VolumesMinutaRepository;
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
import br.com.live.repository.AberturaCaixasRepository;
import br.com.live.repository.ParametrosEnderecoCaixaRepository;
import br.com.live.repository.ParametrosMapaEndRepository;
import br.com.live.service.AcertoCalculoDepreciacaoService;
import br.com.live.service.ExpedicaoService;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.StatusGravacao;
import net.sf.jasperreports.engine.JRException;

@RestController
@CrossOrigin
@RequestMapping("/endereco")
public class ExpedicaoController {
	
	private ExpedicaoService expedicaoService;
	private ParametrosMapaEndRepository parametrosMapaEndRepository;
	private ExpedicaoCustom expedicaoCustom;
	private AberturaCaixasRepository aberturaCaixasRepository;
	private ParametrosEnderecoCaixaRepository parametrosEnderecoCaixaRepository;
	private AcertoCalculoDepreciacaoService acertoCalculoDepreciacaoService;
    private VolumesMinutaRepository volumesMinutaRepository;
	
    @Autowired
    public ExpedicaoController(ExpedicaoService expedicaoService, ParametrosMapaEndRepository parametrosMapaEndRepository, ExpedicaoCustom expedicaoCustom, AberturaCaixasRepository aberturaCaixasRepository,
    		ParametrosEnderecoCaixaRepository parametrosEnderecoCaixaRepository, AcertoCalculoDepreciacaoService acertoCalculoDepreciacaoService, VolumesMinutaRepository volumesMinutaRepository) {
    	this.expedicaoService = expedicaoService;
    	this.parametrosMapaEndRepository = parametrosMapaEndRepository;
    	this.expedicaoCustom = expedicaoCustom;
    	this.aberturaCaixasRepository = aberturaCaixasRepository;
    	this.parametrosEnderecoCaixaRepository = parametrosEnderecoCaixaRepository;
    	this.acertoCalculoDepreciacaoService = acertoCalculoDepreciacaoService;
        this.volumesMinutaRepository = volumesMinutaRepository;
    }

    @RequestMapping(value = "/find-endereco/{deposito}", method = RequestMethod.GET)
    public List<EnderecoCount> findEnderecoRef(@PathVariable("deposito") int deposito) {
        return expedicaoService.findEnderecoRef(deposito);
    }
    
    @RequestMapping(value = "/find-dados-embarque/{numeroTag}", method = RequestMethod.GET)
    public Embarque findEnderecoRef(@PathVariable("numeroTag") String numeroTag) {
        return expedicaoService.findGrupoEmbarque(numeroTag);
    }
    
    @RequestMapping(value = "/find-dados-modal-endereco/{deposito}/{endereco}", method = RequestMethod.GET)
    public DadosModalEndereco findDadosModalEnd(@PathVariable("deposito") int deposito, @PathVariable("endereco") String endereco) {
        return expedicaoService.findDadosModalEnd(deposito, endereco);
    }
    
    @RequestMapping(value = "/find-parametros/{deposito}", method = RequestMethod.GET)
    public ParametrosMapaEndereco findParametros(@PathVariable("deposito") int deposito) {
        return parametrosMapaEndRepository.findByDeposito(deposito);
    }
    
    @RequestMapping(value = "/gravar-endereco", method = RequestMethod.POST)
    public String findEnderecoRef(@RequestBody BodyExpedicao body) {
    	return expedicaoService.gravarEndereco(body.numeroTag, body.endereco, body.usuario);
    }
    
    @RequestMapping(value = "/gravar-parametros", method = RequestMethod.POST)
    public void gravaParametros(@RequestBody BodyExpedicao body) {
    	expedicaoService.salvarParametros(body.deposito, body.blocoInicio, body.blocoFim, body.corredorInicio, body.corredorFim, body.boxInicio, body.boxFim, body.cestoInicio, body.cestoFim);
    	expedicaoService.gravarEnderecosDeposito(body.deposito);
    }
    
    @RequestMapping(value = "/find-all-capacidades-artigos", method = RequestMethod.GET)
    public List<ConsultaCapacidadeArtigosEnderecos> findAllCapacidadesArtigos() {
        return expedicaoCustom.findArtigosEnderecos();
    }
    
    @RequestMapping(value = "/gravar-capacidades", method = RequestMethod.POST)
    public List<ConsultaCapacidadeArtigosEnderecos> gravarCapacidades(@RequestBody BodyExpedicao body) {
    	expedicaoService.gravarCapacidades(body.itens);
    	return expedicaoCustom.findArtigosEnderecos();
    }
    
    @RequestMapping(value = "/abrir-caixa", method = RequestMethod.POST)
    public String abrirCaixa(@RequestBody BodyExpedicao body) {
    	return expedicaoService.abrirCaixa(body.codCaixa, body.usuario);
    }
    
    @RequestMapping(value = "/find-dados-caixa/{codCaixa}", method = RequestMethod.GET)
    public CaixasParaEnderecar findDadosCaixa(@PathVariable("codCaixa") int codCaixa) {
        return aberturaCaixasRepository.findByNumeroCaixa(codCaixa);
    }
    
    @RequestMapping(value = "/fechar-caixa", method = RequestMethod.POST)
    public CaixasParaEnderecar fecharCaixa(@RequestBody BodyExpedicao body) {
    	expedicaoService.fecharCaixa(body.codCaixa);
    	return aberturaCaixasRepository.findByNumeroCaixa(body.codCaixa);
    }
    
    @RequestMapping(value = "/find-produtos-caixa/{codCaixa}", method = RequestMethod.GET)
    public List<ProdutoEnderecar> findProdutosEnderecarCaixa (@PathVariable("codCaixa") int codCaixa) {
    	return expedicaoService.findProdutosEnderecarCaixa(codCaixa);
    }
    
    @RequestMapping(value = "/gravar-dados-facilitador", method = RequestMethod.POST)
    public void gravarDadosFacilitador(@RequestBody BodyExpedicao body) {
    	expedicaoService.salvarDadosFacilitador(body.referencias, body.deposito, body.blocoInicio, body.corredorInicio, body.boxInicio, body.boxFim, body.produtosSel);
    }
    
    @RequestMapping(value = "/gravar-endereco-caixa", method = RequestMethod.POST)
    public String gravarEnderecoCaixa(@RequestBody BodyExpedicao body) {
    	return expedicaoService.gravarEnderecoCaixa(body.codCaixa, body.endereco);
    }
    
    @RequestMapping(value = "/find-caixas-no-endereco/{endereco}", method = RequestMethod.GET)
    public List<ConsultaCaixasNoEndereco> findCaixasNoEndereco (@PathVariable("endereco") String endereco) {
    	return expedicaoService.consultaCaixasNoEndereco(endereco);
    }
    
    @RequestMapping(value = "/gravar-parametros-endereco-caixa", method = RequestMethod.POST)
    public void gravarParametrosEnderecoCaixa(@RequestBody BodyExpedicao body) {
    	expedicaoService.salvarParametrosEnderecoCaixa(body.deposito, body.ruaInicio, body.ruaFim, body.boxInicio, body.boxFim);
    }
    
    @RequestMapping(value = "/find-parametros-deposito-caixas/{deposito}", method = RequestMethod.GET)
    public ParametrosMapaEnderecoCaixa findCaixasNoEndereco (@PathVariable("deposito") int deposito) {
    	return parametrosEnderecoCaixaRepository.findByDeposito(deposito);
    }
    
    @RequestMapping(value = "/verifica-caixas-endereco", method = RequestMethod.GET)
    public List<ConsultaCaixasNoEndereco> verificaCaixasNoEndereco () {
    	return expedicaoService.verificarCaixasNoEndereco();
    }
    
    @RequestMapping(value = "/acertar-calculo-depreciacao", method = RequestMethod.GET)
    public void acertarCalculo () {
    	acertoCalculoDepreciacaoService.inserirMesesCalculo();
    }
    
    @RequestMapping(value = "/find-quant-enderecos/{nivel}/{grupo}/{subGrupo}/{item}/{deposito}", method = RequestMethod.GET)
    public List<ConsultaTag> findQuantEndereco(@PathVariable("nivel") String nivel, @PathVariable("grupo") String grupo, @PathVariable("subGrupo") String subGrupo,
    		@PathVariable("item") String item, @PathVariable("deposito") int deposito) {
    	return expedicaoService.findQuantEnderecos(nivel, grupo, subGrupo, item, deposito);
    }
    
    @RequestMapping(value = "/find-produto-by-tag/{numeroTag}", method = RequestMethod.GET)
    public String findProdutoByTag (@PathVariable("numeroTag") String numeroTag) {
    	return expedicaoService.findProdutoByTag(numeroTag);
    }
    
    @RequestMapping(value = "/find-historico-by-tag/{numeroTag}", method = RequestMethod.GET)
    public List<ConsultaTag> findHistoricoByTag (@PathVariable("numeroTag") String numeroTag) {
    	return expedicaoService.findHistoricoTag(numeroTag);
    }
    
    @RequestMapping(value = "/delete-variacao/{idVariacao}", method = RequestMethod.DELETE)
    public void deleteVariacaoById (@PathVariable("idVariacao") long idVariacao) {
    	expedicaoService.deleteVariacaoById(idVariacao);
    }
    
    @RequestMapping(value = "/find-variacao-artigo", method = RequestMethod.GET)
    public List<ConsultaVariacaoArtigo> findVariacaoArtigo() {
    	return expedicaoService.findVaricaoArtigo();
    }
    
    @RequestMapping(value = "/save-variacao", method = RequestMethod.POST)
    public List<ConsultaVariacaoArtigo> saveVariacao(@RequestBody BodyVariacaoPesoArtigo body) {
    	expedicaoService.saveVariacaoPesoArtigo(body.variacoes);
    	return expedicaoService.findVaricaoArtigo();
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
    
    @RequestMapping(value = "/find-all-enderecos-ecom-async/{leitor}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllEnderecosEcommerce(@PathVariable("leitor") String leitor) {
    	return expedicaoCustom.findEnderecosVolumes(leitor);
    }
    
    @RequestMapping(value = "/find-dados-minuta-atacado", method = RequestMethod.POST)
    public List<ConsultaMinutaTransporte> findDadosMinutaAtacado(@RequestBody BodyMinutaTransporte body) {
    	return expedicaoCustom.findDadosMinutaAtacado(body.dataEmiInicio, body.dataEmiFim, body.dataLibPaypalIni, body.dataLibPaypalFim,
    			body.codEmpresa, body.localCaixa, body.transportadora, body.pedido, body.nota, body.horaLibPaypalInicio, body.horaLibPaypalFim);
    }
    
    @RequestMapping(value = "/find-dados-minuta-ecommerce", method = RequestMethod.POST)
    public List<ConsultaMinutaTransporte> findDadosMinutaEcommerce(@RequestBody BodyMinutaTransporte body) {
    	return expedicaoCustom.findDadosMinutaEcommerce(body.dataInicioBox, body.dataFimBox, body.horaInicio, body.horaFim, body.nota, 
    			body.transportadora, ConteudoChaveAlfaNum.parseValueToString(body.enderecos));
    }
    
    @RequestMapping(value = "/gerar-minuta-atacado", method = RequestMethod.POST)
    public String gerarMinutaAtacado(@RequestBody BodyMinutaTransporte body) throws FileNotFoundException, JRException {
    	return expedicaoService.gerarMinutaTransporteAtacado(body.notasSelecionadas, body.transportadora);
    }
    
    @RequestMapping(value = "/gerar-minuta-ecommerce", method = RequestMethod.POST)
    public String gerarMinutaTransporteEcommerce(@RequestBody BodyMinutaTransporte body) throws FileNotFoundException, JRException {
    	return expedicaoService.gerarMinutaTransporteEcommerce(body.notasSelecionadas, body.transportadora);
    }
    
    @RequestMapping(value = "/find-volumes-sem-leitura-ecommerce", method = RequestMethod.POST)
    public List<ConsultaMinutaTransporte> findVolumesSemLeituraEcommerce(@RequestBody BodyMinutaTransporte body) throws FileNotFoundException, JRException {
    	return expedicaoCustom.findVolumesSemLeituraEcom(body.dataInicioBox, body.dataFimBox, body.nota, body.transportadora);
    }
    
    @RequestMapping(value = "/find-volumes-sem-leitura-atacado", method = RequestMethod.POST)
    public List<ConsultaMinutaTransporte> findVolumesSemLeituraAtacado(@RequestBody BodyMinutaTransporte body) throws FileNotFoundException, JRException {
    	return expedicaoCustom.findVolumesSemLeituraAtac(body.dataEmiInicio, body.dataEmiFim, body.codEmpresa, body.transportadora, body.pedido, body.nota);
    }
    
    @RequestMapping(value = "/trocar-endereco", method = RequestMethod.POST)
    public void changeEndereco(@RequestBody BodyExpedicao body) {
    	expedicaoService.changeAllocationTAG(body.numeroTag, body.endereco, body.usuario);
    }
    
    @RequestMapping(value = "/limpar-endereco", method = RequestMethod.POST)
    public void cleanEndereco(@RequestBody BodyExpedicao body) {
    	expedicaoService.clearAllocation(body.endereco);
    }
    
    @RequestMapping(value = "/count-parts-allocation/{allocation}", method = RequestMethod.GET)
    public int countPartsAllocation(@PathVariable("allocation") String allocation) {
    	return expedicaoCustom.showCountPartsAllocation(allocation);
    }
    
    @RequestMapping(value = "/find-allocations/{startAllocation}/{endAllocation}", method = RequestMethod.GET)
    public List<ConsultaTag> findAllocations(@PathVariable("startAllocation") String startAllocation, @PathVariable("endAllocation") String endAllocation) {
    	return expedicaoCustom.findAllocations(startAllocation.toUpperCase(), endAllocation.toUpperCase());
    }
    
    @RequestMapping(value = "/clear-multi-allocations", method = RequestMethod.POST)
    public void cleanMultiAllocations(@RequestBody BodyExpedicao body) {
    	expedicaoService.clearMultiAllocations(body.enderecos);
    }
    
    @RequestMapping(value = "/validate-warehouse", method = RequestMethod.POST)
    public StatusGravacao validateWarehouse(@RequestBody BodyExpedicao body) {
    	return expedicaoService.validateWarehouse(body.endereco, body.numeroTag);
    }
    
    @RequestMapping(value = "/find-history-mov-allocation", method = RequestMethod.POST)
    public List<ConsultaTag> findHistoryMovAllocation(@RequestBody BodyExpedicao body) {
    	return expedicaoCustom.findMovsEnderecos(body.endereco.toUpperCase(), body.dataInicio, body.dataFim, ConteudoChaveAlfaNum.parseValueToString(body.usuarioMov), body.tipoMov);
    }
    
    @RequestMapping(value = "/volume-allocation", method = RequestMethod.POST)
    public StatusGravacao volumeAllocation(@RequestBody BodyExpedicao body) {
    	String chaveNFe = body.notaFiscal;
    	int notaFiscal = Integer.parseInt(chaveNFe.substring(28,34));
    	return expedicaoService.allocateBox(body.endereco, body.volume, notaFiscal);
    }
    
    @RequestMapping(value = "/clean-allocation-volume", method = RequestMethod.POST)
    public void cleanAllocationVolume(@RequestBody BodyExpedicao body) {
    	expedicaoCustom.cleanAllocationVolume(body.volume);
    }
    
    @RequestMapping(value = "/count-volumes-sem-enderecar/{chaveNFe}", method = RequestMethod.GET)
    public int countVolumesSemEndereco(@PathVariable("chaveNFe") String chaveNFe) {
    	int notaFiscal = Integer.parseInt(chaveNFe.substring(28,34));
    	String transportadora = expedicaoCustom.findTransportadoraNotaFiscal(notaFiscal);
    	return expedicaoCustom.countVolumeSemEndereco(notaFiscal, transportadora);
    }
    
    @RequestMapping(value = "/validate-volume-enderecado", method = RequestMethod.POST)
    public StatusGravacao validateVolumeEnderecao(@RequestBody BodyExpedicao body) {
    	return expedicaoService.validateVolumeEnderecado(body.volume);
    }
    
    @RequestMapping(value = "/find-all-clientes-async/{leitor}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllClientesAsync(@PathVariable("leitor") String leitor) {
    	return expedicaoCustom.findClientesAsync(leitor);
    }
    
    @RequestMapping(value = "/find-all-representantes-async-col/{leitor}", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findAllRepresentantesAsync(@PathVariable("leitor") String leitor) {
    	return expedicaoCustom.findRepresentanteAsync(leitor);
    }

    @RequestMapping(value = "/find-usuarios-enderecamento", method = RequestMethod.GET)
    public List<ConteudoChaveAlfaNum> findUsuariosEnderecamento() {
        return expedicaoCustom.findUsuariosEnderecamento();
    }
    
    @RequestMapping(value = "/find-quant-movimentacoes", method = RequestMethod.POST)
    public int findQuantMovimentacoes(@RequestBody BodyExpedicao body) {
        return expedicaoCustom.somaQuantidadeMovimentacoes(body.endereco.toUpperCase(), body.dataInicio, body.dataFim, ConteudoChaveAlfaNum.parseValueToString(body.usuarioMov), body.tipoMov);
    }

    @RequestMapping(value = "/gravar-auditoria-transporte", method = RequestMethod.POST)
    public void gravarAuditoriaTransporte(@RequestBody BodyExpedicao body) {
        expedicaoService.gravarAuditoria(body.rua, body.volume);
    }

    @RequestMapping(value = "/consulta-auditoria-transporte", method = RequestMethod.POST)
    public List<ConsultaHistAuditoria> consultaAuditoriaTransporte(@RequestBody BodyExpedicao body) {
        return expedicaoService.consultaHistoricoAuditoria(body.dataInicio, body.dataFim);
    }

    @RequestMapping(value = "/find-minutas-geradas", method = RequestMethod.GET)
    public List<ConteudoChaveNumerica> findMinutasGeradas() {
        return expedicaoCustom.findListMinutasGeradas();
    }

    @RequestMapping(value = "/delete-notas-minuta", method = RequestMethod.POST)
    public List<ConsultaMinutaTransporte> deleteNotasMinuta (@RequestBody BodyMinutaTransporte body) {
        expedicaoService.deleteByNota(body.notasRemoveSelect);
        return expedicaoCustom.consultaRelatorioMinutas(body.minuta, body.dataInicio, body.dataFim);
    }

    @RequestMapping(value = "/consulta-minutas-transporte", method = RequestMethod.POST)
    public List<ConsultaMinutaTransporte> consultaMinutas (@RequestBody BodyMinutaTransporte body) {
        return expedicaoCustom.consultaRelatorioMinutas(body.minuta, body.dataInicio, body.dataFim);
    }

    @RequestMapping(value = "/validar-local-caixa-volumes/{minuta}", method = RequestMethod.GET)
    public StatusGravacao validarLocalCaixaVolumes(@PathVariable("minuta") int minuta) {
        return expedicaoService.validarVolumesMinuta(minuta);
    }

    @RequestMapping(value = "/alterar-local-caixa-volumes", method = RequestMethod.POST)
    public StatusGravacao alterarLocalCaixa(@RequestBody BodyMinutaTransporte body) {
        return expedicaoService.alterarLocalCaixaVolume(body.minuta, body.volume, body.localCaixaVolume);
    }

    @RequestMapping(value = "/total-caixas-minuta/{minuta}", method = RequestMethod.GET)
    public int totalCaixasMinuta(@PathVariable("minuta") int minuta) {
        return expedicaoCustom.totalCaixasMinuta(minuta);
    }

    @RequestMapping(value = "/obter-transportadora-minuta/{minuta}", method = RequestMethod.GET)
    public String obterTransportadoraMinuta(@PathVariable("minuta") int minuta) {
        return expedicaoCustom.obterTransportadoraMinuta(minuta);
    }

    @RequestMapping(value = "/caixas-disponiveis/{statusCaixa}", method = RequestMethod.GET)
    public List<CaixasEsteira> obterCaixasDisponiveis(@PathVariable("statusCaixa") int statusCaixa) {
        return expedicaoService.obterListaCaixasDisponiveis(statusCaixa);
    }

    @RequestMapping(value = "/colocar-caixa-esteira", method = RequestMethod.POST)
    public void colocarCaixaEsteira(@RequestBody BodyExpedicao body) {
        expedicaoService.colocarCaixaEsteira(body.codCaixa);
    }

    @RequestMapping(value = "/retirar-caixa-esteira", method = RequestMethod.POST)
    public void retirarCaixaEsteira(@RequestBody BodyExpedicao body) {
        expedicaoService.retirarCaixaEsteira(body.codCaixa);
    }
}
