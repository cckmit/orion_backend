package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyAtividadeProjeto;
import br.com.live.sistema.entity.AtividadeProjetoEntity;
import br.com.live.sistema.repository.AtividadeProjetoRepository;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AtividadeProjetoService {

    AtividadeProjetoRepository atividadeProjetoRepository;

    public AtividadeProjetoService(AtividadeProjetoRepository atividadeProjetoRepository) {
        this.atividadeProjetoRepository = atividadeProjetoRepository;
    }

    public List<BodyAtividadeProjeto> saveAtividadeProjeto(BodyAtividadeProjeto atividadeProjeto){

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

        return findAll(atividadeProjeto.idProjeto);
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

}
