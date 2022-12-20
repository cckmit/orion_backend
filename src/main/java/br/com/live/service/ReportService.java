package br.com.live.service;

import net.sf.jasperreports.engine.*;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
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
    public String generateReport(String reportFormat, JRBeanCollectionDataSource dataSource, String layout, Map<String, Object> parameters) throws FileNotFoundException, JRException {
        
        int fileName = (Integer) parameters.get("numMinuta");
        
    	File file = ResourceUtils.getFile(configuracoesService.getDiretorioJasper() + layout + ".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        	
        parameters.put("CollectionBeanParam", dataSource);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, configuracoesService.getDiretorioTemp() + fileName + ".pdf");
        }
        
        String arquivoGerado = fileName + "." + reportFormat;

        return arquivoGerado;
    }
}
