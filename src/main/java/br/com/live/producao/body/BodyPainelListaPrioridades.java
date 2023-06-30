package br.com.live.producao.body;

import java.util.List;

import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

public class BodyPainelListaPrioridades {
    public String dtEmbarqueInicio;
    public String dtEmbarqueFim;
    public int periodoProdInicio;
    public int periodoProdFim;
    public List<ConteudoChaveNumerica> listDepositosPedidos;
    public List<ConteudoChaveNumerica> listDepositosEstoques;
    public List<ConteudoChaveNumerica> listColecoes;
    public List<ConteudoChaveNumerica> listLinhasProdutos;
    public List<ConteudoChaveAlfaNum> listReferencias;
    public List<ConteudoChaveAlfaNum> listTamanhos;
    public List<ConteudoChaveAlfaNum> listCores;
    public List<ConteudoChaveNumerica> listArtigos;
    public List<ConteudoChaveNumerica> listSegmentos;
    public List<ConteudoChaveNumerica> listFamilias;
    public List<ConteudoChaveNumerica> listFaccoes;
    public List<ConteudoChaveAlfaNum> listNaturezas;
    public List<ConteudoChaveNumerica> listSituacoesPedidos;
    public List<ConteudoChaveNumerica> listPedidosOrdens;
    public List<ConteudoChaveNumerica> listPedidosComerciais;
    public List<ConteudoChaveNumerica> listNumerosInterno;
    public boolean produtosComSobra;
    public boolean produtoscomFalta;
    public boolean produtosFaltaSemProducao;	
}