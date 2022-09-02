package br.com.live.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import br.com.live.custom.EngenhariaCustom;
import br.com.live.entity.ConsumoFiosLinhas;
import br.com.live.entity.ConsumoMetragemFio;
import br.com.live.entity.MarcasFio;
import br.com.live.entity.Micromovimentos;
import br.com.live.entity.OperacaoXMicromovimentos;
import br.com.live.entity.TempoMaquinaCM;
import br.com.live.entity.TipoPonto;
import br.com.live.entity.TipoPontoFio;
import br.com.live.entity.TiposFio;
import br.com.live.repository.ConsumoFiosLinhasRepository;
import br.com.live.repository.ConsumoMetragemFioRepository;
import br.com.live.repository.MarcasFioRepository;
import br.com.live.repository.MicromovimentosRepository;
import br.com.live.repository.OperXMicromvRepository;
import br.com.live.repository.TempoMaquinaCMRepository;
import br.com.live.repository.TiposFioRepository;
import br.com.live.repository.TiposPontoFioRepository;
import br.com.live.repository.TiposPontoRepository;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.StatusGravacao;

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

	public EngenhariaService(MarcasFioRepository marcasFioRepository, TiposFioRepository tiposFioRepository,
			EngenhariaCustom engenhariaCustom, TiposPontoFioRepository tiposPontoFioRepository,
			TiposPontoRepository tiposPontoRepository, ConsumoFiosLinhasRepository consumoFiosLinhasRepository,
			ConsumoMetragemFioRepository consumoMetragemFioRepository, MicromovimentosRepository micromovimentosRepository,
			TempoMaquinaCMRepository tempoMaquinaCMRepository, OperXMicromvRepository operXMicromvRepository) {
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
		System.out.println("Entrou " + idTempoMaqCM);
		if (engenhariaCustom.existsTempoMaquina(idTempoMaqCM)) 
			return new StatusGravacao(false, "Não é possível excluir o Tempo Máquina, pois o mesmo está em uso no cadastro de Relacionamento Operação X Micromovimentos!", engenhariaCustom.findAllTempoMaquinaCM()); 
		tempoMaquinaCMRepository.deleteById(idTempoMaqCM);
		tempoMaquinaCMRepository.flush();
		return new StatusGravacao(true, "Excluído com sucesso!", engenhariaCustom.findAllTempoMaquinaCM());
	}
	
	public void deleteOperXMicromvById(long id) {
		operXMicromvRepository.deleteById(id);
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
	
	public void saveTempoMaquinaCM(long id, String grupo, String subgrupo, float medida, float tempo) {
		
		TempoMaquinaCM dadosTempoMaq = null;
		
		if (id > 0) dadosTempoMaq = tempoMaquinaCMRepository.findByidTempoMaqCM(id);
		else dadosTempoMaq = tempoMaquinaCMRepository.findByMaqSubMaquinaCM(grupo, subgrupo, medida);
		
		if (dadosTempoMaq == null) { 
			dadosTempoMaq = new TempoMaquinaCM(engenhariaCustom.nextId(), grupo, subgrupo, medida, tempo);
		} else {
			dadosTempoMaq.grupo = grupo;
			dadosTempoMaq.subgrupo = subgrupo;
			dadosTempoMaq.medida = medida;
			dadosTempoMaq.tempo = tempo;
		}
		tempoMaquinaCMRepository.save(dadosTempoMaq);
	}
	
	public void importarTempoMaquinaCM(List<TempoMaquinaCM> tabImportarTempoMaq) {
		for (TempoMaquinaCM dados : tabImportarTempoMaq) {
			saveTempoMaquinaCM(0, dados.grupo, dados.subgrupo, dados.medida, dados.tempo);			
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
	}
	public void atualizaTempoOperacao(int operacao) {
		float tempoTotal = 0;
		float tempoHomem = 0;
		
		List<OperacaoXMicromovimentos> listaSequencia = operXMicromvRepository.findByCodOper(operacao);
		
		for (OperacaoXMicromovimentos dadosSeq : listaSequencia) {
			
			if (dadosSeq.tipo == MICROMOVIMENTO) {
				//Buscar Total de Tempo do Micromovimento
				Micromovimentos dadosMicromv = micromovimentosRepository.findByIdMicroMov(dadosSeq.idMicromovimento);
				if (dadosMicromv != null);
					tempoHomem = dadosMicromv.tempo;
			} else {
				// Buscar Total de Tempo Máquina
				TempoMaquinaCM dadosMaq = tempoMaquinaCMRepository.findByidTempoMaqCM(dadosSeq.idTempoMaquina);
				if (dadosMaq != null);
					tempoHomem = dadosMaq.tempo;
			}
			tempoTotal = tempoTotal + tempoHomem;	
		}
		engenhariaCustom.atualizarTempoHomem(operacao, tempoTotal);
		
	}
}
