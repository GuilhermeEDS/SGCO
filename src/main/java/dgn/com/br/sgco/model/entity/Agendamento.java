package dgn.com.br.sgco.model.entity;

import dgn.com.br.sgco.arq.Entidade;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.Duration;
import java.util.Date;

@Entity
@Data
public class Agendamento extends Entidade {
    @ManyToOne
    private TipoAgendamento tipo;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Dentista dentista;

    private Date dataConsulta;

    private Duration tempoEstimado;

    private String observacoes;

    @ManyToOne
    private FormaPagamento formaPagamento;
}
