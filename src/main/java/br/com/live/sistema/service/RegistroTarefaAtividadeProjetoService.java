package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyRegistroTarefaAtividadeProjeto;
import br.com.live.sistema.entity.RegistroTarefaAtividadeProjetoEntity;
import br.com.live.sistema.repository.RegistroTarefaAtividadeProjetoRepository;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RegistroTarefaAtividadeProjetoService {

    RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository;

    public RegistroTarefaAtividadeProjetoService(RegistroTarefaAtividadeProjetoRepository registroTarefaAtividadeProjetoRepository) {
        this.registroTarefaAtividadeProjetoRepository = registroTarefaAtividadeProjetoRepository;
    }

    public List<BodyRegistroTarefaAtividadeProjeto> saveRegistroTarefaAtividadeProjeto(BodyRegistroTarefaAtividadeProjeto registroTarefaAtividadeProjeto) throws ParseException {

        if (registroTarefaAtividadeProjeto.id == 0) registroTarefaAtividadeProjeto.id = registroTarefaAtividadeProjetoRepository.findNextId();

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

        RegistroTarefaAtividadeProjetoEntity registroTarefaAtividadeProjetoEntity = new RegistroTarefaAtividadeProjetoEntity();
        registroTarefaAtividadeProjetoEntity.setId(registroTarefaAtividadeProjeto.id);
        registroTarefaAtividadeProjetoEntity.setIdProjeto(registroTarefaAtividadeProjeto.idProjeto);
        registroTarefaAtividadeProjetoEntity.setIdRegistroAtividade(registroTarefaAtividadeProjeto.idRegistroAtividade);
        registroTarefaAtividadeProjetoEntity.setDescricao(registroTarefaAtividadeProjeto.descricao);
        registroTarefaAtividadeProjetoEntity.setAcaoRealizada(registroTarefaAtividadeProjeto.acaoRealizada);
        registroTarefaAtividadeProjetoEntity.setIdResponsavel(registroTarefaAtividadeProjeto.idResponsavel);
        registroTarefaAtividadeProjetoEntity.setDocumentoAssociado(registroTarefaAtividadeProjeto.documentoAssociado);
        registroTarefaAtividadeProjetoEntity.setCusto(registroTarefaAtividadeProjeto.custo);

        if (registroTarefaAtividadeProjeto.dataInicio != null) registroTarefaAtividadeProjetoEntity.setDataInicio(FormataData.parseStringToDate(registroTarefaAtividadeProjeto.dataInicio));
        if (registroTarefaAtividadeProjeto.horaInicio != null) registroTarefaAtividadeProjetoEntity.setHoraInicio(formatoHora.parse(registroTarefaAtividadeProjeto.horaInicio));
        if (registroTarefaAtividadeProjeto.dataFim != null) registroTarefaAtividadeProjetoEntity.setDataFim(FormataData.parseStringToDate(registroTarefaAtividadeProjeto.dataFim));
        if (registroTarefaAtividadeProjeto.horaFim != null) registroTarefaAtividadeProjetoEntity.setHoraFim(formatoHora.parse(registroTarefaAtividadeProjeto.horaFim));

        registroTarefaAtividadeProjetoRepository.save(registroTarefaAtividadeProjetoEntity);

        return findAll(registroTarefaAtividadeProjeto.idProjeto, registroTarefaAtividadeProjeto.idRegistroAtividade);
    }

    public List<BodyRegistroTarefaAtividadeProjeto> findAll(Long idProjeto, Long idRegistroAtividadeProjeto){

        List<RegistroTarefaAtividadeProjetoEntity> registroTarefaAtividadeProjetoEntityList;

        if (idProjeto == 0 && idRegistroAtividadeProjeto == 0){
            registroTarefaAtividadeProjetoEntityList = registroTarefaAtividadeProjetoRepository.findAll();
        } else if (idProjeto != 0 && idRegistroAtividadeProjeto == 0) {
            registroTarefaAtividadeProjetoEntityList = registroTarefaAtividadeProjetoRepository.findAllByIdProjeto(idProjeto);
        } else {
            registroTarefaAtividadeProjetoEntityList = registroTarefaAtividadeProjetoRepository.findAllByRegistroAtividade(idProjeto, idRegistroAtividadeProjeto);
        }

        List<BodyRegistroTarefaAtividadeProjeto> registroTarefaAtividadeProjetoBodyList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        for (RegistroTarefaAtividadeProjetoEntity registroTarefaAtividadeProjetoEntity : registroTarefaAtividadeProjetoEntityList){

            BodyRegistroTarefaAtividadeProjeto registroTarefaAtividadeProjeto = new BodyRegistroTarefaAtividadeProjeto();
            registroTarefaAtividadeProjeto.id = registroTarefaAtividadeProjetoEntity.getId();
            registroTarefaAtividadeProjeto.idProjeto = registroTarefaAtividadeProjetoEntity.getIdProjeto();
            registroTarefaAtividadeProjeto.idRegistroAtividade = registroTarefaAtividadeProjetoEntity.getIdRegistroAtividade();
            registroTarefaAtividadeProjeto.descricao = registroTarefaAtividadeProjetoEntity.getDescricao();
            registroTarefaAtividadeProjeto.acaoRealizada = registroTarefaAtividadeProjetoEntity.getAcaoRealizada();
            registroTarefaAtividadeProjeto.idResponsavel = registroTarefaAtividadeProjetoEntity.getIdResponsavel();
            registroTarefaAtividadeProjeto.documentoAssociado = registroTarefaAtividadeProjetoEntity.getDocumentoAssociado();
            registroTarefaAtividadeProjeto.custo = registroTarefaAtividadeProjetoEntity.getCusto();

            if (registroTarefaAtividadeProjetoEntity.getDataInicio() != null) registroTarefaAtividadeProjeto.dataInicio = dateFormat.format(registroTarefaAtividadeProjetoEntity.getDataInicio());
            if (registroTarefaAtividadeProjetoEntity.getHoraInicio() != null) registroTarefaAtividadeProjeto.horaInicio = timeFormat.format(registroTarefaAtividadeProjetoEntity.getHoraInicio());
            if (registroTarefaAtividadeProjetoEntity.getDataFim() != null) registroTarefaAtividadeProjeto.dataFim = dateFormat.format(registroTarefaAtividadeProjetoEntity.getDataFim());
            if (registroTarefaAtividadeProjetoEntity.getHoraFim() != null) registroTarefaAtividadeProjeto.horaFim = timeFormat.format(registroTarefaAtividadeProjetoEntity.getHoraFim());

            registroTarefaAtividadeProjetoBodyList.add(registroTarefaAtividadeProjeto);
        }
        return registroTarefaAtividadeProjetoBodyList;
    }
}