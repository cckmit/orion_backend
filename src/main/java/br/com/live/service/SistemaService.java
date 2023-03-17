package br.com.live.service;

import br.com.live.entity.Sistema;
import br.com.live.repository.SistemaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SistemaService {

    private SistemaRepository sistemaRepository;

    public SistemaService(SistemaRepository sistemaRepository) {
        this.sistemaRepository = sistemaRepository;
    }

    public void saveSistema(int id, String nomeSistema, String objetivo, String bancoDeDados, String tipo, String fornecedor, String cnpj, String endereco, String formaPagto, boolean temContrato, byte[] contrato, String ambiente, int usuariosAtivos, int capacidadeUsuarios) {

        Sistema sistema = null;

        sistema = sistemaRepository.findById(id);

        if (sistema == null) {
            sistema = new Sistema(sistemaRepository.findNextId(), nomeSistema, objetivo, bancoDeDados, tipo, fornecedor, cnpj, endereco, formaPagto, temContrato, contrato, ambiente, usuariosAtivos, capacidadeUsuarios);
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
            sistema.usuariosAtivos = usuariosAtivos;
            sistema.capacidadeUsuarios = capacidadeUsuarios;
        }
        sistemaRepository.save(sistema);
    }

    public void deleteById(int id) {
        sistemaRepository.deleteById(id);
    }
}
