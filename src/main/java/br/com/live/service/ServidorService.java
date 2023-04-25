package br.com.live.service;

import br.com.live.custom.GestaoAtivosCustom;
import br.com.live.entity.GestaoAtivosOportunidade;
import br.com.live.entity.Servidor;
import br.com.live.model.ConsultaGestaoAtivos;
import br.com.live.repository.GestaoAtivosOportunidadeRepository;
import br.com.live.repository.ServidorRepository;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;
import br.com.live.util.ErrorMessageException;
import br.com.live.util.FormataData;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;

@Service
@Transactional
public class ServidorService {

    private ServidorRepository servidorRepository;
    private GestaoAtivosOportunidadeRepository gestaoAtivosOportunidadeRepository;
    private GestaoAtivosCustom gestaoAtivosCustom;

    public ServidorService(ServidorRepository servidorRepository, GestaoAtivosOportunidadeRepository gestaoAtivosOportunidadeRepository, GestaoAtivosCustom gestaoAtivosCustom) {
        this.servidorRepository = servidorRepository;
        this.gestaoAtivosOportunidadeRepository = gestaoAtivosOportunidadeRepository;
        this.gestaoAtivosCustom = gestaoAtivosCustom;
    }

    public List<ConsultaGestaoAtivos> findAllServidores() {
        return gestaoAtivosCustom.findAllServidores();
    }
    
    public List<ConteudoChaveAlfaNum> findUser(String usuario){
    	return gestaoAtivosCustom.findUser(usuario);
    }

    public void saveServidor(int id, String nomeServidor, boolean maquinaFisica, String sistemaOperacional, String ip, String hd, String memoria, String processador, 
    		String aplicacoes, byte[] documentacao, String status, int gestorResponsavel) {

        Servidor servidor = null;

        servidor = servidorRepository.findById(id);

        if (servidor == null) {
            servidor = new Servidor(servidorRepository.findNextId(), nomeServidor, maquinaFisica, sistemaOperacional, ip, hd, memoria, processador, aplicacoes, documentacao, status,
            		gestorResponsavel);
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
    
    public void saveOportunidade(int id, int tipo, int idAtivo, String nomeAtivo, String dataCadastro, int prioridade, String descricao, String objetivo, String contextualizacao, 
    		String descricaoProblema, String perguntasEmAberto, String riscos) {

    	GestaoAtivosOportunidade oportunidade = null;
    	int nextId = gestaoAtivosCustom.findNextIdByTipo(id, tipo);
  
        oportunidade = new GestaoAtivosOportunidade(tipo + "-" + id + "-" + nextId, tipo, id, nextId, nomeAtivo, FormataData.parseStringToDate(dataCadastro), prioridade, descricao, objetivo, 
        		contextualizacao, descricaoProblema, perguntasEmAberto, riscos);
         
        gestaoAtivosOportunidadeRepository.save(oportunidade);
    }

    public void deleteById(int id) {
    	
    	if(!gestaoAtivosCustom.deleteServidorById(id))
    		throw new ErrorMessageException("NÃO PERMITIDO - Existe Integrações cadastradas para esse Sistema!"); 
    }

}
