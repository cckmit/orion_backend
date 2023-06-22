package br.com.live.sistema.service;

import br.com.live.sistema.entity.Chamado;
import br.com.live.sistema.model.ConsultaChamado;
import br.com.live.sistema.repository.ChamadoRepository;
import br.com.live.util.FormataData;
import br.com.live.util.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final ReportService reportService;

    public ChamadoService(ChamadoRepository chamadoRepository, ReportService reportService) {
        this.chamadoRepository = chamadoRepository;
        this.reportService = reportService;
    }

    public Chamado findChamadosByCodigo(int codChamado) {
        return chamadoRepository.findByCodChamado(codChamado);
    }

    public Chamado saveChamado(int codChamado, String tituloChamado, int codTecnico, int codArea, int codDepartamento, int codSetor, int impacto,
                               String descricaoChamado, String nomeRequerente, String dataInicioTriagem, String dataFimTriagem, String dataInicioAnalise,
                               String dataFimAnalise, String dataInicioAprovEscopo, String dataFimAprovEscopo, String dataInicioOrcamento, String dataFimOrcamento,
                               String dataInicioFilaDesenvForn, String dataFimFilaDesenvForn, String dataInicioDesenvForn, String dataFimDesenvForn,
                               String dataInicioFilaDesenvInt, String dataFimFilaDesenvInt, String dataInicioDesenvInt, String dataFimDesenvInt,
                               String dataInicioQualidadeTestes, String dataFimQualidadeTestes, String dataInicioEntrega, String dataFimEntrega) {

        Chamado dadosChamado = null;

        dadosChamado = chamadoRepository.findByCodChamado(codChamado);

        if (dadosChamado == null) {
           dadosChamado = new Chamado(codChamado, tituloChamado, codTecnico, codArea, codDepartamento, codSetor, impacto, descricaoChamado,
                   nomeRequerente, FormataData.parseStringToDate(dataInicioTriagem), FormataData.parseStringToDate(dataFimTriagem),
                   FormataData.parseStringToDate(dataInicioAnalise), FormataData.parseStringToDate(dataFimAnalise),
                   FormataData.parseStringToDate(dataInicioAprovEscopo), FormataData.parseStringToDate(dataFimAprovEscopo),
                   FormataData.parseStringToDate(dataInicioOrcamento), FormataData.parseStringToDate(dataFimOrcamento),
                   FormataData.parseStringToDate(dataInicioFilaDesenvForn), FormataData.parseStringToDate(dataFimFilaDesenvForn),
                   FormataData.parseStringToDate(dataInicioDesenvForn), FormataData.parseStringToDate(dataFimDesenvForn),
                   FormataData.parseStringToDate(dataInicioFilaDesenvInt), FormataData.parseStringToDate(dataFimFilaDesenvInt),
                   FormataData.parseStringToDate(dataInicioDesenvInt), FormataData.parseStringToDate(dataFimDesenvInt),
                   FormataData.parseStringToDate(dataInicioQualidadeTestes), FormataData.parseStringToDate(dataFimQualidadeTestes),
                   FormataData.parseStringToDate(dataInicioEntrega), FormataData.parseStringToDate(dataFimEntrega));
        } else {
            dadosChamado.tituloChamado = tituloChamado;
            dadosChamado.codTecnico = codTecnico;
            dadosChamado.codArea = codArea;
            dadosChamado.codDepartamento = codDepartamento;
            dadosChamado.codSetor = codSetor;
            dadosChamado.impacto = impacto;
            dadosChamado.descricaoChamado = descricaoChamado;
            dadosChamado.nomeRequerente = nomeRequerente;
            dadosChamado.dataInicioTriagem = !dataInicioTriagem.isEmpty() ? FormataData.parseStringToDate(dataInicioTriagem) : null;
            dadosChamado.dataFimTriagem = !dataFimTriagem.isEmpty() ? FormataData.parseStringToDate(dataFimTriagem) : null;
            dadosChamado.dataInicioAnalise = !dataInicioAnalise.isEmpty() ? FormataData.parseStringToDate(dataInicioAnalise) : null;
            dadosChamado.dataFimAnalise = !dataFimAnalise.isEmpty() ? FormataData.parseStringToDate(dataFimAnalise) : null;
            dadosChamado.dataInicioAprovEscopo = !dataInicioAprovEscopo.isEmpty() ? FormataData.parseStringToDate(dataInicioAprovEscopo) : null;
            dadosChamado.dataFimAprovEscopo = !dataFimAprovEscopo.isEmpty() ? FormataData.parseStringToDate(dataFimAprovEscopo) : null;
            dadosChamado.dataInicioOrcamento = !dataInicioOrcamento.isEmpty() ? FormataData.parseStringToDate(dataInicioOrcamento) : null;
            dadosChamado.dataFimOrcamento = !dataFimOrcamento.isEmpty() ? FormataData.parseStringToDate(dataFimOrcamento) : null;
            dadosChamado.dataInicioFilaDesenvForn = !dataInicioFilaDesenvForn.isEmpty() ? FormataData.parseStringToDate(dataInicioFilaDesenvForn) : null;
            dadosChamado.dataFimFilaDesenvForn = !dataFimFilaDesenvForn.isEmpty() ? FormataData.parseStringToDate(dataFimFilaDesenvForn) : null;
            dadosChamado.dataInicioDesenvForn = !dataInicioDesenvForn.isEmpty() ? FormataData.parseStringToDate(dataInicioDesenvForn) : null;
            dadosChamado.dataFimDesenvForn = !dataFimDesenvForn.isEmpty() ? FormataData.parseStringToDate(dataFimDesenvForn) : null;
            dadosChamado.dataInicioFilaDesenvInt = !dataInicioFilaDesenvInt.isEmpty() ? FormataData.parseStringToDate(dataInicioFilaDesenvInt) : null;
            dadosChamado.dataFimFilaDesenvInt = !dataFimFilaDesenvInt.isEmpty() ? FormataData.parseStringToDate(dataFimFilaDesenvInt) : null;
            dadosChamado.dataInicioDesenvInt = !dataInicioDesenvInt.isEmpty() ? FormataData.parseStringToDate(dataInicioDesenvInt) : null;
            dadosChamado.dataFimDesenvInt = !dataFimDesenvInt.isEmpty() ? FormataData.parseStringToDate(dataFimDesenvInt) : null;
            dadosChamado.dataInicioQualidadeTestes = !dataInicioQualidadeTestes.isEmpty() ? FormataData.parseStringToDate(dataInicioQualidadeTestes) : null;
            dadosChamado.dataFimQualidadeTestes = !dataFimQualidadeTestes.isEmpty() ? FormataData.parseStringToDate(dataFimQualidadeTestes) : null;
            dadosChamado.dataInicioEntrega = !dataInicioEntrega.isEmpty() ? FormataData.parseStringToDate(dataInicioEntrega) : null;
            dadosChamado.dataFimEntrega = !dataFimEntrega.isEmpty() ? FormataData.parseStringToDate(dataFimEntrega) : null;
        }


        chamadoRepository.save(dadosChamado);

        return dadosChamado;
    }

    public String gerarPdfChamados(List<ConsultaChamado> chamados, String dataInicio, String dataFim) throws JRException, FileNotFoundException {

        String nomeRelatorioGerado = "";

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(chamados);

        Map<String, Object> parameters = setParameters(dataInicio, dataFim);

        nomeRelatorioGerado = reportService.generateReport("pdf", dataSource, "chamados", parameters, "RelatorioMelhoriasSistemas", true);

        return nomeRelatorioGerado;
    }

    public Map<String, Object> setParameters(String dataInicio, String dataFim) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dataInicio", dataInicio);
        parameters.put("dataFim", dataFim);

        return parameters;
    }

    public void deleteByCodChamado(int codChamado) {
        chamadoRepository.deleteByCodChamado(codChamado);
    }
}
