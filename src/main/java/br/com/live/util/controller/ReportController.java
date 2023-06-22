package br.com.live.util.controller;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.live.util.service.ConfiguracoesService;

@RestController
@CrossOrigin
@RequestMapping("/reports")
public class ReportController {
	
	private final ConfiguracoesService configuracoesService;
	
	@Autowired
	public ReportController(ConfiguracoesService configuracoesService) {
		this.configuracoesService = configuracoesService;
	}
	
	//
    // CONTROLLER PARA FAZER DOWNLOAD DE ARQUIVOS A PARTIR DO NOME
	// EX. fileName = exemplo.pdf
    //
	
    @RequestMapping(value = "/download-report/{fileName}", method = RequestMethod.GET)
    @ResponseBody
    public void report(@PathVariable("fileName") String nomeArquivo, HttpServletResponse response){                  
    	response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + nomeArquivo);
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		try {
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			FileInputStream fis = new FileInputStream(configuracoesService.getDiretorioTemp() + nomeArquivo);
			int len;
			byte[] buf = new byte[1024];
			while((len = fis.read(buf)) > 0) {
				bos.write(buf, 0, len);
			}
			bos.close();
			fis.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
