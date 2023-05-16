package br.com.live.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.live.custom.GestaoAtivosCustom;
import br.com.live.entity.GestaoAtivosOportunidade;
import br.com.live.model.ConsultaGestaoAtivos;
import br.com.live.repository.GestaoAtivosOportunidadeRepository;
import br.com.live.util.FormataData;

@Service
@Transactional
public class OportunidadesService {
	
	private final GestaoAtivosCustom gestaoAtivosCustom;
	private final GestaoAtivosOportunidadeRepository gestaoAtivosOportunidadeRepository;
	
	public OportunidadesService(GestaoAtivosCustom gestaoAtivosCustom, GestaoAtivosOportunidadeRepository gestaoAtivosOportunidadeRepository) {
		this.gestaoAtivosCustom = gestaoAtivosCustom;
		this.gestaoAtivosOportunidadeRepository = gestaoAtivosOportunidadeRepository;
	}
	
	public List<ConsultaGestaoAtivos> findAllOportunidades(){
    	return gestaoAtivosCustom.findAllOportunidades();
    }
	
	public String findColunaConsulta(String tipo, String idOp){
    	return gestaoAtivosCustom.findColunaConsulta(tipo, idOp);
    }
	
	public void saveOportunidade(int id, int tipo, int idAtivo, String dataCadastro, int prioridade, String descricao, String objetivo, String contextualizacao,
    		String descricaoProblema, String perguntasEmAberto, String riscos) {

    	GestaoAtivosOportunidade oportunidade = null;
    	int nextId = gestaoAtivosCustom.findNextIdByTipo(id, tipo);
  
        oportunidade = new GestaoAtivosOportunidade(tipo + "-" + id + "-" + nextId, tipo, id, nextId, FormataData.parseStringToDate(dataCadastro), prioridade, descricao, objetivo,
        		contextualizacao, descricaoProblema, perguntasEmAberto, riscos);
         
        gestaoAtivosOportunidadeRepository.save(oportunidade);
    }
}
