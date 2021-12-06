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

	public TipoPontoFio saveTiposPontoFio(int idTipoPonto, String idRegistro, int tipoFio1, int tipoFio2, int tipoFio3, float consumoFio, String descricao) {
		TipoPontoFio dadosTipoPontoFio = null;

		dadosTipoPontoFio = tiposPontoFioRepository.findByIdTipoPontoFio(idRegistro);

		if (dadosTipoPontoFio == null) {

			int sequencia = engenhariaCustom.findIdNewSequenciaPontoFio(idTipoPonto);
			
			dadosTipoPontoFio = new TipoPontoFio(sequencia, descricao, idTipoPonto, tipoFio1, tipoFio2, tipoFio3, consumoFio);

		} else {
			dadosTipoPontoFio.consumoFio = consumoFio;
		}

		tiposPontoFioRepository.save(dadosTipoPontoFio);

		return dadosTipoPontoFio;
	}

	public ConsumoFiosLinhas saveConsumo(String id, String referencia, int idTipoPonto, float comprimentoCostura) {
		ConsumoFiosLinhas consumo = null;

		consumo = consumoFiosLinhasRepository.findConsumoById(id);

		if (consumo == null) {
			consumo = new ConsumoFiosLinhas(referencia, idTipoPonto, comprimentoCostura);
		} else {
			consumo.comprimentoCostura = comprimentoCostura;
		}

		consumoFiosLinhasRepository.save(consumo);

		return consumo;
	}

	public void CalculaConsumoFio(int idTipoPonto, String idConsumo, String referencia) {

		ConsumoMetragemFio consumoMetragem;
		TiposFio dadosFio = null;
		float metragemTotal;
		float metragemUm;

		List<TipoPontoFio> tiposFio = new ArrayList<TipoPontoFio>();
		List<ConsumoMetragemFio> tiposFioGravados = new ArrayList<ConsumoMetragemFio>();
		ConsumoFiosLinhas dadosConsumo = consumoFiosLinhasRepository.findConsumoById(idConsumo);

		tiposFio = tiposPontoFioRepository.findByIdTipoPonto(idTipoPonto);
		tiposFioGravados = consumoMetragemFioRepository.findConsumoMetragemFioByIdReferencia(idConsumo);

		if (tiposFioGravados.size() > 0) {
			for (ConsumoMetragemFio dadosFioGrav : tiposFioGravados) {
				
				metragemTotal = (dadosConsumo.comprimentoCostura * dadosFioGrav.metragemCosturaCm) / 100;

				metragemUm = metragemTotal / dadosFioGrav.pacote;

				dadosFioGrav.metragemTotal = metragemTotal;
				dadosFioGrav.metragemUm = metragemUm;

				consumoMetragemFioRepository.saveAndFlush(dadosFioGrav);
			}

		} else {
			for (TipoPontoFio dados : tiposFio) {
				dadosFio = tiposFioRepository.findByIdTipo(dados.tipoFio1);

				metragemTotal = (dadosConsumo.comprimentoCostura * dados.consumoFio) / 100;

				metragemUm = metragemTotal / dadosFio.centimetroCone;

				consumoMetragem = new ConsumoMetragemFio(dados.sequencia, referencia, idConsumo, idTipoPonto,
						dadosFio.centimetroCone, dados.consumoFio, metragemTotal, metragemUm, dados.tipoFio1);

				consumoMetragemFioRepository.saveAndFlush(consumoMetragem);
			}
		}
	}

	public void atualizarPacote(int pacoteAtualizado, String idConsumo, int tipoFioAtualizado) {
		ConsumoMetragemFio consumoMetragem = consumoMetragemFioRepository.findConsumoMetragemFioById(idConsumo);

		consumoMetragem.pacote = pacoteAtualizado;
		consumoMetragem.idTipoFio = tipoFioAtualizado;

		consumoMetragemFioRepository.save(consumoMetragem);
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

	public List<ConteudoChaveNumerica> makeListOptionTiposFio(int idTipoPonto, int sequencia) {

		List<ConteudoChaveNumerica> listTiposFio = new ArrayList<ConteudoChaveNumerica>();

		if ((idTipoPonto < 0) || (sequencia < 0)) return listTiposFio;

		ConteudoChaveNumerica objectTipoFio1 = null;
		ConteudoChaveNumerica objectTipoFio2 = null;
		ConteudoChaveNumerica objectTipoFio3 = null;

		String id = idTipoPonto + "-" + sequencia;

		TipoPontoFio dadosPonto = tiposPontoFioRepository.findByIdTipoPontoFio(id);

		if (dadosPonto == null) return listTiposFio;

		TiposFio dadosFio = null;

		if (dadosPonto.tipoFio1 > 0) {
			
			dadosFio = tiposFioRepository.findByIdTipo(dadosPonto.tipoFio1);
			
			objectTipoFio1 = new ConteudoChaveNumerica(dadosPonto.tipoFio1, Integer.toString(dadosPonto.tipoFio1) + " - " + dadosFio.descricao);
			listTiposFio.add(objectTipoFio1);
		}

		if (dadosPonto.tipoFio2 > 0) {

			dadosFio = tiposFioRepository.findByIdTipo(dadosPonto.tipoFio2);

			objectTipoFio2 = new ConteudoChaveNumerica(dadosPonto.tipoFio2, Integer.toString(dadosPonto.tipoFio2) + " - " + dadosFio.descricao);
			listTiposFio.add(objectTipoFio2);
		}

		if (dadosPonto.tipoFio3 > 0) {

			dadosFio = tiposFioRepository.findByIdTipo(dadosPonto.tipoFio2);

			objectTipoFio3 = new ConteudoChaveNumerica(dadosPonto.tipoFio3, Integer.toString(dadosPonto.tipoFio3) + " - " + dadosFio.descricao);
			listTiposFio.add(objectTipoFio3);
		}

		return listTiposFio;
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
