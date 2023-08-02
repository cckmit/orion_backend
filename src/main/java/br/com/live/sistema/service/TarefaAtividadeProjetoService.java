package br.com.live.sistema.service;

import br.com.live.sistema.entity.AtividadeProjetoEntity;
import br.com.live.sistema.entity.TarefaAtividadeProjetoEntity;
import br.com.live.sistema.repository.AtividadeProjetoRepository;
import br.com.live.sistema.repository.TarefaAtividadeProjetoRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaAtividadeProjetoService {

    TarefaAtividadeProjetoRepository tarefaAtividadeProjetoRepository;
    AtividadeProjetoRepository atividadeProjetoRepository;
    ProjetoService projetoService;

    public TarefaAtividadeProjetoService(TarefaAtividadeProjetoRepository tarefaAtividadeProjetoRepository, AtividadeProjetoRepository atividadeProjetoRepository, ProjetoService projetoService) {
        this.tarefaAtividadeProjetoRepository = tarefaAtividadeProjetoRepository;
        this.atividadeProjetoRepository = atividadeProjetoRepository;
        this.projetoService = projetoService;
    }

    public List<TarefaAtividadeProjetoEntity> saveTarefaAtividadeProjeto(TarefaAtividadeProjetoEntity tarefaAtividadeProjeto) throws ParseException {
        saveTarefaAtividade(tarefaAtividadeProjeto);
        atualizarDadosAtividade(tarefaAtividadeProjeto.idProjeto, tarefaAtividadeProjeto.idAtividade);
        projetoService.gerarRegistroAtividadeProjeto(tarefaAtividadeProjeto.idProjeto, tarefaAtividadeProjeto.idAtividade);
        projetoService.atualizarStatusProjeto(tarefaAtividadeProjeto.idProjeto);
        return findAll(tarefaAtividadeProjeto.idProjeto);
    }

    public List<TarefaAtividadeProjetoEntity> deleteByIdTarefaAtividadeProjeto(long id, long idProjeto, long idAtividade){
        deleteById(id);
        atualizarDadosAtividade(idProjeto, idAtividade);
        projetoService.gerarRegistroAtividadeProjeto(idProjeto, idAtividade);
        projetoService.atualizarStatusProjeto(idProjeto);
        return findAll(idProjeto);
    }

    @Transactional
    public void deleteById(long id) {
        tarefaAtividadeProjetoRepository.deleteById(id);
    }

    @Transactional
    public void saveTarefaAtividade(TarefaAtividadeProjetoEntity tarefaAtividadeProjeto) throws ParseException {

        TarefaAtividadeProjetoEntity tarefaAtividadeProjetoEntity = new TarefaAtividadeProjetoEntity();

        if (tarefaAtividadeProjeto.id == 0){
            tarefaAtividadeProjeto.id = tarefaAtividadeProjetoRepository.findNextId();
        } else {
            Optional<TarefaAtividadeProjetoEntity> tarefaAtividadeProjetoEntityOptional = tarefaAtividadeProjetoRepository.findById(tarefaAtividadeProjeto.id);
            if (tarefaAtividadeProjetoEntityOptional.isPresent()) tarefaAtividadeProjetoEntity = tarefaAtividadeProjetoEntityOptional.get();
        }

        tarefaAtividadeProjetoEntity.setId(tarefaAtividadeProjeto.id);
        tarefaAtividadeProjetoEntity.setIdProjeto(tarefaAtividadeProjeto.idProjeto);
        tarefaAtividadeProjetoEntity.setIdAtividade(tarefaAtividadeProjeto.idAtividade);
        tarefaAtividadeProjetoEntity.setDescricao(tarefaAtividadeProjeto.descricao);
        tarefaAtividadeProjetoEntity.setTempoPrevisto(tarefaAtividadeProjeto.tempoPrevisto);

        tarefaAtividadeProjetoRepository.save(tarefaAtividadeProjetoEntity);
    }

    @Transactional
    public void atualizarDadosAtividade(long idProjeto, long idAtividade){

        List<TarefaAtividadeProjetoEntity> tarefaAtividadeProjetoList = tarefaAtividadeProjetoRepository.findAllByAtividade(idProjeto, idAtividade);

        Optional<AtividadeProjetoEntity> atividadeProjeto = atividadeProjetoRepository.findById(idAtividade);
        AtividadeProjetoEntity atividadeProjetoEntity = atividadeProjeto.get();

        atividadeProjetoEntity.setTempoPrevisto(0);

        if (!tarefaAtividadeProjetoList.isEmpty()){
            double tempoPrevisto = tarefaAtividadeProjetoRepository.calcularTempoPrevistoTarefaAtividade(idProjeto, idAtividade);
            atividadeProjetoEntity.setTempoPrevisto(tempoPrevisto);
        }

        atividadeProjetoRepository.save(atividadeProjetoEntity);
    }

    public List<TarefaAtividadeProjetoEntity> findAll(Long idProjeto){

        List<TarefaAtividadeProjetoEntity> tarefaAtividadeProjetoEntityList = new ArrayList<>();

        if (idProjeto == 0){
            tarefaAtividadeProjetoEntityList = tarefaAtividadeProjetoRepository.findAll();
        } else {
            tarefaAtividadeProjetoEntityList = tarefaAtividadeProjetoRepository.findAllByIdProjeto(idProjeto);
        }
        return tarefaAtividadeProjetoEntityList;
    }
}
