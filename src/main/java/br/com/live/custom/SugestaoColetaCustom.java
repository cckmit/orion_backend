package br.com.live.custom;

import java.util.ArrayList;
import java.util.List;

import br.com.live.entity.LoteSugestaoColetaPorAreaItem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.live.model.ConsultaSugestaoColetaPorLoteArea;
import br.com.live.model.ItemAColetarPorPedido;
import br.com.live.model.SugestaoColeta;
import br.com.live.util.ConteudoChaveAlfaNum;
import br.com.live.util.ConteudoChaveNumerica;

@Repository
public class SugestaoColetaCustom {

    private JdbcTemplate jdbcTemplate;

    public SugestaoColetaCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SugestaoColeta> findPedidosSugestaoColeta(String dataEmissaoInicio, String dataEmissaoFim, String dataEmbarqueInicio, String dataEmbarqueFim,
                                                          List<ConteudoChaveNumerica> empresas, List<ConteudoChaveAlfaNum> clientes, List<ConteudoChaveNumerica> representantes,
                                                          List<ConteudoChaveAlfaNum> transportadora) {
        List<SugestaoColeta> dadosColeta = null;

        String query = "select min(a.codigo_empresa) empresa, a.data_emis_venda emissao, a.pedido_venda pedido, min(a.data_entr_venda) embarque, "
                + " min(a.valor_total_pedi) valorSaldo, "
                + " lpad(min(a.cli_ped_cgc_cli9),9,0) || '/' || lpad(min(a.cli_ped_cgc_cli4),4,0) || '-' || lpad(min(a.cli_ped_cgc_cli2),2,0) || ' - ' || min(b.nome_cliente) cliente, "
                + " sum(c.qtde_faturada) qtdeFatu, "
                + " sum(c.qtde_pedida) qtdePedido, "
                + " sum(c.qtde_pedida - c.qtde_faturada) qtdeSaldo, "
                + " sum(c.qtde_afaturar) qtdeColetada, "
                + " sum(c.qtde_pedida - c.qtde_faturada - c.qtde_afaturar) qtdeColetar "
                + " from pedi_100 a, pedi_010 b, pedi_110 c "
                + " where a.situacao_venda <> 10 "
                + " and a.cod_cancelamento = 0 "
                + " and b.cgc_9 = a.cli_ped_cgc_cli9 "
                + " and b.cgc_4 = a.cli_ped_cgc_cli4 "
                + " and b.cgc_2 = a.cli_ped_cgc_cli2 "
                + " and a.data_emis_venda between to_date('" + dataEmissaoInicio + "' , 'dd-MM-yyyy') and to_date('" + dataEmissaoFim + "' , 'dd-MM-yyyy') "
                + " and c.pedido_venda = a.pedido_venda "
                + " and c.cod_cancelamento = 0 "
                // Desconsidera o que já foi sugerido para coleta
                + " and not exists (select 1 from orion_exp_362 y "
                + " where y.pedido_venda = a.pedido_venda ) ";

        if (!dataEmbarqueInicio.equals("NaN-NaN-NaN")) {
            query += " and a.data_entr_venda between to_date('" + dataEmbarqueInicio + "' , 'dd-MM-yyyy') and to_date('" + dataEmbarqueFim + "' , 'dd-MM-yyyy')";
        }
        if (empresas.size() > 0) {
            query += " and a.codigo_empresa in (" + ConteudoChaveNumerica.parseValueToString(empresas) + ") ";
        }
        if (clientes.size() > 0) {
            query += " and a.cli_ped_cgc_cli9 || a.cli_ped_cgc_cli4 || a.cli_ped_cgc_cli2 in (" + ConteudoChaveAlfaNum.parseValueToString(clientes) + ")";
        }
        if (representantes.size() > 0) {
            query += " and a.cod_rep_cliente in (" + ConteudoChaveNumerica.parseValueToString(representantes) + ")";
        }
        if (transportadora.size() > 0) {
            query += " and a.trans_pv_forne9 || a.trans_pv_forne4 || a.trans_pv_forne2 in (" + ConteudoChaveAlfaNum.parseValueToString(transportadora) + ") ";
        }

        query += " group by a.pedido_venda ";

        try {
            dadosColeta = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(SugestaoColeta.class));
        } catch (Exception e) {
            dadosColeta = new ArrayList<SugestaoColeta>();
            System.out.println(e);
        }

        return dadosColeta;
    }

    public List<ItemAColetarPorPedido> findItensParaColetarByPedido(int pedidoVenda) {

        String query = "select p.pedido_venda pedido, p.cd_it_pe_nivel99 nivel, p.cd_it_pe_grupo grupo, p.cd_it_pe_subgrupo sub, p.cd_it_pe_item item, p.codigo_deposito deposito,sum(p.qtde_pedida - p.qtde_faturada - p.qtde_afaturar) qtdeColetar, "
                + " nvl((select min(y.endereco) "
                + " from estq_110 y "
                + " where y.nivel = p.cd_it_pe_nivel99 "
                + " and y.grupo = p.cd_it_pe_grupo "
                + " and y.subgrupo = p.cd_it_pe_subgrupo "
                + " and y.item = p.cd_it_pe_item "
                + " and y.deposito = p.codigo_deposito),'SEM ENDERE') endereco "
                + " from pedi_110 p "
                + " where p.pedido_venda = ? "
                + " and p.cod_cancelamento = 0 "
                + " and (p.qtde_pedida - p.qtde_faturada - p.qtde_afaturar) > 0 "
                + " group by p.pedido_venda, p.cd_it_pe_nivel99, p.cd_it_pe_grupo, p.cd_it_pe_subgrupo, p.cd_it_pe_item, p.codigo_deposito ";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ItemAColetarPorPedido.class), pedidoVenda);
    }

    public List<ConsultaSugestaoColetaPorLoteArea> findSugestaoColetaParaLiberarByIdUsuario(long idUsuario, long idLote) {

        List<ConsultaSugestaoColetaPorLoteArea> listaSugestao = null;

        String query = " select b.id idLoteArea, b.id_lote idLote, b.id_area idArea, nvl(c.descricao, 'SEM AREA') descricaoArea, " +
                "                 (select count(*) " +
                "                      from (select z.pedido_venda " +
                "                      from orion_exp_362 z " +
                "                      where z.id_lote_area = b.id " +
                "                      group by z.pedido_venda)) qtdePedidos, " +
                "                 (select count(*) from ( " +
                "                      select d.pedido_venda pedido, d.endereco from orion_exp_362 d, orion_exp_361 e, orion_exp_350 f " +
                "                      where e.id_lote = " + idLote +
                "                      and d.id_lote_area = e.id " +
                "                      and f.id = b.id_area " +
                "                      and d.endereco between f.endereco_inicial and f.endereco_final " +
                "                      group by d.pedido_venda, d.endereco " +
                "                      order by d.endereco) " +
                "                      ) qtdePedidosArea, " +
                "                 (select sum(z.qtde_coletar) " +
                "                      from orion_exp_362 z " +
                "                      where z.id_lote_area = b.id) qtdePecas, " +
                "                      (select count(*) " +
                "                      from (select z.nivel, z.grupo, z.sub, z.item " +
                "                      from orion_exp_362 z " +
                "                      where z.id_lote_area = b.id " +
                "                      group by z.nivel, z.grupo, z.sub, z.item)) qtdeSkus, " +
                "                 (select count(*) " +
                "                      from (select z.endereco " +
                "                      from orion_exp_362 z " +
                "                      where z.id_lote_area = b.id " +
                "                      group by z.endereco)) qtdeEnderecos " +
                "                  from orion_exp_360 a, orion_exp_361 b, orion_exp_350 c " +
                "                  where a.situacao = 0 " +
                "                  and a.id_usuario = " + idUsuario +
                "                  and a.id = " + idLote +
                "                  and b.id_lote = a.id " +
                "                  and c.id (+) = b.id_area ";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConsultaSugestaoColetaPorLoteArea.class));
    }

    public List<ConteudoChaveNumerica> findAllColetores() {

        String query = "select hdoc_001.codigo value, replace(hdoc_030.observacao, 'COLETOR - ','')  label "
                + " from hdoc_001, hdoc_030 "
                + " where hdoc_001.tipo   = 120 "
                + " and hdoc_030.empresa = 1 "
                + " and hdoc_030.usuario = hdoc_001.descricao "
                + " and hdoc_030.observacao not like '%INATIVO%' "
                + " order by hdoc_001.codigo ";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class));
    }

    public List<ConteudoChaveNumerica> findColetoresByIdAreaLote(long idAreaLote) {

        List<ConteudoChaveNumerica> listIdColetores = null;

        String query = "select hdoc_001.codigo value, replace(hdoc_030.observacao, 'COLETOR - ','')  label "
                + " from hdoc_001, hdoc_030 "
                + " where hdoc_001.tipo   = 120 "
                + " and hdoc_030.empresa = 1 "
                + " and hdoc_030.usuario = hdoc_001.descricao "
                + " and hdoc_030.observacao not like '%INATIVO%' "
                + " and hdoc_001.codigo in (select o.id_coletor from orion_exp_363 o "
                + " where o.id_lote_area = ?) "
                + " order by hdoc_001.codigo ";

        try {
            listIdColetores = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class), idAreaLote);
        } catch (Exception e) {
            listIdColetores = new ArrayList<ConteudoChaveNumerica>();
        }

        return listIdColetores;
    }

    public List<ConteudoChaveNumerica> findColetoresDisponiveiByLote(long idLote) {

        List<ConteudoChaveNumerica> listIdColetores = null;

        String query = "select hdoc_001.codigo value, replace(hdoc_030.observacao, 'COLETOR - ','')  label "
                + " from hdoc_001, hdoc_030 "
                + " where hdoc_001.tipo   = 120 "
                + " and hdoc_030.empresa = 1 "
                + " and hdoc_030.usuario = hdoc_001.descricao "
                + " and hdoc_030.observacao not like '%INATIVO%' "
                + " and hdoc_001.codigo not in (select y.id_coletor from orion_exp_361 z, orion_exp_363 y "
                + " where z.id_lote = ? "
                + " and y.id_lote_area = z.id) "
                + " order by hdoc_001.codigo ";

        try {
            listIdColetores = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ConteudoChaveNumerica.class), idLote);
        } catch (Exception e) {
            listIdColetores = new ArrayList<ConteudoChaveNumerica>();
        }

        return listIdColetores;
    }

    public List<LoteSugestaoColetaPorAreaItem> findPedidosSomenteEmUmaNaArea(long idArea, long idLote) {

        String query = "select q.pedido_venda pedidoVenda, q.nivel, q.grupo, q.sub, q.item, q.endereco, q.qtde_coletar qtdeColetar from orion_exp_362 q, orion_exp_361 r, orion_exp_350 s " +
                " where r.id = q.id_lote_area " +
                " and r.id_lote = " + idLote +
                " and s.id = r.id_area " +
                " and s.id = " + idArea +
                " and exists ( " +
                " select count(*) from ( " +
                " select d.pedido_venda pedido, d.endereco from orion_exp_362 d, orion_exp_361 e, orion_exp_350 f " +
                " where e.id_lote = " + idLote +
                " and d.id_lote_area = e.id " +
                " and f.id = " + idArea +
                " and d.endereco between f.endereco_inicial and f.endereco_final " +
                " group by d.pedido_venda, d.endereco " +
                " order by d.endereco)) ";

        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(LoteSugestaoColetaPorAreaItem.class));
    }

    public List<LoteSugestaoColetaPorAreaItem> findAllPedidosByArea(long idArea, long idLote) {

        String query = " select q.pedido_venda pedidoVenda, q.nivel, q.grupo, q.sub, q.item, q.endereco, q.qtde_coletar qtdeColetar from orion_exp_362 q, orion_exp_361 r, orion_exp_350 s " +
                " where r.id = q.id_lote_area " +
                " and r.id_lote = " + idLote +
                " and s.id = r.id_area " +
                " and s.id = " + idArea +
                " order by q.endereco ";
        return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(LoteSugestaoColetaPorAreaItem.class));
    }
}