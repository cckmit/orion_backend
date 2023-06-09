package br.com.live.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosRetornoFluxogama {

	@JsonProperty("uno.319.ws_id")
	private String uno319WsId;

	@JsonProperty("uno.107.ds")
	private String uno107Ds;

	@JsonProperty("uno.108.ds")
	private String uno108Ds;

	@JsonProperty("uno.107.ws_id")
	private String uno107WsId;

	@JsonProperty("modelo.ds_referencia")
	private String modeloDsReferencia;

	@JsonProperty("uno.102.ds")
	private String uno102Ds;

	@JsonProperty("uno.108.ws_id")
	private String uno108WsId;

	@JsonProperty("uno.314.ds")
	private String uno314Ds;

	@JsonProperty("uno.315.ds")
	private String uno315Ds;

	@JsonProperty("uno.315.ws_id")
	private String uno315WsId;

	@JsonProperty("uno.318.ds")
	private String uno318Ds;

	@JsonProperty("uno.102.ws_id")
	private String uno102WsId;

	@JsonProperty("uno.319.ds")
	private String uno319Ds;

	@JsonProperty("uno.314.ws_id")
	private String uno314WsId;

	@JsonProperty("uno.115")
	private String uno115;

	@JsonProperty("uno.98.ws_id")
	private String uno98WsId;

	@JsonProperty("uno.98.ds")
	private String uno98Ds;

	@JsonProperty("cor")
	public List<DadosRetornoFluxogamaCor> cores;

	@JsonProperty("tabela.48")
	public List<DadosRetornoFluxogamaCategoria> categorias;

	@JsonProperty("uno.143.ws_id")
	private String uno143;

	@JsonProperty("uno.317.ws_id")
	private String uno317;

	@JsonProperty("uno.99.ds")
	private String uno99;

	@JsonProperty("uno.101.ws_id")
	private String uno101;

	public String getUno319WsId() {
		return uno319WsId;
	}

	public void setUno319WsId(String uno319WsId) {
		this.uno319WsId = uno319WsId;
	}

	public String getUno107Ds() {
		return uno107Ds;
	}

	public void setUno107Ds(String uno107Ds) {
		this.uno107Ds = uno107Ds;
	}

	public String getUno108Ds() {
		return uno108Ds;
	}

	public void setUno108Ds(String uno108Ds) {
		this.uno108Ds = uno108Ds;
	}

	public String getUno107WsId() {
		return uno107WsId;
	}

	public void setUno107WsId(String uno107WsId) {
		this.uno107WsId = uno107WsId;
	}

	public String getModeloDsReferencia() {
		return modeloDsReferencia;
	}

	public void setModeloDsReferencia(String modeloDsReferencia) {
		this.modeloDsReferencia = modeloDsReferencia;
	}

	public String getUno102Ds() {
		return uno102Ds;
	}

	public void setUno102Ds(String uno102Ds) {
		this.uno102Ds = uno102Ds;
	}

	public String getUno108WsId() {
		return uno108WsId;
	}

	public void setUno108WsId(String uno108WsId) {
		this.uno108WsId = uno108WsId;
	}

	public String getUno314Ds() {
		return uno314Ds;
	}

	public void setUno314Ds(String uno314Ds) {
		this.uno314Ds = uno314Ds;
	}

	public String getUno315Ds() {
		return uno315Ds;
	}

	public void setUno315Ds(String uno315Ds) {
		this.uno315Ds = uno315Ds;
	}

	public String getUno315WsId() {
		return uno315WsId;
	}

	public void setUno315WsId(String uno315WsId) {
		this.uno315WsId = uno315WsId;
	}

	public String getUno318Ds() {
		return uno318Ds;
	}

	public void setUno318Ds(String uno318Ds) {
		this.uno318Ds = uno318Ds;
	}

	public String getUno102WsId() {
		return uno102WsId;
	}

	public void setUno102WsId(String uno102WsId) {
		this.uno102WsId = uno102WsId;
	}

	public String getUno319Ds() {
		return uno319Ds;
	}

	public void setUno319Ds(String uno319Ds) {
		this.uno319Ds = uno319Ds;
	}

	public String getUno314WsId() {
		return uno314WsId;
	}

	public void setUno314WsId(String uno314WsId) {
		this.uno314WsId = uno314WsId;
	}

	public String getUno115() {
		return uno115;
	}

	public void setUno115(String uno115) {
		this.uno115 = uno115;
	}

	public String getUno98WsId() {
		return uno98WsId;
	}

	public void setUno98WsId(String uno98WsId) {
		this.uno98WsId = uno98WsId;
	}

	public String getUno98Ds() {
		return uno98Ds;
	}

	public void setUno98Ds(String uno98Ds) {
		this.uno98Ds = uno98Ds;
	}

	public List<DadosRetornoFluxogamaCor> getCores() {
		return cores;
	}

	public void setCores(List<DadosRetornoFluxogamaCor> cores) {
		this.cores = cores;
	}

	public List<DadosRetornoFluxogamaCategoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<DadosRetornoFluxogamaCategoria> categorias) {
		this.categorias = categorias;
	}

	public String getUno143() {
		return uno143;
	}

	public void setUno143(String uno143) {
		this.uno143 = uno143;
	}

	public String getUno317() {
		return uno317;
	}

	public void setUno317(String uno317) {
		this.uno317 = uno317;
	}

	public String getUno99() {
		return uno99;
	}

	public void setUno99(String uno99) {
		this.uno99 = uno99;
	}

	public String getUno101() {
		return uno101;
	}

	public void setUno101(String uno101) {
		this.uno101 = uno101;
	}

	public DadosRetornoFluxogama() {

	}

	public DadosRetornoFluxogama(String uno319WsId, String uno107Ds, String uno108Ds, String uno107WsId, String modeloDsReferencia, String uno102Ds,
								 String uno108WsId, String uno314Ds, String uno315Ds, String uno315WsId, String uno318Ds, String uno102WsId,
								 String uno319Ds, String uno314WsId, String uno115, String uno98WsId, String uno98Ds,
								 List<DadosRetornoFluxogamaCor> cores, List<DadosRetornoFluxogamaCategoria> categorias,String uno143, String uno317, String uno99, String uno101) {
		this.uno319WsId = uno319WsId;
		this.uno107Ds = uno107Ds;
		this.uno108Ds = uno108Ds;
		this.uno107WsId = uno107WsId;
		this.modeloDsReferencia = modeloDsReferencia;
		this.uno102Ds = uno102Ds;
		this.uno108WsId = uno108WsId;
		this.uno314Ds = uno314Ds;
		this.uno315Ds = uno315Ds;
		this.uno315WsId = uno315WsId;
		this.uno318Ds = uno318Ds;
		this.uno102WsId = uno102WsId;
		this.uno319Ds = uno319Ds;
		this.uno314WsId = uno314WsId;
		this.uno115 = uno115;
		this.uno98WsId = uno98WsId;
		this.uno98Ds = uno98Ds;
		this.cores = cores;
		this.categorias = categorias;
		this.uno143 = uno143;
		this.uno317 = uno317;
		this.uno99 = uno99;
		this.uno101 = uno101;
	}
}
