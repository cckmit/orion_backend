package br.com.live.producao.body;

import java.util.List;

import br.com.live.producao.entity.PreRequisicaoAlmoxarifado;
import br.com.live.producao.entity.PreRequisicaoAlmoxarifadoItem;
import br.com.live.producao.model.ConsultaPreRequisicaoAlmoxItem;
import br.com.live.producao.model.RequisicaoAlmoxarifado;

public class BodySuprimento {
	public PreRequisicaoAlmoxarifado preRequisicaoAlmoxarifado;
	public PreRequisicaoAlmoxarifadoItem preRequisicaoAlmoxarifadoItem;
	public List<ConsultaPreRequisicaoAlmoxItem> preRequisicaoAlmoxarifadoItens;
	public RequisicaoAlmoxarifado requisicaoAlmoxarifado; 
}