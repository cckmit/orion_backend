package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyAtividadeProjeto;
import br.com.live.sistema.entity.AtividadeProjetoEntity;
import br.com.live.sistema.entity.RegistroAtividadeProjetoEntity;
import br.com.live.sistema.entity.RegistroTarefaAtividadeProjetoEntity;
import br.com.live.sistema.repository.AtividadeProjetoRepository;
import br.com.live.sistema.repository.RegistroAtividadeProjetoRepository;
import br.com.live.sistema.repository.RegistroTarefaAtividadeProjetoRepository;
import br.com.live.sistema.repository.TarefaTipoAtividadeProjetoRepository;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AtividadeProjetoService {

    AtividadeProjetoRepository atividadeProjetoRepository;
    TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository;
    RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository;
    RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository;
    ProjetoService projetoService;

    public AtividadeProjetoService(AtividadeProjetoRepository atividadeProjetoRepository, TarefaTipoAtividadeProjetoRepository tarefaTipoAtividadeProjetoRepository, RegistroAtividadeProjetoRepository registroAtividadeProjetoRepository, RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository, ProjetoService projetoService) {
        this.atividadeProjetoRepository = atividadeProjetoRepository;
        this.tarefaTipoAtividadeProjetoRepository = tarefaTipoAtividadeProjetoRepository;
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
            atividadeProjeto.tempoPrevisto = atividadeProjetoEntity.getTempoPrevisto();
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
        atividadeProjetoEntity.setIdResponsavel(atividadeProjeto.idResponsavel);
        atividadeProjetoEntity.setTempoPrevisto(atividadeProjeto.tempoPrevisto);
        atividadeProjetoEntity.setMarco(atividadeProjeto.marco);

        if (atividadeProjeto.idTipoAtividade != 0) atividadeProjetoEntity.setIdTipoAtividade(atividadeProjeto.idTipoAtividade);
        if (atividadeProjeto.dataPrevInicio != null) atividadeProjetoEntity.setDataPrevInicio(FormataData.parseStringToDate(atividadeProjeto.dataPrevInicio));
        if (atividadeProjeto.dataPrevFim != null) atividadeProjetoEntity.setDataPrevFim(FormataData.parseStringToDate(atividadeProjeto.dataPrevFim));

        atividadeProjetoRepository.save(atividadeProjetoEntity);
    }
}
