package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyTarefaTipoAtividadeProjeto;
import br.com.live.sistema.entity.TarefaTipoAtividadeProjetoEntity;
import br.com.live.sistema.repository.TarefaTipoAtividadeProjetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TarefaTipoAtividadeProjetoService {

    TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository;

    public TarefaTipoAtividadeProjetoService(TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository) {
        this.tarefaTipoAtividadeProjetoRepository = tarefaTipoAtividadeProjetoRepository;
    }

    public List<TarefaTipoAtividadeProjetoEntity> saveTarefaTipoAtividadeProjeto(TarefaTipoAtividadeProjetoEntity tarefaTipoAtividadeProjeto){

        if (tarefaTipoAtividadeProjeto.getId() == 0) {
            tarefaTipoAtividadeProjeto.setId(tarefaTipoAtividadeProjetoRepository.findNextId());
            tarefaTipoAtividadeProjeto.setOrdenacao(tarefaTipoAtividadeProjetoRepository.findNextOrdem(tarefaTipoAtividadeProjeto.getIdTipoAtividade()));
        }

        tarefaTipoAtividadeProjetoRepository.save(tarefaTipoAtividadeProjeto);

        Long idTipoAtividade = tarefaTipoAtividadeProjeto.getIdTipoAtividade();

        return tarefaTipoAtividadeProjetoRepository.findByTipoAtividadeId(idTipoAtividade);
    }

    public List<TarefaTipoAtividadeProjetoEntity> saveOrdenacaoTarefaTipoAtividadeProjeto(BodyTarefaTipoAtividadeProjeto tarefaTipoAtividadeProjetoBody){

        Long idTipoAtividade = tarefaTipoAtividadeProjetoBody.idTipoAtividade;

        int ordenacao = 0;

        for (TarefaTipoAtividadeProjetoEntity tarefaTipoAtividadeProjeto : tarefaTipoAtividadeProjetoBody.listTarefaTipoAtividadeProjeto){
            ordenacao++;
            tarefaTipoAtividadeProjeto.setOrdenacao(ordenacao);
            tarefaTipoAtividadeProjetoRepository.save(tarefaTipoAtividadeProjeto);
        }
        return tarefaTipoAtividadeProjetoRepository.findByTipoAtividadeId(idTipoAtividade);
    }
}
