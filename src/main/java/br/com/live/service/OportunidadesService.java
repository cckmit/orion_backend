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
	
	public void saveOportunidade(String id, int tipo, String dataCadastro, int prioridade, String descricao, String objetivo, String contextualizacao,
    		String descricaoProblema, String perguntasEmAberto, String riscos) {

		GestaoAtivosOportunidade oportunidade = null;

		oportunidade = gestaoAtivosOportunidadeRepository.findByIdOp(id);

		if (oportunidade == null){

			int idInt = Integer.parseInt(id);
			int nextId = gestaoAtivosCustom.findNextIdByTipo(idInt, tipo);

			oportunidade = new GestaoAtivosOportunidade(tipo + "-" + idInt + "-" + nextId, tipo, idInt, nextId, FormataData.parseStringToDate(dataCadastro), prioridade, descricao, objetivo, contextualizacao, descricaoProblema, perguntasEmAberto, riscos);

			gestaoAtivosOportunidadeRepository.save(oportunidade);

		} else {
			oportunidade.dataCadastro = FormataData.parseStringToDate(dataCadastro);
			oportunidade.prioridade = prioridade;
			oportunidade.descricao = descricao;
			oportunidade.objetivo = objetivo;
			oportunidade.contextualizacao = contextualizacao;
			oportunidade.descricaoProblema = descricaoProblema;
			oportunidade.perguntasEmAberto = perguntasEmAberto;
			oportunidade.riscos = riscos;
		}
    }
}
