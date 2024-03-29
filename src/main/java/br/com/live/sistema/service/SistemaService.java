package br.com.live.sistema.service;

import br.com.live.sistema.custom.GestaoAtivosCustom;
import br.com.live.sistema.entity.Sistema;
import br.com.live.sistema.model.ConsultaGestaoAtivos;
import br.com.live.sistema.repository.SistemaRepository;
import br.com.live.util.ErrorMessageException;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SistemaService {

    private SistemaRepository sistemaRepository;
    private GestaoAtivosCustom gestaoAtivosCustom;

    public SistemaService(SistemaRepository sistemaRepository, GestaoAtivosCustom gestaoAtivosCustom) {
        this.sistemaRepository = sistemaRepository;
        this.gestaoAtivosCustom = gestaoAtivosCustom;
    }
    
    public List<ConsultaGestaoAtivos> findAllSistemas(){
    	return gestaoAtivosCustom.findAllSistemas();
    }

    public void saveSistema(int id, String nomeSistema, String objetivo, String bancoDeDados, String tipo, String fornecedor, String cnpj, String endereco, String formaPagto, 
    		boolean temContrato, byte[] contrato, String ambiente, String status, int usuariosAtivos, int capacidadeUsuarios, int gestorResponsavel) {

        Sistema sistema = null;

        if (id > 0) sistema = sistemaRepository.findById(id); // ID = 0, contempla TODOS.

        if (sistema == null) {
            sistema = new Sistema(sistemaRepository.findNextId(), nomeSistema, objetivo, bancoDeDados, tipo, fornecedor, cnpj, endereco, formaPagto, temContrato, 
            		contrato, ambiente, status, usuariosAtivos, capacidadeUsuarios, gestorResponsavel);
        } else {
            sistema.nomeSistema = nomeSistema;
            sistema.objetivo = objetivo;
            sistema.bancoDeDados = bancoDeDados;
            sistema.tipo = tipo;
            sistema.fornecedor = fornecedor;
            sistema.cnpj = cnpj;
            sistema.endereco = endereco;
            sistema.formaPagto = formaPagto;
            sistema.temContrato = temContrato;
            sistema.contrato = contrato;
            sistema.ambiente = ambiente;
            sistema.status = status;
            sistema.usuariosAtivos = usuariosAtivos;
            sistema.capacidadeUsuarios = capacidadeUsuarios;
            sistema.gestorResponsavel = gestorResponsavel;
        }
        sistemaRepository.save(sistema);
    }
    
    public void deleteSistemaById(int id) {
    	
    	if(!gestaoAtivosCustom.deleteSistemaById(id))
    		throw new ErrorMessageException("NÃO PERMITIDO - Existe Integrações cadastradas para esse Sistema!");   
    }
}
