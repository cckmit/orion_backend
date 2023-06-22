package br.com.live.administrativo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orion_fin_080")
public class MovimentoPagSeguroEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "hora_inicial_transacao")
    private String horaInicialTransacao;

    @Column(name = "data_venda_ajuste")
    private String dataVendaAjuste;

    @Column(name = "hora_venda_ajuste")
    private String horaVendaAjuste;

    @Column(name = "tipo_evento")
    private String tipoEvento;

    @Column(name = "tipo_transacao")
    private String tipoTransacao;

    @Column(name = "nsu")
    private String nsu;

    @Column(name = "filler1")
    private String filler1;

    @Column(name = "card_bin")
    private String cardBin;

    @Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "autorizacao")
    private String autorizacao;

    @Column(name = "cv")
    private String cv;

    @Column(name = "numero_serie_leitor")
    private String numeroSerieLeitor;

    @Column(name = "uso_interno_ps")
    private String usoInternoPS;

    @Column(name = "tipo_registro")
    private String tipoRegistro;

    @Column(name = "estabelecimento")
    private String estabelecimento;

    @Column(name = "data_inicial_transacao")
    private String dataInicialTransacao;

    @Column(name = "codigo_transacao")
    private String codigoTransacao;

    @Column(name = "codigo_venda")
    private String codigoVenda;

    @Column(name = "valor_total_transacao")
    private double valorTotalTransacao;

    @Column(name = "valor_parcela")
    private double valorParcela;

    @Column(name = "pagamento_prazo")
    private String pagamentoPrazo;

    @Column(name = "plano")
    private String plano;

    @Column(name = "parcela")
    private String parcela;

    @Column(name = "quantidade_parcela")
    private String quantidadeParcela;

    @Column(name = "data_movimentacao")
    private String dataMovimentacao;

    @Column(name = "taxa_parcela_comprador")
    private double taxaParcelaComprador;

    @Column(name = "tarifa_boleto_compra")
    private double tarifaBoletoCompra;

    @Column(name = "valor_original_transacao")
    private double valorOriginalTransacao;

    @Column(name = "taxa_parcela_vendedor")
    private double taxaParcelaVendedor;

    @Column(name = "taxa_intermediacao")
    private double taxaIntermediacao;

    @Column(name = "tarifa_intermediacao")
    private double tarifaIntermediacao;

    @Column(name = "tarifa_boleto_vendedor")
    private double tarifaBoletoVendedor;

    @Column(name = "taxa_rep_aplicacao")
    private double taxaRepAplicacao;

    @Column(name = "valor_liquido_transacao")
    private double valorLiquidoTransacao;

    @Column(name = "taxa_antecipacao")
    private int taxaAntecipacao;

    @Column(name = "valor_liquido_antecipacao")
    private double valorLiquidoAntecipacao;

    @Column(name = "status_pagamento")
    private String statusPagamento;

    @Column(name = "identificador_revenda")
    private String identificadorRevenda;

    @Column(name = "meio_pagamento")
    private String meioPagamento;

    @Column(name = "instituicao_financeira")
    private String instituicaoFinanceira;

    @Column(name = "canal_entrada")
    private String canalEntrada;

    @Column(name = "leitor")
    private String leitor;

    @Column(name = "meio_captura")
    private String meioCaptura;

    @Column(name = "cod_banco")
    private String codBanco;

    @Column(name = "banco_agencia")
    private String bancoAgencia;

    @Column(name = "conta_banco")
    private String contaBanco;

    @Column(name = "num_logico")
    private String numLogico;

    public MovimentoPagSeguroEntity(Long id, String horaInicialTransacao, String dataVendaAjuste, String horaVendaAjuste, String tipoEvento, String tipoTransacao, String nsu, String filler1, String cardBin, String cardHolder, String autorizacao, String cv, String numeroSerieLeitor, String usoInternoPS, String tipoRegistro, String estabelecimento, String dataInicialTransacao, String codigoTransacao, String codigoVenda, double valorTotalTransacao, double valorParcela, String pagamentoPrazo, String plano, String parcela, String quantidadeParcela, String dataMovimentacao, double taxaParcelaComprador, double tarifaBoletoCompra, double valorOriginalTransacao, double taxaParcelaVendedor, double taxaIntermediacao, double tarifaIntermediacao, double tarifaBoletoVendedor, double taxaRepAplicacao, double valorLiquidoTransacao, int taxaAntecipacao, double valorLiquidoAntecipacao, String statusPagamento, String identificadorRevenda, String meioPagamento, String instituicaoFinanceira, String canalEntrada, String leitor, String meioCaptura, String codBanco, String bancoAgencia, String contaBanco, String numLogico) {
        this.id = id;
        this.horaInicialTransacao = horaInicialTransacao;
        this.dataVendaAjuste = dataVendaAjuste;
        this.horaVendaAjuste = horaVendaAjuste;
        this.tipoEvento = tipoEvento;
        this.tipoTransacao = tipoTransacao;
        this.nsu = nsu;
        this.filler1 = filler1;
        this.cardBin = cardBin;
        this.cardHolder = cardHolder;
        this.autorizacao = autorizacao;
        this.cv = cv;
        this.numeroSerieLeitor = numeroSerieLeitor;
        this.usoInternoPS = usoInternoPS;
        this.tipoRegistro = tipoRegistro;
        this.estabelecimento = estabelecimento;
        this.dataInicialTransacao = dataInicialTransacao;
        this.codigoTransacao = codigoTransacao;
        this.codigoVenda = codigoVenda;
        this.valorTotalTransacao = valorTotalTransacao;
        this.valorParcela = valorParcela;
        this.pagamentoPrazo = pagamentoPrazo;
        this.plano = plano;
        this.parcela = parcela;
        this.quantidadeParcela = quantidadeParcela;
        this.dataMovimentacao = dataMovimentacao;
        this.taxaParcelaComprador = taxaParcelaComprador;
        this.tarifaBoletoCompra = tarifaBoletoCompra;
        this.valorOriginalTransacao = valorOriginalTransacao;
        this.taxaParcelaVendedor = taxaParcelaVendedor;
        this.taxaIntermediacao = taxaIntermediacao;
        this.tarifaIntermediacao = tarifaIntermediacao;
        this.tarifaBoletoVendedor = tarifaBoletoVendedor;
        this.taxaRepAplicacao = taxaRepAplicacao;
        this.valorLiquidoTransacao = valorLiquidoTransacao;
        this.taxaAntecipacao = taxaAntecipacao;
        this.valorLiquidoAntecipacao = valorLiquidoAntecipacao;
        this.statusPagamento = statusPagamento;
        this.identificadorRevenda = identificadorRevenda;
        this.meioPagamento = meioPagamento;
        this.instituicaoFinanceira = instituicaoFinanceira;
        this.canalEntrada = canalEntrada;
        this.leitor = leitor;
        this.meioCaptura = meioCaptura;
        this.codBanco = codBanco;
        this.bancoAgencia = bancoAgencia;
        this.contaBanco = contaBanco;
        this.numLogico = numLogico;
    }

    public MovimentoPagSeguroEntity(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoraInicialTransacao() {
        return horaInicialTransacao;
    }

    public void setHoraInicialTransacao(String horaInicialTransacao) {
        this.horaInicialTransacao = horaInicialTransacao;
    }

    public String getDataVendaAjuste() {
        return dataVendaAjuste;
    }

    public void setDataVendaAjuste(String dataVendaAjuste) {
        this.dataVendaAjuste = dataVendaAjuste;
    }

    public String getHoraVendaAjuste() {
        return horaVendaAjuste;
    }

    public void setHoraVendaAjuste(String horaVendaAjuste) {
        this.horaVendaAjuste = horaVendaAjuste;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public String getNsu() {
        return nsu;
    }

    public void setNsu(String nsu) {
        this.nsu = nsu;
    }

    public String getFiller1() {
        return filler1;
    }

    public void setFiller1(String filler1) {
        this.filler1 = filler1;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(String autorizacao) {
        this.autorizacao = autorizacao;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getNumeroSerieLeitor() {
        return numeroSerieLeitor;
    }

    public void setNumeroSerieLeitor(String numeroSerieLeitor) {
        this.numeroSerieLeitor = numeroSerieLeitor;
    }

    public String getUsoInternoPS() {
        return usoInternoPS;
    }

    public void setUsoInternoPS(String usoInternoPS) {
        this.usoInternoPS = usoInternoPS;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getDataInicialTransacao() {
        return dataInicialTransacao;
    }

    public void setDataInicialTransacao(String dataInicialTransacao) {
        this.dataInicialTransacao = dataInicialTransacao;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public String getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(String codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public double getValorTotalTransacao() {
        return valorTotalTransacao;
    }

    public void setValorTotalTransacao(double valorTotalTransacao) {
        this.valorTotalTransacao = valorTotalTransacao;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public String getPagamentoPrazo() {
        return pagamentoPrazo;
    }

    public void setPagamentoPrazo(String pagamentoPrazo) {
        this.pagamentoPrazo = pagamentoPrazo;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getQuantidadeParcela() {
        return quantidadeParcela;
    }

    public void setQuantidadeParcela(String quantidadeParcela) {
        this.quantidadeParcela = quantidadeParcela;
    }

    public String getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(String dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public double getTaxaParcelaComprador() {
        return taxaParcelaComprador;
    }

    public void setTaxaParcelaComprador(double taxaParcelaComprador) {
        this.taxaParcelaComprador = taxaParcelaComprador;
    }

    public double getTarifaBoletoCompra() {
        return tarifaBoletoCompra;
    }

    public void setTarifaBoletoCompra(double tarifaBoletoCompra) {
        this.tarifaBoletoCompra = tarifaBoletoCompra;
    }

    public double getValorOriginalTransacao() {
        return valorOriginalTransacao;
    }

    public void setValorOriginalTransacao(double valorOriginalTransacao) {
        this.valorOriginalTransacao = valorOriginalTransacao;
    }

    public double getTaxaParcelaVendedor() {
        return taxaParcelaVendedor;
    }

    public void setTaxaParcelaVendedor(double taxaParcelaVendedor) {
        this.taxaParcelaVendedor = taxaParcelaVendedor;
    }

    public double getTaxaIntermediacao() {
        return taxaIntermediacao;
    }

    public void setTaxaIntermediacao(double taxaIntermediacao) {
        this.taxaIntermediacao = taxaIntermediacao;
    }

    public double getTarifaIntermediacao() {
        return tarifaIntermediacao;
    }

    public void setTarifaIntermediacao(double tarifaIntermediacao) {
        this.tarifaIntermediacao = tarifaIntermediacao;
    }

    public double getTarifaBoletoVendedor() {
        return tarifaBoletoVendedor;
    }

    public void setTarifaBoletoVendedor(double tarifaBoletoVendedor) {
        this.tarifaBoletoVendedor = tarifaBoletoVendedor;
    }

    public double getTaxaRepAplicacao() {
        return taxaRepAplicacao;
    }

    public void setTaxaRepAplicacao(double taxaRepAplicacao) {
        this.taxaRepAplicacao = taxaRepAplicacao;
    }

    public double getValorLiquidoTransacao() {
        return valorLiquidoTransacao;
    }

    public void setValorLiquidoTransacao(double valorLiquidoTransacao) {
        this.valorLiquidoTransacao = valorLiquidoTransacao;
    }

    public int getTaxaAntecipacao() {
        return taxaAntecipacao;
    }

    public void setTaxaAntecipacao(int taxaAntecipacao) {
        this.taxaAntecipacao = taxaAntecipacao;
    }

    public double getValorLiquidoAntecipacao() {
        return valorLiquidoAntecipacao;
    }

    public void setValorLiquidoAntecipacao(double valorLiquidoAntecipacao) {
        this.valorLiquidoAntecipacao = valorLiquidoAntecipacao;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public String getIdentificadorRevenda() {
        return identificadorRevenda;
    }

    public void setIdentificadorRevenda(String identificadorRevenda) {
        this.identificadorRevenda = identificadorRevenda;
    }

    public String getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(String meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public String getInstituicaoFinanceira() {
        return instituicaoFinanceira;
    }

    public void setInstituicaoFinanceira(String instituicaoFinanceira) {
        this.instituicaoFinanceira = instituicaoFinanceira;
    }

    public String getCanalEntrada() {
        return canalEntrada;
    }

    public void setCanalEntrada(String canalEntrada) {
        this.canalEntrada = canalEntrada;
    }

    public String getLeitor() {
        return leitor;
    }

    public void setLeitor(String leitor) {
        this.leitor = leitor;
    }

    public String getMeioCaptura() {
        return meioCaptura;
    }

    public void setMeioCaptura(String meioCaptura) {
        this.meioCaptura = meioCaptura;
    }

    public String getCodBanco() {
        return codBanco;
    }

    public void setCodBanco(String codBanco) {
        this.codBanco = codBanco;
    }

    public String getBancoAgencia() {
        return bancoAgencia;
    }

    public void setBancoAgencia(String bancoAgencia) {
        this.bancoAgencia = bancoAgencia;
    }

    public String getContaBanco() {
        return contaBanco;
    }

    public void setContaBanco(String contaBanco) {
        this.contaBanco = contaBanco;
    }

    public String getNumLogico() {
        return numLogico;
    }

    public void setNumLogico(String numLogico) {
        this.numLogico = numLogico;
    }
}
