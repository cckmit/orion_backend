package br.com.live.bo;

import org.springframework.beans.BeanUtils;

import br.com.live.entity.PlanoMestre;
import br.com.live.entity.PlanoMestreParamProgItem;
import br.com.live.entity.PlanoMestreParametros;
import br.com.live.entity.ProdutoPlanoMestre;
import br.com.live.entity.ProdutoPlanoMestrePorCor;

public class CopiarPlanoMestre {

	public static PlanoMestre getCopiaPlanoMestre(PlanoMestre origem) {				
		PlanoMestre copia = new PlanoMestre();						
		BeanUtils.copyProperties(origem, copia, "id", "data", "situacao");		
		copia.descricao += " (CÓPIA DO PLANO " + origem.id + ")";			
		return copia;
	}
	
	public static PlanoMestreParametros getCopiaPlanoMestreParametros(PlanoMestreParametros origem, long idPlanoMestreNew) {
		PlanoMestreParametros copia = new PlanoMestreParametros(); 		
		BeanUtils.copyProperties(origem, copia, "idPlanoMestre");
		copia.idPlanoMestre = idPlanoMestreNew;		 		  
		return copia;
	}
	
	public static ProdutoPlanoMestre getCopiaProdutoPlanoMestre(ProdutoPlanoMestre origem, long idPlanoMestreNew) {
		ProdutoPlanoMestre copia = new ProdutoPlanoMestre();
		BeanUtils.copyProperties(origem, copia, "id", "idPlanoMestre");
		copia.idPlanoMestre = idPlanoMestreNew;
		return copia;
	}

	public static ProdutoPlanoMestrePorCor getCopiaProdutoPlanoMestrePorCor(ProdutoPlanoMestrePorCor origem, long idPlanoMestreNew) {
		ProdutoPlanoMestrePorCor copia = new ProdutoPlanoMestrePorCor();
		BeanUtils.copyProperties(origem, copia, "id", "idPlanoMestre");
		copia.idPlanoMestre = idPlanoMestreNew;
		return copia;
	}

	public static PlanoMestreParamProgItem getCopiaPlanoMestreParamProgItem(PlanoMestreParamProgItem origem, long idPlanoMestreNew, long idItemPlanoMestre) {
		PlanoMestreParamProgItem copia = new PlanoMestreParamProgItem();
		BeanUtils.copyProperties(origem, copia, "id", "idPlanoMestre");
		copia.idItemPlanoMestre = idItemPlanoMestre;
		copia.idPlanoMestre = idPlanoMestreNew;
		return copia;
	}
	
}
