package br.com.live.model;

import java.util.Date;

public class ConsultaControleLancamentoHoras {

    public Date dataLancamento;
    public double totalHorasLancamentoDia;
    public int diaTrabalhado;

    public int getDiaTrabalhado() {
        return diaTrabalhado;
    }

    public void setDiaTrabalhado(int diaTrabalhado) {
        this.diaTrabalhado = diaTrabalhado;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public double getTotalHorasLancamentoDia() {
        return totalHorasLancamentoDia;
    }

    public void setTotalHorasLancamentoDia(double totalHorasLancamentoDia) {
        this.totalHorasLancamentoDia = totalHorasLancamentoDia;
    }
}
