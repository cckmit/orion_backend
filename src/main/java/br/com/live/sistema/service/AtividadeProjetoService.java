package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyAtividadeProjeto;
import br.com.live.sistema.entity.AtividadeProjetoEntity;
import br.com.live.sistema.repository.AtividadeProjetoRepository;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AtividadeProjetoService {

    AtividadeProjetoRepository atividadeProjetoRepository;

    public AtividadeProjetoService(AtividadeProjetoRepository atividadeProjetoRepository) {
        this.atividadeProjetoRepository = atividadeProjetoRepository;
    }

    public List<AtividadeProjetoEntity> saveAtividadeProjeto(BodyAtividadeProjeto atividadeProjeto){

        if (atividadeProjeto.id == 0) atividadeProjeto.id = atividadeProjetoRepository.findNextId();

        AtividadeProjetoEntity atividadeProjetoEntity = new AtividadeProjetoEntity();
        atividadeProjetoEntity.setId(atividadeProjeto.id);
        atividadeProjetoEntity.setDescricao(atividadeProjeto.descricao);
        atividadeProjetoEntity.setIdProjeto(atividadeProjeto.idProjeto);
        atividadeProjetoEntity.setIdFase(atividadeProjeto.idFase);
        atividadeProjetoEntity.setIdTipoAtividade(atividadeProjeto.idTipoAtividade);
        atividadeProjetoEntity.setResponsavel(atividadeProjeto.responsavel);
        atividadeProjetoEntity.setDataPrevInicio(FormataData.parseStringToDate(atividadeProjeto.dataPrevInicio));
        atividadeProjetoEntity.setDataPrevFim(FormataData.parseStringToDate(atividadeProjeto.dataPrevFim));
        atividadeProjetoEntity.setTempoPrevisto(atividadeProjeto.tempoPrevisto);
        atividadeProjetoRepository.save(atividadeProjetoEntity);

        return atividadeProjetoRepository.findAllByIdProjeto(atividadeProjeto.idProjeto);
    }
}
