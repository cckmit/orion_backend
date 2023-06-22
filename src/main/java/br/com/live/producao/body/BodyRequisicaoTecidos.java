package br.com.live.producao.body;

import java.util.List;

import br.com.live.producao.entity.RequisicaoTecidos;
import br.com.live.producao.entity.RequisicaoTecidosItem;
import br.com.live.producao.model.ConsultaRequisicaoTecidosItem;

public class BodyRequisicaoTecidos {

	public long idRequisicao;
	public long idRequisicaoItem;
	public RequisicaoTecidos requisicaoTecidos;
	public RequisicaoTecidosItem requisicaoTecidosItem; 
	public List<ConsultaRequisicaoTecidosItem> requisicaoTecidosItens;
	public boolean consideraConfirmados;
	
}
