package br.com.live.producao.service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import br.com.live.producao.custom.CalendarioCustom;
import br.com.live.producao.custom.ConfeccaoCustom;
import br.com.live.producao.custom.OrdemProducaoCustom;
import br.com.live.producao.entity.CaixaPretaConfeccao;
import br.com.live.producao.entity.CaixaPretaLocalConfeccao;
import br.com.live.producao.entity.CaixaPretaMovimentacao;
import br.com.live.producao.entity.EncolhimentoCad;
import br.com.live.producao.entity.MetasProducao;
import br.com.live.producao.entity.MetasProducaoSemana;
import br.com.live.producao.entity.ObservacaoOrdemPacote;
import br.com.live.producao.entity.Restricoes;
import br.com.live.producao.entity.RestricoesRolo;
import br.com.live.producao.entity.TipoObservacao;
import br.com.live.producao.model.CalendarioSemana;
import br.com.live.producao.model.ConsultaEncolhimentoCad;
import br.com.live.producao.model.ConsultaMovimentacoesCaixaPreta;
import br.com.live.producao.model.ConsultaObservacaoOrdemPacote;
import br.com.live.producao.model.EtiquetasDecoracao;
import br.com.live.producao.model.OrdemConfeccao;
import br.com.live.producao.repository.CaixaPretaConfeccaoRepository;
import br.com.live.producao.repository.CaixaPretaLocalConfeccaoRepository;
import br.com.live.producao.repository.CaixaPretaMovimentacaoRepository;
import br.com.live.producao.repository.EncolhimentoCadRepository;
import br.com.live.producao.repository.MetasProducaoRepository;
import br.com.live.producao.repository.MetasProducaoSemanaRepository;
import br.com.live.producao.repository.ObservacaoOrdemPacoteRepository;
import br.com.live.producao.repository.RestricoesRepository;
import br.com.live.producao.repository.RestricoesRoloRepository;
import br.com.live.producao.repository.TipoObservacaoRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.FormataData;
import br.com.live.util.service.ReportService;

@Service
@Transactional
public class ConfeccaoService {
	private final TipoObservacaoRepository tipoObservacaoRepository;
	private final ObservacaoOrdemPacoteRepository observacaoOrdemPacoteRepository;
	private final ConfeccaoCustom confeccaoCustom;
	private final RestricoesRepository restricoesRepository;
	private final RestricoesRoloRepository restricoesRoloRepository;
	private final MetasProducaoRepository metasProducaoRepository;
	private final MetasProducaoSemanaRepository metasProducaoSemanaRepository;
	private final CalendarioCustom calendarioCustom;
	private final ReportService reportService;
	private final EncolhimentoCadRepository encolhimentoCadRepository;
	private final OrdemProducaoCustom ordemProducaoCustom;
	private final CaixaPretaConfeccaoRepository caixaPretaConfeccaoRepository;
	private final CaixaPretaLocalConfeccaoRepository caixaPretaLocalConfeccaoRepository;
	private final CaixaPretaMovimentacaoRepository caixaPretaMovimentacaoRepository;

	public ConfeccaoService(TipoObservacaoRepository tipoObservacaoRepository, ConfeccaoCustom confeccaoCustom,
			ObservacaoOrdemPacoteRepository observacaoOrdemPacoteRepository, RestricoesRepository restricoesRepository, RestricoesRoloRepository restricoesRoloRepository,
			MetasProducaoRepository metasProducaoRepository, MetasProducaoSemanaRepository metasProducaoSemanaRepository, CalendarioCustom calendarioCustom, 
			ReportService reportService, EncolhimentoCadRepository encolhimentoCadRepository, OrdemProducaoCustom ordemProducaoCustom,
			CaixaPretaConfeccaoRepository caixaPretaConfeccaoRepository, CaixaPretaLocalConfeccaoRepository caixaPretaLocalConfeccaoRepository,
			CaixaPretaMovimentacaoRepository caixaPretaMovimentacaoRepository) {
		this.tipoObservacaoRepository = tipoObservacaoRepository;
		this.confeccaoCustom = confeccaoCustom;
		this.observacaoOrdemPacoteRepository = observacaoOrdemPacoteRepository;
		this.restricoesRepository = restricoesRepository;
		this.restricoesRoloRepository = restricoesRoloRepository;
		this.metasProducaoRepository = metasProducaoRepository;
		this.metasProducaoSemanaRepository = metasProducaoSemanaRepository;
		this.calendarioCustom = calendarioCustom;
		this.reportService = reportService;
		this.encolhimentoCadRepository = encolhimentoCadRepository;
		this.ordemProducaoCustom = ordemProducaoCustom; 
		this.caixaPretaConfeccaoRepository = caixaPretaConfeccaoRepository;
		this.caixaPretaLocalConfeccaoRepository = caixaPretaLocalConfeccaoRepository;
		this.caixaPretaMovimentacaoRepository = caixaPretaMovimentacaoRepository;
	}

	public TipoObservacao saveTipoObservacao(long id, String descricao) {

		TipoObservacao dadosId = tipoObservacaoRepository.findByIdLong(id);

		if (dadosId != null) {
			dadosId.descricao = descricao;
		} else {
			dadosId = new TipoObservacao(confeccaoCustom.findNextIdOrdem(), descricao);
		}
		tipoObservacaoRepository.save(dadosId);

		return dadosId;
	}

	public void deleteById(long id) {
		tipoObservacaoRepository.deleteById(id);
	}
	
	public String deleteMetasById(String idMeta) {
		metasProducaoRepository.deleteById(idMeta);
		return idMeta;
	}
	
	public String deleteMetasSemanaById(String idMeta) {
		metasProducaoSemanaRepository.deleteByIdMes(idMeta);
		return idMeta;
	}
	
	public void deleteObservacaoById(String id) {
		observacaoOrdemPacoteRepository.deleteById(id);
	}

	public void saveObservacao(List<Integer> listEstagio, List<Integer> listOrdemProducao, int ordemConfeccao, int tipoObservacao, String obsAdicional) {
						
			for (Integer ordem : listOrdemProducao) {
				for (Integer estagio : listEstagio) {
					ObservacaoOrdemPacote dadosObs = observacaoOrdemPacoteRepository.findByIdComposto(estagio + "-" + ordem + "-" + ordemConfeccao);
					if (dadosObs == null) {
						Boolean existsEstagio =  ordemProducaoCustom.existsEstagioOrdemProducao(ordem, estagio);
						if(existsEstagio == true) {
							ObservacaoOrdemPacote dadosObsSave = new ObservacaoOrdemPacote(estagio, ordem, ordemConfeccao, tipoObservacao, obsAdicional);
							observacaoOrdemPacoteRepository.save(dadosObsSave);
						}
					} else {
						dadosObs.observacaoAdicional = obsAdicional;
						observacaoOrdemPacoteRepository.save(dadosObs);						
					}
				}				
			}
	}
	
	public List<ConteudoChaveNumerica> findAllPacotesOrdem(int ordemProducao) {
		return confeccaoCustom.findPacotesOrdem(ordemProducao, true);
	}

	public List<ConteudoChaveNumerica> findAllPacotesOrdemSemOpcTodos(int ordemProducao) {
		return confeccaoCustom.findPacotesOrdem(ordemProducao, false);
	}
	
	public List<ConsultaObservacaoOrdemPacote> findAllObsWithQuantidade() {
		return confeccaoCustom.findAllObsWithQuantidade();
	}

	public List<Restricoes> findAllRestricoes() {
		return restricoesRepository.findAll();
	}
	
	public List<MetasProducao> findAllMetasProducao() {
		return metasProducaoRepository.findAll();
	}

	public Restricoes findRestricaoById(long idRestricao) {
		return restricoesRepository.findByIdRestricao(idRestricao);
	}

	public void saveRestricoes(long idRestricao, String descricao) {
		Restricoes dadosRestricao = restricoesRepository.findByIdRestricao(idRestricao);

		if (dadosRestricao == null) {
			dadosRestricao = new Restricoes(confeccaoCustom.findNextIdRestricao(), descricao);
		} else {
			dadosRestricao.descricao = descricao;
		}
		restricoesRepository.save(dadosRestricao);
	}

	public void deleteByIdRestricao(long idRestricao) {
		restricoesRepository.deleteById(idRestricao);
	}

	public void deleteBySeqRestricao(long idSeq) {
		restricoesRoloRepository.deleteById(idSeq);
	}

	public void proxySaveRestricoesPorOrdemBenef(List<ConteudoChaveNumerica> ordens, List<ConteudoChaveNumerica> restricoes) {
		for (ConteudoChaveNumerica dadosOrdens : ordens) {
			List<Integer> rolos = confeccaoCustom.findRolosByOrdem(dadosOrdens.value);
			for (Integer rolo : rolos) {
				for (ConteudoChaveNumerica dadosRestricao : restricoes) {
					saveRestricaoRolo(rolo, dadosRestricao.value);
				}
			}
		}
	}

	public void proxySaveRestricoesRolo(List<ConteudoChaveNumerica> rolos, List<ConteudoChaveNumerica> restricoes) {
		for (ConteudoChaveNumerica dadosRolo : rolos) {
			for ( ConteudoChaveNumerica dadosRestricao : restricoes) {
				saveRestricaoRolo(dadosRolo.value, dadosRestricao.value);
			}
		}
	}

	public void saveRestricaoRolo(int codigoRolo, int restricao) {
		int existeRestricao = confeccaoCustom.validarRestricaoRolo(codigoRolo, restricao);

		if (existeRestricao == 1) return;

		RestricoesRolo dadosSave = new RestricoesRolo(confeccaoCustom.findNextIdRestricaoRolo(), codigoRolo, restricao);
		restricoesRoloRepository.save(dadosSave);
		restricoesRoloRepository.flush();
	}
	
	public String saveMetaProducao(String id, int mes, int ano, int codEstagio, int metaMes, int diasUteis, int metaDiaria, int metaAjustada, int numeroExpedidor, int metaExpedidor) {
		MetasProducao dadosMeta = metasProducaoRepository.findByIdMeta(id);
		
		if (dadosMeta == null) {
			dadosMeta = new MetasProducao(mes, ano, codEstagio, metaMes, diasUteis, metaDiaria, metaAjustada, numeroExpedidor, metaExpedidor);
		} else {
			dadosMeta.metaMes = metaMes; 
			dadosMeta.diasUteis = diasUteis;
			dadosMeta.metaDiaria = metaDiaria;
			dadosMeta.metaAjustada = metaAjustada;
			dadosMeta.numeroExpedidor = numeroExpedidor;
			dadosMeta.metaExpedidor = metaExpedidor;
		}						
		metasProducaoRepository.save(dadosMeta);		
		saveMetaSemana(dadosMeta.id, mes, ano, metaDiaria, metaAjustada);
		
		return dadosMeta.id;
	}
	
	public void saveMetaSemana(String idMesAno, int mes, int ano, int qtdePecasMetaDiaria, int qtdePecasMetaAjustDiaria) {
		
		metasProducaoSemanaRepository.deleteByIdMes(idMesAno);
		
		List<CalendarioSemana> semanas = calendarioCustom.getSemanasByMes(mes, ano);
		
		long id = 0;
		int numSemanaMes = 0;
		int qtdePecasMetaSemana = 0;
		int qtdePecasMetaAjustSemana = 0;
		int qtdePecasMetaTurno = 0;
		int qtdePecasMetaAjustTurno = 0;
		
		for (CalendarioSemana semana : semanas) {
			numSemanaMes++;			
			qtdePecasMetaSemana = semana.getQtdeDiasUteis() * qtdePecasMetaDiaria;
			qtdePecasMetaAjustSemana = semana.getQtdeDiasUteis() * qtdePecasMetaAjustDiaria;
			
			// DIVIDE POR 2 TURNOS DE PRODU��O (ATUALMENTE S� EXISTEM 2 TURNOS)
			qtdePecasMetaTurno = (int) qtdePecasMetaSemana / 2; 
			qtdePecasMetaAjustTurno = (int) qtdePecasMetaAjustSemana / 2; 
			id = metasProducaoSemanaRepository.findNextId();
			MetasProducaoSemana metaProducaoSemana = new MetasProducaoSemana(id, idMesAno, numSemanaMes, semana.getQtdeDiasUteis(), semana.getDataInicio(), semana.getDataFim(), qtdePecasMetaSemana, qtdePecasMetaTurno, qtdePecasMetaAjustSemana, qtdePecasMetaAjustTurno);
			metasProducaoSemanaRepository.save(metaProducaoSemana);
		}
	}

	public String gerarEtiquetasDecoracao(int ordemProducao, List<ConteudoChaveNumerica> pacotes, List<ConteudoChaveNumerica> estagios) throws JRException, FileNotFoundException {
		List<EtiquetasDecoracao> listOrdens = new ArrayList<>();
		List<EtiquetasDecoracao> etiquetasEditGeracao = new ArrayList<>();
		EtiquetasDecoracao dadosEditados = null;
		String fileName = "";
		String reportTittle = "ETIQ_" + ordemProducao;
		
		// Antes do loop, ordene a lista estagios pelo valor (value)
		estagios.sort(Comparator.comparingInt(ConteudoChaveNumerica::getValue));

		listOrdens = confeccaoCustom.consultaEtiquetasDecoracao(ordemProducao, pacotes);
		
		for (ConteudoChaveNumerica estagioSelect : estagios) {
			for (EtiquetasDecoracao ordemSelect : listOrdens) {
			dadosEditados = new EtiquetasDecoracao(ordemSelect.ordemProducao, ordemSelect.ordemConfeccao, ordemSelect.alternativa, ordemSelect.roteiro, ordemSelect.nivel, 
					ordemSelect.referencia,	ordemSelect.tamanho, ordemSelect.cor, ordemSelect.quantidade, ordemSelect.periodo, estagioSelect.label);
			etiquetasEditGeracao.add(dadosEditados);
		}
		}

		JRBeanCollectionDataSource dataSourceEtiquetas = new JRBeanCollectionDataSource(etiquetasEditGeracao);
		fileName = reportService.generateReport("pdf", dataSourceEtiquetas, "etiqueta_decoracao", null, reportTittle, true);

		return fileName;
	}
	
	public void saveEncolhimentoCad(int id, int usuario, Date dataRegistro, String tecido, float largAcomodacao, 
			float compAcomodacao, float largTermo, float compTermo, float largEstampa, float compEstampa, float largEstampaPoli, float compEstampaPoli,
			float largPolimerizadeira, float compPolimerizadeira, float largEstampaPrensa, float compEstampaPrensa, String observacao, int tipo) {
		
		String[] prodConcat = tecido.split("[.]");
		String nivel = prodConcat[0];
		String grupo = prodConcat[1];
		String subgrupo = prodConcat[2];
		String item = prodConcat[3];	
		
		EncolhimentoCad dados = encolhimentoCadRepository.findById(id);
		
		if (dados == null) {
			int idNew = encolhimentoCadRepository.findNextID();
			dados = new EncolhimentoCad(idNew, usuario, dataRegistro, nivel, grupo, subgrupo, item, largAcomodacao, compAcomodacao, largTermo, compTermo, largEstampa, compEstampa, 
										largEstampaPoli, compEstampaPoli, largPolimerizadeira, compPolimerizadeira, largEstampaPrensa, compEstampaPrensa, observacao, tipo);
		} else {
			dados.usuario = usuario; 
			dados.dataRegistro = dataRegistro;
			dados.nivel = nivel;
			dados.grupo = grupo;
			dados.subgrupo = subgrupo;
			dados.item = item;
			dados.largAcomodacao = largAcomodacao; 
			dados.compAcomodacao = compAcomodacao;
			dados.largTermo = largTermo;
			dados.compTermo = compTermo;
			dados.largEstampa = largEstampa;
			dados.compEstampa = compEstampa;
			dados.largEstampaPoli = largEstampaPoli;
			dados.compEstampaPoli = compEstampaPoli;
			dados.largPolimerizadeira = largPolimerizadeira;
			dados.compPolimerizadeira = compPolimerizadeira;
			dados.largEstampaPrensa = largEstampaPrensa;
			dados.compEstampaPrensa = compEstampaPrensa;
			dados.observacao = observacao;
		}						
		encolhimentoCadRepository.save(dados);		
	}
	
	public List<ConsultaEncolhimentoCad> calcularMediaPorProduto(String produto, int tipo){
		
		String[] prodConcat = produto.split("[.]");
		String nivel = prodConcat[0];
		String grupo = prodConcat[1];
		String subgrupo = prodConcat[2];
		
		return confeccaoCustom.consultaMediaPorGrupoSubGrupo(nivel, grupo, subgrupo, tipo);
	}
	
	public List<ConsultaMovimentacoesCaixaPreta> findAllCaixa(){
		return confeccaoCustom.findAllCaixa();
	}
	
	public List<ConsultaMovimentacoesCaixaPreta> findAllLocais(){
		return confeccaoCustom.findAllLocais();
	}
	
	public List<ConteudoChaveNumerica> findCentroCusto(int centroCusto){
		return confeccaoCustom.findCentroCusto(centroCusto);
	}
	
	public List<ConteudoChaveNumerica> findAllLocaisSelect(){
		return confeccaoCustom.findAllLocaisSelect();
	}
	
	public int findNextIdCaixa() {
		return caixaPretaConfeccaoRepository.findNextId();
	}
	
	public List<ConsultaMovimentacoesCaixaPreta> findAllMovimentacoesCaixas(){
		return confeccaoCustom.findAllMovimentacoesCaixas();
	}
	
	public List<ConsultaMovimentacoesCaixaPreta> findLocalizacaoCaixas(){
		return confeccaoCustom.findLocalizacaoCaixas();
	}
	
	public void saveCaixaPreta(int idCaixa, int centroCusto, String descricao, int forma) {
		
		CaixaPretaConfeccao dadosCaixa = caixaPretaConfeccaoRepository.findByidCaixa(idCaixa);
		// Obtém a data e hora atual
        Date agora = new Date();
        Date dataCadastro = agora;
        Date ultimaAlteracao = agora;
        
		if(forma == 1) {			
	        ultimaAlteracao = null;
		} 
		
		if (dadosCaixa == null) {
			int idNew = caixaPretaConfeccaoRepository.findNextId();
			dadosCaixa = new CaixaPretaConfeccao(idNew, centroCusto, descricao, dataCadastro, ultimaAlteracao, 0);
		} else {
			dadosCaixa.centroCusto = centroCusto; 
			dadosCaixa.descricao = descricao;
			dadosCaixa.ultimaAlteracao = ultimaAlteracao;
			dadosCaixa.situacao = 0;
			
		}						
		caixaPretaConfeccaoRepository.save(dadosCaixa);		
	}
	
	public void saveLocalCaixa(int id, String descricao, int forma) {
		
		CaixaPretaLocalConfeccao dadosLocal = caixaPretaLocalConfeccaoRepository.findByid(id);
		// Obtém a data e hora atual
        Date agora = new Date();
        Date dataCadastro = agora;
        Date ultimaAlteracao = agora;
        
		if(forma == 1) {			
	        ultimaAlteracao = null;
		} 
		
		if (dadosLocal == null) {
			int idNew = caixaPretaLocalConfeccaoRepository.findNextId();
			dadosLocal = new CaixaPretaLocalConfeccao(idNew, descricao, dataCadastro, ultimaAlteracao, 0);
		} else {
			dadosLocal.descricao = descricao;
			dadosLocal.ultimaAlteracao = ultimaAlteracao;
			dadosLocal.situacao = 0;
			
		}						
		caixaPretaLocalConfeccaoRepository.save(dadosLocal);		
	}
	
	public CaixaPretaConfeccao findCaixaById(int id) {
		return caixaPretaConfeccaoRepository.findByidCaixa(id);
	}
	
	public CaixaPretaLocalConfeccao findLocalById(int id) {
		return caixaPretaLocalConfeccaoRepository.findByid(id);
	}
	
	public void deleteCaixaPretaById(int id) {
		caixaPretaConfeccaoRepository.deleteById(id);
	}
	
	public void deleteLocalCaixaPreta(int id) {
		caixaPretaLocalConfeccaoRepository.deleteById(id);
	}
	
	public Map<String, Object> setParameters(String codCaixa) {
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("codCaixa", codCaixa);

		return parameters;
	}
	
	public String gerarEtiquetaCaixaPreta(String idCaixa) throws FileNotFoundException, JRException {
		
		String codCaixa = confeccaoCustom.findCodCaixaCentroCusto(idCaixa);
		System.out.println(codCaixa);
		String nomeRelatorioGerado = "";
		
		Map<String, Object> parameters = setParameters(codCaixa);

		nomeRelatorioGerado = reportService.generateReport("pdf", null, "gerar_etiqueta_caixa_preta", parameters, "EtiquetaCaixaPreta", false);
		

		return nomeRelatorioGerado;
	}
	
	public String findCodCaixaPreta(String codCaixa) {
		return confeccaoCustom.findCodCaixaPreta(codCaixa);
	}
	
	public void saveMovimentacaoCaixaPreta(String codCaixa, int centroCusto, int localDestino, int idUsuario) {
		
		CaixaPretaMovimentacao dadosMovimentacao = null;
		String primeiroQuatroDigitos = codCaixa.substring(0, 4);
		int caixa = Integer.parseInt(primeiroQuatroDigitos);
		
		// Obtém a data e hora atual
        Date agora = new Date();
        String hora =  agora.getHours() + ":" + agora.getMinutes() + ":" + agora.getSeconds();
        
    	dadosMovimentacao = new CaixaPretaMovimentacao(caixaPretaMovimentacaoRepository.findNextId(), idUsuario, caixa, centroCusto, agora, hora, localDestino);
		caixaPretaMovimentacaoRepository.save(dadosMovimentacao);
	}
}
