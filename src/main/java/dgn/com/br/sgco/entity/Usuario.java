package dgn.com.br.sgco.entity;

import dgn.com.br.sgco.arq.Entidade;
import dgn.com.br.sgco.enumeration.Papel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class Usuario extends Entidade {
    @OneToOne
    private Pessoa pessoa;

    @OneToOne
    private Dentista dentista;

    @OneToOne
    private Paciente paciente;

    private String senha;

    @Enumerated(EnumType.ORDINAL)
    private Papel papel;
}
