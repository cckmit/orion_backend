package br.com.live.service;

import br.com.live.custom.DreLojaCustom;
import br.com.live.entity.ConciliacaoLojaDreEntity;
import br.com.live.entity.ParametroGeralDreEntity;
import br.com.live.repository.ConciliacaoLojaDreRepository;
import br.com.live.repository.ParametroGeralDreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParametroGeralDreService {

    private final ParametroGeralDreRepository parametroGeralDreRepository;
    private final ConciliacaoLojaDreRepository conciliacaoLojaDreRepository;
    private final DreLojaCustom dreLojaCustom;

    public ParametroGeralDreService(ParametroGeralDreRepository parametroGeralDreRepository, ConciliacaoLojaDreRepository conciliacaoLojaDreRepository,DreLojaCustom dreLojaCustom) {
        this.parametroGeralDreRepository = parametroGeralDreRepository;
        this.conciliacaoLojaDreRepository = conciliacaoLojaDreRepository;
        this.dreLojaCustom = dreLojaCustom;
    }

    public List<ParametroGeralDreEntity> saveParametroGeralDre(ParametroGeralDreEntity parametroGeralDreEntity){

        Optional<ParametroGeralDreEntity> optionalEntity = parametroGeralDreRepository.findById(parametroGeralDreEntity.getId());

        if(optionalEntity.isPresent()) {
            ParametroGeralDreEntity entity = optionalEntity.get();
            entity.setPercEncargos(parametroGeralDreEntity.getPercEncargos());
            entity.setValImpostoPlanejamento(parametroGeralDreEntity.getValImpostoPlanejamento());
            entity.setValCustoVendaProduto(parametroGeralDreEntity.getValCustoVendaProduto());
            parametroGeralDreRepository.save(entity);
        } else {
            parametroGeralDreEntity.setId(parametroGeralDreRepository.findNextID());
            parametroGeralDreRepository.save(parametroGeralDreEntity);
        }

        return parametroGeralDreRepository.findAll();
    }

    public void saveDreConciliacaoLoja(List<ConciliacaoLojaDreEntity> conciliacaoLojaDreEntityList){

        ConciliacaoLojaDreEntity firstItem = conciliacaoLojaDreEntityList.get(0);
        int mesDre = firstItem.mesDre;
        int anoDre = firstItem.anoDre;
        dreLojaCustom.deleteParametrosLojaByMesAno(mesDre, anoDre);

        long nextId = conciliacaoLojaDreRepository.findNextId();

        for (ConciliacaoLojaDreEntity fieldConciliacaoLojaDre : conciliacaoLojaDreEntityList){
            fieldConciliacaoLojaDre.id = (nextId++);

            conciliacaoLojaDreRepository.save(fieldConciliacaoLojaDre);
        }
    }

    public void deleteDreParametroGeral(long id, int mesDre, int anoDre){
        parametroGeralDreRepository.deleteById(id);
        dreLojaCustom.deleteParametrosLojaByMesAno(mesDre, anoDre);
    }
}
