package dgn.com.br.sgco.entity;

import dgn.com.br.sgco.arq.Entidade;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class TipoAgendamento extends Entidade {
    /*
    ¯\_(ツ)_/¯

    Como deixar registrado na entidade de uma forma ótima?

    - Consulta
    - Cirurgia
    - Acompanhamento
    */
    private String descricao;
}
