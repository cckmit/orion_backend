package br.com.live.body;

import java.util.List;

import br.com.live.entity.RequisicaoTecidos;
import br.com.live.entity.RequisicaoTecidosItem;
import br.com.live.model.ConsultaRequisicaoTecidosItem;

public class BodyRequisicaoTecidos {

	public long idRequisicao;
	public long idRequisicaoItem;
	public RequisicaoTecidos requisicaoTecidos;
	public RequisicaoTecidosItem requisicaoTecidosItem; 
	public List<ConsultaRequisicaoTecidosItem> requisicaoTecidosItens;
	
}
