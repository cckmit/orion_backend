package br.com.live.service;

import br.com.live.entity.Servidor;
import br.com.live.repository.ServidorRepository;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;

@Service
@Transactional
public class ServidorService {

    private ServidorRepository servidorRepository;

    public ServidorService(ServidorRepository servidorRepository) {
        this.servidorRepository = servidorRepository;
    }

    public List<Servidor> findAllServidores() throws IOException {

        List<Servidor> servidores = servidorRepository.findAll();


        for (Servidor servidor : servidores) {
            byte[] arquivoBytes = servidor.getDocumentacao();

            String extensaoAquivo = obterExtensaoArquivo(arquivoBytes);

            File arquivo = byteArrayToFile(arquivoBytes, "C:\\tempArquivos\\Doc-" + servidor.nomeServidor + "." + extensaoAquivo);
            byte[] documentacaoBytes = fileToByteArray(arquivo);
            servidor.setDocumentacao(documentacaoBytes);
        }
        return servidores;
    }

    public static String obterExtensaoArquivo(byte[] arquivoBytes) {
        Tika tika = new Tika();
        String tipoMIME = tika.detect(arquivoBytes);
        return tipoMIME.split("/")[1];
    }

    private File byteArrayToFile(byte[] byteArray, String fileName) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(byteArray);
        fos.close();
        return file;
    }

    private byte[] fileToByteArray(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            return bos.toByteArray();
        }
    }

    public void saveServidor(int id, String nomeServidor, int maquinaFisica, String sistemaOperacional, String ip, int hd, int memoria, String processador, String aplicacoes, byte[] documentacao) {

        Servidor servidor = null;

        servidor = servidorRepository.findById(id);

        if (servidor == null) {
            servidor = new Servidor(servidorRepository.findNextId(), nomeServidor, maquinaFisica, sistemaOperacional, ip, hd, memoria, processador, aplicacoes, documentacao);
        } else {
            servidor.nomeServidor = nomeServidor;
            servidor.maquinaFisica = maquinaFisica;
            servidor.sistemaOperacional = sistemaOperacional;
            servidor.ip = ip;
            servidor.hd = hd;
            servidor.memoria = memoria;
            servidor.processador = processador;
            servidor.aplicacoes = aplicacoes;
            servidor.documentacao = documentacao;
        }

        servidorRepository.save(servidor);
    }

    public void deleteById(int id) {
        servidorRepository.deleteById(id);
    }

}
