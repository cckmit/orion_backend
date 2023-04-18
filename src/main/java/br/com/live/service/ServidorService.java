package br.com.live.service;

import br.com.live.entity.GestaoAtivosOportunidade;
import br.com.live.entity.Servidor;
import br.com.live.repository.GestaoAtivosOportunidadeRepository;
import br.com.live.repository.ServidorRepository;
import br.com.live.util.FormataData;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ServidorService {

    private ServidorRepository servidorRepository;
    private GestaoAtivosOportunidadeRepository gestaoAtivosOportunidadeRepository;

    public ServidorService(ServidorRepository servidorRepository, GestaoAtivosOportunidadeRepository gestaoAtivosOportunidadeRepository) {
        this.servidorRepository = servidorRepository;
        this.gestaoAtivosOportunidadeRepository = gestaoAtivosOportunidadeRepository;
    }

    public List<Servidor> findAllServidores() throws IOException {

        List<Servidor> servidores = servidorRepository.findAll();

        return servidores;
    }

    public void saveServidor(int id, String nomeServidor, boolean maquinaFisica, String sistemaOperacional, String ip, String hd, String memoria, String processador, 
    		String aplicacoes, byte[] documentacao, String status) {

        Servidor servidor = null;

        servidor = servidorRepository.findById(id);

        if (servidor == null) {
            servidor = new Servidor(servidorRepository.findNextId(), nomeServidor, maquinaFisica, sistemaOperacional, ip, hd, memoria, processador, aplicacoes, documentacao, status);
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
            servidor.status = status;
        }

        servidorRepository.save(servidor);
    }
    
    public void saveOportunidade(String id, int tipo, String dataCadastro, int prioridade, String descricao, String objetivo, String contextualizacao, String descricaoProblema, 
    		String perguntasEmAberto, String riscos) {

    	GestaoAtivosOportunidade oportunidade = null;

    	oportunidade = gestaoAtivosOportunidadeRepository.findByIdOp(id);

        if (oportunidade == null) {
        	oportunidade = new GestaoAtivosOportunidade(id, tipo, FormataData.parseStringToDate(dataCadastro), prioridade, descricao, objetivo, contextualizacao, descricaoProblema, perguntasEmAberto,
        			riscos);
        } else {
        	oportunidade.dataCadastro = FormataData.parseStringToDate(dataCadastro);
        	oportunidade.prioridade = prioridade;
        	oportunidade.descricao = descricao;
        	oportunidade.objetivo = objetivo;
        	oportunidade.contextualizacao = contextualizacao;
        	oportunidade.descricaoProblema = descricaoProblema;
        	oportunidade.perguntasEmAberto = perguntasEmAberto;
        	oportunidade.riscos = riscos;
        }
        gestaoAtivosOportunidadeRepository.save(oportunidade);
    }

    public void deleteById(int id) {
        servidorRepository.deleteById(id);
    }

}
