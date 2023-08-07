package br.com.live.administrativo.body;

import java.util.List;

import br.com.live.administrativo.entity.ImportacaoParametroCustoEntity;
import br.com.live.administrativo.model.ConsultaLanctoContabeis;

public class BodyContabilidade {
	
	public List<ConsultaLanctoContabeis> tabImportarLanctoContab;
	public int id;
	public String usuario;
	public String datainsercao;
	public String criticas;
	public List<ImportacaoParametroCustoEntity> parametrosCusto;
	public int usuarioImp;

}
