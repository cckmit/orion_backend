package br.com.live.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import br.com.live.custom.EngenhariaCustom;
import br.com.live.entity.ConsumoFiosLinhas;
import br.com.live.entity.ConsumoMetragemFio;
import br.com.live.entity.MarcasFio;
import br.com.live.entity.TipoPonto;
import br.com.live.entity.TipoPontoFio;
import br.com.live.entity.TiposFio;
import br.com.live.repository.ConsumoFiosLinhasRepository;
import br.com.live.repository.ConsumoMetragemFioRepository;
import br.com.live.repository.MarcasFioRepository;
import br.com.live.repository.TiposFioRepository;
import br.com.live.repository.TiposPontoFioRepository;
import br.com.live.repository.TiposPontoRepository;
import br.com.live.util.ConteudoChaveNumerica;

@Service
public class EngenhariaService {

	private final MarcasFioRepository marcasFioRepository;
	private final TiposFioRepository tiposFioRepository;
	private final EngenhariaCustom engenhariaCustom;
	private final TiposPontoFioRepository tiposPontoFioRepository;
	private final TiposPontoRepository tiposPontoRepository;
	private final ConsumoFiosLinhasRepository consumoFiosLinhasRepository;
	private final ConsumoMetragemFioRepository consumoMetragemFioRepository;

	public EngenhariaService(MarcasFioRepository marcasFioRepository, TiposFioRepository tiposFioRepository,
			EngenhariaCustom engenhariaCustom, TiposPontoFioRepository tiposPontoFioRepository,
			TiposPontoRepository tiposPontoRepository, ConsumoFiosLinhasRepository consumoFiosLinhasRepository,
			ConsumoMetragemFioRepository consumoMetragemFioRepository) {
		this.marcasFioRepository = marcasFioRepository;
		this.tiposFioRepository = tiposFioRepository;
		this.engenhariaCustom = engenhariaCustom;
		this.tiposPontoFioRepository = tiposPontoFioRepository;
		this.tiposPontoRepository = tiposPontoRepository;
		this.consumoFiosLinhasRepository = consumoFiosLinhasRepository;
		this.consumoMetragemFioRepository = consumoMetragemFioRepository;
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
}