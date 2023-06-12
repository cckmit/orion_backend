package br.com.live.custom;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaReferenciasIntegradas;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class IntegracaoFluxogamaCustom {

    public static final int SUCESSO = 0;
    public static final int ERRO = 1;

    private final JdbcTemplate jdbcTemplate;

    public IntegracaoFluxogamaCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void atualizarInformacoesCapaReferencia(String referencia, String descReferencia, String colecao, String unMedida, String linha, String artigo,
                                                   String artigoCotas, String classFiscal, String compradoFabric, String responsavel, String serie, String codContabil) {
        try{
            String observacao = referencia + " atualizada com sucesso! Parametros: ";

            String query = " update basi_030" +
                    " set estagio_altera_programado = 6, ";

            if (descReferencia != null) {
                query += " descr_referencia = '" + descReferencia.toUpperCase() + "' ";
                observacao += "descReferencia: " + descReferencia.toUpperCase() + " / ";
            }
            if (colecao != null) {
                query += " ,colecao = " + Integer.parseInt(colecao);
                observacao += "colecao: " + Integer.parseInt(colecao) + " / ";
            }
            if (unMedida != null) {
                query += " ,unidade_medida = '" + unMedida.toUpperCase() + "' ";
                observacao += "unidadeMedida: " + unMedida.toUpperCase() + " / ";
            }
            if (linha != null) {
                query += " ,linha_produto = " + Integer.parseInt(linha);
                observacao += "linhaProduto: " + Integer.parseInt(linha) + " / ";
            }
            if (artigo != null) {
                query += " ,artigo = " + Integer.parseInt(artigo);
                observacao += "artigoProduto: " + Integer.parseInt(artigo) + " / ";
            }
            if (artigoCotas != null) {
                query += " ,artigo_cotas = " + Integer.parseInt(artigoCotas);
                observacao += "artigoCotas: " + Integer.parseInt(artigoCotas) + " / ";
            }
            if (classFiscal != null) {
                query += " ,classific_fiscal = '" + classFiscal + "' ";
                observacao += "classificaoFiscal: " + classFiscal + " / ";
            }
            if (compradoFabric != null) {
                query += " ,comprado_fabric = " + Integer.parseInt(compradoFabric);
                observacao += "compradoFabricado: " + Integer.parseInt(compradoFabric) + " / ";
            }
            if (responsavel != null) {
                query += " ,responsavel = '" + responsavel + "' ";
                observacao += "responsavel: " + responsavel + " / ";
            }
            if (serie != null) {
                query += " ,serie_tamanho = " + Integer.parseInt(serie);
                observacao += "serieTamanho: " + Integer.parseInt(serie) + " / ";
            }
            if (codContabil != null) {
                query += " ,codigo_contabil = " + Integer.parseInt(codContabil);
                observacao += " codContabil: " + Integer.parseInt(codContabil) + " / ";
            }
            query += " where basi_030.nivel_estrutura = '1' " +
                    " and basi_030.referencia = '" + referencia + "' ";

            jdbcTemplate.update(query);
            gravarModeloIntegrado(referencia, observacao, SUCESSO);
        } catch (Exception e) {
            gravarModeloIntegrado(referencia, "Ocorreu um erro ao atualizar as informações da referência " + referencia + " / ERRO " + e.getMessage(), ERRO);
        }
    }

    public void inserirTamanhosReferencia(String referencia, String tamanho) {
        try {
            String query = " INSERT INTO basi_020 (BASI030_NIVEL030, BASI030_REFERENC, TAMANHO_REF, DESCR_TAM_REFER, ARTIGO_COTAS) " +
                    " VALUES ('1', ?, ?, (SELECT a.descr_tamanho FROM basi_220 a WHERE a.tamanho_ref = '" + tamanho + "'), " +
                    " (SELECT w.artigo_cotas FROM BASI_030 W WHERE W.NIVEL_ESTRUTURA = '1' AND W.REFERENCIA = '" + referencia + "')) ";
            jdbcTemplate.update(query, referencia, tamanho);
            gravarModeloIntegrado(referencia, "Tamanho " + tamanho + " inserido na referência " + referencia, SUCESSO);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void excluirTamanhosDivergentes(String referencia, String tamanho) {
        try {
            String queryDelete = " DELETE FROM BASI_030 a " +
                    " WHERE a.BASI030_NIVEL030 = '1' " +
                    " AND a.BASI030_REFERENC = '" + referencia + "' " +
                    " AND a.TAMANHO_REF = '" + tamanho + "' ";
            jdbcTemplate.update(queryDelete);
        } catch (Exception e) {
            gravarModeloIntegrado(referencia, "Não foi possível excluir o tamanho " + tamanho + " da referência " + referencia, ERRO);
        }
    }

    public void atualizarCategoriasReferencia(String referencia, int codAtributo, String categoria) {
        String chaveAcesso = "1" + referencia + "BASI_030";

        try {
            String query = " insert into basi_544 values ('000001',?,'BASI_030', 1, 1,?,?) ";
            jdbcTemplate.update(query, chaveAcesso, codAtributo, categoria);
        } catch (Exception e) {
            String queryUpdate = " update basi_544 a " +
                                "   set a.conteudo_atributo = '" + categoria + "' " +
                                "   where a.chave_acesso = '" + chaveAcesso + "' " +
                                "         and a.codigo_atributo = " + codAtributo;
            jdbcTemplate.update(queryUpdate);
        }
        gravarModeloIntegrado(referencia, "Atualizado a categoria " + codAtributo + " da referência " + referencia + " / Valor: " + categoria, SUCESSO);
    }

    public void atualizarCoresReferencia(String referencia, String tamanho, String cor, String classFiscal, String artigoCotas, String codContabil) {
        try {
            String queryInsert = " insert into basi_010 (nivel_estrutura, " +
                    " grupo_estrutura, " +
                    " subgru_estrutura, " +
                    " item_estrutura, " +
                    " descricao_15 ";
            if (classFiscal != null) {
                queryInsert += " ,classific_fiscal";
            }
            if (artigoCotas != null) {
                queryInsert += " ,artigo_cotas";
            }
            if (codContabil != null) {
                queryInsert += " ,codigo_contabil";
            }

            queryInsert += ") values ('1', '" + referencia.toUpperCase() + "', '" + tamanho.toUpperCase() + "', '" + cor.toUpperCase() +
                    "',(select max(g.descricao) from basi_100 g where g.cor_sortimento = '" + cor.toUpperCase() + "' )";
            if (classFiscal != null) {
                queryInsert += " ,'" + classFiscal + "' ";
            }
            if (artigoCotas != null) {
                queryInsert += " ," + Integer.parseInt(artigoCotas);
            }
            if (codContabil != null) {
                queryInsert += " ," + Integer.parseInt(codContabil);
            }
            queryInsert += ")";

            jdbcTemplate.update(queryInsert);
        } catch (Exception e) {
            String queryUpdate = " update basi_010 " +
                    "set descricao_15 = (select max(g.descricao) from basi_100 g where g.cor_sortimento = '" + cor.toUpperCase() + "' ) ";

            if (classFiscal != null) {
                queryUpdate += ",classific_fiscal = '" + classFiscal + "' ";
            }
            if (artigoCotas != null) {
                queryUpdate += ",artigo_cotas = " + Integer.parseInt(artigoCotas);
            }
            if (codContabil != null) {
                queryUpdate += ",codigo_contabil = " + Integer.parseInt(codContabil);
            }

            queryUpdate += "where basi_010.nivel_estrutura = '1'" +
                    "and basi_010.grupo_estrutura = '" + referencia.toUpperCase() + "' " +
                    "and basi_010.subgru_estrutura = '" + tamanho.toUpperCase() + "' " +
                    "and basi_010.item_estrutura = '" + cor.toUpperCase() + "' ";
            jdbcTemplate.update(queryUpdate);
        }
        gravarModeloIntegrado(referencia, "Atualizada a cor " + cor.toUpperCase() + " da referência " + referencia.toUpperCase() + " tamanho " + tamanho.toUpperCase(), SUCESSO);
    }

    public void excluirCoresDivergentes(String referencia, String tamanho, String cores) {
        try {
            String queryDelete = " delete from basi_010 a " +
                    " where a.nivel_estrutura = '1' " +
                    " and a.grupo_estrutura = '" + referencia.toUpperCase() + "' " +
                    " and a.subgru_estrutura = '" + tamanho.toUpperCase() + "' " +
                    " and a.item_estrutura not in (" + cores.toUpperCase() + ") ";
            jdbcTemplate.update(queryDelete);
        } catch (Exception e) {
            gravarModeloIntegrado(referencia, "Não foi possível excluir as cores divergentes da referência " + referencia.toUpperCase() + " tamanho " + tamanho.toUpperCase(), ERRO);
        }
    }

    public void gravarModeloIntegrado(String modelo, String observacao, int status) {
        try {
            String query = " INSERT INTO ORION_INTEG_FLUXOGAMA_001 (ID, REFERENCIA, DATA_HORA_INTEG, OBSERVACAO, STATUS) VALUES (?, ?, sysdate, ?, ?) ";
            jdbcTemplate.update(query, obterProximoIdIntegracao(), modelo, observacao, status);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public LocalDateTime obterUltimaExecucao() {
        LocalDateTime dataHoraUltExecucao = null;

        String query = " SELECT max(a.data_hora_integ) FROM ORION_INTEG_FLUXOGAMA_001 a " +
                " WHERE a.status = 0 ";
        try {
            dataHoraUltExecucao = jdbcTemplate.queryForObject(query, LocalDateTime.class);
        } catch (Exception e) {
            dataHoraUltExecucao = null;
        }
        return  dataHoraUltExecucao;
    }

    public int obterProximoIdIntegracao() {
        int proxIdIntegracao = 0;

        String query = " select nvl(max(m.id), 0) + 1 from orion_integ_fluxogama_001 m ";
        try {
            proxIdIntegracao = jdbcTemplate.queryForObject(query, Integer.class);
        } catch (Exception e) {
            proxIdIntegracao = 0;
        }
        return proxIdIntegracao;
    }

    public List<String> obterListaTamanhosPorGrade(int gradeTamanho) {
        String query = " SELECT basi_215.tamanho FROM basi_215 " +
                " WHERE basi_215.serie_tamanho = " + gradeTamanho;
        return jdbcTemplate.queryForList(query, String.class);
    }

    public List<String> obterListaTamanhosDivergentes(String referencia, String gradeTamanho) {
        String query = " select a.tamanho_ref from basi_020 a " +
                " where a.basi030_nivel030 = '1' " +
                " and a.basi030_referenc = '" + referencia + "' " +
                " and not exists (select 1 from basi_215 b " +
                "                       where b.tamanho = a.tamanho_ref " +
                "                       and b.serie_tamanho = " + Integer.parseInt(gradeTamanho) + ") ";
        return jdbcTemplate.queryForList(query, String.class);
    }

    public int obterCodigoPilarSystextil(String descricao) {
        String query = " select b.codigo from hdoc_001 b " +
                " where b.tipo = 9 " +
                " and b.descricao = '" + descricao.toUpperCase() + "' ";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    public void atualizarCamposBasi400(String referencia, int tipoInformacao, int codigoInformacao) {
        try {
            String query = " insert into basi_400 (nivel, grupo, subgrupo, item, tipo_informacao, codigo_informacao) values ('1', ?, '000', '000000', ?, ?) ";
            jdbcTemplate.update(query, referencia.toUpperCase(), tipoInformacao, codigoInformacao);
        } catch (Exception e) {
            String queryBasi400 = " delete from basi_400 w " +
                    " where w.nivel = '1' " +
                    " and w.grupo = ? " +
                    " and w.subgrupo = '000' " +
                    " and w.item = '000000' " +
                    " and w.tipo_informacao = ? ";
            jdbcTemplate.update(queryBasi400, referencia.toUpperCase(), tipoInformacao);

            String query = " insert into basi_400 (nivel, grupo, subgrupo, item, tipo_informacao, codigo_informacao) values ('1', ?, '000', '000000', ?, ?) ";
            jdbcTemplate.update(query, referencia.toUpperCase(), tipoInformacao, codigoInformacao);
        }
        gravarModeloIntegrado(referencia, "Atualizado a tabela basi_400 para a referência " + referencia + " / Valor: " + codigoInformacao, SUCESSO);
    }
    
    public List<ConsultaReferenciasIntegradas> findRefsIntegradas(String dataInicio, String dataFim, String referencia) {
    	String query = " select a.id, a.referencia, a.data_hora_integ data, a.status, a.observacao from orion_integ_fluxogama_001 a "
    			+ " where trunc(a.data_hora_integ) between to_date('" + dataInicio + "', 'dd-mm-yyyy') and to_date('" + dataFim + "', 'dd-mm-yyyy') ";
    	
    	if (referencia != null && !referencia.equalsIgnoreCase("")) {
    		query += " and a.referencia = '" + referencia.toUpperCase() + "' ";
    	}
    	query += " order by a.data_hora_integ desc ";
    	return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaReferenciasIntegradas.class));
    }
}
