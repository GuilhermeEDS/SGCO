package dgn.com.br.sgco.entity;

import dgn.com.br.sgco.arq.Entidade;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class Consulta extends Entidade {
    @OneToOne
    @PrimaryKeyJoinColumn
    private Agendamento agendamento;

    private float valorProcedimento;

    private String descricao;
}
