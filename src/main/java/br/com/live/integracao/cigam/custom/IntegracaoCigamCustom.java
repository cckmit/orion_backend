package br.com.live.integracao.cigam.custom;

import br.com.live.integracao.cigam.model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;

import static br.com.live.util.FormataData.formatarData;

@Repository
public class IntegracaoCigamCustom {
    private JdbcTemplate jdbcTemplate;
    public IntegracaoCigamCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ConsultaNotasNaoEnviadas> findNotasParaEnviar() {
        String query = " select a.num_nota_fiscal notaFiscal, a.serie_nota_fiscal serieFiscal, a.cnpj9, a.cnpj4, a.cnpj2, " +
                " a.numero_danfe_nfe numeroDanfe, a.arquivo_xml arquivoXml from systextil.obrf_160 a, systextil.fatu_050 b, systextil.pedi_010 c " +
                " where c.cgc_9 = a.cnpj9 " +
                " and c.cgc_4 = a.cnpj4 " +
                " and c.cgc_2 = a.cnpj2 " +
                " and c.cliente_integracao = 'CIGAM' " +
                " and b.num_nota_fiscal = a.num_nota_fiscal " +
                " and b.serie_nota_fisc = a.serie_nota_fiscal " +
                " and b.data_emissao >= trunc(sysdate - 4) " +
                " and b.codigo_empresa in (1,100) " +
                " and not exists (select 1 from cigam.notas_enviadas w " +
                "                  where w.nota_fiscal = a.num_nota_fiscal " +
                "                  and w.serie_nota = a.serie_nota_fiscal) ";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaNotasNaoEnviadas.class));
    }

    public void inserirNotasEnviadas(int notaFiscal, int serieFiscal, int cnpj9, int cnpj4, int cnpj2) {
        try {
            String queryInsert = "insert into cigam.notas_enviadas (nota_fiscal, serie_nota, cnpj9, cnpj4, cnpj2, data_hora_envio) values (?, ?, ?, ?, ?, sysdate)";
            jdbcTemplate.update(queryInsert, notaFiscal, serieFiscal, cnpj9, cnpj4, cnpj2);
        } catch (Exception e) {
            //System.out.println("Nota " + notaFiscal + " Já Inserida!");
        }
    }

    public void inserirDadosRetornoMovimentos(ListaRetornoApiMovimentos dadosRetorno) throws ParseException {
        try {
            String query = " INSERT INTO CIGAM.INTEG_MOVIMENTOS(identificador, portal, loja, cnpj_emp, numorcamento, usuario, numnf, serie, chave_nf, protocolo_aut_nfe, nrecf, " +
                    " modelo_nf, data_lancamento, hora_lancamento, codigo_cliente, doc_cliente, id_cfop, cod_vendedor, cod_barra, qtde, preco_unitario, " +
                    " valor_liquido, desconto, preco_custo, cst_icms, cst_pis, cst_cofins, cst_ipi, valor_icms, aliquota_icms, base_icms, valor_pis, " +
                    " aliquota_pis, base_pis, valor_cofins, aliquota_cofins, base_cofins, valor_icms_st, aliquota_icms_st, base_icms_st, valor_ipi, " +
                    " aliquota_ipi, base_ipi, operacao, tipo_transacao, cancelado, datcancel, excluido, seqitem, cod_sefaz_situacao) VALUES " +
                    " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            jdbcTemplate.update(query, dadosRetorno.identificador, dadosRetorno.portal, dadosRetorno.loja, dadosRetorno.cnpj_emp, dadosRetorno.numorcamento,
                    dadosRetorno.usuario, dadosRetorno.numnf, dadosRetorno.serie, dadosRetorno.chave_nf, dadosRetorno.protocolo_aut_nfe,
                    dadosRetorno.nrecf, dadosRetorno.modelo_nf, formatarData(dadosRetorno.data_lancamento), dadosRetorno.hora_lancamento,
                    dadosRetorno.codigo_cliente, dadosRetorno.doc_cliente, dadosRetorno.id_cfop, dadosRetorno.cod_vendedor, dadosRetorno.cod_barra,
                    dadosRetorno.qtde, dadosRetorno.preco_unitario, dadosRetorno.valor_liquido, dadosRetorno.desconto, dadosRetorno.preco_custo,
                    dadosRetorno.cst_icms, dadosRetorno.cst_pis, dadosRetorno.cst_cofins, dadosRetorno.cst_ipi, dadosRetorno.valor_icms,
                    dadosRetorno.aliquota_icms, dadosRetorno.base_icms, dadosRetorno.valor_pis, dadosRetorno.aliquota_pis, dadosRetorno.base_pis,
                    dadosRetorno.valor_cofins, dadosRetorno.aliquota_cofins, dadosRetorno.base_cofins, dadosRetorno.valor_icms_st,
                    dadosRetorno.aliquota_icms_st, dadosRetorno.base_icms_st, dadosRetorno.valor_ipi, dadosRetorno.aliquota_ipi, dadosRetorno.base_ipi,
                    dadosRetorno.operacao, dadosRetorno.tipo_transacao, dadosRetorno.cancelado, formatarData(dadosRetorno.datcancel),
                    dadosRetorno.excluido, dadosRetorno.seqitem, dadosRetorno.cod_sefaz_situacao);
        } catch (Exception e) {
            String updateQuery = "UPDATE CIGAM.INTEG_MOVIMENTOS SET portal = ?, loja = ?, cnpj_emp = ?, numorcamento = ?, usuario = ?, " +
                    "numnf = ?, serie = ?, chave_nf = ?, protocolo_aut_nfe = ?, nrecf = ?, modelo_nf = ?, data_lancamento = ?, " +
                    "hora_lancamento = ?, codigo_cliente = ?, doc_cliente = ?, id_cfop = ?, cod_vendedor = ?, cod_barra = ?, " +
                    "qtde = ?, preco_unitario = ?, valor_liquido = ?, desconto = ?, preco_custo = ?, cst_icms = ?, cst_pis = ?, " +
                    "cst_cofins = ?, cst_ipi = ?, valor_icms = ?, aliquota_icms = ?, base_icms = ?, valor_pis = ?, aliquota_pis = ?, " +
                    "base_pis = ?, valor_cofins = ?, aliquota_cofins = ?, base_cofins = ?, valor_icms_st = ?, aliquota_icms_st = ?, " +
                    "base_icms_st = ?, valor_ipi = ?, aliquota_ipi = ?, base_ipi = ?, operacao = ?, tipo_transacao = ?, " +
                    "cancelado = ?, datcancel = ?, excluido = ?, seqitem = ?, cod_sefaz_situacao = ? " +
                    "WHERE identificador = ?";

            jdbcTemplate.update(updateQuery, dadosRetorno.portal, dadosRetorno.loja, dadosRetorno.cnpj_emp, dadosRetorno.numorcamento,
                    dadosRetorno.usuario, dadosRetorno.numnf, dadosRetorno.serie, dadosRetorno.chave_nf, dadosRetorno.protocolo_aut_nfe,
                    dadosRetorno.nrecf, dadosRetorno.modelo_nf, formatarData(dadosRetorno.data_lancamento), dadosRetorno.hora_lancamento,
                    dadosRetorno.codigo_cliente, dadosRetorno.doc_cliente, dadosRetorno.id_cfop, dadosRetorno.cod_vendedor, dadosRetorno.cod_barra,
                    dadosRetorno.qtde, dadosRetorno.preco_unitario, dadosRetorno.valor_liquido, dadosRetorno.desconto, dadosRetorno.preco_custo,
                    dadosRetorno.cst_icms, dadosRetorno.cst_pis, dadosRetorno.cst_cofins, dadosRetorno.cst_ipi, dadosRetorno.valor_icms,
                    dadosRetorno.aliquota_icms, dadosRetorno.base_icms, dadosRetorno.valor_pis, dadosRetorno.aliquota_pis, dadosRetorno.base_pis,
                    dadosRetorno.valor_cofins, dadosRetorno.aliquota_cofins, dadosRetorno.base_cofins, dadosRetorno.valor_icms_st,
                    dadosRetorno.aliquota_icms_st, dadosRetorno.base_icms_st, dadosRetorno.valor_ipi, dadosRetorno.aliquota_ipi, dadosRetorno.base_ipi,
                    dadosRetorno.operacao, dadosRetorno.tipo_transacao, dadosRetorno.cancelado, formatarData(dadosRetorno.datcancel),
                    dadosRetorno.excluido, dadosRetorno.seqitem, dadosRetorno.cod_sefaz_situacao, dadosRetorno.identificador);
        }
   }

   public void inserirDadosDuplicatas(ListaRetornoApiDuplicatas dadosRetorno) {
        try {
            String query = " INSERT INTO CIGAM.INTEG_DUPLICATAS (portal, loja, cnpj_emp, cod_cliente, numorcamento, codpgto, " +
                    " forma_pgto, qtde_parcelas, ordem_parcela, data_emissao, data_vencimento, data_baixa, valor_fatura, valor_pago, valor_desconto, " +
                    " valor_juros, documento, nsu, cod_autorizacao, identificador) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
            jdbcTemplate.update(query, dadosRetorno.portal, dadosRetorno.loja, dadosRetorno.cnpj_emp, dadosRetorno.cod_cliente,
                    dadosRetorno.numorcamento, dadosRetorno.codpgto, dadosRetorno.forma_pgto, dadosRetorno.qtde_parcelas,
                    dadosRetorno.ordem_parcela, formatarData(dadosRetorno.data_emissao), formatarData(dadosRetorno.data_vencimento),
                    formatarData(dadosRetorno.data_baixa), dadosRetorno.valor_fatura, dadosRetorno.valor_pago,
                    dadosRetorno.valor_desconto, dadosRetorno.valor_juros, dadosRetorno.documento, dadosRetorno.nsu,
                    dadosRetorno.cod_autorizacao, dadosRetorno.identificador);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
   }

   public void inserirDadosClienteFornecedor(ListaRetornoApiClienteFornecedor dadosRetorno) {
        try {
            String query = " INSERT INTO CIGAM.INTEG_CLIFOR (portal, cod_cliente, razao_cliente, nome_cliente, doc_cliente, tipo_cliente, endereco_cliente," +
                    " numero_rua_cliente, complement_end_cli, bairro_cliente, cep_cliente, cidade_cliente, uf_cliente, pais, fone_cliente, email_cliente, " +
                    " sexo, data_cadastro, data_nascimento, cel_cliente, ativo, dt_update, inscricao_estadual, incricao_municipal, identidade_cliente, cartao_fidelidade, " +
                    " cod_ibge_municipio, classe_cliente, matricula_conveniado, tipo_cadastro, id_estado_civil, fax_cliente, site_cliente) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            jdbcTemplate.update(query, dadosRetorno.portal, dadosRetorno.cod_cliente, dadosRetorno.razao_cliente, dadosRetorno.nome_cliente, dadosRetorno.doc_cliente,
                    dadosRetorno.tipo_cliente, dadosRetorno.endereco_cliente, dadosRetorno.numero_rua_cliente, dadosRetorno.complement_end_cli,
                    dadosRetorno.bairro_cliente, dadosRetorno.cep_cliente, dadosRetorno.cidade_cliente, dadosRetorno.uf_cliente, dadosRetorno.pais,
                    dadosRetorno.fone_cliente, dadosRetorno.email_cliente, dadosRetorno.sexo, formatarData(dadosRetorno.data_cadastro),
                    formatarData(dadosRetorno.data_nascimento), dadosRetorno.cel_cliente, dadosRetorno.ativo, formatarData(dadosRetorno.dt_update),
                    dadosRetorno.inscricao_estadual, dadosRetorno.incricao_municipal, dadosRetorno.identidade_cliente, dadosRetorno.cartao_fidelidade,
                    dadosRetorno.cod_ibge_municipio, dadosRetorno.classe_cliente, dadosRetorno.matricula_conveniado, dadosRetorno.tipo_cadastro,
                    dadosRetorno.id_estado_civil, dadosRetorno.fax_cliente, dadosRetorno.site_cliente);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
   }

   public void inserirDadosFuncionarios(ListaRetornoApiFuncionarios dadosRetorno) {
        try {
            String query = " INSERT INTO cigam.integ_funcionarios (cod_vendedor, cpf_vendedor, nome_vendedor, cargo, end_usuario, loja, cnpj, status, data_demissao, data_admissao) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            jdbcTemplate.update(query, dadosRetorno.cod_vendedor, dadosRetorno.cpf_vendedor, dadosRetorno.nom_vendedor, dadosRetorno.cargo,
                    dadosRetorno.end_usuario, dadosRetorno.loja, dadosRetorno.cnpj, dadosRetorno.status,
                    formatarData(dadosRetorno.datademissao), formatarData(dadosRetorno.dataadmissao));
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
   }

   public void inserirDadosEstoque(ListaRetornoApiEstoque dadosRetorno) {
        try {
            String query = " INSERT INTO cigam.integ_estoque (loja, cnpj, cod_barras, ean, qtd_estoque, data_estoque) " +
                    " VALUES (?,?,?,?,?,trunc(sysdate-1)) ";
            jdbcTemplate.update(query, dadosRetorno.loja, dadosRetorno.cnpj, dadosRetorno.codbarra, dadosRetorno.ean, dadosRetorno.qtdestoque);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
   }
}
