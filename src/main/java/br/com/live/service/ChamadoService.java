package br.com.live.service;

import br.com.live.entity.Chamado;
import br.com.live.model.ConsultaChamado;
import br.com.live.repository.ChamadoRepository;
import br.com.live.util.FormataData;
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

    public Chamado saveChamado(int codChamado, String tituloChamado, int codTecnico, int codArea,
                               int codDepartamento, int codSetor, int impacto, String descricaoChamado, String dataChamado,
                               String nomeRequerente, String dataAnalise, String dataEntregaDes, String dataEntregaUsuario) {

        Chamado dadosChamado = null;

        dadosChamado = chamadoRepository.findByCodChamado(codChamado);

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
