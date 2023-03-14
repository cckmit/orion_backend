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

    public Chamado saveChamado(int codChamado, String tituloChamado, int codTecnico, int codArea,
                               int codDepartamento, int codSetor, int impacto, String descricaoChamado, String dataChamado,
                               String nomeRequerente, String dataAnalise, String dataEntregaDes, String dataEntregaUsuario) {

        Chamado dadosChamado = null;

        System.out.println(dataAnalise + " / " + dataEntregaDes + " / " + dataEntregaUsuario);

        dadosChamado = chamadoRepository.findByCodChamado(codChamado);

        System.out.println("dataAnalise: " + dataAnalise + " dataEntregaDes" + dataEntregaDes + " dataEntregaUsuario" + dataEntregaUsuario);

        if (dadosChamado == null) {
           dadosChamado = new Chamado(codChamado, tituloChamado, codTecnico, codArea, codDepartamento, codSetor, impacto, descricaoChamado, FormataData.parseStringToDate(dataChamado), nomeRequerente, FormataData.parseStringToDate(dataAnalise), FormataData.parseStringToDate(dataEntregaDes), FormataData.parseStringToDate(dataEntregaUsuario));
        } else {
            dadosChamado.tituloChamado = tituloChamado;
            dadosChamado.codTecnico = codTecnico;
            dadosChamado.codArea = codArea;
            dadosChamado.codDepartamento = codDepartamento;
            dadosChamado.codSetor = codSetor;
            dadosChamado.impacto = impacto;
            dadosChamado.descricaoChamado = descricaoChamado;
            dadosChamado.dataChamado = FormataData.parseStringToDate(dataChamado);
            dadosChamado.nomeRequerente = nomeRequerente;
            dadosChamado.dataAnalise = FormataData.parseStringToDate(dataAnalise);
            dadosChamado.dataEntregaDes = FormataData.parseStringToDate(dataEntregaDes);
            dadosChamado.dataEntregaUsuario = FormataData.parseStringToDate(dataEntregaUsuario);
        }
        chamadoRepository.save(dadosChamado);

        return dadosChamado;
    }

    public void deleteByCodChamado(int codChamado) {
        chamadoRepository.deleteByCodChamado(codChamado);
    }
}
