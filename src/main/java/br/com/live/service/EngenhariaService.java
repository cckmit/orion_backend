package br.com.live.service;

import org.springframework.stereotype.Service;
import br.com.live.custom.EngenhariaCustom;
import br.com.live.entity.MarcasFio;
import br.com.live.entity.TipoPonto;
import br.com.live.entity.TipoPontoFio;
import br.com.live.entity.TiposFio;
import br.com.live.repository.MarcasFioRepository;
import br.com.live.repository.TiposFioRepository;
import br.com.live.repository.TiposPontoFioRepository;
import br.com.live.repository.TiposPontoRepository;

@Service
public class EngenhariaService {
	
	private final MarcasFioRepository marcasFioRepository;
	private final TiposFioRepository tiposFioRepository;
	private final EngenhariaCustom engenhariaCustom;
	private final TiposPontoFioRepository tiposPontoFioRepository;
	private final TiposPontoRepository tiposPontoRepository;
	
	public EngenhariaService(MarcasFioRepository marcasFioRepository, TiposFioRepository tiposFioRepository, EngenhariaCustom engenhariaCustom, TiposPontoFioRepository tiposPontoFioRepository, TiposPontoRepository tiposPontoRepository) {
		this.marcasFioRepository = marcasFioRepository;
		this.tiposFioRepository = tiposFioRepository;
		this.engenhariaCustom = engenhariaCustom;
		this.tiposPontoFioRepository = tiposPontoFioRepository;
		this.tiposPontoRepository = tiposPontoRepository;
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
	
	public TiposFio saveTipos(int id, String descricao, int titulo, int centimetrosCone) {
		 
		TiposFio tipos = null;
		
		if (id == 0) {
			id = engenhariaCustom.findNewIdTipos();
			tipos = new TiposFio(id, descricao, titulo, centimetrosCone);
		} else {
			tipos = tiposFioRepository.findByIdTipo(id);
			tipos.descricao = descricao;
			tipos.titulo = titulo;
			tipos.centimetroCone = centimetrosCone;
		}
		tiposFioRepository.save(tipos);
		
		return tiposFioRepository.findByIdTipo(id);
	}

	public TipoPonto saveTiposPonto(int id, String descricao, String maquina) {
		 
		TipoPonto tipos = null;
		String[] codigoSeparado = maquina.split("[.]");
		
		if (id == 0) {
			id = engenhariaCustom.findIdNewTipoPonto();
			tipos = new TipoPonto(id, descricao, codigoSeparado[0], codigoSeparado[1]);
		} else {
			tipos = tiposPontoRepository.findByIdTipoPonto(id);
			tipos.descricao = descricao;
			tipos.grupoMaquina = codigoSeparado[0];
			tipos.subGrupoMaquina = codigoSeparado[1];
		}
		tiposPontoRepository.save(tipos);
		
		return tiposPontoRepository.findByIdTipoPonto(id);
	}

	public TipoPontoFio saveTiposPontoFio(int idTipoPonto, String idRegistro,int tipoFio, float consumoFio) {
		TipoPontoFio dadosTipoPontoFio = null;
	
		dadosTipoPontoFio = tiposPontoFioRepository.findByIdTipoPontoFio(idRegistro);
		
		if (dadosTipoPontoFio == null) {

			int sequencia = engenhariaCustom.findIdNewSequenciaPontoFio(idTipoPonto);

			dadosTipoPontoFio = new TipoPontoFio(sequencia, idTipoPonto, tipoFio, consumoFio);
		} else {
			dadosTipoPontoFio.consumoFio = consumoFio;
		}

		tiposPontoFioRepository.save(dadosTipoPontoFio);

		return dadosTipoPontoFio;
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
}
