package br.com.live.sistema.body;

import br.com.live.sistema.model.EnvolvidoTarefaProjeto;

import java.util.List;

public class BodyRegistroTarefaAtividadeProjeto {

    public Long id;
    public Long idProjeto;
    public Long idRegistroAtividade;
    public String descricao;
    public String acaoRealizada;
    public Long idResponsavel;
    public String dataInicio;
    public String horaInicio;
    public String dataFim;
    public String horaFim;
    public String documentoAssociado;
    public double custo;
    public double tempoGasto;
    public Long idTarefaAtividade;
    public List<EnvolvidoTarefaProjeto> envolvidoTarefaProjetoList;

}
