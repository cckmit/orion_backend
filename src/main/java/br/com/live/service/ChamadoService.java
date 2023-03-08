package br.com.live.service;

import br.com.live.entity.Chamado;
import br.com.live.repository.ChamadoRepository;
import br.com.live.util.FormataData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;

    public ChamadoService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    public Chamado findChamadosByCodigo(int codChamado) {
        return chamadoRepository.findByCodChamado(codChamado);
    }

    public Chamado saveChamado(int codChamado, String tituloChamado, int codRequerente, int codTecnico, int codArea,
                               int codDepartamento, int codSetor, boolean impacto, String descricaoChamado, String dataChamado) {

        Chamado dadosChamado = null;

        dadosChamado = chamadoRepository.findByCodChamado(codChamado);

        if (dadosChamado == null) {
           dadosChamado = new Chamado(codChamado, tituloChamado,codRequerente, codTecnico, codArea,
                   codDepartamento, codSetor, impacto, descricaoChamado, FormataData.parseStringToDate(dataChamado));
        } else {
            dadosChamado.tituloChamado = tituloChamado;
            dadosChamado.codRequerente = codRequerente;
            dadosChamado.codTecnico = codTecnico;
            dadosChamado.codArea = codArea;
            dadosChamado.codDepartamento = codDepartamento;
            dadosChamado.codSetor = codSetor;
            dadosChamado.impacto = impacto;
            dadosChamado.descricaoChamado = descricaoChamado;
            dadosChamado.dataChamado = FormataData.parseStringToDate(dataChamado);
        }
        chamadoRepository.save(dadosChamado);

        return dadosChamado;
    }

    public void deleteByCodChamado(int codChamado) {
        chamadoRepository.deleteByCodChamado(codChamado);
    }
}
