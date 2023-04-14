package br.com.live.service;

import br.com.live.custom.OrcamentoLojaDreCustom;
import br.com.live.entity.OrcamentoLojaDreEntity;
import br.com.live.repository.OrcamentoLojaDreRepository;
import org.hibernate.cfg.BinderHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
public class OrcamentoLojaDreService {

    OrcamentoLojaDreRepository orcamentoLojaDreRepository;
    OrcamentoLojaDreCustom orcamentoLojaDreCustom;

    public OrcamentoLojaDreService(OrcamentoLojaDreRepository orcamentoLojaDreRepository, OrcamentoLojaDreCustom orcamentoLojaDreCustom) {
        this.orcamentoLojaDreRepository = orcamentoLojaDreRepository;
        this.orcamentoLojaDreCustom = orcamentoLojaDreCustom;
    }

    public void deleteOrcamentoLojaByCnpjAno(String cnpjLoja, int anoOrcamento){
        orcamentoLojaDreCustom.deleteOrcamentoLojaByCnpjAno(cnpjLoja, anoOrcamento);
    }

    public void saveOrcamentoLojaDre(List<OrcamentoLojaDreEntity> orcamentoLojaDreList){

        long nextId = orcamentoLojaDreRepository.findNextId();

        for (OrcamentoLojaDreEntity fieldOrcamentoLojaDre : orcamentoLojaDreList){
            fieldOrcamentoLojaDre.id = (nextId++);

            String propriedade = fieldOrcamentoLojaDre.propriedade;
            BigDecimal valPropriedade = fieldOrcamentoLojaDre.valPropriedade != null ? fieldOrcamentoLojaDre.valPropriedade : BigDecimal.ZERO;

            if (propriedade.equals("Preço Médio")) {
                fieldOrcamentoLojaDre.valPropriedade = formatValorPropriedade(2, valPropriedade);
            } else if (propriedade.contains("%")) {
                valPropriedade = valPropriedade.multiply(new BigDecimal("100"));
                fieldOrcamentoLojaDre.valPropriedade = formatValorPropriedade(2, valPropriedade);
            }else{
                fieldOrcamentoLojaDre.valPropriedade = formatValorPropriedade(0, valPropriedade);
            }

            orcamentoLojaDreRepository.save(fieldOrcamentoLojaDre);
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
}