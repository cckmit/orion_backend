package br.com.live.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetornoAPIModelos {
	public int proximaPagina;
	public List<DadosRetornoFluxogama> dados;
}
