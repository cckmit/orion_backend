package br.com.live.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ReportService {
	
	private final ConfiguracoesService configuracoesService;
	
	public ReportService(ConfiguracoesService configuracoesService) {
		this.configuracoesService = configuracoesService;
	}
	
	//
	// METODO PARA GERAR O REPORT A PARTIR DE UM .jrxml E GRAVAR NA PASTA DEFINIDA NO application-dev.properties
	// EXEMPLO DE DATASOURCE - JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(micromov);
	//
    public String generateReport(String reportFormat, JRBeanCollectionDataSource dataSource, String layout) throws FileNotFoundException, JRException {
        Random hashName = new Random();
        
        int fileName = hashName.nextInt(9999999);
        
    	File file = ResourceUtils.getFile("classpath:" + layout + ".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Orion");
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, configuracoesService.getDiretorioReport() + fileName + ".pdf");
        }
        
        String arquivoGerado = fileName + "." + reportFormat;

        return arquivoGerado;
    }
}
