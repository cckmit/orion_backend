package br.com.live.sistema.service;

import br.com.live.sistema.body.BodyProjeto;
import br.com.live.sistema.entity.ProjetoEntity;
import br.com.live.sistema.repository.ProjetoRepository;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProjetoService {

    ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public List<ProjetoEntity> saveProjeto(BodyProjeto projeto){

        if (projeto.id == 0) projeto.id = projetoRepository.findNextId();

        ProjetoEntity projetoEntity = new ProjetoEntity();
        projetoEntity.setId(projeto.id);
        projetoEntity.setCodProjeto(projeto.codProjeto);
        projetoEntity.setDescricao(projeto.descricao);
        projetoEntity.setDataCriacao(FormataData.parseStringToDate(projeto.dataCriacao));
        projetoEntity.setArea(projeto.area);
        projetoEntity.setDepartamento(projeto.departamento);
        projetoEntity.setSetor(projeto.setor);
        projetoEntity.setOrigemProjeto(projeto.origemProjeto);
        projetoEntity.setSubOrigemProjeto(projeto.subOrigemProjeto);
        projetoEntity.setStatus(projeto.status);
        projetoRepository.save(projetoEntity);

        return projetoRepository.findAll();
    }
}
