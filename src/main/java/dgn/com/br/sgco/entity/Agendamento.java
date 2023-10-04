package dgn.com.br.sgco.entity;

import dgn.com.br.sgco.arq.Entidade;
import dgn.com.br.sgco.enumeration.FormaPagamento;
import dgn.com.br.sgco.enumeration.TipoAgendamento;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode
public class Agendamento extends Entidade {

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Dentista dentista;

    private TipoAgendamento tipo;

    private Date dataConsulta;

    private Duration tempoEstimado;

    private String observacoesPaciente;

    private String observacoesDentista;

    private FormaPagamento formaPagamento;
}
