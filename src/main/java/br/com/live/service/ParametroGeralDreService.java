package br.com.live.service;

import br.com.live.custom.DreLojaCustom;
import br.com.live.entity.ConciliacaoLojaDreEntity;
import br.com.live.entity.ParametroGeralDreEntity;
import br.com.live.repository.ConciliacaoLojaDreRepository;
import br.com.live.repository.ParametroGeralDreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        long nextId = conciliacaoLojaDreRepository.findNextId();

        for (ConciliacaoLojaDreEntity fieldConciliacaoLojaDre : conciliacaoLojaDreEntityList){
            fieldConciliacaoLojaDre.id = (nextId++);

            BigDecimal valTaxaCaptura = fieldConciliacaoLojaDre.valTaxaCaptura != null ? fieldConciliacaoLojaDre.valTaxaCaptura : BigDecimal.ZERO;
            BigDecimal valCustoAntecipacao = fieldConciliacaoLojaDre.valCustoAntecipacao != null ? fieldConciliacaoLojaDre.valCustoAntecipacao : BigDecimal.ZERO;

            fieldConciliacaoLojaDre.valTaxaCaptura = formatValorPropriedade(2, valTaxaCaptura);
            fieldConciliacaoLojaDre.valCustoAntecipacao = formatValorPropriedade(2, valCustoAntecipacao);

            conciliacaoLojaDreRepository.save(fieldConciliacaoLojaDre);
        }
    }

    BigDecimal formatValorPropriedade(int qtdDecimal, BigDecimal valPropriedade){

        if (valPropriedade.remainder(BigDecimal.ONE).compareTo(new BigDecimal("0.5")) == 0) {
            valPropriedade = valPropriedade.setScale(qtdDecimal, RoundingMode.CEILING);
        } else {
            valPropriedade = valPropriedade.setScale(qtdDecimal, RoundingMode.HALF_UP);
        }

        return valPropriedade;
    }

    public void deleteDreParametroGeral(long id, int mesDre, int anoDre){
        parametroGeralDreRepository.deleteById(id);
        dreLojaCustom.deleteParametrosLojaByMesAno(mesDre, anoDre);
    }
}
