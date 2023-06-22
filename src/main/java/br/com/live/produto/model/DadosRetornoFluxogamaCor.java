package br.com.live.produto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosRetornoFluxogamaCor {

    @JsonProperty("cor.ds")
    private String corDescricao;
    @JsonProperty("cor.ws_id")
    private String corId;

    public String getCorDescricao() {
        return corDescricao;
    }

    public void setCorDescricao(String corDescricao) {
        this.corDescricao = corDescricao;
    }

    public String getCorId() {
        return corId;
    }

    public void setCorId(String corId) {
        this.corId = corId;
    }

    public DadosRetornoFluxogamaCor(){}

    public DadosRetornoFluxogamaCor(String corDescricao, String corId) {
        this.corDescricao = corDescricao;
        this.corId = corId;
    }
}
