package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyAtividadeProjeto;
import br.com.live.sistema.entity.AtividadeProjetoEntity;
import br.com.live.sistema.entity.RegistroAtividadeProjetoEntity;
import br.com.live.sistema.entity.RegistroTarefaAtividadeProjetoEntity;
import br.com.live.sistema.entity.TarefaTipoAtividadeProjetoEntity;
import br.com.live.sistema.repository.AtividadeProjetoRepository;
import br.com.live.sistema.repository.RegistroAtividadeProjetoRepository;
import br.com.live.sistema.repository.RegistroTarefaAtividadeProjetoRepository;
import br.com.live.sistema.repository.TarefaTipoAtividadeProjetoRepository;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AtividadeProjetoService {

    AtividadeProjetoRepository atividadeProjetoRepository;
    TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository;
    RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository;
    RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository;

    public AtividadeProjetoService(AtividadeProjetoRepository atividadeProjetoRepository, TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository, RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository, RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository) {
        this.atividadeProjetoRepository = atividadeProjetoRepository;
        this.tarefaTipoAtividadeProjetoRepository = tarefaTipoAtividadeProjetoRepository;
        this.registroAtividadeProjetoRepository = registroAtividadeProjetoRepository;
        this.registroTarefaAtividadeProjetoRepository = registroTarefaAtividadeProjetoRepository;
    }

    public List<BodyAtividadeProjeto> deleteByIdAtividadeProjeto(Long id, Long idProjeto){

        atividadeProjetoRepository.deleteById(id);
        removerRegistrosAtividadesProjeto(idProjeto);
        gerarRegistrosAtividadesProjeto(idProjeto);

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
            atividadeProjeto.idResponsavel = atividadeProjetoEntity.getIdResponsavel();
            atividadeProjeto.tempoPrevisto = atividadeProjetoEntity.getTempoPrevisto();

            if (atividadeProjetoEntity.getIdTipoAtividade() != null && atividadeProjetoEntity.getIdTipoAtividade() != 0) atividadeProjeto.idTipoAtividade = atividadeProjetoEntity.getIdTipoAtividade();
            if (atividadeProjetoEntity.getDataPrevInicio() != null) atividadeProjeto.dataPrevInicio = dateFormat.format(atividadeProjetoEntity.getDataPrevInicio());
            if (atividadeProjetoEntity.getDataPrevFim() != null) atividadeProjeto.dataPrevFim = dateFormat.format(atividadeProjetoEntity.getDataPrevFim());

            atividadeProjetoBodyList.add(atividadeProjeto);
        }
        return atividadeProjetoBodyList;
    }

    public List<BodyAtividadeProjeto> saveAtividadeProjeto(BodyAtividadeProjeto atividadeProjeto){

        saveAtividade(atividadeProjeto);
        removerRegistrosAtividadesProjeto(atividadeProjeto.idProjeto);
        gerarRegistrosAtividadesProjeto(atividadeProjeto.idProjeto);

        return findAll(atividadeProjeto.idProjeto);
    }

    @Transactional
    public void saveAtividade(BodyAtividadeProjeto atividadeProjeto){

        if (atividadeProjeto.id == 0) atividadeProjeto.id = atividadeProjetoRepository.findNextId();

        AtividadeProjetoEntity atividadeProjetoEntity = new AtividadeProjetoEntity();
        atividadeProjetoEntity.setId(atividadeProjeto.id);
        atividadeProjetoEntity.setDescricao(atividadeProjeto.descricao);
        atividadeProjetoEntity.setIdProjeto(atividadeProjeto.idProjeto);
        atividadeProjetoEntity.setIdFase(atividadeProjeto.idFase);
        atividadeProjetoEntity.setIdResponsavel(atividadeProjeto.idResponsavel);
        atividadeProjetoEntity.setTempoPrevisto(atividadeProjeto.tempoPrevisto);

        if (atividadeProjeto.idTipoAtividade != 0) atividadeProjetoEntity.setIdTipoAtividade(atividadeProjeto.idTipoAtividade);
        if (atividadeProjeto.dataPrevInicio != null) atividadeProjetoEntity.setDataPrevInicio(FormataData.parseStringToDate(atividadeProjeto.dataPrevInicio));
        if (atividadeProjeto.dataPrevFim != null) atividadeProjetoEntity.setDataPrevFim(FormataData.parseStringToDate(atividadeProjeto.dataPrevFim));

        atividadeProjetoRepository.save(atividadeProjetoEntity);
    }

    @Transactional
    public void removerRegistrosAtividadesProjeto(long idProjeto) {

        List<RegistroAtividadeProjetoEntity> registroAtividadeProjetoList = registroAtividadeProjetoRepository.findAllByIdProjeto(idProjeto);

        for (RegistroAtividadeProjetoEntity registroAtividadeProjeto : registroAtividadeProjetoList) {

            List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoList = registroTarefaAtividadeProjetoRepository.findAllByRegistroAtividade(idProjeto, registroAtividadeProjeto.getId());
            for (RegistroTarefaAtividadeProjetoEntity registroTarefaAtividadeProjeto : registroTarefaAtividadeProjetoList) {
                registroTarefaAtividadeProjetoRepository.deleteById(registroTarefaAtividadeProjeto.getId());
            }

            registroAtividadeProjetoRepository.deleteById(registroAtividadeProjeto.getId());
        }
    }

    @Transactional
    public void gerarRegistrosAtividadesProjeto(long idProjeto) {

        List<AtividadeProjetoEntity> atividadeProjetoList = atividadeProjetoRepository.findAllByIdProjeto(idProjeto);

        for (AtividadeProjetoEntity atividadeProjeto : atividadeProjetoList) {
            RegistroAtividadeProjetoEntity registroAtividadeProjeto = new RegistroAtividadeProjetoEntity();
            registroAtividadeProjeto.setId(registroAtividadeProjetoRepository.findNextId());
            registroAtividadeProjeto.setIdProjeto(atividadeProjeto.getIdProjeto());
            registroAtividadeProjeto.setDescricao(atividadeProjeto.getDescricao());
            registroAtividadeProjeto.setAcaoRealizada("");
            registroAtividadeProjeto.setIdResponsavel(atividadeProjeto.getIdResponsavel());
            registroAtividadeProjeto.setCusto(0);
            registroAtividadeProjeto.setIdFase(atividadeProjeto.getIdFase());

            registroAtividadeProjetoRepository.save(registroAtividadeProjeto);

            if (atividadeProjeto.getIdTipoAtividade() != null) {

                List<TarefaTipoAtividadeProjetoEntity> tarefaTipoAtividadeProjetoList = tarefaTipoAtividadeProjetoRepository.findByTipoAtividadeId(atividadeProjeto.getIdTipoAtividade());

                for (TarefaTipoAtividadeProjetoEntity tarefaTipoAtividadeProjeto : tarefaTipoAtividadeProjetoList) {
                    RegistroTarefaAtividadeProjetoEntity registroTarefaAtividadeProjeto = new RegistroTarefaAtividadeProjetoEntity();
                    registroTarefaAtividadeProjeto.setId(registroTarefaAtividadeProjetoRepository.findNextId());
                    registroTarefaAtividadeProjeto.setIdProjeto(atividadeProjeto.getIdProjeto());
                    registroTarefaAtividadeProjeto.setIdRegistroAtividade(registroAtividadeProjeto.getId());
                    registroTarefaAtividadeProjeto.setDescricao(tarefaTipoAtividadeProjeto.getDescricao());
                    registroTarefaAtividadeProjeto.setAcaoRealizada("");
                    registroTarefaAtividadeProjeto.setIdResponsavel(atividadeProjeto.getIdResponsavel());
                    registroTarefaAtividadeProjeto.setCusto(0);

                    registroTarefaAtividadeProjetoRepository.save(registroTarefaAtividadeProjeto);
                }
            }
        }
    }
}
