package br.com.live.service;

import br.com.live.entity.Servidor;
import br.com.live.repository.ServidorRepository;
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

        return servidores;
    }

    public void saveServidor(int id, String nomeServidor, boolean maquinaFisica, String sistemaOperacional, String ip, String hd, String memoria, String processador, String aplicacoes, byte[] documentacao) {

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
