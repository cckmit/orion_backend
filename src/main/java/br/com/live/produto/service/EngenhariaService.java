package br.com.live.produto.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import br.com.live.comercial.model.ConsultaTransportadora;
import br.com.live.produto.custom.EngenhariaCustom;
import br.com.live.produto.entity.ConsumoFiosLinhas;
import br.com.live.produto.entity.ConsumoMetragemFio;
import br.com.live.produto.entity.MarcasFio;
import br.com.live.produto.entity.Micromovimentos;
import br.com.live.produto.entity.OperacaoXMicromovimentos;
import br.com.live.produto.entity.TempoMaquinaCM;
import br.com.live.produto.entity.TipoPonto;
import br.com.live.produto.entity.TipoPontoFio;
import br.com.live.produto.entity.TiposFio;
import br.com.live.produto.model.ConsultaOperacaoXMicromovimentos;
import br.com.live.produto.repository.ConsumoFiosLinhasRepository;
import br.com.live.produto.repository.ConsumoMetragemFioRepository;
import br.com.live.produto.repository.MarcasFioRepository;
import br.com.live.produto.repository.MicromovimentosRepository;
import br.com.live.produto.repository.OperXMicromvRepository;
import br.com.live.produto.repository.TempoMaquinaCMRepository;
import br.com.live.produto.repository.TiposFioRepository;
import br.com.live.produto.repository.TiposPontoFioRepository;
import br.com.live.produto.repository.TiposPontoRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.StatusGravacao;
import br.com.live.util.service.ReportService;

@Transactional
@Service
public class EngenhariaService {

	private static final int MICROMOVIMENTO = 1;
	private static final int TEMPO_MAQUINA = 2;
	private final MarcasFioRepository marcasFioRepository;
	private final TiposFioRepository tiposFioRepository;
	private final EngenhariaCustom engenhariaCustom;
	private final TiposPontoFioRepository tiposPontoFioRepository;
	private final TiposPontoRepository tiposPontoRepository;
	private final ConsumoFiosLinhasRepository consumoFiosLinhasRepository;
	private final ConsumoMetragemFioRepository consumoMetragemFioRepository;
	private final MicromovimentosRepository micromovimentosRepository;
	private final TempoMaquinaCMRepository tempoMaquinaCMRepository;
	private final OperXMicromvRepository operXMicromvRepository;
	private final ReportService reportService;

	public EngenhariaService(MarcasFioRepository marcasFioRepository, TiposFioRepository tiposFioRepository,
			EngenhariaCustom engenhariaCustom, TiposPontoFioRepository tiposPontoFioRepository,
			TiposPontoRepository tiposPontoRepository, ConsumoFiosLinhasRepository consumoFiosLinhasRepository,
			ConsumoMetragemFioRepository consumoMetragemFioRepository, MicromovimentosRepository micromovimentosRepository,
			TempoMaquinaCMRepository tempoMaquinaCMRepository, OperXMicromvRepository operXMicromvRepository, ReportService reportService) {
		this.marcasFioRepository = marcasFioRepository;
		this.tiposFioRepository = tiposFioRepository;
		this.engenhariaCustom = engenhariaCustom;
		this.tiposPontoFioRepository = tiposPontoFioRepository;
		this.tiposPontoRepository = tiposPontoRepository;
		this.consumoFiosLinhasRepository = consumoFiosLinhasRepository;
		this.consumoMetragemFioRepository = consumoMetragemFioRepository;
		this.micromovimentosRepository = micromovimentosRepository;
		this.tempoMaquinaCMRepository = tempoMaquinaCMRepository;
		this.operXMicromvRepository = operXMicromvRepository;
		this.reportService = reportService;
	}

	public MarcasFio saveMarcas(int id, String descricao) {

		MarcasFio marcas = null;

		if (id == 0) {
			id = engenhariaCustom.findNewIdMarcas();
			marcas = new MarcasFio(id, descricao);
		} else {
			marcas = marcasFioRepository.findByIdMarcas(id);
			marcas.descricao = descricao;
		}
		marcasFioRepository.save(marcas);

		return marcasFioRepository.findByIdMarcas(id);
	}

	public TiposFio saveTipos(int id, String descricao, int titulo, int centimetrosCone, int centimetrosCone2,
			int centimetrosCone3) {

		TiposFio tipos = null;

		if (id == 0) {
			id = engenhariaCustom.findNewIdTipos();
			tipos = new TiposFio(id, descricao, titulo, centimetrosCone, centimetrosCone2, centimetrosCone3);
		} else {
			tipos = tiposFioRepository.findByIdTipo(id);
			tipos.descricao = descricao;
			tipos.titulo = titulo;
			tipos.centimetroCone = centimetrosCone;
			tipos.centimetroCone2 = centimetrosCone2;
			tipos.centimetroCone3 = centimetrosCone3;
		}
		tiposFioRepository.save(tipos);

		return tiposFioRepository.findByIdTipo(id);
	}

	public TipoPonto saveTiposPonto(int id, String descricao) {
		TipoPonto tipos = null;

		if (id == 0) {
			id = engenhariaCustom.findIdNewTipoPonto();
			tipos = new TipoPonto(id, descricao);
		} else {
			tipos = tiposPontoRepository.findByIdTipoPonto(id);
			tipos.descricao = descricao;
		}
		tiposPontoRepository.save(tipos);

		return tiposPontoRepository.findByIdTipoPonto(id);
	}

	public TipoPontoFio saveTiposPontoFio(int idTipoPonto, String idRegistro, int tipoFio1, int tipoFio2, int tipoFio3,
			float consumoFio, String descricao) {
		TipoPontoFio dadosTipoPontoFio = null;

		dadosTipoPontoFio = tiposPontoFioRepository.findByIdTipoPontoFio(idRegistro);

		if (dadosTipoPontoFio == null) {

			int sequencia = engenhariaCustom.findIdNewSequenciaPontoFio(idTipoPonto);

			dadosTipoPontoFio = new TipoPontoFio(sequencia, descricao, idTipoPonto, consumoFio);

		} else {
			dadosTipoPontoFio.consumoFio = consumoFio;
			dadosTipoPontoFio.descricao = descricao;
		}

		tiposPontoFioRepository.save(dadosTipoPontoFio);

		return dadosTipoPontoFio;
	}

	public ConsumoFiosLinhas saveConsumo(String id, String referencia, int idTipoPonto, float comprimentoCostura) {
		ConsumoFiosLinhas consumo = null;

		consumo = consumoFiosLinhasRepository.findConsumoById(id);

		if (consumo == null) {

			int sequencia = engenhariaCustom.findProxSequenciaTipoPonto(idTipoPonto, referencia);

			consumo = new ConsumoFiosLinhas(referencia.toUpperCase(), idTipoPonto, sequencia, comprimentoCostura);
			consumoFiosLinhasRepository.saveAndFlush(consumo);
			insertFiosTipoDePonto(idTipoPonto, referencia.toUpperCase(), sequencia);
		} else {
			consumo.comprimentoCostura = comprimentoCostura;
			consumoFiosLinhasRepository.saveAndFlush(consumo);
			CalculaConsumoFios(id);
		}

		return consumo;
	}

	public void insertFiosTipoDePonto(int idTipoPonto, String referencia, int sequencia) {
		List<TipoPontoFio> fios = tiposPontoFioRepository.findByIdTipoPonto(idTipoPonto);

		for (TipoPontoFio fio : fios) {
			ConsumoMetragemFio consumoMetragem = new ConsumoMetragemFio(fio.sequencia, referencia,
					referencia + "-" + idTipoPonto + "-" + sequencia, idTipoPonto,
					0, fio.consumoFio, 0, 0, 0, " ");

			consumoMetragemFioRepository.save(consumoMetragem);
		}
	}

	public void CalculaConsumoFios(String idConsumo) {

		List<ConsumoMetragemFio> tiposFioGravados = new ArrayList<ConsumoMetragemFio>();
		tiposFioGravados = consumoMetragemFioRepository.findConsumoMetragemFioByIdReferencia(idConsumo);
		
		for (ConsumoMetragemFio dadosFioGrav : tiposFioGravados) {
			if (dadosFioGrav.pacote > 0)
				calculoConsumoFioAvulso(dadosFioGrav.id);
		}
	}

	public void calculoConsumoFioAvulso(String idConsumo) {
		ConsumoMetragemFio consumoMetragem = consumoMetragemFioRepository.findConsumoMetragemFioById(idConsumo);
		ConsumoFiosLinhas consumoCapa = consumoFiosLinhasRepository.findConsumoById(consumoMetragem.idReferencia);

		float metragemTotal = (consumoMetragem.metragemCosturaCm * consumoCapa.comprimentoCostura) / 100;

		float metragemUm = metragemTotal / consumoMetragem.pacote;

		consumoMetragem.metragemTotal = metragemTotal;
		consumoMetragem.metragemUm = metragemUm;

		consumoMetragemFioRepository.saveAndFlush(consumoMetragem);
	}

	public void atualizarPacote(int pacoteAtualizado, String idConsumo, int tipoFioAtualizado, String observacao) {
		ConsumoMetragemFio consumoMetragem = consumoMetragemFioRepository.findConsumoMetragemFioById(idConsumo);

		consumoMetragem.pacote = pacoteAtualizado;
		consumoMetragem.idTipoFio = tipoFioAtualizado;
		consumoMetragem.observacao = observacao;

		consumoMetragemFioRepository.saveAndFlush(consumoMetragem);
		calculoConsumoFioAvulso(idConsumo);
	}

	public List<ConteudoChaveNumerica> makeListOptionPackages(int tipoFio) {

		List<ConteudoChaveNumerica> listPacotes = new ArrayList<ConteudoChaveNumerica>();

		if (tipoFio <= 0)
			return listPacotes;

		TiposFio dadosFio = null;
		ConteudoChaveNumerica pacote1 = null;
		ConteudoChaveNumerica pacote2 = null;
		ConteudoChaveNumerica pacote3 = null;

		dadosFio = tiposFioRepository.findByIdTipo(tipoFio);

		if (dadosFio.centimetroCone > 0) {
			pacote1 = new ConteudoChaveNumerica(dadosFio.centimetroCone, Integer.toString(dadosFio.centimetroCone));
			listPacotes.add(pacote1);
		}

		if (dadosFio.centimetroCone2 > 0) {
			pacote2 = new ConteudoChaveNumerica(dadosFio.centimetroCone2, Integer.toString(dadosFio.centimetroCone2));
			listPacotes.add(pacote2);
		}

		if (dadosFio.centimetroCone3 > 0) {
			pacote3 = new ConteudoChaveNumerica(dadosFio.centimetroCone3, Integer.toString(dadosFio.centimetroCone3));
			listPacotes.add(pacote3);
		}

		return listPacotes;
	}

	public void deleteMarcas(int idMarcas) {
		marcasFioRepository.deleteById(idMarcas);
	}

	public void deleteTipos(int idTipos) {
		tiposFioRepository.deleteById(idTipos);
	}

	public void deleteTipoPonto(int idTipoPonto) {
		tiposPontoFioRepository.deleteByIdTipoPonto(idTipoPonto);
		tiposPontoRepository.deleteById(idTipoPonto);
	}

	public void deleteTipoPontoFio(String idComposto) {
		tiposPontoFioRepository.deleteById(idComposto);
	}

	public void deleteConsumoFiosLinhas(String idComposto) {
		engenhariaCustom.deleteConsumoByIdReferencia(idComposto);
		consumoFiosLinhasRepository.deleteById(idComposto);
	}
	
	public StatusGravacao deleteMicroMovimentoById(String idMicroMov) {
		if (engenhariaCustom.existsMicromovimento(idMicroMov)) 
			return new StatusGravacao(false, "Não é possível excluir o Micromovimento, pois o mesmo está em uso no cadastro de Relacionamento Operação X Micromovimentos!", findAllMicromovimentos()); 
		micromovimentosRepository.deleteById(idMicroMov);
		return new StatusGravacao(true, "Excluído com sucesso!", findAllMicromovimentos());
	}
	
	public StatusGravacao deleteTempoMaquina(long idTempoMaqCM) {
		if (engenhariaCustom.existsTempoMaquina(idTempoMaqCM)) 
			return new StatusGravacao(false, "Não é possível excluir o Tempo Máquina, pois o mesmo está em uso no cadastro de Relacionamento Operação X Micromovimentos!", engenhariaCustom.findAllTempoMaquinaCM()); 
		tempoMaquinaCMRepository.deleteById(idTempoMaqCM);
		tempoMaquinaCMRepository.flush();
		return new StatusGravacao(true, "Excluído com sucesso!", engenhariaCustom.findAllTempoMaquinaCM());
	}
	
	public void deleteOperXMicromvById(List<Long> listId) {
		for (Long idmicromv : listId) {
			operXMicromvRepository.deleteById(idmicromv);	
		}
	}
	
	public List<Micromovimentos> findAllMicromovimentos() {
		return micromovimentosRepository.findAll();
	}
	
	public List<OperacaoXMicromovimentos> findAllOperMicromovimentos() {
		return operXMicromvRepository.findAll();
	}
	
	public Micromovimentos findMicroMovimentoById(String idMicroMov) {
		return micromovimentosRepository.findByIdMicroMov(idMicroMov);
	}
	
	public String saveMicromovimentos(String codigo, String descricao, float tempo, float interferencia, boolean editMode) {
		String msgErro = "";
		
		Micromovimentos dadosMicromov = micromovimentosRepository.findByIdMicroMov(codigo.toUpperCase());
		
		if ((editMode == false) && (dadosMicromov != null)) {
			msgErro = " Código " + codigo + " já existente!";
			return msgErro;
		}
		
		if (dadosMicromov == null) {
			dadosMicromov = new Micromovimentos(codigo.toUpperCase(), descricao, tempo, interferencia);
		} else {
			dadosMicromov.descricao = descricao;
			dadosMicromov.tempo = tempo;
			dadosMicromov.interferencia = interferencia;
		}
		micromovimentosRepository.save(dadosMicromov);
		
		return msgErro;
	}
	
	public void importarMicromovimentos(List<Micromovimentos> tabImportar) {
		for (Micromovimentos micromov : tabImportar) {
			Micromovimentos newMicromov = new Micromovimentos(micromov.id, micromov.descricao, micromov.tempo, micromov.interferencia);
			micromovimentosRepository.save(newMicromov);
		}
	}
	public void deleteTempoMaquinaCMById(long idTempoMaqCM) {
		tempoMaquinaCMRepository.deleteById(idTempoMaqCM);
	}
	
	public List<TempoMaquinaCM> findAllTempoMaquinaCM() {
		return tempoMaquinaCMRepository.findAllTempMaq();
	}
	
	public TempoMaquinaCM findTempoMaquinaCMById(long idTempoMaqCM) {
		return tempoMaquinaCMRepository.findByidTempoMaqCM(idTempoMaqCM);
	}
	
	public void saveTempoMaquinaCM(long id, String grupo, String subgrupo, float medida, float tempo, float interferencia) {
		
		TempoMaquinaCM dadosTempoMaq = null;
		
		if (id > 0) dadosTempoMaq = tempoMaquinaCMRepository.findByidTempoMaqCM(id);
		else dadosTempoMaq = tempoMaquinaCMRepository.findByMaqSubMaquinaCM(grupo, subgrupo, medida);
		
		if (dadosTempoMaq == null) { 
			dadosTempoMaq = new TempoMaquinaCM(engenhariaCustom.nextId(), grupo, subgrupo, medida, tempo, interferencia);
		} else {
			dadosTempoMaq.grupo = grupo;
			dadosTempoMaq.subgrupo = subgrupo;
			dadosTempoMaq.medida = medida;
			dadosTempoMaq.tempo = tempo;
			dadosTempoMaq.interferencia = interferencia;
		}
		tempoMaquinaCMRepository.save(dadosTempoMaq);
	}
	
	public void importarTempoMaquinaCM(List<TempoMaquinaCM> tabImportarTempoMaq) {
		for (TempoMaquinaCM dados : tabImportarTempoMaq) {
			saveTempoMaquinaCM(0, dados.grupo, dados.subgrupo, dados.medida, dados.tempo, dados.interferencia);			
		}
	}
		
	public void saveOperXMultiplosMicromovimento(int codOperacao, int tipo, List<String> listIdMicromovimento, List<Integer> listIdTempoMaquina, int seqInserir) {		
		if (tipo == MICROMOVIMENTO) {
			for (String idMicromovimento : listIdMicromovimento) {				
				// Se for inserido mais que 1 registro, deve alocá-los ao final, caso contrário deixa inserir na sequência informada.
				if (listIdMicromovimento.size() > 1) seqInserir = engenhariaCustom.findNextSequecia(codOperacao);					
				saveOperXMicromovimento(0, codOperacao, seqInserir, tipo, idMicromovimento, 0);			
			}		
		} else {		
			for (Integer IdTempoMaquina : listIdTempoMaquina) {						
				// Se for inserido mais que 1 registro, deve alocá-los ao final, caso contrário deixa inserir na sequência informada.
				if (listIdTempoMaquina.size() > 1) seqInserir = engenhariaCustom.findNextSequecia(codOperacao);
				saveOperXMicromovimento(0, codOperacao, seqInserir, tipo, "", IdTempoMaquina);			
			}
		}
	}
	
	public void saveOperXMicromovimento(long id, int codOperacao, int sequencia, int tipo, String idMicromovimento, int idTempoMaquina) {
		
		OperacaoXMicromovimentos dadosOperXMicromv = operXMicromvRepository.findById(id);
		
		if (dadosOperXMicromv == null) { 
			id = operXMicromvRepository.findNextID();
			dadosOperXMicromv = new OperacaoXMicromovimentos(id, codOperacao, sequencia, tipo, idMicromovimento, idTempoMaquina);
		} else {
			dadosOperXMicromv.sequencia = sequencia;
			dadosOperXMicromv.tipo = tipo;
			dadosOperXMicromv.idMicromovimento = "";
			dadosOperXMicromv.idTempoMaquina = 0;
			if (tipo == MICROMOVIMENTO) dadosOperXMicromv.idMicromovimento = idMicromovimento;
			if (tipo == TEMPO_MAQUINA) dadosOperXMicromv.idTempoMaquina = idTempoMaquina;				
		}
		operXMicromvRepository.save(dadosOperXMicromv);
		operXMicromvRepository.flush();
	}
	
	public float atualizaTempoOperacao(int operacao) {
		float tempoTotal = 0;
		float tempoMicromv = 0;
		float tempoMaquina = 0;
		
		List<OperacaoXMicromovimentos> listaSequencia = operXMicromvRepository.findByCodOper(operacao);
		
		for (OperacaoXMicromovimentos dadosSeq : listaSequencia) {			
			if (dadosSeq.tipo == MICROMOVIMENTO) {
				//Buscar Total de Tempo do Micromovimento
				Micromovimentos dadosMicromv = micromovimentosRepository.findByIdMicroMov(dadosSeq.idMicromovimento);
				if (dadosMicromv != null);
					tempoMicromv = (float) (((dadosMicromv.interferencia / 100) * dadosMicromv.tempo) + dadosMicromv.tempo);
					tempoTotal = tempoTotal + tempoMicromv;	
			} else {
				// Buscar Total de Tempo Máquina
				TempoMaquinaCM dadosMaq = tempoMaquinaCMRepository.findByidTempoMaqCM(dadosSeq.idTempoMaquina);				
				if (dadosMaq != null);
					tempoMaquina = (float) (((dadosMaq.interferencia * dadosMaq.tempo) / 100) + dadosMaq.tempo);					
					tempoTotal = tempoTotal + tempoMaquina;
			}		
		}
		
		tempoTotal = (float) (tempoTotal + ((1.86 * tempoTotal) / 100));
		
		// TODO - REMOVIDO TEMPORARIAMENTE
		//engenhariaCustom.updateMicroMovimentoGenericoSystextil(operacao, tempoTotal);
		//engenhariaCustom.updateOperacaoXMicroMovimentoGenericoSystextil(operacao, tempoTotal);
		engenhariaCustom.atualizarTempoHomem(operacao, tempoTotal);
		
		return tempoTotal;
	}
	
	public void atualizarFichaDigital(List<String> referencias) {		
		for (String referencia : referencias) {			
			engenhariaCustom.atualizarFichaDigital(referencia);
		}		
	}
	public void copiarMicromovimentosDePara (int codOperacaoOrigem, int codOperacaoDestino) {
		operXMicromvRepository.deleteByCodOperacao(codOperacaoDestino);
		List<OperacaoXMicromovimentos> microMovimentosOrigem = operXMicromvRepository.findByCodOper(codOperacaoOrigem);
		for (OperacaoXMicromovimentos microMovimento : microMovimentosOrigem) {
			long id = operXMicromvRepository.findNextID();
			OperacaoXMicromovimentos micromovimentoDestino = new OperacaoXMicromovimentos(id, codOperacaoDestino, microMovimento.sequencia, microMovimento.tipo, microMovimento.idMicromovimento, microMovimento.idTempoMaquina);
			operXMicromvRepository.save(micromovimentoDestino);
		}
	}
	
	public void resequenciarOper(int operacao, int intervalo) {
		List<OperacaoXMicromovimentos> listaSequencia = operXMicromvRepository.findByCodOper(operacao);
		int seqIncrement = intervalo;
		
		for (OperacaoXMicromovimentos dado : listaSequencia){
			dado.sequencia = seqIncrement;
			operXMicromvRepository.save(dado);
			seqIncrement = seqIncrement + intervalo;			
		}
	}

	public String gerarPdfMicromovimentos(List<ConsultaOperacaoXMicromovimentos> listMicromov, String operacao, String maquina, float interferencia, float tempoNormal) throws JRException, FileNotFoundException {
		String nomeRelatorioGerado = "";

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listMicromov);

		Map<String, Object> parameters = setParameters(operacao, maquina, interferencia, tempoNormal);

		nomeRelatorioGerado = reportService.generateReport("pdf", dataSource, "micromovimentos", parameters, operacao, false);

		return nomeRelatorioGerado;
	}

	public Map<String, Object> setParameters(String operacao, String maquina, float interferencia, float tempoNormal) {

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("operacao", operacao);
		parameters.put("maquina", maquina);
		parameters.put("interferencia", interferencia);
		parameters.put("tempoNormal", tempoNormal);

		return parameters;
	}
}
