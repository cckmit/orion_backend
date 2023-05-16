package br.com.live.custom;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaGestaoAtivos;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class GestaoAtivosCustom {
	
	private JdbcTemplate jdbcTemplate;

	public GestaoAtivosCustom(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ConsultaGestaoAtivos> findAllServidores(){
		
		String query = " SELECT a.id id, "
				+ "       a.nome_servidor nomeServidor, "
				+ "       a.maquina_fisica maquinaFisica, "
				+ "       a.sistema_operacional sistemaOperacional, "
				+ "       a.ip ip, "
				+ "       a.hd hd, "
				+ "       a.memoria memoria, "
				+ "       a.processador processador, "
				+ "       a.aplicacoes aplicacoes, "
				+ "       a.documentacao documentacao, "
				+ "       a.gestor_responsavel || ' - ' || c.nome gestorResponsavel, "
				+ "       a.gestor_responsavel idGestorResponsavel, "
				+ "       a.status status, "
				+ "       NVL((SELECT COUNT(*) FROM orion_ti_020 b WHERE b.tipo = 1 AND b.id_ativo = a.id), 0) numOportunidades "
				+ "		FROM orion_ti_001 a, orion_001 c "
				+ "		WHERE c.id = a.gestor_responsavel "
				+ "		AND a.id > 0"; // id 0 = todos
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaGestaoAtivos.class));
	}
	
	public List<ConsultaGestaoAtivos> findAllSistemas(){
		
		String query = " SELECT a.id id, "
				+ "       a.nome_sistema nomeSistema, "
				+ "       a.objetivo objetivo, "
				+ "       a.banco_de_dados bancoDeDados, "
				+ "       a.tipo tipo, "
				+ "       a.fornecedor fornecedor, "
				+ "       a.cnpj cnpj, "
				+ "       a.endereco endereco, "
				+ "       a.forma_pagto formaPagto, "
				+ "       a.tem_contrato temContrato, "
				+ "       a.ambiente ambiente, "
				+ "       a.gestor_responsavel || ' - ' || c.nome gestorResponsavel, "
				+ "       a.gestor_responsavel idGestorResponsavel, "
				+ "       a.usuarios_ativos usuariosAtivos, "
				+ "       a.capacidade_usuarios capacidadeUsuarios, "
				+ "       a.status status, "
				+ "       NVL((SELECT COUNT(*) FROM orion_ti_020 b WHERE b.tipo = 2 AND b.id_ativo = a.id), 0) numOportunidades "
				+ "		FROM orion_ti_005 a, orion_001 c"
				+ "     WHERE c.id = a.gestor_responsavel "
				+ "		AND a.id > 0 " // id 0 = todos
				+ "		ORDER BY a.nome_sistema ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaGestaoAtivos.class));
	}
	
	public List<ConsultaGestaoAtivos> findAllIntegracoes(){
		
		String query = " SELECT a.id id, "
				+ "       a.nome_integracao nomeIntegracao, "
				+ "       a.objetivo objetivo, "
				+ "       a.tipo_integracao tipoIntegracao, "
				+ "       a.tipo_conexao tipoConexao, "
				+ "       a.sistema_origem sistemaOrigem, "
				+ "       a.sistema_destino sistemaDestino, "
				+ "       a.servidor servidor, "
				+ "       a.fornecedor fornecedor, "
				+ "       a.cnpj cnpj, "
				+ "       a.endereco endereco, "
				+ "       a.status status, "
				+ "       NVL((SELECT COUNT(*) FROM orion_ti_020 b WHERE b.tipo = 3 AND b.id_ativo = a.id), 0) numOportunidades, "
				+ "       a.gestor_responsavel || ' - ' || c.nome gestorResponsavel, "
				+ "       a.gestor_responsavel idGestorResponsavel "
				+ "		FROM orion_ti_010 a, orion_001 c "
				+ "     WHERE c.id = a.gestor_responsavel "
				+ "		ORDER BY a.nome_integracao";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaGestaoAtivos.class));
	}
	
	public List<ConsultaGestaoAtivos> findAllServicos(){
		
		String query = " SELECT a.id id, "
				+ "       a.nome_servico nomeServico, "
				+ "       a.objetivo objetivo, "
				+ "       a.time_responsavel timeResponsavel, "
				+ "       a.disponibilidade disponibilidade, "
				+ "       a.tecnicos_fornecedores tecnicosFornecedores, "
				+ "       NVL((SELECT COUNT(*) FROM orion_ti_020 b WHERE b.tipo = 4 AND b.id_ativo = a.id), 0) numOportunidades, "
				+ "       a.gestor_responsavel || ' - ' || c.nome gestorResponsavel, "
				+ "       a.gestor_responsavel idGestorResponsavel "
				+ "		FROM orion_ti_015 a, orion_001 c "
				+ "     WHERE c.id = a.gestor_responsavel "
				+ "		ORDER BY a.nome_servico ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaGestaoAtivos.class));
	}
	
	public List<ConsultaGestaoAtivos> findAllOportunidades(){
		
		String query = " SELECT a.id idOp, "
				+ "       DECODE(a.tipo, 1, 'Servidores', "
				+ "       DECODE(a.tipo, 2, 'Sistemas', "
				+ "       DECODE(a.tipo, 3, 'Integrações', "
				+ "       DECODE(a.tipo, 4, 'Serviços')))) tipo, "
				+ "       a.data_cadastro dataCadastro, "
				+ "       a.prioridade prioridade, "
				+ "       a.descricao descricao, "
				+ "       a.objetivo objetivo, "
				+ "       a.contextualizacao contextualizacao, "
				+ "       a.descricao_problema descricaoProblema, "
				+ "       a.perguntas_em_aberto perguntasEmAberto, "
				+ "       a.riscos riscos "
				+ "      FROM orion_ti_020 a ";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaGestaoAtivos.class));
	}
	
	public List<ConteudoChaveAlfaNum> findUser(String usuario) {
		
		String query = " SELECT a.id value, "
				+ "		a.id || ' - ' || a.nome label "
				+ "		FROM orion_001 a WHERE a.id || a.nome LIKE '%" + usuario + "%'";
		
		return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveAlfaNum.class));
		
	}
	
	public String findColunaConsulta(String tipo, String id) {
		String resposta = "";

		String query = " SELECT a." + tipo + " FROM orion_ti_020 a WHERE a.id = '" + id + "'";
		
		try {
			resposta = jdbcTemplate.queryForObject(query, String.class);
		} catch (Exception e) {
			resposta = "";
		}
		
		return resposta;
	}
	
	public boolean deleteSistemaById(int id) {
		
		boolean response = true;
		
		String query = " DELETE FROM orion_ti_005 where id = ? ";
		
		try {
			jdbcTemplate.update(query, id);
		} catch (Exception e) {
			response = false;
		};
		return response;	
	}
	
	public boolean deleteServidorById(int id) {
		
		boolean response = true;
		
		String query = " DELETE FROM orion_ti_001 where id = ? ";
		
		try {
			jdbcTemplate.update(query, id);
		} catch (Exception e) {
			response = false;
		};
		return response;	
	}
	
	public int findNextIdByTipo(int id, int tipo) {
		
		int proximo = 0;
		
		String query = " SELECT NVL((MAX(a.sequencia)),0) +1 "
				+ "       FROM orion_ti_020 a "
				+ "       WHERE a.tipo = " + tipo
				+ "       AND a.id_ativo = " + id;

		try {
			proximo = jdbcTemplate.queryForObject(query, Integer.class);
		} catch (Exception e) {
			proximo = 0;
		}
		
		return proximo;
	}

}
