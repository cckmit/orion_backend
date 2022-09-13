package br.com.live.body;

import br.com.live.util.ConteudoChaveNumerica;

import java.util.List;

public class BodyConfeccao {
    public long id;
    public String descricao;
    public List<ConteudoChaveNumerica> restricoes;
    public List<ConteudoChaveNumerica> rolos;
    public List<ConteudoChaveNumerica> ordens;
}
