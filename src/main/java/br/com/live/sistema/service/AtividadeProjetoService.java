package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyAtividadeProjeto;
import br.com.live.sistema.entity.*;
import br.com.live.sistema.repository.*;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AtividadeProjetoService {

    AtividadeProjetoRepository atividadeProjetoRepository;
    TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository;
    TarefaAtividadeProjetoRepository tarefaAtividadeProjetoRepository;
    RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository;
    RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository;
    ProjetoService projetoService;

    public AtividadeProjetoService(AtividadeProjetoRepository atividadeProjetoRepository, TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository, TarefaAtividadeProjetoRepository tarefaAtividadeProjetoRepository, RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository, RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository, ProjetoService projetoService) {
        this.atividadeProjetoRepository = atividadeProjetoRepository;
        this.tarefaTipoAtividadeProjetoRepository = tarefaTipoAtividadeProjetoRepository;
        this.tarefaAtividadeProjetoRepository = tarefaAtividadeProjetoRepository;
        this.registroAtividadeProjetoRepository = registroAtividadeProjetoRepository;
        this.registroTarefaAtividadeProjetoRepository = registroTarefaAtividadeProjetoRepository;
        this.projetoService = projetoService;
    }

    public List<BodyAtividadeProjeto> deleteByIdAtividadeProjeto(Long id, Long idProjeto){

        atividadeProjetoRepository.deleteById(id);
        projetoService.gerarRegistroAtividadeProjeto(idProjeto, id);
        projetoService.atualizarStatusProjeto(idProjeto);

        return findAll(idProjeto);
    }

    public List<BodyAtividadeProjeto> findAll(Long idProjeto){

        List<AtividadeProjetoEntity> atividadeProjetoEntityList;

        if (idProjeto == 0) {
            atividadeProjetoEntityList = atividadeProjetoRepository.findAll();
        } else {
            atividadeProjetoEntityList = atividadeProjetoRepository.findAllByIdProjeto(idProjeto);
        }

        List<BodyAtividadeProjeto> atividadeProjetoBodyList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (AtividadeProjetoEntity atividadeProjetoEntity : atividadeProjetoEntityList){

            BodyAtividadeProjeto atividadeProjeto = new BodyAtividadeProjeto();
            atividadeProjeto.id = atividadeProjetoEntity.getId();
            atividadeProjeto.descricao = atividadeProjetoEntity.getDescricao();
            atividadeProjeto.idProjeto = atividadeProjetoEntity.getIdProjeto();
            atividadeProjeto.idFase = atividadeProjetoEntity.getIdFase();
            atividadeProjeto.tempoPrevisto = tarefaAtividadeProjetoRepository.calcularTempoPrevistoTarefaAtividade(idProjeto, atividadeProjetoEntity.getId());
            atividadeProjeto.marco = atividadeProjetoEntity.getMarco();

            if (atividadeProjetoEntity.getIdTipoAtividade() != null && atividadeProjetoEntity.getIdTipoAtividade() != 0) atividadeProjeto.idTipoAtividade = atividadeProjetoEntity.getIdTipoAtividade();
            if (atividadeProjetoEntity.getDataPrevInicio() != null) atividadeProjeto.dataPrevInicio = dateFormat.format(atividadeProjetoEntity.getDataPrevInicio());
            if (atividadeProjetoEntity.getDataPrevFim() != null) atividadeProjeto.dataPrevFim = dateFormat.format(atividadeProjetoEntity.getDataPrevFim());
            if (atividadeProjetoEntity.getIdResponsavel() != null) atividadeProjeto.idResponsavel = atividadeProjetoEntity.getIdResponsavel();

            atividadeProjetoBodyList.add(atividadeProjeto);
        }
        return atividadeProjetoBodyList;
    }

    public List<BodyAtividadeProjeto> saveAtividadeProjeto(BodyAtividadeProjeto atividadeProjeto){

        saveAtividade(atividadeProjeto);
        gerarTarefaAtividadeProjeto(atividadeProjeto.id);
        projetoService.gerarRegistroAtividadeProjeto(atividadeProjeto.idProjeto, atividadeProjeto.id);
        projetoService.atualizarStatusProjeto(atividadeProjeto.idProjeto);

        return findAll(atividadeProjeto.idProjeto);
    }

    @Transactional
    public void saveAtividade(BodyAtividadeProjeto atividadeProjeto){

        AtividadeProjetoEntity atividadeProjetoEntity = new AtividadeProjetoEntity();

        if (atividadeProjeto.id == 0) {
            atividadeProjeto.id = atividadeProjetoRepository.findNextId();
        } else{
            Optional<AtividadeProjetoEntity> atividadeProjetoEntityOptional = atividadeProjetoRepository.findById(atividadeProjeto.id);
            if (atividadeProjetoEntityOptional.isPresent()) atividadeProjetoEntity = atividadeProjetoEntityOptional.get();
        }

        atividadeProjetoEntity.setId(atividadeProjeto.id);
        atividadeProjetoEntity.setDescricao(atividadeProjeto.descricao);
        atividadeProjetoEntity.setIdProjeto(atividadeProjeto.idProjeto);
        atividadeProjetoEntity.setIdFase(atividadeProjeto.idFase);
        atividadeProjetoEntity.setTempoPrevisto(atividadeProjeto.tempoPrevisto);
        atividadeProjetoEntity.setMarco(atividadeProjeto.marco);

        if (atividadeProjeto.idResponsavel != 0) atividadeProjetoEntity.setIdResponsavel(atividadeProjeto.idResponsavel);
        if (atividadeProjeto.dataPrevInicio != null) atividadeProjetoEntity.setDataPrevInicio(FormataData.parseStringToDate(atividadeProjeto.dataPrevInicio));
        if (atividadeProjeto.dataPrevFim != null) atividadeProjetoEntity.setDataPrevFim(FormataData.parseStringToDate(atividadeProjeto.dataPrevFim));
        if (atividadeProjeto.idTipoAtividade != 0) atividadeProjetoEntity.setIdTipoAtividade(atividadeProjeto.idTipoAtividade);

        atividadeProjetoRepository.save(atividadeProjetoEntity);
    }

    @Transactional
    public void gerarTarefaAtividadeProjeto(long idAtividade){

        Optional<AtividadeProjetoEntity> atividadeProjetoEntityOptional = atividadeProjetoRepository.findById(idAtividade);

        if (atividadeProjetoEntityOptional.isPresent()) {

            AtividadeProjetoEntity atividadeProjetoEntity = atividadeProjetoEntityOptional.get();

            long idProjeto = atividadeProjetoEntity.getIdProjeto();

            List<TarefaAtividadeProjetoEntity> tarefaAtividadeProjetoEntityList = tarefaAtividadeProjetoRepository.findAllByAtividade(idProjeto, idAtividade);

            if (atividadeProjetoEntity.getIdTipoAtividade() != null && tarefaAtividadeProjetoEntityList.isEmpty()) {

                List<TarefaTipoAtividadeProjetoEntity> tarefaTipoAtividadeProjetoEntityList = tarefaTipoAtividadeProjetoRepository.findByTipoAtividadeId(atividadeProjetoEntity.getIdTipoAtividade());

                for (TarefaTipoAtividadeProjetoEntity tarefaTipoAtividadeProjetoEntity : tarefaTipoAtividadeProjetoEntityList){

                    TarefaAtividadeProjetoEntity tarefaAtividadeProjeto = new TarefaAtividadeProjetoEntity();
                    tarefaAtividadeProjeto.setId(tarefaAtividadeProjetoRepository.findNextId());
                    tarefaAtividadeProjeto.setIdProjeto(idProjeto);
                    tarefaAtividadeProjeto.setIdAtividade(idAtividade);
                    tarefaAtividadeProjeto.setDescricao(tarefaTipoAtividadeProjetoEntity.getDescricao());
                    tarefaAtividadeProjeto.setTempoPrevisto(tarefaTipoAtividadeProjetoEntity.getTempoEstimado());
                    tarefaAtividadeProjetoRepository.save(tarefaAtividadeProjeto);
                }
            }
        }
    }
}
